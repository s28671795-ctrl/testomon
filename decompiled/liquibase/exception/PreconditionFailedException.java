/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import java.util.ArrayList;
import java.util.List;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.precondition.FailedPrecondition;
import liquibase.precondition.Precondition;

public class PreconditionFailedException
extends Exception {
    private static final long serialVersionUID = 1L;
    private final List<FailedPrecondition> failedPreconditions;

    public PreconditionFailedException(String message, DatabaseChangeLog changeLog, Precondition precondition) {
        this(new FailedPrecondition(message, changeLog, precondition));
    }

    public PreconditionFailedException(FailedPrecondition failedPrecondition) {
        super("Preconditions Failed");
        this.failedPreconditions = new ArrayList<FailedPrecondition>();
        this.failedPreconditions.add(failedPrecondition);
    }

    public PreconditionFailedException(List<FailedPrecondition> failedPreconditions) {
        super("Preconditions Failed");
        this.failedPreconditions = failedPreconditions;
    }

    public List<FailedPrecondition> getFailedPreconditions() {
        return this.failedPreconditions;
    }
}

