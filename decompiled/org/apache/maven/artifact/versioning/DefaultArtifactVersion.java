/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.artifact.versioning;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.ComparableVersion;

public class DefaultArtifactVersion
implements ArtifactVersion {
    private Integer majorVersion;
    private Integer minorVersion;
    private Integer incrementalVersion;
    private Integer buildNumber;
    private String qualifier;
    private ComparableVersion comparable;

    public DefaultArtifactVersion(String version) {
        this.parseVersion(version);
    }

    public int hashCode() {
        return 11 + this.comparable.hashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ArtifactVersion)) {
            return false;
        }
        return this.compareTo((ArtifactVersion)other) == 0;
    }

    @Override
    public int compareTo(ArtifactVersion otherVersion) {
        if (otherVersion instanceof DefaultArtifactVersion) {
            return this.comparable.compareTo(((DefaultArtifactVersion)otherVersion).comparable);
        }
        return this.compareTo(new DefaultArtifactVersion(otherVersion.toString()));
    }

    @Override
    public int getMajorVersion() {
        return this.majorVersion != null ? this.majorVersion : 0;
    }

    @Override
    public int getMinorVersion() {
        return this.minorVersion != null ? this.minorVersion : 0;
    }

    @Override
    public int getIncrementalVersion() {
        return this.incrementalVersion != null ? this.incrementalVersion : 0;
    }

    @Override
    public int getBuildNumber() {
        return this.buildNumber != null ? this.buildNumber : 0;
    }

    @Override
    public String getQualifier() {
        return this.qualifier;
    }

    @Override
    public final void parseVersion(String version) {
        String part1;
        this.comparable = new ComparableVersion(version);
        int index = version.indexOf(45);
        String part2 = null;
        if (index < 0) {
            part1 = version;
        } else {
            part1 = version.substring(0, index);
            part2 = version.substring(index + 1);
        }
        if (part2 != null) {
            try {
                if (part2.length() == 1 || !part2.startsWith("0")) {
                    this.buildNumber = Integer.valueOf(part2);
                } else {
                    this.qualifier = part2;
                }
            }
            catch (NumberFormatException e) {
                this.qualifier = part2;
            }
        }
        if (!part1.contains(".") && !part1.startsWith("0")) {
            try {
                this.majorVersion = Integer.valueOf(part1);
            }
            catch (NumberFormatException e) {
                this.qualifier = version;
                this.buildNumber = null;
            }
        } else {
            boolean fallback = false;
            StringTokenizer tok = new StringTokenizer(part1, ".");
            try {
                this.majorVersion = DefaultArtifactVersion.getNextIntegerToken(tok);
                if (tok.hasMoreTokens()) {
                    this.minorVersion = DefaultArtifactVersion.getNextIntegerToken(tok);
                }
                if (tok.hasMoreTokens()) {
                    this.incrementalVersion = DefaultArtifactVersion.getNextIntegerToken(tok);
                }
                if (tok.hasMoreTokens()) {
                    this.qualifier = tok.nextToken();
                    fallback = Pattern.compile("\\d+").matcher(this.qualifier).matches();
                }
                if (part1.contains("..") || part1.startsWith(".") || part1.endsWith(".")) {
                    fallback = true;
                }
            }
            catch (NumberFormatException e) {
                fallback = true;
            }
            if (fallback) {
                this.qualifier = version;
                this.majorVersion = null;
                this.minorVersion = null;
                this.incrementalVersion = null;
                this.buildNumber = null;
            }
        }
    }

    private static Integer getNextIntegerToken(StringTokenizer tok) {
        try {
            String s2 = tok.nextToken();
            if (s2.length() > 1 && s2.startsWith("0")) {
                throw new NumberFormatException("Number part has a leading 0: '" + s2 + "'");
            }
            return Integer.valueOf(s2);
        }
        catch (NoSuchElementException e) {
            throw new NumberFormatException("Number is invalid");
        }
    }

    public String toString() {
        return this.comparable.toString();
    }
}

