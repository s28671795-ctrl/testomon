/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.RuntimeEnvironment;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ContextChangeSetFilter;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.changelog.filter.IgnoreChangeSetFilter;
import liquibase.changelog.filter.LabelChangeSetFilter;
import liquibase.changelog.filter.NotRanChangeSetFilter;
import liquibase.changelog.filter.UpToTagChangeSetFilter;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.ChangeLogSyncListener;
import liquibase.changelog.visitor.ChangeLogSyncVisitor;
import liquibase.changelog.visitor.DefaultChangeExecListener;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.database.Database;
import liquibase.exception.CommandValidationException;
import liquibase.exception.DatabaseException;
import liquibase.lockservice.LockService;
import liquibase.logging.mdc.MdcObject;
import liquibase.util.StringUtil;

public class ChangelogSyncCommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"changelogSync"};
    private String tag = null;

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Marks all changes as executed in the database");
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(LockService.class, DatabaseChangeLog.class, ChangeLogParameters.class);
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        String changelogFile = commandScope.getArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG);
        Database database = (Database)commandScope.getDependency(Database.class);
        DatabaseChangeLog changeLog = (DatabaseChangeLog)commandScope.getDependency(DatabaseChangeLog.class);
        ChangeLogParameters changeLogParameters = (ChangeLogParameters)commandScope.getDependency(ChangeLogParameters.class);
        ChangeLogHistoryService changeLogHistoryService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
        changeLogHistoryService.init();
        changeLogHistoryService.generateDeploymentId();
        try {
            ChangeLogIterator runChangeLogIterator = this.buildChangeLogIterator(this.tag, changeLog, changeLogParameters.getContexts(), changeLogParameters.getLabels(), database);
            AtomicInteger changesetCount = new AtomicInteger(0);
            HashMap<String, Object> scopeVars = new HashMap<String, Object>(1);
            scopeVars.put("changesetCount", changesetCount);
            Scope.child(scopeVars, () -> runChangeLogIterator.run(new ChangeLogSyncVisitor(database, this.getChangeExecListener()), new RuntimeEnvironment(database, changeLogParameters.getContexts(), changeLogParameters.getLabels())));
            Scope.getCurrentScope().addMdcValue("changesetSyncCount", changesetCount.toString());
            this.addChangelogToMdc(changelogFile, changeLog);
            try (MdcObject changelogSyncOutcome = Scope.getCurrentScope().addMdcValue("changelogSyncOutcome", "success");){
                Scope.getCurrentScope().getLog(this.getClass()).info("Finished executing " + this.defineCommandNames()[0][0] + " command");
            }
        }
        catch (Exception e) {
            this.addChangelogToMdc(changelogFile, changeLog);
            try (MdcObject changelogSyncOutcome = Scope.getCurrentScope().addMdcValue("changelogSyncOutcome", "fail");){
                Scope.getCurrentScope().getLog(this.getClass()).warning("Failed executing " + this.defineCommandNames()[0][0] + " command");
            }
            throw e;
        }
    }

    public ChangeLogSyncListener getChangeExecListener() {
        return new DefaultChangeExecListener(new ChangeExecListener[0]);
    }

    private void addChangelogToMdc(String changelogFile, DatabaseChangeLog changeLog) {
        if (StringUtil.isNotEmpty(changeLog.getLogicalFilePath())) {
            Scope.getCurrentScope().addMdcValue("changelogFile", changeLog.getLogicalFilePath());
        } else {
            Scope.getCurrentScope().addMdcValue("changelogFile", changelogFile);
        }
    }

    private ChangeLogIterator buildChangeLogIterator(String tag, DatabaseChangeLog changeLog, Contexts contexts, LabelExpression labelExpression, Database database) throws DatabaseException {
        if (tag == null) {
            return new ChangeLogIterator(changeLog, new NotRanChangeSetFilter(database.getRanChangeSetList()), new ContextChangeSetFilter(contexts), new LabelChangeSetFilter(labelExpression), new IgnoreChangeSetFilter(), new DbmsChangeSetFilter(database));
        }
        List<RanChangeSet> ranChangeSetList = database.getRanChangeSetList();
        return new ChangeLogIterator(changeLog, new UpToTagChangeSetFilter(tag, ranChangeSetList), new NotRanChangeSetFilter(database.getRanChangeSetList()), new ContextChangeSetFilter(contexts), new LabelChangeSetFilter(labelExpression), new IgnoreChangeSetFilter(), new DbmsChangeSetFilter(database));
    }

    @Override
    public void validate(CommandScope commandScope) throws CommandValidationException {
        commandScope.addArgumentValue(DatabaseChangelogCommandStep.UPDATE_NULL_CHECKSUMS, Boolean.TRUE);
    }

    protected void setTag(String tag) {
        this.tag = tag;
    }

    static {
        new CommandBuilder(new String[][]{COMMAND_NAME});
    }
}

