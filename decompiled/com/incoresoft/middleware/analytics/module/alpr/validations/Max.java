/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.alpr.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Max {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public long value();

    public static class Validator
    implements ConstraintValidator<Max, Integer> {
        private long maxValue;

        @Override
        public void initialize(Max constraintAnnotation) {
            this.maxValue = constraintAnnotation.value();
        }

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            Objects.requireNonNull(value);
            if (value == 0) {
                return true;
            }
            return (long)value.intValue() <= this.maxValue;
        }
    }
}

