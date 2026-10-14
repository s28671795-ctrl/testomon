/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers.validations;

import com.google.common.net.InetAddresses;
import com.google.common.net.InternetDomainName;
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
@SpecifyViolationType(value="EXTERNAL_ADDRESS_IS_NOT_VALID")
public @interface ExternalAddress {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<ExternalAddress, Validatable> {
        @Override
        public boolean isValid(Validatable value) {
            if (!value.isExternalAddressEnabled() || value.getExternalHost() == null) {
                return true;
            }
            String externalHost = value.getExternalHost();
            return InetAddresses.isUriInetAddress(externalHost) || InternetDomainName.isValid(externalHost);
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

