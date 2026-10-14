/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition;

import liquibase.changelog.DatabaseChangeLog;
import liquibase.precondition.Precondition;

public class ErrorPrecondition {
    private final Throwable cause;
    private final Precondition precondition;
    private final DatabaseChangeLog changeLog;

    public ErrorPrecondition(Throwable exception, DatabaseChangeLog changeLog, Precondition precondition) {
        this.cause = exception;
        this.changeLog = changeLog;
        this.precondition = precondition;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public Precondition getPrecondition() {
        return this.precondition;
    }

    public String toString() {
        Throwable cause = this.cause;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        String causeMessage = cause.getMessage();
        if (causeMessage == null) {
            causeMessage = this.cause.getMessage();
        }
        if (this.changeLog == null) {
            return causeMessage;
        }
        return this.changeLog + " : " + this.precondition.toString() + " : " + causeMessage;
    }
}

