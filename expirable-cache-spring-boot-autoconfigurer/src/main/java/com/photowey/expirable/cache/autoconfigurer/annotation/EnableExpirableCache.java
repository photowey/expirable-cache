package com.photowey.expirable.cache.autoconfigurer.annotation;

import com.photowey.expirable.cache.autoconfigurer.selector.ExpirableCacheAutoConfigurerImportSelector;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnableExpirableCache}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
@Inherited
@Documented
@AutoConfigurationPackage
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {ExpirableCacheAutoConfigurerImportSelector.class})
public @interface EnableExpirableCache {
}
