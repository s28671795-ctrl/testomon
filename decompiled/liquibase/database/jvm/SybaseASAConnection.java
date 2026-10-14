/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.jvm;

import java.sql.Connection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.LiquibaseService;

@LiquibaseService(skip=true)
public class SybaseASAConnection
extends JdbcConnection {
    public SybaseASAConnection() {
    }

    public SybaseASAConnection(Connection connection) {
        super(connection);
    }

    @Override
    public String nativeSQL(String sql) throws DatabaseException {
        return sql;
    }
}

