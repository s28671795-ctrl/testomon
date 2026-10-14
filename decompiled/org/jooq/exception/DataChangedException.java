/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.DataAccessException;

public class DataChangedException
extends DataAccessException {
    public DataChangedException(String message) {
        super(message);
    }

    public DataChangedException(String message, Throwable cause) {
        super(message, cause);
    }
}

