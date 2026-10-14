/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.validation;

import com.incoresoft.validation.Nameable;
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
public @interface NameCannotConsistOnlyOfSpecialSymbols {
    public String message() default "NAME_CANNOT_CONSIST_ONLY_OF_WHITESPACES_AND_SPECIAL_SYMBOLS";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<NameCannotConsistOnlyOfSpecialSymbols, Nameable> {
        private static final Pattern pattern = Pattern.compile("^[\\s\\p{P}\\p{C}$^]+$");

        @Override
        public boolean isValid(Nameable item, ConstraintValidatorContext context) {
            Objects.requireNonNull(item);
            String name = item.getName();
            if (name == null) {
                return true;
            }
            if (pattern.matcher(name.trim()).matches()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("").addPropertyNode("name").addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}

