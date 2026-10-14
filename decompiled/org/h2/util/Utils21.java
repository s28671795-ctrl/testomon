/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

public final class Utils21 {
    public static Thread newVirtualThread(Runnable runnable2) {
        Thread thread2 = new Thread(runnable2);
        thread2.setDaemon(true);
        return thread2;
    }

    private Utils21() {
    }
}

