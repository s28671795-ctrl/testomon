/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valuecontext;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.valuecontext.BeanValueContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;

public final class ValueContexts {
    private ValueContexts() {
    }

    public static <T, V> ValueContext<T, V> getLocalExecutionContextForExecutable(ExecutableParameterNameProvider parameterNameProvider, T value, Validatable validatable, PathImpl propertyPath) {
        return new ValueContext(parameterNameProvider, value, validatable, propertyPath);
    }

    public static <T, V> BeanValueContext<T, V> getLocalExecutionContextForBean(ExecutableParameterNameProvider parameterNameProvider, T value, BeanMetaData<?> currentBeanMetaData, PathImpl propertyPath) {
        return new BeanValueContext(parameterNameProvider, value, currentBeanMetaData, propertyPath);
    }

    public static <T, V> BeanValueContext<T, V> getLocalExecutionContextForValueValidation(ExecutableParameterNameProvider parameterNameProvider, BeanMetaData<?> currentBeanMetaData, PathImpl propertyPath) {
        return new BeanValueContext(parameterNameProvider, null, currentBeanMetaData, propertyPath);
    }
}

