/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.systemlogs;

import org.jetbrains.annotations.Nullable;

public enum SystemLogLevel {
    ERROR,
    WARNING,
    INFORMING;


    public String toString() {
        return this.name().toLowerCase();
    }

    @Nullable
    public static SystemLogLevel parse(String name) {
        try {
            return SystemLogLevel.valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}

