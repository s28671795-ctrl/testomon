/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NumberSignHelper;

public class PositiveOrZeroValidatorForByte
implements ConstraintValidator<PositiveOrZero, Byte> {
    @Override
    public boolean isValid(Byte value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return NumberSignHelper.signum(value) >= 0;
    }
}

