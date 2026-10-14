/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Scope;
import liquibase.UpdateSummaryEnum;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.StatusChangeLogIterator;
import liquibase.changelog.filter.ContextChangeSetFilter;
import liquibase.changelog.filter.CountChangeSetFilter;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.changelog.filter.IgnoreChangeSetFilter;
import liquibase.changelog.filter.LabelChangeSetFilter;
import liquibase.changelog.filter.ShouldRunChangeSetFilter;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandScope;
import liquibase.command.CommonArgumentNames;
import liquibase.command.core.AbstractUpdateCommandStep;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;

public class UpdateCountCommandStep
extends AbstractUpdateCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"updateCount"};
    public static final CommandArgumentDefinition<String> CHANGELOG_FILE_ARG;
    public static final CommandArgumentDefinition<String> LABEL_FILTER_ARG;
    public static final CommandArgumentDefinition<String> CONTEXTS_ARG;
    public static final CommandArgumentDefinition<Integer> COUNT_ARG;
    public static final CommandArgumentDefinition<ChangeLogParameters> CHANGELOG_PARAMETERS;

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Deploy the specified number of changes from the changelog file");
    }

    @Override
    public String getChangelogFileArg(CommandScope commandScope) {
        return commandScope.getArgumentValue(CHANGELOG_FILE_ARG);
    }

    @Override
    public String getContextsArg(CommandScope commandScope) {
        return commandScope.getArgumentValue(CONTEXTS_ARG);
    }

    @Override
    public String getLabelFilterArg(CommandScope commandScope) {
        return commandScope.getArgumentValue(LABEL_FILTER_ARG);
    }

    @Override
    public String[] getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public UpdateSummaryEnum getShowSummary(CommandScope commandScope) {
        return (UpdateSummaryEnum)((Object)commandScope.getDependency(UpdateSummaryEnum.class));
    }

    @Override
    public ChangeLogIterator getStatusChangelogIterator(CommandScope commandScope, Database database, Contexts contexts, LabelExpression labelExpression, DatabaseChangeLog databaseChangeLog) throws DatabaseException {
        return new StatusChangeLogIterator(databaseChangeLog, new ShouldRunChangeSetFilter(database), new ContextChangeSetFilter(contexts), new LabelChangeSetFilter(labelExpression), new DbmsChangeSetFilter(database), new IgnoreChangeSetFilter(), new CountChangeSetFilter(commandScope.getArgumentValue(COUNT_ARG)));
    }

    @Override
    public ChangeLogIterator getStandardChangelogIterator(CommandScope commandScope, Database database, Contexts contexts, LabelExpression labelExpression, DatabaseChangeLog databaseChangeLog) throws DatabaseException {
        return new ChangeLogIterator(databaseChangeLog, new ShouldRunChangeSetFilter(database), new ContextChangeSetFilter(contexts), new LabelChangeSetFilter(labelExpression), new DbmsChangeSetFilter(database), new IgnoreChangeSetFilter(), new CountChangeSetFilter(commandScope.getArgumentValue(COUNT_ARG)));
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        ArrayList deps = new ArrayList(super.requiredDependencies());
        deps.add(UpdateSummaryEnum.class);
        return deps;
    }

    @Override
    protected void customMdcLogging(CommandScope commandScope) {
        Scope.getCurrentScope().addMdcValue("updateCount", String.valueOf(commandScope.getArgumentValue(COUNT_ARG)));
    }

    @Override
    public void postUpdateLog(int rowsAffected) {
        if (rowsAffected > -1) {
            Scope.getCurrentScope().getUI().sendMessage(String.format(coreBundle.getString("update.count.successful.with.row.count"), rowsAffected));
        } else {
            Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("update.count.successful"));
        }
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        CHANGELOG_FILE_ARG = builder.argument(CommonArgumentNames.CHANGELOG_FILE, String.class).required().description("The root changelog").build();
        LABEL_FILTER_ARG = builder.argument("labelFilter", String.class).addAlias("labels").description("Changeset labels to match").build();
        CONTEXTS_ARG = builder.argument("contextFilter", String.class).addAlias("contexts").description("Changeset contexts to match").build();
        COUNT_ARG = builder.argument("count", Integer.class).required().description("The number of changes in the changelog to deploy").build();
        CHANGELOG_PARAMETERS = builder.argument("changelogParameters", ChangeLogParameters.class).hidden().build();
    }
}

