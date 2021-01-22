package com.photowey.expirable.cache.boot.cache;

import java.util.concurrent.TimeUnit;

/**
 * {@code CacheService}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public interface CacheService {

    <K, T> T get(K k);

    <K, V> V put(K k, V v);

    <K, V> void put(K key, V value, long expire);

    <K, V> void put(K key, V value, long expire, TimeUnit timeUnit);
}
