/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.change.ColumnConfig;
import liquibase.change.ConstraintsConfig;
import liquibase.database.Database;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddAutoIncrementGenerator;
import liquibase.statement.core.AddAutoIncrementStatement;
import liquibase.structure.core.Index;

public class AddAutoIncrementGeneratorSQLite
extends AddAutoIncrementGenerator {
    @Override
    public ValidationErrors validate(AddAutoIncrementStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return super.validate(statement, database, sqlGeneratorChain);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(AddAutoIncrementStatement statement, Database database) {
        return database instanceof SQLiteDatabase;
    }

    @Override
    public boolean generateStatementsIsVolatile(Database database) {
        return true;
    }

    @Override
    public Sql[] generateSql(final AddAutoIncrementStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        SQLiteDatabase.AlterTableVisitor alterTableVisitor = new SQLiteDatabase.AlterTableVisitor(){

            @Override
            public ColumnConfig[] getColumnsToAdd() {
                return new ColumnConfig[0];
            }

            @Override
            public boolean copyThisColumn(ColumnConfig column) {
                return true;
            }

            @Override
            public boolean createThisColumn(ColumnConfig column) {
                AddAutoIncrementGeneratorSQLite.this.setPrimaryKeyAndAutoIncrement(column, statement);
                return true;
            }

            @Override
            public boolean createThisIndex(Index index) {
                return true;
            }
        };
        Sql[] generatedSqls = SQLiteDatabase.getAlterTableSqls(database, alterTableVisitor, statement.getCatalogName(), statement.getSchemaName(), statement.getTableName());
        return generatedSqls;
    }

    private void setPrimaryKeyAndAutoIncrement(ColumnConfig column, AddAutoIncrementStatement statement) {
        if (column.getName().equals(statement.getColumnName())) {
            column.setAutoIncrement(true);
            ConstraintsConfig constraints = column.getConstraints();
            if (constraints == null) {
                constraints = new ConstraintsConfig();
                column.setConstraints(constraints);
            }
            constraints.setPrimaryKey(true);
        }
    }
}

