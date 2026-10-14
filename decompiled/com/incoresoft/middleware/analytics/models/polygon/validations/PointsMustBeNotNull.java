/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.polygon.validations;

import com.incoresoft.middleware.analytics.models.polygon.validations.PointsValidatable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface PointsMustBeNotNull {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<PointsMustBeNotNull, PointsValidatable> {
        @Override
        public boolean isValid(PointsValidatable value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            boolean isValid = Arrays.stream(value.getPoints()).allMatch(Objects::nonNull);
            if (!isValid) {
                this.updateContext(context);
            }
            return isValid;
        }

        private void updateContext(ConstraintValidatorContext context) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("points").addConstraintViolation();
        }
    }
}

