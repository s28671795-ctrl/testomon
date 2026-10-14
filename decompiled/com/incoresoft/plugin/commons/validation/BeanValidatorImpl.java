/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.validation;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import com.incoresoft.middleware.validation.ValidationViolation;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugin.commons.validation.ValidationViolationsUtil;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.jetbrains.annotations.NotNull;

public class BeanValidatorImpl
implements BeanValidator {
    private final AtomicReference<ValidatorFactory> factoryRef = new AtomicReference<ValidatorFactory>(Validation.buildDefaultValidatorFactory());

    public void setFactory(@NotNull ConstraintValidatorFactory factory2) {
        this.factoryRef.set(Validation.byDefaultProvider().configure().constraintValidatorFactory(factory2).buildValidatorFactory());
    }

    public ValidatorFactory getFactory() {
        return this.factoryRef.get();
    }

    @Override
    public <T> T validateObjectOrThrow(@NotNull T object, Class<?> ... groups2) {
        return this.validateObjectOrThrow(object, ValidationException::new, groups2);
    }

    @Override
    public <T> T validateObjectOrThrow(@NotNull T object, Function<List<ValidationViolation>, ? extends Exception> exceptionFunction, Class<?> ... groups2) {
        List<ValidationViolation> violations = this.validateObject(object, groups2);
        if (!violations.isEmpty()) {
            throw exceptionFunction.apply(violations);
        }
        return object;
    }

    @Override
    @NotNull
    public <T> List<ValidationViolation> validateObject(@NotNull T object, Class<?> ... groups2) {
        Set<ConstraintViolation<T>> violations = this.factoryRef.get().getValidator().validate(object, groups2);
        LinkedList<ValidationViolation> validationViolations = new LinkedList<ValidationViolation>();
        for (ConstraintViolation<T> violation : violations) {
            validationViolations.add(ValidationViolationsUtil.obtainValidationViolation(object, violation));
        }
        return validationViolations;
    }

    @Inject
    public BeanValidatorImpl() {
    }
}

