package com.photowey.expirable.cache.boot.cache;

/**
 * {@code NamedCacheService}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public interface NamedCacheService extends CacheService {

    /**
     * Cache name.
     *
     * @return cache name
     */
    String candidateName();
}
