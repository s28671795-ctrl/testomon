/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.sql.Driver;
import java.util.Properties;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.LiquibaseService;

@LiquibaseService(skip=true)
public class MockDatabaseConnection
implements DatabaseConnection {
    private int databaseMajorVersion = 999;
    private int databaseMinorVersion = 999;

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void open(String url2, Driver driverObject, Properties driverProperties) throws DatabaseException {
    }

    @Override
    public void close() throws DatabaseException {
    }

    @Override
    public void commit() throws DatabaseException {
    }

    @Override
    public boolean getAutoCommit() throws DatabaseException {
        return false;
    }

    @Override
    public String getCatalog() throws DatabaseException {
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws DatabaseException {
        return sql;
    }

    @Override
    public void rollback() throws DatabaseException {
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws DatabaseException {
    }

    @Override
    public String getDatabaseProductName() throws DatabaseException {
        return null;
    }

    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        return null;
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        return this.databaseMajorVersion;
    }

    public MockDatabaseConnection setDatabaseMajorVersion(int databaseMajorVersion) {
        this.databaseMajorVersion = databaseMajorVersion;
        return this;
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        return this.databaseMinorVersion;
    }

    public MockDatabaseConnection setDatabaseMinorVersion(int databaseMinorVersion) {
        this.databaseMinorVersion = databaseMinorVersion;
        return this;
    }

    @Override
    public String getURL() {
        return "jdbc://mock";
    }

    @Override
    public String getConnectionUserName() {
        return null;
    }

    @Override
    public boolean isClosed() throws DatabaseException {
        return false;
    }

    @Override
    public void attached(Database database) {
    }
}

