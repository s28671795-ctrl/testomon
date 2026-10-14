/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class AbstractDecimalMinValidator<T>
implements ConstraintValidator<DecimalMin, T> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    protected BigDecimal minValue;
    private boolean inclusive;

    @Override
    public void initialize(DecimalMin minValue) {
        try {
            this.minValue = new BigDecimal(minValue.value());
        }
        catch (NumberFormatException nfe) {
            throw LOG.getInvalidBigDecimalFormatException(minValue.value(), nfe);
        }
        this.inclusive = minValue.inclusive();
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        int comparisonResult = this.compare(value);
        return this.inclusive ? comparisonResult >= 0 : comparisonResult > 0;
    }

    protected abstract int compare(T var1);
}

