/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.trafficlights.validators;

import com.incoresoft.middleware.analytics.module.trafficlights.Point;
import com.incoresoft.middleware.analytics.module.trafficlights.TrafficLightColor;
import com.incoresoft.middleware.analytics.module.trafficlights.TrafficLightsAnalyticsModule;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface GreenLightSettingsAreRequired {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<GreenLightSettingsAreRequired, TrafficLightsAnalyticsModule> {
        @Override
        public boolean isValid(TrafficLightsAnalyticsModule value, ConstraintValidatorContext context) {
            Objects.requireNonNull(value);
            List<Point> points = value.getPoints();
            if (points.stream().noneMatch(p -> p.getColor() == TrafficLightColor.green)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("").addPropertyNode("points").addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}

