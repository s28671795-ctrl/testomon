/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.BatchDmlExecutablePreparedStatement;

public class BatchDmlExecutablePreparedStatementGenerator
extends AbstractSqlGenerator<BatchDmlExecutablePreparedStatement> {
    @Override
    public ValidationErrors validate(BatchDmlExecutablePreparedStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }

    @Override
    public Sql[] generateSql(BatchDmlExecutablePreparedStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return EMPTY_SQL;
    }
}

