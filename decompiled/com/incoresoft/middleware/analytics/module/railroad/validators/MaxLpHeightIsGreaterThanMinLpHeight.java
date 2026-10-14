/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.railroad.validators;

import com.incoresoft.middleware.analytics.module.railroad.RailroadsAnalyticsModule;
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
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface MaxLpHeightIsGreaterThanMinLpHeight {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<MaxLpHeightIsGreaterThanMinLpHeight, RailroadsAnalyticsModule> {
        @Override
        public boolean isValid(RailroadsAnalyticsModule value, ConstraintValidatorContext context) {
            Objects.requireNonNull(value);
            if (value.getMaxLpHeight() > value.getMinLpHeight()) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("maxLpHeight").addConstraintViolation();
            return false;
        }
    }
}

