/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import java.math.BigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.AbstractMinValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.NumberComparatorHelper;

public class MinValidatorForCharSequence
extends AbstractMinValidator<CharSequence> {
    @Override
    protected int compare(CharSequence number) {
        try {
            return NumberComparatorHelper.compare(new BigDecimal(number.toString()), this.minValue);
        }
        catch (NumberFormatException nfe) {
            return -1;
        }
    }
}

