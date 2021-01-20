package com.photowey.expirable.cache.boot.parser;

/**
 * {@code ExpressionParser}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public interface IExpressionParser extends Parser {

    String parseExpression(String candidate, String[] keys, Object[] values);
}
