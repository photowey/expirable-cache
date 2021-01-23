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

package com.photowey.expirable.cache.autoconfigurer.config.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.photowey.expirable.cache.boot.cache.local.LocalCacheConditionMatcher;
import com.photowey.expirable.cache.boot.cache.local.LocalCacheService;
import com.photowey.expirable.cache.boot.cache.local.LocalCacheServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * {@code ExpirableCacheLocalAutoConfiguration}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(Cache.class)
public class ExpirableCacheLocalAutoConfiguration {

    /**
     * {@link LocalCacheService}
     *
     * @return {@link LocalCacheService}
     */
    @Bean
    @Conditional(value = LocalCacheConditionMatcher.class)
    public LocalCacheService localCacheService() {
        return new LocalCacheServiceImpl();
    }
}
