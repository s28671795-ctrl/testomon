/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.DatabaseDataType;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.ModifyDataTypeStatement;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Table;

public class ModifyDataTypeGenerator
extends AbstractSqlGenerator<ModifyDataTypeStatement> {
    @Override
    public boolean supports(ModifyDataTypeStatement statement, Database database) {
        if (database instanceof SQLiteDatabase || database instanceof Db2zDatabase) {
            return false;
        }
        return super.supports(statement, database);
    }

    @Override
    public Warnings warn(ModifyDataTypeStatement modifyDataTypeStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Warnings warnings = super.warn(modifyDataTypeStatement, database, sqlGeneratorChain);
        if (database instanceof MySQLDatabase) {
            ((MySQLDatabase)database).warnAboutAlterColumn("modifyDataType", warnings);
        }
        return warnings;
    }

    @Override
    public ValidationErrors validate(ModifyDataTypeStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", statement.getTableName());
        validationErrors.checkRequiredField("columnName", statement.getColumnName());
        validationErrors.checkRequiredField("newDataType", statement.getNewDataType());
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(ModifyDataTypeStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String alterTable = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName());
        alterTable = alterTable + " " + this.getModifyString(database) + " ";
        String columnName = database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName());
        alterTable = alterTable + columnName;
        alterTable = alterTable + this.getPreDataTypeString(database);
        DatabaseDataType newDataType = DataTypeFactory.getInstance().fromDescription(statement.getNewDataType(), database).toDatabaseDataType(database);
        alterTable = alterTable + newDataType;
        if (database instanceof PostgresDatabase) {
            alterTable = alterTable + " USING (" + columnName + "::" + newDataType + ")";
        }
        return new Sql[]{new UnparsedSql(alterTable, this.getAffectedTable(statement))};
    }

    protected Relation getAffectedTable(ModifyDataTypeStatement statement) {
        return new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName());
    }

    protected String getModifyString(Database database) {
        if (database instanceof SybaseASADatabase || database instanceof SybaseDatabase || database instanceof MySQLDatabase || database instanceof OracleDatabase || database instanceof InformixDatabase) {
            return "MODIFY";
        }
        return "ALTER COLUMN";
    }

    protected String getPreDataTypeString(Database database) {
        if (database instanceof DerbyDatabase || database instanceof AbstractDb2Database) {
            return " SET DATA TYPE ";
        }
        if (database instanceof SybaseASADatabase || database instanceof SybaseDatabase || database instanceof MSSQLDatabase || database instanceof MySQLDatabase || database instanceof HsqlDatabase || database instanceof H2Database || database instanceof OracleDatabase || database instanceof InformixDatabase) {
            return " ";
        }
        return " TYPE ";
    }
}

