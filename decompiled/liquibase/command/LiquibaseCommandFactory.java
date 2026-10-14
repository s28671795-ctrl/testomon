/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import liquibase.command.CommandExecutionException;
import liquibase.command.CommandResult;
import liquibase.command.LiquibaseCommand;
import liquibase.plugin.AbstractPluginFactory;

class LiquibaseCommandFactory
extends AbstractPluginFactory<LiquibaseCommand> {
    protected LiquibaseCommandFactory() {
    }

    @Override
    protected Class<LiquibaseCommand> getPluginClass() {
        return LiquibaseCommand.class;
    }

    @Override
    protected int getPriority(LiquibaseCommand obj, Object ... args2) {
        return obj.getPriority((String)args2[0]);
    }

    public LiquibaseCommand getCommand(String commandName) {
        return (LiquibaseCommand)this.getPlugin(commandName);
    }

    public <T extends CommandResult> T execute(LiquibaseCommand<T> command) throws CommandExecutionException {
        command.validate();
        try {
            return command.run();
        }
        catch (Exception e) {
            if (e instanceof CommandExecutionException) {
                throw (CommandExecutionException)e;
            }
            throw new CommandExecutionException(e);
        }
    }
}

