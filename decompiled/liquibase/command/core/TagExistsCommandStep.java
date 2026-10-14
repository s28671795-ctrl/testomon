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
import liquibase.command.CommandResultDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.TagCommandStep;
import liquibase.database.Database;
import liquibase.lockservice.LockService;
import liquibase.lockservice.LockServiceFactory;

public class TagExistsCommandStep
extends AbstractCommandStep {
    protected static final String[] COMMAND_NAME = new String[]{"tagExists"};
    public static final CommandArgumentDefinition<String> TAG_ARG;
    public static final CommandResultDefinition<Boolean> TAG_EXISTS_RESULT;

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(Database.class, LockService.class);
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database database = (Database)commandScope.getDependency(Database.class);
        ChangeLogHistoryService changeLogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
        changeLogService.init();
        LockServiceFactory.getInstance().getLockService(database).init();
        String tag = commandScope.getArgumentValue(TagCommandStep.TAG_ARG);
        this.sendResults(changeLogService.tagExists(tag), database, tag, resultsBuilder);
    }

    private void sendResults(boolean exists, Database database, String tag, CommandResultsBuilder resultsBuilder) {
        resultsBuilder.addResult(TAG_EXISTS_RESULT, exists);
        Scope.getCurrentScope().getUI().sendMessage(String.format(coreBundle.getString(exists ? "tag.exists" : "tag.does.not.exist"), tag, database.getConnection().getConnectionUserName() + "@" + database.getConnection().getURL()));
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Verify the existence of the specified tag");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        TAG_ARG = builder.argument("tag", String.class).required().description("Tag to check").build();
        TAG_EXISTS_RESULT = builder.result("tagExistsResult", Boolean.class).description("Does the tag exists?").build();
    }
}

