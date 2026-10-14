/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.DeleteStatement;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Table;
import liquibase.util.SqlUtil;

public class DeleteGenerator
extends AbstractSqlGenerator<DeleteStatement> {
    @Override
    public ValidationErrors validate(DeleteStatement deleteStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", deleteStatement.getTableName());
        if (deleteStatement.getWhereParameters() != null && !deleteStatement.getWhereParameters().isEmpty() && deleteStatement.getWhere() == null) {
            validationErrors.addError("whereParams set but no whereClause");
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(DeleteStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder("DELETE FROM ").append(database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()));
        if (statement.getWhere() != null) {
            sql.append(" WHERE ").append(SqlUtil.replacePredicatePlaceholders(database, statement.getWhere(), statement.getWhereColumnNames(), statement.getWhereParameters()));
        }
        return new Sql[]{new UnparsedSql(sql.toString(), this.getAffectedTable(statement))};
    }

    protected Relation getAffectedTable(DeleteStatement statement) {
        return new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName());
    }
}

