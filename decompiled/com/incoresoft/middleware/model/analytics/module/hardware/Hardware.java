/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics.module.hardware;

import java.util.Arrays;

public enum Hardware {
    cpu,
    gpu;


    public static Hardware lookup(String id) {
        return Arrays.stream(Hardware.values()).filter(it -> it.name().equals(id)).findFirst().orElse(null);
    }
}

