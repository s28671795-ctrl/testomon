/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;

public class NotBlankValidator
implements ConstraintValidator<NotBlank, CharSequence> {
    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if (charSequence == null) {
            return false;
        }
        return charSequence.toString().trim().length() > 0;
    }
}

