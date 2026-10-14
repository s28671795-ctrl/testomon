/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.io.OutputStream;
import java.util.SortedMap;
import java.util.TreeMap;
import liquibase.Scope;
import liquibase.command.CommandFailedException;
import liquibase.command.CommandResultDefinition;
import liquibase.command.CommandResults;
import liquibase.command.CommandScope;
import liquibase.util.StringUtil;

public class CommandResultsBuilder {
    private final OutputStream outputStream;
    private final SortedMap<String, Object> resultValues = new TreeMap<String, Object>();
    private final CommandScope commandScope;

    CommandResultsBuilder(CommandScope commandScope, OutputStream outputStream2) {
        this.outputStream = outputStream2;
        this.commandScope = commandScope;
    }

    public CommandScope getCommandScope() {
        return this.commandScope;
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    public CommandResultsBuilder addResult(String key, Object value) {
        this.resultValues.put(key, value);
        return this;
    }

    public Object getResult(String key) {
        return this.resultValues.get(key);
    }

    public <T> CommandResultsBuilder addResult(CommandResultDefinition<T> definition, T value) {
        return this.addResult(definition.getName(), value);
    }

    public CommandFailedException commandFailed(String message, int exitCode) {
        return this.commandFailed(message, exitCode, false);
    }

    public CommandFailedException commandFailed(String message, int exitCode, boolean expected) {
        return new CommandFailedException(this.build(), exitCode, message, expected);
    }

    CommandResults build() {
        try {
            if (this.outputStream != null) {
                this.outputStream.flush();
            }
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Error flushing " + StringUtil.join(this.commandScope.getCommand().getName(), " ") + " output: " + e.getMessage(), e);
        }
        return new CommandResults(this.resultValues, this.commandScope);
    }
}

