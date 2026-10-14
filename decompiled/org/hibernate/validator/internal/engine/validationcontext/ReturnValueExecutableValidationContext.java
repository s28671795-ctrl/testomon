/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.validationcontext;

import java.lang.reflect.Executable;
import java.util.Optional;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.hibernate.validator.internal.engine.validationcontext.AbstractValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ExecutableValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ValidatorScopedContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;

public class ReturnValueExecutableValidationContext<T>
extends AbstractValidationContext<T>
implements ExecutableValidationContext<T> {
    private final Executable executable;
    private final Object executableReturnValue;
    private final Optional<ExecutableMetaData> executableMetaData;

    ReturnValueExecutableValidationContext(ConstraintValidatorManager constraintValidatorManager, ConstraintValidatorFactory constraintValidatorFactory, ValidatorScopedContext validatorScopedContext, TraversableResolver traversableResolver, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext, T rootBean, Class<T> rootBeanClass, BeanMetaData<T> rootBeanMetaData, Executable executable, Optional<ExecutableMetaData> executableMetaData, Object executableReturnValue) {
        super(constraintValidatorManager, constraintValidatorFactory, validatorScopedContext, traversableResolver, constraintValidatorInitializationContext, rootBean, rootBeanClass, rootBeanMetaData, ReturnValueExecutableValidationContext.buildDisableAlreadyValidatedBeanTracking(executableMetaData));
        this.executable = executable;
        this.executableMetaData = executableMetaData;
        this.executableReturnValue = executableReturnValue;
    }

    @Override
    public Executable getExecutable() {
        return this.executable;
    }

    @Override
    public Optional<ExecutableMetaData> getExecutableMetaData() {
        return this.executableMetaData;
    }

    private static boolean buildDisableAlreadyValidatedBeanTracking(Optional<ExecutableMetaData> executableMetaData) {
        if (!executableMetaData.isPresent()) {
            return false;
        }
        return !executableMetaData.get().getReturnValueMetaData().hasCascadables();
    }

    @Override
    protected ConstraintViolation<T> createConstraintViolation(String messageTemplate, String interpolatedMessage, Path propertyPath, ConstraintDescriptor<?> constraintDescriptor, ValueContext<?, ?> valueContext, ConstraintViolationCreationContext constraintViolationCreationContext) {
        return ConstraintViolationImpl.forReturnValueValidation(messageTemplate, constraintViolationCreationContext.getMessageParameters(), constraintViolationCreationContext.getExpressionVariables(), interpolatedMessage, this.getRootBeanClass(), this.getRootBean(), valueContext.getCurrentBean(), valueContext.getCurrentValidatedValue(), propertyPath, constraintDescriptor, this.executableReturnValue, constraintViolationCreationContext.getDynamicPayload());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append('{');
        sb.append("rootBeanClass=").append(this.getRootBeanClass());
        sb.append(", executable=").append(this.executable);
        sb.append(", executableReturnValue=").append(this.executableReturnValue);
        sb.append(", executableMetaData=").append(this.executableMetaData);
        sb.append('}');
        return sb.toString();
    }
}

