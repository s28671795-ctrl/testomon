/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.LiquibaseException;

public class RollbackFailedException
extends LiquibaseException {
    private static final long serialVersionUID = 1L;

    public RollbackFailedException() {
    }

    public RollbackFailedException(String message) {
        super(message);
    }

    public RollbackFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RollbackFailedException(Throwable cause) {
        super(cause);
    }
}

