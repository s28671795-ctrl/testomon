/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.LiquibaseException;

public class SetupException
extends LiquibaseException {
    private static final long serialVersionUID = 1L;

    public SetupException(String message, Throwable cause) {
        super(message, cause);
    }

    public SetupException(String message) {
        super(message);
    }

    public SetupException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}

