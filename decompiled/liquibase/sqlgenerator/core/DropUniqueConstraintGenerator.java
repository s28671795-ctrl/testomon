/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.change.ColumnConfig;
import liquibase.database.Database;
import liquibase.database.core.CockroachDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.DropUniqueConstraintStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;

public class DropUniqueConstraintGenerator
extends AbstractSqlGenerator<DropUniqueConstraintStatement> {
    @Override
    public boolean supports(DropUniqueConstraintStatement statement, Database database) {
        return !(database instanceof SQLiteDatabase);
    }

    @Override
    public ValidationErrors validate(DropUniqueConstraintStatement dropUniqueConstraintStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", dropUniqueConstraintStatement.getTableName());
        validationErrors.checkRequiredField("constraintName", dropUniqueConstraintStatement.getConstraintName());
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(DropUniqueConstraintStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql = database instanceof MySQLDatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP KEY " + database.escapeConstraintName(statement.getConstraintName()) : (database instanceof OracleDatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP CONSTRAINT " + database.escapeConstraintName(statement.getConstraintName()) + " DROP INDEX" : (database instanceof SybaseASADatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP CONSTRAINT " + database.escapeConstraintName(statement.getConstraintName()) : (database instanceof CockroachDatabase ? "DROP INDEX " + database.escapeConstraintName(statement.getConstraintName()) + " CASCADE" : "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP CONSTRAINT " + database.escapeConstraintName(statement.getConstraintName()))));
        return new Sql[]{new UnparsedSql(sql, this.getAffectedUniqueConstraint(statement))};
    }

    protected UniqueConstraint getAffectedUniqueConstraint(DropUniqueConstraintStatement statement) {
        UniqueConstraint constraint = new UniqueConstraint().setName(statement.getConstraintName()).setRelation(new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName()));
        if (statement.getUniqueColumns() != null) {
            int i = 0;
            for (ColumnConfig column : statement.getUniqueColumns()) {
                constraint.addColumn(i++, new Column(column));
            }
        }
        return constraint;
    }
}

