/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.notempty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;

public class NotEmptyValidatorForArraysOfByte
implements ConstraintValidator<NotEmpty, byte[]> {
    @Override
    public boolean isValid(byte[] array, ConstraintValidatorContext constraintValidatorContext) {
        if (array == null) {
            return false;
        }
        return array.length > 0;
    }
}

