/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import liquibase.database.PreparedStatementFactory;
import liquibase.exception.DatabaseException;
import liquibase.statement.SqlStatement;

public interface ExecutablePreparedStatement
extends SqlStatement {
    public void execute(PreparedStatementFactory var1) throws DatabaseException;
}

