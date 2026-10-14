/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.user;

import java.util.Arrays;

public enum UserTemperatureUnit {
    CELSIUS("celsius"),
    FAHRENHEIT("fahrenheit");

    private final String format;

    private UserTemperatureUnit(String format) {
        this.format = format;
    }

    public static UserTemperatureUnit findByFormat(String format) {
        return Arrays.stream(UserTemperatureUnit.values()).filter(it -> it.getFormat().equalsIgnoreCase(format)).findFirst().orElse(CELSIUS);
    }

    public String toString() {
        return this.format;
    }

    public String getFormat() {
        return this.format;
    }
}

