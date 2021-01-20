package com.photowey.expirable.cache.autoconfigurer.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

/**
 * {@code ExpirableCacheAutoConfigurerImportSelector}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public class ExpirableCacheAutoConfigurerImportSelector implements ImportSelector {

    @Override
    public Predicate<String> getExclusionFilter() {
        return null;
    }

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[0];
    }
}
