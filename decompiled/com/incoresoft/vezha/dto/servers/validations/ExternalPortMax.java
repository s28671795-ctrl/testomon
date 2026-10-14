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

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@SpecifyViolationType(value="MAX")
public @interface ExternalPortMax {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public long value();

    public static class Validator
    extends BeanConstraintValidator<ExternalPortMax, Validatable> {
        private long max;

        @Override
        public void initialize(ExternalPortMax constraintAnnotation) {
            this.max = constraintAnnotation.value();
        }

        @Override
        public boolean isValid(Validatable value) {
            return !value.isExternalAddressEnabled() || value.getExternalPort() == null || (long)value.getExternalPort().intValue() <= this.max;
        }

        @Override
        public String getValidatingFieldName() {
            return "externalPort";
        }
    }

    public static interface Validatable {
        public boolean isExternalAddressEnabled();

        public Integer getExternalPort();
    }
}

