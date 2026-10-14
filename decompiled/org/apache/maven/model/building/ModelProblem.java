/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

public interface ModelProblem {
    public String getSource();

    public int getLineNumber();

    public int getColumnNumber();

    public String getModelId();

    public Exception getException();

    public String getMessage();

    public Severity getSeverity();

    public Version getVersion();

    public static enum Version {
        BASE,
        V20,
        V30,
        V31;

    }

    public static enum Severity {
        FATAL,
        ERROR,
        WARNING;

    }
}

