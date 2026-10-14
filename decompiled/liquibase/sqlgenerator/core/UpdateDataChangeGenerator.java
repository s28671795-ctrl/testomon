/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.UpdateExecutablePreparedStatement;

public class UpdateDataChangeGenerator
extends AbstractSqlGenerator<UpdateExecutablePreparedStatement> {
    @Override
    public ValidationErrors validate(UpdateExecutablePreparedStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        if (statement.getWhereParameters() != null && !statement.getWhereParameters().isEmpty() && statement.getWhereClause() == null) {
            validationErrors.addError("whereParams set but no whereClause");
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(UpdateExecutablePreparedStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return EMPTY_SQL;
    }
}

