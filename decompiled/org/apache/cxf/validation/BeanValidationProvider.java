/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ParameterNameProvider;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidationProviderResolver;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import javax.validation.spi.ValidationProvider;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.validation.ResponseConstraintViolationException;
import org.apache.cxf.validation.ValidationConfiguration;

public class BeanValidationProvider {
    private static final Logger LOG = LogUtils.getL7dLogger(BeanValidationProvider.class);
    private final ValidatorFactory factory;

    public BeanValidationProvider() {
        try {
            this.factory = Validation.buildDefaultValidatorFactory();
        }
        catch (ValidationException ex) {
            LOG.severe("Bean Validation provider can not be found, no validation will be performed");
            throw ex;
        }
    }

    public BeanValidationProvider(ParameterNameProvider parameterNameProvider) {
        this(new ValidationConfiguration(parameterNameProvider));
    }

    public BeanValidationProvider(ValidationConfiguration cfg) {
        try {
            Configuration<?> factoryCfg = Validation.byDefaultProvider().configure();
            BeanValidationProvider.initFactoryConfig(factoryCfg, cfg);
            this.factory = factoryCfg.buildValidatorFactory();
        }
        catch (ValidationException ex) {
            LOG.severe("Bean Validation provider can not be found, no validation will be performed");
            throw ex;
        }
    }

    public BeanValidationProvider(ValidatorFactory factory2) {
        if (factory2 == null) {
            throw new NullPointerException("Factory is null");
        }
        this.factory = factory2;
    }

    public BeanValidationProvider(ValidationProviderResolver resolver2) {
        this(resolver2, null);
    }

    public <T extends Configuration<T>, U extends ValidationProvider<T>> BeanValidationProvider(ValidationProviderResolver resolver2, Class<U> providerType) {
        this(resolver2, providerType, null);
    }

    public <T extends Configuration<T>, U extends ValidationProvider<T>> BeanValidationProvider(ValidationProviderResolver resolver2, Class<U> providerType, ValidationConfiguration cfg) {
        try {
            Configuration<Object> factoryCfg = providerType != null ? Validation.byProvider(providerType).providerResolver(resolver2).configure() : Validation.byDefaultProvider().providerResolver(resolver2).configure();
            BeanValidationProvider.initFactoryConfig(factoryCfg, cfg);
            this.factory = factoryCfg.buildValidatorFactory();
        }
        catch (ValidationException ex) {
            LOG.severe("Bean Validation provider can not be found, no validation will be performed");
            throw ex;
        }
    }

    private static void initFactoryConfig(Configuration<?> factoryCfg, ValidationConfiguration cfg) {
        if (cfg != null) {
            factoryCfg.parameterNameProvider(cfg.getParameterNameProvider());
            factoryCfg.messageInterpolator(cfg.getMessageInterpolator());
            factoryCfg.traversableResolver(cfg.getTraversableResolver());
            factoryCfg.constraintValidatorFactory(cfg.getConstraintValidatorFactory());
            for (Map.Entry<String, String> entry : cfg.getProperties().entrySet()) {
                factoryCfg.addProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    public <T> void validateParameters(T instance, Method method2, Object[] arguments) {
        ExecutableValidator methodValidator = this.getExecutableValidator();
        Set<ConstraintViolation<T>> violations = methodValidator.validateParameters(instance, method2, arguments, new Class[0]);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public <T> void validateReturnValue(T instance, Method method2, Object returnValue) {
        ExecutableValidator methodValidator = this.getExecutableValidator();
        Set<ConstraintViolation<T>> violations = methodValidator.validateReturnValue(instance, method2, returnValue, new Class[0]);
        if (!violations.isEmpty()) {
            throw new ResponseConstraintViolationException(violations);
        }
    }

    public <T> void validateReturnValue(T bean) {
        Set<ConstraintViolation<T>> violations = this.doValidateBean(bean);
        if (!violations.isEmpty()) {
            throw new ResponseConstraintViolationException(violations);
        }
    }

    public <T> void validateBean(T bean) {
        Set<ConstraintViolation<T>> violations = this.doValidateBean(bean);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private <T> Set<ConstraintViolation<T>> doValidateBean(T bean) {
        return this.factory.getValidator().validate(bean, new Class[0]);
    }

    private ExecutableValidator getExecutableValidator() {
        return this.factory.getValidator().forExecutables();
    }
}

