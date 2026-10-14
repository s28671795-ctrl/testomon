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
import javax.validation.Constraint;
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
    extends BasePasswordValidator<NotBlank> {
        @Override
        protected boolean check(PasswordValidatable userDTO) {
            return StringUtils.isNotBlank(userDTO.getPassword());
        }
    }
}

