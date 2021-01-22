package com.photowey.expirable.cache.boot.lock;

/**
 * {@code LockManager}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public interface LockManager {

    Lock getLock(String lockName);

    void putLock(String lockName, Lock lock);

    // pop()
}
