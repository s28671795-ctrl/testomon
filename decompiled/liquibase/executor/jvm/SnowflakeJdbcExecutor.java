/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.util.List;
import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.exception.DatabaseException;
import liquibase.executor.jvm.JdbcExecutor;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.SetColumnRemarksStatement;

public class SnowflakeJdbcExecutor
extends JdbcExecutor {
    @Override
    public boolean supports(Database database) {
        return database instanceof SnowflakeDatabase;
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public void execute(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        try {
            super.execute(sql, sqlVisitors);
        }
        catch (DatabaseException e) {
            if (sql instanceof SetColumnRemarksStatement && e.getMessage().contains("Object found is of type 'VIEW', not specified type 'TABLE'")) {
                throw new DatabaseException("setColumnRemarks change type isn't supported on Snowflake for a 'view'", e);
            }
            throw e;
        }
    }
}

