/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.validations;

import com.google.inject.Inject;
import com.incoresoft.vezha.dto.streams.StreamGroupDTO;
import com.incoresoft.vezha.models.streams.groups.StreamGroupManager;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.jetbrains.annotations.NotNull;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface GroupIsParentToItself {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<GroupIsParentToItself, StreamGroupDTO> {
        private final StreamGroupManager streamGroupManager;

        @Inject
        public Validator(@NotNull StreamGroupManager streamGroupManager) {
            this.streamGroupManager = Objects.requireNonNull(streamGroupManager);
        }

        @Override
        public boolean isValid(StreamGroupDTO request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            if (this.streamGroupManager.exists(request.getId()) && this.streamGroupManager.groupIsParentToItself(request.toGroup(0))) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("").addPropertyNode("parentId").addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}

