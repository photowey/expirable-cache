package com.photowey.expirable.cache.boot.cache.redis;

import com.photowey.expirable.cache.boot.cache.DeletedCacheService;
import com.photowey.expirable.cache.boot.cache.NamedCacheService;

/**
 * {@code RedisCacheService}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public interface RedisCacheService extends NamedCacheService, DeletedCacheService {
}
