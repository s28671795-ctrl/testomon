/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.role.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.vezha.dto.roles.RoleDTO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.jetbrains.annotations.NotNull;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface RoleWithNameAlreadyExists {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<RoleWithNameAlreadyExists, RoleDTO> {
        private final RolesManager roleManager;

        @Inject
        public Validator(@NotNull RolesManager roleManager) {
            this.roleManager = Objects.requireNonNull(roleManager);
        }

        @Override
        public boolean isValid(RoleDTO request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            Optional<Role> check2 = this.roleManager.getAll().stream().filter(it -> it.getClientId() == request.getClientId()).filter(it -> it.getName().equals(request.getName())).findAny();
            if (check2.isEmpty() || check2.get().getId() == request.getId()) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("name").addConstraintViolation();
            return false;
        }
    }
}

