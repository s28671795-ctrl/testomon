/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.vezha.models.users.validations.role.RoleIdsContainer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface RolesWithThisIdsMustExist {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<RolesWithThisIdsMustExist, RoleIdsContainer> {
        private final RolesManager roleManager;

        @Override
        public boolean isValid(RoleIdsContainer request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            if (request.getRoleIds() == null || request.getRoleIds().isEmpty()) {
                return true;
            }
            boolean allExists = request.getRoleIds().stream().allMatch(id -> this.roleManager.get((Integer)id) != null);
            if (allExists) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("roleIds").addConstraintViolation();
            return false;
        }

        @Inject
        public Validator(RolesManager roleManager) {
            this.roleManager = roleManager;
        }
    }
}

