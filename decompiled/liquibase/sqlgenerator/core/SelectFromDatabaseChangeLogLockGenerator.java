/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.SelectFromDatabaseChangeLogLockStatement;
import liquibase.structure.DatabaseObject;
import liquibase.util.StringUtil;

public class SelectFromDatabaseChangeLogLockGenerator
extends AbstractSqlGenerator<SelectFromDatabaseChangeLogLockStatement> {
    @Override
    public ValidationErrors validate(SelectFromDatabaseChangeLogLockStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors errors2 = new ValidationErrors();
        errors2.checkRequiredField("columnToSelect", statement.getColumnsToSelect());
        return errors2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sql[] generateSql(SelectFromDatabaseChangeLogLockStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String liquibaseSchema = database.getLiquibaseSchemaName();
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            String sql = "SELECT " + StringUtil.join(statement.getColumnsToSelect(), ",", col -> {
                if (col.getComputed() != null && col.getComputed().booleanValue()) {
                    return col.getName();
                }
                return database.escapeColumnName(null, null, null, col.getName());
            }) + " FROM " + database.escapeTableName(database.getLiquibaseCatalogName(), liquibaseSchema, database.getDatabaseChangeLogLockTableName()) + " WHERE " + database.escapeColumnName(database.getLiquibaseCatalogName(), liquibaseSchema, database.getDatabaseChangeLogLockTableName(), "ID") + "=1";
            if (database instanceof OracleDatabase) {
                sql = sql + " FOR UPDATE";
            }
            Sql[] sqlArray = new Sql[]{new UnparsedSql(sql, new DatabaseObject[0])};
            return sqlArray;
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }
}

