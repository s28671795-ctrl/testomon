/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.user;

import java.util.Arrays;

public enum UserDistanceUnit {
    MILE("m"),
    KILOMETER("km");

    private final String format;

    private UserDistanceUnit(String format) {
        this.format = format;
    }

    public static UserDistanceUnit findByFormat(String format) {
        return Arrays.stream(UserDistanceUnit.values()).filter(it -> it.getFormat().equalsIgnoreCase(format)).findFirst().orElse(KILOMETER);
    }

    public String toString() {
        return this.format;
    }

    public String getFormat() {
        return this.format;
    }
}

