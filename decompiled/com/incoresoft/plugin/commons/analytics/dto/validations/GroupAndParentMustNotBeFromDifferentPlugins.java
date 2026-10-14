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
import java.util.Optional;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface GroupAndParentMustNotBeFromDifferentPlugins {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<GroupAndParentMustNotBeFromDifferentPlugins, AnalyticsGroupDTO> {
        private final AnalyticsGroupsManager analyticsGroupsManager;

        @Override
        public boolean isValid(AnalyticsGroupDTO request) {
            Objects.requireNonNull(request);
            if (request.getParentId() == 0) {
                return true;
            }
            Optional<AnalyticsGroup> parentGroup = this.analyticsGroupsManager.get(request.getParentId());
            if (parentGroup.isEmpty()) {
                return true;
            }
            if (request.getId() == null) {
                return Objects.equals(request.getPluginName(), parentGroup.get().getPluginName());
            }
            return Objects.equals(this.analyticsGroupsManager.get(request.getId()).orElseThrow().getPluginName(), parentGroup.get().getPluginName());
        }

        @Override
        public String getValidatingFieldName() {
            return "parentId";
        }

        @Inject
        public Validator(AnalyticsGroupsManager analyticsGroupsManager) {
            this.analyticsGroupsManager = analyticsGroupsManager;
        }
    }
}

