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

package com.photowey.expirable.cache.boot.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code DefaultCacheManager}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class DefaultCacheManager implements CacheManager, InitializingBean, BeanFactoryAware {

    private ListableBeanFactory beanFactory;

    private ConcurrentHashMap<String, Cache> cacheHolder = new ConcurrentHashMap<>(5);

    @Override
    public Cache getCache(String cacheName) {
        return this.cacheHolder.get(cacheName);
    }

    @Override
    public void putCache(String cacheName, Cache cache) {
        this.cacheHolder.put(cacheName, cache);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Cache> beans = this.beanFactory.getBeansOfType(Cache.class);
        beans.forEach((k, v) -> cacheHolder.put(k, v));
    }
}
