/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.validations;

import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import com.incoresoft.vezha.dto.streams.ListStreamRenameDTO;
import com.incoresoft.vezha.dto.streams.StreamRenameDTO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface StreamNamesMustBeUnique {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<StreamNamesMustBeUnique, ListStreamRenameDTO> {
        @Override
        public boolean isValid(ListStreamRenameDTO value) {
            long distinctNames = value.streams().stream().map(StreamRenameDTO::newName).distinct().count();
            return distinctNames == (long)value.streams().size();
        }

        @Override
        public String getValidatingFieldName() {
            return "streams";
        }
    }
}

