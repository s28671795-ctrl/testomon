/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.DataAccessException;

public class DataMigrationException
extends DataAccessException {
    public DataMigrationException(String message) {
        super(message);
    }

    public DataMigrationException(String message, Throwable cause) {
        super(message, cause);
    }
}

