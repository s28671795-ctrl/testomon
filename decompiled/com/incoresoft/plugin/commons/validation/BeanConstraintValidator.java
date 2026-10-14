/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.validation;

import java.lang.annotation.Annotation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class BeanConstraintValidator<A extends Annotation, T>
implements ConstraintValidator<A, T> {
    @Override
    public final boolean isValid(T value, ConstraintValidatorContext context) {
        boolean isValid = this.isValid(value);
        if (!isValid) {
            this.updateContext(context);
        }
        return isValid;
    }

    public abstract boolean isValid(T var1);

    public abstract String getValidatingFieldName();

    private void updateContext(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("").addPropertyNode(this.getValidatingFieldName()).addConstraintViolation();
    }
}

