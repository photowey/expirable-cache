package com.photowey.expirable.cache.boot.aop.advice;

import com.photowey.expirable.cache.boot.annotation.ExpirableCache;
import com.photowey.expirable.cache.boot.parser.IExpressionParser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * {@code ExpirableCacheAdvice}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public class ExpirableCacheAdvice implements MethodInterceptor, BeanFactoryAware {

    private final IExpressionParser expressionParser;
    private ListableBeanFactory beanFactory;

    public ExpirableCacheAdvice(IExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method target = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();
        ExpirableCache annotation = target.getAnnotation(ExpirableCache.class);
        String cacheKey = this.parseCacheKey(annotation.key(), target, arguments);
        if (!StringUtils.hasText(cacheKey)) {
            throw new IllegalArgumentException(String.format("can't parse the candidate-cache-key:[%s] from target:[%s]", annotation.key(), target.getName()));
        }
        // TODO

        return null;
    }

    private String parseCacheKey(String expression, Method target, Object[] args) {
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(target);
        return this.expressionParser.parseExpression(expression, parameterNames, args);
    }
}
