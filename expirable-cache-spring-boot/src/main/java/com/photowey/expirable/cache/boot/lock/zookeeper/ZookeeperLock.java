/*
 * Copyright © 2020-2021 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.photowey.expirable.cache.boot.lock.zookeeper;

import com.photowey.expirable.cache.boot.lock.Lock;
import com.photowey.expirable.cache.boot.properties.ExpirableCacheProperties;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

/**
 * {@code ZookeeperLock}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class ZookeeperLock implements Lock, InitializingBean, EnvironmentAware, BeanFactoryAware, DisposableBean {

    private static final String ZOOKEEPER_LOCK_NAME = "zookeeper";

    private ConfigurableEnvironment environment;
    private ListableBeanFactory beanFactory;

    private final CuratorFramework curatorFramework;

    private InterProcessLock lock;

    public ZookeeperLock(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    @Override
    public String getLockName() {
        return ZOOKEEPER_LOCK_NAME;
    }

    @Override
    public void lock(String key, long releaseMillis, TimeUnit timeUnit) throws Exception {
        // FIXME
        ExpirableCacheProperties expirableCacheProperties = this.beanFactory.getBean(ExpirableCacheProperties.class);
        ExpirableCacheProperties.Zookeeper zookeeper = expirableCacheProperties.getZookeeper();
        lock = new InterProcessMutex(this.curatorFramework, zookeeper.getLockPath());
        if (releaseMillis > 0) {
            lock.acquire(releaseMillis, timeUnit);
        } else {
            lock.acquire();
        }
    }

    @Override
    public void unlock() throws Exception {
        // FIXME
        lock.release();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void destroy() throws Exception {
        this.curatorFramework.close();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
}
