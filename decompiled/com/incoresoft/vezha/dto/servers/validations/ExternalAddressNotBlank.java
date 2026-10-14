/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers.validations;

import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import com.incoresoft.plugin.commons.validation.SpecifyViolationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@SpecifyViolationType(value="NOT_BLANK")
public @interface ExternalAddressNotBlank {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<ExternalAddressNotBlank, Validatable> {
        @Override
        public boolean isValid(Validatable value) {
            return !value.isExternalAddressEnabled() || StringUtils.isNotBlank(value.getExternalHost());
        }

        @Override
        public String getValidatingFieldName() {
            return "externalHost";
        }
    }

    public static interface Validatable {
        public boolean isExternalAddressEnabled();

        public String getExternalHost();
    }
}

