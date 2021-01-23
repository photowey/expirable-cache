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

package com.photowey.expirable.cache.boot.util;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * {@code ProxyUtils}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public final class ProxyUtils {

    private ProxyUtils() {
        // utils class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * Gets target object
     *
     * @param proxy {@link Object}
     * @return {@link Object}
     */
    public static Object getTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            proxy = getJdkDynamicProxyTargetObject(proxy);
        }
        if (AopUtils.isCglibProxy(proxy)) {
            proxy = getCglibProxyTargetObject(proxy);
        }

        return getTarget(proxy);
    }

    /**
     * Gets target Class
     *
     * @param proxy {@link Object}
     * @return {@link Class<?>}
     */
    public static Class<?> getTargetClass(Object proxy) throws Exception {
        if (proxy == null) {
            throw new java.lang.IllegalArgumentException("proxy can not be null");
        }
        // not proxy
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy.getClass();
        }
        AdvisedSupport advisedSupport = getAdvisedSupport(proxy);
        Object target = advisedSupport.getTargetSource().getTarget();
        if (target == null) {
            if (CollectionUtils.isNotEmpty(advisedSupport.getProxiedInterfaces())) {
                return advisedSupport.getProxiedInterfaces()[0];
            } else {
                return proxy.getClass();
            }
        } else {
            return getTargetClass(target);
        }
    }

    /**
     * Gets advised support.
     *
     * @param proxy the proxy
     * @return the advised support
     * @throws Exception the exception
     */
    public static AdvisedSupport getAdvisedSupport(Object proxy) throws Exception {
        Field h;
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            h = proxy.getClass().getSuperclass().getDeclaredField("h");
        } else {
            h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        }
        ReflectionUtils.makeAccessible(h);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        ReflectionUtils.makeAccessible(advised);

        return (AdvisedSupport) advised.get(dynamicAdvisedInterceptor);
    }

    /**
     * Gets cglib proxy target object
     *
     * @param proxy {@link Object}
     * @return {@link Object}
     * @throws Exception
     */
    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field field = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        ReflectionUtils.makeAccessible(field);
        Object dynamicAdvisedInterceptor = field.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        ReflectionUtils.makeAccessible(advised);

        return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
    }

    /**
     * Gets JDK dynamic-proxy target object
     *
     * @param proxy {@link Object}
     * @return {@link Object}
     * @throws Exception
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
        ReflectionUtils.makeAccessible(field);
        AopProxy aopProxy = (AopProxy) field.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        ReflectionUtils.makeAccessible(advised);

        return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
    }
}
