/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.utils;

public class ConcurrencyUtils {
    public static void ensureNotInterrupted() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    public static void ensureInterruptedStatusRestored(Throwable e) {
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}

