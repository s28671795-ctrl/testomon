/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff;

public class StringDiff {
    private final String baseVersion;
    private final String targetVersion;

    public StringDiff(String baseVersion, String targetVersion) {
        this.baseVersion = baseVersion;
        this.targetVersion = targetVersion;
    }

    public String getReferenceVersion() {
        return this.baseVersion;
    }

    public String getTargetVersion() {
        return this.targetVersion;
    }

    public boolean areEqual() {
        if (this.baseVersion == null) {
            return this.targetVersion == null;
        }
        return this.baseVersion.equals(this.targetVersion);
    }
}

