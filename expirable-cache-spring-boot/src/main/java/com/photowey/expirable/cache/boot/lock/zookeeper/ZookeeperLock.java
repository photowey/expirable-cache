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
 * {@code ZookeeperLock}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class ZookeeperLock implements Lock, InitializingBean, EnvironmentAware, BeanFactoryAware {

    private static final String ZOOKEEPER_LOCK_NAME = "zookeeper";

    private ConfigurableEnvironment environment;
    private ListableBeanFactory beanFactory;

    @Override
    public String getLockName() {
        return ZOOKEEPER_LOCK_NAME;
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
