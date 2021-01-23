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

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@code AnnotationUtils}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public final class AnnotationUtils {

    private AnnotationUtils() {
        // utils class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static <T extends Annotation> boolean hasAnnotation(Class<?>[] classes, Class<T> annotationClass) {
        if (CollectionUtils.isNotEmpty(classes)) {
            List<Class<?>> classList = Arrays.stream(classes).filter(Objects::nonNull).collect(Collectors.toList());
            for (Class clazz : classList) {
                // find in class
                T targetClassAnnotation = (T) clazz.getAnnotation(annotationClass);
                if (targetClassAnnotation != null) {
                    return true;
                }
                // find in methods
                Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
                for (Method method : methods) {
                    T targetMethodAnnotation = method.getAnnotation(annotationClass);
                    if (targetMethodAnnotation != null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
