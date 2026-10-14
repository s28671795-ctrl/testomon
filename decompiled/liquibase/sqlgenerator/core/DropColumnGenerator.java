/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.sqlgenerator.core.DropDefaultValueGenerator;
import liquibase.statement.core.DropColumnStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;

public class DropColumnGenerator
extends AbstractSqlGenerator<DropColumnStatement> {
    @Override
    public ValidationErrors validate(DropColumnStatement dropColumnStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        if (dropColumnStatement.isMultiple()) {
            ValidationErrors validationErrors = new ValidationErrors();
            DropColumnStatement firstColumn = dropColumnStatement.getColumns().get(0);
            for (DropColumnStatement drop : dropColumnStatement.getColumns()) {
                validationErrors.addAll(this.validateSingleColumn(drop));
                if (drop.getTableName() != null && !drop.getTableName().equals(firstColumn.getTableName())) {
                    validationErrors.addError("All columns must be targeted at the same table");
                }
                if (!drop.isMultiple()) continue;
                validationErrors.addError("Nested multiple drop column statements are not supported");
            }
            return validationErrors;
        }
        return this.validateSingleColumn(dropColumnStatement);
    }

    private ValidationErrors validateSingleColumn(DropColumnStatement dropColumnStatement) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", dropColumnStatement.getTableName());
        validationErrors.checkRequiredField("columnName", dropColumnStatement.getColumnName());
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(DropColumnStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        if (statement.isMultiple()) {
            return this.generateMultipleColumnSql(statement.getColumns(), database);
        }
        return this.generateSingleColumnSql(statement, database);
    }

    private Sql[] generateMultipleColumnSql(List<DropColumnStatement> columns, Database database) {
        ArrayList<Sql> result = new ArrayList<Sql>();
        if (database instanceof MySQLDatabase) {
            StringBuilder alterTable = new StringBuilder("ALTER TABLE " + database.escapeTableName(columns.get(0).getCatalogName(), columns.get(0).getSchemaName(), columns.get(0).getTableName()));
            for (int i = 0; i < columns.size(); ++i) {
                alterTable.append(" DROP ").append(database.escapeColumnName(columns.get(i).getCatalogName(), columns.get(i).getSchemaName(), columns.get(i).getTableName(), columns.get(i).getColumnName()));
                if (i >= columns.size() - 1) continue;
                alterTable.append(",");
            }
            result.add(new UnparsedSql(alterTable.toString(), this.getAffectedColumns(columns)));
        } else if (database instanceof MSSQLDatabase) {
            for (DropColumnStatement column : columns) {
                Sql[] sqls = this.generateSingleColumnSql(column, database);
                result.add(sqls[0]);
                result.add(sqls[1]);
            }
        } else {
            for (DropColumnStatement column : columns) {
                result.add(this.generateSingleColumnSql(column, database)[0]);
            }
        }
        return result.toArray(EMPTY_SQL);
    }

    private Sql[] generateSingleColumnSql(DropColumnStatement statement, Database database) {
        if (database instanceof DB2Database) {
            return new Sql[]{new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()), this.getAffectedColumn(statement))};
        }
        if (database instanceof Db2zDatabase) {
            return new Sql[]{new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " RESTRICT", this.getAffectedColumn(statement))};
        }
        if (database instanceof SybaseDatabase || database instanceof SybaseASADatabase || database instanceof FirebirdDatabase || database instanceof InformixDatabase) {
            return new Sql[]{new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()), this.getAffectedColumn(statement))};
        }
        if (database instanceof MSSQLDatabase) {
            return new Sql[]{this.generateDropDV(statement, database), new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()), this.getAffectedColumn(statement))};
        }
        return new Sql[]{new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " DROP COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()), this.getAffectedColumn(statement))};
    }

    private Column[] getAffectedColumns(List<DropColumnStatement> columns) {
        ArrayList<Column> affected = new ArrayList<Column>();
        for (DropColumnStatement column : columns) {
            affected.add(this.getAffectedColumn(column));
        }
        return affected.toArray(new Column[0]);
    }

    protected Column getAffectedColumn(DropColumnStatement statement) {
        return new Column().setName(statement.getColumnName()).setRelation(new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName()));
    }

    private UnparsedSql generateDropDV(DropColumnStatement statement, Database database) {
        return new UnparsedSql((String)DropDefaultValueGenerator.DROP_DF_MSSQL.apply(database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()), database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName())), new DatabaseObject[0]);
    }
}

