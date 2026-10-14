/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.hv;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.NotBlank;

public class NotBlankValidator
implements ConstraintValidator<NotBlank, CharSequence> {
    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if (charSequence == null) {
            return true;
        }
        return charSequence.toString().trim().length() > 0;
    }
}

