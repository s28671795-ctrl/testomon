/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.plugin.commons.analytics.dto.validations.ParentIdValidatable;
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
public @interface AnalyticsParentMustExist {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<AnalyticsParentMustExist, ParentIdValidatable> {
        private final AnalyticsGroupsManager analyticsGroupsManager;

        @Override
        public boolean isValid(ParentIdValidatable value) {
            Objects.requireNonNull(value);
            return this.analyticsGroupsManager.exists(value.getParentId());
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

