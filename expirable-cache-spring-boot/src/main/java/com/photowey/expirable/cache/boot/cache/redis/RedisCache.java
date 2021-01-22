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
