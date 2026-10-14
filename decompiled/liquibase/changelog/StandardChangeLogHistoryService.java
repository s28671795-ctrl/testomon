/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import liquibase.ChecksumVersion;
import liquibase.ContextExpression;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Labels;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.CheckSum;
import liquibase.change.ColumnConfig;
import liquibase.changelog.AbstractChangeLogHistoryService;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.jvm.ChangelogJdbcMdcListener;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.ColumnConstraint;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AddColumnStatement;
import liquibase.statement.core.CreateDatabaseChangeLogTableStatement;
import liquibase.statement.core.GetNextChangeSetSequenceValueStatement;
import liquibase.statement.core.MarkChangeSetRanStatement;
import liquibase.statement.core.ModifyDataTypeStatement;
import liquibase.statement.core.RemoveChangeSetRanStatusStatement;
import liquibase.statement.core.ReorganizeTableStatement;
import liquibase.statement.core.SelectFromDatabaseChangeLogStatement;
import liquibase.statement.core.SetNullableStatement;
import liquibase.statement.core.TagDatabaseStatement;
import liquibase.statement.core.UpdateStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.DataType;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Table;

public class StandardChangeLogHistoryService
extends AbstractChangeLogHistoryService {
    private List<RanChangeSet> ranChangeSetList;
    private boolean serviceInitialized;
    private Boolean hasDatabaseChangeLogTable;
    private boolean databaseChecksumsCompatible = true;
    private Integer lastChangeSetSequenceValue;
    protected static final String LABELS_SIZE = "255";
    protected static final String CONTEXTS_SIZE = "255";

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean supports(Database database) {
        return true;
    }

    public String getDatabaseChangeLogTableName() {
        return this.getDatabase().getDatabaseChangeLogTableName();
    }

    public String getLiquibaseSchemaName() {
        return this.getDatabase().getLiquibaseSchemaName();
    }

    public String getLiquibaseCatalogName() {
        return this.getDatabase().getLiquibaseCatalogName();
    }

    public boolean canCreateChangeLogTable() {
        return true;
    }

    @Override
    public void reset() {
        this.ranChangeSetList = null;
        this.serviceInitialized = false;
        this.hasDatabaseChangeLogTable = null;
    }

    public boolean hasDatabaseChangeLogTable() {
        if (this.hasDatabaseChangeLogTable == null) {
            try {
                this.hasDatabaseChangeLogTable = SnapshotGeneratorFactory.getInstance().hasDatabaseChangeLogTable(this.getDatabase());
            }
            catch (LiquibaseException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return this.hasDatabaseChangeLogTable;
    }

    protected String getCharTypeName() {
        if (this.getDatabase() instanceof MSSQLDatabase && ((MSSQLDatabase)this.getDatabase()).sendsStringParametersAsUnicode()) {
            return "nvarchar";
        }
        return "varchar";
    }

    @Override
    public void init() throws DatabaseException {
        if (this.serviceInitialized) {
            return;
        }
        Database database = this.getDatabase();
        Table changeLogTable = null;
        try {
            changeLogTable = SnapshotGeneratorFactory.getInstance().getDatabaseChangeLogTable(new SnapshotControl(database, false, Table.class, Column.class), database);
        }
        catch (LiquibaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        ArrayList<AbstractSqlStatement> statementsToExecute = new ArrayList<AbstractSqlStatement>();
        boolean changeLogCreateAttempted = false;
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.getDatabase());
        if (changeLogTable != null) {
            Integer columnSize;
            boolean hasDeploymentIdColumn;
            boolean hasDescription = changeLogTable.getColumn("DESCRIPTION") != null;
            boolean bl = changeLogTable.getColumn("COMMENTS") != null;
            boolean hasTag = changeLogTable.getColumn("TAG") != null;
            boolean hasLiquibase = changeLogTable.getColumn("LIQUIBASE") != null;
            boolean hasContexts = changeLogTable.getColumn("CONTEXTS") != null;
            boolean hasLabels = changeLogTable.getColumn("LABELS") != null;
            boolean liquibaseColumnNotRightSize = false;
            if (!(this.getDatabase() instanceof SQLiteDatabase)) {
                Integer columnSize2;
                DataType type = changeLogTable.getColumn("LIQUIBASE").getType();
                liquibaseColumnNotRightSize = type.getTypeName().toLowerCase().startsWith("varchar") ? (columnSize2 = type.getColumnSize()) != null && columnSize2 < 20 : false;
            }
            boolean hasOrderExecuted = changeLogTable.getColumn("ORDEREXECUTED") != null;
            boolean checksumNotRightSize = false;
            if (!(this.getDatabase() instanceof SQLiteDatabase)) {
                DataType type = changeLogTable.getColumn("MD5SUM").getType();
                if (type.getTypeName().toLowerCase().startsWith("varchar") || type.getTypeName().toLowerCase().startsWith("character varying")) {
                    Integer columnSize3 = type.getColumnSize();
                    checksumNotRightSize = columnSize3 != null && columnSize3 < 35;
                } else {
                    liquibaseColumnNotRightSize = false;
                }
            }
            boolean hasExecTypeColumn = changeLogTable.getColumn("EXECTYPE") != null;
            String charTypeName = this.getCharTypeName();
            boolean bl2 = hasDeploymentIdColumn = changeLogTable.getColumn("DEPLOYMENT_ID") != null;
            if (!hasDescription) {
                executor2.comment("Adding missing databasechangelog.description column");
                statementsToExecute.add(new AddColumnStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "DESCRIPTION", charTypeName + "(255)", null, new ColumnConstraint[0]));
            }
            if (!hasTag) {
                executor2.comment("Adding missing databasechangelog.tag column");
                statementsToExecute.add(new AddColumnStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "TAG", charTypeName + "(255)", null, new ColumnConstraint[0]));
            }
            if (!bl) {
                executor2.comment("Adding missing databasechangelog.comments column");
                statementsToExecute.add(new AddColumnStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "COMMENTS", charTypeName + "(255)", null, new ColumnConstraint[0]));
            }
            if (!hasLiquibase) {
                executor2.comment("Adding missing databasechangelog.liquibase column");
                statementsToExecute.add(new AddColumnStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "LIQUIBASE", charTypeName + "(20)", null, new ColumnConstraint[0]));
            }
            if (!hasOrderExecuted) {
                executor2.comment("Adding missing databasechangelog.orderexecuted column");
                statementsToExecute.add(new AddColumnStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "ORDEREXECUTED", "int", null, new ColumnConstraint[0]));
                statementsToExecute.add(new UpdateStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName()).addNewColumnValue("ORDEREXECUTED", -1));
                statementsToExecute.add(new SetNullableStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "ORDEREXECUTED", "int", false));
            }
            if (checksumNotRightSize) {
                executor2.comment("Modifying size of databasechangelog.md5sum column");
                statementsToExecute.add(new ModifyDataTypeStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "MD5SUM", charTypeName + "(35)"));
            }
            if (liquibaseColumnNotRightSize) {
                executor2.comment("Modifying size of databasechangelog.liquibase column");
                statementsToExecute.add(new ModifyDataTypeStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "LIQUIBASE", charTypeName + "(20)"));
            }
            if (!hasExecTypeColumn) {
                executor2.comment("Adding missing databasechangelog.exectype column");
                statementsToExecute.add(new AddColumnStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "EXECTYPE", charTypeName + "(10)", null, new ColumnConstraint[0]));
                statementsToExecute.add(new UpdateStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName()).addNewColumnValue("EXECTYPE", "EXECUTED"));
                statementsToExecute.add(new SetNullableStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "EXECTYPE", charTypeName + "(10)", false));
            }
            if (hasContexts) {
                columnSize = changeLogTable.getColumn("CONTEXTS").getType().getColumnSize();
                if (columnSize != null && columnSize < Integer.parseInt(this.getContextsSize())) {
                    executor2.comment("Modifying size of databasechangelog.contexts column");
                    statementsToExecute.add(new ModifyDataTypeStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "CONTEXTS", charTypeName + "(" + this.getContextsSize() + ")"));
                }
            } else {
                executor2.comment("Adding missing databasechangelog.contexts column");
                statementsToExecute.add(new AddColumnStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "CONTEXTS", charTypeName + "(" + this.getContextsSize() + ")", null, new ColumnConstraint[0]));
            }
            if (hasLabels) {
                columnSize = changeLogTable.getColumn("LABELS").getType().getColumnSize();
                if (columnSize != null && columnSize < Integer.parseInt(this.getLabelsSize())) {
                    executor2.comment("Modifying size of databasechangelog.labels column");
                    statementsToExecute.add(new ModifyDataTypeStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "LABELS", charTypeName + "(" + this.getLabelsSize() + ")"));
                }
            } else {
                executor2.comment("Adding missing databasechangelog.labels column");
                statementsToExecute.add(new AddColumnStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "LABELS", charTypeName + "(" + this.getLabelsSize() + ")", null, new ColumnConstraint[0]));
            }
            if (!hasDeploymentIdColumn) {
                executor2.comment("Adding missing databasechangelog.deployment_id column");
                statementsToExecute.add(new AddColumnStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName(), "DEPLOYMENT_ID", "VARCHAR(10)", null, new ColumnConstraint[0]));
                if (database instanceof DB2Database) {
                    statementsToExecute.add(new ReorganizeTableStatement(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName()));
                }
            }
            SelectFromDatabaseChangeLogStatement databaseChangeLogStatement = new SelectFromDatabaseChangeLogStatement(new SelectFromDatabaseChangeLogStatement.ByCheckSumNotNullAndNotLike(ChecksumVersion.latest().getVersion()), new ColumnConfig().setName("MD5SUM"));
            List md5sumRS = ChangelogJdbcMdcListener.query(this.getDatabase(), ex -> ex.queryForList(databaseChangeLogStatement));
            this.databaseChecksumsCompatible = md5sumRS.isEmpty();
        } else if (!changeLogCreateAttempted) {
            executor2.comment("Create Database Change Log Table");
            CreateDatabaseChangeLogTableStatement createTableStatement = new CreateDatabaseChangeLogTableStatement();
            if (!this.canCreateChangeLogTable()) {
                throw new DatabaseException("Cannot create " + this.getDatabase().escapeTableName(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName()) + " table for your getDatabase().\n\nPlease construct it manually using the following SQL as a base and re-run Liquibase:\n\n" + createTableStatement);
            }
            statementsToExecute.add(createTableStatement);
            Scope.getCurrentScope().getLog(this.getClass()).info("Creating database history table with name: " + this.getDatabase().escapeTableName(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName()));
        }
        for (SqlStatement sqlStatement : statementsToExecute) {
            if (SqlGeneratorFactory.getInstance().supports(sqlStatement, database)) {
                ChangelogJdbcMdcListener.execute(this.getDatabase(), ex -> ex.execute(sql));
                this.getDatabase().commit();
                continue;
            }
            Scope.getCurrentScope().getLog(this.getClass()).info("Cannot run " + sqlStatement.getClass().getSimpleName() + " on " + this.getDatabase().getShortName() + " when checking databasechangelog table");
        }
        if (!statementsToExecute.isEmpty()) {
            this.ranChangeSetList = null;
        }
        this.serviceInitialized = true;
    }

    @Override
    public void upgradeChecksums(DatabaseChangeLog databaseChangeLog, Contexts contexts, LabelExpression labels) throws DatabaseException {
        super.upgradeChecksums(databaseChangeLog, contexts, labels);
        this.getDatabase().commit();
    }

    @Override
    public List<RanChangeSet> getRanChangeSets() throws DatabaseException {
        if (this.ranChangeSetList == null) {
            Database database = this.getDatabase();
            String databaseChangeLogTableName = this.getDatabase().escapeTableName(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName(), this.getDatabaseChangeLogTableName());
            ArrayList<RanChangeSet> ranChangeSets = new ArrayList<RanChangeSet>();
            if (this.hasDatabaseChangeLogTable()) {
                Scope.getCurrentScope().getLog(this.getClass()).info("Reading from " + databaseChangeLogTableName);
                List<Map<String, ?>> results = this.queryDatabaseChangeLogTable(database);
                for (Map<String, ?> rs : results) {
                    String storedFileName = rs.get("FILENAME").toString();
                    String fileName = DatabaseChangeLog.normalizePath(storedFileName);
                    String author = rs.get("AUTHOR").toString();
                    String id = rs.get("ID").toString();
                    String md5sum = rs.get("MD5SUM") == null ? null : rs.get("MD5SUM").toString();
                    String description = rs.get("DESCRIPTION") == null ? null : rs.get("DESCRIPTION").toString();
                    String comments = rs.get("COMMENTS") == null ? null : rs.get("COMMENTS").toString();
                    Object tmpDateExecuted = rs.get("DATEEXECUTED");
                    Date dateExecuted = null;
                    if (tmpDateExecuted instanceof Date) {
                        dateExecuted = (Date)tmpDateExecuted;
                    } else if (tmpDateExecuted instanceof LocalDateTime) {
                        dateExecuted = Date.from(((LocalDateTime)tmpDateExecuted).atZone(ZoneId.systemDefault()).toInstant());
                    } else {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            dateExecuted = df.parse((String)tmpDateExecuted);
                        }
                        catch (ParseException parseException) {
                            // empty catch block
                        }
                    }
                    String tmpOrderExecuted = rs.get("ORDEREXECUTED").toString();
                    Integer orderExecuted = tmpOrderExecuted == null ? null : Integer.valueOf(tmpOrderExecuted);
                    String tag = rs.get("TAG") == null ? null : rs.get("TAG").toString();
                    String execType = rs.get("EXECTYPE") == null ? null : rs.get("EXECTYPE").toString();
                    ContextExpression contexts = new ContextExpression((String)rs.get("CONTEXTS"));
                    Labels labels = new Labels((String)rs.get("LABELS"));
                    String deploymentId = (String)rs.get("DEPLOYMENT_ID");
                    String liquibaseVersion = rs.get("LIQUIBASE") == null ? null : rs.get("LIQUIBASE").toString();
                    try {
                        RanChangeSet ranChangeSet = new RanChangeSet(fileName, id, author, CheckSum.parse(md5sum), dateExecuted, tag, ChangeSet.ExecType.valueOf(execType), description, comments, contexts, labels, deploymentId, storedFileName);
                        ranChangeSet.setOrderExecuted(orderExecuted);
                        ranChangeSet.setLiquibaseVersion(liquibaseVersion);
                        ranChangeSets.add(ranChangeSet);
                    }
                    catch (IllegalArgumentException e) {
                        Scope.getCurrentScope().getLog(this.getClass()).severe("Unknown EXECTYPE from database: " + execType);
                        throw e;
                    }
                }
            }
            this.ranChangeSetList = ranChangeSets;
        }
        return Collections.unmodifiableList(this.ranChangeSetList);
    }

    public List<Map<String, ?>> queryDatabaseChangeLogTable(Database database) throws DatabaseException {
        SelectFromDatabaseChangeLogStatement select = new SelectFromDatabaseChangeLogStatement(new ColumnConfig().setName("*").setComputed(true)).setOrderBy("DATEEXECUTED ASC", "ORDEREXECUTED ASC");
        return ChangelogJdbcMdcListener.query(this.getDatabase(), executor2 -> executor2.queryForList(select));
    }

    @Override
    public RanChangeSet getRanChangeSet(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        if (!this.hasDatabaseChangeLogTable()) {
            return null;
        }
        return super.getRanChangeSet(changeSet);
    }

    @Override
    public void setExecType(ChangeSet changeSet, ChangeSet.ExecType execType) throws DatabaseException {
        MarkChangeSetRanStatement markChangeSetRanStatement = new MarkChangeSetRanStatement(changeSet, execType);
        ChangelogJdbcMdcListener.execute(this.getDatabase(), executor2 -> executor2.execute(markChangeSetRanStatement));
        this.getDatabase().commit();
        if (this.ranChangeSetList != null) {
            this.ranChangeSetList.add(new RanChangeSet(changeSet, execType, null, null));
        }
    }

    @Override
    public void removeFromHistory(ChangeSet changeSet) throws DatabaseException {
        RemoveChangeSetRanStatusStatement removeChangeSetRanStatusStatement = new RemoveChangeSetRanStatusStatement(changeSet);
        ChangelogJdbcMdcListener.execute(this.getDatabase(), executor2 -> executor2.execute(removeChangeSetRanStatusStatement));
        this.getDatabase().commit();
        if (this.ranChangeSetList != null) {
            this.ranChangeSetList.remove(new RanChangeSet(changeSet));
        }
    }

    @Override
    public int getNextSequenceValue() throws LiquibaseException {
        if (this.lastChangeSetSequenceValue == null) {
            if (this.getDatabase().getConnection() == null) {
                this.lastChangeSetSequenceValue = 0;
            } else {
                GetNextChangeSetSequenceValueStatement nextChangeSetSequenceValueStatement = new GetNextChangeSetSequenceValueStatement();
                this.lastChangeSetSequenceValue = ChangelogJdbcMdcListener.query(this.getDatabase(), executor2 -> executor2.queryForInt(nextChangeSetSequenceValueStatement));
            }
        }
        this.lastChangeSetSequenceValue = this.lastChangeSetSequenceValue + 1;
        return this.lastChangeSetSequenceValue;
    }

    @Override
    public void tag(String tagString) throws DatabaseException {
        SelectFromDatabaseChangeLogStatement totalRowsStatement = new SelectFromDatabaseChangeLogStatement(new ColumnConfig().setName("COUNT(*)", true));
        int totalRows = ChangelogJdbcMdcListener.query(this.getDatabase(), executor2 -> executor2.queryForInt(totalRowsStatement));
        if (totalRows == 0) {
            ChangeSet emptyChangeSet = new ChangeSet(String.valueOf(new Date().getTime()), "liquibase", false, false, "liquibase-internal", null, null, this.getDatabase().getObjectQuotingStrategy(), null);
            this.setExecType(emptyChangeSet, ChangeSet.ExecType.EXECUTED);
        }
        TagDatabaseStatement tagStatement = new TagDatabaseStatement(tagString);
        ChangelogJdbcMdcListener.execute(this.getDatabase(), executor2 -> executor2.execute(tagStatement));
        this.getDatabase().commit();
        if (this.ranChangeSetList != null) {
            this.ranChangeSetList.get(this.ranChangeSetList.size() - 1).setTag(tagString);
        }
    }

    @Override
    public boolean tagExists(String tag) throws DatabaseException {
        SelectFromDatabaseChangeLogStatement selectChangelogStatement = new SelectFromDatabaseChangeLogStatement(new SelectFromDatabaseChangeLogStatement.ByTag(tag), new ColumnConfig().setName("COUNT(*)", true));
        int count = ChangelogJdbcMdcListener.query(this.getDatabase(), executor2 -> executor2.queryForInt(selectChangelogStatement));
        return count > 0;
    }

    @Override
    public void clearAllCheckSums() throws LiquibaseException {
        Database database = this.getDatabase();
        UpdateStatement updateStatement = new UpdateStatement(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogTableName());
        updateStatement.addNewColumnValue("MD5SUM", null);
        ChangelogJdbcMdcListener.execute(this.getDatabase(), executor2 -> executor2.execute(updateStatement));
        database.commit();
    }

    @Override
    public void destroy() throws DatabaseException {
        Database database = this.getDatabase();
        try {
            Relation example = new Table().setName(database.getDatabaseChangeLogTableName()).setSchema(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName());
            if (SnapshotGeneratorFactory.getInstance().has(example, database)) {
                Relation table = SnapshotGeneratorFactory.getInstance().createSnapshot(example, database);
                DiffOutputControl diffOutputControl = new DiffOutputControl(true, true, false, null);
                Change[] change = ChangeGeneratorFactory.getInstance().fixUnexpected(table, diffOutputControl, database, database);
                SqlStatement[] sqlStatement = change[0].generateStatements(database);
                ChangelogJdbcMdcListener.execute(this.getDatabase(), executor2 -> executor2.execute(sqlStatement[0]));
            }
            this.reset();
        }
        catch (InvalidExampleException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    protected String getLabelsSize() {
        return "255";
    }

    protected String getContextsSize() {
        return "255";
    }

    @Override
    public boolean isDatabaseChecksumsCompatible() {
        return this.databaseChecksumsCompatible;
    }
}

