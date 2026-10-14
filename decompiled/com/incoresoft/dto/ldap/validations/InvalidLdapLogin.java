/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.ldap.validations;

import com.incoresoft.plugin.commons.validation.SpecifyViolationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
@SpecifyViolationType(value="LOGIN_CONTAINS_INVALID_CHARACTERS")
public @interface InvalidLdapLogin {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<InvalidLdapLogin, String> {
        private static final Set<String> FORBIDDEN_SEQUENCES = Set.of("=", ",");

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtils.isBlank(value)) {
                return true;
            }
            return FORBIDDEN_SEQUENCES.stream().noneMatch(value::contains);
        }
    }
}

