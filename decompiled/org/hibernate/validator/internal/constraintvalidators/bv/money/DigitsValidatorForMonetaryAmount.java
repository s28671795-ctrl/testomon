/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.money.MonetaryAmount
 */
package org.hibernate.validator.internal.constraintvalidators.bv.money;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Digits;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class DigitsValidatorForMonetaryAmount
implements ConstraintValidator<Digits, MonetaryAmount> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private int maxIntegerLength;
    private int maxFractionLength;

    @Override
    public void initialize(Digits constraintAnnotation) {
        this.maxIntegerLength = constraintAnnotation.integer();
        this.maxFractionLength = constraintAnnotation.fraction();
        this.validateParameters();
    }

    @Override
    public boolean isValid(MonetaryAmount monetaryAmount, ConstraintValidatorContext constraintValidatorContext) {
        if (monetaryAmount == null) {
            return true;
        }
        BigDecimal bigNum = (BigDecimal)monetaryAmount.getNumber().numberValueExact(BigDecimal.class);
        int integerPartLength = bigNum.precision() - bigNum.scale();
        int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();
        return this.maxIntegerLength >= integerPartLength && this.maxFractionLength >= fractionPartLength;
    }

    private void validateParameters() {
        if (this.maxIntegerLength < 0) {
            throw LOG.getInvalidLengthForIntegerPartException();
        }
        if (this.maxFractionLength < 0) {
            throw LOG.getInvalidLengthForFractionPartException();
        }
    }
}

