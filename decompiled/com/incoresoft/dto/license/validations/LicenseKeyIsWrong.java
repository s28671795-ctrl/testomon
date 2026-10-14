/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.license.validations;

import com.incoresoft.dto.license.ActivateWithKeyDTO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface LicenseKeyIsWrong {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<LicenseKeyIsWrong, ActivateWithKeyDTO> {
        private static final Pattern LICENSE_KEY_PATTERN = Pattern.compile("^[A-Z0-9]{5}-[A-Z0-9]{5}-[A-Z0-9]{5}-[A-Z0-9]{5}-[A-Z0-9]{5}$");

        @Override
        public boolean isValid(ActivateWithKeyDTO request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            if (request.getKey() == null || request.getKey().isBlank()) {
                return true;
            }
            boolean keyIsValid = LICENSE_KEY_PATTERN.matcher(request.getKey()).matches();
            if (keyIsValid) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("key").addConstraintViolation();
            return false;
        }
    }
}

