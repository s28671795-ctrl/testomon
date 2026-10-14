/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import java.math.BigInteger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NumberSignHelper;

public class PositiveOrZeroValidatorForBigInteger
implements ConstraintValidator<PositiveOrZero, BigInteger> {
    @Override
    public boolean isValid(BigInteger value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return NumberSignHelper.signum(value) >= 0;
    }
}

