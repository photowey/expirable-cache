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

package com.photowey.expirable.cache.boot.lock;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code DefaultLockManager}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class DefaultLockManager implements LockManager, InitializingBean, BeanFactoryAware, BeanPostProcessor {

    private ListableBeanFactory beanFactory;

    private ConcurrentHashMap<String, Lock> lockHolder = new ConcurrentHashMap<>(3);

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
        beans.forEach((k, v) -> this.putLock(v.getLockName(), v));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }
}
