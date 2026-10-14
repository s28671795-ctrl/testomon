/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.DataAccessException;

public class IntegrityConstraintViolationException
extends DataAccessException {
    public IntegrityConstraintViolationException(String message) {
        super(message);
    }

    public IntegrityConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}

