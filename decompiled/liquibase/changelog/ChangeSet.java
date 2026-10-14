/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import liquibase.ChecksumVersion;
import liquibase.ContextExpression;
import liquibase.Labels;
import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.change.CheckSum;
import liquibase.change.DbmsTargetedChange;
import liquibase.change.core.EmptyChange;
import liquibase.change.core.RawSQLChange;
import liquibase.change.core.SQLFileChange;
import liquibase.change.visitor.ChangeVisitor;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RollbackContainer;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.database.Database;
import liquibase.database.DatabaseList;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.DatabaseException;
import liquibase.exception.MigrationFailedException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.RollbackFailedException;
import liquibase.exception.SetupException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.LoggingExecutor;
import liquibase.logging.Logger;
import liquibase.logging.mdc.customobjects.RollbackSqlFile;
import liquibase.parser.ChangeLogParserConfiguration;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.precondition.Conditional;
import liquibase.precondition.ErrorPrecondition;
import liquibase.precondition.FailedPrecondition;
import liquibase.precondition.core.PreconditionContainer;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.sql.visitor.SqlVisitorFactory;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.util.SqlUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

public class ChangeSet
implements Conditional,
ChangeLogChild {
    protected CheckSum checkSum;
    private CheckSum storedCheckSum;
    private static final String AND = " AND ";
    private static final String COMMA = ",";
    private static final String WHITESPACE = " ";
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    protected String key;
    private ChangeLogParameters changeLogParameters;
    private final List<Change> changes;
    private String id;
    private String author;
    private String filePath = "UNKNOWN CHANGE LOG";
    private String logicalFilePath;
    private String storedFilePath;
    private boolean alwaysRun;
    private boolean runOnChange;
    private ContextExpression contextFilter;
    private Labels labels;
    private boolean ignore;
    private Set<String> dbmsSet;
    private Boolean failOnError;
    private final Set<CheckSum> validCheckSums = new HashSet<CheckSum>();
    private boolean runInTransaction;
    private ValidationFailOption onValidationFail = ValidationFailOption.HALT;
    private boolean validationFailed;
    private final RollbackContainer rollback = new RollbackContainer();
    private String comments;
    private PreconditionContainer preconditions;
    private String runWith;
    private String runWithSpoolFile;
    private final List<SqlVisitor> sqlVisitors = new ArrayList<SqlVisitor>();
    private ObjectQuotingStrategy objectQuotingStrategy;
    private final DatabaseChangeLog changeLog;
    private String created;
    private String runOrder;
    private final Map<String, Object> attributes = new HashMap<String, Object>();
    private String deploymentId;
    private List<String> generatedSql = new ArrayList<String>();
    private ExecType execType;
    private String errorMsg;
    private ExecType rollbackExecType;
    private Date operationStartTime;
    private Date operationStopTime;

    public boolean shouldAlwaysRun() {
        return this.alwaysRun;
    }

    public boolean shouldRunOnChange() {
        return this.runOnChange;
    }

    public ChangeSet(DatabaseChangeLog databaseChangeLog) {
        this.changes = new ArrayList<Change>();
        this.changeLog = databaseChangeLog;
    }

    public ChangeSet(String id, String author, boolean alwaysRun, boolean runOnChange, String filePath, String contextFilter, String dbmsList, DatabaseChangeLog databaseChangeLog) {
        this(id, author, alwaysRun, runOnChange, filePath, contextFilter, dbmsList, null, null, true, ObjectQuotingStrategy.LEGACY, databaseChangeLog);
    }

    public ChangeSet(String id, String author, boolean alwaysRun, boolean runOnChange, String filePath, String contextFilter, String dbmsList, boolean runInTransaction, DatabaseChangeLog databaseChangeLog) {
        this(id, author, alwaysRun, runOnChange, filePath, contextFilter, dbmsList, null, null, runInTransaction, ObjectQuotingStrategy.LEGACY, databaseChangeLog);
    }

    public ChangeSet(String id, String author, boolean alwaysRun, boolean runOnChange, String filePath, String contextFilter, String dbmsList, ObjectQuotingStrategy quotingStrategy, DatabaseChangeLog databaseChangeLog) {
        this(id, author, alwaysRun, runOnChange, filePath, contextFilter, dbmsList, null, null, true, quotingStrategy, databaseChangeLog);
    }

    public ChangeSet(String id, String author, boolean alwaysRun, boolean runOnChange, String filePath, String contextFilter, String dbmsList, boolean runInTransaction, ObjectQuotingStrategy quotingStrategy, DatabaseChangeLog databaseChangeLog) {
        this(id, author, alwaysRun, runOnChange, filePath, contextFilter, dbmsList, null, null, runInTransaction, quotingStrategy, databaseChangeLog);
    }

    public ChangeSet(String id, String author, boolean alwaysRun, boolean runOnChange, String filePath, String contextFilter, String dbmsList, String runWith, String runWithSpoolFile, boolean runInTransaction, ObjectQuotingStrategy quotingStrategy, DatabaseChangeLog databaseChangeLog) {
        this(databaseChangeLog);
        this.id = id;
        this.author = author;
        this.filePath = filePath;
        this.alwaysRun = alwaysRun;
        this.runOnChange = runOnChange;
        this.runInTransaction = runInTransaction;
        this.objectQuotingStrategy = quotingStrategy;
        this.contextFilter = new ContextExpression(contextFilter);
        this.setDbms(dbmsList);
        this.runWith = runWith;
        this.runWithSpoolFile = runWithSpoolFile;
    }

    protected void setDbms(String dbmsList) {
        this.dbmsSet = DatabaseList.toDbmsSet(dbmsList);
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getLogicalFilePath() {
        return this.logicalFilePath;
    }

    public void setLogicalFilePath(String logicalFilePath) {
        this.logicalFilePath = logicalFilePath;
    }

    public String getStoredFilePath() {
        if (this.storedFilePath == null) {
            return this.getFilePath();
        }
        return this.storedFilePath;
    }

    public void setStoredFilePath(String storedFilePath) {
        this.storedFilePath = storedFilePath;
    }

    public String getRunWith() {
        return this.runWith == null || this.runWith.isEmpty() ? null : this.runWith;
    }

    public void setRunWith(String runWith) {
        this.runWith = runWith;
    }

    public String getRunWithSpoolFile() {
        return this.runWithSpoolFile;
    }

    public void setRunWithSpoolFile(String runWithSpoolFile) {
        this.runWithSpoolFile = runWithSpoolFile;
    }

    public void clearCheckSum() {
        this.checkSum = null;
    }

    public CheckSum generateCheckSum(ChecksumVersion version) {
        try {
            return Scope.child(Collections.singletonMap(Scope.Attr.checksumVersion.name(), version), () -> {
                if (this.checkSum == null) {
                    StringBuilder stringToMD5 = new StringBuilder();
                    for (Change change : this.getChanges()) {
                        if (change instanceof DbmsTargetedChange && !Scope.getCurrentScope().getChecksumVersion().lowerOrEqualThan(ChecksumVersion.V8) && !DatabaseList.definitionMatches(((DbmsTargetedChange)((Object)change)).getDbms(), Scope.getCurrentScope().getDatabase(), true)) continue;
                        stringToMD5.append(change.generateCheckSum()).append(":");
                    }
                    for (SqlVisitor visitor : this.getSqlVisitors()) {
                        stringToMD5.append(visitor.generateCheckSum()).append(";");
                    }
                    this.checkSum = CheckSum.compute(stringToMD5.toString());
                }
                return this.checkSum;
            });
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void load(ParsedNode node, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        this.id = node.getChildValue((String)null, "id", String.class);
        this.author = node.getChildValue((String)null, "author", String.class);
        this.alwaysRun = node.getChildValue(null, "runAlways", node.getChildValue(null, "alwaysRun", false));
        this.runOnChange = node.getChildValue(null, "runOnChange", false);
        this.runWith = node.getChildValue((String)null, "runWith", String.class);
        this.runWithSpoolFile = node.getChildValue((String)null, "runWithSpoolFile", String.class);
        this.contextFilter = new ContextExpression(node.getChildValue((String)null, "contextFilter", String.class));
        if (this.contextFilter.isEmpty()) {
            this.contextFilter = new ContextExpression(node.getChildValue((String)null, "context", String.class));
        }
        this.labels = new Labels(StringUtil.trimToNull(node.getChildValue((String)null, "labels", String.class)));
        this.setDbms(node.getChildValue((String)null, "dbms", String.class));
        this.runInTransaction = node.getChildValue(null, "runInTransaction", true);
        this.created = node.getChildValue((String)null, "created", String.class);
        this.runOrder = node.getChildValue((String)null, "runOrder", String.class);
        this.ignore = node.getChildValue(null, "ignore", false);
        this.comments = StringUtil.join(node.getChildren(null, "comment"), "\n", obj -> {
            if (((ParsedNode)obj).getValue() == null) {
                return "";
            }
            return ((ParsedNode)obj).getValue().toString();
        });
        this.comments = StringUtil.trimToNull(this.comments);
        String objectQuotingStrategyString = StringUtil.trimToNull(node.getChildValue((String)null, "objectQuotingStrategy", String.class));
        if (this.changeLog != null) {
            this.objectQuotingStrategy = this.changeLog.getObjectQuotingStrategy();
        }
        if (objectQuotingStrategyString != null) {
            this.objectQuotingStrategy = ObjectQuotingStrategy.valueOf(objectQuotingStrategyString);
        }
        if (this.objectQuotingStrategy == null) {
            this.objectQuotingStrategy = ObjectQuotingStrategy.LEGACY;
        }
        this.filePath = this.logicalFilePath = StringUtil.trimToNull(node.getChildValue((String)null, "logicalFilePath", String.class));
        if (this.filePath == null) {
            if (this.changeLog != null) {
                this.filePath = this.changeLog.getFilePath();
            }
        } else {
            this.filePath = this.filePath.replaceAll("\\\\", "/").replaceFirst("^/", "");
        }
        this.setFailOnError(node.getChildValue((String)null, "failOnError", Boolean.class));
        String onValidationFailString = node.getChildValue(null, "onValidationFail", "HALT");
        this.setOnValidationFail(ValidationFailOption.valueOf(onValidationFailString));
        for (ParsedNode child : node.getChildren()) {
            this.handleChildNode(child, resourceAccessor);
        }
    }

    protected void handleChildNode(ParsedNode child, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        switch (child.getName()) {
            case "rollback": {
                this.handleRollbackNode(child, resourceAccessor);
                break;
            }
            case "validCheckSum": 
            case "validCheckSums": {
                if (child.getValue() == null) {
                    return;
                }
                if (child.getValue() instanceof Collection) {
                    for (Object checksum : (Collection)child.getValue()) {
                        this.addValidCheckSum((String)checksum);
                    }
                    break;
                }
                this.addValidCheckSum(child.getValue(String.class));
                break;
            }
            case "modifySql": {
                String dbmsString = StringUtil.trimToNull(child.getChildValue((String)null, "dbms", String.class));
                String contextFilterString = StringUtil.trimToNull(child.getChildValue((String)null, "contextFilter", String.class));
                if (contextFilterString == null) {
                    contextFilterString = StringUtil.trimToNull(child.getChildValue((String)null, "context", String.class));
                }
                String labelsString = StringUtil.trimToNull(child.getChildValue((String)null, "labels", String.class));
                boolean applyToRollback = child.getChildValue(null, "applyToRollback", false);
                HashSet<String> dbms = new HashSet<String>();
                if (dbmsString != null) {
                    dbms.addAll(StringUtil.splitAndTrim(dbmsString, COMMA));
                }
                ContextExpression contextFilter = null;
                if (contextFilterString != null) {
                    contextFilter = new ContextExpression(contextFilterString);
                }
                Labels labels = null;
                if (labelsString != null) {
                    labels = new Labels(labelsString);
                }
                List<ParsedNode> potentialVisitors = child.getChildren();
                for (ParsedNode node : potentialVisitors) {
                    SqlVisitor sqlVisitor = SqlVisitorFactory.getInstance().create(node.getName());
                    if (sqlVisitor == null) continue;
                    sqlVisitor.setApplyToRollback(applyToRollback);
                    if (!dbms.isEmpty()) {
                        sqlVisitor.setApplicableDbms(dbms);
                    }
                    sqlVisitor.setContextFilter(contextFilter);
                    sqlVisitor.setLabels(labels);
                    sqlVisitor.load(node, resourceAccessor);
                    this.addSqlVisitor(sqlVisitor);
                }
                break;
            }
            case "preConditions": {
                this.preconditions = new PreconditionContainer();
                this.preconditions.load(child, resourceAccessor);
                break;
            }
            case "changes": {
                for (ParsedNode changeNode : child.getChildren()) {
                    this.handleChildNode(changeNode, resourceAccessor);
                }
                break;
            }
            default: {
                Change change = this.toChange(child, resourceAccessor);
                if (change == null && child.getValue() instanceof String) {
                    this.setAttribute(child.getName(), child.getValue());
                    break;
                }
                this.addChange(change);
            }
        }
    }

    protected void handleRollbackNode(ParsedNode rollbackNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        String changeSetId = rollbackNode.getChildValue((String)null, "changeSetId", String.class);
        if (changeSetId != null) {
            String changeSetAuthor = rollbackNode.getChildValue((String)null, "changeSetAuthor", String.class);
            String changeSetPath = rollbackNode.getChildValue(null, "changeSetPath", this.getFilePath());
            DatabaseChangeLog changeLog = this.getChangeLog();
            List<ChangeSet> changeSets = changeLog.getChangeSets(changeSetPath, changeSetAuthor, changeSetId);
            while (changeSets.isEmpty() && changeLog != null) {
                if ((changeLog = changeLog.getParentChangeLog()) == null) continue;
                changeSets = changeLog.getChangeSets(changeSetPath, changeSetAuthor, changeSetId);
            }
            if (changeSets.isEmpty()) {
                throw new ParsedNodeException("Change set " + new ChangeSet(changeSetId, changeSetAuthor, false, false, changeSetPath, null, null, null).toString(false) + " does not exist");
            }
            for (ChangeSet changeSet : changeSets) {
                for (Change change : changeSet.getChanges()) {
                    this.rollback.getChanges().add(change);
                }
            }
            return;
        }
        boolean foundValue = false;
        for (ParsedNode childNode : rollbackNode.getChildren()) {
            Change rollbackChange = this.toChange(childNode, resourceAccessor);
            if (rollbackChange == null) continue;
            this.addRollbackChange(rollbackChange);
            foundValue = true;
        }
        Object value = rollbackNode.getValue();
        if (value != null) {
            if (value instanceof String) {
                String finalValue = StringUtil.trimToNull((String)value);
                if (finalValue != null) {
                    String[] strings;
                    for (String string : strings = StringUtil.processMultiLineSQL(finalValue, true, true, ";", this)) {
                        this.addRollbackChange(new RawSQLChange(string));
                        foundValue = true;
                    }
                }
            } else {
                throw new ParsedNodeException("Unexpected object: " + value.getClass().getName() + " '" + value + "'");
            }
        }
        if (!foundValue) {
            this.addRollbackChange(new EmptyChange());
        }
    }

    protected Change toChange(ParsedNode value, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        Change change = Scope.getCurrentScope().getSingleton(ChangeFactory.class).create(value.getName());
        if (change == null) {
            if (value.getChildren().size() > 0 && ChangeLogParserConfiguration.CHANGELOG_PARSE_MODE.getCurrentValue().equals((Object)ChangeLogParserConfiguration.ChangelogParseMode.STRICT)) {
                String message = "";
                if (this.getChangeLog() != null && this.getChangeLog().getPhysicalFilePath() != null) {
                    message = "Error parsing " + this.getChangeLog().getPhysicalFilePath() + ": ";
                }
                message = message + "Unknown change type '" + value.getName() + "'. Check for spelling or capitalization errors and missing extensions such as liquibase-commercial.";
                throw new ParsedNodeException(message);
            }
            return null;
        }
        change.load(value, resourceAccessor);
        for (ChangeVisitor changeVisitor : this.getChangeVisitors()) {
            change.modify(changeVisitor);
        }
        return change;
    }

    @Override
    public ParsedNode serialize() {
        throw new RuntimeException("TODO");
    }

    public ExecType execute(DatabaseChangeLog databaseChangeLog, Database database) throws MigrationFailedException {
        return this.execute(databaseChangeLog, null, database);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ExecType execute(DatabaseChangeLog databaseChangeLog, ChangeExecListener listener, Database database) throws MigrationFailedException {
        block56: {
            Logger log = Scope.getCurrentScope().getLog(this.getClass());
            this.addChangeSetMdcProperties();
            Boolean failOnError = this.getFailOnError();
            if (failOnError != null) {
                Scope.getCurrentScope().addMdcValue("failOnError", String.valueOf(failOnError));
            }
            if (this.validationFailed) {
                return ExecType.MARK_RAN;
            }
            this.operationStartTime = new Date();
            long startTime = this.operationStartTime.getTime();
            Scope.getCurrentScope().addMdcValue("changesetOperationStart", Instant.ofEpochMilli(startTime).toString());
            boolean skipChange = false;
            Executor originalExecutor = this.setupCustomExecutorIfNecessary(database);
            try {
                Executor executor2;
                block55: {
                    StringBuilder message;
                    executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
                    database.setObjectQuotingStrategy(this.objectQuotingStrategy);
                    if (database.supportsDDLInTransaction()) {
                        database.setAutoCommit(!this.runInTransaction);
                    }
                    executor2.modifyChangeSet(this);
                    executor2.comment("Changeset " + this.toString(false));
                    if (StringUtil.trimToNull(this.getComments()) != null) {
                        String comments = this.getComments();
                        String[] lines = comments.split("\\n");
                        for (int i = 0; i < lines.length; ++i) {
                            if (i <= 0) continue;
                            lines[i] = database.getLineComment() + WHITESPACE + lines[i];
                        }
                        executor2.comment(StringUtil.join(Arrays.asList(lines), "\n"));
                    }
                    try {
                        if (this.preconditions != null) {
                            this.preconditions.check(database, databaseChangeLog, this, listener);
                        }
                    }
                    catch (PreconditionFailedException e) {
                        if (listener != null) {
                            listener.preconditionFailed(e, this.preconditions.getOnFail());
                        }
                        message = new StringBuilder();
                        message.append(StreamUtil.getLineSeparator());
                        for (FailedPrecondition failedPrecondition : e.getFailedPreconditions()) {
                            message.append("          ").append(failedPrecondition.toString());
                            message.append(StreamUtil.getLineSeparator());
                        }
                        if (this.preconditions.getOnFail().equals((Object)PreconditionContainer.FailOption.HALT)) {
                            throw new MigrationFailedException(this, message.toString(), e);
                        }
                        if (this.preconditions.getOnFail().equals((Object)PreconditionContainer.FailOption.CONTINUE)) {
                            skipChange = true;
                            this.execType = ExecType.SKIPPED;
                            Scope.getCurrentScope().getLog(this.getClass()).info("Continuing past: " + this + " despite precondition failure due to onFail='CONTINUE': " + message);
                            break block55;
                        }
                        if (this.preconditions.getOnFail().equals((Object)PreconditionContainer.FailOption.MARK_RAN)) {
                            this.execType = ExecType.MARK_RAN;
                            skipChange = true;
                            log.info("Marking ChangeSet: \"" + this + "\" as ran despite precondition failure due to onFail='MARK_RAN': " + message);
                            break block55;
                        }
                        if (this.preconditions.getOnFail().equals((Object)PreconditionContainer.FailOption.WARN)) {
                            this.execType = null;
                            break block55;
                        }
                        throw new UnexpectedLiquibaseException("Unexpected precondition onFail attribute: " + (Object)((Object)this.preconditions.getOnFail()), e);
                    }
                    catch (PreconditionErrorException e) {
                        if (listener != null) {
                            listener.preconditionErrored(e, this.preconditions.getOnError());
                        }
                        message = new StringBuilder();
                        message.append(StreamUtil.getLineSeparator());
                        for (ErrorPrecondition errorPrecondition : e.getErrorPreconditions()) {
                            message.append("          ").append(errorPrecondition.toString());
                            message.append(StreamUtil.getLineSeparator());
                        }
                        if (this.preconditions.getOnError().equals((Object)PreconditionContainer.ErrorOption.HALT)) {
                            throw new MigrationFailedException(this, message.toString(), e);
                        }
                        if (this.preconditions.getOnError().equals((Object)PreconditionContainer.ErrorOption.CONTINUE)) {
                            skipChange = true;
                            this.execType = ExecType.SKIPPED;
                        } else if (this.preconditions.getOnError().equals((Object)PreconditionContainer.ErrorOption.MARK_RAN)) {
                            this.execType = ExecType.MARK_RAN;
                            skipChange = true;
                            log.info("Marking ChangeSet: " + this + " ran despite precondition error: " + message);
                        } else if (this.preconditions.getOnError().equals((Object)PreconditionContainer.ErrorOption.WARN)) {
                            this.execType = null;
                        } else {
                            throw new UnexpectedLiquibaseException("Unexpected precondition onError attribute: " + (Object)((Object)this.preconditions.getOnError()), e);
                        }
                        database.rollback();
                    }
                    finally {
                        database.rollback();
                    }
                }
                if (!skipChange) {
                    for (Change change : this.changes) {
                        try {
                            change.finishInitialization();
                        }
                        catch (SetupException se) {
                            throw new MigrationFailedException(this, (Throwable)se);
                        }
                    }
                    log.fine("Reading ChangeSet: " + this);
                    for (Change change : this.getChanges()) {
                        if (!(change instanceof DbmsTargetedChange) || DatabaseList.definitionMatches(((DbmsTargetedChange)((Object)change)).getDbms(), database, true)) {
                            if (listener != null) {
                                listener.willRun(change, this, this.changeLog, database);
                            }
                            if (change.generateStatementsVolatile(database)) {
                                executor2.comment("WARNING The following SQL may change each run and therefore is possibly incorrect and/or invalid:");
                            }
                            String sql = this.addSqlMdc(change, database, false);
                            this.getGeneratedSql().add(sql);
                            database.executeStatements(change, databaseChangeLog, this.sqlVisitors);
                            log.info(change.getConfirmationMessage());
                            if (listener == null) continue;
                            listener.ran(change, this, this.changeLog, database);
                            continue;
                        }
                        log.fine("Change " + change.getSerializedObjectName() + " not included for database " + database.getShortName());
                    }
                    if (this.runInTransaction) {
                        database.commit();
                    }
                    if (this.execType == null) {
                        this.execType = ExecType.EXECUTED;
                    }
                    this.operationStopTime = new Date();
                    long stopTime = this.operationStopTime.getTime();
                    Scope.getCurrentScope().addMdcValue("changesetOperationStop", Instant.ofEpochMilli(stopTime).toString());
                    Scope.getCurrentScope().addMdcValue("changesetOutcome", this.execType.value.toLowerCase());
                    log.info("ChangeSet " + this.toString(false) + " ran successfully in " + (stopTime - startTime) + "ms");
                } else {
                    log.fine("Skipping ChangeSet: " + this);
                }
            }
            catch (Exception e) {
                this.operationStopTime = new Date();
                Scope.getCurrentScope().addMdcValue("changesetOperationStop", Instant.ofEpochMilli(this.operationStopTime.getTime()).toString());
                Scope.getCurrentScope().addMdcValue("changesetOutcome", ExecType.FAILED.value.toLowerCase());
                log.severe(String.format("ChangeSet %s encountered an exception.", this.toString(false)), e);
                this.setErrorMsg(e.getMessage());
                try {
                    database.rollback();
                }
                catch (Exception e1) {
                    throw new MigrationFailedException(this, (Throwable)e);
                }
                if (failOnError != null && !failOnError.booleanValue()) {
                    log.info("Changeset " + this.toString(false) + " failed, but failOnError was false.  Error: " + e.getMessage());
                    log.fine("Failure Stacktrace", e);
                    this.execType = ExecType.FAILED;
                    break block56;
                }
                if (e instanceof MigrationFailedException) {
                    throw (MigrationFailedException)e;
                }
                throw new MigrationFailedException(this, (Throwable)e);
            }
            finally {
                Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("jdbc", database, originalExecutor);
                if (!this.runInTransaction && database.supportsDDLInTransaction()) {
                    try {
                        database.setAutoCommit(false);
                    }
                    catch (DatabaseException e) {
                        Scope.getCurrentScope().getLog(this.getClass()).warning("Could not resetInternalState autocommit", e);
                    }
                }
            }
        }
        return this.execType;
    }

    private Executor setupCustomExecutorIfNecessary(Database database) {
        Executor originalExecutor = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
        if (this.getRunWith() == null || originalExecutor instanceof LoggingExecutor) {
            return originalExecutor;
        }
        Scope.getCurrentScope().addMdcValue("runWith", this.getRunWith());
        String executorName = ChangeSet.lookupExecutor(this.getRunWith());
        Executor customExecutor = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor(executorName, database);
        Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("jdbc", database, customExecutor);
        List<Change> changes = this.getChanges();
        for (Change change : changes) {
            ResourceAccessor resourceAccessor;
            if (!(change instanceof AbstractChange) || (resourceAccessor = ((AbstractChange)change).getResourceAccessor()) == null) continue;
            customExecutor.setResourceAccessor(resourceAccessor);
            break;
        }
        return originalExecutor;
    }

    public static String lookupExecutor(String executorName) {
        if (StringUtil.isEmpty(executorName)) {
            return null;
        }
        String key = "liquibase." + executorName.toLowerCase() + ".executor";
        if (executorName.equalsIgnoreCase("psql") || executorName.equalsIgnoreCase("sqlcmd") || executorName.equalsIgnoreCase("sqlplus")) {
            return executorName;
        }
        String replacementExecutorName = (String)Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class).getCurrentConfiguredValue(null, null, key).getValue();
        if (replacementExecutorName != null) {
            Scope.getCurrentScope().getLog(ChangeSet.class).info("Mapped '" + executorName + "' to executor '" + replacementExecutorName + "'");
            return replacementExecutorName;
        }
        if (executorName.equalsIgnoreCase("native")) {
            String message = "Unable to locate an executor for 'runWith=" + executorName + "'.  You must specify a valid executor name.";
            Scope.getCurrentScope().getLog(ChangeSet.class).warning(message);
            Scope.getCurrentScope().getUI().sendErrorMessage("WARNING: " + message);
        }
        return executorName;
    }

    public void rollback(Database database) throws RollbackFailedException {
        this.rollback(database, null);
    }

    public void rollback(Database database, ChangeExecListener listener) throws RollbackFailedException {
        this.operationStartTime = new Date();
        Scope.getCurrentScope().addMdcValue("changesetOperationStart", Instant.ofEpochMilli(this.operationStartTime.getTime()).toString());
        this.addChangeSetMdcProperties();
        Scope.getCurrentScope().addMdcValue("deploymentId", this.getDeploymentId());
        Executor originalExecutor = this.setupCustomExecutorIfNecessary(database);
        try {
            Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
            executor2.comment("Rolling Back ChangeSet: " + this);
            database.setObjectQuotingStrategy(this.objectQuotingStrategy);
            if (database.supportsDDLInTransaction()) {
                database.setAutoCommit(!this.runInTransaction);
            }
            executor2.modifyChangeSet(this);
            if (this.hasCustomRollbackChanges()) {
                LinkedList<SqlStatement> statements = new LinkedList<SqlStatement>();
                for (Change change : this.rollback.getChanges()) {
                    ValidationErrors errors2;
                    if (this.ignoreSpecificChangeTypes(change, database)) continue;
                    if (listener != null) {
                        listener.willRun(change, this, this.changeLog, database);
                    }
                    if ((errors2 = change.validate(database)).hasErrors()) {
                        throw new RollbackFailedException("Rollback statement failed validation: " + errors2);
                    }
                    SqlStatement[] changeStatements = change.generateStatements(database);
                    String sql = this.addSqlMdc(change, database, false);
                    this.getGeneratedSql().add(sql);
                    if (change instanceof SQLFileChange) {
                        this.addSqlFileMdc((SQLFileChange)change);
                    }
                    if (changeStatements != null) {
                        statements.addAll(Arrays.asList(changeStatements));
                    }
                    if (listener == null) continue;
                    listener.ran(change, this, this.changeLog, database);
                }
                if (!statements.isEmpty()) {
                    database.executeRollbackStatements(statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT), this.sqlVisitors);
                }
            } else {
                List<Change> changes = this.getChanges();
                for (int i = changes.size() - 1; i >= 0; --i) {
                    Change change = changes.get(i);
                    if (change instanceof RawSQLChange && this.getFilePath().toLowerCase().endsWith(".sql")) {
                        throw new RollbackFailedException("Liquibase does not support automatic rollback generation for raw sql changes (did you mean to specify keyword \"empty\" to ignore rolling back this change?)");
                    }
                    String sql = this.addSqlMdc(change, database, true);
                    this.getGeneratedSql().add(sql);
                    database.executeRollbackStatements(change, this.sqlVisitors);
                }
            }
            if (this.runInTransaction) {
                database.commit();
            }
            this.rollbackExecType = ExecType.EXECUTED;
            Scope.getCurrentScope().addMdcValue("changesetOutcome", ExecType.EXECUTED.value.toLowerCase());
            this.operationStopTime = new Date();
            Scope.getCurrentScope().addMdcValue("changesetOperationStop", Instant.ofEpochMilli(this.operationStopTime.getTime()).toString());
            Scope.getCurrentScope().getLog(this.getClass()).fine("ChangeSet " + this.toString() + " has been successfully rolled back.");
        }
        catch (Exception e) {
            this.setErrorMsg(e.getMessage());
            this.operationStopTime = new Date();
            Scope.getCurrentScope().addMdcValue("changesetOperationStop", Instant.ofEpochMilli(this.operationStopTime.getTime()).toString());
            try {
                this.rollbackExecType = ExecType.FAILED;
                Scope.getCurrentScope().addMdcValue("changesetOutcome", ExecType.FAILED.value.toLowerCase());
                Scope.getCurrentScope().getLog(this.getClass()).fine("ChangeSet " + this + " rollback failed.");
                database.rollback();
            }
            catch (DatabaseException databaseException) {
                // empty catch block
            }
            throw new RollbackFailedException(e);
        }
        finally {
            Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("jdbc", database, originalExecutor);
            if (!this.runInTransaction && database.supportsDDLInTransaction()) {
                try {
                    database.setAutoCommit(false);
                }
                catch (DatabaseException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).warning("Could not resetInternalState autocommit", e);
                }
            }
        }
    }

    private void addSqlFileMdc(SQLFileChange change) {
        RollbackSqlFile rollbackSqlFile = new RollbackSqlFile(change);
        Scope.getCurrentScope().addMdcValue("rollbackSqlFile", rollbackSqlFile);
    }

    private boolean ignoreSpecificChangeTypes(Change change, Database database) {
        return change instanceof DbmsTargetedChange && !DatabaseList.definitionMatches(((DbmsTargetedChange)((Object)change)).getDbms(), database, true) || change instanceof RawSQLChange && "empty".equalsIgnoreCase(((RawSQLChange)change).getSql());
    }

    public boolean hasCustomRollbackChanges() {
        return this.rollback.getChanges() != null && !this.rollback.getChanges().isEmpty();
    }

    public List<Change> getChanges() {
        return Collections.unmodifiableList(this.changes);
    }

    public void removeAllChanges(Collection<?> collection) {
        this.changes.removeAll(collection);
    }

    public void addChange(Change change) {
        if (change == null) {
            return;
        }
        this.changes.add(change);
        change.setChangeSet(this);
    }

    public String getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    @Deprecated
    public ContextExpression getContexts() {
        return this.getContextFilter();
    }

    @Deprecated
    public ChangeSet setContexts(ContextExpression contexts) {
        return this.setContextFilter(contexts);
    }

    public ContextExpression getContextFilter() {
        return this.contextFilter;
    }

    public ChangeSet setContextFilter(ContextExpression contextFilter) {
        this.contextFilter = contextFilter;
        return this;
    }

    public Labels getLabels() {
        return this.labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public Set<String> getDbmsSet() {
        return this.dbmsSet;
    }

    public boolean isIgnore() {
        return this.ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public boolean isInheritableIgnore() {
        DatabaseChangeLog changeLog = this.getChangeLog();
        if (changeLog == null) {
            return false;
        }
        return changeLog.isIncludeIgnore();
    }

    public Collection<ContextExpression> getInheritableContextFilter() {
        ArrayList<ContextExpression> expressions = new ArrayList<ContextExpression>();
        for (DatabaseChangeLog changeLog = this.getChangeLog(); changeLog != null; changeLog = changeLog.getParentChangeLog()) {
            ContextExpression includeExpression;
            ContextExpression expression = changeLog.getContextFilter();
            if (expression != null && !expression.isEmpty()) {
                expressions.add(expression);
            }
            if ((includeExpression = changeLog.getIncludeContextFilter()) == null || includeExpression.isEmpty()) continue;
            expressions.add(includeExpression);
        }
        return Collections.unmodifiableCollection(expressions);
    }

    public Collection<Labels> getInheritableLabels() {
        ArrayList<Labels> labels = new ArrayList<Labels>();
        for (DatabaseChangeLog changeLog = this.getChangeLog(); changeLog != null; changeLog = changeLog.getParentChangeLog()) {
            Labels includeLabels = changeLog.getIncludeLabels();
            if (includeLabels == null || includeLabels.isEmpty()) continue;
            labels.add(includeLabels);
        }
        return Collections.unmodifiableCollection(labels);
    }

    public String buildFullContext() {
        StringBuilder contextExpression = new StringBuilder();
        boolean notFirstContext = false;
        for (ContextExpression inheritableContext : this.getInheritableContextFilter()) {
            this.appendContext(contextExpression, inheritableContext.toString(), notFirstContext);
            notFirstContext = true;
        }
        ContextExpression changeSetContext = this.getContexts();
        if (changeSetContext != null && !changeSetContext.isEmpty()) {
            this.appendContext(contextExpression, changeSetContext.toString(), notFirstContext);
        }
        return StringUtil.trimToNull(contextExpression.toString());
    }

    public String buildFullLabels() {
        StringBuilder labels = new StringBuilder();
        boolean notFirstLabel = false;
        for (Labels inheritableLabel : this.getInheritableLabels()) {
            this.appendLabels(labels, inheritableLabel.toString(), notFirstLabel);
            notFirstLabel = true;
        }
        Labels changeSetLabels = this.getLabels();
        if (changeSetLabels != null && !changeSetLabels.isEmpty()) {
            this.appendLabels(labels, changeSetLabels.toString(), notFirstLabel);
        }
        return StringUtil.trimToNull(labels.toString());
    }

    private void appendLabels(StringBuilder existingLabels, String labelToAppend, boolean notFirstContext) {
        if (notFirstContext) {
            existingLabels.append(COMMA);
        }
        existingLabels.append(labelToAppend);
    }

    private void appendContext(StringBuilder contextExpression, String contextToAppend, boolean notFirstContext) {
        boolean complexExpression;
        boolean bl = complexExpression = contextToAppend.contains(COMMA) || contextToAppend.contains(WHITESPACE);
        if (notFirstContext) {
            contextExpression.append(AND);
        }
        if (complexExpression) {
            contextExpression.append(OPEN_BRACKET);
        }
        contextExpression.append(contextToAppend);
        if (complexExpression) {
            contextExpression.append(CLOSE_BRACKET);
        }
    }

    public DatabaseChangeLog getChangeLog() {
        return this.changeLog;
    }

    public String toString(boolean includeMD5Sum) {
        ChecksumVersion checksumVersion = ChecksumVersion.enumFromChecksumVersion(this.checkSum != null ? this.checkSum.getVersion() : ChecksumVersion.latest().getVersion());
        return this.filePath + "::" + this.getId() + "::" + this.getAuthor() + (includeMD5Sum ? "::(Checksum: " + this.generateCheckSum(checksumVersion) + CLOSE_BRACKET : "");
    }

    public String toNormalizedString() {
        return DatabaseChangeLog.normalizePath(this.filePath) + "::" + this.getId() + "::" + this.getAuthor();
    }

    public String toString() {
        return this.toString(false);
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isAlwaysRun() {
        return this.alwaysRun;
    }

    public boolean isRunOnChange() {
        return this.runOnChange;
    }

    public boolean isRunInTransaction() {
        return this.runInTransaction;
    }

    public RollbackContainer getRollback() {
        return this.rollback;
    }

    public void addRollBackSQL(String sql) {
        if (StringUtil.trimToNull(sql) == null) {
            if (this.rollback.getChanges().isEmpty()) {
                this.rollback.getChanges().add(new EmptyChange());
            }
            return;
        }
        for (String statement : StringUtil.splitSQL(sql, null, this)) {
            this.rollback.getChanges().add(new RawSQLChange(statement.trim()));
        }
    }

    public void addRollbackChange(Change change) {
        if (change == null) {
            return;
        }
        this.rollback.getChanges().add(change);
        change.setChangeSet(this);
    }

    public boolean supportsRollback(Database database) {
        if (this.rollback.getChanges() != null && !this.rollback.getChanges().isEmpty()) {
            return true;
        }
        for (Change change : this.getChanges()) {
            if (change.supportsRollback(database)) continue;
            return false;
        }
        return true;
    }

    public String getDescription() {
        List<Change> changes = this.getChanges();
        if (changes.isEmpty()) {
            return "empty";
        }
        ArrayList<String> messages = new ArrayList<String>();
        for (Change change : changes) {
            messages.add(change.getDescription());
        }
        return StringUtil.limitSize(StringUtil.join(messages, "; "), 255);
    }

    public Boolean getFailOnError() {
        return this.failOnError;
    }

    public void setFailOnError(Boolean failOnError) {
        this.failOnError = failOnError;
    }

    public ValidationFailOption getOnValidationFail() {
        return this.onValidationFail;
    }

    public void setOnValidationFail(ValidationFailOption onValidationFail) {
        this.onValidationFail = onValidationFail;
    }

    public void setValidationFailed(boolean validationFailed) {
        this.validationFailed = validationFailed;
    }

    public void addValidCheckSum(String text) {
        this.validCheckSums.add(CheckSum.parse(text));
    }

    public Set<CheckSum> getValidCheckSums() {
        return Collections.unmodifiableSet(this.validCheckSums);
    }

    public boolean isCheckSumValid(CheckSum storedCheckSum) {
        CheckSum currentMd5Sum;
        for (CheckSum validCheckSum : this.validCheckSums) {
            if (!"1:any".equalsIgnoreCase(validCheckSum.toString()) && !"1:all".equalsIgnoreCase(validCheckSum.toString()) && !"1:*".equalsIgnoreCase(validCheckSum.toString())) continue;
            return true;
        }
        CheckSum checkSum = currentMd5Sum = storedCheckSum != null ? this.generateCheckSum(ChecksumVersion.enumFromChecksumVersion(storedCheckSum.getVersion())) : null;
        if (currentMd5Sum == null) {
            return true;
        }
        if (storedCheckSum == null) {
            return true;
        }
        if (currentMd5Sum.equals(storedCheckSum)) {
            return true;
        }
        for (CheckSum validCheckSum : this.validCheckSums) {
            if (!currentMd5Sum.equals(validCheckSum) && !storedCheckSum.equals(validCheckSum)) continue;
            return true;
        }
        return false;
    }

    @Override
    public PreconditionContainer getPreconditions() {
        return this.preconditions;
    }

    @Override
    public void setPreconditions(PreconditionContainer preconditionContainer) {
        this.preconditions = preconditionContainer;
    }

    public void addSqlVisitor(SqlVisitor sqlVisitor) {
        this.sqlVisitors.add(sqlVisitor);
    }

    public List<SqlVisitor> getSqlVisitors() {
        return this.sqlVisitors;
    }

    public ChangeLogParameters getChangeLogParameters() {
        return this.changeLogParameters;
    }

    public void setChangeLogParameters(ChangeLogParameters changeLogParameters) {
        this.changeLogParameters = changeLogParameters;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setRunOrder(String runOrder) {
        if (runOrder != null && !"first".equals(runOrder = runOrder.toLowerCase()) && !"last".equals(runOrder)) {
            throw new UnexpectedLiquibaseException("runOrder must be 'first' or 'last'");
        }
        this.runOrder = runOrder;
    }

    @Override
    public String getSerializedObjectName() {
        return "changeSet";
    }

    @Override
    public Set<String> getSerializableFields() {
        return new LinkedHashSet<String>(Arrays.asList("id", "author", "runAlways", "runOnChange", "failOnError", "contextFilter", "labels", "dbms", "objectQuotingStrategy", "comment", "preconditions", "changes", "rollback", "labels", "logicalFilePath", "created", "runInTransaction", "runOrder", "ignore"));
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        if ("id".equals(field2)) {
            return this.getId();
        }
        if ("author".equals(field2)) {
            return this.getAuthor();
        }
        if ("runAlways".equals(field2)) {
            if (this.isAlwaysRun()) {
                return true;
            }
            return null;
        }
        if ("runOnChange".equals(field2)) {
            if (this.isRunOnChange()) {
                return true;
            }
            return null;
        }
        if ("failOnError".equals(field2)) {
            return this.getFailOnError();
        }
        if ("contextFilter".equals(field2) || "context".equals(field2)) {
            if (!this.getContextFilter().isEmpty()) {
                return this.getContextFilter().toString().replaceFirst("^\\(", "").replaceFirst("\\)$", "");
            }
            return null;
        }
        if ("labels".equals(field2)) {
            if (this.getLabels() != null && !this.getLabels().isEmpty()) {
                return StringUtil.join(this.getLabels().getLabels(), ", ");
            }
            return null;
        }
        if ("dbms".equals(field2)) {
            if (this.getDbmsSet() != null && !this.getDbmsSet().isEmpty()) {
                return StringUtil.join(this.getDbmsSet(), COMMA);
            }
            return null;
        }
        if ("comment".equals(field2)) {
            return StringUtil.trimToNull(this.getComments());
        }
        if ("objectQuotingStrategy".equals(field2)) {
            if (this.getObjectQuotingStrategy() == null) {
                return null;
            }
            return this.getObjectQuotingStrategy().toString();
        }
        if ("preconditions".equals(field2)) {
            if (this.getPreconditions() != null && !this.getPreconditions().getNestedPreconditions().isEmpty()) {
                return this.getPreconditions();
            }
            return null;
        }
        if ("changes".equals(field2)) {
            return this.getChanges();
        }
        if ("created".equals(field2)) {
            return this.getCreated();
        }
        if ("logicalFilePath".equals(field2)) {
            return this.getLogicalFilePath();
        }
        if ("rollback".equals(field2)) {
            if (this.rollback.getChanges() != null && !this.rollback.getChanges().isEmpty()) {
                return this.rollback;
            }
            return null;
        }
        if ("runInTransaction".equals(field2)) {
            if (!this.isRunInTransaction()) {
                return false;
            }
            return null;
        }
        if ("runOrder".equals(field2)) {
            return this.getRunOrder();
        }
        if ("ignore".equals(field2)) {
            if (this.isIgnore()) {
                return true;
            }
            return null;
        }
        throw new UnexpectedLiquibaseException("Unexpected field request on changeSet: " + field2);
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        if ("comment".equals(field2) || "preconditions".equals(field2) || "changes".equals(field2) || "rollback".equals(field2)) {
            return LiquibaseSerializable.SerializationType.NESTED_OBJECT;
        }
        return LiquibaseSerializable.SerializationType.NAMED_FIELD;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public String getSerializableFieldNamespace(String field2) {
        return this.getSerializedObjectNamespace();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ChangeSet)) {
            return false;
        }
        return this.toString(false).equals(((ChangeSet)obj).toString(false));
    }

    public int hashCode() {
        return this.toString(false).hashCode();
    }

    public Object getAttribute(String attribute) {
        return this.attributes.get(attribute);
    }

    public ChangeSet setAttribute(String attribute, Object value) {
        this.attributes.put(attribute, value);
        return this;
    }

    public CheckSum getStoredCheckSum() {
        return this.storedCheckSum;
    }

    public void setStoredCheckSum(CheckSum storedCheckSum) {
        this.storedCheckSum = storedCheckSum;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public void addChangeSetMdcProperties() {
        String commentMdc = this.comments != null ? this.comments : "";
        String labelMdc = this.labels != null ? this.labels.toString() : "";
        String contextsMdc = this.contextFilter != null && this.contextFilter.getOriginalString() != null ? this.contextFilter.getOriginalString() : "";
        String changelogPath = this.getChangeLog() != null ? this.getChangeLog().getLogicalFilePath() : null;
        Scope.getCurrentScope().addMdcValue("changelogFile", changelogPath);
        Scope.getCurrentScope().addMdcValue("changesetComment", commentMdc);
        Scope.getCurrentScope().addMdcValue("changesetLabel", labelMdc);
        Scope.getCurrentScope().addMdcValue("changesetContext", contextsMdc);
    }

    private String addSqlMdc(Change change, Database database, boolean generateRollbackStatements) throws Exception {
        if (!change.supports(database)) {
            return null;
        }
        AtomicReference statementsReference = new AtomicReference();
        HashMap<String, Object> scopeValues = new HashMap<String, Object>();
        scopeValues.put("shouldExecute", Boolean.FALSE);
        Scope.child(scopeValues, () -> statementsReference.set(generateRollbackStatements ? change.generateRollbackStatements(database) : change.generateStatements(database)));
        String sqlStatementsMdc = Arrays.stream((SqlStatement[])statementsReference.get()).map(statement -> SqlUtil.getSqlString(statement, SqlGeneratorFactory.getInstance(), database)).collect(Collectors.joining("\n"));
        Scope.getCurrentScope().addMdcValue("changesetSql", sqlStatementsMdc);
        return sqlStatementsMdc;
    }

    private List<ChangeVisitor> getChangeVisitors() {
        return this.getChangeLog().getChangeVisitors();
    }

    public ObjectQuotingStrategy getObjectQuotingStrategy() {
        return this.objectQuotingStrategy;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getRunOrder() {
        return this.runOrder;
    }

    public List<String> getGeneratedSql() {
        return this.generatedSql;
    }

    public void setGeneratedSql(List<String> generatedSql) {
        this.generatedSql = generatedSql;
    }

    public ExecType getExecType() {
        return this.execType;
    }

    public void setExecType(ExecType execType) {
        this.execType = execType;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ExecType getRollbackExecType() {
        return this.rollbackExecType;
    }

    public void setRollbackExecType(ExecType rollbackExecType) {
        this.rollbackExecType = rollbackExecType;
    }

    public Date getOperationStartTime() {
        return this.operationStartTime;
    }

    public void setOperationStartTime(Date operationStartTime) {
        this.operationStartTime = operationStartTime;
    }

    public Date getOperationStopTime() {
        return this.operationStopTime;
    }

    public void setOperationStopTime(Date operationStopTime) {
        this.operationStopTime = operationStopTime;
    }

    public static enum ValidationFailOption {
        HALT("HALT"),
        MARK_RAN("MARK_RAN");

        final String key;

        private ValidationFailOption(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }

    public static enum ExecType {
        EXECUTED("EXECUTED", false, true),
        FAILED("FAILED", false, false),
        SKIPPED("SKIPPED", false, false),
        RERAN("RERAN", true, true),
        MARK_RAN("MARK_RAN", false, true);

        public final String value;
        public final boolean ranBefore;
        public final boolean ran;

        private ExecType(String value, boolean ranBefore, boolean ran) {
            this.value = value;
            this.ranBefore = ranBefore;
            this.ran = ran;
        }
    }

    public static enum RunStatus {
        NOT_RAN,
        ALREADY_RAN,
        RUN_AGAIN,
        MARK_RAN,
        INVALID_MD5SUM;

    }
}

