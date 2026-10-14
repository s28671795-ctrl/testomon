/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.StoredProcedure;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.SystemUtil;

public class SnowflakeDatabase
extends AbstractJdbcDatabase {
    public static final String PRODUCT_NAME = "Snowflake";
    private static final Pattern CREATE_VIEW_AS_PATTERN = Pattern.compile("^CREATE\\s+.*?VIEW\\s+.*?AS\\s+", 34);
    private final Set<String> systemTables = new HashSet<String>();
    private final Set<String> systemViews = new HashSet<String>();

    public SnowflakeDatabase() {
        super.setCurrentDateTimeFunction("current_timestamp::timestamp_ntz");
        this.unmodifiableDataTypes.addAll(Arrays.asList("integer", "bool", "boolean", "int4", "int8", "float4", "float8", "numeric", "bigserial", "serial", "bytea", "timestamptz", "array", "object", "variant"));
        this.unquotedObjectsAreUppercased = true;
        super.addReservedWords(this.getDefaultReservedWords());
        this.defaultAutoIncrementStartWith = BigInteger.ONE;
        this.defaultAutoIncrementBy = BigInteger.ONE;
        this.sequenceNextValueFunction = "%s.nextval";
    }

    @Override
    public String getShortName() {
        return "snowflake";
    }

    @Override
    public String getDisplayName() {
        return PRODUCT_NAME;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return PRODUCT_NAME;
    }

    @Override
    public Integer getDefaultPort() {
        return null;
    }

    @Override
    public Set<String> getSystemTables() {
        return this.systemTables;
    }

    @Override
    public Set<String> getSystemViews() {
        return this.systemViews;
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supportsDropTableCascadeConstraints() {
        return true;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return PRODUCT_NAME.equalsIgnoreCase(conn.getDatabaseProductName());
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:snowflake:")) {
            return "net.snowflake.client.jdbc.SnowflakeDriver";
        }
        return null;
    }

    @Override
    public boolean supportsCatalogs() {
        return true;
    }

    @Override
    public boolean supportsCatalogInObjectName(Class<? extends DatabaseObject> type) {
        return type == Table.class || type == View.class || type == StoredProcedure.class;
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public boolean supportsAutoIncrement() {
        return true;
    }

    @Override
    public String getAutoIncrementClause() {
        return "AUTOINCREMENT";
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
    public boolean generateAutoIncrementStartWith(BigInteger startWith) {
        return true;
    }

    @Override
    public boolean generateAutoIncrementBy(BigInteger incrementBy) {
        return true;
    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return true;
    }

    @Override
    protected String getConnectionSchemaName() {
        String string;
        block9: {
            DatabaseConnection connection = this.getConnection();
            if (connection == null) {
                return null;
            }
            ResultSet resultSet = ((JdbcConnection)connection).createStatement().executeQuery("SELECT CURRENT_SCHEMA()");
            try {
                resultSet.next();
                string = resultSet.getString(1);
                if (resultSet == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if (resultSet != null) {
                        try {
                            resultSet.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    Scope.getCurrentScope().getLog(this.getClass()).info("Error getting default schema", e);
                    return null;
                }
            }
            resultSet.close();
        }
        return string;
    }

    @Override
    public void setConnection(DatabaseConnection conn) {
        super.setConnection(conn);
        this.configureSession();
    }

    @Override
    public void rollback() throws DatabaseException {
        super.rollback();
        this.configureSession();
    }

    protected void configureSession() {
        if (SystemUtil.getJavaMajorVersion() >= 17) {
            try {
                Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).execute(new RawSqlStatement("alter session set jdbc_query_result_format = 'JSON'"));
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning(e.getMessage(), e);
            }
        }
    }

    private Set<String> getDefaultReservedWords() {
        HashSet<String> reservedWords = new HashSet<String>();
        reservedWords.add("ACCOUNT");
        reservedWords.add("ALL");
        reservedWords.add("ALTER");
        reservedWords.add("AND");
        reservedWords.add("ANY");
        reservedWords.add("AS");
        reservedWords.add("BETWEEN");
        reservedWords.add("BY");
        reservedWords.add("CASE");
        reservedWords.add("CAST");
        reservedWords.add("CHECK");
        reservedWords.add("COLUMN");
        reservedWords.add("CONNECT");
        reservedWords.add("CONNECTION");
        reservedWords.add("CONSTRAINT");
        reservedWords.add("CREATE");
        reservedWords.add("CROSS");
        reservedWords.add("CURRENT");
        reservedWords.add("CURRENT_TIME");
        reservedWords.add("CURRENT_TIMESTAMP");
        reservedWords.add("CURRENT_USER");
        reservedWords.add("DATABASE");
        reservedWords.add("DELETE");
        reservedWords.add("DISTINCT");
        reservedWords.add("DROP");
        reservedWords.add("ELSE");
        reservedWords.add("EXISTS");
        reservedWords.add("FALSE");
        reservedWords.add("FOLLOWING");
        reservedWords.add("FOR");
        reservedWords.add("FROM");
        reservedWords.add("FULL");
        reservedWords.add("GRANT");
        reservedWords.add("GROUP");
        reservedWords.add("GSCLUSTER");
        reservedWords.add("HAVING");
        reservedWords.add("ILIKE");
        reservedWords.add("IN");
        reservedWords.add("INCREMENT");
        reservedWords.add("INNER");
        reservedWords.add("INSERT");
        reservedWords.add("INTERSECT");
        reservedWords.add("INTO");
        reservedWords.add("IS");
        reservedWords.add("ISSUE");
        reservedWords.add("JOIN");
        reservedWords.add("LATERAL");
        reservedWords.add("LEFT");
        reservedWords.add("LIKE");
        reservedWords.add("LOCALTIME");
        reservedWords.add("LOCALTIMESTAMP");
        reservedWords.add("MINUS");
        reservedWords.add("NATURAL");
        reservedWords.add("NOT");
        reservedWords.add("NULL");
        reservedWords.add("OF");
        reservedWords.add("ON");
        reservedWords.add("OR");
        reservedWords.add("ORDER");
        reservedWords.add("ORGANIZATION");
        reservedWords.add("QUALIFY");
        reservedWords.add("REGEXP");
        reservedWords.add("REVOKE");
        reservedWords.add("RIGHT");
        reservedWords.add("RLIKE");
        reservedWords.add("ROW");
        reservedWords.add("ROWS");
        reservedWords.add("SAMPLE");
        reservedWords.add("SCHEMA");
        reservedWords.add("SELECT");
        reservedWords.add("SET");
        reservedWords.add("SOME");
        reservedWords.add("START");
        reservedWords.add("TABLE");
        reservedWords.add("TABLESAMPLE");
        reservedWords.add("THEN");
        reservedWords.add("TO");
        reservedWords.add("TRIGGER");
        reservedWords.add("TRUE");
        reservedWords.add("TRY_CAST");
        reservedWords.add("UNION");
        reservedWords.add("UNIQUE");
        reservedWords.add("UPDATE");
        reservedWords.add("USING");
        reservedWords.add("VALUES");
        reservedWords.add("VIEW");
        reservedWords.add("WHEN");
        reservedWords.add("WHENEVER");
        reservedWords.add("WHERE");
        reservedWords.add("WITH");
        return reservedWords;
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String viewName) throws DatabaseException {
        String definition = super.getViewDefinition(schema, viewName);
        if (definition == null || definition.isEmpty()) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Error reading '" + (viewName != null && viewName.isEmpty() ? viewName : "") + "' view definition");
            return null;
        }
        if (definition.endsWith(";")) {
            definition = definition.substring(0, definition.length() - 1);
        }
        return CREATE_VIEW_AS_PATTERN.matcher(definition).replaceFirst("");
    }

    @Override
    public boolean supportsDatabaseChangeLogHistory() {
        return true;
    }

    @Override
    public String generateConnectCommandSuccessMessage() {
        return "WARNING: The 'connect' command relies on information reported by the JDBC driver. The Snowflake driver does not report on schema issues, and therefore users should manually confirm Snowflake schemas for accuracy.";
    }
}

