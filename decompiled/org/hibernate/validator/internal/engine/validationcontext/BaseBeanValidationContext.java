/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.validationcontext;

import javax.validation.Path;
import javax.validation.TraversableResolver;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.validationcontext.ValidationContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;

public interface BaseBeanValidationContext<T>
extends ValidationContext<T> {
    public T getRootBean();

    public Class<T> getRootBeanClass();

    public BeanMetaData<T> getRootBeanMetaData();

    public TraversableResolver getTraversableResolver();

    public boolean isBeanAlreadyValidated(Object var1, Class<?> var2, PathImpl var3);

    public void markCurrentBeanAsProcessed(ValueContext<?, ?> var1);

    public boolean hasMetaConstraintBeenProcessed(Object var1, Path var2, MetaConstraint<?> var3);

    public void markConstraintProcessed(Object var1, Path var2, MetaConstraint<?> var3);

    default public boolean appliesTo(MetaConstraint<?> metaConstraint) {
        return true;
    }
}

