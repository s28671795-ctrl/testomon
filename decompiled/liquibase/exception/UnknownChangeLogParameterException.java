/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.UnexpectedLiquibaseException;

public class UnknownChangeLogParameterException
extends UnexpectedLiquibaseException {
    private static final long serialVersionUID = -854481194428706115L;

    public UnknownChangeLogParameterException(String message) {
        super(message);
    }

    public UnknownChangeLogParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownChangeLogParameterException(Throwable cause) {
        super(cause);
    }
}

