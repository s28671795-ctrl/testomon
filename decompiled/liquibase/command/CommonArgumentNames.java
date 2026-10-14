/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

public enum CommonArgumentNames {
    USERNAME("username"),
    PASSWORD("password"),
    URL("url"),
    REFERENCE_URL("referenceUrl"),
    CHANGELOG_FILE("changelogFile");

    private final String argumentName;

    private CommonArgumentNames(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return this.argumentName;
    }
}

