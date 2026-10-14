/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.ArrayList;
import java.util.Arrays;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.ReorganizeTableStatement;
import liquibase.statement.core.SetNullableStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;

public class SetNullableGenerator
extends AbstractSqlGenerator<SetNullableStatement> {
    @Override
    public boolean supports(SetNullableStatement statement, Database database) {
        try {
            if (database instanceof Db2zDatabase || database instanceof DB2Database && database.getDatabaseMajorVersion() > 0 && database.getDatabaseMajorVersion() < 9) {
                return false;
            }
        }
        catch (DatabaseException databaseException) {
            // empty catch block
        }
        return !(database instanceof SQLiteDatabase);
    }

    @Override
    public ValidationErrors validate(SetNullableStatement setNullableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", setNullableStatement.getTableName());
        if (setNullableStatement.isNullable()) {
            if (database instanceof OracleDatabase) {
                if (setNullableStatement.getConstraintName() == null && setNullableStatement.getColumnName() == null) {
                    validationErrors.addError("Oracle requires either constraintName or columnName to be set");
                }
            } else {
                validationErrors.checkRequiredField("columnName", setNullableStatement.getColumnName());
            }
        } else {
            validationErrors.checkRequiredField("columnName", setNullableStatement.getColumnName());
        }
        if (database instanceof MSSQLDatabase || database instanceof MySQLDatabase || database instanceof InformixDatabase) {
            validationErrors.checkRequiredField("columnDataType", setNullableStatement.getColumnDataType());
        }
        return validationErrors;
    }

    @Override
    public Warnings warn(SetNullableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Warnings warnings = super.warn(statement, database, sqlGeneratorChain);
        if (database instanceof MySQLDatabase) {
            ((MySQLDatabase)database).warnAboutAlterColumn("setNullable", warnings);
        }
        return warnings;
    }

    @Override
    public Sql[] generateSql(SetNullableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Sql[] a;
        String sql;
        block23: {
            String nullableString;
            String string = nullableString = statement.isNullable() ? " NULL" : " NOT NULL";
            if (database instanceof OracleDatabase && statement.getConstraintName() != null) {
                if (!statement.isNullable()) {
                    nullableString = nullableString + (!statement.isValidate() ? " ENABLE NOVALIDATE " : "");
                    sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " MODIFY " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " CONSTRAINT " + statement.getConstraintName() + nullableString;
                } else {
                    sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP CONSTRAINT " + statement.getConstraintName();
                }
            } else if (database instanceof OracleDatabase || database instanceof SybaseDatabase || database instanceof SybaseASADatabase) {
                nullableString = nullableString + (database instanceof OracleDatabase && !statement.isValidate() ? " ENABLE NOVALIDATE " : "");
                sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " MODIFY " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + nullableString;
            } else if (database instanceof MSSQLDatabase) {
                sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ALTER COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " " + DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType(), database).toDatabaseDataType(database) + nullableString;
            } else if (database instanceof MySQLDatabase) {
                sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " MODIFY " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " " + DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType(), database).toDatabaseDataType(database) + nullableString;
            } else if (database instanceof DerbyDatabase) {
                sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ALTER COLUMN  " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + nullableString;
            } else if (database instanceof HsqlDatabase || database instanceof H2Database) {
                sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ALTER COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " SET" + nullableString;
            } else if (database instanceof InformixDatabase) {
                if (statement.isNullable()) {
                    nullableString = "";
                }
                sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " MODIFY (" + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " " + DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType(), database).toDatabaseDataType(database) + nullableString + ")";
            } else {
                String standardSqlStatement = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ALTER COLUMN  " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + (statement.isNullable() ? " DROP NOT NULL" : " SET NOT NULL");
                if (database instanceof FirebirdDatabase) {
                    try {
                        if (database.getDatabaseMajorVersion() <= 2) {
                            sql = "UPDATE RDB$RELATION_FIELDS SET RDB$NULL_FLAG = " + (statement.isNullable() ? "NULL" : "1") + " WHERE RDB$RELATION_NAME = '" + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + "' AND RDB$FIELD_NAME = '" + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + "'";
                            break block23;
                        }
                        sql = standardSqlStatement;
                    }
                    catch (DatabaseException ignored) {
                        sql = standardSqlStatement;
                    }
                } else {
                    sql = standardSqlStatement;
                }
            }
        }
        ArrayList<Sql> returnList = new ArrayList<Sql>();
        returnList.add(new UnparsedSql(sql, this.getAffectedColumn(statement)));
        if (database instanceof DB2Database && (a = SqlGeneratorFactory.getInstance().generateSql(new ReorganizeTableStatement(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()), database)) != null) {
            returnList.addAll(Arrays.asList(a));
        }
        return returnList.toArray(EMPTY_SQL);
    }

    protected Column getAffectedColumn(SetNullableStatement statement) {
        return new Column().setName(statement.getColumnName()).setRelation(new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName()));
    }
}

