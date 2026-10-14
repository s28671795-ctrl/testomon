/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.AssertTrue;

public class AssertTrueValidator
implements ConstraintValidator<AssertTrue, Boolean> {
    @Override
    public boolean isValid(Boolean bool, ConstraintValidatorContext constraintValidatorContext) {
        return bool == null || bool != false;
    }
}

