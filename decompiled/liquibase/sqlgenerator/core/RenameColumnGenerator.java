/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.RenameColumnStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class RenameColumnGenerator
extends AbstractSqlGenerator<RenameColumnStatement> {
    @Override
    public boolean supports(RenameColumnStatement statement, Database database) {
        if (database instanceof SQLiteDatabase) {
            try {
                if (database.getDatabaseMajorVersion() <= 3 && database.getDatabaseMinorVersion() < 25) {
                    return false;
                }
            }
            catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public ValidationErrors validate(RenameColumnStatement renameColumnStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", renameColumnStatement.getTableName());
        validationErrors.checkRequiredField("oldColumnName", renameColumnStatement.getOldColumnName());
        validationErrors.checkRequiredField("newColumnName", renameColumnStatement.getNewColumnName());
        if (database instanceof MySQLDatabase) {
            validationErrors.checkRequiredField("columnDataType", StringUtil.trimToNull(renameColumnStatement.getColumnDataType()));
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(RenameColumnStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql = database instanceof MSSQLDatabase ? "exec sp_rename '" + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + "." + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName()) + "', '" + statement.getNewColumnName() + "', 'COLUMN'" : (database instanceof MySQLDatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " CHANGE " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName()) + " " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getNewColumnName()) + " " + DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType(), database).toDatabaseDataType(database) : (database instanceof SybaseDatabase ? "exec sp_rename '" + statement.getTableName() + "." + statement.getOldColumnName() + "', '" + statement.getNewColumnName() + "'" : (database instanceof HsqlDatabase || database instanceof H2Database ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ALTER COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName()) + " RENAME TO " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getNewColumnName()) : (database instanceof FirebirdDatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ALTER COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName()) + " TO " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getNewColumnName()) : (database instanceof DerbyDatabase || database instanceof InformixDatabase ? "RENAME COLUMN " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + "." + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName()) + " TO " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getNewColumnName()) : (database instanceof SybaseASADatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " RENAME " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName()) + " TO " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getNewColumnName()) : (database instanceof AbstractDb2Database ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " RENAME COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName()) + " TO " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getNewColumnName()) : "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " RENAME COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName()) + " TO " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getNewColumnName()))))))));
        if (database instanceof MySQLDatabase && statement.getRemarks() != null) {
            sql = sql + " COMMENT '" + statement.getRemarks() + "' ";
        }
        return new Sql[]{new UnparsedSql(sql, this.getAffectedOldColumn(statement), this.getAffectedNewColumn(statement))};
    }

    protected Column getAffectedOldColumn(RenameColumnStatement statement) {
        return new Column().setName(statement.getOldColumnName()).setRelation(new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName()));
    }

    protected Column getAffectedNewColumn(RenameColumnStatement statement) {
        return new Column().setName(statement.getNewColumnName()).setRelation(new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName()));
    }
}

