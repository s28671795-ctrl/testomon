/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import liquibase.change.ColumnConfig;
import liquibase.change.ConstraintsConfig;
import liquibase.database.Database;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddColumnGenerator;
import liquibase.statement.ColumnConstraint;
import liquibase.statement.ForeignKeyConstraint;
import liquibase.statement.core.AddColumnStatement;
import liquibase.structure.core.Index;

public class AddColumnGeneratorSQLite
extends AddColumnGenerator {
    @Override
    public ValidationErrors validate(AddColumnStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = super.validate(statement, database, sqlGeneratorChain);
        validationErrors.checkRequiredField("tableName", statement);
        validationErrors.checkRequiredField("columnName", statement);
        return validationErrors;
    }

    @Override
    public boolean generateStatementsIsVolatile(Database database) {
        return true;
    }

    @Override
    public boolean supports(AddColumnStatement statement, Database database) {
        return database instanceof SQLiteDatabase;
    }

    @Override
    public Sql[] generateSql(AddColumnStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        final ArrayList<AddColumnStatement> columns = new ArrayList<AddColumnStatement>(statement.getColumns());
        if (columns.size() == 0) {
            columns.add(statement);
        }
        final Set newColumnNames = columns.stream().map(AddColumnStatement::getColumnName).collect(Collectors.toSet());
        SQLiteDatabase.AlterTableVisitor alterTableVisitor = new SQLiteDatabase.AlterTableVisitor(){

            @Override
            public ColumnConfig[] getColumnsToAdd() {
                ColumnConfig[] columnConfigs = new ColumnConfig[columns.size()];
                int i = 0;
                for (AddColumnStatement column : columns) {
                    ColumnConfig newColumn = new ColumnConfig();
                    newColumn.setName(column.getColumnName());
                    newColumn.setType(column.getColumnType());
                    newColumn.setAutoIncrement(column.isAutoIncrement());
                    ConstraintsConfig constraintsConfig = new ConstraintsConfig();
                    if (column.isPrimaryKey()) {
                        constraintsConfig.setPrimaryKey(true);
                    }
                    if (column.isNullable()) {
                        constraintsConfig.setNullable(true);
                    }
                    if (column.isUnique()) {
                        constraintsConfig.setUnique(true);
                    }
                    newColumn.setConstraints(constraintsConfig);
                    for (ColumnConstraint constraint : column.getConstraints()) {
                        if (!(constraint instanceof ForeignKeyConstraint)) continue;
                        ForeignKeyConstraint fkConstraint = (ForeignKeyConstraint)constraint;
                        constraintsConfig.setReferencedTableCatalogName(fkConstraint.getReferencedTableCatalogName());
                        constraintsConfig.setReferencedTableSchemaName(fkConstraint.getReferencedTableSchemaName());
                        constraintsConfig.setReferencedTableName(fkConstraint.getReferencedTableName());
                        constraintsConfig.setReferencedColumnNames(fkConstraint.getReferencedColumnNames());
                        constraintsConfig.setReferences(fkConstraint.getReferences());
                        constraintsConfig.setForeignKeyName(fkConstraint.getForeignKeyName());
                        if (!fkConstraint.isDeleteCascade()) continue;
                        constraintsConfig.setDeleteCascade(true);
                    }
                    columnConfigs[i++] = newColumn;
                }
                return columnConfigs;
            }

            @Override
            public boolean copyThisColumn(ColumnConfig column) {
                return !newColumnNames.contains(column.getName());
            }

            @Override
            public boolean createThisColumn(ColumnConfig column) {
                return true;
            }

            @Override
            public boolean createThisIndex(Index index) {
                return true;
            }
        };
        String catalogName = ((AddColumnStatement)columns.get(0)).getCatalogName();
        String schemaName = ((AddColumnStatement)columns.get(0)).getSchemaName();
        String tableName = ((AddColumnStatement)columns.get(0)).getTableName();
        Sql[] generatedSqls = SQLiteDatabase.getAlterTableSqls(database, alterTableVisitor, catalogName, schemaName, tableName);
        return generatedSqls;
    }

    @Override
    public int getPriority() {
        return 5;
    }
}

