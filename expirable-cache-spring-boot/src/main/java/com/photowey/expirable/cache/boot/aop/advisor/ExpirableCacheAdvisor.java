package com.photowey.expirable.cache.boot.aop.advisor;

import com.photowey.expirable.cache.boot.aop.pointcut.ExpirableCachePointcut;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * {@code ExpirableCacheAdvisor}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public class ExpirableCacheAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private Pointcut pointcut;

    public ExpirableCacheAdvisor() {
        this.pointcut = new ExpirableCachePointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
