/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.Arrays;
import java.util.List;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.lockservice.LockService;
import liquibase.lockservice.LockServiceFactory;

public class TagCommandStep
extends AbstractCommandStep {
    protected static final String[] COMMAND_NAME = new String[]{"tag"};
    public static final CommandArgumentDefinition<String> TAG_ARG;

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(Database.class, LockService.class);
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database database = (Database)commandScope.getDependency(Database.class);
        ChangeLogHistoryService changeLogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
        changeLogService.generateDeploymentId();
        changeLogService.init();
        LockServiceFactory.getInstance().getLockService(database).init();
        changeLogService.tag(commandScope.getArgumentValue(TAG_ARG));
        this.sendResults(database);
    }

    private void sendResults(Database database) {
        Scope.getCurrentScope().getUI().sendMessage(String.format(coreBundle.getString("successfully.tagged"), database.getConnection().getConnectionUserName() + "@" + database.getConnection().getURL()));
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Mark the current database state with the specified tag");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        TAG_ARG = builder.argument("tag", String.class).required().description("Tag to add to the database changelog table").build();
    }
}

