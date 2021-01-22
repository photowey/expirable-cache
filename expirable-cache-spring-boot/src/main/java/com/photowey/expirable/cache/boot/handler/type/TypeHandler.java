package com.photowey.expirable.cache.boot.handler.type;

import java.lang.reflect.Type;

/**
 * {@code TypeHandler}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public interface TypeHandler {

    boolean supports(Type type);

    Object handleType(Object candidate, Type type);

}
