/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.command.CommandArgument;
import liquibase.command.CommandResult;
import liquibase.command.LiquibaseCommand;

public abstract class AbstractCommand<T extends CommandResult>
implements LiquibaseCommand<T> {
    @Override
    public int getPriority(String commandName) {
        if (commandName != null && commandName.equalsIgnoreCase(this.getName())) {
            return 1;
        }
        return -1;
    }

    @Override
    public SortedSet<CommandArgument> getArguments() {
        return new TreeSet<CommandArgument>();
    }
}

