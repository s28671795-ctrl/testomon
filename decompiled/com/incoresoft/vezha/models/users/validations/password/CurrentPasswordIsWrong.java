/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users.validations.password;

import com.google.inject.Inject;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import com.incoresoft.vezha.dto.users.ChangePasswordDTO;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface CurrentPasswordIsWrong {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<CurrentPasswordIsWrong, ChangePasswordDTO> {
        private final UsersManagerImpl usersManager;

        @Inject
        public Validator(@NotNull UsersManagerImpl usersManager) {
            this.usersManager = Objects.requireNonNull(usersManager);
        }

        @Override
        public boolean isValid(ChangePasswordDTO value) {
            Objects.requireNonNull(value);
            UserImpl user = (UserImpl)this.usersManager.get(value.getId());
            return user.checkPassword(value.currentPassword());
        }

        @Override
        public String getValidatingFieldName() {
            return "currentPassword";
        }
    }
}

