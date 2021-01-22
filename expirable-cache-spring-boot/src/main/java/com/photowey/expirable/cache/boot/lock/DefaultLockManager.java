package com.photowey.expirable.cache.boot.lock;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code DefaultLockManager}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class DefaultLockManager implements LockManager, InitializingBean, BeanFactoryAware {

    private ListableBeanFactory beanFactory;

    private ConcurrentHashMap<String, Lock> lockHolder = new ConcurrentHashMap<>(5);

    @Override
    public Lock getLock(String lockName) {
        return this.lockHolder.get(lockName);
    }

    @Override
    public void putLock(String lockName, Lock lock) {
        this.lockHolder.put(lockName, lock);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Lock> beans = this.beanFactory.getBeansOfType(Lock.class);
        beans.forEach((k, v) -> lockHolder.put(k, v));
    }
}