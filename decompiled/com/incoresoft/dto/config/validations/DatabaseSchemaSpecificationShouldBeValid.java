/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config.validations;

import com.incoresoft.dto.config.SetupDatabaseConfigDTO;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
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
public @interface DatabaseSchemaSpecificationShouldBeValid {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<DatabaseSchemaSpecificationShouldBeValid, SetupDatabaseConfigDTO> {
        @Override
        public boolean isValid(SetupDatabaseConfigDTO settingsDTO) {
            if (settingsDTO.getType() == null) {
                return true;
            }
            if (settingsDTO.getType().isSchemaSpecifiable() && StringUtils.isEmpty(settingsDTO.getSchemaName())) {
                return false;
            }
            return settingsDTO.getType().isSchemaSpecifiable() || !StringUtils.isNotEmpty(settingsDTO.getSchemaName());
        }

        @Override
        public String getValidatingFieldName() {
            return "schemaName";
        }
    }
}

