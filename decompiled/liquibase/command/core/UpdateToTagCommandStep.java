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
import liquibase.change.core.TagDatabaseChange;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.StatusChangeLogIterator;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ContextChangeSetFilter;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.changelog.filter.IgnoreChangeSetFilter;
import liquibase.changelog.filter.LabelChangeSetFilter;
import liquibase.changelog.filter.ShouldRunChangeSetFilter;
import liquibase.changelog.filter.UpToTagChangeSetFilter;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.CommonArgumentNames;
import liquibase.command.core.AbstractUpdateCommandStep;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;

public class UpdateToTagCommandStep
extends AbstractUpdateCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"updateToTag"};
    public static final CommandArgumentDefinition<String> CHANGELOG_FILE_ARG;
    public static final CommandArgumentDefinition<String> LABEL_FILTER_ARG;
    public static final CommandArgumentDefinition<String> CONTEXTS_ARG;
    public static final CommandArgumentDefinition<String> TAG_ARG;
    public static final CommandArgumentDefinition<ChangeLogParameters> CHANGELOG_PARAMETERS;
    private boolean warningMessageShown = false;

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        this.setFastCheckEnabled(false);
        super.run(resultsBuilder);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Deploy changes from the changelog file to the specified tag");
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
    public ChangeLogIterator getStandardChangelogIterator(CommandScope commandScope, Database database, Contexts contexts, LabelExpression labelExpression, DatabaseChangeLog changeLog) throws DatabaseException {
        List<RanChangeSet> ranChangeSetList = database.getRanChangeSetList();
        String tag = commandScope.getArgumentValue(TAG_ARG);
        UpToTagChangeSetFilter upToTagChangeSetFilter = this.getUpToTagChangeSetFilter(tag, ranChangeSetList);
        if (!this.warningMessageShown && !upToTagChangeSetFilter.isSeenTag()) {
            this.checkForTagExists(changeLog, tag);
        }
        List<ChangeSetFilter> changesetFilters = this.getStandardChangelogIteratorFilters(database, contexts, labelExpression);
        changesetFilters.add(upToTagChangeSetFilter);
        return new ChangeLogIterator(changeLog, changesetFilters.toArray(new ChangeSetFilter[0]));
    }

    private void checkForTagExists(DatabaseChangeLog changeLog, String tag) {
        boolean found = changeLog.getChangeSets().stream().anyMatch(cs -> cs.getChanges().stream().anyMatch(ch -> ch instanceof TagDatabaseChange && ((TagDatabaseChange)ch).getTag().equals(tag)));
        if (!found) {
            String message = String.format("The tag '%s' was not found in the changelog '%s'. All changesets in the changelog were deployed.%nLearn about options for undoing these changes at https://docs.liquibase.com.", tag, changeLog.getPhysicalFilePath());
            Scope.getCurrentScope().getLog(UpdateToTagCommandStep.class).warning(message);
            Scope.getCurrentScope().getUI().sendMessage("WARNING:  " + message);
            this.warningMessageShown = true;
        }
    }

    private UpToTagChangeSetFilter getUpToTagChangeSetFilter(String tag, List<RanChangeSet> ranChangeSetList) {
        return new UpToTagChangeSetFilter(tag, ranChangeSetList);
    }

    @Override
    public ChangeLogIterator getStatusChangelogIterator(CommandScope commandScope, Database database, Contexts contexts, LabelExpression labelExpression, DatabaseChangeLog changeLog) throws DatabaseException {
        List<RanChangeSet> ranChangeSetList = database.getRanChangeSetList();
        String tag = commandScope.getArgumentValue(TAG_ARG);
        return new StatusChangeLogIterator(changeLog, tag, this.getUpToTagChangeSetFilter(tag, ranChangeSetList), new ShouldRunChangeSetFilter(database), new ContextChangeSetFilter(contexts), new LabelChangeSetFilter(labelExpression), new DbmsChangeSetFilter(database), new IgnoreChangeSetFilter());
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        ArrayList deps = new ArrayList(super.requiredDependencies());
        deps.add(UpdateSummaryEnum.class);
        return deps;
    }

    @Override
    protected void customMdcLogging(CommandScope commandScope) {
        Scope.getCurrentScope().addMdcValue("updateToTag", commandScope.getArgumentValue(TAG_ARG));
    }

    @Override
    public void postUpdateLog(int rowsAffected) {
        if (rowsAffected > -1) {
            Scope.getCurrentScope().getUI().sendMessage(String.format(coreBundle.getString("update.to.tag.successful.with.row.count"), rowsAffected));
        } else {
            Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("update.to.tag.successful"));
        }
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        CHANGELOG_FILE_ARG = builder.argument(CommonArgumentNames.CHANGELOG_FILE, String.class).required().description("The root changelog").build();
        LABEL_FILTER_ARG = builder.argument("labelFilter", String.class).addAlias("labels").description("Changeset labels to match").build();
        CONTEXTS_ARG = builder.argument("contextFilter", String.class).addAlias("contexts").description("Changeset contexts to match").build();
        TAG_ARG = builder.argument("tag", String.class).required().description("The tag to update to").build();
        CHANGELOG_PARAMETERS = builder.argument("changelogParameters", ChangeLogParameters.class).hidden().build();
    }
}

