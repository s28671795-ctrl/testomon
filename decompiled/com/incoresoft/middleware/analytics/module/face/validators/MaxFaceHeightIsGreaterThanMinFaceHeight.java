/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.face.validators;

import com.incoresoft.middleware.analytics.module.face.validators.FaceDimensions;
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
public @interface MaxFaceHeightIsGreaterThanMinFaceHeight {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<MaxFaceHeightIsGreaterThanMinFaceHeight, FaceDimensions> {
        @Override
        public boolean isValid(FaceDimensions value, ConstraintValidatorContext context) {
            Objects.requireNonNull(value);
            if (value.getMaxFaceHeight() == null || value.getMinFaceHeight() == null) {
                return true;
            }
            if (value.getMaxFaceHeight() == 0 || value.getMaxFaceHeight() > value.getMinFaceHeight()) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("maxFaceHeight").addConstraintViolation();
            return false;
        }
    }
}

