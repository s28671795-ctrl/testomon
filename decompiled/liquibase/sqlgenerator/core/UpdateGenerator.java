/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Date;
import liquibase.database.Database;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.core.UpdateStatement;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Table;
import liquibase.util.SqlUtil;

public class UpdateGenerator
extends AbstractSqlGenerator<UpdateStatement> {
    @Override
    public ValidationErrors validate(UpdateStatement updateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", updateStatement.getTableName());
        validationErrors.checkRequiredField("columns", updateStatement.getNewColumnValues());
        if (updateStatement.getWhereParameters() != null && !updateStatement.getWhereParameters().isEmpty() && updateStatement.getWhereClause() == null) {
            validationErrors.addError("whereParams set but no whereClause");
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(UpdateStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder("UPDATE ").append(database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName())).append(" SET");
        for (String column : statement.getNewColumnValues().keySet()) {
            sql.append(" ").append(database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), column)).append(" = ").append(this.convertToString(statement.getNewColumnValues().get(column), database)).append(",");
        }
        int lastComma = sql.lastIndexOf(",");
        if (lastComma >= 0) {
            sql.deleteCharAt(lastComma);
        }
        if (statement.getWhereClause() != null) {
            sql.append(" WHERE ").append(SqlUtil.replacePredicatePlaceholders(database, statement.getWhereClause(), statement.getWhereColumnNames(), statement.getWhereParameters()));
        }
        return new Sql[]{new UnparsedSql(sql.toString(), this.getAffectedTable(statement))};
    }

    protected Relation getAffectedTable(UpdateStatement statement) {
        return new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName());
    }

    private String convertToString(Object newValue, Database database) {
        String sqlString;
        if (newValue == null || "NULL".equalsIgnoreCase(newValue.toString())) {
            sqlString = "NULL";
        } else if (newValue instanceof String && !this.looksLikeFunctionCall((String)newValue, database)) {
            sqlString = DataTypeFactory.getInstance().fromObject(newValue, database).objectToSql(newValue, database);
        } else if (newValue instanceof Date) {
            Date date = (Date)newValue;
            if (date.getClass().equals(Date.class)) {
                date = new java.sql.Date(date.getTime());
            }
            sqlString = database.getDateLiteral(date);
        } else {
            sqlString = newValue instanceof Boolean ? (((Boolean)newValue).booleanValue() ? DataTypeFactory.getInstance().getTrueBooleanValue(database) : DataTypeFactory.getInstance().getFalseBooleanValue(database)) : (newValue instanceof DatabaseFunction ? database.generateDatabaseFunctionValue((DatabaseFunction)newValue) : newValue.toString());
        }
        return sqlString;
    }
}

