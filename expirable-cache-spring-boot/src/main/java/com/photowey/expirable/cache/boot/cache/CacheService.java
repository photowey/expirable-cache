package com.photowey.expirable.cache.boot.cache;

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

}
