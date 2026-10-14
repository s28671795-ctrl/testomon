/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.eventmanager.validations;

import com.incoresoft.vezha.dto.eventmanager.EventSchemeDTO;
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
public @interface NodesNotEmpty {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<NodesNotEmpty, EventSchemeDTO> {
        @Override
        public boolean isValid(EventSchemeDTO request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            if (request.getNodes() == null || !request.getNodes().isEmpty()) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("nodes").addConstraintViolation();
            return false;
        }
    }
}

