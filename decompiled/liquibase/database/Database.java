/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.core.DropTableChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.database.DatabaseConnection;
import liquibase.database.LiquibaseTableNamesFactory;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.TempObjectQuotingStrategy;
import liquibase.diff.DiffGeneratorFactory;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.exception.DateParseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.ExecutorService;
import liquibase.servicelocator.PrioritizedService;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.EmptyDatabaseSnapshot;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.sql.Sql;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.StringUtil;

public interface Database
extends PrioritizedService,
AutoCloseable {
    public static final String databaseChangeLogTableName = "DatabaseChangeLog".toUpperCase(Locale.US);
    public static final String databaseChangeLogLockTableName = "DatabaseChangeLogLock".toUpperCase(Locale.US);
    public static final String COMPLETE_SQL_SCOPE_KEY = "completeSql";

    public boolean isCorrectDatabaseImplementation(DatabaseConnection var1) throws DatabaseException;

    public String getDefaultDriver(String var1);

    public DatabaseConnection getConnection();

    public void setConnection(DatabaseConnection var1);

    public boolean requiresUsername();

    public boolean requiresPassword();

    public boolean getAutoCommitMode();

    public boolean supportsDDLInTransaction();

    public String getDatabaseProductName();

    public String getDatabaseProductVersion() throws DatabaseException;

    public int getDatabaseMajorVersion() throws DatabaseException;

    public int getDatabaseMinorVersion() throws DatabaseException;

    public String getShortName();

    default public String getDisplayName() {
        return this.getShortName();
    }

    public String getDefaultCatalogName();

    public void setDefaultCatalogName(String var1) throws DatabaseException;

    public String getDefaultSchemaName();

    public Integer getDefaultScaleForNativeDataType(String var1);

    public void setDefaultSchemaName(String var1) throws DatabaseException;

    public Integer getDefaultPort();

    public Integer getFetchSize();

    public String getLiquibaseCatalogName();

    public void setLiquibaseCatalogName(String var1);

    public String getLiquibaseSchemaName();

    public void setLiquibaseSchemaName(String var1);

    public boolean supportsInitiallyDeferrableColumns();

    @Deprecated
    public boolean supportsSequences();

    public boolean supportsDropTableCascadeConstraints();

    public boolean supportsAutoIncrement();

    public String getDateLiteral(String var1);

    public String getCurrentDateTimeFunction();

    public void setCurrentDateTimeFunction(String var1);

    public String getLineComment();

    public String getAutoIncrementClause(BigInteger var1, BigInteger var2, String var3, Boolean var4);

    public String getDatabaseChangeLogTableName();

    public void setDatabaseChangeLogTableName(String var1);

    public String getDatabaseChangeLogLockTableName();

    public void setDatabaseChangeLogLockTableName(String var1);

    public String getLiquibaseTablespaceName();

    public void setLiquibaseTablespaceName(String var1);

    public String getConcatSql(String ... var1);

    public void setCanCacheLiquibaseTableInfo(boolean var1);

    public void dropDatabaseObjects(CatalogAndSchema var1) throws LiquibaseException;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    default public void dropDatabaseObjects(CatalogAndSchema schemaToDrop, SnapshotControl snapshotControl) throws LiquibaseException {
        if (snapshotControl == null) {
            snapshotControl = new SnapshotControl(this);
        }
        ObjectQuotingStrategy currentStrategy = this.getObjectQuotingStrategy();
        this.setObjectQuotingStrategy(ObjectQuotingStrategy.QUOTE_ALL_OBJECTS);
        try {
            DatabaseSnapshot snapshot;
            try {
                Set<Class<? extends DatabaseObject>> typesToInclude = snapshotControl.getTypesToInclude();
                typesToInclude.remove(Index.class);
                typesToInclude.remove(PrimaryKey.class);
                typesToInclude.remove(UniqueConstraint.class);
                if (this.supportsForeignKeyDisable() || this.getShortName().equals("postgresql")) {
                    typesToInclude.remove(ForeignKey.class);
                }
                long createSnapshotStarted = System.currentTimeMillis();
                snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(schemaToDrop, this, snapshotControl);
                Scope.getCurrentScope().getLog(this.getClass()).fine(String.format("Database snapshot generated in %d ms. Snapshot includes: %s", System.currentTimeMillis() - createSnapshotStarted, typesToInclude));
            }
            catch (LiquibaseException e) {
                throw new UnexpectedLiquibaseException(e);
            }
            long changeSetStarted = System.currentTimeMillis();
            CompareControl compareControl = new CompareControl(new CompareControl.SchemaComparison[]{new CompareControl.SchemaComparison(CatalogAndSchema.DEFAULT, schemaToDrop)}, snapshot.getSnapshotControl().getTypesToInclude());
            DiffResult diffResult = DiffGeneratorFactory.getInstance().compare(new EmptyDatabaseSnapshot(this), snapshot, compareControl);
            List<ChangeSet> changeSets = new DiffToChangeLog(diffResult, new DiffOutputControl(true, true, false, null).addIncludedSchema(schemaToDrop)).generateChangeSets();
            Scope.getCurrentScope().getLog(this.getClass()).fine(String.format("ChangeSet to Remove Database Objects generated in %d ms.", System.currentTimeMillis() - changeSetStarted));
            boolean previousAutoCommit = this.getAutoCommitMode();
            this.commit();
            this.setAutoCommit(false);
            boolean reEnableFK = this.supportsForeignKeyDisable() && this.disableForeignKeyChecks();
            StringBuilder completeSql = new StringBuilder();
            try {
                for (ChangeSet changeSet : changeSets) {
                    changeSet.setFailOnError(false);
                    for (Change change : changeSet.getChanges()) {
                        SqlStatement[] sqlStatements;
                        if (change instanceof DropTableChange) {
                            ((DropTableChange)change).setCascadeConstraints(true);
                        }
                        for (SqlStatement statement : sqlStatements = change.generateStatements(this)) {
                            AtomicReference<Object> sqls = new AtomicReference<Object>(null);
                            Scope.child(Collections.singletonMap("generatedSqlArray", sqls), () -> Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).execute(statement));
                            if (StringUtil.isNotEmpty(completeSql.toString()) && !completeSql.toString().endsWith("; ")) {
                                completeSql.append("; ");
                            }
                            completeSql.append(StringUtil.join(Arrays.stream((Sql[])sqls.get()).map(Sql::toSql).collect(Collectors.toList()), "; "));
                        }
                    }
                    this.commit();
                }
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
            finally {
                if (reEnableFK) {
                    this.enableForeignKeyChecks();
                }
            }
            LiquibaseTableNamesFactory liquibaseTableNamesFactory = Scope.getCurrentScope().getSingleton(LiquibaseTableNamesFactory.class);
            liquibaseTableNamesFactory.destroy(this);
            this.setAutoCommit(previousAutoCommit);
            Scope.getCurrentScope().getLog(this.getClass()).info(String.format("Successfully deleted all supported object types in schema %s.", schemaToDrop.toString()));
            this.addCompleteSqlToScope(completeSql.toString());
        }
        finally {
            this.setObjectQuotingStrategy(currentStrategy);
            this.commit();
        }
    }

    default public void addCompleteSqlToScope(String completeSql) {
        AtomicReference sqlsReference = (AtomicReference)((Object)Scope.getCurrentScope().get(COMPLETE_SQL_SCOPE_KEY, AtomicReference.class));
        if (sqlsReference != null) {
            sqlsReference.set(completeSql.toString());
        }
    }

    @Deprecated
    public void tag(String var1) throws DatabaseException;

    @Deprecated
    public boolean doesTagExist(String var1) throws DatabaseException;

    public boolean isSystemObject(DatabaseObject var1);

    public boolean isLiquibaseObject(DatabaseObject var1);

    public String getViewDefinition(CatalogAndSchema var1, String var2) throws DatabaseException;

    public String getDateLiteral(Date var1);

    public String getTimeLiteral(Time var1);

    public String getDateTimeLiteral(Timestamp var1);

    public String getDateLiteral(java.util.Date var1);

    public String escapeObjectName(String var1, String var2, String var3, Class<? extends DatabaseObject> var4);

    public String escapeTableName(String var1, String var2, String var3);

    public String escapeIndexName(String var1, String var2, String var3);

    public String escapeObjectName(String var1, Class<? extends DatabaseObject> var2);

    public int getMaxFractionalDigitsForTimestamp();

    public int getDefaultFractionalDigitsForTimestamp();

    public String escapeColumnName(String var1, String var2, String var3, String var4);

    @Deprecated
    public String escapeColumnName(String var1, String var2, String var3, String var4, boolean var5);

    public String escapeColumnNameList(String var1);

    public boolean supportsTablespaces();

    @Deprecated
    public boolean supportsCatalogs();

    default public boolean supports(Class<? extends DatabaseObject> object) {
        if (Sequence.class.isAssignableFrom(object)) {
            return this.supportsSequences();
        }
        if (Schema.class.isAssignableFrom(object)) {
            return this.supportsSchemas();
        }
        if (Catalog.class.isAssignableFrom(object)) {
            return this.supportsCatalogs();
        }
        return true;
    }

    public CatalogAndSchema.CatalogAndSchemaCase getSchemaAndCatalogCase();

    @Deprecated
    public boolean supportsSchemas();

    public boolean supportsCatalogInObjectName(Class<? extends DatabaseObject> var1);

    public String generatePrimaryKeyName(String var1);

    public String escapeSequenceName(String var1, String var2, String var3);

    public String escapeViewName(String var1, String var2, String var3);

    public ChangeSet.RunStatus getRunStatus(ChangeSet var1) throws DatabaseException, DatabaseHistoryException;

    public RanChangeSet getRanChangeSet(ChangeSet var1) throws DatabaseException, DatabaseHistoryException;

    public void markChangeSetExecStatus(ChangeSet var1, ChangeSet.ExecType var2) throws DatabaseException;

    public List<RanChangeSet> getRanChangeSetList() throws DatabaseException;

    public java.util.Date getRanDate(ChangeSet var1) throws DatabaseException, DatabaseHistoryException;

    public void removeRanStatus(ChangeSet var1) throws DatabaseException;

    public void commit() throws DatabaseException;

    public void rollback() throws DatabaseException;

    public String escapeStringForDatabase(String var1);

    @Override
    public void close() throws DatabaseException;

    public boolean supportsRestrictForeignKeys();

    public String escapeConstraintName(String var1);

    public boolean isAutoCommit() throws DatabaseException;

    public void setAutoCommit(boolean var1) throws DatabaseException;

    public boolean isSafeToRunUpdate() throws DatabaseException;

    public void executeStatements(Change var1, DatabaseChangeLog var2, List<SqlVisitor> var3) throws LiquibaseException;

    public void execute(SqlStatement[] var1, List<SqlVisitor> var2) throws LiquibaseException;

    public void saveStatements(Change var1, List<SqlVisitor> var2, Writer var3) throws IOException, LiquibaseException;

    public void executeRollbackStatements(Change var1, List<SqlVisitor> var2) throws LiquibaseException;

    public void executeRollbackStatements(SqlStatement[] var1, List<SqlVisitor> var2) throws LiquibaseException;

    public void saveRollbackStatement(Change var1, List<SqlVisitor> var2, Writer var3) throws IOException, LiquibaseException;

    public java.util.Date parseDate(String var1) throws DateParseException;

    public List<DatabaseFunction> getDateFunctions();

    public void resetInternalState();

    public boolean supportsForeignKeyDisable();

    public boolean disableForeignKeyChecks() throws DatabaseException;

    public void enableForeignKeyChecks() throws DatabaseException;

    public boolean isCaseSensitive();

    public boolean isReservedWord(String var1);

    @Deprecated
    public CatalogAndSchema correctSchema(CatalogAndSchema var1);

    public String correctObjectName(String var1, Class<? extends DatabaseObject> var2);

    public boolean isFunction(String var1);

    public int getDataTypeMaxParameters(String var1);

    public CatalogAndSchema getDefaultSchema();

    public boolean dataTypeIsNotModifiable(String var1);

    public String generateDatabaseFunctionValue(DatabaseFunction var1);

    public ObjectQuotingStrategy getObjectQuotingStrategy();

    public void setObjectQuotingStrategy(ObjectQuotingStrategy var1);

    public boolean createsIndexesForForeignKeys();

    public boolean getOutputDefaultSchema();

    public void setOutputDefaultSchema(boolean var1);

    public boolean isDefaultSchema(String var1, String var2);

    public boolean isDefaultCatalog(String var1);

    public boolean getOutputDefaultCatalog();

    public void setOutputDefaultCatalog(boolean var1);

    public boolean supportsPrimaryKeyNames();

    public boolean supportsNotNullConstraintNames();

    public boolean supportsBatchUpdates() throws DatabaseException;

    public boolean requiresExplicitNullForColumns();

    public String getSystemSchema();

    public void addReservedWords(Collection<String> var1);

    public String escapeDataTypeName(String var1);

    public String unescapeDataTypeName(String var1);

    public String unescapeDataTypeString(String var1);

    public ValidationErrors validate();

    default public boolean failOnDefferable() {
        return true;
    }

    default public void afterUpdate() throws LiquibaseException {
    }

    default public TempObjectQuotingStrategy temporarilySetObjectQuotingStrategy(ObjectQuotingStrategy objectQuotingStrategy) {
        ObjectQuotingStrategy originalObjectQuotingStrategy = this.getObjectQuotingStrategy();
        this.setObjectQuotingStrategy(objectQuotingStrategy);
        return new TempObjectQuotingStrategy(this, originalObjectQuotingStrategy);
    }

    default public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return false;
    }

    default public boolean supportsDatabaseChangeLogHistory() {
        return false;
    }

    default public void checkDatabaseConnection() throws DatabaseException {
    }

    default public String generateConnectCommandSuccessMessage() {
        return null;
    }
}

