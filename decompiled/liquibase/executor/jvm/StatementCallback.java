/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.SQLException;
import java.sql.Statement;
import liquibase.exception.DatabaseException;
import liquibase.statement.SqlStatement;

interface StatementCallback {
    public Object doInStatement(Statement var1) throws SQLException, DatabaseException;

    public SqlStatement getStatement();
}

