/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.AbstractDecimalMaxValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalNumberComparatorHelper;

public class DecimalMaxValidatorForInteger
extends AbstractDecimalMaxValidator<Integer> {
    @Override
    protected int compare(Integer number) {
        return DecimalNumberComparatorHelper.compare(number.longValue(), this.maxValue);
    }
}

