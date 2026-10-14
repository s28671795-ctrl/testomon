/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

public final class OsUtils {
    static final OSType OS;

    public static OSType getOperatingSystemType() {
        return OS;
    }

    static {
        String osName = System.getProperty("os.name", "generic").toLowerCase();
        OS = osName.contains("mac") || osName.contains("darwin") ? OSType.MacOS : (osName.contains("win") ? OSType.Windows : (osName.contains("nux") ? OSType.Linux : OSType.Other));
    }

    public static enum OSType {
        Windows,
        MacOS,
        Linux,
        Other;

    }
}

