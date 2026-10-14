/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Date;
import liquibase.change.ColumnConfig;
import liquibase.database.Database;
import liquibase.database.core.SQLiteDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.BooleanType;
import liquibase.datatype.core.CharType;
import liquibase.datatype.core.DateType;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddDefaultValueGenerator;
import liquibase.statement.core.AddDefaultValueStatement;
import liquibase.structure.core.Index;

public class AddDefaultValueGeneratorSQLite
extends AddDefaultValueGenerator {
    @Override
    public ValidationErrors validate(AddDefaultValueStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return super.validate(statement, database, sqlGeneratorChain);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(AddDefaultValueStatement statement, Database database) {
        return database instanceof SQLiteDatabase;
    }

    @Override
    public boolean generateStatementsIsVolatile(Database database) {
        return true;
    }

    @Override
    public Sql[] generateSql(final AddDefaultValueStatement statement, final Database database, SqlGeneratorChain sqlGeneratorChain) {
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
                if (column.getName().equals(statement.getColumnName())) {
                    if (statement.getDefaultValueConstraintName() != null) {
                        column.setDefaultValueConstraintName(statement.getDefaultValueConstraintName());
                    }
                    Object defaultValue = statement.getDefaultValue();
                    LiquibaseDataType dataType = DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType(), database);
                    if (dataType instanceof BooleanType) {
                        if (defaultValue instanceof Boolean) {
                            column.setDefaultValueBoolean((Boolean)defaultValue);
                        } else {
                            column.setDefaultValueBoolean(defaultValue.toString());
                        }
                    } else if (dataType instanceof CharType) {
                        column.setDefaultValue(defaultValue.toString());
                    } else if (dataType instanceof DateType) {
                        if (defaultValue instanceof Date) {
                            column.setDefaultValueDate((Date)defaultValue);
                        } else {
                            column.setDefaultValueDate(defaultValue.toString());
                        }
                    } else {
                        column.setDefaultValue(defaultValue.toString());
                    }
                }
                return true;
            }

            @Override
            public boolean createThisIndex(Index index) {
                return true;
            }
        };
        return SQLiteDatabase.getAlterTableSqls(database, alterTableVisitor, statement.getCatalogName(), statement.getSchemaName(), statement.getTableName());
    }
}

