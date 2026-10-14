/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.CatalogAndSchema;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.DateParseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.statement.DatabaseFunction;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Table;
import liquibase.util.BooleanUtil;
import liquibase.util.ISODateFormat;
import liquibase.util.JdbcUtil;

public class H2Database
extends AbstractJdbcDatabase {
    private static final String PATCH_VERSION_REGEX = "^(?:\\d+\\.)(?:\\d+\\.)(\\d+).*$";
    private static final Pattern PATCH_VERSION_PATTERN = Pattern.compile("^(?:\\d+\\.)(?:\\d+\\.)(\\d+).*$");
    private static final String START_CONCAT = "CONCAT(";
    private static final String END_CONCAT = ")";
    private static final String SEP_CONCAT = ", ";
    private static final String LEGAL_IDENTIFIER_REGEX = "^[a-zA-Z_][a-zA-Z_0-9]*$";
    private static final Pattern LEGAL_IDENTIFIER_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z_0-9]*$");
    protected static final Set<String> V2_RESERVED_WORDS = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList("ALL", "AND", "ANY", "ARRAY", "AS", "ASYMMETRIC", "AUTHORIZATION", "BETWEEN", "BOTH", "CASE", "CAST", "CHECK", "CONSTRAINT", "CROSS", "CURRENT_CATALOG", "CURRENT_DATE", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_SCHEMA", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "DAY", "DEFAULT", "DISTINCT", "ELSE", "END", "EXCEPT", "EXISTS", "FALSE", "FETCH", "FILTER", "FOR", "FOREIGN", "FROM", "FULL", "GROUP", "GROUPS", "HAVING", "HOUR", "IF", "ILIKE", "IN", "INNER", "INTERSECT", "INTERSECTS", "INTERVAL", "IS", "JOIN", "KEY", "LEADING", "LEFT", "LIKE", "LIMIT", "LOCALTIME", "LOCALTIMESTAMP", "MINUS", "MINUTE", "MONTH", "NATURAL", "NOT", "NULL", "OFFSET", "ON", "OR", "ORDER", "OVER", "PARTITION", "PRIMARY", "QUALIFY", "RANGE", "REGEXP", "RIGHT", "ROW", "ROWNUM", "ROWS", "SELECT", "SESSION_USER", "SET", "SOME", "SYMMETRIC", "SYSDATE", "SYSTEM_USER", "SYSTIME", "SYSTIMESTAMP", "TABLE", "TO", "TODAY", "TOP", "TRAILING", "TRUE", "UNESCAPE", "UNION", "UNIQUE", "UNKNOWN", "USER", "USING", "VALUE", "VALUES", "WHEN", "WHERE", "WINDOW", "WITH", "YEAR", "_ROWID_")));
    protected static final Set<String> V1_RESERVED_WORDS = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList("ALL", "AND", "ARRAY", "AS", "BETWEEN", "BOTH", "CASE", "CHECK", "CONSTRAINT", "CROSS", "CURRENT_CATALOG", "CURRENT_DATE", "CURRENT_SCHEMA", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "DISTINCT", "EXCEPT", "EXISTS", "FALSE", "FETCH", "FILTER", "FOR", "FOREIGN", "FROM", "FULL", "GROUP", "GROUPS", "HAVING", "IF", "ILIKE", "IN", "INNER", "INTERSECT", "INTERSECTS", "INTERVAL", "IS", "JOIN", "LEADING", "LEFT", "LIKE", "LIMIT", "LOCALTIME", "LOCALTIMESTAMP", "MINUS", "NATURAL", "NOT", "NULL", "OFFSET", "ON", "OR", "ORDER", "OVER", "PARTITION", "PRIMARY", "QUALIFY", "RANGE", "REGEXP", "RIGHT", "ROW", "ROWNUM", "ROWS", "SELECT", "SYSDATE", "SYSTIME", "SYSTIMESTAMP", "TABLE", "TODAY", "TOP", "TRAILING", "TRUE", "UNION", "UNIQUE", "UNKNOWN", "USING", "VALUES", "WHERE", "WINDOW", "WITH", "_ROWID_")));
    private String connectionSchemaName = "PUBLIC";
    private static final int MAJOR_VERSION_FOR_MINMAX_IN_SEQUENCES = 1;
    private static final int MINOR_VERSION_FOR_MINMAX_IN_SEQUENCES = 3;
    private static final int BUILD_VERSION_FOR_MINMAX_IN_SEQUENCES = 175;

    public H2Database() {
        this.unquotedObjectsAreUppercased = true;
        super.setCurrentDateTimeFunction("NOW()");
        this.dateFunctions.add(new DatabaseFunction("CURRENT_DATE"));
        this.dateFunctions.add(new DatabaseFunction("CURDATE"));
        this.dateFunctions.add(new DatabaseFunction("SYSDATE"));
        this.dateFunctions.add(new DatabaseFunction("TODAY"));
        this.dateFunctions.add(new DatabaseFunction("CURRENT_TIME"));
        this.dateFunctions.add(new DatabaseFunction("CURTIME"));
        this.dateFunctions.add(new DatabaseFunction("CURRENT_TIMESTAMP"));
        this.dateFunctions.add(new DatabaseFunction("NOW"));
        this.sequenceNextValueFunction = "NEXTVAL('%s')";
        this.sequenceCurrentValueFunction = "CURRVAL('%s')";
        this.unmodifiableDataTypes.addAll(Arrays.asList("int", "integer", "mediumint", "int4", "signed", "boolean", "bit", "bool", "tinyint", "smallint", "int2", "year", "bigint", "int8", "identity", "float", "float8", "real", "float4", "time", "date", "timestamp", "datetime", "smalldatetime", "timestamp with time zone", "other", "uuid", "array", "geometry"));
    }

    @Override
    public String getShortName() {
        return "h2";
    }

    @Override
    public Integer getDefaultPort() {
        return 8082;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "H2";
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:h2")) {
            return "org.h2.Driver";
        }
        return null;
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return "H2".equals(conn.getDatabaseProductName());
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String name) throws DatabaseException {
        String definition = super.getViewDefinition(schema, name);
        if (!definition.startsWith("SELECT")) {
            definition = definition.replaceFirst(".*?\\n", "");
        }
        definition = definition.replaceFirst("/\\*.*", "");
        return definition;
    }

    @Override
    public Date parseDate(String dateAsString) throws DateParseException {
        try {
            return new ISODateFormat().parse(dateAsString);
        }
        catch (ParseException e) {
            throw new DateParseException(dateAsString);
        }
    }

    @Override
    public boolean isSafeToRunUpdate() throws DatabaseException {
        if (this.getConnection() == null) {
            return true;
        }
        String url2 = this.getConnection().getURL();
        return super.isSafeToRunUpdate() || !url2.startsWith("jdbc:h2:tcp:") && !url2.startsWith("jdbc:h2:ssl:");
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    protected String getConnectionSchemaName() {
        return this.connectionSchemaName;
    }

    @Override
    public String getConcatSql(String ... values2) {
        if (values2 == null) {
            return null;
        }
        return this.getConcatSql(Arrays.asList(values2));
    }

    private String getConcatSql(List<String> values2) {
        if (values2.size() == 1) {
            return values2.get(0);
        }
        return START_CONCAT + values2.get(0) + SEP_CONCAT + this.getConcatSql(values2.subList(1, values2.size())) + END_CONCAT;
    }

    @Override
    public String getDateLiteral(String isoDate) {
        return "'" + isoDate.replace('T', ' ') + "'";
    }

    @Override
    public boolean isReservedWord(String objectName) {
        return super.isReservedWord(objectName) || this.getReservedWords().contains(objectName.toUpperCase(Locale.US));
    }

    protected Set<String> getReservedWords() {
        try {
            if (this.getDatabaseMajorVersion() >= 2) {
                return V2_RESERVED_WORDS;
            }
        }
        catch (DatabaseException e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Failed to determine database version, reported error: " + e.getMessage());
        }
        return V1_RESERVED_WORDS;
    }

    @Override
    public String correctObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName == null) {
            return null;
        }
        if (this.isCatalogOrSchemaType(objectType)) {
            if (BooleanUtil.isTrue(GlobalConfiguration.PRESERVE_SCHEMA_CASE.getCurrentValue()) || this.getSchemaAndCatalogCase() == CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE) {
                return objectName;
            }
            if (this.getSchemaAndCatalogCase() == CatalogAndSchema.CatalogAndSchemaCase.UPPER_CASE) {
                return objectName.toUpperCase(Locale.US);
            }
            if (this.getSchemaAndCatalogCase() == CatalogAndSchema.CatalogAndSchemaCase.LOWER_CASE) {
                return objectName.toLowerCase(Locale.US);
            }
        }
        if (this.unquotedObjectsAreUppercased == null || this.quotingStrategy == ObjectQuotingStrategy.QUOTE_ALL_OBJECTS) {
            return objectName;
        }
        if (BooleanUtil.isTrue(this.unquotedObjectsAreUppercased)) {
            return objectName.toUpperCase(Locale.US);
        }
        return objectName.toLowerCase(Locale.US);
    }

    @Override
    public String escapeObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName == null) {
            return null;
        }
        return this.mustQuoteObjectName(objectName, objectType) ? this.quoteObject(this.correctObjectName(objectName, objectType), objectType) : objectName;
    }

    @Override
    protected boolean mustQuoteObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        return this.quotingStrategy == ObjectQuotingStrategy.QUOTE_ALL_OBJECTS || this.isCatalogOrSchemaType(objectType) && (BooleanUtil.isTrue(GlobalConfiguration.PRESERVE_SCHEMA_CASE.getCurrentValue()) || this.getSchemaAndCatalogCase() == CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE) || this.isIllegalIdentifier(objectName) || this.isReservedWord(objectName);
    }

    private boolean isIllegalIdentifier(String objectName) {
        return !LEGAL_IDENTIFIER_PATTERN.matcher(objectName).matches();
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    protected String getAutoIncrementClause() {
        try {
            if (this.getDatabaseMajorVersion() == 1) {
                return "AUTO_INCREMENT";
            }
            return "GENERATED BY DEFAULT AS IDENTITY";
        }
        catch (DatabaseException e) {
            return "AUTO_INCREMENT";
        }
    }

    @Override
    protected String getAutoIncrementStartWithClause() {
        try {
            if (this.getDatabaseMajorVersion() == 1) {
                return "%d";
            }
            return super.getAutoIncrementStartWithClause();
        }
        catch (DatabaseException e) {
            return "%d";
        }
    }

    @Override
    protected String getAutoIncrementByClause() {
        try {
            if (this.getDatabaseMajorVersion() == 1) {
                return "%d";
            }
            return super.getAutoIncrementByClause();
        }
        catch (DatabaseException e) {
            return "%d";
        }
    }

    @Override
    public String getAutoIncrementClause(BigInteger startWith, BigInteger incrementBy, String generationType, Boolean defaultOnNull) {
        String clause = super.getAutoIncrementClause(startWith, incrementBy, generationType, defaultOnNull);
        if (clause.startsWith("AUTO_INCREMENT")) {
            return clause;
        }
        return clause.replace(",", "");
    }

    @Override
    public boolean createsIndexesForForeignKeys() {
        return true;
    }

    @Override
    public boolean supportsDropTableCascadeConstraints() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setConnection(DatabaseConnection conn) {
        Connection sqlConn = null;
        if (!(conn instanceof OfflineConnection)) {
            try {
                if (conn instanceof JdbcConnection) {
                    Method wrappedConn = conn.getClass().getMethod("getWrappedConnection", new Class[0]);
                    wrappedConn.setAccessible(true);
                    sqlConn = (Connection)wrappedConn.invoke((Object)conn, new Object[0]);
                }
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
            if (sqlConn != null) {
                ResultSet resultSet;
                Statement statement;
                block10: {
                    statement = null;
                    resultSet = null;
                    try {
                        statement = sqlConn.createStatement();
                        resultSet = statement.executeQuery("SELECT SCHEMA()");
                        String schemaName = null;
                        if (resultSet.next()) {
                            schemaName = resultSet.getString(1);
                        }
                        if (schemaName == null) break block10;
                        this.connectionSchemaName = schemaName;
                    }
                    catch (SQLException e) {
                        try {
                            Scope.getCurrentScope().getLog(this.getClass()).info("Could not read current schema name: " + e.getMessage());
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
        super.setConnection(conn);
    }

    public boolean supportsMinMaxForSequences() {
        try {
            if (this.getDatabaseMajorVersion() > 1) {
                return true;
            }
            if (this.getDatabaseMajorVersion() == 1 && this.getDatabaseMinorVersion() > 3) {
                return true;
            }
            if (this.getDatabaseMajorVersion() == 1 && this.getDatabaseMinorVersion() == 3 && this.getBuildVersion() >= 175) {
                return true;
            }
        }
        catch (DatabaseException e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Failed to determine database version, reported error: " + e.getMessage());
        }
        return false;
    }

    private int getBuildVersion() throws DatabaseException {
        Matcher matcher = PATCH_VERSION_PATTERN.matcher(this.getDatabaseProductVersion());
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        }
        Scope.getCurrentScope().getLog(this.getClass()).warning("Failed to determine H2 build number from product version: " + this.getDatabaseProductVersion());
        return -1;
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        return 9;
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

