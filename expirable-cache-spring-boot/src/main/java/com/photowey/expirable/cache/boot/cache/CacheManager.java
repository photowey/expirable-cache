package com.photowey.expirable.cache.boot.cache;

/**
 * {@code CacheManager}
 * The Cache Manager
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public interface CacheManager {

    Cache getCache(String cacheName);

    void putCache(String cacheName, Cache cache);
}
