/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Date;
import liquibase.database.Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.LiquibaseException;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.InsertOrUpdateGenerator;
import liquibase.statement.core.InsertOrUpdateStatement;

public class InsertOrUpdateGeneratorHsql
extends InsertOrUpdateGenerator {
    @Override
    public boolean supports(InsertOrUpdateStatement statement, Database database) {
        return database instanceof HsqlDatabase;
    }

    @Override
    protected String getRecordCheck(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause) {
        return "MERGE INTO " + insertOrUpdateStatement.getTableName() + " USING (VALUES (1)) ON " + whereClause + " WHEN NOT MATCHED THEN ";
    }

    @Override
    protected String getInsertStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values2 = new StringBuilder();
        for (String columnKey : insertOrUpdateStatement.getColumnValues().keySet()) {
            columns.append(",");
            columns.append(columnKey);
            values2.append(",");
            values2.append(this.convertToString(insertOrUpdateStatement.getColumnValue(columnKey), database));
        }
        columns.deleteCharAt(0);
        values2.deleteCharAt(0);
        return "INSERT (" + columns + ") VALUES (" + values2 + ")";
    }

    @Override
    protected String getElse(Database database) {
        return " WHEN MATCHED THEN ";
    }

    @Override
    protected String getUpdateStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause, SqlGeneratorChain sqlGeneratorChain) throws LiquibaseException {
        if (insertOrUpdateStatement.getOnlyUpdate().booleanValue()) {
            return super.getUpdateStatement(insertOrUpdateStatement, database, whereClause, sqlGeneratorChain);
        }
        StringBuilder sql = new StringBuilder("UPDATE SET ");
        for (String columnKey : insertOrUpdateStatement.getColumnValues().keySet()) {
            if (!insertOrUpdateStatement.getAllowColumnUpdate(columnKey)) continue;
            sql.append(columnKey).append(" = ");
            sql.append(this.convertToString(insertOrUpdateStatement.getColumnValue(columnKey), database));
            sql.append(",");
        }
        int lastComma = sql.lastIndexOf(",");
        if (lastComma > -1) {
            sql.deleteCharAt(lastComma);
        }
        return sql.toString();
    }

    private String convertToString(Object newValue, Database database) {
        String sqlString = newValue == null || "".equals(newValue.toString()) || "NULL".equalsIgnoreCase(newValue.toString()) ? "NULL" : (newValue instanceof String && !this.looksLikeFunctionCall((String)newValue, database) ? "'" + database.escapeStringForDatabase(newValue.toString()) + "'" : (newValue instanceof Date ? database.getDateLiteral((Date)newValue) : (newValue instanceof Boolean ? (((Boolean)newValue).booleanValue() ? DataTypeFactory.getInstance().getTrueBooleanValue(database) : DataTypeFactory.getInstance().getFalseBooleanValue(database)) : newValue.toString())));
        return sqlString;
    }
}

