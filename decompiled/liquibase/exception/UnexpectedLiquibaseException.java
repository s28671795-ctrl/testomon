/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import liquibase.exception.ExitCodeException;

public class UnexpectedLiquibaseException
extends RuntimeException
implements ExitCodeException {
    private static final long serialVersionUID = 1570124571347160550L;
    private Integer exitCode = null;

    public UnexpectedLiquibaseException(String message) {
        super(message);
    }

    public UnexpectedLiquibaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedLiquibaseException(Throwable cause) {
        super(cause);
    }

    public UnexpectedLiquibaseException(Throwable cause, int exitCode) {
        super(cause);
        this.exitCode = exitCode;
    }

    @Override
    public Integer getExitCode() {
        return this.exitCode;
    }
}

