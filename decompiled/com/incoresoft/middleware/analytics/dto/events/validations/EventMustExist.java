/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.events.validations;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.dto.events.EventDTO;
import com.incoresoft.middleware.eventmanager.Event;
import com.incoresoft.middleware.eventmanager.EventsManagerConnector;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.jetbrains.annotations.NotNull;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface EventMustExist {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<EventMustExist, EventDTO> {
        private final EventsManagerConnector connector;

        @Inject
        public Validator(@NotNull EventsManagerConnector connector) {
            this.connector = Objects.requireNonNull(connector);
        }

        @Override
        public boolean isValid(EventDTO value, ConstraintValidatorContext context) {
            Set existingEventIds = this.connector.getEvents().stream().map(Event::getId).collect(Collectors.toSet());
            if (existingEventIds.contains(value.getId())) {
                return true;
            }
            this.updateContext(context);
            return false;
        }

        private void updateContext(ConstraintValidatorContext context) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("id").addConstraintViolation();
        }
    }
}

