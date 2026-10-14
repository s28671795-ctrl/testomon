/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.validation;

import com.google.inject.Injector;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import org.jetbrains.annotations.NotNull;

public class GuiceValidatorFactory
implements ConstraintValidatorFactory {
    private final Injector injector;

    public GuiceValidatorFactory(@NotNull Injector injector) {
        this.injector = Objects.requireNonNull(injector);
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        return (T)((ConstraintValidator)this.injector.getInstance(key));
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
        if (instance instanceof AutoCloseable) {
            try {
                ((AutoCloseable)((Object)instance)).close();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

