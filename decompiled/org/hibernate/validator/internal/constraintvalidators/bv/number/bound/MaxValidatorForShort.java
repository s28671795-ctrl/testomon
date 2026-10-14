/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.AbstractMaxValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.NumberComparatorHelper;

public class MaxValidatorForShort
extends AbstractMaxValidator<Short> {
    @Override
    protected int compare(Short number) {
        return NumberComparatorHelper.compare(number.longValue(), this.maxValue);
    }
}

