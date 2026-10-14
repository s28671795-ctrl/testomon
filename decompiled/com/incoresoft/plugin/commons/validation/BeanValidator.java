/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.validation;

import com.incoresoft.middleware.validation.ValidationViolation;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public interface BeanValidator {
    public <T> T validateObjectOrThrow(@NotNull T var1, Class<?> ... var2);

    public <T> T validateObjectOrThrow(@NotNull T var1, Function<List<ValidationViolation>, ? extends Exception> var2, Class<?> ... var3);

    @NotNull
    public <T> List<ValidationViolation> validateObject(@NotNull T var1, Class<?> ... var2);
}

