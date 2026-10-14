/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.Scope;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Table;
import liquibase.util.JdbcUtil;

public class CockroachDatabase
extends PostgresDatabase {
    private Integer databaseMajorVersion;
    private Integer databaseMinorVersion;
    private static final String VERSION_NUMBER_REGEX = "v(\\d+)\\.(\\d+)\\.(\\d+)";
    private static final Pattern VERSION_NUMBER_PATTERN = Pattern.compile("v(\\d+)\\.(\\d+)\\.(\\d+)");

    public CockroachDatabase() {
        super.setCurrentDateTimeFunction("NOW()");
    }

    @Override
    public int getPriority() {
        return super.getPriority() + 5;
    }

    @Override
    public String getShortName() {
        return "cockroachdb";
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "CockroachDB";
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        if (this.databaseMajorVersion == null) {
            return 20;
        }
        return this.databaseMajorVersion;
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        if (this.databaseMinorVersion == null) {
            return 0;
        }
        return this.databaseMinorVersion;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        String url2 = conn.getURL();
        if (!url2.startsWith("jdbc:postgres")) {
            if (!url2.startsWith("postgres")) return false;
        }
        if (!(conn instanceof JdbcConnection)) return false;
        try (Statement stmt = ((JdbcConnection)conn).createStatement();){
            if (stmt == null) return false;
            try (ResultSet rs = stmt.executeQuery("select version()");){
                if (!rs.next()) return false;
                boolean bl = ((String)JdbcUtil.getResultSetValue(rs, 1)).startsWith("CockroachDB");
                return bl;
            }
        }
        catch (SQLException throwables) {
            return false;
        }
    }

    @Override
    public Integer getDefaultPort() {
        return 26257;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public boolean supportsDDLInTransaction() {
        return false;
    }

    @Override
    public void setConnection(DatabaseConnection conn) {
        super.setConnection(conn);
        this.databaseMajorVersion = null;
        this.databaseMinorVersion = null;
        if (conn instanceof JdbcConnection) {
            try {
                String version = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new RawSqlStatement("SELECT version()"), String.class);
                Matcher versionMatcher = VERSION_NUMBER_PATTERN.matcher(version);
                if (versionMatcher.find()) {
                    this.databaseMajorVersion = Integer.parseInt(versionMatcher.group(1));
                    this.databaseMinorVersion = Integer.parseInt(versionMatcher.group(2));
                }
            }
            catch (Throwable e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot determine cockroachdb version: " + e.getMessage(), e);
            }
        } else {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot determine cockroachdb version: cannot query database");
        }
    }

    @Override
    public boolean useSerialDatatypes() {
        try {
            return this.getDatabaseMajorVersion() <= 21 && (this.getDatabaseMajorVersion() != 21 || this.getDatabaseMinorVersion() < 2);
        }
        catch (DatabaseException e) {
            return true;
        }
    }

    @Override
    public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return type.isAssignableFrom(Table.class);
    }
}

