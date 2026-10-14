/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.jvm;

import java.sql.Connection;
import java.sql.Savepoint;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.LiquibaseService;

@LiquibaseService(skip=true)
public class SybaseConnection
extends JdbcConnection {
    public SybaseConnection() {
    }

    public SybaseConnection(Connection delegate) {
        super(delegate);
    }

    @Override
    public void commit() throws DatabaseException {
    }

    @Override
    public void rollback() throws DatabaseException {
    }

    @Override
    public void rollback(Savepoint savepoint) throws DatabaseException {
    }
}

