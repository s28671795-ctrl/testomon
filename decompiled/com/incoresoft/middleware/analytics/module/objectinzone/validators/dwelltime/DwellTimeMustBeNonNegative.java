/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.objectinzone.validators.dwelltime;

import com.incoresoft.middleware.analytics.models.objectinzone.Trigger;
import com.incoresoft.middleware.analytics.module.objectinzone.ObjectInZoneTimePeriod;
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
public @interface DwellTimeMustBeNonNegative {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<DwellTimeMustBeNonNegative, ObjectInZoneTimePeriod> {
        @Override
        public boolean isValid(ObjectInZoneTimePeriod value, ConstraintValidatorContext context) {
            Objects.requireNonNull(value);
            if (value.getTrigger() == Trigger.COUNTER || value.getDwellTime() == null || value.getDwellTime() >= 0) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("dwellTime").addConstraintViolation();
            return false;
        }
    }
}

