/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import java.util.ArrayList;
import java.util.List;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.precondition.ErrorPrecondition;
import liquibase.precondition.Precondition;

public class PreconditionErrorException
extends Exception {
    private static final long serialVersionUID = 1L;
    private final List<ErrorPrecondition> erroredPreconditions;

    public PreconditionErrorException(String message, List<ErrorPrecondition> erroredPreconditions) {
        super(message);
        this.erroredPreconditions = erroredPreconditions;
    }

    public PreconditionErrorException(Exception cause, DatabaseChangeLog changeLog, Precondition precondition) {
        this(new ErrorPrecondition(cause, changeLog, precondition));
    }

    public PreconditionErrorException(ErrorPrecondition errorPrecondition) {
        super("Precondition Error", errorPrecondition.getCause());
        this.erroredPreconditions = new ArrayList<ErrorPrecondition>();
        this.erroredPreconditions.add(errorPrecondition);
    }

    public PreconditionErrorException(List<ErrorPrecondition> errorPreconditions) {
        super("Precondition Error");
        this.erroredPreconditions = errorPreconditions;
    }

    public List<ErrorPrecondition> getErrorPreconditions() {
        return this.erroredPreconditions;
    }
}

