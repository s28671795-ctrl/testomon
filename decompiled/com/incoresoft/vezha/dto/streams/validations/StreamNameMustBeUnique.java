/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.jetbrains.annotations.NotNull;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface StreamNameMustBeUnique {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<StreamNameMustBeUnique, Validatable> {
        private final StreamsManager streamManager;

        @Inject
        public Validator(@NotNull StreamsManager streamManager) {
            this.streamManager = Objects.requireNonNull(streamManager);
        }

        @Override
        public boolean isValid(Validatable request) {
            Objects.requireNonNull(request);
            boolean doesStreamWithNameExist = this.streamManager.getAll().stream().filter(it -> request.getId() == null || it.getId() != request.getId().intValue()).filter(it -> it.getClientId() == request.getClientId()).anyMatch(it -> Objects.equals(request.getName(), it.getName()));
            return !doesStreamWithNameExist;
        }

        @Override
        public String getValidatingFieldName() {
            return "name";
        }
    }

    public static interface Validatable {
        public Integer getId();

        public String getName();

        public int getClientId();
    }
}

