package com.photowey.expirable.cache.boot.cache;

/**
 * {@code DeletedCacheService}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public interface DeletedCacheService extends CacheService {

    void delete(Object key);

}
