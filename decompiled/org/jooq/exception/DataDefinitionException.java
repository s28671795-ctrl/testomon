/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.DataAccessException;

public class DataDefinitionException
extends DataAccessException {
    public DataDefinitionException(String message) {
        super(message);
    }

    public DataDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }
}

