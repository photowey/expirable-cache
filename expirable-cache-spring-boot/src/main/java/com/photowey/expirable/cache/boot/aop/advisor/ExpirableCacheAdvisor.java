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
