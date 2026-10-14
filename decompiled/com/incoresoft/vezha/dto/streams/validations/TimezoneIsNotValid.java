/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.ZoneId;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface TimezoneIsNotValid {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<TimezoneIsNotValid, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value != null) {
                try {
                    ZoneId zoneId = ZoneId.of(value);
                    return true;
                }
                catch (Exception e) {
                    return false;
                }
            }
            return true;
        }
    }
}

