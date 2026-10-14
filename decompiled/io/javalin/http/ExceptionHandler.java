/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExceptionHandler<T extends Exception> {
    public void handle(@NotNull T var1, @NotNull Context var2);
}

