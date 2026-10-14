/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.AbstractConstraintValidatorManagerImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.Contracts;

public class PredefinedScopeConstraintValidatorManagerImpl
extends AbstractConstraintValidatorManagerImpl {
    public PredefinedScopeConstraintValidatorManagerImpl(ConstraintValidatorFactory defaultConstraintValidatorFactory, HibernateConstraintValidatorInitializationContext defaultConstraintValidatorInitializationContext) {
        super(defaultConstraintValidatorFactory, defaultConstraintValidatorInitializationContext);
    }

    @Override
    public <A extends Annotation> ConstraintValidator<A, ?> getInitializedValidator(Type validatedValueType, ConstraintDescriptorImpl<A> descriptor, ConstraintValidatorFactory constraintValidatorFactory, HibernateConstraintValidatorInitializationContext initializationContext) {
        Contracts.assertNotNull(validatedValueType);
        Contracts.assertNotNull(descriptor);
        Contracts.assertNotNull(constraintValidatorFactory);
        Contracts.assertNotNull(initializationContext);
        return this.createAndInitializeValidator(validatedValueType, descriptor, constraintValidatorFactory, initializationContext);
    }

    @Override
    public boolean isPredefinedScope() {
        return true;
    }

    @Override
    public void clear() {
    }
}

