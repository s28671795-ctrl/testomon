/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.DataAccessException;

public class IOException
extends DataAccessException {
    public IOException(String message, java.io.IOException cause) {
        super(message, cause);
    }

    @Override
    public synchronized java.io.IOException getCause() {
        return (java.io.IOException)super.getCause();
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        if (!(cause instanceof java.io.IOException)) {
            throw new IllegalArgumentException("Can only wrap java.io.IOException: " + String.valueOf(cause));
        }
        return super.initCause(cause);
    }
}

