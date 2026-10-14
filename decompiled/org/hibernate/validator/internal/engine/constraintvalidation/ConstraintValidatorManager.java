/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;

public interface ConstraintValidatorManager {
    public <A extends Annotation> ConstraintValidator<A, ?> getInitializedValidator(Type var1, ConstraintDescriptorImpl<A> var2, ConstraintValidatorFactory var3, HibernateConstraintValidatorInitializationContext var4);

    public void clear();

    public ConstraintValidatorFactory getDefaultConstraintValidatorFactory();

    public HibernateConstraintValidatorInitializationContext getDefaultConstraintValidatorInitializationContext();

    public boolean isPredefinedScope();
}

