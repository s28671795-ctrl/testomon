/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.validationcontext;

import java.util.Set;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;

public interface ValidationContext<T> {
    public boolean isFailFastModeEnabled();

    public ConstraintValidatorManager getConstraintValidatorManager();

    public HibernateConstraintValidatorInitializationContext getConstraintValidatorInitializationContext();

    public ConstraintValidatorFactory getConstraintValidatorFactory();

    public void addConstraintFailure(ValueContext<?, ?> var1, ConstraintViolationCreationContext var2, ConstraintDescriptor<?> var3);

    public Set<ConstraintViolation<T>> getFailingConstraints();

    public ConstraintValidatorContextImpl createConstraintValidatorContextFor(ConstraintDescriptorImpl<?> var1, PathImpl var2);
}

