package com.photowey.expirable.cache.boot.cache;

import java.util.concurrent.TimeUnit;

/**
 * {@code Cache} is an interface indicating that the object is a cache-object
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public interface Cache {

    String getCacheName();

    <T> T get(Object key);

    void put(Object key, Object value);

    void put(Object key, Object value, long expireMillis);

    void put(Object key, Object value, long expire, TimeUnit timeUnit);

    <T> T pop(Object key);
}
