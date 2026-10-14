/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.Arrays;
import java.util.List;
import liquibase.Scope;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.database.Database;

public class ValidateCommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"validate"};

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Validate the changelog for errors");
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(Database.class, DatabaseChangeLog.class);
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("no.validation.errors.found"));
        resultsBuilder.addResult("statusCode", (Object)0);
    }

    static {
        CommandBuilder commandBuilder = new CommandBuilder(new String[][]{COMMAND_NAME});
    }
}

