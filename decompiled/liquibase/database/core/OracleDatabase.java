/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.CatalogAndSchema;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.ExecutorService;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.SqlStatement;
import liquibase.statement.UniqueConstraint;
import liquibase.statement.core.RawCallStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.util.JdbcUtil;
import liquibase.util.StringUtil;

public class OracleDatabase
extends AbstractJdbcDatabase {
    private static final String PROXY_USER_REGEX = ".*(?:thin|oci)\\:(.+)/@.*";
    public static final Pattern PROXY_USER_PATTERN = Pattern.compile(".*(?:thin|oci)\\:(.+)/@.*");
    private static final String VERSION_REGEX = "(\\d+)\\.(\\d+)\\..*";
    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\..*");
    public static final String PRODUCT_NAME = "oracle";
    private static final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    protected final int SHORT_IDENTIFIERS_LENGTH = 30;
    protected final int LONG_IDENTIFIERS_LEGNTH = 128;
    public static final int ORACLE_12C_MAJOR_VERSION = 12;
    public static final int ORACLE_23C_MAJOR_VERSION = 23;
    private final Set<String> reservedWords = new HashSet<String>();
    private Set<String> userDefinedTypes;
    private Map<String, String> savedSessionNlsSettings;
    private Boolean canAccessDbaRecycleBin;
    private Integer databaseMajorVersion;
    private Integer databaseMinorVersion;

    public OracleDatabase() {
        this.unquotedObjectsAreUppercased = true;
        super.setCurrentDateTimeFunction("SYSTIMESTAMP");
        this.dateFunctions.add(new DatabaseFunction("SYSDATE"));
        this.dateFunctions.add(new DatabaseFunction("SYSTIMESTAMP"));
        this.dateFunctions.add(new DatabaseFunction("CURRENT_TIMESTAMP"));
        this.sequenceNextValueFunction = "%s.nextval";
        this.sequenceCurrentValueFunction = "%s.currval";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    private void tryProxySession(String url2, Connection con) {
        Matcher m4 = PROXY_USER_PATTERN.matcher(url2);
        if (m4.matches()) {
            Method method2;
            Properties props = new Properties();
            props.put("PROXY_USER_NAME", m4.group(1));
            try {
                method2 = con.getClass().getMethod("openProxySession", Integer.TYPE, Properties.class);
                method2.setAccessible(true);
                method2.invoke((Object)con, 1, props);
            }
            catch (Exception e) {
                Scope.getCurrentScope().getLog(this.getClass()).info("Could not open proxy session on OracleDatabase: " + e.getCause().getMessage());
                return;
            }
            try {
                method2 = con.getClass().getMethod("isProxySession", new Class[0]);
                method2.setAccessible(true);
                boolean b = (Boolean)method2.invoke((Object)con, new Object[0]);
                if (!b) {
                    Scope.getCurrentScope().getLog(this.getClass()).info("Proxy session not established on OracleDatabase: ");
                }
            }
            catch (Exception e) {
                Scope.getCurrentScope().getLog(this.getClass()).info("Could not open proxy session on OracleDatabase: " + e.getCause().getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setConnection(DatabaseConnection conn) {
        this.reservedWords.addAll(Arrays.asList("GROUP", "USER", "SESSION", "PASSWORD", "RESOURCE", "START", "SIZE", "UID", "DESC", "ORDER"));
        Connection sqlConn = null;
        if (!(conn instanceof OfflineConnection)) {
            try {
                if (conn instanceof JdbcConnection) {
                    sqlConn = ((JdbcConnection)conn).getWrappedConnection();
                }
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
            if (sqlConn != null) {
                this.tryProxySession(conn.getURL(), sqlConn);
                try {
                    this.reservedWords.addAll(Arrays.asList(sqlConn.getMetaData().getSQLKeywords().toUpperCase().split(",\\s*")));
                }
                catch (SQLException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).info("Could get sql keywords on OracleDatabase: " + e.getMessage());
                }
                try {
                    Method method2 = sqlConn.getClass().getMethod("setRemarksReporting", Boolean.TYPE);
                    method2.setAccessible(true);
                    method2.invoke((Object)sqlConn, true);
                }
                catch (Exception e) {
                    Scope.getCurrentScope().getLog(this.getClass()).info("Could not set remarks reporting on OracleDatabase: " + e.getMessage());
                }
                CallableStatement statement = null;
                try {
                    Matcher majorVersionMatcher;
                    statement = sqlConn.prepareCall("{call DBMS_UTILITY.DB_VERSION(?,?)}");
                    statement.registerOutParameter(1, 12);
                    statement.registerOutParameter(2, 12);
                    statement.execute();
                    String compatibleVersion = statement.getString(2);
                    if (compatibleVersion != null && (majorVersionMatcher = VERSION_PATTERN.matcher(compatibleVersion)).matches()) {
                        this.databaseMajorVersion = Integer.valueOf(majorVersionMatcher.group(1));
                        this.databaseMinorVersion = Integer.valueOf(majorVersionMatcher.group(2));
                    }
                }
                catch (SQLException e) {
                    String message = "Cannot read from DBMS_UTILITY.DB_VERSION: " + e.getMessage();
                    Scope.getCurrentScope().getLog(this.getClass()).info("Could not set check compatibility mode on OracleDatabase, assuming not running in any sort of compatibility mode: " + message);
                }
                finally {
                    JdbcUtil.closeStatement(statement);
                }
                if (GlobalConfiguration.DDL_LOCK_TIMEOUT.getCurrentValue() != null) {
                    int timeoutValue = GlobalConfiguration.DDL_LOCK_TIMEOUT.getCurrentValue();
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Setting DDL_LOCK_TIMEOUT value to " + timeoutValue);
                    String sql = "ALTER SESSION SET DDL_LOCK_TIMEOUT=" + timeoutValue;
                    PreparedStatement ddlLockTimeoutStatement = null;
                    try {
                        ddlLockTimeoutStatement = sqlConn.prepareStatement(sql);
                        ddlLockTimeoutStatement.execute();
                    }
                    catch (SQLException sqle) {
                        Scope.getCurrentScope().getUI().sendErrorMessage("Unable to set the DDL_LOCK_TIMEOUT_VALUE: " + sqle.getMessage(), sqle);
                        Scope.getCurrentScope().getLog(this.getClass()).warning("Unable to set the DDL_LOCK_TIMEOUT_VALUE: " + sqle.getMessage(), sqle);
                    }
                    finally {
                        JdbcUtil.closeStatement(ddlLockTimeoutStatement);
                    }
                }
            }
        }
        super.setConnection(conn);
    }

    @Override
    public String getShortName() {
        return PRODUCT_NAME;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Oracle";
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        if (this.databaseMajorVersion == null) {
            return super.getDatabaseMajorVersion();
        }
        return this.databaseMajorVersion;
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        if (this.databaseMinorVersion == null) {
            return super.getDatabaseMinorVersion();
        }
        return this.databaseMinorVersion;
    }

    @Override
    public Integer getDefaultPort() {
        return 1521;
    }

    @Override
    public String getJdbcCatalogName(CatalogAndSchema schema) {
        return null;
    }

    @Override
    public String getJdbcSchemaName(CatalogAndSchema schema) {
        return this.correctObjectName(schema.getCatalogName() == null ? schema.getSchemaName() : schema.getCatalogName(), Schema.class);
    }

    @Override
    protected String getAutoIncrementClause(String generationType, Boolean defaultOnNull) {
        if (StringUtil.isEmpty(generationType)) {
            return super.getAutoIncrementClause();
        }
        String autoIncrementClause = "GENERATED %s AS IDENTITY";
        String generationStrategy = generationType;
        if (Boolean.TRUE.equals(defaultOnNull) && generationType.toUpperCase().equals("BY DEFAULT")) {
            generationStrategy = generationStrategy + " ON NULL";
        }
        return String.format(autoIncrementClause, generationStrategy);
    }

    @Override
    public String generatePrimaryKeyName(String tableName) {
        if (tableName.length() > 27) {
            return "PK_" + tableName.toUpperCase(Locale.US).substring(0, 27);
        }
        return "PK_" + tableName.toUpperCase(Locale.US);
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return true;
    }

    @Override
    public boolean isReservedWord(String objectName) {
        return this.reservedWords.contains(objectName.toUpperCase());
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Schema.class.isAssignableFrom(object)) {
            return false;
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsSchemas() {
        return false;
    }

    @Override
    protected String getConnectionCatalogName() throws DatabaseException {
        if (this.getConnection() instanceof OfflineConnection) {
            return this.getConnection().getCatalog();
        }
        if (!(this.getConnection() instanceof JdbcConnection)) {
            return this.defaultCatalogName;
        }
        try {
            return Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new RawCallStatement("select sys_context( 'userenv', 'current_schema' ) from dual"), String.class);
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Error getting default schema", e);
            return null;
        }
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return PRODUCT_NAME.equalsIgnoreCase(conn.getDatabaseProductName());
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:oracle")) {
            return "oracle.jdbc.OracleDriver";
        }
        return null;
    }

    @Override
    public String getDefaultCatalogName() {
        String defaultCatalogName = super.getDefaultCatalogName();
        if (Boolean.TRUE.equals(GlobalConfiguration.PRESERVE_SCHEMA_CASE.getCurrentValue())) {
            return defaultCatalogName;
        }
        return defaultCatalogName == null ? null : defaultCatalogName.toUpperCase(Locale.US);
    }

    @Override
    public String getDateLiteral(String isoDate) {
        String normalLiteral = super.getDateLiteral(isoDate);
        if (this.isDateOnly(isoDate)) {
            return "TO_DATE(" + normalLiteral + ", 'YYYY-MM-DD')";
        }
        if (this.isTimeOnly(isoDate)) {
            return "TO_DATE(" + normalLiteral + ", 'HH24:MI:SS')";
        }
        if (this.isTimestamp(isoDate)) {
            return "TO_TIMESTAMP(" + normalLiteral + ", 'YYYY-MM-DD HH24:MI:SS.FF')";
        }
        if (this.isDateTime(isoDate)) {
            int seppos = normalLiteral.lastIndexOf(46);
            if (seppos != -1) {
                normalLiteral = normalLiteral.substring(0, seppos) + "'";
            }
            return "TO_DATE(" + normalLiteral + ", 'YYYY-MM-DD HH24:MI:SS')";
        }
        return "UNSUPPORTED:" + isoDate;
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        if (example == null) {
            return false;
        }
        if (this.isLiquibaseObject(example)) {
            return false;
        }
        if (example instanceof Schema) {
            if ("SYSTEM".equals(example.getName()) || "SYS".equals(example.getName()) || "CTXSYS".equals(example.getName()) || "XDB".equals(example.getName())) {
                return true;
            }
            if ("SYSTEM".equals(example.getSchema().getCatalogName()) || "SYS".equals(example.getSchema().getCatalogName()) || "CTXSYS".equals(example.getSchema().getCatalogName()) || "XDB".equals(example.getSchema().getCatalogName())) {
                return true;
            }
        } else if (this.isSystemObject(example.getSchema())) {
            return true;
        }
        if (example instanceof Catalog) {
            if ("SYSTEM".equals(example.getName()) || "SYS".equals(example.getName()) || "CTXSYS".equals(example.getName()) || "XDB".equals(example.getName())) {
                return true;
            }
        } else if (example.getName() != null) {
            if (example.getName().startsWith("BIN$")) {
                boolean filteredInOriginalQuery = this.canAccessDbaRecycleBin();
                if (!filteredInOriginalQuery) {
                    filteredInOriginalQuery = StringUtil.trimToEmpty(example.getSchema().getName()).equalsIgnoreCase(this.getConnection().getConnectionUserName());
                }
                if (filteredInOriginalQuery) {
                    return !(example instanceof PrimaryKey) && !(example instanceof Index) && !(example instanceof UniqueConstraint);
                }
                return true;
            }
            if (example.getName().startsWith("AQ$")) {
                return true;
            }
            if (example.getName().startsWith("DR$")) {
                return true;
            }
            if (example.getName().startsWith("SYS_IOT_OVER")) {
                return true;
            }
            if ((example.getName().startsWith("MDRT_") || example.getName().startsWith("MDRS_")) && example.getName().endsWith("$")) {
                return true;
            }
            if (example.getName().startsWith("MLOG$_")) {
                return true;
            }
            if (example.getName().startsWith("RUPD$_")) {
                return true;
            }
            if (example.getName().startsWith("WM$_")) {
                return true;
            }
            if ("CREATE$JAVA$LOB$TABLE".equals(example.getName())) {
                return true;
            }
            if ("JAVA$CLASS$MD5$TABLE".equals(example.getName())) {
                return true;
            }
            if (example.getName().startsWith("ISEQ$$_")) {
                return true;
            }
            if (example.getName().startsWith("USLOG$")) {
                return true;
            }
            if (example.getName().startsWith("SYS_FBA")) {
                return true;
            }
        }
        return super.isSystemObject(example);
    }

    @Override
    public boolean supportsTablespaces() {
        return true;
    }

    @Override
    public boolean supportsAutoIncrement() {
        boolean isAutoIncrementSupported = false;
        try {
            if (this.getDatabaseMajorVersion() >= 12) {
                isAutoIncrementSupported = true;
            }
        }
        catch (DatabaseException ex) {
            isAutoIncrementSupported = false;
        }
        return isAutoIncrementSupported;
    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return false;
    }

    @Override
    public int getDataTypeMaxParameters(String dataTypeName) {
        if ("BINARY_FLOAT".equals(dataTypeName.toUpperCase())) {
            return 0;
        }
        if ("BINARY_DOUBLE".equals(dataTypeName.toUpperCase())) {
            return 0;
        }
        return super.getDataTypeMaxParameters(dataTypeName);
    }

    public String getSystemTableWhereClause(String tableNameColumn) {
        ArrayList<String> clauses = new ArrayList<String>(Arrays.asList("BIN$", "AQ$", "DR$", "SYS_IOT_OVER", "MLOG$_", "RUPD$_", "WM$_", "ISEQ$$_", "USLOG$", "SYS_FBA"));
        clauses.replaceAll(s2 -> tableNameColumn + " NOT LIKE '" + s2 + "%'");
        return "(" + StringUtil.join(clauses, " AND ") + ")";
    }

    @Override
    public boolean jdbcCallsCatalogsSchemas() {
        return true;
    }

    public Set<String> getUserDefinedTypes() {
        if (this.userDefinedTypes == null) {
            this.userDefinedTypes = new HashSet<String>();
            if (this.getConnection() != null && !(this.getConnection() instanceof OfflineConnection)) {
                try {
                    try {
                        this.userDefinedTypes.addAll(Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForList((SqlStatement)new RawSqlStatement("SELECT DISTINCT TYPE_NAME FROM ALL_TYPES"), String.class));
                    }
                    catch (DatabaseException e) {
                        this.userDefinedTypes.addAll(Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForList((SqlStatement)new RawSqlStatement("SELECT TYPE_NAME FROM USER_TYPES"), String.class));
                    }
                }
                catch (DatabaseException databaseException) {
                    // empty catch block
                }
            }
        }
        return this.userDefinedTypes;
    }

    @Override
    public String generateDatabaseFunctionValue(DatabaseFunction databaseFunction) {
        if (databaseFunction != null && "current_timestamp".equalsIgnoreCase(databaseFunction.toString())) {
            return databaseFunction.toString();
        }
        if (databaseFunction instanceof SequenceNextValueFunction || databaseFunction instanceof SequenceCurrentValueFunction) {
            String quotedSeq = super.generateDatabaseFunctionValue(databaseFunction);
            return quotedSeq.replaceFirst("\"([^.\"]+)\\.([^.\"]+)\"", "\"$1\".\"$2\"");
        }
        return super.generateDatabaseFunctionValue(databaseFunction);
    }

    @Override
    public ValidationErrors validate() {
        ValidationErrors errors2 = super.validate();
        DatabaseConnection connection = this.getConnection();
        if (connection == null || connection instanceof OfflineConnection) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Cannot validate offline database");
            return errors2;
        }
        if (!this.canAccessDbaRecycleBin()) {
            errors2.addWarning(this.getDbaRecycleBinWarning());
        }
        return errors2;
    }

    public String getDbaRecycleBinWarning() {
        return "Liquibase needs to access the DBA_RECYCLEBIN table so we can automatically handle the case where constraints are deleted and restored. Since Oracle doesn't properly restore the original table names referenced in the constraint, we use the information from the DBA_RECYCLEBIN to automatically correct this issue.\n\nThe user you used to connect to the database (" + this.getConnection().getConnectionUserName() + ") needs to have \"SELECT ON SYS.DBA_RECYCLEBIN\" permissions set before we can perform this operation. Please run the following SQL to set the appropriate permissions, and try running the command again.\n\n     GRANT SELECT ON SYS.DBA_RECYCLEBIN TO " + this.getConnection().getConnectionUserName() + ";";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean canAccessDbaRecycleBin() {
        if (this.canAccessDbaRecycleBin == null) {
            DatabaseConnection connection = this.getConnection();
            if (connection == null || connection instanceof OfflineConnection) {
                return false;
            }
            Statement statement = null;
            try {
                statement = ((JdbcConnection)connection).createStatement();
                ResultSet resultSet = statement.executeQuery("select 1 from dba_recyclebin where 0=1");
                resultSet.close();
                this.canAccessDbaRecycleBin = true;
            }
            catch (Exception e) {
                if (e instanceof SQLException && e.getMessage().startsWith("ORA-00942")) {
                    this.canAccessDbaRecycleBin = false;
                } else {
                    Scope.getCurrentScope().getLog(this.getClass()).warning("Cannot check dba_recyclebin access", e);
                    this.canAccessDbaRecycleBin = false;
                }
            }
            finally {
                JdbcUtil.close(null, statement);
            }
        }
        return this.canAccessDbaRecycleBin;
    }

    @Override
    public boolean supportsNotNullConstraintNames() {
        return true;
    }

    public boolean isValidOracleIdentifier(String identifier, Class<? extends DatabaseObject> type) {
        if (identifier == null || identifier.length() < 1) {
            return false;
        }
        if (!identifier.matches("^(i?)[A-Z][A-Z0-9\\$\\_\\#]*$")) {
            return false;
        }
        return identifier.length() <= 128;
    }

    public int getIdentifierMaximumLength() {
        try {
            if (this.getDatabaseMajorVersion() < 12) {
                return 30;
            }
            if (this.getDatabaseMajorVersion() == 12 && this.getDatabaseMinorVersion() <= 1) {
                return 30;
            }
            return 128;
        }
        catch (DatabaseException ex) {
            throw new UnexpectedLiquibaseException("Cannot determine the Oracle database version number", ex);
        }
    }

    @Override
    public boolean supportsDatabaseChangeLogHistory() {
        return true;
    }
}

