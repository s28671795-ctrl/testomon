/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.sql.Driver;
import java.util.Properties;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.PrioritizedService;

public interface DatabaseConnection
extends PrioritizedService,
AutoCloseable {
    public void open(String var1, Driver var2, Properties var3) throws DatabaseException;

    default public boolean supports(String url2) {
        return true;
    }

    @Override
    public void close() throws DatabaseException;

    public void commit() throws DatabaseException;

    public boolean getAutoCommit() throws DatabaseException;

    public String getCatalog() throws DatabaseException;

    public String nativeSQL(String var1) throws DatabaseException;

    public void rollback() throws DatabaseException;

    public void setAutoCommit(boolean var1) throws DatabaseException;

    public String getDatabaseProductName() throws DatabaseException;

    public String getDatabaseProductVersion() throws DatabaseException;

    public int getDatabaseMajorVersion() throws DatabaseException;

    public int getDatabaseMinorVersion() throws DatabaseException;

    public String getURL();

    public String getConnectionUserName();

    public boolean isClosed() throws DatabaseException;

    public void attached(Database var1);
}

