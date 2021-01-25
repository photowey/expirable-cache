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

package com.photowey.expirable.cache.autoconfigurer.config.zookeeper;

import com.photowey.expirable.cache.boot.lock.zookeeper.ZookeeperLock;
import com.photowey.expirable.cache.boot.properties.ExpirableCacheProperties;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code ExpirableCacheZookeeperAutoConfiguration}
 *
 * @author photowey
 * @date 2021/01/25
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(value = {CuratorFramework.class, Watcher.class})
public class ExpirableCacheZookeeperAutoConfiguration implements BeanFactoryAware {

    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework() {
        ExpirableCacheProperties expirableCacheProperties = this.beanFactory.getBean(ExpirableCacheProperties.class);
        ExpirableCacheProperties.Zookeeper zookeeper = expirableCacheProperties.getZookeeper();
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zookeeper.getBaseSleepTimeMs(), zookeeper.getMaxRetries());
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zookeeper.getConnectString(), zookeeper.getSessionTimeoutMs(), zookeeper.getConnectionTimeoutMs(), retryPolicy);
        curatorFramework.start();

        return curatorFramework;
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperLock zookeeperLock() {
        return new ZookeeperLock(this.curatorFramework());
    }
}
