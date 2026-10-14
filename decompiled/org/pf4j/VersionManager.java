/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

public interface VersionManager {
    public boolean checkVersionConstraint(String var1, String var2);

    public int compareVersions(String var1, String var2);
}

