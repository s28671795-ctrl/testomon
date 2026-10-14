/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.database;

public abstract class SQLConnectionException
extends RuntimeException {
    private final Exception cause;

    public SQLConnectionException(Exception cause) {
        super(cause);
        this.cause = cause;
    }

    public SQLConnectionException() {
        this.cause = null;
    }

    public abstract String getErrorKey();

    @Override
    public Exception getCause() {
        return this.cause;
    }
}

