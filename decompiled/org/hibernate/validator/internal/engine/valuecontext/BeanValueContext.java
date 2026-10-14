/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valuecontext;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;

public class BeanValueContext<T, V>
extends ValueContext<T, V> {
    private final BeanMetaData<T> currentBeanMetaData;

    BeanValueContext(ExecutableParameterNameProvider parameterNameProvider, T currentBean, BeanMetaData<T> currentBeanMetaData, PathImpl propertyPath) {
        super(parameterNameProvider, currentBean, currentBeanMetaData, propertyPath);
        this.currentBeanMetaData = currentBeanMetaData;
    }

    public final BeanMetaData<T> getCurrentBeanMetaData() {
        return this.currentBeanMetaData;
    }
}

