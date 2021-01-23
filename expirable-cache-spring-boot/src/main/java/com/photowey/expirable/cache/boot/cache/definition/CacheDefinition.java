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

package com.photowey.expirable.cache.boot.cache.definition;

import com.photowey.expirable.cache.boot.lock.difinition.LockDefinition;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * {@code CacheDefinition}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class CacheDefinition implements Serializable {

    private static final long serialVersionUID = 8840628418999945791L;

    private String[] cacheNames;
    private String cacheKey;
    private Long expire;
    private TimeUnit timeUnit;
    private Method target;
    private LockDefinition lockDefinition;

    public CacheDefinition() {
    }

    public CacheDefinition(String[] cacheNames, String cacheKey, Long expire, TimeUnit timeUnit, Method target, LockDefinition lockDefinition) {
        this.cacheNames = cacheNames;
        this.cacheKey = cacheKey;
        this.expire = expire;
        this.timeUnit = timeUnit;
        this.target = target;
        this.lockDefinition = lockDefinition;
    }

    public String[] getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(String[] cacheNames) {
        this.cacheNames = cacheNames;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Method getTarget() {
        return target;
    }

    public void setTarget(Method target) {
        this.target = target;
    }

    public LockDefinition getLockDefinition() {
        return lockDefinition;
    }

    public void setLockDefinition(LockDefinition lockDefinition) {
        this.lockDefinition = lockDefinition;
    }
}
