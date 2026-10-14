/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.LiquibaseException;

public class DuplicateStatementIdentifierException
extends LiquibaseException {
    private static final long serialVersionUID = 1L;

    public DuplicateStatementIdentifierException(String message) {
        super(message);
    }
}

