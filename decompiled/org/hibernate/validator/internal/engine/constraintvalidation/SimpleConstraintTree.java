/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.Collection;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.validationcontext.ValidationContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

class SimpleConstraintTree<B extends Annotation>
extends ConstraintTree<B> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    public SimpleConstraintTree(ConstraintValidatorManager constraintValidatorManager, ConstraintDescriptorImpl<B> descriptor, Type validatedValueType) {
        super(constraintValidatorManager, descriptor, validatedValueType);
    }

    @Override
    protected void validateConstraints(ValidationContext<?> validationContext, ValueContext<?, ?> valueContext, Collection<ConstraintValidatorContextImpl> violatedConstraintValidatorContexts) {
        if (LOG.isTraceEnabled()) {
            LOG.tracef("Validating value %s against constraint defined by %s.", valueContext.getCurrentValidatedValue(), (Object)this.descriptor);
        }
        ConstraintValidator validator = this.getInitializedConstraintValidator(validationContext, valueContext);
        ConstraintValidatorContextImpl constraintValidatorContext = validationContext.createConstraintValidatorContextFor(this.descriptor, valueContext.getPropertyPath());
        if (this.validateSingleConstraint(valueContext, constraintValidatorContext, validator).isPresent()) {
            violatedConstraintValidatorContexts.add(constraintValidatorContext);
        }
    }
}

