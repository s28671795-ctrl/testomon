/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.AbstractDecimalMinValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalNumberComparatorHelper;

public class DecimalMinValidatorForShort
extends AbstractDecimalMinValidator<Short> {
    @Override
    protected int compare(Short number) {
        return DecimalNumberComparatorHelper.compare(number.longValue(), this.minValue);
    }
}

