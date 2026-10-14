/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import liquibase.RuntimeEnvironment;
import liquibase.Scope;
import liquibase.change.core.RawSQLChange;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.AlreadyRanChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.filter.ContextChangeSetFilter;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.changelog.filter.IgnoreChangeSetFilter;
import liquibase.changelog.filter.LabelChangeSetFilter;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.changelog.visitor.DefaultChangeExecListener;
import liquibase.changelog.visitor.RollbackVisitor;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.lockservice.LockService;
import liquibase.logging.mdc.MdcObject;
import liquibase.logging.mdc.customobjects.ChangesetsRolledback;
import liquibase.logging.mdc.customobjects.ExceptionDetails;
import liquibase.report.RollbackReportParameters;
import liquibase.resource.Resource;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

public abstract class AbstractRollbackCommandStep
extends AbstractCommandStep {
    public static final CommandArgumentDefinition<String> ROLLBACK_SCRIPT_ARG;

    protected void doRollback(CommandResultsBuilder resultsBuilder, List<RanChangeSet> ranChangeSetList, ChangeSetFilter changeSetFilter) throws Exception {
        this.doRollback(resultsBuilder, ranChangeSetList, changeSetFilter, null);
    }

    protected void doRollback(CommandResultsBuilder resultsBuilder, List<RanChangeSet> ranChangeSetList, ChangeSetFilter changeSetFilter, RollbackReportParameters rollbackReportParameters) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        String changelogFile = commandScope.getArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG);
        if (rollbackReportParameters != null) {
            rollbackReportParameters.setChangelogArgValue(changelogFile);
        }
        String rollbackScript = commandScope.getArgumentValue(ROLLBACK_SCRIPT_ARG);
        Scope.getCurrentScope().addMdcValue("rollbackScript", rollbackScript);
        ChangeLogParameters changeLogParameters = (ChangeLogParameters)commandScope.getDependency(ChangeLogParameters.class);
        DatabaseChangeLog databaseChangeLog = (DatabaseChangeLog)commandScope.getDependency(DatabaseChangeLog.class);
        Database database = (Database)commandScope.getDependency(Database.class);
        try {
            ChangeLogIterator logIterator = new ChangeLogIterator(ranChangeSetList, databaseChangeLog, new AlreadyRanChangeSetFilter(ranChangeSetList), new ContextChangeSetFilter(changeLogParameters.getContexts()), new LabelChangeSetFilter(changeLogParameters.getLabels()), new IgnoreChangeSetFilter(), new DbmsChangeSetFilter(database), changeSetFilter);
            AbstractRollbackCommandStep.doRollback(database, changelogFile, rollbackScript, logIterator, changeLogParameters, databaseChangeLog, (ChangeExecListener)commandScope.getDependency(ChangeExecListener.class), rollbackReportParameters);
        }
        catch (Throwable t2) {
            AbstractRollbackCommandStep.handleRollbackException(this.defineCommandNames()[0][0], rollbackReportParameters);
            throw t2;
        }
        finally {
            Scope.getCurrentScope().getMdcManager().remove("changesetsRolledback");
        }
    }

    public static void doRollback(Database database, String changelogFile, String rollbackScript, ChangeLogIterator logIterator, ChangeLogParameters changeLogParameters, DatabaseChangeLog databaseChangeLog, ChangeExecListener changeExecListener) throws Exception {
        AbstractRollbackCommandStep.doRollback(database, changelogFile, rollbackScript, logIterator, changeLogParameters, databaseChangeLog, changeExecListener, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void doRollback(Database database, String changelogFile, String rollbackScript, ChangeLogIterator logIterator, ChangeLogParameters changeLogParameters, DatabaseChangeLog databaseChangeLog, ChangeExecListener changeExecListener, RollbackReportParameters rollbackReportParameters) throws Exception {
        try {
            if (rollbackScript == null) {
                ArrayList<ChangeSet> processedChangesets = new ArrayList<ChangeSet>();
                logIterator.run(new RollbackVisitor(database, changeExecListener, processedChangesets), new RuntimeEnvironment(database, changeLogParameters.getContexts(), changeLogParameters.getLabels()));
                AbstractRollbackCommandStep.addChangelogFileToMdc(changelogFile, databaseChangeLog);
                Scope.getCurrentScope().addMdcValue("changesetsRolledback", ChangesetsRolledback.fromChangesetList(processedChangesets), false);
                if (processedChangesets.isEmpty()) {
                    Scope.getCurrentScope().getUI().sendMessage("INFO: 0 changesets rolled back.");
                }
            } else {
                List<ChangeSet> changeSets = AbstractRollbackCommandStep.determineRollbacks(database, logIterator, changeLogParameters);
                AbstractRollbackCommandStep.executeRollbackScript(database, rollbackScript, changeSets, databaseChangeLog, changeLogParameters, changeExecListener);
                AbstractRollbackCommandStep.removeRunStatus(changeSets, database);
                AbstractRollbackCommandStep.addChangelogFileToMdc(changelogFile, databaseChangeLog);
                Scope.getCurrentScope().addMdcValue("changesetsRolledback", ChangesetsRolledback.fromChangesetList(changeSets));
            }
            try (MdcObject deploymentOutcomeMdc = Scope.getCurrentScope().getMdcManager().put("deploymentOutcome", "success");){
                Scope.getCurrentScope().getLog(AbstractRollbackCommandStep.class).info("Rollback command completed successfully.");
                if (rollbackReportParameters != null) {
                    rollbackReportParameters.getOperationInfo().setOperationOutcome("success");
                }
            }
        }
        catch (Exception exception) {
            if (rollbackReportParameters != null) {
                rollbackReportParameters.setSuccess(false);
                String source2 = ExceptionDetails.findSource(database);
                ExceptionDetails exceptionDetails = new ExceptionDetails(exception, source2);
                rollbackReportParameters.setRollbackException(exceptionDetails);
                throw exception;
            }
        }
        finally {
            if (rollbackReportParameters != null && changeExecListener instanceof DefaultChangeExecListener) {
                DefaultChangeExecListener defaultListener = (DefaultChangeExecListener)changeExecListener;
                List<ChangeSet> failedChangeSets = defaultListener.getFailedRollbackChangeSets();
                List<ChangeSet> rolledBackChangeSets = defaultListener.getRolledBackChangeSets();
                List<ChangeSet> pendingChangeSets = logIterator.getSkippedDueToExceptionChangeSets();
                HashMap<ChangeSet, String> pendingChangeSetMap = new HashMap<ChangeSet, String>();
                pendingChangeSets.forEach(changeSet -> pendingChangeSetMap.put((ChangeSet)changeSet, "Unexpected error running Liquibase."));
                rollbackReportParameters.getChangesetInfo().setChangesetCount(failedChangeSets.size() + rolledBackChangeSets.size());
                rollbackReportParameters.getChangesetInfo().addAllToChangesetInfoList(rolledBackChangeSets, true);
                rollbackReportParameters.getChangesetInfo().addAllToChangesetInfoList(failedChangeSets, true);
                rollbackReportParameters.getChangesetInfo().setFailedChangesetCount(failedChangeSets.size());
                rollbackReportParameters.getChangesetInfo().addAllToPendingChangesetInfoList(pendingChangeSetMap);
                rollbackReportParameters.getChangesetInfo().setPendingChangesetCount(pendingChangeSetMap.size());
                rollbackReportParameters.setFailedChangeset(failedChangeSets.stream().map(ChangeSet::toString).collect(Collectors.joining(", ")));
                rolledBackChangeSets.forEach(changeSet -> changeSet.setGeneratedSql(new ArrayList<String>()));
            }
        }
    }

    private static void addChangelogFileToMdc(String changelogFile, DatabaseChangeLog databaseChangeLog) {
        if (StringUtil.isNotEmpty(databaseChangeLog.getLogicalFilePath())) {
            Scope.getCurrentScope().addMdcValue("changelogFile", databaseChangeLog.getLogicalFilePath());
        } else {
            Scope.getCurrentScope().addMdcValue("changelogFile", changelogFile);
        }
    }

    private static List<ChangeSet> determineRollbacks(Database database, ChangeLogIterator logIterator, ChangeLogParameters changeLogParameters) throws LiquibaseException {
        final ArrayList<ChangeSet> changeSetsToRollback = new ArrayList<ChangeSet>();
        logIterator.run(new ChangeSetVisitor(){

            @Override
            public ChangeSetVisitor.Direction getDirection() {
                return ChangeSetVisitor.Direction.REVERSE;
            }

            @Override
            public void visit(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
                changeSetsToRollback.add(changeSet);
            }
        }, new RuntimeEnvironment(database, changeLogParameters.getContexts(), changeLogParameters.getLabels()));
        return changeSetsToRollback;
    }

    private static void executeRollbackScript(Database database, String rollbackScript, List<ChangeSet> changeSets, DatabaseChangeLog changelog, ChangeLogParameters changeLogParameters, ChangeExecListener changeExecListener) throws LiquibaseException {
        String rollbackScriptContents;
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
        try {
            Resource resource = Scope.getCurrentScope().getResourceAccessor().get(rollbackScript);
            if (resource == null) {
                throw new LiquibaseException("WARNING: The rollback script '" + rollbackScript + "' was not located.  Please check your parameters. No rollback was performed");
            }
            try (InputStream stream = resource.openInputStream();){
                rollbackScriptContents = StreamUtil.readStreamAsString(stream);
            }
        }
        catch (IOException e) {
            throw new LiquibaseException("Error reading rollbackScript " + executor2 + ": " + e.getMessage());
        }
        rollbackScriptContents = changeLogParameters.expandExpressions(rollbackScriptContents, changelog);
        RawSQLChange rollbackChange = AbstractRollbackCommandStep.buildRawSQLChange(rollbackScriptContents);
        try {
            AbstractRollbackCommandStep.sendRollbackMessages(changeSets, changelog, RollbackMessageType.WILL_ROLLBACK, database, changeExecListener, null);
            executor2.execute(rollbackChange);
            AbstractRollbackCommandStep.sendRollbackMessages(changeSets, changelog, RollbackMessageType.ROLLED_BACK, database, changeExecListener, null);
        }
        catch (DatabaseException e) {
            Scope.getCurrentScope().getLog(AbstractRollbackCommandStep.class).severe("Error executing rollback script: " + e.getMessage());
            if (changeExecListener != null) {
                AbstractRollbackCommandStep.sendRollbackMessages(changeSets, changelog, RollbackMessageType.ROLLBACK_FAILED, database, changeExecListener, e);
            }
            throw new DatabaseException("Error executing rollback script", e);
        }
        database.commit();
    }

    protected static RawSQLChange buildRawSQLChange(String rollbackScriptContents) {
        RawSQLChange rollbackChange = new RawSQLChange(rollbackScriptContents);
        rollbackChange.setSplitStatements(true);
        rollbackChange.setStripComments(true);
        return rollbackChange;
    }

    private static void sendRollbackMessages(List<ChangeSet> changeSets, DatabaseChangeLog databaseChangeLog, RollbackMessageType messageType, Database database, ChangeExecListener changeExecListener, Exception exception) {
        changeSets.forEach(changeSet -> {
            if (messageType == RollbackMessageType.WILL_ROLLBACK) {
                changeExecListener.willRollback((ChangeSet)changeSet, databaseChangeLog, database);
            } else if (messageType == RollbackMessageType.ROLLED_BACK) {
                String message = "Rolled Back Changeset:" + changeSet.toString(false);
                Scope.getCurrentScope().getUI().sendMessage(message);
                Scope.getCurrentScope().getLog(AbstractRollbackCommandStep.class).info(message);
                changeExecListener.rolledBack((ChangeSet)changeSet, databaseChangeLog, database);
            } else if (messageType == RollbackMessageType.ROLLBACK_FAILED) {
                String message = "Failed rolling back Changeset:" + changeSet.toString(false);
                Scope.getCurrentScope().getUI().sendMessage(message);
                changeExecListener.rollbackFailed((ChangeSet)changeSet, databaseChangeLog, database, exception);
            }
        });
    }

    private static void removeRunStatus(List<ChangeSet> changeSets, Database database) throws LiquibaseException {
        for (ChangeSet changeSet : changeSets) {
            database.removeRanStatus(changeSet);
            database.commit();
        }
    }

    private static void handleRollbackException(String operationName, RollbackReportParameters rollbackReportParameters) {
        try (MdcObject deploymentOutcomeMdc = Scope.getCurrentScope().addMdcValue("deploymentOutcome", "fail");){
            Scope.getCurrentScope().getLog(AbstractRollbackCommandStep.class).info(operationName + " command encountered an exception.");
            if (rollbackReportParameters != null) {
                rollbackReportParameters.getOperationInfo().setOperationOutcome("fail");
                rollbackReportParameters.setSuccess(false);
            }
        }
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(DatabaseChangeLog.class, LockService.class, ChangeExecListener.class);
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Rollback changes made to the database based on the specific tag");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[0][]);
        ROLLBACK_SCRIPT_ARG = builder.argument("rollbackScript", String.class).description("Rollback script to execute").build();
    }

    private static enum RollbackMessageType {
        WILL_ROLLBACK,
        ROLLED_BACK,
        ROLLBACK_FAILED;

    }
}

