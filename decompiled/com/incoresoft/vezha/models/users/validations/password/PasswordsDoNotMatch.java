/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users.validations.password;

import com.incoresoft.vezha.models.users.validations.password.BasePasswordValidator;
import com.incoresoft.vezha.models.users.validations.password.PasswordValidatable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface PasswordsDoNotMatch {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BasePasswordValidator<PasswordsDoNotMatch> {
        @Override
        protected boolean check(PasswordValidatable userDTO) {
            return Objects.equals(userDTO.getPassword(), userDTO.getConfirmationPassword());
        }
    }
}

