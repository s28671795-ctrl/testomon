/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.settings;

public interface OnSettingsChangeListener {
    public static final String ALL_SETTINGS = "ALL_SETTINGS";

    default public String key() {
        return ALL_SETTINGS;
    }

    public void onSettingsChange(String var1, Object var2);
}

