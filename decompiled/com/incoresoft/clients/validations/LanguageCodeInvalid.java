/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.clients.validations;

import com.incoresoft.dto.clients.CreateUpdateClientDTO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface LanguageCodeInvalid {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<?>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<LanguageCodeInvalid, CreateUpdateClientDTO> {
        @Override
        public boolean isValid(CreateUpdateClientDTO value, ConstraintValidatorContext context) {
            Objects.requireNonNull(value);
            if (value.getCountry() == null) {
                return true;
            }
            Set<String> countryCodes = Set.of(Locale.getISOCountries());
            if (countryCodes.contains(value.getCountry())) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("country").addConstraintViolation();
            return false;
        }
    }
}

