/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandResultDefinition;
import liquibase.command.CommonArgumentNames;

public class CommandBuilder {
    private final String[][] commandNames;

    public CommandBuilder(String[] ... commandNames) {
        this.commandNames = commandNames;
    }

    public <DataType> CommandArgumentDefinition.Building<DataType> argument(String name, Class<DataType> type) {
        return new CommandArgumentDefinition.Building<DataType>(this.commandNames, new CommandArgumentDefinition<DataType>(name, type));
    }

    public <DataType> CommandArgumentDefinition.Building<DataType> argument(CommonArgumentNames argument, Class<DataType> type) {
        return new CommandArgumentDefinition.Building<DataType>(this.commandNames, new CommandArgumentDefinition<DataType>(argument.getArgumentName(), type));
    }

    public <DataType> CommandArgumentDefinition.Building<DataType> addArgument(CommandArgumentDefinition<DataType> example) {
        return new CommandArgumentDefinition.Building<DataType>(this.commandNames, example);
    }

    public <DataType> CommandResultDefinition.Building<DataType> result(String name, Class<DataType> type) {
        return new CommandResultDefinition.Building<DataType>(new CommandResultDefinition<DataType>(name, type));
    }
}

