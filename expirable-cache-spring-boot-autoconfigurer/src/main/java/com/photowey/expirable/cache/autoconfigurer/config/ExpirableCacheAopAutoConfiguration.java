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

import com.photowey.expirable.cache.boot.aop.advice.ExpirableCacheAdvice;
import com.photowey.expirable.cache.boot.aop.advisor.ExpirableCacheAdvisor;
import com.photowey.expirable.cache.boot.aop.creator.ExpirableCacheProxyCreator;
import com.photowey.expirable.cache.boot.aop.matcher.AopConditionalMatcher;
import com.photowey.expirable.cache.boot.parser.IExpressionParser;
import com.photowey.expirable.cache.boot.parser.impl.ExpressionParserImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * {@code ExpirableCacheAopAutoConfiguration}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
@Configuration
public class ExpirableCacheAopAutoConfiguration {

    @Bean
    public ExpressionParser expressionParser() {
        return new SpelExpressionParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public IExpressionParser expirableCacheExpressionParser() {
        return new ExpressionParserImpl(this.expressionParser());
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ExpirableCacheAdvice expirableCacheAdvice() {
        return new ExpirableCacheAdvice(this.expirableCacheExpressionParser());
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ExpirableCacheAdvisor expirableCacheAdvisor() {
        ExpirableCacheAdvisor expirableCacheAdvisor = new ExpirableCacheAdvisor();
        expirableCacheAdvisor.setAdvice(this.expirableCacheAdvice());
        expirableCacheAdvisor.setOrder(Ordered.LOWEST_PRECEDENCE - 10);

        return expirableCacheAdvisor;
    }

    @Bean
    @Conditional(value = AopConditionalMatcher.class)
    public ExpirableCacheProxyCreator expirableCacheProxyCreator() {
        ExpirableCacheProxyCreator expirableCacheProxyCreator = new ExpirableCacheProxyCreator();
        expirableCacheProxyCreator.setPointcutAdvisor(this.expirableCacheAdvisor());

        return expirableCacheProxyCreator;
    }
}
