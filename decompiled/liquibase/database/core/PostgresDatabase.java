/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.changelog.column.LiquibaseColumn;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.core.DatabaseUtils;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.logging.Logger;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawCallStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.JdbcUtil;
import liquibase.util.StringUtil;

public class PostgresDatabase
extends AbstractJdbcDatabase {
    private static final String dbFullVersion = null;
    public static final String PRODUCT_NAME = "PostgreSQL";
    public static final int MINIMUM_DBMS_MAJOR_VERSION = 9;
    public static final int MINIMUM_DBMS_MINOR_VERSION = 2;
    public static final List<String> VALID_AUTO_INCREMENT_COLUMN_TYPE_NAMES = Collections.unmodifiableList(Arrays.asList("int2", "int4", "int8", "smallint", "int", "bigint", "smallserial", "serial", "bigserial"));
    static final int PGSQL_PK_BYTES_LIMIT = 63;
    static final String PGSQL_PK_SUFFIX = "_pkey";
    private static final int PGSQL_DEFAULT_TCP_PORT_NUMBER = 5432;
    private static final Logger LOG = Scope.getCurrentScope().getLog(PostgresDatabase.class);
    private final Set<String> systemTablesAndViews = new HashSet<String>();
    private final Set<String> reservedWords = new HashSet<String>();

    public PostgresDatabase() {
        super.setCurrentDateTimeFunction("NOW()");
        this.reservedWords.addAll(Arrays.asList("ALL", "ANALYSE", "ANALYZE", "AND", "ANY", "ARRAY", "AS", "ASC", "ASYMMETRIC", "AUTHORIZATION", "BINARY", "BOTH", "CASE", "CAST", "CHECK", "COLLATE", "COLLATION", "COLUMN", "CONCURRENTLY", "CONSTRAINT", "CREATE", "CROSS", "CURRENT_CATALOG", "CURRENT_DATE", "CURRENT_ROLE", "CURRENT_SCHEMA", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "DEFAULT", "DEFERRABLE", "DESC", "DISTINCT", "DO", "ELSE", "END", "EXCEPT", "FALSE", "FETCH", "FOR", "FOREIGN", "FREEZE", "FROM", "FULL", "GRANT", "GROUP", "HAVING", "ILIKE", "IN", "INITIALLY", "INNER", "INTERSECT", "INTO", "IS", "ISNULL", "JOIN", "LATERAL", "LEADING", "LEFT", "LIKE", "LIMIT", "LOCALTIME", "LOCALTIMESTAMP", "NATURAL", "NOT", "NOTNULL", "NULL", "OFFSET", "ON", "ONLY", "OR", "ORDER", "OUTER", "OVERLAPS", "PLACING", "PRIMARY", "REFERENCES", "RETURNING", "RIGHT", "SELECT", "SESSION_USER", "SIMILAR", "SOME", "SYMMETRIC", "TABLE", "TABLESAMPLE", "THEN", "TO", "TRAILING", "TRUE", "UNION", "UNIQUE", "USER", "USING", "VARIADIC", "VERBOSE", "WHEN", "WHERE", "WINDOW", "WITH"));
        this.sequenceNextValueFunction = "nextval('%s')";
        this.sequenceCurrentValueFunction = "currval('%s')";
        this.unmodifiableDataTypes.addAll(Arrays.asList("bool", "int4", "int8", "float4", "float8", "bigserial", "serial", "oid", "bytea", "date", "timestamptz", "text", "int2[]", "int4[]", "int8[]", "float4[]", "float8[]", "bool[]", "varchar[]", "text[]", "numeric[]"));
        this.unquotedObjectsAreUppercased = false;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String getShortName() {
        return "postgresql";
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return PRODUCT_NAME;
    }

    @Override
    public Integer getDefaultPort() {
        return 5432;
    }

    @Override
    public Set<String> getSystemViews() {
        return this.systemTablesAndViews;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return true;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        if (!PRODUCT_NAME.equalsIgnoreCase(conn.getDatabaseProductName())) {
            return false;
        }
        int majorVersion = conn.getDatabaseMajorVersion();
        int minorVersion = conn.getDatabaseMinorVersion();
        if (majorVersion < 9 || majorVersion == 9 && minorVersion < 2) {
            LOG.warning(String.format("Your PostgreSQL software version (%d.%d) seems to indicate that your software is older than %d.%d. This means that you might encounter strange behaviour and incorrect error messages.", majorVersion, minorVersion, 9, 2));
            return true;
        }
        return true;
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:postgresql:")) {
            return "org.postgresql.Driver";
        }
        return null;
    }

    @Override
    public boolean supportsCatalogInObjectName(Class<? extends DatabaseObject> type) {
        return false;
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    public String getDatabaseChangeLogTableName() {
        return super.getDatabaseChangeLogTableName().toLowerCase(Locale.US);
    }

    @Override
    public String getDatabaseChangeLogLockTableName() {
        return super.getDatabaseChangeLogLockTableName().toLowerCase(Locale.US);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setConnection(DatabaseConnection conn) {
        super.setConnection(conn);
        if (conn instanceof JdbcConnection) {
            ResultSet resultSet;
            Statement statement;
            block5: {
                statement = null;
                resultSet = null;
                try {
                    String setting;
                    statement = ((JdbcConnection)conn).createStatement();
                    resultSet = statement.executeQuery("select setting from pg_settings where name = 'edb_redwood_date'");
                    if (!resultSet.next() || !"on".equals(setting = resultSet.getString(1))) break block5;
                    LOG.warning("EnterpriseDB " + conn.getURL() + " does not store DATE columns. Instead, it auto-converts them to TIMESTAMPs. (edb_redwood_date=true)");
                }
                catch (SQLException | DatabaseException e) {
                    try {
                        LOG.info("Cannot check pg_settings", e);
                    }
                    catch (Throwable throwable) {
                        JdbcUtil.close(resultSet, statement);
                        throw throwable;
                    }
                    JdbcUtil.close(resultSet, statement);
                }
            }
            JdbcUtil.close(resultSet, statement);
        }
    }

    @Override
    public String unescapeDataTypeName(String dataTypeName) {
        return dataTypeName.replace("\"", "");
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        if (example instanceof Table && example.getSchema() != null && ("pg_catalog".equals(example.getSchema().getName()) || "pg_toast".equals(example.getSchema().getName()))) {
            return true;
        }
        return super.isSystemObject(example);
    }

    @Override
    public boolean supportsTablespaces() {
        return true;
    }

    @Override
    public String getAutoIncrementClause() {
        if (this.useSerialDatatypes()) {
            return "";
        }
        return super.getAutoIncrementClause();
    }

    public boolean useSerialDatatypes() {
        try {
            return this.getDatabaseMajorVersion() < 10;
        }
        catch (DatabaseException e) {
            return true;
        }
    }

    @Override
    protected String getAutoIncrementClause(String generationType, Boolean defaultOnNull) {
        if (this.useSerialDatatypes()) {
            return "";
        }
        if (StringUtil.isEmpty(generationType)) {
            return super.getAutoIncrementClause();
        }
        String autoIncrementClause = "GENERATED %s AS IDENTITY";
        return String.format(autoIncrementClause, generationType);
    }

    @Override
    public boolean generateAutoIncrementStartWith(BigInteger startWith) {
        if (this.useSerialDatatypes()) {
            return false;
        }
        return super.generateAutoIncrementStartWith(startWith);
    }

    @Override
    public boolean generateAutoIncrementBy(BigInteger incrementBy) {
        if (this.useSerialDatatypes()) {
            return false;
        }
        return super.generateAutoIncrementBy(incrementBy);
    }

    @Override
    public String escapeObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (this.quotingStrategy == ObjectQuotingStrategy.LEGACY && this.hasMixedCase(objectName)) {
            return "\"" + objectName + "\"";
        }
        if (objectType != null && LiquibaseColumn.class.isAssignableFrom(objectType)) {
            return objectName != null && !objectName.isEmpty() ? objectName.trim() : objectName;
        }
        return super.escapeObjectName(objectName, objectType);
    }

    @Override
    public String correctObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName == null || this.quotingStrategy != ObjectQuotingStrategy.LEGACY) {
            return super.correctObjectName(objectName, objectType);
        }
        if (objectType.equals(Schema.class) && Boolean.TRUE.equals(GlobalConfiguration.PRESERVE_SCHEMA_CASE.getCurrentValue())) {
            return objectName;
        }
        if (objectName.contains("-") || this.hasMixedCase(objectName) || this.startsWithNumeric(objectName) || this.isReservedWord(objectName)) {
            return objectName;
        }
        return objectName.toLowerCase(Locale.US);
    }

    protected boolean hasMixedCase(String tableName) {
        if (tableName == null) {
            return false;
        }
        return StringUtil.hasUpperCase(tableName) && StringUtil.hasLowerCase(tableName);
    }

    @Override
    public boolean isReservedWord(String tableName) {
        return this.reservedWords.contains(tableName.toUpperCase());
    }

    @Override
    protected SqlStatement getConnectionSchemaNameCallStatement() {
        return new RawCallStatement("select current_schema()");
    }

    @Override
    public String generatePrimaryKeyName(String tableName) {
        int pkNameBaseAllowedBytesCount;
        Charset charset = GlobalConfiguration.FILE_ENCODING.getCurrentValue();
        byte[] tableNameBytes = tableName.getBytes(charset);
        if (tableNameBytes.length <= (pkNameBaseAllowedBytesCount = 63 - PGSQL_PK_SUFFIX.getBytes(charset).length)) {
            return tableName + PGSQL_PK_SUFFIX;
        }
        String baseName = new String(tableNameBytes, 0, pkNameBaseAllowedBytesCount, charset);
        return baseName.substring(0, baseName.length() - 1) + PGSQL_PK_SUFFIX;
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        int major = 0;
        int minor = 0;
        try {
            major = this.getDatabaseMajorVersion();
            minor = this.getDatabaseMinorVersion();
        }
        catch (DatabaseException x) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Unable to determine exact database server version - specified TIMESTAMP precision will not be set: ", x);
            return 0;
        }
        String minimumVersion = "7.2";
        if (StringUtil.isMinimumVersion(minimumVersion, major, minor, 0)) {
            return 6;
        }
        return 0;
    }

    @Override
    public CatalogAndSchema.CatalogAndSchemaCase getSchemaAndCatalogCase() {
        return CatalogAndSchema.CatalogAndSchemaCase.LOWER_CASE;
    }

    @Override
    public void rollback() throws DatabaseException {
        super.rollback();
        DatabaseUtils.initializeDatabase(this.getDefaultCatalogName(), this.getDefaultSchemaName(), this);
    }

    @Override
    public void setDefaultCatalogName(String defaultCatalogName) {
        if (StringUtil.isNotEmpty(defaultCatalogName)) {
            Scope.getCurrentScope().getUI().sendMessage("WARNING: Postgres does not support catalogs, so the values set in 'defaultCatalogName' and 'referenceDefaultCatalogName' will be ignored.");
        }
        super.setDefaultCatalogName(defaultCatalogName);
    }

    @Override
    public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return type.isAssignableFrom(Table.class);
    }

    @Override
    public boolean supportsDatabaseChangeLogHistory() {
        return true;
    }
}

