/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Negative;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NumberSignHelper;

public class NegativeValidatorForCharSequence
implements ConstraintValidator<Negative, CharSequence> {
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            return NumberSignHelper.signum(new BigDecimal(value.toString())) < 0;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }
}

