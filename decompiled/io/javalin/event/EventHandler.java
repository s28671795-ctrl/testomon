/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.event;

@FunctionalInterface
public interface EventHandler {
    public void handleEvent() throws Exception;
}

