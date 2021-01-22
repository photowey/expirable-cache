package com.photowey.expirable.cache.boot.lock.redis;

import com.photowey.expirable.cache.boot.lock.Lock;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

/**
 * {@code RedisLock}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class RedisLock implements Lock, InitializingBean, EnvironmentAware, BeanFactoryAware {

    private static final String REDIS_LOCK_NAME = "redis";

    private ConfigurableEnvironment environment;
    private ListableBeanFactory beanFactory;

    @Override
    public String getLockName() {
        return REDIS_LOCK_NAME;
    }

    @Override
    public void lock(String key, long releaseMillis, TimeUnit timeUnit) {

    }

    @Override
    public void unlock() {

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
}
