/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.settings.validations;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceManager;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import com.incoresoft.version.VersionUtils;
import com.incoresoft.vezha.dto.settings.VmsIntegrationRequestDTO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface VmsVersionNotCompatible {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<VmsVersionNotCompatible, VmsIntegrationRequestDTO> {
        private final CoreServiceManager coreServiceManager;

        @Override
        public boolean isValid(VmsIntegrationRequestDTO dto) {
            return VersionUtils.areCompatible(this.coreServiceManager.getServiceUpdateState().getCurrentVersion(), dto.getVmsVersion());
        }

        @Override
        public String getValidatingFieldName() {
            return "vmsVersion";
        }

        @Inject
        public Validator(CoreServiceManager coreServiceManager) {
            this.coreServiceManager = coreServiceManager;
        }
    }
}

