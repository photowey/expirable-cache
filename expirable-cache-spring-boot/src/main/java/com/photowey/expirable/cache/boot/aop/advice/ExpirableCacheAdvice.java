/*
 * Copyright © 2020-2021 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.photowey.expirable.cache.boot.aop.advice;

import com.photowey.expirable.cache.boot.annotation.ExpirableCache;
import com.photowey.expirable.cache.boot.aop.creator.ExpirableCacheProxyCreator;
import com.photowey.expirable.cache.boot.cache.Cache;
import com.photowey.expirable.cache.boot.cache.CacheManager;
import com.photowey.expirable.cache.boot.cache.definition.CacheDefinition;
import com.photowey.expirable.cache.boot.cache.holder.CacheDefinitionHolder;
import com.photowey.expirable.cache.boot.cache.redis.RedisCache;
import com.photowey.expirable.cache.boot.handler.type.TypeHandler;
import com.photowey.expirable.cache.boot.lock.Lock;
import com.photowey.expirable.cache.boot.lock.LockManager;
import com.photowey.expirable.cache.boot.parser.IExpressionParser;
import com.photowey.expirable.cache.boot.util.CollectionUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * {@code ExpirableCacheAdvice}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public class ExpirableCacheAdvice implements MethodInterceptor, BeanFactoryAware {

    private static final Logger log = LoggerFactory.getLogger(ExpirableCacheProxyCreator.class);

    private final IExpressionParser expressionParser;
    private ListableBeanFactory beanFactory;

    public ExpirableCacheAdvice(IExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method target = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();
        ExpirableCache annotation = target.getAnnotation(ExpirableCache.class);
        String cacheKey = this.parseCacheKey(annotation.key(), target, arguments);
        this.throwException(target, annotation, cacheKey);
        Class<?> targetClass = (methodInvocation.getThis() != null ? AopUtils.getTargetClass(methodInvocation.getThis()) : null);
        CacheDefinition cacheDefinition = CacheDefinitionHolder.getCacheDefinition(target, targetClass);
        Object targetCache = this.getCache(cacheKey, cacheDefinition);
        if (!ObjectUtils.isEmpty(targetCache)) {
            if (log.isInfoEnabled()) {
                log.info("hits:[{}] the cacheValue in cache", target.getName());
            }
            if (annotation.handleType()) {
                return this.handleTypeConvert(target, targetCache);
            }
            return targetCache;
        }
        LockManager lockManager = this.beanFactory.getBean(LockManager.class);
        // Find lock candidate.
        Lock lock = lockManager.getLock(cacheDefinition.getLockDefinition().getLockName());
        try {
            lock.lock(cacheDefinition.getLockDefinition().getLockKey(), cacheDefinition.getLockDefinition().getExpire(),
                    cacheDefinition.getLockDefinition().getTimeUnit());
            // Get cache again is very important.
            targetCache = this.getCache(cacheKey, cacheDefinition);
            if (!ObjectUtils.isEmpty(targetCache)) {
                if (log.isInfoEnabled()) {
                    log.info("hits:[{}] the cache-value in cache", target.getName());
                }
                if (annotation.handleType()) {
                    return this.handleTypeConvert(target, targetCache);
                }
                return targetCache;
            }
            // Hits cache failure.
            Object invocationResult = methodInvocation.proceed();
            if (!ObjectUtils.isEmpty(invocationResult)) {
                if (log.isInfoEnabled()) {
                    log.info("easyCache : query from db!");
                }
                this.putCache(cacheDefinition, cacheKey, invocationResult);

                return invocationResult;
            }
        } catch (Exception e) {
            log.error("handle the method:[{}] cache-value exception", target.getName());
        } finally {
            lock.unlock();
        }

        return null;
    }

    private void throwException(Method target, ExpirableCache annotation, String cacheKey) {
        if (!StringUtils.hasText(cacheKey)) {
            throw new IllegalArgumentException(String.format("can't parse the candidate-cache-key:[%s] from target:[%s]",
                    annotation.key(), target.getName()));
        }
    }

    private Object handleTypeConvert(Method target, Object targetCache) {
        Map<String, TypeHandler> typeHandlerMap = this.beanFactory.getBeansOfType(TypeHandler.class);
        for (Map.Entry<String, TypeHandler> entry : typeHandlerMap.entrySet()) {
            if (entry.getValue().supports(target.getGenericReturnType())) {
                return entry.getValue().handleType(targetCache, target.getGenericReturnType());
            }
        }

        return targetCache;
    }

    private Object getCache(String cacheKey, CacheDefinition cacheDefinition) {
        String[] cacheNames = cacheDefinition.getCacheNames();
        CacheManager cacheManager = this.beanFactory.getBean(CacheManager.class);
        if (CollectionUtils.isEmpty(cacheNames)) {
            // Default cache.
            RedisCache redisCache = this.beanFactory.getBean(RedisCache.class);
            Cache defaultCacheCandidate = cacheManager.getCache(redisCache.getCacheName());
            Object targetCache = defaultCacheCandidate.get(cacheKey);
            if (!ObjectUtils.isEmpty(targetCache)) {
                return targetCache;
            }
        }

        for (String cacheName : cacheNames) {
            Cache defaultCacheCandidate = cacheManager.getCache(cacheName);
            Object targetCache = defaultCacheCandidate.get(cacheKey);
            if (!ObjectUtils.isEmpty(targetCache)) {
                return targetCache;
            }
        }

        return null;
    }

    private void putCache(CacheDefinition cacheDefinition, String cacheKey, Object invocationResult) {
        String[] cacheNames = cacheDefinition.getCacheNames();
        CacheManager cacheManager = this.beanFactory.getBean(CacheManager.class);
        if (CollectionUtils.isEmpty(cacheNames)) {
            RedisCache redisCache = this.beanFactory.getBean(RedisCache.class);
            Cache defaultCacheCandidate = cacheManager.getCache(redisCache.getCacheName());
            defaultCacheCandidate.put(cacheKey, invocationResult);
        }
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            if (!ObjectUtils.isEmpty(invocationResult)) {
                cache.put(cacheKey, invocationResult);
            }
        }
    }

    private String parseCacheKey(String expression, Method target, Object[] args) {
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(target);
        return this.expressionParser.parseExpression(expression, parameterNames, args);
    }
}
