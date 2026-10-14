/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
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
public @interface StreamWithUuidMustExist {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<StreamWithUuidMustExist, StreamRenameDTO> {
        private final StreamsManager streamManager;

        @Override
        public boolean isValid(StreamRenameDTO streamRenameDTO) {
            return this.streamManager.get(streamRenameDTO.uuid()) != null;
        }

        @Override
        public String getValidatingFieldName() {
            return "uuid";
        }

        @Inject
        public Validator(StreamsManager streamManager) {
            this.streamManager = streamManager;
        }
    }
}

