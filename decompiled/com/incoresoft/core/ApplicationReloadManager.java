/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ApplicationReloadManager {
    public void reload() {
        CompletableFuture.runAsync(() -> System.exit(-1), CompletableFuture.delayedExecutor(1L, TimeUnit.SECONDS));
    }
}

