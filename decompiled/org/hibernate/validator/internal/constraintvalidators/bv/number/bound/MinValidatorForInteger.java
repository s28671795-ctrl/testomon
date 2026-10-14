/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.AbstractMinValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.NumberComparatorHelper;

public class MinValidatorForInteger
extends AbstractMinValidator<Integer> {
    @Override
    protected int compare(Integer number) {
        return NumberComparatorHelper.compare(number.longValue(), this.minValue);
    }
}

