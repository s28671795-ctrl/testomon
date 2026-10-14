/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.motion.validators;

import com.incoresoft.middleware.analytics.models.motion.MotionRuleType;
import com.incoresoft.middleware.analytics.module.motion.MotionRule;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.Set;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface NonLineCrossingObjectHasAtLeastThreePoints {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<NonLineCrossingObjectHasAtLeastThreePoints, MotionRule> {
        @Override
        public boolean isValid(MotionRule rule, ConstraintValidatorContext context) {
            Objects.requireNonNull(rule);
            Set<MotionRuleType> excludedTypes = Set.of(MotionRuleType.line_crossing_object, MotionRuleType.camera_sabotage);
            if (!excludedTypes.contains((Object)rule.getRule()) && rule.getPoints().length < 3) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("").addPropertyNode("points").addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}

