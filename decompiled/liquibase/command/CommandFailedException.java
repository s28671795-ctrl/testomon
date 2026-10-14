/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import liquibase.command.CommandResults;
import liquibase.exception.ExitCodeException;

public class CommandFailedException
extends Exception
implements ExitCodeException {
    private static final long serialVersionUID = -394350095952659571L;
    private final CommandResults results;
    private final int exitCode;
    private final boolean expected;

    public CommandFailedException(CommandResults results, int exitCode, String message, boolean expected) {
        super(message);
        this.results = results;
        this.exitCode = exitCode;
        this.expected = expected;
    }

    public CommandResults getResults() {
        return this.results;
    }

    @Override
    public Integer getExitCode() {
        return this.exitCode;
    }

    public boolean isExpected() {
        return this.expected;
    }
}

