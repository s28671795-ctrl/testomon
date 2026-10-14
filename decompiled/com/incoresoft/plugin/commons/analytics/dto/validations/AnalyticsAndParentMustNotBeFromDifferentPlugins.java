/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.plugin.commons.analytics.dto.PluginAnalyticsWithGroupDTO;
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
public @interface AnalyticsAndParentMustNotBeFromDifferentPlugins {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<AnalyticsAndParentMustNotBeFromDifferentPlugins, PluginAnalyticsWithGroupDTO> {
        private final AnalyticsManager analyticsManager;
        private final AnalyticsGroupsManager analyticsGroupsManager;

        @Override
        public boolean isValid(PluginAnalyticsWithGroupDTO request) {
            Objects.requireNonNull(request);
            if (request.getParentId() == 0) {
                return true;
            }
            Optional<AnalyticsGroup> analyticsGroup = this.analyticsGroupsManager.get(request.getParentId());
            if (analyticsGroup.isEmpty()) {
                return true;
            }
            if (request.getId() == null) {
                return Objects.equals(request.getModule().getAnalyticPluginName(), analyticsGroup.get().getPluginName());
            }
            return Objects.equals(((Analytics)this.analyticsManager.get(request.getId())).getPluginName(), analyticsGroup.get().getPluginName());
        }

        @Override
        public String getValidatingFieldName() {
            return "parentId";
        }

        @Inject
        public Validator(AnalyticsManager analyticsManager, AnalyticsGroupsManager analyticsGroupsManager) {
            this.analyticsManager = analyticsManager;
            this.analyticsGroupsManager = analyticsGroupsManager;
        }
    }
}

