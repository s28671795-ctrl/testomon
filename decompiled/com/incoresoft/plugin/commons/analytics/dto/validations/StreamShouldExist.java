/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.plugin.commons.analytics.dto.validations.StreamIdValidatable;
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
public @interface StreamShouldExist {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<StreamShouldExist, StreamIdValidatable> {
        private final StreamsManager streamsManager;

        @Inject
        public Validator(StreamsManager streamsManager) {
            this.streamsManager = streamsManager;
        }

        @Override
        public boolean isValid(StreamIdValidatable value) {
            if (value.getStreamId() == null) {
                return true;
            }
            return this.streamsManager.get(value.getStreamId()) != null;
        }

        @Override
        public String getValidatingFieldName() {
            return "streamId";
        }
    }
}

