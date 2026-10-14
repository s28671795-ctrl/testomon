/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.InsertOrUpdateGenerator;
import liquibase.statement.core.InsertOrUpdateStatement;

public class InsertOrUpdateGeneratorInformix
extends InsertOrUpdateGenerator {
    private static final String SOURCE_ALIAS = "src";
    private static final String DEST_ALIAS = "dst";

    @Override
    public boolean supports(InsertOrUpdateStatement statement, Database database) {
        return database instanceof InformixDatabase;
    }

    @Override
    protected String getRecordCheck(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause) {
        StringBuilder sql = new StringBuilder();
        String[] pkFields = insertOrUpdateStatement.getPrimaryKey().split(",");
        String tableReference = database.escapeTableName(insertOrUpdateStatement.getCatalogName(), insertOrUpdateStatement.getSchemaName(), insertOrUpdateStatement.getTableName());
        sql.append("MERGE INTO ").append(tableReference).append(" AS ").append(DEST_ALIAS).append("\n");
        sql.append("USING (\n");
        sql.append(this.getSelect(insertOrUpdateStatement, database));
        sql.append(") AS ").append(SOURCE_ALIAS).append("\n");
        sql.append("ON ");
        for (int i = 0; i < pkFields.length; ++i) {
            sql.append(DEST_ALIAS).append(".").append(pkFields[i]).append(" = ").append(SOURCE_ALIAS).append(".").append(pkFields[i]);
            if (i >= pkFields.length - 1) continue;
            sql.append(" AND ");
        }
        sql.append("\nWHEN NOT MATCHED THEN\n");
        return sql.toString();
    }

    @Override
    protected String getInsertStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values2 = new StringBuilder();
        for (String columnKey : insertOrUpdateStatement.getColumnValues().keySet()) {
            columns.append(", ");
            columns.append(DEST_ALIAS).append(".").append(columnKey);
            values2.append(SOURCE_ALIAS).append(".").append(columnKey).append(", ");
        }
        columns.delete(0, 2);
        int lastComma = values2.lastIndexOf(",");
        if (lastComma > -1) {
            values2.delete(lastComma, lastComma + 2);
        }
        return "INSERT (" + columns.toString() + ") VALUES (" + values2.toString() + ")\n";
    }

    @Override
    protected String getElse(Database database) {
        return "";
    }

    @Override
    protected String getUpdateStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause, SqlGeneratorChain sqlGeneratorChain) {
        Map<String, Object> columnValues = insertOrUpdateStatement.getColumnValues();
        String[] pkFields = insertOrUpdateStatement.getPrimaryKey().split(",");
        if (pkFields.length != columnValues.size()) {
            StringBuilder sql = new StringBuilder("WHEN MATCHED THEN\n");
            sql.append("UPDATE SET ");
            HashSet<String> hashPkFields = new HashSet<String>(Arrays.asList(pkFields));
            for (String columnKey : columnValues.keySet()) {
                if (hashPkFields.contains(columnKey) || !insertOrUpdateStatement.getAllowColumnUpdate(columnKey)) continue;
                sql.append(DEST_ALIAS).append(".").append(columnKey).append(" = ");
                sql.append(SOURCE_ALIAS).append(".").append(columnKey);
                sql.append(", ");
            }
            int lastComma = sql.lastIndexOf(",");
            if (lastComma > -1) {
                sql.delete(lastComma, lastComma + 2);
            }
            return sql.toString();
        }
        return "";
    }

    private String convertToString(Object newValue, Database database) {
        String sqlString = newValue == null || newValue.toString().equals("") || newValue.toString().equalsIgnoreCase("NULL") ? "NULL::INTEGER" : (newValue instanceof String && !this.looksLikeFunctionCall((String)newValue, database) ? "'" + database.escapeStringForDatabase(newValue.toString()) + "'" : (newValue instanceof Date ? database.getDateLiteral((Date)newValue) : (newValue instanceof Boolean ? (((Boolean)newValue).booleanValue() ? DataTypeFactory.getInstance().getTrueBooleanValue(database) : DataTypeFactory.getInstance().getFalseBooleanValue(database)) : newValue.toString())));
        return sqlString;
    }

    private String getSelect(InsertOrUpdateStatement insertOrUpdateStatement, Database database) {
        StringBuilder select = new StringBuilder();
        select.append("\tSELECT ");
        for (String columnKey : insertOrUpdateStatement.getColumnValues().keySet()) {
            select.append(this.convertToString(insertOrUpdateStatement.getColumnValue(columnKey), database));
            select.append(" AS ");
            select.append(columnKey);
            select.append(", ");
        }
        int lastComma = select.lastIndexOf(", ");
        if (lastComma > -1) {
            select.delete(lastComma, lastComma + 2);
        }
        select.append("\n\tFROM sysmaster:informix.sysdual\n");
        return select.toString();
    }
}

