/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import com.github.zafarkhaja.semver.Version;
import org.pf4j.VersionManager;
import org.pf4j.util.StringUtils;

public class DefaultVersionManager
implements VersionManager {
    @Override
    public boolean checkVersionConstraint(String version, String constraint) {
        return StringUtils.isNullOrEmpty(constraint) || "*".equals(constraint) || Version.valueOf(version).satisfies(constraint);
    }

    @Override
    public int compareVersions(String v1, String v2) {
        return Version.valueOf(v1).compareTo(Version.valueOf(v2));
    }
}

