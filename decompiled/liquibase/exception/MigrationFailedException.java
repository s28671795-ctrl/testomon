/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.changelog.ChangeSet;
import liquibase.exception.LiquibaseException;

public class MigrationFailedException
extends LiquibaseException {
    private static final long serialVersionUID = 1L;
    private final String failedChangeSetName;

    public MigrationFailedException() {
        this.failedChangeSetName = "(unknown)";
    }

    public MigrationFailedException(ChangeSet failedChangeSet, String message) {
        super(message);
        this.failedChangeSetName = failedChangeSet.toString(false);
    }

    public MigrationFailedException(ChangeSet failedChangeSet, String message, Throwable cause) {
        super(message, cause);
        this.failedChangeSetName = failedChangeSet.toString(false);
    }

    public MigrationFailedException(ChangeSet failedChangeSet, Throwable cause) {
        super(cause);
        this.failedChangeSetName = failedChangeSet.toString(false);
    }

    @Override
    public String getMessage() {
        String message = "Migration failed";
        if (this.failedChangeSetName != null) {
            message = message + " for changeset " + this.failedChangeSetName;
        }
        message = message + ":\n     Reason: " + super.getMessage();
        return message;
    }
}

