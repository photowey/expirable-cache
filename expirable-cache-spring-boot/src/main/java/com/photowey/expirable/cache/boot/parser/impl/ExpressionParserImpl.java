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

package com.photowey.expirable.cache.boot.parser.impl;

import com.photowey.expirable.cache.boot.parser.IExpressionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * {@code ExpressionParserImpl}
 *
 * @author photowey
 * @date 2021/01/21
 * @since 1.0.0
 */
public class ExpressionParserImpl implements IExpressionParser {

    private static final Logger log = LoggerFactory.getLogger(ExpressionParserImpl.class);

    private final ExpressionParser expressionParser;

    public ExpressionParserImpl(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Nullable
    @Override
    public String parseExpression(String candidate, String[] keys, Object[] values) {
        this.verifyNull(keys, values);
        if (ObjectUtils.isEmpty(values)) {
            return null;
        }
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < values.length; i++) {
            context.setVariable(keys[i], values[i]);
        }
        String parseResult = this.parse(candidate, context);
        if (log.isInfoEnabled()) {
            log.info("parse the cache-key:[{}], result is:[{}]", candidate, parseResult);
        }

        return parseResult;
    }

    @Nullable
    @Override
    public String parse(String candidate, EvaluationContext context) {
        Expression expression = this.expressionParser.parseExpression(candidate);
        return expression.getValue(context, String.class);
    }

    private void verifyNull(String[] keys, Object[] values) {
        if (null == keys) {
            throw new NullPointerException("handle parseExpression(),the keys is null.");
        }
        if (null == values) {
            throw new NullPointerException("handle parseExpression(),the values is null.");
        }
    }
}
