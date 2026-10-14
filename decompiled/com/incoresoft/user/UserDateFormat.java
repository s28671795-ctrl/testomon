/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.user;

import java.util.Arrays;

public enum UserDateFormat {
    DD_MM_YYYY("DD-MM-YYYY"),
    D_M_YY("D-M-YY"),
    MM_DD_YYYY("MM-DD-YYYY"),
    M_D_YY("M-D-YY"),
    YYYY_MM_DD("YYYY-MM-DD");

    private final String format;

    private UserDateFormat(String format) {
        this.format = format;
    }

    public static UserDateFormat findByFormat(String format) {
        return Arrays.stream(UserDateFormat.values()).filter(it -> it.getFormat().equalsIgnoreCase(format)).findFirst().orElse(DD_MM_YYYY);
    }

    public String toString() {
        return this.format;
    }

    public String getFormat() {
        return this.format;
    }
}

