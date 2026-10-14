/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import liquibase.exception.LiquibaseException;

public class CommandResult {
    public String message;
    public boolean succeeded;

    public CommandResult() {
        this.message = "Successful";
        this.succeeded = true;
    }

    public CommandResult(String message) {
        this.message = message;
        this.succeeded = true;
    }

    public CommandResult(String message, boolean succeeded) {
        this.message = message;
        this.succeeded = succeeded;
    }

    public String print() throws LiquibaseException {
        return this.message;
    }
}

