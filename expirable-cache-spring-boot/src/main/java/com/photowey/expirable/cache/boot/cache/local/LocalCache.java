package com.photowey.expirable.cache.boot.cache.local;

import com.photowey.expirable.cache.boot.cache.Cache;

import java.util.concurrent.TimeUnit;

/**
 * {@code LocalCache}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class LocalCache implements Cache {

    private static final String LOCAL_CACHE_NAME = "local";

    private final LocalCacheService localCacheService;

    public LocalCache(LocalCacheService localCacheService) {
        this.localCacheService = localCacheService;
    }

    @Override
    public String getCacheName() {
        return LOCAL_CACHE_NAME;
    }

    @Override
    public <T> T get(Object key) {
        return this.localCacheService.get(key);
    }

    @Override
    public void put(Object key, Object value) {
        this.localCacheService.put(key, value);
    }

    @Override
    public void put(Object key, Object value, long expireMillis) {
        this.localCacheService.put(key, value, expireMillis);
    }

    @Override
    public void put(Object key, Object value, long expire, TimeUnit timeUnit) {
        this.localCacheService.put(key, value, expire, timeUnit);
    }

    @Override
    public <T> T pop(Object key) {
        T tt = this.localCacheService.get(key);
        this.localCacheService.delete(key);

        return tt;
    }
}
