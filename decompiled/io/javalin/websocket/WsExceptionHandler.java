/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.websocket.WsContext;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface WsExceptionHandler<T extends Exception> {
    public void handle(@NotNull T var1, @NotNull WsContext var2);
}

