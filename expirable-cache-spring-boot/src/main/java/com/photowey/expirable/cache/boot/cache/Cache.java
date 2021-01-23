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

import java.util.concurrent.TimeUnit;

/**
 * {@code Cache} is an interface indicating that the object is a cache-object
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public interface Cache {

    String getCacheName();

    <T> T get(Object key);

    void put(Object key, Object value);

    void put(Object key, Object value, long expireMillis);

    void put(Object key, Object value, long expire, TimeUnit timeUnit);

    <T> T pop(Object key);
}
