/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import liquibase.Beta;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.RuntimeEnvironment;
import liquibase.Scope;
import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.StatusChangeLogIterator;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ContextChangeSetFilter;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.changelog.filter.IgnoreChangeSetFilter;
import liquibase.changelog.filter.LabelChangeSetFilter;
import liquibase.changelog.filter.ShouldRunChangeSetFilter;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.DefaultChangeExecListener;
import liquibase.changelog.visitor.ListVisitor;
import liquibase.changelog.visitor.StatusVisitor;
import liquibase.changelog.visitor.UpdateVisitor;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CleanUpCommandStep;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;
import liquibase.executor.ExecutorService;
import liquibase.lockservice.LockService;
import liquibase.lockservice.LockServiceFactory;
import liquibase.logging.mdc.MdcObject;
import liquibase.logging.mdc.customobjects.ChangesetsUpdated;
import liquibase.report.UpdateReportParameters;
import liquibase.util.ShowSummaryUtil;
import liquibase.util.StringUtil;
import liquibase.util.UpdateSummaryDetails;

public abstract class AbstractUpdateCommandStep
extends AbstractCommandStep
implements CleanUpCommandStep {
    public static final String DEFAULT_CHANGE_EXEC_LISTENER_RESULT_KEY = "defaultChangeExecListener";
    private static final String DATABASE_UP_TO_DATE_MESSAGE = "Database is up to date, no changesets to execute";
    private boolean isFastCheckEnabled = true;
    private boolean isDBLocked = true;
    private final Map<String, Boolean> upToDateFastCheck = new ConcurrentHashMap<String, Boolean>();

    public abstract String getChangelogFileArg(CommandScope var1);

    public abstract String getContextsArg(CommandScope var1);

    public abstract String getLabelFilterArg(CommandScope var1);

    public abstract String[] getCommandName();

    public abstract UpdateSummaryEnum getShowSummary(CommandScope var1);

    public UpdateSummaryOutputEnum getShowSummaryOutput(CommandScope commandScope) {
        return (UpdateSummaryOutputEnum)((Object)commandScope.getDependency(UpdateSummaryOutputEnum.class));
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(Database.class, LockService.class, DatabaseChangeLog.class, ChangeExecListener.class, ChangeLogParameters.class);
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        UpdateReportParameters updateReportParameters = new UpdateReportParameters();
        updateReportParameters.setCommandTitle(this.getFormattedCommandName(this.getCommandName()));
        resultsBuilder.addResult("updateReport", updateReportParameters);
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database database = (Database)commandScope.getDependency(Database.class);
        updateReportParameters.getDatabaseInfo().setDatabaseType(database.getDatabaseProductName());
        updateReportParameters.getDatabaseInfo().setVersion(database.getDatabaseProductVersion());
        updateReportParameters.getDatabaseInfo().setDatabaseUrl(database.getConnection().getURL());
        updateReportParameters.setJdbcUrl(database.getConnection().getURL());
        ChangeLogParameters changeLogParameters = (ChangeLogParameters)commandScope.getDependency(ChangeLogParameters.class);
        Contexts contexts = new Contexts(this.getContextsArg(commandScope));
        LabelExpression labelExpression = new LabelExpression(this.getLabelFilterArg(commandScope));
        updateReportParameters.getOperationInfo().setLabels(labelExpression.getOriginalString());
        updateReportParameters.getOperationInfo().setContexts(contexts.toString());
        DatabaseChangelogCommandStep.addCommandFiltersMdc(labelExpression, contexts);
        this.customMdcLogging(commandScope);
        ChangeExecListener changeExecListener = this.getChangeExecListener(resultsBuilder, commandScope);
        try {
            DatabaseChangeLog databaseChangeLog = (DatabaseChangeLog)commandScope.getDependency(DatabaseChangeLog.class);
            updateReportParameters.setChangelogArgValue(databaseChangeLog.getFilePath());
            ChangeLogIterator runChangeLogIterator = this.getStandardChangelogIterator(commandScope, database, contexts, labelExpression, databaseChangeLog);
            this.preRun(commandScope, runChangeLogIterator, changeLogParameters);
            if (this.isFastCheckEnabled && this.isUpToDate(commandScope, database, databaseChangeLog, contexts, labelExpression, resultsBuilder.getOutputStream())) {
                updateReportParameters.getOperationInfo().setRowsAffected(0);
                updateReportParameters.getOperationInfo().setUpdateSummaryMsg(DATABASE_UP_TO_DATE_MESSAGE);
                return;
            }
            if (!this.isDBLocked) {
                LockServiceFactory.getInstance().getLockService(database).waitForLock();
            }
            ChangeLogHistoryService changelogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
            changelogService.generateDeploymentId();
            Scope.getCurrentScope().addMdcValue("deploymentId", changelogService.getDeploymentId());
            Scope.getCurrentScope().getLog(this.getClass()).info(String.format("Using deploymentId: %s", changelogService.getDeploymentId()));
            StatusVisitor statusVisitor = this.getStatusVisitor(commandScope, database, contexts, labelExpression, databaseChangeLog);
            AtomicInteger rowsAffected = new AtomicInteger(0);
            HashMap<String, Object> scopeValues = new HashMap<String, Object>();
            scopeValues.put("showSummary", (Object)this.getShowSummary(commandScope));
            scopeValues.put("rowsAffected", rowsAffected);
            Scope.child(scopeValues, () -> {
                try {
                    runChangeLogIterator.run(new UpdateVisitor(database, changeExecListener, new ShouldRunChangeSetFilter(database)), new RuntimeEnvironment(database, contexts, labelExpression));
                }
                finally {
                    UpdateSummaryDetails details = ShowSummaryUtil.buildSummaryDetails(databaseChangeLog, this.getShowSummary(commandScope), this.getShowSummaryOutput(commandScope), statusVisitor, resultsBuilder.getOutputStream(), runChangeLogIterator, changeExecListener);
                    if (details != null) {
                        updateReportParameters.getOperationInfo().setUpdateSummaryMsg(details.getOutput());
                        updateReportParameters.getChangesetInfo().addAllToPendingChangesetInfoList(details.getSkipped());
                        updateReportParameters.getChangesetInfo().setPendingChangesetCount(updateReportParameters.getChangesetInfo().getPendingChangesetInfoList().size());
                    }
                }
            });
            updateReportParameters.getOperationInfo().setRowsAffected(rowsAffected.get());
            database.afterUpdate();
            resultsBuilder.addResult("statusCode", (Object)0);
            this.addChangelogFileToMdc(this.getChangelogFileArg(commandScope), databaseChangeLog);
            Scope.getCurrentScope().addMdcValue("rowsAffected", String.valueOf(rowsAffected.get()));
            this.logDeploymentOutcomeMdc(changeExecListener, true, updateReportParameters);
            this.postUpdateLog(rowsAffected.get());
        }
        catch (Exception e) {
            DatabaseChangeLog databaseChangeLog = (DatabaseChangeLog)commandScope.getDependency(DatabaseChangeLog.class);
            this.addChangelogFileToMdc(this.getChangelogFileArg(commandScope), databaseChangeLog);
            this.logDeploymentOutcomeMdc(changeExecListener, false, updateReportParameters);
            updateReportParameters.getOperationInfo().setException(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
            resultsBuilder.addResult("statusCode", (Object)1);
            throw e;
        }
        finally {
            try {
                LockServiceFactory.getInstance().getLockService(database).releaseLock();
            }
            catch (LockException e) {
                Scope.getCurrentScope().getLog(this.getClass()).severe(Liquibase.MSG_COULD_NOT_RELEASE_LOCK, e);
            }
        }
    }

    protected void preRun(CommandScope commandScope, ChangeLogIterator runChangeLogIterator, ChangeLogParameters changeLogParameters) throws LiquibaseException {
    }

    private StatusVisitor getStatusVisitor(CommandScope commandScope, Database database, Contexts contexts, LabelExpression labelExpression, DatabaseChangeLog databaseChangeLog) throws LiquibaseException {
        StatusVisitor statusVisitor = new StatusVisitor(database);
        ChangeLogIterator shouldRunIterator = this.getStatusChangelogIterator(commandScope, database, contexts, labelExpression, databaseChangeLog);
        shouldRunIterator.run(statusVisitor, new RuntimeEnvironment(database, contexts, labelExpression));
        return statusVisitor;
    }

    private ChangeExecListener getChangeExecListener(CommandResultsBuilder resultsBuilder, CommandScope commandScope) {
        ChangeExecListener changeExecListener = (ChangeExecListener)commandScope.getDependency(ChangeExecListener.class);
        if (changeExecListener instanceof DefaultChangeExecListener) {
            ((DefaultChangeExecListener)changeExecListener).reset();
        }
        resultsBuilder.addResult(DEFAULT_CHANGE_EXEC_LISTENER_RESULT_KEY, changeExecListener);
        return changeExecListener;
    }

    private void addChangelogFileToMdc(String changeLogFile, DatabaseChangeLog databaseChangeLog) {
        if (StringUtil.isNotEmpty(databaseChangeLog.getLogicalFilePath())) {
            Scope.getCurrentScope().addMdcValue("changelogFile", databaseChangeLog.getLogicalFilePath());
        } else if (StringUtil.isNotEmpty(changeLogFile)) {
            Scope.getCurrentScope().addMdcValue("changelogFile", changeLogFile);
        }
    }

    protected void customMdcLogging(CommandScope commandScope) {
    }

    @Override
    public void cleanUp(CommandResultsBuilder resultsBuilder) {
        LockServiceFactory.getInstance().resetAll();
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).resetAll();
        Scope.getCurrentScope().getSingleton(ExecutorService.class).reset();
    }

    private void logDeploymentOutcomeMdc(ChangeExecListener defaultListener, boolean success, UpdateReportParameters updateReportParameters) {
        String successLog = "Update command completed successfully.";
        String failureLog = "Update command encountered an exception.";
        if (defaultListener instanceof DefaultChangeExecListener) {
            List<ChangeSet> deployedChangeSets = ((DefaultChangeExecListener)defaultListener).getDeployedChangeSets();
            int deployedChangeSetCount = deployedChangeSets.size();
            List<ChangeSet> failedChangeSets = ((DefaultChangeExecListener)defaultListener).getFailedChangeSets();
            int failedChangeSetCount = failedChangeSets.size();
            ChangesetsUpdated changesetsUpdated = new ChangesetsUpdated(deployedChangeSets);
            updateReportParameters.getChangesetInfo().setChangesetCount(deployedChangeSetCount + failedChangeSetCount);
            updateReportParameters.getChangesetInfo().setFailedChangesetCount(failedChangeSetCount);
            updateReportParameters.getChangesetInfo().addAllToChangesetInfoList(deployedChangeSets, false);
            updateReportParameters.getChangesetInfo().addAllToChangesetInfoList(failedChangeSets, false);
            if (!updateReportParameters.getChangesetInfo().getPendingChangesetInfoList().isEmpty()) {
                updateReportParameters.getChangesetInfo().getPendingChangesetInfoList().removeIf(pendingChangesetInfo -> failedChangeSets.stream().anyMatch(changeSet -> changeSet.equals(pendingChangesetInfo.getChangeSet())));
                updateReportParameters.getChangesetInfo().setPendingChangesetCount(updateReportParameters.getChangesetInfo().getPendingChangesetCount() - failedChangeSetCount);
            }
            Scope.getCurrentScope().addMdcValue("deploymentOutcomeCount", String.valueOf(deployedChangeSetCount));
            Scope.getCurrentScope().addMdcValue("changesetsUpdated", changesetsUpdated);
            deployedChangeSets.forEach(changeSet -> changeSet.setGeneratedSql(new ArrayList<String>()));
        }
        String deploymentOutcome = success ? "success" : "fail";
        updateReportParameters.setSuccess(success);
        updateReportParameters.getOperationInfo().setOperationOutcome(deploymentOutcome);
        try (MdcObject deploymentOutcomeMdc = Scope.getCurrentScope().addMdcValue("deploymentOutcome", deploymentOutcome);){
            Scope.getCurrentScope().getLog(this.getClass()).info(success ? successLog : failureLog);
        }
    }

    @Beta
    public ChangeLogIterator getStandardChangelogIterator(CommandScope commandScope, Database database, Contexts contexts, LabelExpression labelExpression, DatabaseChangeLog changeLog) throws LiquibaseException {
        List<ChangeSetFilter> changesetFilters = this.getStandardChangelogIteratorFilters(database, contexts, labelExpression);
        return new ChangeLogIterator(changeLog, changesetFilters.toArray(new ChangeSetFilter[0]));
    }

    @Beta
    public ChangeLogIterator getStatusChangelogIterator(CommandScope commandScope, Database database, Contexts contexts, LabelExpression labelExpression, DatabaseChangeLog changeLog) throws LiquibaseException {
        List<ChangeSetFilter> changesetFilters = this.getStandardChangelogIteratorFilters(database, contexts, labelExpression);
        changesetFilters.add(new ShouldRunChangeSetFilter(database));
        return new StatusChangeLogIterator(changeLog, changesetFilters.toArray(new ChangeSetFilter[0]));
    }

    protected List<ChangeSetFilter> getStandardChangelogIteratorFilters(Database database, Contexts contexts, LabelExpression labelExpression) {
        return new ArrayList<ChangeSetFilter>(Arrays.asList(new ContextChangeSetFilter(contexts), new LabelChangeSetFilter(labelExpression), new DbmsChangeSetFilter(database), new IgnoreChangeSetFilter()));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isUpToDateFastCheck(CommandScope commandScope, Database database, DatabaseChangeLog databaseChangeLog, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        String cacheKey = String.format("%s/%s/%s/%s/%s/%s", contexts, labelExpression, database.getDefaultSchemaName(), database.getDefaultCatalogName(), database.getConnection().getURL(), databaseChangeLog.getLogicalFilePath());
        if (!this.upToDateFastCheck.containsKey(cacheKey)) {
            ChangeLogHistoryService changeLogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
            try {
                if (changeLogService.isDatabaseChecksumsCompatible() && this.listUnrunChangeSets(database, databaseChangeLog, contexts, labelExpression).isEmpty()) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Fast check found no un-run changesets");
                    this.upToDateFastCheck.put(cacheKey, true);
                } else {
                    this.upToDateFastCheck.put(cacheKey, false);
                }
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).info("Error querying Liquibase tables, disabling fast check for this execution. Reason: " + e.getMessage());
                this.upToDateFastCheck.put(cacheKey, false);
            }
            finally {
                changeLogService.reset();
            }
        }
        return this.upToDateFastCheck.get(cacheKey);
    }

    private List<ChangeSet> listUnrunChangeSets(Database database, DatabaseChangeLog databaseChangeLog, Contexts contexts, LabelExpression labels) throws LiquibaseException {
        ListVisitor visitor = new ListVisitor();
        databaseChangeLog.validate(database, contexts, labels);
        List<ChangeSetFilter> changesetFilters = this.getStandardChangelogIteratorFilters(database, contexts, labels);
        changesetFilters.add(new ShouldRunChangeSetFilter(database));
        ChangeLogIterator logIterator = new ChangeLogIterator(databaseChangeLog, changesetFilters.toArray(new ChangeSetFilter[0]));
        logIterator.run(visitor, new RuntimeEnvironment(database, contexts, labels));
        return visitor.getSeenChangeSets();
    }

    @Beta
    public boolean isUpToDate(CommandScope commandScope, Database database, DatabaseChangeLog databaseChangeLog, Contexts contexts, LabelExpression labelExpression, OutputStream outputStream2) throws LiquibaseException, IOException {
        if (this.isUpToDateFastCheck(commandScope, database, databaseChangeLog, contexts, labelExpression)) {
            Scope.getCurrentScope().getUI().sendMessage(DATABASE_UP_TO_DATE_MESSAGE);
            StatusVisitor statusVisitor = this.getStatusVisitor(commandScope, database, contexts, labelExpression, databaseChangeLog);
            UpdateSummaryEnum showSummary = this.getShowSummary(commandScope);
            UpdateSummaryOutputEnum showSummaryOutput = this.getShowSummaryOutput(commandScope);
            ShowSummaryUtil.showUpdateSummary(databaseChangeLog, showSummary, showSummaryOutput, statusVisitor, outputStream2, null);
            return true;
        }
        return false;
    }

    public void setFastCheckEnabled(boolean fastCheckEnabled) {
        this.isFastCheckEnabled = fastCheckEnabled;
    }

    @Beta
    public void postUpdateLog(int rowsAffected) {
    }

    protected void setDBLock(boolean locked) {
        this.isDBLocked = locked;
    }

    private String getFormattedCommandName(String[] commandName) {
        return Arrays.stream(commandName).filter(Objects::nonNull).map(camelCaseName -> StringUtil.join(StringUtil.splitCamelCase(camelCaseName), " ")).map(StringUtil::upperCaseFirst).collect(Collectors.joining(" "));
    }
}

