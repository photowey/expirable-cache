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

package com.photowey.expirable.cache.autoconfigurer.config;

import com.photowey.expirable.cache.autoconfigurer.config.local.ExpirableCacheLocalAutoConfiguration;
import com.photowey.expirable.cache.autoconfigurer.config.redis.ExpirableCacheRedisAutoConfiguration;
import com.photowey.expirable.cache.autoconfigurer.config.zookeeper.ExpirableCacheZookeeperAutoConfiguration;
import com.photowey.expirable.cache.autoconfigurer.selector.ExpirableCacheAutoConfigurerImportSelector;
import com.photowey.expirable.cache.boot.cache.CacheManager;
import com.photowey.expirable.cache.boot.cache.DefaultCacheManager;
import com.photowey.expirable.cache.boot.lock.DefaultLockManager;
import com.photowey.expirable.cache.boot.lock.LockManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@code ExpirableCacheAutoConfiguration}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
@Configuration
@Import(value = {
        CommonConfigurer.class,
        ExpirableCacheAutoConfigurerImportSelector.class,
        ExpirableCacheRedisAutoConfiguration.class,
        ExpirableCacheLocalAutoConfiguration.class,
        ExpirableCacheZookeeperAutoConfiguration.class,
        ExpirableCacheAopAutoConfiguration.class
})
public class ExpirableCacheAutoConfiguration {

    /**
     * Create Default {@link CacheManager} instance.
     *
     * @return {@link CacheManager}
     */
    @Bean
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        return new DefaultCacheManager();
    }

    /**
     * Create Default {@link LockManager} instance.
     *
     * @return {@link LockManager}
     */
    @Bean
    @ConditionalOnMissingBean
    public LockManager lockManager() {
        return new DefaultLockManager();
    }
}
