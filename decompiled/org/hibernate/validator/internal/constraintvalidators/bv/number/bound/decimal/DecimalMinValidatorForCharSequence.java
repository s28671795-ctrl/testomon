/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import java.math.BigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.AbstractDecimalMinValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalNumberComparatorHelper;

public class DecimalMinValidatorForCharSequence
extends AbstractDecimalMinValidator<CharSequence> {
    @Override
    protected int compare(CharSequence number) {
        try {
            return DecimalNumberComparatorHelper.compare(new BigDecimal(number.toString()), this.minValue);
        }
        catch (NumberFormatException nfe) {
            return -1;
        }
    }
}

