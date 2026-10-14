/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NumberSignHelper;

public class PositiveOrZeroValidatorForFloat
implements ConstraintValidator<PositiveOrZero, Float> {
    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return NumberSignHelper.signum(value, InfinityNumberComparatorHelper.LESS_THAN) >= 0;
    }
}

