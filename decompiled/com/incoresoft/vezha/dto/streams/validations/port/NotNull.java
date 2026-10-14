/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.validations.port;

import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface NotNull {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<NotNull, Validatable> {
        @Override
        public boolean isValid(Validatable validatable) {
            StreamType type;
            if (validatable.getParams() != null && ((type = validatable.getParams().getType()) == StreamType.file || type == StreamType.embedded)) {
                return true;
            }
            return validatable.getPort() != null;
        }

        @Override
        public String getValidatingFieldName() {
            return "port";
        }
    }

    public static interface Validatable {
        public Integer getPort();

        public StreamParams getParams();
    }
}

