/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.custom;

import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;
import liquibase.statement.SqlStatement;

public interface CustomSqlRollback {
    public SqlStatement[] generateRollbackStatements(Database var1) throws CustomChangeException, RollbackImpossibleException;
}

