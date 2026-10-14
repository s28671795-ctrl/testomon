/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.InsertExecutablePreparedStatement;

public class InsertDataChangeGenerator
extends AbstractSqlGenerator<InsertExecutablePreparedStatement> {
    @Override
    public ValidationErrors validate(InsertExecutablePreparedStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }

    @Override
    public Sql[] generateSql(InsertExecutablePreparedStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return EMPTY_SQL;
    }
}

