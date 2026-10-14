/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users.validations.email;

import com.incoresoft.vezha.dto.users.UserDTO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface NotBlank {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<NotBlank, UserDTO> {
        @Override
        public boolean isValid(UserDTO request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            if (request.getId() != null || StringUtils.isNotBlank(request.getEmail())) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("email").addConstraintViolation();
            return false;
        }
    }
}

