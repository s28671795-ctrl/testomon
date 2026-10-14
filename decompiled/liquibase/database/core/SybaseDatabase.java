/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.StringUtil;

public class SybaseDatabase
extends AbstractJdbcDatabase {
    public static final String PRODUCT_NAME = "Adaptive Server Enterprise";
    protected Set<String> systemTablesAndViews = new HashSet<String>();

    public SybaseDatabase() {
        super.setCurrentDateTimeFunction("GETDATE()");
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
        this.systemTablesAndViews.add("sysquerymetrics");
        this.systemTablesAndViews.add("syssegments");
        this.systemTablesAndViews.add("sysconstraints");
    }

    @Override
    public String getShortName() {
        return "sybase";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public Integer getDefaultPort() {
        return 4100;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Sybase";
    }

    @Override
    public boolean supportsDDLInTransaction() {
        return false;
    }

    @Override
    public Set<String> getSystemViews() {
        return this.systemTablesAndViews;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Sequence.class.isAssignableFrom(object)) {
            return false;
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsSequences() {
        return false;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        String dbProductName = conn.getDatabaseProductName();
        return this.isSybaseProductName(dbProductName);
    }

    boolean isSybaseProductName(String dbProductName) {
        return PRODUCT_NAME.equals(dbProductName) || "Sybase SQL Server".equals(dbProductName) || "sql server".equals(dbProductName) || "ASE".equals(dbProductName);
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:xsybase") || url2.startsWith("jdbc:sybase:Tds")) {
            return "com.sybase.jdbc4.jdbc.SybDriver";
        }
        if (url2.startsWith("jdbc:jtds:sybase")) {
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
        return false;
    }

    @Override
    protected boolean generateAutoIncrementBy(BigInteger incrementBy) {
        return false;
    }

    @Override
    public String getConcatSql(String ... values2) {
        return StringUtil.join(values2, " + ");
    }

    @Override
    public boolean supportsTablespaces() {
        return true;
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        if (example.getSchema() != null && example.getSchema().getName() != null) {
            if (example instanceof Table && ("sys".equals(example.getSchema().getName()) || "sybfi".equals(example.getSchema().getName()))) {
                return true;
            }
            if (example instanceof View && ("sys".equals(example.getSchema().getName()) || "sybfi".equals(example.getSchema().getName()))) {
                return true;
            }
        }
        if (example instanceof Column && example.getName().startsWith("sybfi")) {
            return true;
        }
        return super.isSystemObject(example);
    }

    public String generateDefaultConstraintName(String tableName, String columnName) {
        return "DF_" + tableName + "_" + columnName;
    }

    @Override
    protected SqlStatement getConnectionSchemaNameCallStatement() {
        return new RawSqlStatement("select user_name()");
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
    public String getViewDefinition(CatalogAndSchema schema, String viewName) throws DatabaseException {
        schema = schema.customize(this);
        GetViewDefinitionStatement statement = new GetViewDefinitionStatement(schema.getCatalogName(), schema.getSchemaName(), viewName);
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this);
        List definitionRows = executor2.queryForList((SqlStatement)statement, String.class);
        StringBuilder definition = new StringBuilder();
        for (String d : definitionRows) {
            definition.append(d);
        }
        String defUpper = definition.toString().toUpperCase();
        int findPos = 0;
        if (defUpper.contains(" AS ")) {
            findPos = defUpper.indexOf(" AS ") + 3;
        } else if (defUpper.contains(" AS\n")) {
            findPos = defUpper.indexOf(" AS\n") + 3;
        } else if (defUpper.contains("\nAS ")) {
            findPos = defUpper.indexOf("\nAS ") + 3;
        } else if (defUpper.contains("\nAS\n")) {
            findPos = defUpper.indexOf("\nAS\n") + 3;
        }
        return definition.substring(findPos, definition.toString().length());
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        if (this.getConnection() == null) {
            return -1;
        }
        return this.getConnection().getDatabaseMajorVersion();
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        if (this.getConnection() == null) {
            return -1;
        }
        return this.getConnection().getDatabaseMinorVersion();
    }

    @Override
    public String escapeIndexName(String catalogName, String schemaName, String indexName) {
        return indexName;
    }

    @Override
    public String quoteObject(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName == null) {
            return null;
        }
        return this.getQuotingStartCharacter() + objectName + this.getQuotingEndCharacter();
    }

    @Override
    protected boolean mustQuoteObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName.contains("(")) {
            return false;
        }
        return super.mustQuoteObjectName(objectName, objectType);
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
    public boolean requiresExplicitNullForColumns() {
        return true;
    }
}

