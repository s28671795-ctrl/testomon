/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Arrays;
import java.util.HashSet;
import liquibase.database.Database;
import liquibase.database.core.MySQLDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.LiquibaseException;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.InsertOrUpdateGenerator;
import liquibase.statement.core.InsertOrUpdateStatement;
import liquibase.structure.core.Column;

public class InsertOrUpdateGeneratorMySQL
extends InsertOrUpdateGenerator {
    @Override
    public boolean supports(InsertOrUpdateStatement statement, Database database) {
        return database instanceof MySQLDatabase;
    }

    @Override
    protected String getInsertStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder(super.getInsertStatement(insertOrUpdateStatement, database, sqlGeneratorChain));
        sql.deleteCharAt(sql.lastIndexOf(";"));
        StringBuilder updateClause = new StringBuilder("ON DUPLICATE KEY UPDATE ");
        String[] pkFields = insertOrUpdateStatement.getPrimaryKey().split(",");
        HashSet<String> hashPkFields = new HashSet<String>(Arrays.asList(pkFields));
        boolean hasFields = false;
        for (String columnKey : insertOrUpdateStatement.getColumnValues().keySet()) {
            if (hashPkFields.contains(columnKey) || !insertOrUpdateStatement.getAllowColumnUpdate(columnKey)) continue;
            hasFields = true;
            updateClause.append(database.escapeObjectName(columnKey, Column.class)).append(" = ");
            Object columnValue = insertOrUpdateStatement.getColumnValue(columnKey);
            updateClause.append(DataTypeFactory.getInstance().fromObject(columnValue, database).objectToSql(columnValue, database));
            updateClause.append(",");
        }
        if (hasFields) {
            updateClause.deleteCharAt(updateClause.lastIndexOf(","));
            sql.append((CharSequence)updateClause);
        } else {
            sql.insert(sql.indexOf("INSERT ") + "INSERT ".length(), "IGNORE ");
        }
        return sql.toString();
    }

    @Override
    protected String getUpdateStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause, SqlGeneratorChain sqlGeneratorChain) throws LiquibaseException {
        if (insertOrUpdateStatement.getOnlyUpdate().booleanValue()) {
            return super.getUpdateStatement(insertOrUpdateStatement, database, whereClause, sqlGeneratorChain);
        }
        return "";
    }

    @Override
    protected String getRecordCheck(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause) {
        return "";
    }

    @Override
    protected String getElse(Database database) {
        return "";
    }
}

