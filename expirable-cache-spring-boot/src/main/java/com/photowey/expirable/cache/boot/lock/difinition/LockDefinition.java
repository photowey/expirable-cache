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

package com.photowey.expirable.cache.boot.lock.difinition;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * {@code LockDefinition}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class LockDefinition implements Serializable {

    private static final long serialVersionUID = 2071351913934062125L;

    private String lockName;
    private Long expire;
    private TimeUnit timeUnit;
    private String lockKey;

    public LockDefinition() {
    }

    public LockDefinition(String lockName, Long expire, TimeUnit timeUnit, String lockKey) {
        this.lockName = lockName;
        this.expire = expire;
        this.timeUnit = timeUnit;
        this.lockKey = lockKey;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
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

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }
}
