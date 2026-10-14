/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.Arrays;
import java.util.List;
import liquibase.Beta;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.database.Database;

public class UpdateTestingRollbackCommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"updateTestingRollback"};
    public static final CommandArgumentDefinition<String> TAG_ARG;

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(Database.class, DatabaseChangeLog.class, ChangeExecListener.class);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Updates database, then rolls back changes before updating again. Useful for testing rollback support");
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database database = (Database)commandScope.getDependency(Database.class);
        Contexts contexts = (Contexts)commandScope.getDependency(Contexts.class);
        LabelExpression labelExpression = (LabelExpression)commandScope.getDependency(LabelExpression.class);
        ChangeExecListener changeExecListener = (ChangeExecListener)commandScope.getDependency(ChangeExecListener.class);
        String tag = commandScope.getArgumentValue(TAG_ARG);
        DatabaseChangeLog databaseChangeLog = (DatabaseChangeLog)commandScope.getDependency(DatabaseChangeLog.class);
        Liquibase liquibase = new Liquibase(databaseChangeLog, Scope.getCurrentScope().getResourceAccessor(), database);
        liquibase.setChangeExecListener(changeExecListener);
        ChangeLogHistoryService changeLogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
        int originalSize = changeLogService.getRanChangeSets().size();
        this.initialUpdate(liquibase, tag, contexts, labelExpression);
        changeLogService.reset();
        int changesetsToRollback = changeLogService.getRanChangeSets().size() - originalSize;
        Scope.getCurrentScope().getLog(this.getClass()).info(String.format("Rolling back %d changeset(s).", changesetsToRollback));
        this.rollbackUpdate(liquibase, changesetsToRollback, contexts, labelExpression);
        this.finalUpdate(liquibase, tag, contexts, labelExpression);
    }

    @Beta
    protected void initialUpdate(Liquibase liquibase, String tag, Contexts contexts, LabelExpression labelExpression) throws Exception {
        liquibase.update(tag, contexts, labelExpression);
    }

    @Beta
    protected void rollbackUpdate(Liquibase liquibase, int changesetsToRollback, Contexts contexts, LabelExpression labelExpression) throws Exception {
        liquibase.rollback(changesetsToRollback, null, contexts, labelExpression);
    }

    @Beta
    protected void finalUpdate(Liquibase liquibase, String tag, Contexts contexts, LabelExpression labelExpression) throws Exception {
        liquibase.update(tag, contexts, labelExpression);
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        TAG_ARG = builder.argument("tag", String.class).hidden().build();
    }
}

