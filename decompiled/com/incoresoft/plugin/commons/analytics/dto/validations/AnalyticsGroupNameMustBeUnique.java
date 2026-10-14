/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.analytics.dto.AnalyticsGroupDTO;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
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
public @interface AnalyticsGroupNameMustBeUnique {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<AnalyticsGroupNameMustBeUnique, AnalyticsGroupDTO> {
        private final AnalyticsGroupsManager manager;
        private final UsersManager usersManager;

        @Override
        public boolean isValid(AnalyticsGroupDTO dto) {
            Objects.requireNonNull(dto);
            User user = (User)this.usersManager.get(dto.getUserId());
            if (dto.getId() == null) {
                return this.manager.getAll(dto.getPluginName(), user).stream().noneMatch(group -> group.getName().equals(dto.getName()));
            }
            return this.manager.getAll(dto.getPluginName(), user).stream().noneMatch(group -> group.getName().equals(dto.getName()) && group.getId() != dto.getId().intValue());
        }

        @Override
        public String getValidatingFieldName() {
            return "name";
        }

        @Inject
        public Validator(AnalyticsGroupsManager manager, UsersManager usersManager) {
            this.manager = manager;
            this.usersManager = usersManager;
        }
    }
}

