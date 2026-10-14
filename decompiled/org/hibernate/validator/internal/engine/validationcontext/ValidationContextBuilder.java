/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.validationcontext;

import java.lang.reflect.Executable;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.TraversableResolver;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.validationcontext.BaseBeanValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.BeanValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ExecutableValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ParameterExecutableValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.PropertyValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ReturnValueExecutableValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ValidatorScopedContext;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;

public class ValidationContextBuilder {
    private final ConstraintValidatorManager constraintValidatorManager;
    private final ConstraintValidatorFactory constraintValidatorFactory;
    private final TraversableResolver traversableResolver;
    private final HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext;
    private final ValidatorScopedContext validatorScopedContext;

    public ValidationContextBuilder(ConstraintValidatorManager constraintValidatorManager, ConstraintValidatorFactory constraintValidatorFactory, ValidatorScopedContext validatorScopedContext, TraversableResolver traversableResolver, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext) {
        this.constraintValidatorManager = constraintValidatorManager;
        this.constraintValidatorFactory = constraintValidatorFactory;
        this.traversableResolver = traversableResolver;
        this.constraintValidatorInitializationContext = constraintValidatorInitializationContext;
        this.validatorScopedContext = validatorScopedContext;
    }

    public <T> BaseBeanValidationContext<T> forValidate(Class<T> rootBeanClass, BeanMetaData<T> rootBeanMetaData, T rootBean) {
        return new BeanValidationContext<T>(this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, rootBean, rootBeanClass, rootBeanMetaData);
    }

    public <T> BaseBeanValidationContext<T> forValidateProperty(Class<T> rootBeanClass, BeanMetaData<T> rootBeanMetaData, T rootBean, PathImpl propertyPath) {
        return new PropertyValidationContext<T>(this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, rootBean, rootBeanClass, rootBeanMetaData, propertyPath.getLeafNode().getName());
    }

    public <T> BaseBeanValidationContext<T> forValidateValue(Class<T> rootBeanClass, BeanMetaData<T> rootBeanMetaData, PathImpl propertyPath) {
        return new PropertyValidationContext<Object>(this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, null, rootBeanClass, rootBeanMetaData, propertyPath.getLeafNode().getName());
    }

    public <T> ExecutableValidationContext<T> forValidateParameters(Class<T> rootBeanClass, BeanMetaData<T> rootBeanMetaData, T rootBean, Executable executable, Object[] executableParameters) {
        return new ParameterExecutableValidationContext<T>(this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, rootBean, rootBeanClass, rootBeanMetaData, executable, rootBeanMetaData.getMetaDataFor(executable), executableParameters);
    }

    public <T> ExecutableValidationContext<T> forValidateReturnValue(Class<T> rootBeanClass, BeanMetaData<T> rootBeanMetaData, T rootBean, Executable executable, Object executableReturnValue) {
        return new ReturnValueExecutableValidationContext<T>(this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, rootBean, rootBeanClass, rootBeanMetaData, executable, rootBeanMetaData.getMetaDataFor(executable), executableReturnValue);
    }
}

