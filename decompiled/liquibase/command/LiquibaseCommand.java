/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.util.SortedSet;
import liquibase.command.CommandArgument;
import liquibase.command.CommandResult;
import liquibase.command.CommandValidationErrors;
import liquibase.plugin.Plugin;

public interface LiquibaseCommand<T extends CommandResult>
extends Plugin {
    public String getName();

    public int getPriority(String var1);

    public SortedSet<CommandArgument> getArguments();

    public CommandValidationErrors validate();

    public T run() throws Exception;
}

