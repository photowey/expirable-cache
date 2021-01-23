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

package com.photowey.expirable.cache.boot.cache.holder;

import com.photowey.expirable.cache.boot.cache.definition.CacheDefinition;
import org.springframework.core.MethodClassKey;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code CacheDefinitionHolder}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public final class CacheDefinitionHolder {

    private CacheDefinitionHolder() {
        // Holder class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    private static final ConcurrentHashMap<Object, CacheDefinition> CACHE_DEFINITION_MAP = new ConcurrentHashMap<>();

    public static CacheDefinition getCacheDefinition(Object key) {
        return CACHE_DEFINITION_MAP.get(key);
    }

    public static CacheDefinition getCacheDefinition(Method method, Class<?> targetClass) {
        return CACHE_DEFINITION_MAP.get(makeCacheKey(method, targetClass));
    }

    public static void putCacheDefinition(Object key, CacheDefinition cacheDefinition) {
        CACHE_DEFINITION_MAP.put(key, cacheDefinition);
    }

    public static MethodClassKey makeCacheKey(Method method, Class<?> targetClass) {
        return new MethodClassKey(method, targetClass);
    }

}
