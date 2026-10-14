/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

public class MissingRequiredArgumentException
extends Exception {
    private static final long serialVersionUID = -4166453748767367091L;
    private final String argumentName;

    public MissingRequiredArgumentException(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return this.argumentName;
    }
}

