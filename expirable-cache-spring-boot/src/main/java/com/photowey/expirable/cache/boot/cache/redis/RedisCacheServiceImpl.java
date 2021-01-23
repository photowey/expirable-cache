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

package com.photowey.expirable.cache.boot.cache.redis;

import com.photowey.expirable.cache.boot.constant.ExpirableCacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * {@code RedisCacheServiceImpl}
 * RedisCacheService
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class RedisCacheServiceImpl implements RedisCacheService {

    @Autowired
    @Qualifier(ExpirableCacheConstants.REDIS_TEMPLATE_BEAN_ID)
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String candidateName() {
        return "redis";
    }

    @Override
    public <K, V> V get(K key) {
        return (V) this.redisTemplate.opsForValue().get(this.populateCacheKey(key));
    }

    @Override
    public <K, V> void put(K key, V value) {
        this.redisTemplate.opsForValue().set(this.populateCacheKey(key), value);
    }

    @Override
    public <K, V> void put(K key, V value, long expire) {
        this.redisTemplate.opsForValue().set(this.populateCacheKey(key), value, expire);
    }

    @Override
    public <K, V> void put(K key, V value, long expire, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(this.populateCacheKey(key), value, expire, timeUnit);
    }

    @Override
    public void delete(Object key) {
        String cacheKey = this.populateCacheKey(key);
        if (this.hasKey(cacheKey)) {
            this.redisTemplate.delete(cacheKey);
        }
    }

    @Override
    public boolean hasKey(Object key) {
        return this.redisTemplate.hasKey(this.populateCacheKey(key));
    }

    private <T> String populateCacheKey(T key) {
        return String.valueOf(key);
    }
}
