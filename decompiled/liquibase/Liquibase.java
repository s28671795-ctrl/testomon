/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import liquibase.CatalogAndSchema;
import liquibase.Contexts;
import liquibase.GlobalConfiguration;
import liquibase.LabelExpression;
import liquibase.RuntimeEnvironment;
import liquibase.Scope;
import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.change.CheckSum;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.ChangeSetStatus;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ContextChangeSetFilter;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.changelog.filter.IgnoreChangeSetFilter;
import liquibase.changelog.filter.LabelChangeSetFilter;
import liquibase.changelog.filter.ShouldRunChangeSetFilter;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.DefaultChangeExecListener;
import liquibase.changelog.visitor.ListVisitor;
import liquibase.changelog.visitor.StatusVisitor;
import liquibase.command.CommandResults;
import liquibase.command.CommandScope;
import liquibase.command.core.AbstractRollbackCommandStep;
import liquibase.command.core.CalculateChecksumCommandStep;
import liquibase.command.core.ChangelogSyncCommandStep;
import liquibase.command.core.ChangelogSyncSqlCommandStep;
import liquibase.command.core.ChangelogSyncToTagCommandStep;
import liquibase.command.core.ChangelogSyncToTagSqlCommandStep;
import liquibase.command.core.ClearChecksumsCommandStep;
import liquibase.command.core.DbDocCommandStep;
import liquibase.command.core.DropAllCommandStep;
import liquibase.command.core.FutureRollbackCountSqlCommandStep;
import liquibase.command.core.FutureRollbackFromTagSqlCommandStep;
import liquibase.command.core.FutureRollbackSqlCommandStep;
import liquibase.command.core.GenerateChangelogCommandStep;
import liquibase.command.core.ListLocksCommandStep;
import liquibase.command.core.MarkNextChangesetRanCommandStep;
import liquibase.command.core.MarkNextChangesetRanSqlCommandStep;
import liquibase.command.core.ReleaseLocksCommandStep;
import liquibase.command.core.RollbackCommandStep;
import liquibase.command.core.RollbackCountCommandStep;
import liquibase.command.core.RollbackCountSqlCommandStep;
import liquibase.command.core.RollbackSqlCommandStep;
import liquibase.command.core.RollbackToDateCommandStep;
import liquibase.command.core.RollbackToDateSqlCommandStep;
import liquibase.command.core.StatusCommandStep;
import liquibase.command.core.TagCommandStep;
import liquibase.command.core.TagExistsCommandStep;
import liquibase.command.core.UnexpectedChangesetsCommandStep;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.UpdateCountCommandStep;
import liquibase.command.core.UpdateSqlCommandStep;
import liquibase.command.core.UpdateTestingRollbackCommandStep;
import liquibase.command.core.UpdateToTagCommandStep;
import liquibase.command.core.helpers.ChangeExecListenerCommandStep;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.command.core.helpers.PreCompareCommandStep;
import liquibase.command.core.helpers.ShowSummaryArgument;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.diff.DiffGeneratorFactory;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.LoggingExecutor;
import liquibase.io.WriterOutputStream;
import liquibase.lockservice.DatabaseChangeLogLock;
import liquibase.lockservice.LockServiceFactory;
import liquibase.logging.Logger;
import liquibase.parser.ChangeLogParser;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.parser.core.xml.XMLChangeLogSAXParser;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.structure.DatabaseObject;
import liquibase.util.LoggingExecutorTextUtil;
import liquibase.util.StringUtil;

public class Liquibase
implements AutoCloseable {
    private static final Logger LOG = Scope.getCurrentScope().getLog(Liquibase.class);
    private static final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    public static final String MSG_COULD_NOT_RELEASE_LOCK = coreBundle.getString("could.not.release.lock");
    protected Database database;
    private DatabaseChangeLog databaseChangeLog;
    private String changeLogFile;
    private UpdateSummaryOutputEnum showSummaryOutput;
    private UpdateSummaryEnum showSummary;
    private final ResourceAccessor resourceAccessor;
    private final ChangeLogParameters changeLogParameters;
    private ChangeExecListener changeExecListener;
    private final DefaultChangeExecListener defaultChangeExecListener = new DefaultChangeExecListener(new ChangeExecListener[0]);
    private final Map<String, Boolean> upToDateFastCheck = new HashMap<String, Boolean>();

    public Liquibase(String changeLogFile, ResourceAccessor resourceAccessor, DatabaseConnection conn) throws LiquibaseException {
        this(changeLogFile, resourceAccessor, DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn));
    }

    public Liquibase(String changeLogFile, ResourceAccessor resourceAccessor, Database database) {
        if (changeLogFile != null) {
            this.changeLogFile = changeLogFile.replace('\\', '/');
        }
        this.resourceAccessor = resourceAccessor;
        this.changeLogParameters = new ChangeLogParameters(database);
        this.database = database;
    }

    public Liquibase(DatabaseChangeLog changeLog, ResourceAccessor resourceAccessor, Database database) {
        this.databaseChangeLog = changeLog;
        if (changeLog != null) {
            this.changeLogFile = changeLog.getPhysicalFilePath();
        }
        if (this.changeLogFile != null) {
            this.changeLogFile = this.changeLogFile.replace('\\', '/');
        }
        this.resourceAccessor = resourceAccessor;
        this.database = database;
        this.changeLogParameters = new ChangeLogParameters(database);
    }

    public String getChangeLogFile() {
        return this.changeLogFile;
    }

    public Logger getLog() {
        return LOG;
    }

    public ChangeLogParameters getChangeLogParameters() {
        return this.changeLogParameters;
    }

    public Database getDatabase() {
        return this.database;
    }

    public ResourceAccessor getResourceAccessor() {
        return this.resourceAccessor;
    }

    @Deprecated
    public void update() throws LiquibaseException {
        this.update(new Contexts());
    }

    @Deprecated
    public void update(String contexts) throws LiquibaseException {
        this.update(new Contexts(contexts));
    }

    @Deprecated
    public void update(Contexts contexts) throws LiquibaseException {
        this.update(contexts, new LabelExpression());
    }

    @Deprecated
    public void update(Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.update(contexts, labelExpression, true);
    }

    @Deprecated
    public void update(Contexts contexts, LabelExpression labelExpression, boolean checkLiquibaseTables) throws LiquibaseException {
        this.runInScope(() -> {
            CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase());
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_ARG, this.databaseChangeLog);
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
            updateCommand.addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null);
            updateCommand.addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null);
            updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY_OUTPUT, this.showSummaryOutput);
            updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY, this.showSummary);
            updateCommand.execute();
        });
    }

    @Deprecated
    protected boolean isUpToDateFastCheck(Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        return new UpdateCommandStep().isUpToDateFastCheck(null, this.database, this.databaseChangeLog, contexts, labelExpression);
    }

    public DatabaseChangeLog getDatabaseChangeLog() throws LiquibaseException {
        return this.getDatabaseChangeLog(false);
    }

    private DatabaseChangeLog getDatabaseChangeLog(boolean shouldWarnOnMismatchedXsdVersion) throws LiquibaseException {
        if (this.databaseChangeLog == null && this.changeLogFile != null) {
            ChangeLogParser parser = ChangeLogParserFactory.getInstance().getParser(this.changeLogFile, this.resourceAccessor);
            if (parser instanceof XMLChangeLogSAXParser) {
                ((XMLChangeLogSAXParser)parser).setShouldWarnOnMismatchedXsdVersion(shouldWarnOnMismatchedXsdVersion);
            }
            this.databaseChangeLog = parser.parse(this.changeLogFile, this.changeLogParameters, this.resourceAccessor);
            Scope.getCurrentScope().getLog(Liquibase.class).info("Parsed changelog file '" + this.changeLogFile + "'");
            if (StringUtil.isNotEmpty(this.databaseChangeLog.getLogicalFilePath())) {
                Scope.getCurrentScope().addMdcValue("changelogFile", this.databaseChangeLog.getLogicalFilePath());
            } else {
                Scope.getCurrentScope().addMdcValue("changelogFile", this.changeLogFile);
            }
        }
        return this.databaseChangeLog;
    }

    protected ChangeLogIterator getStandardChangelogIterator(Contexts contexts, LabelExpression labelExpression, DatabaseChangeLog changeLog) throws DatabaseException {
        return new ChangeLogIterator(changeLog, new ShouldRunChangeSetFilter(this.database), new ContextChangeSetFilter(contexts), new LabelChangeSetFilter(labelExpression), new DbmsChangeSetFilter(this.database), new IgnoreChangeSetFilter());
    }

    @Deprecated
    public void update(String contexts, Writer output) throws LiquibaseException {
        this.update(new Contexts(contexts), output);
    }

    @Deprecated
    public void update(Contexts contexts, Writer output) throws LiquibaseException {
        this.update(contexts, new LabelExpression(), output);
    }

    @Deprecated
    public void update(Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.update(contexts, labelExpression, output, true);
    }

    @Deprecated
    public void update(Contexts contexts, LabelExpression labelExpression, Writer output, boolean checkLiquibaseTables) throws LiquibaseException {
        this.runInScope(() -> {
            CommandScope updateCommand = new CommandScope(UpdateSqlCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase());
            updateCommand.addArgumentValue(UpdateSqlCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
            updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_ARG, this.databaseChangeLog);
            updateCommand.addArgumentValue(UpdateSqlCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null);
            updateCommand.addArgumentValue(UpdateSqlCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null);
            updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener);
            updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters);
            updateCommand.setOutput(new WriterOutputStream(output, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
            updateCommand.execute();
        });
    }

    @Deprecated
    public void update(int changesToApply, String contexts) throws LiquibaseException {
        this.update(changesToApply, new Contexts(contexts), new LabelExpression());
    }

    @Deprecated
    public void update(int changesToApply, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.runInScope(() -> {
            CommandScope updateCommand = new CommandScope(UpdateCountCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase());
            updateCommand.addArgumentValue(UpdateCountCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
            updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_ARG, this.databaseChangeLog);
            updateCommand.addArgumentValue(UpdateCountCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null);
            updateCommand.addArgumentValue(UpdateCountCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null);
            updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener);
            updateCommand.addArgumentValue(UpdateCountCommandStep.COUNT_ARG, changesToApply);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY_OUTPUT, this.showSummaryOutput);
            updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY, this.showSummary);
            updateCommand.execute();
        });
    }

    @Deprecated
    public void update(String tag, String contexts) throws LiquibaseException {
        this.update(tag, new Contexts(contexts), new LabelExpression());
    }

    @Deprecated
    public void update(String tag, Contexts contexts) throws LiquibaseException {
        this.update(tag, contexts, new LabelExpression());
    }

    @Deprecated
    public void update(String tag, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        if (tag == null) {
            this.update(contexts, labelExpression);
            return;
        }
        this.runInScope(() -> {
            CommandScope updateCommand = new CommandScope(UpdateToTagCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase());
            updateCommand.addArgumentValue(UpdateToTagCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
            updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_ARG, this.databaseChangeLog);
            updateCommand.addArgumentValue(UpdateToTagCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null);
            updateCommand.addArgumentValue(UpdateToTagCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null);
            updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener);
            updateCommand.addArgumentValue(UpdateToTagCommandStep.TAG_ARG, tag);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY_OUTPUT, this.showSummaryOutput);
            updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY, this.showSummary);
            updateCommand.execute();
        });
    }

    @Deprecated
    public void update(int changesToApply, String contexts, Writer output) throws LiquibaseException {
        this.update(changesToApply, new Contexts(contexts), new LabelExpression(), output);
    }

    @Deprecated
    public void update(int changesToApply, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.changeLogParameters.setContexts(contexts);
        this.changeLogParameters.setLabels(labelExpression);
        this.runInScope(() -> {
            Executor oldTemplate = this.getAndReplaceJdbcExecutor(output);
            this.outputHeader("Update " + changesToApply + " Changesets Database Script");
            this.update(changesToApply, contexts, labelExpression);
            this.flushOutputWriter(output);
            this.resetServices();
            Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("jdbc", this.database, oldTemplate);
        });
    }

    @Deprecated
    public void update(String tag, String contexts, Writer output) throws LiquibaseException {
        this.update(tag, new Contexts(contexts), new LabelExpression(), output);
    }

    @Deprecated
    public void update(String tag, Contexts contexts, Writer output) throws LiquibaseException {
        this.update(tag, contexts, new LabelExpression(), output);
    }

    @Deprecated
    public void update(String tag, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        if (tag == null) {
            this.update(contexts, labelExpression, output);
            return;
        }
        this.changeLogParameters.setContexts(contexts);
        this.changeLogParameters.setLabels(labelExpression);
        this.runInScope(() -> {
            Executor oldTemplate = this.getAndReplaceJdbcExecutor(output);
            this.outputHeader("Update to '" + tag + "' Database Script");
            this.update(tag, contexts, labelExpression);
            this.flushOutputWriter(output);
            this.resetServices();
            Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("jdbc", this.database, oldTemplate);
        });
    }

    private void addCommandFiltersMdc(LabelExpression labelExpression, Contexts contexts) {
        String labelFilterMdc = labelExpression != null && labelExpression.getOriginalString() != null ? labelExpression.getOriginalString() : "";
        String contextFilterMdc = contexts != null ? contexts.toString() : "";
        Scope.getCurrentScope().addMdcValue("commandLabelFilter", labelFilterMdc);
        Scope.getCurrentScope().addMdcValue("commandContextFilter", contextFilterMdc);
    }

    @Deprecated
    public void outputHeader(String message) throws DatabaseException {
        LoggingExecutorTextUtil.outputHeader(message, this.database, this.changeLogFile);
    }

    public void rollback(int changesToRollback, String contexts, Writer output) throws LiquibaseException {
        this.rollback(changesToRollback, null, contexts, output);
    }

    public void rollback(int changesToRollback, Contexts contexts, Writer output) throws LiquibaseException {
        this.rollback(changesToRollback, null, contexts, output);
    }

    public void rollback(int changesToRollback, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.rollback(changesToRollback, null, contexts, labelExpression, output);
    }

    public void rollback(int changesToRollback, String rollbackScript, String contexts, Writer output) throws LiquibaseException {
        this.rollback(changesToRollback, rollbackScript, new Contexts(contexts), output);
    }

    public void rollback(int changesToRollback, String rollbackScript, Contexts contexts, Writer output) throws LiquibaseException {
        this.rollback(changesToRollback, rollbackScript, contexts, new LabelExpression(), output);
    }

    public void rollback(int changesToRollback, String rollbackScript, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.runInScope(() -> new CommandScope(RollbackCountSqlCommandStep.COMMAND_NAME).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_ARG, this.databaseChangeLog).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener).addArgumentValue(RollbackCountCommandStep.COUNT_ARG, changesToRollback).addArgumentValue(AbstractRollbackCommandStep.ROLLBACK_SCRIPT_ARG, rollbackScript).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).setOutput(new WriterOutputStream(output, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue())).execute());
    }

    public void rollback(int changesToRollback, String contexts) throws LiquibaseException {
        this.rollback(changesToRollback, null, contexts);
    }

    public void rollback(int changesToRollback, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.rollback(changesToRollback, null, contexts, labelExpression);
    }

    public void rollback(int changesToRollback, String rollbackScript, String contexts) throws LiquibaseException {
        this.rollback(changesToRollback, rollbackScript, new Contexts(contexts), new LabelExpression());
    }

    public void rollback(int changesToRollback, String rollbackScript, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.runInScope(() -> new CommandScope(RollbackCountCommandStep.COMMAND_NAME).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_ARG, this.databaseChangeLog).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener).addArgumentValue(RollbackCountCommandStep.COUNT_ARG, changesToRollback).addArgumentValue(AbstractRollbackCommandStep.ROLLBACK_SCRIPT_ARG, rollbackScript).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).execute());
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, String contexts, Writer output) throws LiquibaseException {
        this.rollback(tagToRollBackTo, null, contexts, output);
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, Contexts contexts, Writer output) throws LiquibaseException {
        this.rollback(tagToRollBackTo, null, contexts, output);
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.rollback(tagToRollBackTo, null, contexts, labelExpression, output);
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, String rollbackScript, String contexts, Writer output) throws LiquibaseException {
        this.rollback(tagToRollBackTo, rollbackScript, new Contexts(contexts), output);
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, String rollbackScript, Contexts contexts, Writer output) throws LiquibaseException {
        this.rollback(tagToRollBackTo, rollbackScript, contexts, new LabelExpression(), output);
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, String rollbackScript, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.runInScope(() -> new CommandScope(RollbackSqlCommandStep.COMMAND_NAME).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_ARG, this.databaseChangeLog).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener).addArgumentValue(RollbackCommandStep.TAG_ARG, tagToRollBackTo).addArgumentValue(AbstractRollbackCommandStep.ROLLBACK_SCRIPT_ARG, rollbackScript).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).setOutput(new WriterOutputStream(output, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue())).execute());
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, String contexts) throws LiquibaseException {
        this.rollback(tagToRollBackTo, null, contexts);
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, Contexts contexts) throws LiquibaseException {
        this.rollback(tagToRollBackTo, null, contexts);
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.rollback(tagToRollBackTo, null, contexts, labelExpression);
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, String rollbackScript, String contexts) throws LiquibaseException {
        this.rollback(tagToRollBackTo, rollbackScript, new Contexts(contexts));
    }

    @Deprecated
    public void rollback(String tagToRollBackTo, String rollbackScript, Contexts contexts) throws LiquibaseException {
        this.rollback(tagToRollBackTo, rollbackScript, contexts, new LabelExpression());
    }

    public void rollback(String tagToRollBackTo, String rollbackScript, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.runInScope(() -> new CommandScope(RollbackCommandStep.COMMAND_NAME).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_ARG, this.databaseChangeLog).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener).addArgumentValue(RollbackCommandStep.TAG_ARG, tagToRollBackTo).addArgumentValue(RollbackCommandStep.ROLLBACK_SCRIPT_ARG, rollbackScript).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).execute());
    }

    public void rollback(Date dateToRollBackTo, String contexts, Writer output) throws LiquibaseException {
        this.rollback(dateToRollBackTo, null, contexts, output);
    }

    public void rollback(Date dateToRollBackTo, String rollbackScript, String contexts, Writer output) throws LiquibaseException {
        this.rollback(dateToRollBackTo, new Contexts(contexts), new LabelExpression(), output);
    }

    public void rollback(Date dateToRollBackTo, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.rollback(dateToRollBackTo, null, contexts, labelExpression, output);
    }

    public void rollback(Date dateToRollBackTo, String rollbackScript, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.runInScope(() -> new CommandScope(RollbackToDateSqlCommandStep.COMMAND_NAME).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_ARG, this.databaseChangeLog).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener).addArgumentValue(RollbackToDateCommandStep.DATE_ARG, dateToRollBackTo).addArgumentValue(AbstractRollbackCommandStep.ROLLBACK_SCRIPT_ARG, rollbackScript).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).setOutput(new WriterOutputStream(output, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue())).execute());
    }

    public void rollback(Date dateToRollBackTo, String contexts) throws LiquibaseException {
        this.rollback(dateToRollBackTo, null, contexts);
    }

    public void rollback(Date dateToRollBackTo, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.rollback(dateToRollBackTo, null, contexts, labelExpression);
    }

    public void rollback(Date dateToRollBackTo, String rollbackScript, String contexts) throws LiquibaseException {
        this.rollback(dateToRollBackTo, new Contexts(contexts), new LabelExpression());
    }

    public void rollback(Date dateToRollBackTo, String rollbackScript, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.changeLogParameters.setContexts(contexts);
        this.changeLogParameters.setLabels(labelExpression);
        this.addCommandFiltersMdc(labelExpression, contexts);
        this.runInScope(() -> new CommandScope(RollbackToDateCommandStep.COMMAND_NAME).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_ARG, this.databaseChangeLog).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener).addArgumentValue(RollbackToDateCommandStep.DATE_ARG, dateToRollBackTo).addArgumentValue(AbstractRollbackCommandStep.ROLLBACK_SCRIPT_ARG, rollbackScript).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).execute());
    }

    private Executor getAndReplaceJdbcExecutor(Writer output) {
        Executor oldTemplate = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.database);
        LoggingExecutor loggingExecutor = new LoggingExecutor(oldTemplate, output, this.database);
        Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("logging", this.database, loggingExecutor);
        Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("jdbc", this.database, loggingExecutor);
        return oldTemplate;
    }

    public void changeLogSync(String contexts, Writer output) throws LiquibaseException {
        this.changeLogSync(new Contexts(contexts), new LabelExpression(), output);
    }

    public void changeLogSync(Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.doChangeLogSyncSql(null, contexts, labelExpression, output, () -> "SQL to add all changesets to database history table");
    }

    private void flushOutputWriter(Writer output) throws LiquibaseException {
        if (output == null) {
            return;
        }
        try {
            output.flush();
        }
        catch (IOException e) {
            throw new LiquibaseException(e);
        }
    }

    public void changeLogSync(String contexts) throws LiquibaseException {
        this.changeLogSync(new Contexts(contexts), new LabelExpression());
    }

    @Deprecated
    public void changeLogSync(Contexts contexts) throws LiquibaseException {
        this.changeLogSync(contexts, new LabelExpression());
    }

    public void changeLogSync(Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.changeLogSync(null, contexts, labelExpression);
    }

    public void changeLogSync(String tag, String contexts) throws LiquibaseException {
        this.changeLogSync(tag, new Contexts(contexts), new LabelExpression());
    }

    public void changeLogSync(String tag, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        String commandToRun = StringUtil.isEmpty(tag) ? ChangelogSyncCommandStep.COMMAND_NAME[0] : ChangelogSyncToTagCommandStep.COMMAND_NAME[0];
        this.runInScope(() -> new CommandScope(commandToRun).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(ChangelogSyncToTagCommandStep.TAG_ARG, tag).execute());
    }

    public void changeLogSync(String tag, String contexts, Writer output) throws LiquibaseException {
        this.changeLogSync(tag, new Contexts(contexts), new LabelExpression(), output);
    }

    public void changeLogSync(String tag, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.doChangeLogSyncSql(tag, contexts, labelExpression, output, () -> "SQL to add changesets upto '" + tag + "' to database history table");
    }

    private void doChangeLogSyncSql(String tag, Contexts contexts, LabelExpression labelExpression, Writer output, Supplier<String> header) throws LiquibaseException {
        String commandToRun = StringUtil.isEmpty(tag) ? ChangelogSyncSqlCommandStep.COMMAND_NAME[0] : ChangelogSyncToTagSqlCommandStep.COMMAND_NAME[0];
        this.runInScope(() -> new CommandScope(commandToRun).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(ChangelogSyncToTagSqlCommandStep.TAG_ARG, tag).setOutput(new WriterOutputStream(output, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue())).execute());
    }

    @Deprecated
    public void markNextChangeSetRan(String contexts, Writer output) throws LiquibaseException {
        this.markNextChangeSetRan(new Contexts(contexts), new LabelExpression(), output);
    }

    @Deprecated
    public void markNextChangeSetRan(Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.runInScope(() -> new CommandScope(MarkNextChangesetRanSqlCommandStep.COMMAND_NAME).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).setOutput(new WriterOutputStream(output, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue())).execute());
    }

    @Deprecated
    public void markNextChangeSetRan(String contexts) throws LiquibaseException {
        this.markNextChangeSetRan(new Contexts(contexts), new LabelExpression());
    }

    @Deprecated
    public void markNextChangeSetRan(Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.runInScope(() -> new CommandScope(MarkNextChangesetRanCommandStep.COMMAND_NAME).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).execute());
    }

    @Deprecated
    public void futureRollbackSQL(String contexts, Writer output) throws LiquibaseException {
        this.futureRollbackSQL(null, contexts, output, true);
    }

    @Deprecated
    public void futureRollbackSQL(Writer output) throws LiquibaseException {
        this.futureRollbackSQL(null, null, new Contexts(), new LabelExpression(), output);
    }

    @Deprecated
    public void futureRollbackSQL(String contexts, Writer output, boolean checkLiquibaseTables) throws LiquibaseException {
        this.futureRollbackSQL(null, contexts, output, checkLiquibaseTables);
    }

    @Deprecated
    public void futureRollbackSQL(Integer count, String contexts, Writer output) throws LiquibaseException {
        this.futureRollbackSQL(count, new Contexts(contexts), new LabelExpression(), output, true);
    }

    @Deprecated
    public void futureRollbackSQL(Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.futureRollbackSQL(null, null, contexts, labelExpression, output);
    }

    @Deprecated
    public void futureRollbackSQL(Integer count, String contexts, Writer output, boolean checkLiquibaseTables) throws LiquibaseException {
        this.futureRollbackSQL(count, new Contexts(contexts), new LabelExpression(), output, checkLiquibaseTables);
    }

    @Deprecated
    public void futureRollbackSQL(Integer count, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.futureRollbackSQL(count, contexts, labelExpression, output, true);
    }

    @Deprecated
    public void futureRollbackSQL(Integer count, Contexts contexts, LabelExpression labelExpression, Writer output, boolean checkLiquibaseTables) throws LiquibaseException {
        this.futureRollbackSQL(count, null, contexts, labelExpression, output);
    }

    @Deprecated
    public void futureRollbackSQL(String tag, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.futureRollbackSQL(null, tag, contexts, labelExpression, output);
    }

    @Deprecated
    protected void futureRollbackSQL(Integer count, String tag, Contexts contexts, LabelExpression labelExpression, Writer output) throws LiquibaseException {
        this.futureRollbackSQL(count, tag, contexts, labelExpression, output, true);
    }

    @Deprecated
    protected void futureRollbackSQL(Integer count, String tag, Contexts contexts, LabelExpression labelExpression, Writer output, boolean checkLiquibaseTables) throws LiquibaseException {
        CommandScope commandScope;
        if (count == null && tag == null) {
            commandScope = new CommandScope(FutureRollbackSqlCommandStep.COMMAND_NAME);
        } else if (count != null) {
            commandScope = new CommandScope(FutureRollbackCountSqlCommandStep.COMMAND_NAME);
            commandScope.addArgumentValue(FutureRollbackCountSqlCommandStep.COUNT_ARG, count);
        } else {
            commandScope = new CommandScope(FutureRollbackFromTagSqlCommandStep.COMMAND_NAME);
            commandScope.addArgumentValue(FutureRollbackFromTagSqlCommandStep.TAG_ARG, tag);
        }
        commandScope.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener).setOutput(new WriterOutputStream(output, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
        this.runInScope(commandScope::execute);
    }

    protected void resetServices() {
        LockServiceFactory.getInstance().resetAll();
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).resetAll();
        Scope.getCurrentScope().getSingleton(ExecutorService.class).reset();
    }

    public final void dropAll() throws DatabaseException {
        this.dropAll(new CatalogAndSchema(this.getDatabase().getDefaultCatalogName(), this.getDatabase().getDefaultSchemaName()));
    }

    public final void dropAll(Boolean dropDbclhistory) throws DatabaseException {
        this.dropAll(dropDbclhistory, new CatalogAndSchema(this.getDatabase().getDefaultCatalogName(), this.getDatabase().getDefaultSchemaName()));
    }

    public final void dropAll(CatalogAndSchema ... schemas) throws DatabaseException {
        this.dropAll((Boolean)null, schemas);
    }

    public final void dropAll(Boolean dropDbclhistory, CatalogAndSchema ... schemas) throws DatabaseException {
        CatalogAndSchema[] finalSchemas = schemas;
        try {
            CommandScope dropAll = new CommandScope("dropAll").addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DropAllCommandStep.CATALOG_AND_SCHEMAS_ARG, finalSchemas).addArgumentValue("dropDbclhistory", dropDbclhistory);
            try {
                dropAll.execute();
            }
            catch (CommandExecutionException e) {
                throw new DatabaseException(e);
            }
        }
        catch (LiquibaseException e) {
            if (e instanceof DatabaseException) {
                throw (DatabaseException)e;
            }
            throw new DatabaseException(e);
        }
    }

    @Deprecated
    public void tag(String tagString) throws LiquibaseException {
        new CommandScope("tag").addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.database).addArgumentValue(TagCommandStep.TAG_ARG, tagString).execute();
    }

    @Deprecated
    public boolean tagExists(String tagString) throws LiquibaseException {
        CommandResults commandResults = new CommandScope("tagExists").addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.database).addArgumentValue(TagExistsCommandStep.TAG_ARG, tagString).execute();
        return commandResults.getResult(TagExistsCommandStep.TAG_EXISTS_RESULT);
    }

    @Deprecated
    public void updateTestingRollback(String contexts) throws LiquibaseException {
        this.updateTestingRollback(new Contexts(contexts), new LabelExpression());
    }

    @Deprecated
    public void updateTestingRollback(Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.updateTestingRollback(null, contexts, labelExpression);
    }

    @Deprecated
    public void updateTestingRollback(String tag, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        this.runInScope(() -> {
            CommandScope updateTestingRollback = new CommandScope(UpdateTestingRollbackCommandStep.COMMAND_NAME);
            updateTestingRollback.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase());
            updateTestingRollback.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters);
            updateTestingRollback.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
            updateTestingRollback.addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null);
            updateTestingRollback.addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null);
            updateTestingRollback.addArgumentValue(UpdateTestingRollbackCommandStep.TAG_ARG, tag);
            updateTestingRollback.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, this.changeExecListener);
            updateTestingRollback.execute();
        });
    }

    public void checkLiquibaseTables(boolean updateExistingNullChecksums, DatabaseChangeLog databaseChangeLog, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        ChangeLogHistoryService changeLogHistoryService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this.getDatabase());
        changeLogHistoryService.init();
        if (updateExistingNullChecksums) {
            changeLogHistoryService.upgradeChecksums(databaseChangeLog, contexts, labelExpression);
        }
        LockServiceFactory.getInstance().getLockService(this.getDatabase()).init();
    }

    public boolean isSafeToRunUpdate() throws DatabaseException {
        return this.getDatabase().isSafeToRunUpdate();
    }

    @Deprecated
    public DatabaseChangeLogLock[] listLocks() throws LiquibaseException {
        return ListLocksCommandStep.listLocks(this.database);
    }

    @Deprecated
    public void reportLocks(PrintStream out) throws LiquibaseException {
        this.runInScope(() -> {
            CommandScope listLocksCommand = new CommandScope(ListLocksCommandStep.COMMAND_NAME);
            listLocksCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase());
            listLocksCommand.setOutput(out);
            listLocksCommand.execute();
        });
    }

    public void forceReleaseLocks() throws LiquibaseException {
        new CommandScope(ReleaseLocksCommandStep.COMMAND_NAME[0]).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).execute();
    }

    @Deprecated
    public List<ChangeSet> listUnrunChangeSets(Contexts contexts) throws LiquibaseException {
        return this.listUnrunChangeSets(contexts, new LabelExpression());
    }

    @Deprecated
    public List<ChangeSet> listUnrunChangeSets(Contexts contexts, LabelExpression labels) throws LiquibaseException {
        return this.listUnrunChangeSets(contexts, labels, true);
    }

    @Deprecated
    public List<ChangeSet> listUnrunChangeSets(Contexts contexts, LabelExpression labels, boolean checkLiquibaseTables) throws LiquibaseException {
        this.changeLogParameters.setContexts(contexts);
        this.changeLogParameters.setLabels(labels);
        ListVisitor visitor = new ListVisitor();
        this.runInScope(() -> {
            DatabaseChangeLog changeLog = this.getDatabaseChangeLog();
            if (checkLiquibaseTables) {
                this.checkLiquibaseTables(false, changeLog, contexts, labels);
            }
            changeLog.validate(this.database, contexts, labels);
            ChangeLogIterator logIterator = this.getStandardChangelogIterator(contexts, labels, changeLog);
            logIterator.run(visitor, new RuntimeEnvironment(this.database, contexts, labels));
        });
        return visitor.getSeenChangeSets();
    }

    @Deprecated
    public List<ChangeSetStatus> getChangeSetStatuses(Contexts contexts) throws LiquibaseException {
        return this.getChangeSetStatuses(contexts, new LabelExpression());
    }

    public List<ChangeSetStatus> getChangeSetStatuses(Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        return this.getChangeSetStatuses(contexts, labelExpression, true);
    }

    public List<ChangeSetStatus> getChangeSetStatuses(Contexts contexts, LabelExpression labelExpression, boolean checkLiquibaseTables) throws LiquibaseException {
        this.changeLogParameters.setContexts(contexts);
        this.changeLogParameters.setLabels(labelExpression);
        StatusVisitor visitor = new StatusVisitor(this.database);
        this.runInScope(() -> {
            DatabaseChangeLog changeLog = this.getDatabaseChangeLog();
            if (checkLiquibaseTables) {
                this.checkLiquibaseTables(false, changeLog, contexts, labelExpression);
            }
            changeLog.validate(this.database, contexts, labelExpression);
            ChangeLogIterator logIterator = this.getStandardChangelogIterator(contexts, labelExpression, changeLog);
            logIterator.run(visitor, new RuntimeEnvironment(this.database, contexts, labelExpression));
        });
        return visitor.getStatuses();
    }

    @Deprecated
    public void reportStatus(boolean verbose, String contexts, Writer out) throws LiquibaseException {
        this.reportStatus(verbose, new Contexts(contexts), new LabelExpression(), out);
    }

    @Deprecated
    public void reportStatus(boolean verbose, Contexts contexts, Writer out) throws LiquibaseException {
        this.reportStatus(verbose, contexts, new LabelExpression(), out);
    }

    @Deprecated
    public void reportStatus(boolean verbose, Contexts contexts, LabelExpression labels, Writer out) throws LiquibaseException {
        this.changeLogParameters.setContexts(contexts);
        this.changeLogParameters.setLabels(labels);
        this.runInScope(() -> {
            CommandScope statusCommand = new CommandScope(StatusCommandStep.COMMAND_NAME);
            statusCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase());
            statusCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters);
            statusCommand.addArgumentValue(StatusCommandStep.VERBOSE_ARG, verbose);
            statusCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
            statusCommand.setOutput(new WriterOutputStream(out, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
            statusCommand.execute();
        });
    }

    @Deprecated
    public Collection<RanChangeSet> listUnexpectedChangeSets(String contexts) throws LiquibaseException {
        return this.listUnexpectedChangeSets(new Contexts(contexts), new LabelExpression());
    }

    @Deprecated
    public Collection<RanChangeSet> listUnexpectedChangeSets(Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        return UnexpectedChangesetsCommandStep.listUnexpectedChangeSets(this.getDatabase(), this.getDatabaseChangeLog(), contexts, labelExpression);
    }

    @Deprecated
    public void reportUnexpectedChangeSets(boolean verbose, String contexts, Writer out) throws LiquibaseException {
        this.reportUnexpectedChangeSets(verbose, new Contexts(contexts), new LabelExpression(), out);
    }

    @Deprecated
    public void reportUnexpectedChangeSets(boolean verbose, Contexts contexts, LabelExpression labelExpression, Writer out) throws LiquibaseException {
        this.changeLogParameters.setContexts(contexts);
        this.changeLogParameters.setLabels(labelExpression);
        this.runInScope(() -> {
            CommandScope unexpectedChangesetsCommand = new CommandScope(UnexpectedChangesetsCommandStep.COMMAND_NAME);
            unexpectedChangesetsCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase());
            unexpectedChangesetsCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters);
            unexpectedChangesetsCommand.addArgumentValue(UnexpectedChangesetsCommandStep.VERBOSE_ARG, verbose);
            unexpectedChangesetsCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
            unexpectedChangesetsCommand.addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null);
            unexpectedChangesetsCommand.addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null);
            unexpectedChangesetsCommand.setOutput(new WriterOutputStream(out, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
            unexpectedChangesetsCommand.execute();
        });
    }

    @Deprecated
    public void clearCheckSums() throws LiquibaseException {
        CommandResults commandResults = new CommandScope(ClearChecksumsCommandStep.COMMAND_NAME).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.database).addArgumentValue(DbUrlConnectionArgumentsCommandStep.URL_ARG, this.database.getConnection().getURL()).execute();
    }

    @Deprecated
    public final CheckSum calculateCheckSum(String changeSetIdentifier) throws LiquibaseException {
        String[] changeSetAttributes = changeSetIdentifier.split("::");
        return this.calculateCheckSum(changeSetAttributes[0], changeSetAttributes[1], changeSetAttributes[2]);
    }

    public CheckSum calculateCheckSum(String changeSetPath, String changeSetId, String changeSetAuthor) throws LiquibaseException {
        CommandResults commandResults = new CommandScope("calculateChecksum").addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.database).addArgumentValue(CalculateChecksumCommandStep.CHANGESET_PATH_ARG, changeSetPath).addArgumentValue(CalculateChecksumCommandStep.CHANGESET_ID_ARG, changeSetId).addArgumentValue(CalculateChecksumCommandStep.CHANGESET_AUTHOR_ARG, changeSetAuthor).addArgumentValue(CalculateChecksumCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).execute();
        return commandResults.getResult(CalculateChecksumCommandStep.CHECKSUM_RESULT);
    }

    @Deprecated
    public void generateDocumentation(String outputDirectory) throws LiquibaseException {
        this.generateDocumentation(outputDirectory, new Contexts(), new LabelExpression(), new CatalogAndSchema(null, null));
    }

    @Deprecated
    public void generateDocumentation(String outputDirectory, String contexts) throws LiquibaseException {
        this.generateDocumentation(outputDirectory, new Contexts(contexts), new LabelExpression(), new CatalogAndSchema(null, null));
    }

    @Deprecated
    public void generateDocumentation(String outputDirectory, String contexts, CatalogAndSchema ... schemaList) throws LiquibaseException {
        this.generateDocumentation(outputDirectory, new Contexts(contexts), new LabelExpression(), schemaList);
    }

    @Deprecated
    public void generateDocumentation(String outputDirectory, Contexts contexts, LabelExpression labelExpression, CatalogAndSchema ... schemaList) throws LiquibaseException {
        this.runInScope(() -> new CommandScope(DbDocCommandStep.COMMAND_NAME[0]).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, contexts != null ? contexts.toString() : null).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, labelExpression != null ? labelExpression.getOriginalString() : null).addArgumentValue(DbDocCommandStep.CATALOG_AND_SCHEMAS_ARG, schemaList).addArgumentValue(DbDocCommandStep.OUTPUT_DIRECTORY_ARG, outputDirectory).execute());
    }

    @Deprecated
    public DiffResult diff(Database referenceDatabase, Database targetDatabase, CompareControl compareControl) throws LiquibaseException {
        return DiffGeneratorFactory.getInstance().compare(referenceDatabase, targetDatabase, compareControl);
    }

    @Deprecated
    public void validate() throws LiquibaseException {
        this.runInScope(() -> new CommandScope("validate").addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.database).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, this.changeLogParameters).execute());
    }

    public void setChangeLogParameter(String key, Object value) {
        this.changeLogParameters.set(key, value);
    }

    public void setChangeExecListener(ChangeExecListener listener) {
        this.changeExecListener = listener;
    }

    public DefaultChangeExecListener getDefaultChangeExecListener() {
        return this.defaultChangeExecListener;
    }

    public void setShowSummary(UpdateSummaryEnum showSummary) {
        this.showSummary = showSummary;
    }

    public void setShowSummaryOutput(UpdateSummaryOutputEnum showSummaryOutput) {
        this.showSummaryOutput = showSummaryOutput;
    }

    @Deprecated
    @SafeVarargs
    public final void generateChangeLog(CatalogAndSchema catalogAndSchema, DiffToChangeLog changeLogWriter, PrintStream outputStream2, Class<? extends DatabaseObject> ... snapshotTypes) throws DatabaseException, CommandExecutionException {
        this.generateChangeLog(catalogAndSchema, changeLogWriter, outputStream2, (ChangeLogSerializer)null, snapshotTypes);
    }

    @Deprecated
    @SafeVarargs
    public final void generateChangeLog(CatalogAndSchema catalogAndSchema, DiffToChangeLog changeLogWriter, PrintStream outputStream2, ChangeLogSerializer changeLogSerializer, Class<? extends DatabaseObject> ... snapshotTypes) throws DatabaseException, CommandExecutionException {
        HashSet<Class<? extends DatabaseObject>> finalCompareTypes = null;
        if (snapshotTypes != null && snapshotTypes.length > 0) {
            finalCompareTypes = new HashSet<Class<? extends DatabaseObject>>(Arrays.asList(snapshotTypes));
        }
        CompareControl compareControl = new CompareControl(new CompareControl.SchemaComparison[]{new CompareControl.SchemaComparison(catalogAndSchema, catalogAndSchema)}, finalCompareTypes);
        new CommandScope(GenerateChangelogCommandStep.COMMAND_NAME[0]).addArgumentValue(GenerateChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(PreCompareCommandStep.COMPARE_CONTROL_ARG, compareControl).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getDatabase()).addArgumentValue(PreCompareCommandStep.SNAPSHOT_TYPES_ARG, snapshotTypes).setOutput(outputStream2).execute();
    }

    private void runInScope(Scope.ScopedRunner scopedRunner) throws LiquibaseException {
        HashMap<String, Object> scopeObjects = new HashMap<String, Object>();
        scopeObjects.put(Scope.Attr.database.name(), this.getDatabase());
        scopeObjects.put(Scope.Attr.resourceAccessor.name(), this.getResourceAccessor());
        try {
            Scope.child(scopeObjects, scopedRunner);
        }
        catch (Exception e) {
            if (e instanceof LiquibaseException) {
                throw (LiquibaseException)e;
            }
            throw new LiquibaseException(e);
        }
    }

    @Override
    public void close() throws LiquibaseException {
        if (this.database != null) {
            this.database.close();
        }
    }
}

