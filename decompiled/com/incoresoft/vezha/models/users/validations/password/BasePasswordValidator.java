/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users.validations.password;

import com.incoresoft.vezha.models.users.validations.password.PasswordValidatable;
import java.lang.annotation.Annotation;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public abstract class BasePasswordValidator<T extends Annotation>
implements ConstraintValidator<T, PasswordValidatable> {
    @Override
    public boolean isValid(PasswordValidatable request, ConstraintValidatorContext context) {
        Objects.requireNonNull(request);
        boolean isPasswordOK = true;
        if (request.getId() == null || StringUtils.isNotBlank(request.getPassword()) || StringUtils.isNotBlank(request.getConfirmationPassword())) {
            isPasswordOK = this.check(request);
        }
        if (isPasswordOK) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("").addPropertyNode("password").addConstraintViolation();
        return false;
    }

    protected abstract boolean check(PasswordValidatable var1);
}

