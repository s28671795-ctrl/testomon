/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.function.BiFunction;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.DropDefaultValueStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;

public class DropDefaultValueGenerator
extends AbstractSqlGenerator<DropDefaultValueStatement> {
    public static BiFunction DROP_DF_MSSQL = (tableName, columnName) -> "DECLARE @sql [nvarchar](MAX)\r\nSELECT @sql = N'ALTER TABLE " + tableName + " DROP CONSTRAINT ' + QUOTENAME([df].[name]) FROM [sys].[columns] AS [c] INNER JOIN [sys].[default_constraints] AS [df] ON [df].[object_id] = [c].[default_object_id] WHERE [c].[object_id] = OBJECT_ID(N'" + tableName + "') AND [c].[name] = N'" + columnName + "'\r\nEXEC sp_executesql @sql";

    @Override
    public boolean supports(DropDefaultValueStatement statement, Database database) {
        return !(database instanceof SQLiteDatabase);
    }

    @Override
    public ValidationErrors validate(DropDefaultValueStatement dropDefaultValueStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", dropDefaultValueStatement.getTableName());
        validationErrors.checkRequiredField("columnName", dropDefaultValueStatement.getColumnName());
        if (database instanceof InformixDatabase) {
            validationErrors.checkRequiredField("columnDataType", dropDefaultValueStatement.getColumnDataType());
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(DropDefaultValueStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String escapedTableName = database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName());
        String sql = database instanceof MSSQLDatabase ? (String)DROP_DF_MSSQL.apply(database.escapeStringForDatabase(escapedTableName), database.escapeStringForDatabase(statement.getColumnName())) : (database instanceof MySQLDatabase ? "ALTER TABLE " + escapedTableName + " ALTER " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " DROP DEFAULT" : (database instanceof OracleDatabase ? "ALTER TABLE " + escapedTableName + " MODIFY " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " DEFAULT NULL" : (database instanceof SybaseDatabase ? "ALTER TABLE " + escapedTableName + " REPLACE " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " DEFAULT NULL" : (database instanceof SybaseASADatabase ? "ALTER TABLE " + escapedTableName + " ALTER " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " DEFAULT NULL" : (database instanceof DerbyDatabase ? "ALTER TABLE " + escapedTableName + " ALTER COLUMN  " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " WITH DEFAULT NULL" : (database instanceof InformixDatabase ? "ALTER TABLE " + escapedTableName + " MODIFY (" + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " " + statement.getColumnDataType() + ")" : (database instanceof AbstractDb2Database ? "ALTER TABLE " + escapedTableName + " ALTER COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " DROP DEFAULT" : (database instanceof PostgresDatabase ? "ALTER TABLE " + escapedTableName + " ALTER COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " DROP DEFAULT" : "ALTER TABLE " + escapedTableName + " ALTER COLUMN  " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " SET DEFAULT NULL"))))))));
        return new Sql[]{new UnparsedSql(sql, this.getAffectedColumn(statement))};
    }

    protected Column getAffectedColumn(DropDefaultValueStatement statement) {
        return new Column().setName(statement.getColumnName()).setRelation(new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName()));
    }
}

