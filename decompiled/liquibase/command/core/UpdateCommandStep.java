/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.Arrays;
import java.util.List;
import liquibase.Scope;
import liquibase.UpdateSummaryEnum;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.command.CleanUpCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.CommonArgumentNames;
import liquibase.command.core.AbstractUpdateCommandStep;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.database.Database;
import liquibase.exception.CommandValidationException;

public class UpdateCommandStep
extends AbstractUpdateCommandStep
implements CleanUpCommandStep {
    public static final String[] LEGACY_COMMAND_NAME = new String[]{"migrate"};
    public static String[] COMMAND_NAME = new String[]{"update"};
    public static final CommandArgumentDefinition<String> CHANGELOG_FILE_ARG;
    public static final CommandArgumentDefinition<DatabaseChangeLog> CHANGELOG_ARG;
    public static final CommandArgumentDefinition<String> LABEL_FILTER_ARG;
    public static final CommandArgumentDefinition<String> CONTEXTS_ARG;

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
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME, LEGACY_COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Deploy any changes in the changelog file that have not been deployed");
        if (commandDefinition.is(LEGACY_COMMAND_NAME)) {
            commandDefinition.setHidden(true);
        }
    }

    @Override
    public void validate(CommandScope commandScope) throws CommandValidationException {
        commandScope.addArgumentValue(DatabaseChangelogCommandStep.UPDATE_NULL_CHECKSUMS, Boolean.TRUE);
    }

    @Override
    public void postUpdateLog(int rowsAffected) {
        if (rowsAffected > -1) {
            Scope.getCurrentScope().getUI().sendMessage(String.format(coreBundle.getString("update.successful.with.row.count"), rowsAffected));
        } else {
            Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("update.successful"));
        }
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        List<Class<?>> deps = Arrays.asList(Database.class, DatabaseChangeLog.class, ChangeExecListener.class, ChangeLogParameters.class, UpdateSummaryEnum.class);
        return deps;
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        this.setDBLock(false);
        super.run(resultsBuilder);
    }

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME, LEGACY_COMMAND_NAME);
        CHANGELOG_ARG = builder.argument("databaseChangelog", DatabaseChangeLog.class).hidden().build();
        CHANGELOG_FILE_ARG = builder.argument(CommonArgumentNames.CHANGELOG_FILE, String.class).required().description("The root changelog").supersededBy(CHANGELOG_ARG).build();
        CHANGELOG_ARG.setSupersededBy(CHANGELOG_FILE_ARG);
        LABEL_FILTER_ARG = builder.argument("labelFilter", String.class).addAlias("labels").description("Changeset labels to match").build();
        CONTEXTS_ARG = builder.argument("contextFilter", String.class).addAlias("contexts").description("Changeset contexts to match").build();
    }
}

