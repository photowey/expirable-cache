package com.photowey.expirable.cache.boot.parser;

import org.springframework.expression.EvaluationContext;

/**
 * {@code Parser}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public interface Parser {

    String parse(String candidate, EvaluationContext context);
}
