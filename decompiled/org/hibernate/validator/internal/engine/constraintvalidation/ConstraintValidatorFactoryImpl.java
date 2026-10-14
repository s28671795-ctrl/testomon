/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;

public class ConstraintValidatorFactoryImpl
implements ConstraintValidatorFactory {
    @Override
    public final <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        return (T)((ConstraintValidator)this.run(NewInstance.action(key, "ConstraintValidator")));
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
    }

    private <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }
}

