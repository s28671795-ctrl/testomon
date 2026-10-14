/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util.function;

@FunctionalInterface
public interface ThrowingRunnable<E extends Exception> {
    public void run() throws E;
}

