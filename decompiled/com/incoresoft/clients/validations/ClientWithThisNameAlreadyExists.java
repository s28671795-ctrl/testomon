/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.clients.validations;

import com.google.inject.Inject;
import com.incoresoft.clients.Client;
import com.incoresoft.clients.ClientsManager;
import com.incoresoft.dto.clients.CreateUpdateClientDTO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.jetbrains.annotations.NotNull;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ClientWithThisNameAlreadyExists {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<?>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<ClientWithThisNameAlreadyExists, CreateUpdateClientDTO> {
        private final ClientsManager clientsManager;

        @Inject
        public Validator(@NotNull ClientsManager clientsManager) {
            this.clientsManager = Objects.requireNonNull(clientsManager);
        }

        @Override
        public boolean isValid(CreateUpdateClientDTO request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            Optional<Client> check2 = this.clientsManager.getClients().stream().filter(it -> it.getName().equals(request.getName())).filter(it -> !Objects.equals(it.getId(), request.getId())).findAny();
            if (check2.isEmpty()) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("name").addConstraintViolation();
            return false;
        }
    }
}

