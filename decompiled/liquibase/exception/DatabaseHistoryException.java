/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.LiquibaseException;

public class DatabaseHistoryException
extends LiquibaseException {
    private static final long serialVersionUID = 1L;

    public DatabaseHistoryException(String message) {
        super(message);
    }

    public DatabaseHistoryException(String message, Throwable cause) {
        super(message, cause);
    }
}

