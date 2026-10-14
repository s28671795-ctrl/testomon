/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.jetbrains.annotations.NotNull;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface StreamUrlMustBeUnique {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<StreamUrlMustBeUnique, Validatable> {
        private final StreamsManager streamManager;

        @Inject
        public Validator(@NotNull StreamsManager streamManager) {
            this.streamManager = Objects.requireNonNull(streamManager);
        }

        @Override
        public boolean isValid(Validatable request) {
            URI requestUri;
            Objects.requireNonNull(request);
            if (request.getParams().getType() == StreamType.file || request.getParams().getType() == StreamType.embedded) {
                return true;
            }
            try {
                requestUri = request.getURI();
            }
            catch (Exception e) {
                return true;
            }
            boolean urlIsNotUnique = this.streamManager.getAll().stream().filter(it -> it.getClientId() == request.getClientId()).filter(it -> request.getId() == null || it.getId() != request.getId().intValue()).anyMatch(it -> requestUri.equals(it.getUri()));
            return !urlIsNotUnique;
        }

        @Override
        public String getValidatingFieldName() {
            return "fullPath";
        }
    }

    public static interface Validatable {
        public Integer getId();

        public StreamParams getParams();

        public URI getURI();

        public int getClientId();
    }
}

