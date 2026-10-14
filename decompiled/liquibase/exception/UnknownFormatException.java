/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.LiquibaseException;

public class UnknownFormatException
extends LiquibaseException {
    private static final long serialVersionUID = 4451113070331972484L;

    public UnknownFormatException() {
    }

    public UnknownFormatException(String message) {
        super(message);
    }

    public UnknownFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownFormatException(Throwable cause) {
        super(cause);
    }
}

