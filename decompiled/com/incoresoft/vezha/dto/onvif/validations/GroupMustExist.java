/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.onvif.validations;

import com.google.inject.Inject;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import com.incoresoft.vezha.dto.onvif.OnvifObjectDTO;
import com.incoresoft.vezha.models.streams.groups.StreamGroupManager;
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
public @interface GroupMustExist {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<GroupMustExist, OnvifObjectDTO> {
        private final StreamGroupManager streamGroupManager;

        @Inject
        public Validator(@NotNull StreamGroupManager streamGroupManager) {
            this.streamGroupManager = Objects.requireNonNull(streamGroupManager);
        }

        @Override
        public boolean isValid(OnvifObjectDTO request) {
            Objects.requireNonNull(request);
            if (request.parentId() == null) {
                return true;
            }
            return this.streamGroupManager.exists(request.parentId());
        }

        @Override
        public String getValidatingFieldName() {
            return "parentId";
        }
    }
}

