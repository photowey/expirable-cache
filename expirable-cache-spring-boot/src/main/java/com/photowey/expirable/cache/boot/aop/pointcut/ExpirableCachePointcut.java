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

package com.photowey.expirable.cache.boot.aop.pointcut;

import com.photowey.expirable.cache.boot.annotation.CacheLock;
import com.photowey.expirable.cache.boot.annotation.ExpirableCache;
import com.photowey.expirable.cache.boot.cache.definition.CacheDefinition;
import com.photowey.expirable.cache.boot.cache.holder.CacheDefinitionHolder;
import com.photowey.expirable.cache.boot.factory.UuidFactory;
import com.photowey.expirable.cache.boot.lock.difinition.LockDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * {@code ExpirableCachePointcut}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public class ExpirableCachePointcut implements Pointcut, MethodMatcher {

    private static final Logger log = LoggerFactory.getLogger(ExpirableCachePointcut.class);

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        MethodClassKey methodClassKey = CacheDefinitionHolder.makeCacheKey(method, targetClass);
        CacheDefinition cacheDefinitionCache = CacheDefinitionHolder.getCacheDefinition(methodClassKey);
        if (cacheDefinitionCache != null) {
            return true;
        }

        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        if (AnnotatedElementUtils.hasAnnotation(targetClass, ExpirableCache.class)
                || AnnotatedElementUtils.hasAnnotation(specificMethod, ExpirableCache.class)) {
            ExpirableCache annotation = AnnotationUtils.getAnnotation(specificMethod, ExpirableCache.class);
            CacheDefinition cacheDefinition = this.populateCacheDefinition(specificMethod, annotation);
            CacheDefinitionHolder.putCacheDefinition(methodClassKey, cacheDefinition);
        }

        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... objects) {
        return false;
    }

    @Override
    public boolean isRuntime() {
        return false;
    }

    @Override
    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }


    private CacheDefinition populateCacheDefinition(Method specificMethod, ExpirableCache annotation) {
        CacheDefinition cacheDefinition = new CacheDefinition();
        cacheDefinition.setCacheNames(Objects.requireNonNull(annotation).name());
        cacheDefinition.setCacheKey(annotation.key());
        cacheDefinition.setExpire(annotation.expire());
        cacheDefinition.setTimeUnit(annotation.timeUnit());
        cacheDefinition.setTarget(specificMethod);
        CacheLock lock = annotation.lock();
        LockDefinition lockDefinition = this.populateLockDefinition(lock);
        cacheDefinition.setLockDefinition(lockDefinition);

        return cacheDefinition;
    }

    private LockDefinition populateLockDefinition(CacheLock lock) {
        LockDefinition lockDefinition = new LockDefinition();
        lockDefinition.setLockName(lock.lockName());
        lockDefinition.setExpire(lock.expire());
        lockDefinition.setTimeUnit(lock.timeUnit());
        String lockKey = lock.key();
        if (!StringUtils.hasText(lockKey)) {
            lockKey = UuidFactory.createUuid();
            if (log.isInfoEnabled()) {
                log.info("auto create the @ExpirableCache lockKey(uuid:{})", lockKey);
            }
        }
        lockDefinition.setLockKey(lockKey);
        return lockDefinition;
    }

}
