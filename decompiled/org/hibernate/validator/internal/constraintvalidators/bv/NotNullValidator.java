/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

public class NotNullValidator
implements ConstraintValidator<NotNull, Object> {
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        return object != null;
    }
}

