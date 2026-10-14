/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.validations.host;

import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface NotBlank {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<NotBlank, Validatable> {
        @Override
        public boolean isValid(Validatable request) {
            StreamType type;
            Objects.requireNonNull(request);
            if (request.getParams() != null && ((type = request.getParams().getType()) == StreamType.file || type == StreamType.embedded)) {
                return true;
            }
            return StringUtils.isNotBlank(request.getHost());
        }

        @Override
        public String getValidatingFieldName() {
            return "host";
        }
    }

    public static interface Validatable {
        public String getHost();

        public StreamParams getParams();
    }
}

