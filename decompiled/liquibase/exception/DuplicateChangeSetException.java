/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.LiquibaseException;

public class DuplicateChangeSetException
extends LiquibaseException {
    private static final long serialVersionUID = 1L;

    public DuplicateChangeSetException(String message) {
        super(message);
    }
}

