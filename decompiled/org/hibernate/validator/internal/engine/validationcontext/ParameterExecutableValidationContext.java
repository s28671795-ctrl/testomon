/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.validationcontext;

import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.CrossParameterConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.validationcontext.AbstractValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ExecutableValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ValidatorScopedContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;

public class ParameterExecutableValidationContext<T>
extends AbstractValidationContext<T>
implements ExecutableValidationContext<T> {
    private final Executable executable;
    private final Object[] executableParameters;
    private final Optional<ExecutableMetaData> executableMetaData;

    ParameterExecutableValidationContext(ConstraintValidatorManager constraintValidatorManager, ConstraintValidatorFactory constraintValidatorFactory, ValidatorScopedContext validatorScopedContext, TraversableResolver traversableResolver, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext, T rootBean, Class<T> rootBeanClass, BeanMetaData<T> rootBeanMetaData, Executable executable, Optional<ExecutableMetaData> executableMetaData, Object[] executableParameters) {
        super(constraintValidatorManager, constraintValidatorFactory, validatorScopedContext, traversableResolver, constraintValidatorInitializationContext, rootBean, rootBeanClass, rootBeanMetaData, ParameterExecutableValidationContext.buildDisableAlreadyValidatedBeanTracking(executableMetaData));
        this.executable = executable;
        this.executableMetaData = executableMetaData;
        this.executableParameters = executableParameters;
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
        return !executableMetaData.get().getValidatableParametersMetaData().hasCascadables();
    }

    @Override
    public ConstraintValidatorContextImpl createConstraintValidatorContextFor(ConstraintDescriptorImpl<?> constraintDescriptor, PathImpl path) {
        if (ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER.equals((Object)constraintDescriptor.getConstraintType())) {
            return new CrossParameterConstraintValidatorContextImpl(this.getParameterNames(), this.validatorScopedContext.getClockProvider(), path, constraintDescriptor, this.validatorScopedContext.getConstraintValidatorPayload(), this.validatorScopedContext.getConstraintExpressionLanguageFeatureLevel(), this.validatorScopedContext.getCustomViolationExpressionLanguageFeatureLevel());
        }
        return new ConstraintValidatorContextImpl(this.validatorScopedContext.getClockProvider(), path, constraintDescriptor, this.validatorScopedContext.getConstraintValidatorPayload(), this.validatorScopedContext.getConstraintExpressionLanguageFeatureLevel(), this.validatorScopedContext.getCustomViolationExpressionLanguageFeatureLevel());
    }

    @Override
    protected ConstraintViolation<T> createConstraintViolation(String messageTemplate, String interpolatedMessage, Path propertyPath, ConstraintDescriptor<?> constraintDescriptor, ValueContext<?, ?> valueContext, ConstraintViolationCreationContext constraintViolationCreationContext) {
        return ConstraintViolationImpl.forParameterValidation(messageTemplate, constraintViolationCreationContext.getMessageParameters(), constraintViolationCreationContext.getExpressionVariables(), interpolatedMessage, this.getRootBeanClass(), this.getRootBean(), valueContext.getCurrentBean(), valueContext.getCurrentValidatedValue(), propertyPath, constraintDescriptor, this.executableParameters, constraintViolationCreationContext.getDynamicPayload());
    }

    private List<String> getParameterNames() {
        return this.validatorScopedContext.getParameterNameProvider().getParameterNames(this.executable);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append('{');
        sb.append("rootBeanClass=").append(this.getRootBeanClass());
        sb.append(", executable=").append(this.executable);
        sb.append(", executableParameters=").append(Arrays.toString(this.executableParameters));
        sb.append(", executableMetaData=").append(this.executableMetaData);
        sb.append('}');
        return sb.toString();
    }
}

