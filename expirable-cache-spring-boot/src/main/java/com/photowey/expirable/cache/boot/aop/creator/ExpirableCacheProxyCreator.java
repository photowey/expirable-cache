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

package com.photowey.expirable.cache.boot.aop.creator;

import com.photowey.expirable.cache.boot.annotation.ExpirableCache;
import com.photowey.expirable.cache.boot.util.AnnotationUtils;
import com.photowey.expirable.cache.boot.util.ProxyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.support.AopUtils;

/**
 * {@code ExpirableCacheProxyCreator}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public class ExpirableCacheProxyCreator extends AbstractAdvisorAutoProxyCreator {

    private static final Logger log = LoggerFactory.getLogger(ExpirableCacheProxyCreator.class);

    private PointcutAdvisor pointcutAdvisor;

    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        try {
            Class<?> targetClass = ProxyUtils.getTargetClass(bean);
            if (AnnotationUtils.hasAnnotation(new Class[]{targetClass}, ExpirableCache.class)) {
                return bean;
            }
        } catch (Exception e) {
            log.error("find the bean:{} exception, and ignore.", beanName);
        }

        if (!AopUtils.isAopProxy(bean)) {
            bean = super.wrapIfNecessary(bean, beanName, cacheKey);
        }

        return bean;
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource targetSource) {
        return super.getAdvicesAndAdvisorsForBean(beanClass, beanName, targetSource);
    }

    public PointcutAdvisor getPointcutAdvisor() {
        return pointcutAdvisor;
    }

    public void setPointcutAdvisor(PointcutAdvisor pointcutAdvisor) {
        this.pointcutAdvisor = pointcutAdvisor;
    }
}
