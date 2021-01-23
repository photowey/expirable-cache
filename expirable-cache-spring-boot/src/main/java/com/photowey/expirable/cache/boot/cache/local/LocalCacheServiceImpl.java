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

package com.photowey.expirable.cache.boot.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.photowey.expirable.cache.boot.properties.ExpirableCacheProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.TimeUnit;

/**
 * {@code LocalCacheServiceImpl}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class LocalCacheServiceImpl implements LocalCacheService, InitializingBean, ApplicationContextAware {

    Cache<String, Object> cacheTemplate = null;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ExpirableCacheProperties expirableCacheProperties = this.applicationContext.getBean(ExpirableCacheProperties.class);
        ExpirableCacheProperties.Local.Caffeine caffeine = expirableCacheProperties.getLocal().getCaffeine();
        this.cacheTemplate = Caffeine.newBuilder()
                .initialCapacity(expirableCacheProperties.getLocal().getCaffeine().getInitialCapacity())
                .expireAfterWrite(caffeine.getExpireAfterWrite(), caffeine.getTimeUnit())
                .maximumSize(expirableCacheProperties.getLocal().getCaffeine().getMaximumSize())
                .build();
    }

    @Override
    public String candidateName() {
        return "local";
    }

    @Override
    public <K, V> V get(K key) {
        String candidateKey = this.populateCacheKey(key);
        Object target = this.cacheTemplate.getIfPresent(candidateKey);
        return (V) target;
    }

    @Override
    public <K, V> void put(K key, V value) {
        String candidateKey = this.populateCacheKey(key);
        this.cacheTemplate.put(candidateKey, value);
    }

    @Override
    public <K, V> void put(K key, V value, long expire) {
        this.put(key, value);
    }

    @Override
    public <K, V> void put(K key, V value, long expire, TimeUnit timeUnit) {
        this.put(key, value);
    }

    @Override
    public void delete(Object key) {
        String candidateKey = this.populateCacheKey(key);
        this.cacheTemplate.invalidate(candidateKey);
    }

    @Override
    public boolean hasKey(Object key) {
        return true;
    }

    private <T> String populateCacheKey(T key) {
        final String keyValue = String.valueOf(key);

        return keyValue;
    }
}
