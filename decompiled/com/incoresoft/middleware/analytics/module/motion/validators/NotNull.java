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
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface NotNull {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<NotNull, MotionRule> {
        @Override
        public boolean isValid(MotionRule rule, ConstraintValidatorContext context) {
            Objects.requireNonNull(rule);
            if (!(rule.getRule().equals((Object)MotionRuleType.line_crossing_object) || rule.getRule().equals((Object)MotionRuleType.intruded_object) || rule.getDwellTime() != null && rule.getDwellTime() >= 1)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("").addPropertyNode("dwellTime").addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}

