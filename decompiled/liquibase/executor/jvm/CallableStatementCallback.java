/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.CallableStatement;
import java.sql.SQLException;
import liquibase.exception.DatabaseException;
import liquibase.statement.SqlStatement;

interface CallableStatementCallback {
    public Object doInCallableStatement(CallableStatement var1) throws SQLException, DatabaseException;

    public SqlStatement getStatement();
}

