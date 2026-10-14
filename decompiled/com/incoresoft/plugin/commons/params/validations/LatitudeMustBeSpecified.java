/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params.validations;

import com.google.inject.Inject;
import com.incoresoft.plugin.commons.params.AnalyticsDetectionParams;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface LatitudeMustBeSpecified {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<LatitudeMustBeSpecified, AnalyticsDetectionParams> {
        @Override
        public boolean isValid(AnalyticsDetectionParams value) {
            Objects.requireNonNull(value);
            if (value.getRadius() != null) {
                return value.getLatitude() != null;
            }
            return true;
        }

        @Override
        public String getValidatingFieldName() {
            return "latitude";
        }

        @Inject
        public Validator() {
        }
    }
}

