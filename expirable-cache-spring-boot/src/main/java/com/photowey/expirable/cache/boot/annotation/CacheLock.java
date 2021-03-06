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

package com.photowey.expirable.cache.boot.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * {@code CacheLock}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheLock {

    String lockCandidate() default "redis";

    long expire() default 5L;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    String key() default "";
}
