/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.version;

import com.github.zafarkhaja.semver.Version;

public class VersionUtils {
    public static boolean areCompatible(Version version1, Version version2) {
        return version1.getMajorVersion() == version2.getMajorVersion() && version1.getMinorVersion() == version2.getMinorVersion();
    }
}

