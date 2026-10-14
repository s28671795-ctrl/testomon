/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.windowsshare.validation;

import com.google.inject.Inject;
import com.hierynomus.smbj.common.SmbPath;
import com.incoresoft.vezha.dto.windowsshare.WindowsSharedFolderConfigDTO;
import com.incoresoft.vezha.windowsshare.WindowsShareConfigManager;
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
public @interface AlreadyExists {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    implements ConstraintValidator<AlreadyExists, WindowsSharedFolderConfigDTO> {
        private final WindowsShareConfigManager manager;

        @Inject
        public Validator(@NotNull WindowsShareConfigManager manager) {
            this.manager = Objects.requireNonNull(manager);
        }

        @Override
        public boolean isValid(WindowsSharedFolderConfigDTO request, ConstraintValidatorContext context) {
            Objects.requireNonNull(request);
            SmbPath newSharePath = new SmbPath(request.getHost(), request.getShareName());
            if (request.getId() != null && this.manager.exists(request.getId()) || this.manager.getAll().stream().noneMatch(it -> newSharePath.equals(new SmbPath(it.getHostname(), it.getShareName())))) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("").addPropertyNode("host").addConstraintViolation();
            return false;
        }
    }
}

