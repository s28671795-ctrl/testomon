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
import liquibase.statement.core.RenameTableStatement;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Table;

public class RenameTableGenerator
extends AbstractSqlGenerator<RenameTableStatement> {
    @Override
    public boolean supports(RenameTableStatement statement, Database database) {
        return !(database instanceof FirebirdDatabase);
    }

    @Override
    public ValidationErrors validate(RenameTableStatement renameTableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("newTableName", renameTableStatement.getNewTableName());
        validationErrors.checkRequiredField("oldTableName", renameTableStatement.getOldTableName());
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(RenameTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql = database instanceof MSSQLDatabase ? "exec sp_rename '" + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + "', '" + statement.getNewTableName() + '\'' : (database instanceof SybaseDatabase ? "exec sp_rename '" + statement.getOldTableName() + "', '" + statement.getNewTableName() + '\'' : (database instanceof MySQLDatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " RENAME " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getNewTableName()) : (database instanceof PostgresDatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " RENAME TO " + database.escapeObjectName(statement.getNewTableName(), Table.class) : (database instanceof SybaseASADatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " RENAME " + database.escapeObjectName(statement.getNewTableName(), Table.class) : (database instanceof DerbyDatabase || database instanceof InformixDatabase ? "RENAME TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " TO " + database.escapeObjectName(statement.getNewTableName(), Table.class) : (database instanceof HsqlDatabase || database instanceof H2Database ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " RENAME TO " + database.escapeObjectName(statement.getNewTableName(), Table.class) : (database instanceof OracleDatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " RENAME TO " + database.escapeObjectName(statement.getNewTableName(), Table.class) : (database instanceof AbstractDb2Database ? "RENAME " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " TO " + database.escapeObjectName(statement.getNewTableName(), Table.class) : (database instanceof SQLiteDatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " RENAME TO " + database.escapeObjectName(statement.getNewTableName(), Table.class) : "RENAME " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " TO " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getNewTableName()))))))))));
        return new Sql[]{new UnparsedSql(sql, this.getAffectedOldTable(statement), this.getAffectedNewTable(statement))};
    }

    protected Relation getAffectedNewTable(RenameTableStatement statement) {
        return new Table().setName(statement.getNewTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName());
    }

    protected Relation getAffectedOldTable(RenameTableStatement statement) {
        return new Table().setName(statement.getOldTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName());
    }
}

