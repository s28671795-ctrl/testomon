/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.DataMigrationException;

public class DataMigrationValidationException
extends DataMigrationException {
    public DataMigrationValidationException(String message) {
        super(message);
    }

    public DataMigrationValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

