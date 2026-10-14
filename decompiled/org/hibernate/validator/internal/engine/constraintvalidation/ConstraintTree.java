/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ValidationException;
import org.hibernate.validator.internal.engine.constraintvalidation.ComposingConstraintTree;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.SimpleConstraintTree;
import org.hibernate.validator.internal.engine.validationcontext.ValidationContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class ConstraintTree<A extends Annotation> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    protected final ConstraintDescriptorImpl<A> descriptor;
    private final Type validatedValueType;
    private volatile ConstraintValidator<A, ?> defaultInitializedConstraintValidator;

    protected ConstraintTree(ConstraintValidatorManager constraintValidatorManager, ConstraintDescriptorImpl<A> descriptor, Type validatedValueType) {
        this.descriptor = descriptor;
        this.validatedValueType = validatedValueType;
        if (constraintValidatorManager.isPredefinedScope()) {
            this.defaultInitializedConstraintValidator = constraintValidatorManager.getInitializedValidator(validatedValueType, descriptor, constraintValidatorManager.getDefaultConstraintValidatorFactory(), constraintValidatorManager.getDefaultConstraintValidatorInitializationContext());
        }
    }

    public static <U extends Annotation> ConstraintTree<U> of(ConstraintValidatorManager constraintValidatorManager, ConstraintDescriptorImpl<U> composingDescriptor, Type validatedValueType) {
        if (composingDescriptor.getComposingConstraintImpls().isEmpty()) {
            return new SimpleConstraintTree<U>(constraintValidatorManager, composingDescriptor, validatedValueType);
        }
        return new ComposingConstraintTree<U>(constraintValidatorManager, composingDescriptor, validatedValueType);
    }

    public final boolean validateConstraints(ValidationContext<?> validationContext, ValueContext<?, ?> valueContext) {
        ArrayList<ConstraintValidatorContextImpl> violatedConstraintValidatorContexts = new ArrayList<ConstraintValidatorContextImpl>(5);
        this.validateConstraints(validationContext, valueContext, violatedConstraintValidatorContexts);
        if (!violatedConstraintValidatorContexts.isEmpty()) {
            for (ConstraintValidatorContextImpl constraintValidatorContext : violatedConstraintValidatorContexts) {
                for (ConstraintViolationCreationContext constraintViolationCreationContext : constraintValidatorContext.getConstraintViolationCreationContexts()) {
                    validationContext.addConstraintFailure(valueContext, constraintViolationCreationContext, constraintValidatorContext.getConstraintDescriptor());
                }
            }
            return false;
        }
        return true;
    }

    protected abstract void validateConstraints(ValidationContext<?> var1, ValueContext<?, ?> var2, Collection<ConstraintValidatorContextImpl> var3);

    public final ConstraintDescriptorImpl<A> getDescriptor() {
        return this.descriptor;
    }

    public final Type getValidatedValueType() {
        return this.validatedValueType;
    }

    private ValidationException getExceptionForNullValidator(Type validatedValueType, String path) {
        if (this.descriptor.getConstraintType() == ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER) {
            return LOG.getValidatorForCrossParameterConstraintMustEitherValidateObjectOrObjectArrayException(this.descriptor.getAnnotationType());
        }
        String className = validatedValueType.toString();
        if (validatedValueType instanceof Class) {
            Class clazz = (Class)validatedValueType;
            className = clazz.isArray() ? clazz.getComponentType().toString() + "[]" : clazz.getName();
        }
        return LOG.getNoValidatorFoundForTypeException(this.descriptor.getAnnotationType(), className, path);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final ConstraintValidator<A, ?> getInitializedConstraintValidator(ValidationContext<?> validationContext, ValueContext<?, ?> valueContext) {
        ConstraintValidator<A, ?> validator;
        if (validationContext.getConstraintValidatorManager().isPredefinedScope()) {
            validator = this.defaultInitializedConstraintValidator;
        } else if (validationContext.getConstraintValidatorFactory() == validationContext.getConstraintValidatorManager().getDefaultConstraintValidatorFactory() && validationContext.getConstraintValidatorInitializationContext() == validationContext.getConstraintValidatorManager().getDefaultConstraintValidatorInitializationContext()) {
            validator = this.defaultInitializedConstraintValidator;
            if (validator == null) {
                ConstraintTree constraintTree = this;
                synchronized (constraintTree) {
                    validator = this.defaultInitializedConstraintValidator;
                    if (validator == null) {
                        this.defaultInitializedConstraintValidator = validator = validationContext.getConstraintValidatorManager().getInitializedValidator(this.validatedValueType, this.descriptor, validationContext.getConstraintValidatorManager().getDefaultConstraintValidatorFactory(), validationContext.getConstraintValidatorManager().getDefaultConstraintValidatorInitializationContext());
                    }
                }
            }
        } else {
            validator = validationContext.getConstraintValidatorManager().getInitializedValidator(this.validatedValueType, this.descriptor, validationContext.getConstraintValidatorFactory(), validationContext.getConstraintValidatorInitializationContext());
        }
        if (validator == null) {
            throw this.getExceptionForNullValidator(this.validatedValueType, valueContext.getPropertyPath().asString());
        }
        return validator;
    }

    protected final <V> Optional<ConstraintValidatorContextImpl> validateSingleConstraint(ValueContext<?, ?> valueContext, ConstraintValidatorContextImpl constraintValidatorContext, ConstraintValidator<A, V> validator) {
        boolean isValid;
        try {
            Object validatedValue = valueContext.getCurrentValidatedValue();
            isValid = validator.isValid(validatedValue, constraintValidatorContext);
        }
        catch (RuntimeException e) {
            if (e instanceof ConstraintDeclarationException) {
                throw e;
            }
            throw LOG.getExceptionDuringIsValidCallException(e);
        }
        if (!isValid) {
            return Optional.of(constraintValidatorContext);
        }
        return Optional.empty();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConstraintTree");
        sb.append("{ descriptor=").append(this.descriptor);
        sb.append('}');
        return sb.toString();
    }
}

