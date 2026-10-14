/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.AbstractMaxValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.NumberComparatorHelper;

public class MaxValidatorForInteger
extends AbstractMaxValidator<Integer> {
    @Override
    protected int compare(Integer number) {
        return NumberComparatorHelper.compare(number.longValue(), this.maxValue);
    }
}

