/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.LiquibaseException;

public class UnknownChangelogFormatException
extends LiquibaseException {
    private static final long serialVersionUID = 2124945695429454207L;

    public UnknownChangelogFormatException() {
    }

    public UnknownChangelogFormatException(String message) {
        super(message);
    }

    public UnknownChangelogFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownChangelogFormatException(Throwable cause) {
        super(cause);
    }
}

