/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;

public abstract class AbstractMaxValidator<T>
implements ConstraintValidator<Max, T> {
    protected long maxValue;

    @Override
    public void initialize(Max maxValue) {
        this.maxValue = maxValue.value();
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        return this.compare(value) <= 0;
    }

    protected abstract int compare(T var1);
}

