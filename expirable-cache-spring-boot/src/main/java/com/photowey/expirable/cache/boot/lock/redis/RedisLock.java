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

package com.photowey.expirable.cache.boot.lock.redis;

import com.photowey.expirable.cache.boot.lock.Lock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * {@code RedisLock}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class RedisLock implements Lock, InitializingBean, BeanFactoryAware {

    private static final String REDIS_LOCK_NAME = "redis";

    private ListableBeanFactory beanFactory;

    @Override
    public String getLockName() {
        return REDIS_LOCK_NAME;
    }

    private RLock lock;

    @Override
    public void lock(String key, long releaseMillis, TimeUnit timeUnit) throws Exception {
        RedissonClient redissonClient = this.beanFactory.getBean(RedissonClient.class);
        lock = redissonClient.getLock(key);
        lock.lock();
    }

    @Override
    public void unlock() throws Exception {
        lock.unlock();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RedissonClient redissonClient = this.beanFactory.getBean(RedissonClient.class);
        if (ObjectUtils.isEmpty(redissonClient)) {
            throw new NullPointerException("not found the instance of org.redisson.api.RedissonClient.");
        }
    }
}
