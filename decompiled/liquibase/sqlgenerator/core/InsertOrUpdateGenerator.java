/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Arrays;
import java.util.HashSet;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.sqlgenerator.core.InsertGenerator;
import liquibase.sqlgenerator.core.UpdateGenerator;
import liquibase.statement.core.InsertOrUpdateStatement;
import liquibase.statement.core.UpdateStatement;
import liquibase.structure.core.Table;

public abstract class InsertOrUpdateGenerator
extends AbstractSqlGenerator<InsertOrUpdateStatement> {
    protected abstract String getRecordCheck(InsertOrUpdateStatement var1, Database var2, String var3);

    protected abstract String getElse(Database var1);

    protected String getPostUpdateStatements(Database database) {
        return "";
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public ValidationErrors validate(InsertOrUpdateStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", statement.getTableName());
        validationErrors.checkRequiredField("columns", statement.getColumnValues());
        validationErrors.checkRequiredField("primaryKey", statement.getPrimaryKey());
        return validationErrors;
    }

    protected String getWhereClause(InsertOrUpdateStatement insertOrUpdateStatement, Database database) {
        String[] pkColumns;
        StringBuilder where = new StringBuilder();
        for (String thisPkColumn : pkColumns = insertOrUpdateStatement.getPrimaryKey().split(",")) {
            Object newValue = insertOrUpdateStatement.getColumnValues().get(thisPkColumn);
            where.append(database.escapeColumnName(insertOrUpdateStatement.getCatalogName(), insertOrUpdateStatement.getSchemaName(), insertOrUpdateStatement.getTableName(), thisPkColumn)).append(newValue == null || "NULL".equalsIgnoreCase(newValue.toString()) ? " is " : " = ");
            if (newValue == null || "NULL".equalsIgnoreCase(newValue.toString())) {
                where.append("NULL");
            } else {
                where.append(DataTypeFactory.getInstance().fromObject(newValue, database).objectToSql(newValue, database));
            }
            where.append(" AND ");
        }
        where.delete(where.lastIndexOf(" AND "), where.lastIndexOf(" AND ") + " AND ".length());
        return where.toString();
    }

    protected String getInsertStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Sql[] insertSql;
        StringBuilder insertBuffer = new StringBuilder();
        InsertGenerator insert = new InsertGenerator();
        for (Sql s2 : insertSql = insert.generateSql(insertOrUpdateStatement, database, sqlGeneratorChain)) {
            insertBuffer.append(s2.toSql());
            insertBuffer.append(";");
        }
        insertBuffer.append("\n");
        return insertBuffer.toString();
    }

    protected String getUpdateStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause, SqlGeneratorChain sqlGeneratorChain) throws LiquibaseException {
        Sql[] updateSql;
        StringBuilder updateSqlString = new StringBuilder();
        UpdateGenerator update = new UpdateGenerator();
        UpdateStatement updateStatement = new UpdateStatement(insertOrUpdateStatement.getCatalogName(), insertOrUpdateStatement.getSchemaName(), insertOrUpdateStatement.getTableName());
        if (!(database instanceof OracleDatabase) || insertOrUpdateStatement.getOnlyUpdate() == null || !insertOrUpdateStatement.getOnlyUpdate().booleanValue()) {
            whereClause = whereClause + ";\n";
        }
        updateStatement.setWhereClause(whereClause);
        String[] pkFields = insertOrUpdateStatement.getPrimaryKey().split(",");
        HashSet<String> hashPkFields = new HashSet<String>(Arrays.asList(pkFields));
        for (String columnKey : insertOrUpdateStatement.getColumnValues().keySet()) {
            if (hashPkFields.contains(columnKey) || !insertOrUpdateStatement.getAllowColumnUpdate(columnKey)) continue;
            updateStatement.addNewColumnValue(columnKey, insertOrUpdateStatement.getColumnValue(columnKey));
        }
        if (updateStatement.getNewColumnValues().isEmpty()) {
            throw new LiquibaseException("No fields to update in set clause");
        }
        for (Sql s2 : updateSql = update.generateSql(updateStatement, database, sqlGeneratorChain)) {
            updateSqlString.append(s2.toSql());
            updateSqlString.append(";");
        }
        updateSqlString.deleteCharAt(updateSqlString.lastIndexOf(";"));
        updateSqlString.append("\n");
        return updateSqlString.toString();
    }

    @Override
    public Sql[] generateSql(InsertOrUpdateStatement insertOrUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder completeSql = new StringBuilder();
        String whereClause = this.getWhereClause(insertOrUpdateStatement, database);
        if (!insertOrUpdateStatement.getOnlyUpdate().booleanValue()) {
            completeSql.append(this.getRecordCheck(insertOrUpdateStatement, database, whereClause));
            completeSql.append(this.getInsertStatement(insertOrUpdateStatement, database, sqlGeneratorChain));
        }
        try {
            String updateStatement = this.getUpdateStatement(insertOrUpdateStatement, database, whereClause, sqlGeneratorChain);
            if (!insertOrUpdateStatement.getOnlyUpdate().booleanValue()) {
                completeSql.append(this.getElse(database));
            }
            completeSql.append(updateStatement);
        }
        catch (LiquibaseException liquibaseException) {
            // empty catch block
        }
        if (!insertOrUpdateStatement.getOnlyUpdate().booleanValue()) {
            completeSql.append(this.getPostUpdateStatements(database));
        }
        return new Sql[]{new UnparsedSql(completeSql.toString(), "", this.getAffectedTable(insertOrUpdateStatement))};
    }

    protected Table getAffectedTable(InsertOrUpdateStatement insertOrUpdateStatement) {
        return (Table)new Table().setName(insertOrUpdateStatement.getTableName()).setSchema(insertOrUpdateStatement.getCatalogName(), insertOrUpdateStatement.getSchemaName());
    }
}

