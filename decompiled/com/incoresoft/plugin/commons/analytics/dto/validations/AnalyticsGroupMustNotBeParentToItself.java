/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
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
public @interface AnalyticsGroupMustNotBeParentToItself {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<AnalyticsGroupMustNotBeParentToItself, AnalyticsGroupDTO> {
        private final AnalyticsGroupsManager manager;

        @Override
        public boolean isValid(AnalyticsGroupDTO request) {
            Objects.requireNonNull(request);
            if (request.getId() == null) {
                return true;
            }
            return !this.manager.isGroupParentToItself(new AnalyticsGroup(request.getId(), request.getName(), request.getPluginName(), request.getParentId(), 0));
        }

        @Override
        public String getValidatingFieldName() {
            return "parentId";
        }

        @Inject
        public Validator(AnalyticsGroupsManager manager) {
            this.manager = manager;
        }
    }
}

