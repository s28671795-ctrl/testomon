/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.alpr.validations;

import com.incoresoft.middleware.analytics.module.alpr.LprAdvancedSettings;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface CountriesMustBeValid {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<CountriesMustBeValid, LprAdvancedSettings> {
        private static final Set<String> COUNTRY_CODES = Arrays.stream(Locale.getISOCountries()).collect(Collectors.toSet());
        private static final Set<String> ADDITIONAL_COUNTRY_CODES = Set.of("XK", "TP", "AN");

        @Override
        public boolean isValid(LprAdvancedSettings value, ConstraintValidatorContext context) {
            boolean isValid = this.isValid(value);
            if (!isValid) {
                this.updateContext(context);
            }
            return isValid;
        }

        private boolean isValid(LprAdvancedSettings value) {
            for (String countryCode : value.getCountries()) {
                if (COUNTRY_CODES.contains(countryCode) || ADDITIONAL_COUNTRY_CODES.contains(countryCode)) continue;
                return false;
            }
            return true;
        }

        private void updateContext(ConstraintValidatorContext context) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("countries").addConstraintViolation();
        }
    }
}

