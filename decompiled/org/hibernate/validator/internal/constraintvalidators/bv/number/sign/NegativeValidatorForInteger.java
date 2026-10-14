/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Negative;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NumberSignHelper;

public class NegativeValidatorForInteger
implements ConstraintValidator<Negative, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return NumberSignHelper.signum(value) < 0;
    }
}

