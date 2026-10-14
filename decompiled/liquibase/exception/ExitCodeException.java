/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

public interface ExitCodeException {
    default public Integer getExitCode() {
        return null;
    }
}

