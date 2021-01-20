package com.photowey.expirable.cache.boot.aop.pointcut;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

import java.lang.reflect.Method;

/**
 * {@code ExpirableCachePointcut}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public class ExpirableCachePointcut implements Pointcut, MethodMatcher {

    @Override
    public boolean matches(Method method, Class<?> aClass) {

        // TODO

        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> aClass, Object... objects) {
        return false;
    }

    @Override
    public boolean isRuntime() {
        return false;
    }

    @Override
    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
