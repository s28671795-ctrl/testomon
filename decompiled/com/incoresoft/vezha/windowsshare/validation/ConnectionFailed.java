/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.windowsshare.validation;

import com.incoresoft.plugin.commons.windowsshare.WindowsShareClient;
import com.incoresoft.vezha.dto.windowsshare.WindowsSharedFolderConfigDTO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ConnectionFailed {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<ConnectionFailed, WindowsSharedFolderConfigDTO> {
        @Override
        public boolean isValid(WindowsSharedFolderConfigDTO request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            try {
                new WindowsShareClient(request.toModel()).getDirectories("");
                return true;
            }
            catch (Exception e) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("").addPropertyNode("host").addConstraintViolation();
                return false;
            }
        }
    }
}

