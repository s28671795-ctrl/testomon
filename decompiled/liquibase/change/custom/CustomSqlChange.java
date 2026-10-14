/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.custom;

import liquibase.change.custom.CustomChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.statement.SqlStatement;

public interface CustomSqlChange
extends CustomChange {
    public SqlStatement[] generateStatements(Database var1) throws CustomChangeException;
}

