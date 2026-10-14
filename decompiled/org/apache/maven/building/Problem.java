/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.building;

public interface Problem {
    public String getSource();

    public int getLineNumber();

    public int getColumnNumber();

    public String getLocation();

    public Exception getException();

    public String getMessage();

    public Severity getSeverity();

    public static enum Severity {
        FATAL,
        ERROR,
        WARNING;

    }
}

