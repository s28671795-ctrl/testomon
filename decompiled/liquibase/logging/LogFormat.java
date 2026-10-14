/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging;

public enum LogFormat {
    TEXT(false),
    JSON(true),
    JSON_PRETTY(true);

    private final boolean useScopeLoggerInMaven;

    private LogFormat(boolean useScopeLoggerInMaven) {
        this.useScopeLoggerInMaven = useScopeLoggerInMaven;
    }

    public boolean isUseScopeLoggerInMaven() {
        return this.useScopeLoggerInMaven;
    }
}

