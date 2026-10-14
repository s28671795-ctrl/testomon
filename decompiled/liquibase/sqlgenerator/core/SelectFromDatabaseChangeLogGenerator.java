/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import liquibase.change.ColumnConfig;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.SelectFromDatabaseChangeLogStatement;
import liquibase.structure.DatabaseObject;
import liquibase.util.StringUtil;

public class SelectFromDatabaseChangeLogGenerator
extends AbstractSqlGenerator<SelectFromDatabaseChangeLogStatement> {
    @Override
    public ValidationErrors validate(SelectFromDatabaseChangeLogStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors errors2 = new ValidationErrors();
        errors2.checkRequiredField("columnToSelect", statement.getColumnsToSelect());
        return errors2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sql[] generateSql(SelectFromDatabaseChangeLogStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        List<ColumnConfig> columnsToSelect = Arrays.asList(statement.getColumnsToSelect());
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            String sql = "SELECT " + (database instanceof MSSQLDatabase && statement.getLimit() != null ? "TOP " + statement.getLimit() + " " : "") + StringUtil.join(columnsToSelect, ",", column -> {
                if (column.getComputed() != null && column.getComputed().booleanValue()) {
                    return column.getName();
                }
                return database.escapeColumnName(null, null, null, column.getName());
            }).toUpperCase() + " FROM " + database.escapeTableName(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogTableName());
            SelectFromDatabaseChangeLogStatement.WhereClause whereClause = statement.getWhereClause();
            if (whereClause != null) {
                sql = sql + whereClause.generateSql(database);
            }
            if (statement.getOrderByColumns() != null && statement.getOrderByColumns().length > 0) {
                sql = sql + " ORDER BY ";
                Iterator<String> orderBy = Arrays.asList(statement.getOrderByColumns()).iterator();
                while (orderBy.hasNext()) {
                    String orderColumn = orderBy.next();
                    String[] orderColumnData = orderColumn.split(" ");
                    sql = sql + database.escapeColumnName(null, null, null, orderColumnData[0]);
                    if (orderColumnData.length == 2) {
                        sql = sql + " ";
                        sql = sql + orderColumnData[1].toUpperCase();
                    }
                    if (!orderBy.hasNext()) continue;
                    sql = sql + ", ";
                }
            }
            if (statement.getLimit() != null) {
                if (database instanceof OracleDatabase) {
                    sql = whereClause == null ? sql + " WHERE ROWNUM=" + statement.getLimit() : sql + " AND ROWNUM=" + statement.getLimit();
                } else if (database instanceof MySQLDatabase || database instanceof PostgresDatabase) {
                    sql = sql + " LIMIT " + statement.getLimit();
                } else if (database instanceof AbstractDb2Database) {
                    sql = sql + " FETCH FIRST " + statement.getLimit() + " ROWS ONLY";
                }
            }
            Sql[] sqlArray = new Sql[]{new UnparsedSql(sql, new DatabaseObject[0])};
            return sqlArray;
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }
}

