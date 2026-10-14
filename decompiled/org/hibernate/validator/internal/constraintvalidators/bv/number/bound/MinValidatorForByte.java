/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.AbstractMinValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.NumberComparatorHelper;

public class MinValidatorForByte
extends AbstractMinValidator<Byte> {
    @Override
    protected int compare(Byte number) {
        return NumberComparatorHelper.compare(number.longValue(), this.minValue);
    }
}

