package com.photowey.expirable.cache.boot.lock;

import java.util.concurrent.TimeUnit;

/**
 * {@code Lock}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public interface Lock {

    String getLockName();

    void lock(String key, long releaseMillis, TimeUnit timeUnit);

    void unlock();
}
