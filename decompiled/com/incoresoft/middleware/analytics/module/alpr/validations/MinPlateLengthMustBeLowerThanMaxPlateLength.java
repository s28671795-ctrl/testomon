/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.alpr.validations;

import com.incoresoft.middleware.analytics.module.alpr.LprAdvancedSettings;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface MinPlateLengthMustBeLowerThanMaxPlateLength {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<MinPlateLengthMustBeLowerThanMaxPlateLength, LprAdvancedSettings> {
        @Override
        public boolean isValid(LprAdvancedSettings value, ConstraintValidatorContext context) {
            boolean isValid;
            boolean bl = isValid = value.getMinPlateLength() <= value.getMaxPlateLength();
            if (!isValid) {
                this.addConstraintViolation(context, "minPlateLength");
                this.addConstraintViolation(context, "maxPlateLength");
            }
            return isValid;
        }

        private void addConstraintViolation(ConstraintValidatorContext context, String propertyName) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode(propertyName).addConstraintViolation();
        }
    }
}

