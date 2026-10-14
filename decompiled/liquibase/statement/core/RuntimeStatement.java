/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.database.Database;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.AbstractSqlStatement;

public class RuntimeStatement
extends AbstractSqlStatement {
    public Sql[] generate(Database database) {
        return SqlGenerator.EMPTY_SQL;
    }
}

