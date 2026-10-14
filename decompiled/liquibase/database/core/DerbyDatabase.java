/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Locale;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;

public class DerbyDatabase
extends AbstractJdbcDatabase {
    protected int driverVersionMajor;
    protected int driverVersionMinor;
    private boolean shutdownEmbeddedDerby = true;

    public DerbyDatabase() {
        super.setCurrentDateTimeFunction("CURRENT_TIMESTAMP");
        this.sequenceNextValueFunction = "NEXT VALUE FOR %s";
        this.sequenceCurrentValueFunction = "(SELECT currentvalue FROM sys.syssequences WHERE sequencename = upper('%s'))";
        this.determineDriverVersion();
        this.addReservedWords(Arrays.asList("ADD", "ALL", "ALLOCATE", "ALTER", "AND", "ANY", "ARE", "AS", "ASC", "ASSERTION", "AT", "AUTHORIZATION", "AVG", "BEGIN", "BETWEEN", "BIGINT", "BIT", "BOOLEAN", "BOTH", "BY", "CALL", "CASCADE", "CASCADED", "CASE", "CAST", "CHAR", "CHARACTER", "CHECK", "CLOSE", "COALESCE", "COLLATE", "COLLATION", "COLUMN", "COMMIT", "CONNECT", "CONNECTION", "CONSTRAINT", "CONSTRAINTS", "CONTINUE", "CONVERT", "CORRESPONDING", "CREATE", "CURRENT", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DEALLOCATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DEFERRABLE", "DEFERRED", "DELETE", "DESC", "DESCRIBE", "DIAGNOSTICS", "DISCONNECT", "DISTINCT", "DOUBLE", "DROP", "ELSE", "END", "END-EXEC", "ESCAPE", "EXCEPT", "EXCEPTION", "EXEC", "EXECUTE", "EXISTS", "EXPLAIN", "EXTERNAL", "FALSE", "FETCH", "FIRST", "FLOAT", "FOR", "FOREIGN", "FOUND", "FROM", "FULL", "FUNCTION", "GET", "GETCURRENTCONNECTION", "GLOBAL", "GO", "GOTO", "GRANT", "GROUP", "HAVING", "HOUR", "IDENTITY", "IMMEDIATE", "IN", "INDICATOR", "INITIALLY", "INNER", "INOUT", "INPUT", "INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERSECT", "INTO", "IS", "ISOLATION", "JOIN", "KEY", "LAST", "LEFT", "LIKE", "LOWER", "LTRIM", "MATCH", "MAX", "MIN", "MINUTE", "NATIONAL", "NATURAL", "NCHAR", "NVARCHAR", "NEXT", "NO", "NOT", "NULL", "NULLIF", "NUMERIC", "OF", "ON", "ONLY", "OPEN", "OPTION", "OR", "ORDER", "OUTER", "OUTPUT", "OVERLAPS", "PAD", "PARTIAL", "PREPARE", "PRESERVE", "PRIMARY", "PRIOR", "PRIVILEGES", "PROCEDURE", "PUBLIC", "READ", "REAL", "REFERENCES", "RELATIVE", "RESTRICT", "REVOKE", "RIGHT", "ROLLBACK", "ROWS", "RTRIM", "SCHEMA", "SCROLL", "SECOND", "SELECT", "SESSION_USER", "SET", "SMALLINT", "SOME", "SPACE", "SQL", "SQLCODE", "SQLERROR", "SQLSTATE", "SUBSTR", "SUBSTRING", "SUM", "SYSTEM_USER", "TABLE", "TEMPORARY", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TRANSACTION", "TRANSLATE", "TRANSLATION", "TRUE", "UNION", "UNIQUE", "UNKNOWN", "UPDATE", "UPPER", "USER", "USING", "VALUES", "VARCHAR", "VARYING", "VIEW", "WHENEVER", "WHERE", "WITH", "WORK", "WRITE", "XML", "XMLEXISTS", "XMLPARSE", "XMLQUERY", "XMLSERIALIZE", "YEAR"));
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return "Apache Derby".equalsIgnoreCase(conn.getDatabaseProductName());
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2 == null) {
            return null;
        }
        if (url2.toLowerCase().startsWith("jdbc:derby://")) {
            String derbyNewDriverClassName = "org.apache.derby.client.ClientAutoloadedDriver";
            String derbyOldDriverClassName = "org.apache.derby.jdbc.ClientDriver";
            try {
                Class.forName(derbyNewDriverClassName);
                return derbyNewDriverClassName;
            }
            catch (ClassNotFoundException exception) {
                try {
                    Class.forName(derbyOldDriverClassName);
                    return derbyOldDriverClassName;
                }
                catch (ClassNotFoundException classNotFoundException) {
                    return derbyNewDriverClassName;
                }
            }
        }
        if (url2.startsWith("jdbc:derby") || url2.startsWith("java:derby")) {
            return "org.apache.derby.jdbc.EmbeddedDriver";
        }
        return null;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Schema.class.isAssignableFrom(object)) {
            return false;
        }
        if (Sequence.class.isAssignableFrom(object)) {
            return this.driverVersionMajor == 10 && this.driverVersionMinor >= 6 || this.driverVersionMajor >= 11;
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsSchemas() {
        return false;
    }

    @Override
    public boolean jdbcCallsCatalogsSchemas() {
        return true;
    }

    @Override
    public Integer getDefaultPort() {
        return 1527;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Derby";
    }

    @Override
    public String correctObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName == null) {
            return null;
        }
        return objectName.toUpperCase(Locale.US);
    }

    @Override
    public String getShortName() {
        return "derby";
    }

    public boolean getShutdownEmbeddedDerby() {
        return this.shutdownEmbeddedDerby;
    }

    public void setShutdownEmbeddedDerby(boolean shutdown) {
        this.shutdownEmbeddedDerby = shutdown;
    }

    @Override
    public boolean supportsSequences() {
        return this.driverVersionMajor == 10 && this.driverVersionMinor >= 6 || this.driverVersionMajor >= 11;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public String getDateLiteral(String isoDate) {
        if (this.isDateOnly(isoDate)) {
            return "DATE(" + super.getDateLiteral(isoDate) + ")";
        }
        if (this.isTimeOnly(isoDate)) {
            return "TIME(" + super.getDateLiteral(isoDate) + ")";
        }
        String dateString = super.getDateLiteral(isoDate);
        int decimalDigits = dateString.length() - dateString.indexOf(46) - 2;
        StringBuilder padding = new StringBuilder();
        for (int i = 6; i > decimalDigits; --i) {
            padding.append("0");
        }
        return "TIMESTAMP(" + dateString.replaceFirst("'$", padding + "'") + ")";
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String name) throws DatabaseException {
        return super.getViewDefinition(schema, name).replaceFirst("CREATE VIEW \\w+ AS ", "");
    }

    @Override
    public void close() throws DatabaseException {
        if (this.getConnection() != null) {
            String url2 = this.getConnection().getURL();
            String driverName = this.getDefaultDriver(url2);
            super.close();
            if (this.shutdownEmbeddedDerby && driverName != null && driverName.toLowerCase().contains("embedded")) {
                this.shutdownDerby(url2, driverName);
            }
        }
    }

    protected void shutdownDerby(String url2, String driverName) throws DatabaseException {
        try {
            url2 = url2.contains(";") ? url2.substring(0, url2.indexOf(";")) + ";shutdown=true" : url2 + ";shutdown=true";
            Scope.getCurrentScope().getLog(this.getClass()).info("Shutting down derby connection: " + url2);
            JdbcConnection connection = (JdbcConnection)this.getConnection();
            ClassLoader classLoader = connection.getWrappedConnection().getClass().getClassLoader();
            Driver driver = (Driver)classLoader.loadClass(driverName).getConstructor(new Class[0]).newInstance(new Object[0]);
            driver.connect(url2, null);
        }
        catch (Exception e) {
            String state;
            if (e instanceof SQLException && ("XJ015".equals(state = ((SQLException)e).getSQLState()) || "08006".equals(state))) {
                return;
            }
            throw new DatabaseException("Error closing derby cleanly", e);
        }
    }

    protected void determineDriverVersion() {
        try {
            Enumeration<Driver> it = DriverManager.getDrivers();
            while (it.hasMoreElements()) {
                Driver driver = it.nextElement();
                if (!driver.getClass().getName().contains("derby")) continue;
                this.driverVersionMajor = driver.getMajorVersion();
                this.driverVersionMinor = driver.getMinorVersion();
                return;
            }
            this.driverVersionMajor = 10;
            this.driverVersionMinor = 6;
        }
        catch (Exception e) {
            this.driverVersionMajor = 10;
            this.driverVersionMinor = 6;
        }
    }

    @Override
    protected String getConnectionCatalogName() throws DatabaseException {
        if (this.getConnection() == null || this.getConnection() instanceof OfflineConnection) {
            return null;
        }
        try {
            return Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new RawSqlStatement("select current schema from sysibm.sysdummy1"), String.class);
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Error getting default schema", e);
            return null;
        }
    }

    @Override
    public boolean supportsCatalogInObjectName(Class<? extends DatabaseObject> type) {
        return true;
    }

    public boolean supportsBooleanDataType() {
        if (this.getConnection() == null) {
            return false;
        }
        try {
            return this.getDatabaseMajorVersion() > 10 || this.getDatabaseMajorVersion() == 10 && this.getDatabaseMinorVersion() > 7;
        }
        catch (DatabaseException e) {
            return false;
        }
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        return 9;
    }
}

