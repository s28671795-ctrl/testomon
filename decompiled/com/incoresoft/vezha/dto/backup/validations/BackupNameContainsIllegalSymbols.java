/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.backup.validations;

import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import com.incoresoft.vezha.backup.ws.message.create.client.CreateBackupMessage;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface BackupNameContainsIllegalSymbols {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<BackupNameContainsIllegalSymbols, CreateBackupMessage> {
        private final String[] illegalSymbols = new String[]{"#", "%", "&", "{", "}", "\"", "<", ">", "*", "?", "\\", " ", "\t", "$", "!", "'", "/", "@", ":", "+", "`", "|", "="};

        @Override
        public boolean isValid(CreateBackupMessage dto) {
            if (dto.getName() == null) {
                return true;
            }
            for (String symbol : this.illegalSymbols) {
                if (!dto.getName().contains(symbol)) continue;
                return false;
            }
            return true;
        }

        @Override
        public String getValidatingFieldName() {
            return "name";
        }
    }
}

