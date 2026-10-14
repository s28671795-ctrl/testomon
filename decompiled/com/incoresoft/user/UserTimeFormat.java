/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.user;

import java.util.Arrays;

public enum UserTimeFormat {
    HH_MM_SS("HH:mm:ss"),
    H_MM_SS_A("h:mm:ss a");

    private final String format;

    private UserTimeFormat(String format) {
        this.format = format;
    }

    public static UserTimeFormat findByFormat(String format) {
        return Arrays.stream(UserTimeFormat.values()).filter(it -> it.getFormat().equalsIgnoreCase(format)).findFirst().orElse(HH_MM_SS);
    }

    public String toString() {
        return this.format;
    }

    public String getFormat() {
        return this.format;
    }
}

