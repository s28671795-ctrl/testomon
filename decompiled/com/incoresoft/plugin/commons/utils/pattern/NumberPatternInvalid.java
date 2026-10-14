/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils.pattern;

import com.google.common.base.CaseFormat;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface NumberPatternInvalid {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<NumberPatternInvalid, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return Validator.isValid(value);
        }

        public void ensureValid(String value, String fieldName) {
            boolean isValid = Validator.isValid(value);
            if (!isValid) {
                String violationType = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, NumberPatternInvalid.class.getSimpleName());
                throw new ValidationException(violationType, fieldName, new Object[0]);
            }
        }

        private static boolean isValid(String value) {
            if (value == null) {
                return true;
            }
            if (value.length() == 1 && value.charAt(0) == '%') {
                return false;
            }
            if (value.length() == 2 && value.charAt(0) == '%' && value.charAt(1) == '%') {
                return false;
            }
            if (value.length() > 2) {
                for (int i = 1; i < value.length() - 1; ++i) {
                    if (value.charAt(i) != '%') continue;
                    return false;
                }
            }
            if (value.contains(String.valueOf('%'))) {
                boolean containsSingleSymbolWildcards = value.contains(String.valueOf('$')) || value.contains(String.valueOf('@')) || value.contains(String.valueOf('_'));
                return !containsSingleSymbolWildcards;
            }
            return true;
        }
    }
}

