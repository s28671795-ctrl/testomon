/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.user;

import java.util.Arrays;

public enum UserFirstDayOfWeek {
    SUNDAY("sunday"),
    MONDAY("monday");

    private final String format;

    private UserFirstDayOfWeek(String format) {
        this.format = format;
    }

    public static UserFirstDayOfWeek findByFormat(String format) {
        return Arrays.stream(UserFirstDayOfWeek.values()).filter(it -> it.getFormat().equalsIgnoreCase(format)).findFirst().orElse(MONDAY);
    }

    public String toString() {
        return this.format;
    }

    public String getFormat() {
        return this.format;
    }
}

