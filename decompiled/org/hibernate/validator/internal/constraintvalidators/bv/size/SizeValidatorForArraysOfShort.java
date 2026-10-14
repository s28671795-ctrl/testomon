/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.size;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfPrimitives;

public class SizeValidatorForArraysOfShort
extends SizeValidatorForArraysOfPrimitives
implements ConstraintValidator<Size, short[]> {
    @Override
    public boolean isValid(short[] array, ConstraintValidatorContext constraintValidatorContext) {
        if (array == null) {
            return true;
        }
        return array.length >= this.min && array.length <= this.max;
    }
}

