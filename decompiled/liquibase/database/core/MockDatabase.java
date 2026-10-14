/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.io.Writer;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import liquibase.CatalogAndSchema;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.InternalDatabase;
import liquibase.database.MockDatabaseConnection;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.exception.ValidationErrors;
import liquibase.lockservice.DatabaseChangeLogLock;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;

public class MockDatabase
implements Database,
InternalDatabase {
    private static final int FETCH_SIZE = 1000;
    private boolean outputDefaultSchema;
    private boolean outputDefaultCatalog;
    private boolean supportsCatalogs = true;
    private boolean supportsSchemas = true;
    private boolean supportsSequences = true;
    private String defaultCatalogName;
    private String defaultSchemaName;
    private boolean caseSensitive;
    private DatabaseConnection connection = new MockDatabaseConnection();
    private String currentDateTimeFunction = "DATETIME()";

    @Override
    public Integer getDefaultScaleForNativeDataType(String nativeDataType) {
        return null;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public Schema getSchema() {
        return null;
    }

    public String getName() {
        return "Mock Database";
    }

    @Override
    public CatalogAndSchema getDefaultSchema() {
        return new CatalogAndSchema("default", "default");
    }

    @Override
    public Integer getDefaultPort() {
        return null;
    }

    public DatabaseObject[] getContainingObjects() {
        return null;
    }

    public boolean equals(DatabaseObject otherObject, Database accordingTo) {
        return otherObject.getName().equalsIgnoreCase(this.getName());
    }

    @Override
    public void setCanCacheLiquibaseTableInfo(boolean canCacheLiquibaseTableInfo) {
    }

    @Override
    public boolean requiresUsername() {
        return false;
    }

    @Override
    public boolean requiresPassword() {
        return false;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) {
        return false;
    }

    @Override
    public String getDefaultDriver(String url2) {
        return null;
    }

    @Override
    public DatabaseConnection getConnection() {
        return this.connection;
    }

    @Override
    public void setConnection(DatabaseConnection conn) {
        this.connection = conn;
    }

    public void setConnection(Connection conn) {
    }

    @Override
    public boolean getAutoCommitMode() {
        return false;
    }

    @Override
    public boolean isAutoCommit() {
        return false;
    }

    @Override
    public void setAutoCommit(boolean b) {
    }

    @Override
    public boolean isCaseSensitive() {
        return this.caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean supportsDDLInTransaction() {
        return false;
    }

    @Override
    public String getDatabaseProductName() {
        return null;
    }

    @Override
    public String getDatabaseProductVersion() {
        return null;
    }

    @Override
    public int getDatabaseMajorVersion() {
        return 0;
    }

    @Override
    public int getDatabaseMinorVersion() {
        return 0;
    }

    @Override
    public String getShortName() {
        return "mock";
    }

    public String getDriverName() {
        return null;
    }

    public String getConnectionURL() {
        return null;
    }

    public String getConnectionUsername() {
        return null;
    }

    @Override
    public String getDefaultCatalogName() {
        return this.defaultCatalogName;
    }

    @Override
    public void setDefaultCatalogName(String catalogName) {
        this.defaultCatalogName = catalogName;
    }

    @Override
    public String getDefaultSchemaName() {
        return this.defaultSchemaName;
    }

    @Override
    public void setDefaultSchemaName(String schemaName) {
        this.defaultSchemaName = schemaName;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supportsSequences() {
        return this.supportsSequences;
    }

    public void setSupportsSequences(boolean supportsSequences) {
        this.supportsSequences = supportsSequences;
    }

    @Override
    public boolean supportsDropTableCascadeConstraints() {
        return false;
    }

    @Override
    public boolean supportsAutoIncrement() {
        return true;
    }

    @Override
    public String getDateLiteral(String isoDate) {
        return isoDate;
    }

    @Override
    public String getDateLiteral(Date date) {
        return date.toString();
    }

    @Override
    public String getTimeLiteral(Time time) {
        return time.toString();
    }

    @Override
    public String getDateTimeLiteral(Timestamp timeStamp) {
        return timeStamp.toString();
    }

    @Override
    public String getCurrentDateTimeFunction() {
        return this.currentDateTimeFunction;
    }

    @Override
    public void setCurrentDateTimeFunction(String function) {
        this.currentDateTimeFunction = function;
    }

    @Override
    public String getLineComment() {
        return "--";
    }

    @Override
    public String getAutoIncrementClause(BigInteger startWith, BigInteger incrementBy, String generationType, Boolean defaultOnNull) {
        if (startWith != null) {
            return " " + startWith;
        }
        return " " + incrementBy;
    }

    public SqlStatement getCommitSQL() {
        return null;
    }

    @Override
    public String getDatabaseChangeLogTableName() {
        return "DATABASECHANGELOG";
    }

    @Override
    public void setDatabaseChangeLogTableName(String tableName) {
    }

    @Override
    public String getDatabaseChangeLogLockTableName() {
        return "DATABASECHANGELOGLOCK";
    }

    @Override
    public void setDatabaseChangeLogLockTableName(String tableName) {
    }

    @Override
    public String getConcatSql(String ... values2) {
        return null;
    }

    public boolean acquireLock(Liquibase liquibase) {
        return false;
    }

    public void releaseLock() {
    }

    public DatabaseChangeLogLock[] listLocks() {
        return new DatabaseChangeLogLock[0];
    }

    @Override
    public void dropDatabaseObjects(CatalogAndSchema schema) {
    }

    @Override
    public void tag(String tagString) {
    }

    @Override
    public boolean doesTagExist(String tag) {
        return false;
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        return false;
    }

    @Override
    public boolean isLiquibaseObject(DatabaseObject object) {
        return false;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String name) {
        return null;
    }

    public String getDatabaseProductName(DatabaseConnection conn) {
        return "Mock Database";
    }

    @Override
    public String getDateLiteral(java.util.Date defaultDateValue) {
        return defaultDateValue.toString();
    }

    @Override
    public String escapeTableName(String catalogName, String schemaName, String tableName) {
        if (schemaName == null) {
            return tableName;
        }
        return schemaName + "." + tableName;
    }

    @Override
    public String escapeIndexName(String catalogName, String schemaName, String indexName) {
        return this.escapeTableName(catalogName, schemaName, indexName);
    }

    @Override
    public String escapeColumnName(String catalogName, String schemaName, String tableName, String columnName) {
        return columnName;
    }

    @Override
    public String escapeColumnName(String catalogName, String schemaName, String tableName, String columnName, boolean quoteNamesThatMayBeFunctions) {
        return columnName;
    }

    @Override
    public String escapeColumnNameList(String columnNames) {
        return columnNames;
    }

    @Override
    public String escapeSequenceName(String catalogName, String schemaName, String sequenceName) {
        if (sequenceName == null) {
            return sequenceName;
        }
        return schemaName + "." + sequenceName;
    }

    public String convertRequestedSchemaToSchema(String requestedSchema) {
        return requestedSchema;
    }

    public String convertRequestedSchemaToCatalog(String requestedSchema) {
        return null;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Schema.class.isAssignableFrom(object)) {
            return this.supportsSchemas;
        }
        if (Catalog.class.isAssignableFrom(object)) {
            return this.supportsCatalogs;
        }
        if (Sequence.class.isAssignableFrom(object)) {
            return this.supportsSequences;
        }
        return true;
    }

    @Override
    public boolean supportsSchemas() {
        return this.supportsSchemas;
    }

    public void setSupportsSchemas(boolean supportsSchemas) {
        this.supportsSchemas = supportsSchemas;
    }

    @Override
    public boolean supportsCatalogs() {
        return this.supportsCatalogs;
    }

    @Override
    public CatalogAndSchema.CatalogAndSchemaCase getSchemaAndCatalogCase() {
        return CatalogAndSchema.CatalogAndSchemaCase.UPPER_CASE;
    }

    public void setSupportsCatalogs(boolean supportsCatalogs) {
        this.supportsCatalogs = supportsCatalogs;
    }

    public boolean supportsCatalogInObjectName() {
        return true;
    }

    @Override
    public String generatePrimaryKeyName(String tableName) {
        return "PK_" + tableName;
    }

    @Override
    public String escapeViewName(String catalogName, String schemaName, String viewName) {
        return this.escapeTableName(catalogName, schemaName, viewName);
    }

    public boolean acquireLock() {
        return false;
    }

    @Override
    public ChangeSet.RunStatus getRunStatus(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        return null;
    }

    @Override
    public RanChangeSet getRanChangeSet(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        return Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).getRanChangeSet(changeSet);
    }

    @Override
    public void markChangeSetExecStatus(ChangeSet changeSet, ChangeSet.ExecType execType) throws DatabaseException {
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).setExecType(changeSet, execType);
    }

    @Override
    public List<RanChangeSet> getRanChangeSetList() throws DatabaseException {
        return Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).getRanChangeSets();
    }

    @Override
    public java.util.Date getRanDate(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        return this.getRanChangeSet(changeSet).getDateExecuted();
    }

    @Override
    public void removeRanStatus(ChangeSet changeSet) throws DatabaseException {
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).removeFromHistory(changeSet);
    }

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }

    public SqlStatement getSelectChangeLogLockSQL() {
        return null;
    }

    @Override
    public String escapeStringForDatabase(String string) {
        return string;
    }

    @Override
    public void close() {
    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return true;
    }

    @Override
    public String escapeConstraintName(String constraintName) {
        return constraintName;
    }

    @Override
    public boolean isSafeToRunUpdate() {
        return true;
    }

    @Override
    public String escapeObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        return objectName;
    }

    @Override
    public String escapeObjectName(String catalogName, String schemaName, String objectName, Class<? extends DatabaseObject> objectType) {
        return catalogName + "." + schemaName + "." + objectName;
    }

    @Override
    public void executeStatements(Change change, DatabaseChangeLog changeLog, List<SqlVisitor> sqlVisitors) {
    }

    @Override
    public void execute(SqlStatement[] statements, List<SqlVisitor> sqlVisitors) {
    }

    @Override
    public void saveStatements(Change change, List<SqlVisitor> sqlVisitors, Writer writer) {
    }

    @Override
    public void executeRollbackStatements(Change change, List<SqlVisitor> sqlVisitors) {
    }

    @Override
    public void executeRollbackStatements(SqlStatement[] statements, List<SqlVisitor> sqlVisitors) {
    }

    @Override
    public void saveRollbackStatement(Change change, List<SqlVisitor> sqlVisitors, Writer writer) {
    }

    @Override
    public String getLiquibaseCatalogName() {
        return null;
    }

    @Override
    public void setLiquibaseCatalogName(String catalogName) {
    }

    @Override
    public String getLiquibaseSchemaName() {
        return null;
    }

    @Override
    public void setLiquibaseSchemaName(String schemaName) {
    }

    @Override
    public String getLiquibaseTablespaceName() {
        return null;
    }

    @Override
    public void setLiquibaseTablespaceName(String tablespaceName) {
    }

    @Override
    public java.util.Date parseDate(String dateAsString) {
        return new java.util.Date();
    }

    @Override
    public List<DatabaseFunction> getDateFunctions() {
        return null;
    }

    @Override
    public void resetInternalState() {
    }

    @Override
    public boolean supportsForeignKeyDisable() {
        return false;
    }

    @Override
    public boolean disableForeignKeyChecks() {
        return false;
    }

    @Override
    public void enableForeignKeyChecks() {
    }

    public void updateChecksum(ChangeSet changeSet) {
    }

    @Override
    public boolean isReservedWord(String string) {
        return false;
    }

    @Override
    public CatalogAndSchema correctSchema(CatalogAndSchema schema) {
        return schema.standardize(this);
    }

    @Override
    public String correctObjectName(String name, Class<? extends DatabaseObject> objectType) {
        if (name == null) {
            return null;
        }
        String finalName = name.toLowerCase();
        finalName = finalName.substring(0, finalName.length() - 1) + finalName.substring(finalName.length() - 1, finalName.length()).toUpperCase();
        return finalName;
    }

    public String correctObjectName(String name, Class<? extends DatabaseObject> objectType, boolean quoteCorrectedName) {
        return this.correctObjectName(name, objectType);
    }

    @Override
    public Integer getFetchSize() {
        return 1000;
    }

    @Override
    public boolean isFunction(String string) {
        return string.endsWith("()");
    }

    @Override
    public int getDataTypeMaxParameters(String dataTypeName) {
        return 2;
    }

    public CatalogAndSchema getSchemaFromJdbcInfo(String rawCatalogName, String rawSchemaName) {
        return new CatalogAndSchema(rawCatalogName, rawSchemaName);
    }

    public String getJdbcCatalogName(CatalogAndSchema schema) {
        return schema.getCatalogName();
    }

    public String getJdbcSchemaName(CatalogAndSchema schema) {
        return schema.getSchemaName();
    }

    public String getJdbcCatalogName(Schema schema) {
        return schema.getCatalogName();
    }

    public String getJdbcSchemaName(Schema schema) {
        return schema.getName();
    }

    @Override
    public boolean dataTypeIsNotModifiable(String typeName) {
        return true;
    }

    @Override
    public String generateDatabaseFunctionValue(DatabaseFunction databaseFunction) {
        return null;
    }

    @Override
    public ObjectQuotingStrategy getObjectQuotingStrategy() {
        return ObjectQuotingStrategy.LEGACY;
    }

    @Override
    public void setObjectQuotingStrategy(ObjectQuotingStrategy quotingStrategy) {
    }

    @Override
    public boolean supportsCatalogInObjectName(Class<? extends DatabaseObject> type) {
        return true;
    }

    @Override
    public boolean createsIndexesForForeignKeys() {
        return false;
    }

    @Override
    public boolean getOutputDefaultSchema() {
        return this.outputDefaultSchema;
    }

    @Override
    public void setOutputDefaultSchema(boolean outputDefaultSchema) {
        this.outputDefaultSchema = outputDefaultSchema;
    }

    @Override
    public boolean getOutputDefaultCatalog() {
        return this.outputDefaultCatalog;
    }

    @Override
    public void setOutputDefaultCatalog(boolean outputDefaultCatalog) {
        this.outputDefaultCatalog = outputDefaultCatalog;
    }

    @Override
    public boolean isDefaultSchema(String catalog, String schema) {
        return false;
    }

    @Override
    public boolean isDefaultCatalog(String catalog) {
        return false;
    }

    @Override
    public boolean supportsPrimaryKeyNames() {
        return true;
    }

    @Override
    public String getSystemSchema() {
        return "information_schema";
    }

    @Override
    public void addReservedWords(Collection<String> words) {
    }

    public String toString() {
        return "Mock database";
    }

    @Override
    public String escapeDataTypeName(String dataTypeName) {
        return dataTypeName;
    }

    @Override
    public String unescapeDataTypeName(String dataTypeName) {
        return dataTypeName;
    }

    @Override
    public String unescapeDataTypeString(String dataTypeString) {
        return dataTypeString;
    }

    @Override
    public ValidationErrors validate() {
        return new ValidationErrors();
    }

    @Override
    public boolean supportsNotNullConstraintNames() {
        return false;
    }

    @Override
    public boolean supportsBatchUpdates() {
        return false;
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        return 9;
    }

    @Override
    public int getDefaultFractionalDigitsForTimestamp() {
        return 6;
    }

    @Override
    public boolean requiresExplicitNullForColumns() {
        return false;
    }

    @Override
    public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return false;
    }
}

