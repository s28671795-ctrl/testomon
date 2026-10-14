/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.DataAccessException;

public class TemplatingException
extends DataAccessException {
    public TemplatingException(String message) {
        super(message);
    }

    public TemplatingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

