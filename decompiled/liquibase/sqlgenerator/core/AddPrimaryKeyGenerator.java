/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MockDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.AddPrimaryKeyStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class AddPrimaryKeyGenerator
extends AbstractSqlGenerator<AddPrimaryKeyStatement> {
    @Override
    public boolean supports(AddPrimaryKeyStatement statement, Database database) {
        return !(database instanceof SQLiteDatabase);
    }

    @Override
    public ValidationErrors validate(AddPrimaryKeyStatement addPrimaryKeyStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("columnNames", addPrimaryKeyStatement.getColumnNames());
        validationErrors.checkRequiredField("tableName", addPrimaryKeyStatement.getTableName());
        if (addPrimaryKeyStatement.isClustered() != null) {
            if (database instanceof PostgresDatabase) {
                if (addPrimaryKeyStatement.isClustered().booleanValue() && addPrimaryKeyStatement.getConstraintName() == null) {
                    validationErrors.addError("Postgresql requires constraintName on addPrimaryKey when clustered=true");
                }
            } else if (!(database instanceof MSSQLDatabase) && !(database instanceof MockDatabase) && addPrimaryKeyStatement.isClustered().booleanValue()) {
                validationErrors.addError("Cannot specify clustered=true on " + database.getShortName());
            }
        }
        if (!(database instanceof OracleDatabase || database instanceof AbstractDb2Database || database instanceof PostgresDatabase)) {
            validationErrors.checkDisallowedField("forIndexName", addPrimaryKeyStatement.getForIndexName(), database, new Class[0]);
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(AddPrimaryKeyStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql;
        if (statement.getConstraintName() == null || database instanceof MySQLDatabase || database instanceof SybaseASADatabase) {
            sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ADD PRIMARY KEY (" + database.escapeColumnNameList(statement.getColumnNames()) + ")";
        } else {
            sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ADD CONSTRAINT " + database.escapeConstraintName(statement.getConstraintName()) + " PRIMARY KEY";
            if (database instanceof MSSQLDatabase && statement.isClustered() != null) {
                sql = statement.isClustered() != false ? sql + " CLUSTERED" : sql + " NONCLUSTERED";
            }
            sql = sql + " (" + database.escapeColumnNameList(statement.getColumnNames()) + ")";
        }
        if (StringUtil.trimToNull(statement.getTablespace()) != null && database.supportsTablespaces()) {
            if (database instanceof MSSQLDatabase) {
                sql = sql + " ON " + statement.getTablespace();
            } else if (!(database instanceof AbstractDb2Database) && !(database instanceof SybaseASADatabase)) {
                sql = sql + " USING INDEX TABLESPACE " + statement.getTablespace();
            }
        }
        if (database instanceof OracleDatabase && statement.getForIndexName() != null) {
            sql = sql + " USING INDEX " + database.escapeObjectName(statement.getForIndexCatalogName(), statement.getForIndexSchemaName(), statement.getForIndexName(), Index.class);
        }
        if (database instanceof OracleDatabase) {
            sql = sql + (!statement.shouldValidate() ? " ENABLE NOVALIDATE " : "");
        }
        if (database instanceof PostgresDatabase && statement.isClustered() != null && statement.isClustered().booleanValue() && statement.getConstraintName() != null) {
            return new Sql[]{new UnparsedSql(sql, this.getAffectedPrimaryKey(statement)), new UnparsedSql("CLUSTER " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " USING " + database.escapeObjectName(statement.getConstraintName(), PrimaryKey.class), new DatabaseObject[0])};
        }
        return new Sql[]{new UnparsedSql(sql, this.getAffectedPrimaryKey(statement))};
    }

    protected PrimaryKey getAffectedPrimaryKey(AddPrimaryKeyStatement statement) {
        return new PrimaryKey().setTable((Table)new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName()));
    }
}

