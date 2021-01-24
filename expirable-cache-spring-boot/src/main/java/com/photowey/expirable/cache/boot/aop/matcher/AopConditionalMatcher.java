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

package com.photowey.expirable.cache.boot.aop.matcher;

import com.photowey.expirable.cache.boot.util.CollectionUtils;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * {@code AopConditionalMatcher}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class AopConditionalMatcher implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        String[] beanNames = beanFactory.getBeanNamesForType(AbstractAdvisorAutoProxyCreator.class);
        if (CollectionUtils.isEmpty(beanNames)) {
            return true;
        }

        return false;
    }
}
