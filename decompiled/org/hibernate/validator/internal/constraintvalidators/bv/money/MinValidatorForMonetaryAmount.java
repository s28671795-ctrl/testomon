/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.money.MonetaryAmount
 */
package org.hibernate.validator.internal.constraintvalidators.bv.money;

import java.math.BigDecimal;
import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;

public class MinValidatorForMonetaryAmount
implements ConstraintValidator<Min, MonetaryAmount> {
    private BigDecimal minValue;

    @Override
    public void initialize(Min minValue) {
        this.minValue = BigDecimal.valueOf(minValue.value());
    }

    @Override
    public boolean isValid(MonetaryAmount value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return ((BigDecimal)value.getNumber().numberValueExact(BigDecimal.class)).compareTo(this.minValue) != -1;
    }
}

