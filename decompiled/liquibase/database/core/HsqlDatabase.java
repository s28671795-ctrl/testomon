/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.DatabaseException;
import liquibase.exception.DateParseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Table;
import liquibase.util.ISODateFormat;

public class HsqlDatabase
extends AbstractJdbcDatabase {
    private static final Map<String, HashSet<String>> SUPPORTED_DEFAULT_VALUE_COMPUTED_MAP;
    private static final String START_CONCAT = "CONCAT(";
    private static final String END_CONCAT = ")";
    private static final String SEP_CONCAT = ", ";
    private static final List keywords;
    private Boolean oracleSyntax;

    public HsqlDatabase() {
        this.unquotedObjectsAreUppercased = true;
        super.setCurrentDateTimeFunction("NOW");
        this.sequenceNextValueFunction = "NEXT VALUE FOR %s";
        this.defaultAutoIncrementStartWith = BigInteger.ZERO;
        this.sequenceCurrentValueFunction = "CURRVAL('%s')";
    }

    public static boolean supportsDefaultValueComputed(String columnType, String defaultValue) {
        HashSet<String> possibleComputedValues = SUPPORTED_DEFAULT_VALUE_COMPUTED_MAP.get(columnType);
        return possibleComputedValues != null && possibleComputedValues.contains(defaultValue.toLowerCase());
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return "HSQL Database Engine".equalsIgnoreCase(conn.getDatabaseProductName());
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:hsqldb:")) {
            return "org.hsqldb.jdbcDriver";
        }
        return null;
    }

    @Override
    public Integer getDefaultPort() {
        return 9001;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "HyperSQL";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getShortName() {
        return "hsqldb";
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Catalog.class.isAssignableFrom(object)) {
            try {
                return this.getDatabaseMajorVersion() >= 2;
            }
            catch (DatabaseException e) {
                return true;
            }
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsCatalogs() {
        try {
            return this.getDatabaseMajorVersion() >= 2;
        }
        catch (DatabaseException e) {
            return true;
        }
    }

    @Override
    protected String getConnectionCatalogName() throws DatabaseException {
        if (this.supports(Catalog.class)) {
            return "PUBLIC";
        }
        return null;
    }

    @Override
    protected String getConnectionSchemaName() {
        return "PUBLIC";
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
        String returnString = isoDate;
        try {
            if (this.isDateTime(isoDate)) {
                ISODateFormat isoTimestampFormat = new ISODateFormat();
                SimpleDateFormat dbTimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                returnString = dbTimestampFormat.format(isoTimestampFormat.parse(isoDate));
            }
        }
        catch (ParseException e) {
            throw new RuntimeException("Unexpected date format: " + isoDate, e);
        }
        return "'" + returnString + "'";
    }

    @Override
    public Date parseDate(String dateAsString) throws DateParseException {
        try {
            if (dateAsString.indexOf(" ") > 0) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateAsString);
            }
            if (dateAsString.indexOf(":") > 0) {
                return new SimpleDateFormat("HH:mm:ss").parse(dateAsString);
            }
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateAsString);
        }
        catch (ParseException e) {
            throw new DateParseException(dateAsString);
        }
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public boolean isReservedWord(String value) {
        return keywords.contains(value.toUpperCase());
    }

    @Override
    public boolean isCaseSensitive() {
        return false;
    }

    @Override
    public void setConnection(DatabaseConnection conn) {
        this.oracleSyntax = null;
        super.setConnection(conn);
    }

    public boolean isUsingOracleSyntax() {
        if (this.oracleSyntax == null) {
            this.oracleSyntax = Boolean.FALSE;
            if (this.getConnection() != null && this.getConnection().getURL() != null) {
                for (String str : this.getConnection().getURL().split(";")) {
                    if (!str.contains("sql.syntax_ora") || !str.contains("=")) continue;
                    this.oracleSyntax = Boolean.valueOf(str.split("=")[1].trim());
                    break;
                }
            }
        }
        return this.oracleSyntax;
    }

    @Override
    public String escapeObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (this.quotingStrategy == ObjectQuotingStrategy.QUOTE_ALL_OBJECTS) {
            return super.escapeObjectName(objectName, objectType);
        }
        if (objectName != null && this.quotingStrategy != ObjectQuotingStrategy.QUOTE_ALL_OBJECTS && this.isReservedWord(objectName.toUpperCase(Locale.US))) {
            return "\"" + objectName.toUpperCase(Locale.US) + "\"";
        }
        return objectName;
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        return 9;
    }

    @Override
    public String getAutoIncrementClause(BigInteger startWith, BigInteger incrementBy, String generationType, Boolean defaultOnNull) {
        String clause = super.getAutoIncrementClause(startWith, incrementBy, generationType, defaultOnNull);
        return clause.replace(",", "");
    }

    @Override
    public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return type.isAssignableFrom(Table.class);
    }

    @Override
    public boolean supportsDatabaseChangeLogHistory() {
        return true;
    }

    static {
        keywords = Arrays.asList("ADD", "ALL", "ALLOCATE", "ALTER", "AND", "ANY", "ARE", "ARRAY", "AS", "ASENSITIVE", "ASYMMETRIC", "AT", "ATOMIC", "AUTHORIZATION", "AVG", "BEGIN", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOOLEAN", "BOTH", "BY", "CALL", "CALLED", "CASCADED", "CASE", "CAST", "CHAR", "CHARACTER", "CHECK", "CLOB", "CLOSE", "COLLATE", "COLUMN", "COMMIT", "CONDITION", "CONNECT", "CONSTRAINT", "CONTINUE", "CORRESPONDING", "COUNT", "CREATE", "CROSS", "CUBE", "CURRENT", "CURRENT_DATE", "CURRENT_DEFAULT_TRANSFORM_GRO", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_TRANSFORM_GROUP_FOR_T", "CURRENT_USER", "CURSOR", "DATE", "DAY", "DEALLOCATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELETE", "DEREF", "DESCRIBE", "DETERMINISTIC", "DISCONNECT", "DISTINCT", "DO", "DOUBLE", "DROP", "DYNAMIC", "EACH", "ELEMENT", "ELSE", "ELSEIF", "END", "ESCAPE", "EXCEPT", "EXEC", "EXECUTE", "EXISTS", "EXIT", "EXTERNAL", "FALSE", "FETCH", "FILTER", "FLOAT", "FOR", "FOREIGN", "FREE", "FROM", "FULL", "FUNCTION", "GET", "GLOBAL", "GRANT", "GROUP", "HAVING", "HOLD", "HOUR", "IDENTITY", "IF", "IMMEDIATE", "IN", "INDICATOR", "INNER", "INOUT", "INPUT", "INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERSECT", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "LANGUAGE", "LARGE", "LEADING", "LEAVE", "LEFT", "LIKE", "LOCAL", "LOCALTIME", "LOCALTIMESTAMP", "LOOP", "MATCH", "MAX", "MEMBER", "MERGE", "METHOD", "MIN", "MINUTE", "MODIFIES", "MODULE", "MONTH", "MULTISET", "NATIONAL", "NATURAL", "NCHAR", "NCLOB", "NEW", "NO", "NONE", "NOT", "NULL", "NUMERIC", "OF", "ON", "ONLY", "OPEN", "OR", "ORDER", "OUT", "OUTER", "OUTPUT", "OVER", "OVERLAPS", "PARAMETER", "PARTITION", "PRECISION", "PREPARE", "PRIMARY", "PROCEDURE", "RANGE", "READS", "REAL", "RECURSIVE", "REF", "REFERENCES", "REFERENCING", "RELEASE", "REPEAT", "RESIGNAL", "RESULT", "RETURN", "RETURNS", "REVOKE", "RIGHT", "ROLLBACK", "ROLLUP", "ROW", "ROWS", "SAVEPOINT", "SCOPE", "SCROLL", "SEARCH", "SELECT", "SENSITIVE", "SESSION_USER", "SET", "SIGNAL", "SIMILAR", "SMALLINT", "SOME", "SPECIFIC", "SPECIFICTYPE", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "START", "STATIC", "SUBMULTISET", "SUM", "SYMMETRIC", "SYSTEM", "SYSTEM_USER", "TABLE", "TABLESAMPLE", "THEN", "TIME", "TIMESTAMP", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TRAILING", "TREAT", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNNEST", "UNTIL", "UPDATE", "USER", "USING", "VALUE", "VALUES", "VARCHAR", "VARYING", "WHEN", "WHENEVER", "WHERE", "WHILE", "WINDOW", "WITHIN", "WITHOUT", "YEAR", "ALIAS", "AUTOCOMMIT", "CACHED", "CHECKPOINT", "EXPLAIN", "IGNORECASE", "INDEX", "LOGSIZE", "MATCHED", "MAXROWS", "MEMORY", "MINUS", "NEXT", "OPENBRACKET", "PLAN", "PROPERTY", "READONLY", "REFERENTIAL_INTEGRITY", "RENAME", "RESTART", "SCRIPT", "SCRIPTFORMAT", "SEMICOLON", "SHUTDOWN", "TEMP", "TEXT", "VIEW", "WRITE_DELAY", "VAR_POP", "VAR_SAMP", "STDDEV_POP", "STDDEV_SAMP", "DEFRAG", "INCREMENT", "TOCHAR", "DATABASE", "SCHEMA", "ROLE", "DOW", "INITIAL");
        HashMap<String, HashSet<String>> tempMap = new HashMap<String, HashSet<String>>();
        tempMap.put("datetime", new HashSet<String>(Arrays.asList("CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "TODAY", "NOW", "current_datetime")));
        SUPPORTED_DEFAULT_VALUE_COMPUTED_MAP = Collections.unmodifiableMap(tempMap);
    }
}

