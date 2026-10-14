/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.jooq.tools.StringUtils;

final class FlywayVersion
implements Comparable<FlywayVersion> {
    public static final FlywayVersion EMPTY = new FlywayVersion(null);
    private static final Pattern SPLIT_REGEX = Pattern.compile("\\.(?=\\d)");
    private final List<BigInteger> versionParts;

    public static FlywayVersion fromVersion(String version) {
        if (StringUtils.isEmpty(version)) {
            return EMPTY;
        }
        return new FlywayVersion(version);
    }

    private FlywayVersion(String version) {
        if (!StringUtils.isEmpty(version)) {
            String normalizedVersion = version.replace('_', '.');
            this.versionParts = this.tokenize(normalizedVersion);
        } else {
            this.versionParts = new ArrayList<BigInteger>();
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FlywayVersion version1 = (FlywayVersion)o;
        return this.compareTo(version1) == 0;
    }

    public int hashCode() {
        return this.versionParts == null ? 0 : this.versionParts.hashCode();
    }

    @Override
    public int compareTo(FlywayVersion o) {
        if (o == null) {
            return 1;
        }
        if (this == EMPTY) {
            if (o == EMPTY) {
                return 0;
            }
            return Integer.MIN_VALUE;
        }
        if (o == EMPTY) {
            return Integer.MAX_VALUE;
        }
        List<BigInteger> parts1 = this.versionParts;
        List<BigInteger> parts2 = o.versionParts;
        int largestNumberOfParts = Math.max(parts1.size(), parts2.size());
        for (int i = 0; i < largestNumberOfParts; ++i) {
            int compared = this.getOrZero(parts1, i).compareTo(this.getOrZero(parts2, i));
            if (compared == 0) continue;
            return compared;
        }
        return 0;
    }

    private BigInteger getOrZero(List<BigInteger> elements, int i) {
        return i < elements.size() ? elements.get(i) : BigInteger.ZERO;
    }

    private List<BigInteger> tokenize(String versionStr) {
        ArrayList<BigInteger> parts = new ArrayList<BigInteger>();
        for (String part : SPLIT_REGEX.split(versionStr)) {
            parts.add(new BigInteger(part));
        }
        for (int i = parts.size() - 1; i > 0 && ((BigInteger)parts.get(i)).equals(BigInteger.ZERO); --i) {
            parts.remove(i);
        }
        return parts;
    }
}

