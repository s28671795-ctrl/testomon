/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import liquibase.CatalogAndSchema;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.AbstractSQLChange;
import liquibase.change.Change;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.ExecutorService;
import liquibase.sql.visitor.AppendSqlIfNotPresentVisitor;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Index;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.JdbcUtil;
import liquibase.util.StringUtil;

public class MSSQLDatabase
extends AbstractJdbcDatabase {
    public static final String PRODUCT_NAME = "Microsoft SQL Server";
    private final HashMap<String, Integer> defaultDataTypeParameters = new HashMap();
    protected static final int MSSQL_DEFAULT_TCP_PORT = 1433;
    private static final Pattern CREATE_VIEW_AS_PATTERN = Pattern.compile("(?im)^\\s*(CREATE|ALTER)\\s+VIEW\\s+(\\S+)\\s+?AS\\s*", 34);
    protected Set<String> systemTablesAndViews = new HashSet<String>();
    private Boolean sendsStringParametersAsUnicode;

    public MSSQLDatabase() {
        super.setCurrentDateTimeFunction("GETDATE()");
        this.sequenceNextValueFunction = "NEXT VALUE FOR %s";
        this.systemTablesAndViews.add("syscolumns");
        this.systemTablesAndViews.add("syscomments");
        this.systemTablesAndViews.add("sysdepends");
        this.systemTablesAndViews.add("sysfilegroups");
        this.systemTablesAndViews.add("sysfiles");
        this.systemTablesAndViews.add("sysfiles1");
        this.systemTablesAndViews.add("sysforeignkeys");
        this.systemTablesAndViews.add("sysfulltextcatalogs");
        this.systemTablesAndViews.add("sysfulltextnotify");
        this.systemTablesAndViews.add("sysindexes");
        this.systemTablesAndViews.add("sysindexkeys");
        this.systemTablesAndViews.add("sysmembers");
        this.systemTablesAndViews.add("sysobjects");
        this.systemTablesAndViews.add("syspermissions");
        this.systemTablesAndViews.add("sysproperties");
        this.systemTablesAndViews.add("sysprotects");
        this.systemTablesAndViews.add("sysreferences");
        this.systemTablesAndViews.add("systypes");
        this.systemTablesAndViews.add("sysusers");
        this.systemTablesAndViews.add("sysdiagrams");
        this.systemTablesAndViews.add("syssegments");
        this.systemTablesAndViews.add("sysconstraints");
        this.defaultDataTypeParameters.put("datetime", 3);
        this.defaultDataTypeParameters.put("datetime2", 7);
        this.defaultDataTypeParameters.put("datetimeoffset", 7);
        this.defaultDataTypeParameters.put("time", 7);
        this.defaultDataTypeParameters.put("decimal", 0);
        this.defaultDataTypeParameters.put("numeric", 0);
        this.defaultDataTypeParameters.put("bigint", 0);
        this.defaultDataTypeParameters.put("int", 0);
        this.defaultDataTypeParameters.put("smallint", 0);
        this.defaultDataTypeParameters.put("tinyint", 0);
        this.defaultDataTypeParameters.put("money", 4);
        this.defaultDataTypeParameters.put("smallmoney", 0);
        this.unmodifiableDataTypes.add("datetime");
        this.addReservedWords(MSSQLDatabase.createReservedWordsCollection());
    }

    @Override
    public void executeStatements(Change change, DatabaseChangeLog changeLog, List<SqlVisitor> sqlVisitors) throws LiquibaseException {
        if (change instanceof AbstractSQLChange) {
            String endDelimiter = StringUtil.trimToNull(((AbstractSQLChange)change).getEndDelimiter());
            if (endDelimiter != null && !endDelimiter.contentEquals(";")) {
                super.executeStatements(change, changeLog, sqlVisitors);
            } else {
                super.executeStatements(change, changeLog, MSSQLDatabase.addSqlVisitors(sqlVisitors));
            }
        } else {
            super.executeStatements(change, changeLog, MSSQLDatabase.addSqlVisitors(sqlVisitors));
        }
    }

    protected static List<SqlVisitor> addSqlVisitors(List<SqlVisitor> sqlVisitors) {
        ArrayList<SqlVisitor> sqlChangeVisitors = new ArrayList<SqlVisitor>(sqlVisitors);
        AppendSqlIfNotPresentVisitor appendVisitor = new AppendSqlIfNotPresentVisitor();
        appendVisitor.setValue(";");
        sqlChangeVisitors.add(appendVisitor);
        return sqlChangeVisitors;
    }

    @Override
    public Integer getDefaultScaleForNativeDataType(String nativeDataType) {
        return this.defaultDataTypeParameters.get(nativeDataType.toLowerCase());
    }

    @Override
    public void setDefaultSchemaName(String schemaName) {
        if (!(this.getConnection() instanceof OfflineConnection) && schemaName != null && !schemaName.equalsIgnoreCase(this.getConnectionSchemaName())) {
            throw new RuntimeException(String.format("Cannot use default schema name %s on Microsoft SQL Server because the login schema of the current user (%s) is different and MSSQL does not support setting the default schema per session.", schemaName, this.getConnectionSchemaName()));
        }
        super.setDefaultSchemaName(schemaName);
    }

    @Override
    public String getShortName() {
        return "mssql";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "SQL Server";
    }

    @Override
    public Integer getDefaultPort() {
        return 1433;
    }

    @Override
    public Set<String> getSystemViews() {
        return this.systemTablesAndViews;
    }

    @Override
    protected Set<String> getSystemTables() {
        return this.systemTablesAndViews;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Sequence.class.isAssignableFrom(object)) {
            try {
                return this.isAzureDb() || this.getDatabaseMajorVersion() >= 11;
            }
            catch (DatabaseException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsSequences() {
        try {
            return this.isAzureDb() || this.getDatabaseMajorVersion() >= 11;
        }
        catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        boolean isRealSqlServerConnection;
        String databaseProductName = conn.getDatabaseProductName();
        int majorVersion = conn.getDatabaseMajorVersion();
        boolean bl = isRealSqlServerConnection = PRODUCT_NAME.equalsIgnoreCase(databaseProductName) || "SQLOLEDB".equalsIgnoreCase(databaseProductName);
        if (isRealSqlServerConnection && majorVersion < 10) {
            Scope.getCurrentScope().getLog(this.getClass()).warning(String.format("Your SQL Server major version (%d) seems to indicate that your software is older than SQL Server 2008. Unfortunately, this is not supported, and this connection cannot be used.", majorVersion));
            return false;
        }
        return isRealSqlServerConnection;
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:sqlserver")) {
            return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        }
        if (url2.startsWith("jdbc:jtds:sqlserver")) {
            return "net.sourceforge.jtds.jdbc.Driver";
        }
        return null;
    }

    @Override
    protected String getAutoIncrementClause() {
        return "IDENTITY";
    }

    @Override
    protected boolean generateAutoIncrementStartWith(BigInteger startWith) {
        return true;
    }

    @Override
    protected boolean generateAutoIncrementBy(BigInteger incrementBy) {
        return true;
    }

    @Override
    protected String getAutoIncrementStartWithClause() {
        return "%d";
    }

    @Override
    protected String getAutoIncrementByClause() {
        return "%d";
    }

    @Override
    public String getDefaultCatalogName() {
        if (this.getConnection() == null) {
            return null;
        }
        try {
            return this.getConnection().getCatalog();
        }
        catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    protected SqlStatement getConnectionSchemaNameCallStatement() {
        return new RawSqlStatement("select schema_name()");
    }

    @Override
    public String getConcatSql(String ... values2) {
        StringBuilder returnString = new StringBuilder();
        for (String value : values2) {
            returnString.append(value).append(" + ");
        }
        return returnString.toString().replaceFirst(" \\+ $", "");
    }

    @Override
    public String escapeTableName(String catalogName, String schemaName, String tableName) {
        if ((tableName = this.escapeObjectName(catalogName, schemaName, tableName, Table.class)) != null && tableName.contains(" ") && !tableName.startsWith("\"")) {
            tableName = "\"" + tableName + "\"";
        }
        return tableName;
    }

    @Override
    public boolean supportsTablespaces() {
        return true;
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        if (example.getSchema() == null || example.getSchema().getName() == null) {
            return super.isSystemObject(example);
        }
        if (example instanceof Table && "sys".equals(example.getSchema().getName())) {
            return true;
        }
        if (example instanceof View && "sys".equals(example.getSchema().getName())) {
            return true;
        }
        return super.isSystemObject(example);
    }

    public String generateDefaultConstraintName(String tableName, String columnName) {
        return "DF_" + tableName + "_" + columnName;
    }

    @Override
    public String escapeObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName == null) {
            return null;
        }
        if (objectName.contains("(")) {
            return objectName;
        }
        return super.escapeObjectName(objectName, objectType);
    }

    @Override
    public String getDateLiteral(String isoDate) {
        return super.getDateLiteral(isoDate).replace(' ', 'T');
    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return false;
    }

    @Override
    public boolean supportsDropTableCascadeConstraints() {
        return false;
    }

    @Override
    public boolean supportsCatalogInObjectName(Class<? extends DatabaseObject> type) {
        if (View.class.isAssignableFrom(type)) {
            return false;
        }
        return Relation.class.isAssignableFrom(type);
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String viewName) throws DatabaseException {
        schema = schema.customize(this);
        List defLines = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForList((SqlStatement)new GetViewDefinitionStatement(schema.getCatalogName(), schema.getSchemaName(), viewName), String.class);
        StringBuilder sb = new StringBuilder();
        for (String defLine : defLines) {
            sb.append(defLine);
        }
        String definition = sb.toString();
        String finalDef = definition.replaceAll("\\r\\n", "\n").trim();
        if (finalDef.startsWith("--") || finalDef.startsWith("/*")) {
            return "FULL_DEFINITION: " + finalDef;
        }
        String selectOnly = CREATE_VIEW_AS_PATTERN.matcher(finalDef).replaceFirst("");
        if (selectOnly.equals(finalDef)) {
            return "FULL_DEFINITION: " + finalDef;
        }
        if ((selectOnly = selectOnly.trim()).startsWith("(") && (selectOnly.endsWith(")") || selectOnly.endsWith(");"))) {
            selectOnly = selectOnly.replaceFirst("^\\(", "");
            selectOnly = selectOnly.replaceFirst("\\);?$", "");
        }
        return selectOnly;
    }

    @Override
    public String escapeObjectName(String catalogName, String schemaName, String objectName, Class<? extends DatabaseObject> objectType) {
        if (Index.class.isAssignableFrom(objectType)) {
            return super.escapeObjectName(objectName, objectType);
        }
        boolean includeCatalog = GlobalConfiguration.INCLUDE_CATALOG_IN_SPECIFICATION.getCurrentValue();
        if (catalogName != null && (includeCatalog || !catalogName.equalsIgnoreCase(this.getDefaultCatalogName()))) {
            return super.escapeObjectName(catalogName, schemaName, objectName, objectType);
        }
        String name = this.escapeObjectName(objectName, objectType);
        if (StringUtil.isEmpty(schemaName)) {
            schemaName = this.getDefaultSchemaName();
        }
        if (!StringUtil.isEmpty(schemaName) && !schemaName.equals(this.getConnectionSchemaName())) {
            name = this.escapeObjectName(schemaName, Schema.class) + "." + name;
        }
        return name;
    }

    @Override
    public String getJdbcSchemaName(CatalogAndSchema schema) {
        String schemaName = super.getJdbcSchemaName(schema);
        if (schemaName != null && !this.isCaseSensitive()) {
            schemaName = schemaName.toLowerCase();
        }
        return schemaName;
    }

    @Override
    public boolean isCaseSensitive() {
        if (this.caseSensitive == null) {
            try {
                if (this.getConnection() instanceof JdbcConnection) {
                    String catalog = this.getConnection().getCatalog();
                    String sql = "SELECT CONVERT([sysname], DATABASEPROPERTYEX(N'" + this.escapeStringForDatabase(catalog) + "', 'Collation'))";
                    String collation = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new RawSqlStatement(sql), String.class);
                    this.caseSensitive = collation != null && !collation.contains("_CI_");
                } else if (this.getConnection() instanceof OfflineConnection) {
                    this.caseSensitive = ((OfflineConnection)this.getConnection()).isCaseSensitive();
                }
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Cannot determine case sensitivity from MSSQL", e);
            }
        }
        return this.caseSensitive != null && this.caseSensitive != false;
    }

    @Override
    public int getDataTypeMaxParameters(String dataTypeName) {
        if ("bigint".equalsIgnoreCase(dataTypeName) || "bit".equalsIgnoreCase(dataTypeName) || "date".equalsIgnoreCase(dataTypeName) || "datetime".equalsIgnoreCase(dataTypeName) || "geography".equalsIgnoreCase(dataTypeName) || "geometry".equalsIgnoreCase(dataTypeName) || "hierarchyid".equalsIgnoreCase(dataTypeName) || "image".equalsIgnoreCase(dataTypeName) || "int".equalsIgnoreCase(dataTypeName) || "money".equalsIgnoreCase(dataTypeName) || "ntext".equalsIgnoreCase(dataTypeName) || "real".equalsIgnoreCase(dataTypeName) || "smalldatetime".equalsIgnoreCase(dataTypeName) || "smallint".equalsIgnoreCase(dataTypeName) || "smallmoney".equalsIgnoreCase(dataTypeName) || "text".equalsIgnoreCase(dataTypeName) || "timestamp".equalsIgnoreCase(dataTypeName) || "tinyint".equalsIgnoreCase(dataTypeName) || "rowversion".equalsIgnoreCase(dataTypeName) || "sql_variant".equalsIgnoreCase(dataTypeName) || "sysname".equalsIgnoreCase(dataTypeName) || "uniqueidentifier".equalsIgnoreCase(dataTypeName)) {
            return 0;
        }
        if ("binary".equalsIgnoreCase(dataTypeName) || "char".equalsIgnoreCase(dataTypeName) || "datetime2".equalsIgnoreCase(dataTypeName) || "datetimeoffset".equalsIgnoreCase(dataTypeName) || "float".equalsIgnoreCase(dataTypeName) || "nchar".equalsIgnoreCase(dataTypeName) || "nvarchar".equalsIgnoreCase(dataTypeName) || "time".equalsIgnoreCase(dataTypeName) || "varbinary".equalsIgnoreCase(dataTypeName) || "varchar".equalsIgnoreCase(dataTypeName) || "xml".equalsIgnoreCase(dataTypeName)) {
            return 1;
        }
        return 2;
    }

    @Override
    public String escapeDataTypeName(String dataTypeName) {
        int indexOfPeriod = dataTypeName.indexOf(46);
        if (indexOfPeriod < 0) {
            if (!dataTypeName.startsWith(this.getQuotingStartCharacter())) {
                dataTypeName = this.escapeObjectName(dataTypeName, DatabaseObject.class);
            }
            return dataTypeName;
        }
        String schemaName = dataTypeName.substring(0, indexOfPeriod);
        if (!schemaName.startsWith(this.getQuotingStartCharacter())) {
            schemaName = this.escapeObjectName(schemaName, Schema.class);
        }
        if (!(dataTypeName = dataTypeName.substring(indexOfPeriod + 1)).startsWith(this.getQuotingStartCharacter())) {
            dataTypeName = this.escapeObjectName(dataTypeName, DatabaseObject.class);
        }
        return schemaName + "." + dataTypeName;
    }

    @Override
    public String unescapeDataTypeName(String dataTypeName) {
        int indexOfPeriod = dataTypeName.indexOf(46);
        if (indexOfPeriod < 0) {
            if (dataTypeName.matches("\\[[^]\\[]++\\]")) {
                dataTypeName = dataTypeName.substring(1, dataTypeName.length() - 1);
            }
            return dataTypeName;
        }
        String schemaName = dataTypeName.substring(0, indexOfPeriod);
        if (schemaName.matches("\\[[^]\\[]++\\]")) {
            schemaName = schemaName.substring(1, schemaName.length() - 1);
        }
        if ((dataTypeName = dataTypeName.substring(indexOfPeriod + 1)).matches("\\[[^]\\[]++\\]")) {
            dataTypeName = dataTypeName.substring(1, dataTypeName.length() - 1);
        }
        return schemaName + "." + dataTypeName;
    }

    @Override
    public String unescapeDataTypeString(String dataTypeString) {
        int indexOfLeftParen = dataTypeString.indexOf(40);
        if (indexOfLeftParen < 0) {
            return this.unescapeDataTypeName(dataTypeString);
        }
        return this.unescapeDataTypeName(dataTypeString.substring(0, indexOfLeftParen)) + dataTypeString.substring(indexOfLeftParen);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean sendsStringParametersAsUnicode() {
        block8: {
            if (this.sendsStringParametersAsUnicode == null) {
                try {
                    if (this.getConnection() instanceof JdbcConnection) {
                        PreparedStatement ps = null;
                        ResultSet rs = null;
                        try {
                            String sql = "SELECT CONVERT([sysname], SQL_VARIANT_PROPERTY(?, 'BaseType'))";
                            ps = ((JdbcConnection)this.getConnection()).prepareStatement(sql);
                            ps.setString(1, "Liquibase");
                            rs = ps.executeQuery();
                            String baseType = null;
                            if (rs.next()) {
                                baseType = rs.getString(1);
                            }
                            this.sendsStringParametersAsUnicode = baseType == null || baseType.startsWith("n");
                        }
                        catch (Throwable throwable) {
                            JdbcUtil.close(rs, ps);
                            throw throwable;
                        }
                        JdbcUtil.close(rs, ps);
                        break block8;
                    }
                    if (this.getConnection() instanceof OfflineConnection) {
                        this.sendsStringParametersAsUnicode = ((OfflineConnection)this.getConnection()).getSendsStringParametersAsUnicode();
                    }
                }
                catch (SQLException | DatabaseException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).warning("Cannot determine whether String parameters are sent as Unicode for MSSQL", e);
                }
            }
        }
        return this.sendsStringParametersAsUnicode == null ? true : this.sendsStringParametersAsUnicode;
    }

    public boolean isAzureDb() {
        return "Azure".equalsIgnoreCase(this.getEngineEdition());
    }

    public String getEngineEdition() {
        try {
            if (this.getConnection() instanceof JdbcConnection) {
                String sql = "SELECT CASE ServerProperty('EngineEdition')\n         WHEN 1 THEN 'Personal'\n         WHEN 2 THEN 'Standard'\n         WHEN 3 THEN 'Enterprise'\n         WHEN 4 THEN 'Express'\n         WHEN 5 THEN 'Azure'\n         ELSE 'Unknown'\n       END";
                return Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new RawSqlStatement(sql), String.class);
            }
        }
        catch (DatabaseException e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Could not determine engine edition", e);
        }
        return "Unknown";
    }

    @Override
    protected String getQuotingStartCharacter() {
        return "[";
    }

    @Override
    protected String getQuotingEndCharacter() {
        return "]";
    }

    @Override
    public int getDefaultFractionalDigitsForTimestamp() {
        return 7;
    }

    @Override
    protected String getQuotingEndReplacement() {
        return "]]";
    }

    private static List<String> createReservedWordsCollection() {
        return Arrays.asList("ADD", "ALL", "ALTER", "AND", "ANY", "AS", "ASC", "AUTHORIZATION", "BACKUP", "BEGIN", "BETWEEN", "BREAK", "BROWSE", "BULK", "BY", "CASCADE", "CASE", "CHECK", "CHECKPOINT", "CLOSE", "CLUSTERED", "COALESCE", "COLLATE", "COLUMN", "COMMIT", "COMPUTE", "CONSTRAINT", "CONTAINS", "CONTAINSTABLE", "CONTINUE", "CONVERT", "CREATE", "CROSS", "CURRENT", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DBCC", "DEALLOCATE", "DECLARE", "DEFAULT", "DELETE", "DENY", "DESC", "DISK", "DISTINCT", "DISTRIBUTED", "DOUBLE", "DROP", "DUMP", "ELSE", "END", "ERRLVL", "ESCAPE", "EXCEPT", "EXEC", "EXECUTE", "EXISTS", "EXIT", "EXTERNAL", "FETCH", "FILE", "FILLFACTOR", "FOR", "FOREIGN", "FREETEXT", "FREETEXTTABLE", "FROM", "FULL", "FUNCTION", "GOTO", "GRANT", "GROUP", "HAVING", "HOLDLOCK", "IDENTITY", "IDENTITY_INSERT", "IDENTITYCOL", "IF", "IN", "INDEX", "INNER", "INSERT", "INTERSECT", "INTO", "IS", "JOIN", "KEY", "KILL", "LEFT", "LIKE", "LINENO", "LOAD", "MERGE", "NATIONAL", "NOCHECK", "NONCLUSTERED", "NOT", "NULL", "NULLIF", "OF", "OFF", "OFFSETS", "ON", "OPEN", "OPENDATASOURCE", "OPENQUERY", "OPENROWSET", "OPENXML", "OPTION", "OR", "ORDER", "OUTER", "OVER", "PERCENT", "PIVOT", "PLAN", "PRECISION", "PRIMARY", "PRINT", "PROC", "PROCEDURE", "PUBLIC", "RAISERROR", "READ", "READTEXT", "RECONFIGURE", "REFERENCES", "REPLICATION", "RESTORE", "RESTRICT", "RETURN", "REVERT", "REVOKE", "RIGHT", "ROLLBACK", "ROWCOUNT", "ROWGUIDCOL", "RULE", "SAVE", "SCHEMA", "SECURITYAUDIT", "SELECT", "SEMANTICKEYPHRASETABLE", "SEMANTICSIMILARITYDETAILSTABLE", "SEMANTICSIMILARITYTABLE", "SESSION_USER", "SET", "SETUSER", "SHUTDOWN", "SOME", "STATISTICS", "SYSTEM_USER", "TABLE", "TABLESAMPLE", "TEXTSIZE", "THEN", "TO", "TOP", "TRAN", "TRANSACTION", "TRIGGER", "TRUNCATE", "TRY_CONVERT", "TSEQUAL", "UNION", "UNIQUE", "UNPIVOT", "UPDATE", "UPDATETEXT", "USE", "USER", "VALUES", "VARYING", "VIEW", "WAITFOR", "WHEN", "WHERE", "WHILE", "WITH", "WITHIN GROUP", "WRITETEXT");
    }

    @Override
    public boolean supportsDatabaseChangeLogHistory() {
        return true;
    }

    public static final class MSSQL_SERVER_VERSIONS {
        public static final int MSSQL2008 = 10;
        public static final int MSSQL2012 = 11;
        public static final int MSSQL2014 = 12;
        public static final int MSSQL2016 = 13;
        public static final int MSSQL2017 = 14;

        private MSSQL_SERVER_VERSIONS() {
            throw new IllegalStateException("this class is not expected to be instantiated.");
        }
    }
}

