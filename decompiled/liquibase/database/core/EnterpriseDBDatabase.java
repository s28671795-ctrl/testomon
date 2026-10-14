/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import liquibase.Scope;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.structure.DatabaseObject;
import liquibase.util.JdbcUtil;

public class EnterpriseDBDatabase
extends PostgresDatabase {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        String url2 = conn.getURL();
        if (!url2.startsWith("jdbc:edb:")) {
            if (!url2.startsWith("jdbc:postgres")) return false;
        }
        if (!(conn instanceof JdbcConnection)) return false;
        try (Statement stmt = ((JdbcConnection)conn).createStatement();){
            if (stmt == null) return false;
            try (ResultSet rs = stmt.executeQuery("select version()");){
                if (!rs.next()) return false;
                boolean bl = ((String)JdbcUtil.getResultSetValue(rs, 1)).contains("EnterpriseDB");
                return bl;
            }
        }
        catch (SQLException e) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Error checking if connection is an EnterpriseDB database: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String getShortName() {
        return "edb";
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:edb:")) {
            return "com.edb.Driver";
        }
        return null;
    }

    @Override
    public Integer getDefaultPort() {
        return 5444;
    }

    @Override
    public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return false;
    }
}

