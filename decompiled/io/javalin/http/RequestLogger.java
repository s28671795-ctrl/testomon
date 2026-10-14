/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RequestLogger {
    public void handle(@NotNull Context var1, @NotNull Float var2) throws Exception;
}

