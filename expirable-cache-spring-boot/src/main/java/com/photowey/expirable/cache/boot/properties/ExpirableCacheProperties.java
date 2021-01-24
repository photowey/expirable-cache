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

package com.photowey.expirable.cache.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * {@code ExpirableCacheProperties}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "expirable.cache")
public class ExpirableCacheProperties {

    private Redis redis = new Redis();
    private Local local = new Local();

    public static class Redis {
        private String host;
        private String password;
        private Integer port;
        private int maxTotal = 1024;
        private int maxIdle = 16;
        // default 3_000
        private int timeout = 10_000;
        // default 10_000
        private int connectTimeout = 30_000;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }
    }

    public static class Local {
        private boolean enabled = false;
        private Caffeine caffeine = new Caffeine();

        public static class Caffeine {
            private int initialCapacity = 8;
            private long expireAfterWrite = 60L;
            private TimeUnit timeUnit = TimeUnit.SECONDS;
            private long maximumSize = 2048;

            public int getInitialCapacity() {
                return initialCapacity;
            }

            public void setInitialCapacity(int initialCapacity) {
                this.initialCapacity = initialCapacity;
            }

            public long getExpireAfterWrite() {
                return expireAfterWrite;
            }

            public void setExpireAfterWrite(long expireAfterWrite) {
                this.expireAfterWrite = expireAfterWrite;
            }

            public TimeUnit getTimeUnit() {
                return timeUnit;
            }

            public void setTimeUnit(TimeUnit timeUnit) {
                this.timeUnit = timeUnit;
            }

            public long getMaximumSize() {
                return maximumSize;
            }

            public void setMaximumSize(long maximumSize) {
                this.maximumSize = maximumSize;
            }
        }

        public boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public Caffeine getCaffeine() {
            return caffeine;
        }

        public void setCaffeine(Caffeine caffeine) {
            this.caffeine = caffeine;
        }
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
