/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.hv;

import java.text.Normalizer;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.Normalized;

public class NormalizedValidator
implements ConstraintValidator<Normalized, CharSequence> {
    private Normalizer.Form form;

    @Override
    public void initialize(Normalized parameters) {
        this.form = parameters.form();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        return Normalizer.isNormalized(value, this.form);
    }
}

