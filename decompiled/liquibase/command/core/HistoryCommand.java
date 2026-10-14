/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.PrintStream;
import java.text.DateFormat;
import liquibase.command.AbstractCommand;
import liquibase.command.CommandResult;
import liquibase.command.CommandScope;
import liquibase.command.CommandValidationErrors;
import liquibase.command.core.HistoryFormat;
import liquibase.command.core.InternalHistoryCommandStep;
import liquibase.database.Database;

public class HistoryCommand
extends AbstractCommand {
    private Database database;
    private final DateFormat dateFormat;
    private HistoryFormat format;
    private PrintStream outputStream = System.out;

    public HistoryCommand() {
        this.dateFormat = DateFormat.getDateTimeInstance(3, 3);
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public CommandValidationErrors validate() {
        return new CommandValidationErrors(this);
    }

    public Database getDatabase() {
        return this.database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public PrintStream getOutputStream() {
        return this.outputStream;
    }

    public void setOutputStream(PrintStream outputStream2) {
        this.outputStream = outputStream2;
    }

    @Override
    public CommandResult run() throws Exception {
        CommandScope commandScope = new CommandScope("internalHistory");
        commandScope.setOutput(this.getOutputStream());
        commandScope.addArgumentValue(InternalHistoryCommandStep.DATABASE_ARG, this.getDatabase());
        commandScope.addArgumentValue(InternalHistoryCommandStep.DATE_FORMAT_ARG, this.dateFormat);
        commandScope.addArgumentValue(InternalHistoryCommandStep.FORMAT_ARG, this.format);
        commandScope.execute();
        return new CommandResult("OK");
    }
}

