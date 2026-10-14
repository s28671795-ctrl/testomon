/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NumberSignHelper;

public class PositiveOrZeroValidatorForDouble
implements ConstraintValidator<PositiveOrZero, Double> {
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return NumberSignHelper.signum(value, InfinityNumberComparatorHelper.LESS_THAN) >= 0;
    }
}

