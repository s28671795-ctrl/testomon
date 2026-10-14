/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.restrictions.validation;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.jetbrains.annotations.NotNull;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface UserMustSetOwnPermissions {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<UserMustSetOwnPermissions, ItemRestrictionsDTO> {
        private final UsersManager usersManager;

        @Inject
        public Validator(@NotNull UsersManager usersManager) {
            this.usersManager = Objects.requireNonNull(usersManager);
        }

        @Override
        public boolean isValid(ItemRestrictionsDTO restrictionsDTO, ConstraintValidatorContext context) {
            Objects.requireNonNull(restrictionsDTO);
            User user = (User)this.usersManager.get(restrictionsDTO.getCreatorId());
            boolean userPermissionsSpecified = restrictionsDTO.getUserPermissions().containsKey(user.getId());
            boolean rolePermissionsSpecified = restrictionsDTO.getRolePermissions().containsKey(user.getRoleId());
            if (!userPermissionsSpecified && rolePermissionsSpecified) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("").addPropertyNode("userPermissions").addConstraintViolation();
                return false;
            }
            if (!rolePermissionsSpecified) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("").addPropertyNode("rolePermissions").addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}

