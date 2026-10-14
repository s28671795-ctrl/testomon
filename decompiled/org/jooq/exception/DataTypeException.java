/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.DataAccessException;

public class DataTypeException
extends DataAccessException {
    public DataTypeException(String message) {
        super(message);
    }

    public DataTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}

