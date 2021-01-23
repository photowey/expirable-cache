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

import com.photowey.expirable.cache.boot.cache.Cache;

import java.util.concurrent.TimeUnit;

/**
 * {@code RedisCache}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class RedisCache implements Cache {

    private static final String REDIS_CACHE_NAME = "redis";

    private final RedisCacheService redisCacheService;

    public RedisCache(RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
    }

    @Override
    public String getCacheName() {
        return REDIS_CACHE_NAME;
    }

    @Override
    public <T> T get(Object key) {
        return this.redisCacheService.get(key);
    }

    @Override
    public void put(Object key, Object value) {
        this.redisCacheService.put(key, value);
    }

    @Override
    public void put(Object key, Object value, long expireMillis) {
        this.redisCacheService.put(key, value, expireMillis);
    }

    @Override
    public void put(Object key, Object value, long expire, TimeUnit timeUnit) {
        this.redisCacheService.put(key, value, expire, timeUnit);
    }

    @Override
    public <T> T pop(Object key) {
        T tt = this.redisCacheService.get(key);
        this.redisCacheService.delete(key);

        return tt;
    }
}
