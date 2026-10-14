/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition;

import liquibase.changelog.DatabaseChangeLog;
import liquibase.precondition.Precondition;

public class FailedPrecondition {
    private final String message;
    private final Precondition precondition;
    private final DatabaseChangeLog changeLog;

    public FailedPrecondition(String message, DatabaseChangeLog changeLog, Precondition precondition) {
        this.message = message;
        this.changeLog = changeLog;
        this.precondition = precondition;
    }

    public String getMessage() {
        return this.message;
    }

    public Precondition getPrecondition() {
        return this.precondition;
    }

    public String toString() {
        if (this.changeLog == null) {
            return this.message;
        }
        return this.changeLog + " : " + this.message;
    }
}

