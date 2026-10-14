/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.version.models;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.version.exceptions.VersionInvalidException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinorVersion {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)$");
    private final int majorNumber;
    private final int minorNumber;
    private static final String SEPARATOR = ".";

    public MinorVersion(Version version) {
        this(version.getMajorVersion(), version.getMinorVersion());
    }

    public String toString() {
        return this.majorNumber + SEPARATOR + this.minorNumber;
    }

    public static MinorVersion valueOf(String versionValue) {
        try {
            Matcher matcher = PATTERN.matcher(versionValue);
            if (matcher.matches()) {
                int majorNumber = Integer.parseInt(matcher.group(1));
                int minorNumber = Integer.parseInt(matcher.group(2));
                return new MinorVersion(majorNumber, minorNumber);
            }
            throw new IllegalArgumentException("Invalid version format: " + versionValue);
        }
        catch (Exception e) {
            throw new VersionInvalidException(e);
        }
    }

    public int getMajorNumber() {
        return this.majorNumber;
    }

    public int getMinorNumber() {
        return this.minorNumber;
    }

    public MinorVersion(int majorNumber, int minorNumber) {
        this.majorNumber = majorNumber;
        this.minorNumber = minorNumber;
    }
}

