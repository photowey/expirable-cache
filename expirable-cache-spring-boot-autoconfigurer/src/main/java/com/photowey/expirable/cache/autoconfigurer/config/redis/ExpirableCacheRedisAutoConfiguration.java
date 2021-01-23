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

package com.photowey.expirable.cache.autoconfigurer.config.redis;

import com.photowey.expirable.cache.boot.cache.redis.RedisCache;
import com.photowey.expirable.cache.boot.cache.redis.RedisCacheService;
import com.photowey.expirable.cache.boot.cache.redis.RedisCacheServiceImpl;
import com.photowey.expirable.cache.boot.constant.ExpirableCacheConstants;
import com.photowey.expirable.cache.boot.lock.redis.RedisLock;
import com.photowey.expirable.cache.boot.properties.ExpirableCacheProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * {@code ExpirableCacheRedisAutoConfiguration}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
@AutoConfigureAfter(org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
public class ExpirableCacheRedisAutoConfiguration {

    /**
     * {@link RedisCacheService}
     *
     * @return {@link RedisCacheService}
     */
    @Bean
    public RedisCacheService redisCacheService() {
        return new RedisCacheServiceImpl();
    }

    /**
     * Default cache.
     *
     * @return {@link RedisCache}
     */
    @Bean
    public RedisCache redisCache() {
        RedisCache redisCache = new RedisCache(this.redisCacheService());
        return redisCache;
    }

    /**
     * Default lock.
     *
     * @return {@link RedisLock}
     */
    @Bean
    public RedisLock redisLock() {
        return new RedisLock();
    }

    /**
     * Create {@link RedissonClient} instance if necessary.
     *
     * @param expirableCacheProperties {@link ExpirableCacheProperties}
     * @return {@link RedissonClient}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedissonClient redissonClient(ExpirableCacheProperties expirableCacheProperties) {
        String addressTemplate = "redis://%s:%d";
        ExpirableCacheProperties.Redis redis = expirableCacheProperties.getRedis();
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(String.format(addressTemplate, redis.getHost(), redis.getPort()));
        singleServerConfig.setPassword(redis.getPassword());
        singleServerConfig.setConnectionPoolSize(redis.getMaxIdle());
        singleServerConfig.setConnectionMinimumIdleSize(redis.getMaxIdle());
        singleServerConfig.setTimeout(redis.getTimeout());
        singleServerConfig.setConnectTimeout(redis.getConnectTimeout());

        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisSerializer<String> stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    @ConditionalOnMissingBean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean(ExpirableCacheConstants.REDIS_TEMPLATE_BEAN_ID)
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> expirableCacheRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisPromTemplate = new RedisTemplate<>();
        this.initDomainRedisTemplate(redisPromTemplate, redisConnectionFactory);
        return redisPromTemplate;
    }

    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory redisConnectionFactory) {
        redisTemplate.setKeySerializer(this.stringRedisSerializer());
        redisTemplate.setHashKeySerializer(this.stringRedisSerializer());

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.afterPropertiesSet();
    }
}
