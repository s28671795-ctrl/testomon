/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NegativeOrZero;
import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NumberSignHelper;

public class NegativeOrZeroValidatorForFloat
implements ConstraintValidator<NegativeOrZero, Float> {
    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return NumberSignHelper.signum(value, InfinityNumberComparatorHelper.GREATER_THAN) <= 0;
    }
}

