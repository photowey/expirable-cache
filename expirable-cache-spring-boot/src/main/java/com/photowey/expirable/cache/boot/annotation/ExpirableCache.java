package com.photowey.expirable.cache.boot.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * {@code ExpirableCache}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpirableCache {

    String key() default "";

    @AliasFor("name")
    String[] value() default {};

    @AliasFor("value")
    String[] name() default {};

    int expire() default 60;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
