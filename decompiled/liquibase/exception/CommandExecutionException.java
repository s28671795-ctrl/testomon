/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.LiquibaseException;

public class CommandExecutionException
extends LiquibaseException {
    private static final long serialVersionUID = -2115326810859901171L;

    public CommandExecutionException(Throwable cause) {
        super(cause);
    }

    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}

