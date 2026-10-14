/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.role.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.vezha.dto.roles.RoleDTO;
import com.incoresoft.vezha.models.role.MissedPermissionsValidationError;
import com.incoresoft.vezha.models.role.PermissionUtils;
import com.incoresoft.vezha.plugins.VezhaExtensionsManager;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.jetbrains.annotations.NotNull;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface RoleDoesNotContainRequiredPermissions {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<RoleDoesNotContainRequiredPermissions, RoleDTO> {
        private final VezhaExtensionsManager extensionsManager;

        @Inject
        public Validator(@NotNull VezhaExtensionsManager extensionsManager) {
            this.extensionsManager = Objects.requireNonNull(extensionsManager);
        }

        @Override
        public boolean isValid(RoleDTO request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            Set<Permission> permissions = this.extensionsManager.getPermissions().stream().filter(it -> request.getPermissions().contains(it.getId())).collect(Collectors.toUnmodifiableSet());
            Optional<MissedPermissionsValidationError> validationResult = PermissionUtils.validate(permissions);
            if (validationResult.isEmpty()) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("permissions").addConstraintViolation();
            return false;
        }
    }
}

