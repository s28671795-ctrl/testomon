/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.change.AbstractChange;
import liquibase.change.AddColumnConfig;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.AddColumnChange;
import liquibase.change.core.DropColumnChange;
import liquibase.database.Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.MariaDBDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;

@DatabaseChange(name="mergeColumns", description="Concatenates the values in two columns, joins them with a string, and stores the resulting value in a new column.", priority=1)
public class MergeColumnChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String column1Name;
    private String joinString;
    private String column2Name;
    private String finalColumnName;
    private String finalColumnType;

    @Override
    public boolean supports(Database database) {
        return super.supports(database) && !(database instanceof DerbyDatabase) && !(database instanceof Db2zDatabase);
    }

    @DatabaseChangeProperty(description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(description="Name of the table containing the columns to join")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(description="Name of the column containing the first half of the data", exampleValue="first_name")
    public String getColumn1Name() {
        return this.column1Name;
    }

    public void setColumn1Name(String column1Name) {
        this.column1Name = column1Name;
    }

    @DatabaseChangeProperty(description="Name of the column containing the second half of the data", exampleValue="last_name")
    public String getColumn2Name() {
        return this.column2Name;
    }

    public void setColumn2Name(String column2Name) {
        this.column2Name = column2Name;
    }

    @DatabaseChangeProperty(description="String to place between the values from column1 and column2 (may be empty)", exampleValue=" ")
    public String getJoinString() {
        return this.joinString;
    }

    public void setJoinString(String joinString) {
        this.joinString = joinString;
    }

    @DatabaseChangeProperty(description="Name of the column to create", exampleValue="full_name")
    public String getFinalColumnName() {
        return this.finalColumnName;
    }

    public void setFinalColumnName(String finalColumnName) {
        this.finalColumnName = finalColumnName;
    }

    @DatabaseChangeProperty(description="Data type of the column to create", exampleValue="varchar(255)")
    public String getFinalColumnType() {
        return this.finalColumnType;
    }

    public void setFinalColumnType(String finalColumnType) {
        this.finalColumnType = finalColumnType;
    }

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return database instanceof SQLiteDatabase;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        AddColumnChange addNewColumnChange = new AddColumnChange();
        addNewColumnChange.setSchemaName(this.schemaName);
        addNewColumnChange.setTableName(this.getTableName());
        AddColumnConfig columnConfig = new AddColumnConfig();
        columnConfig.setName(this.getFinalColumnName());
        columnConfig.setType(this.getFinalColumnType());
        addNewColumnChange.addColumn(columnConfig);
        ArrayList<SqlStatement> statements = new ArrayList<SqlStatement>(Arrays.asList(addNewColumnChange.generateStatements(database)));
        String updateStatement = "";
        updateStatement = database instanceof MySQLDatabase || database instanceof MariaDBDatabase ? "UPDATE " + database.escapeTableName(this.getCatalogName(), this.getSchemaName(), this.getTableName()) + " SET " + database.escapeObjectName(this.getFinalColumnName(), Column.class) + " = " + database.getConcatSql("'" + this.getJoinString() + "'", database.escapeObjectName(this.getColumn1Name(), Column.class), database.escapeObjectName(this.getColumn2Name(), Column.class)) : "UPDATE " + database.escapeTableName(this.getCatalogName(), this.getSchemaName(), this.getTableName()) + " SET " + database.escapeObjectName(this.getFinalColumnName(), Column.class) + " = " + database.getConcatSql(database.escapeObjectName(this.getColumn1Name(), Column.class), "'" + this.getJoinString() + "'", database.escapeObjectName(this.getColumn2Name(), Column.class));
        statements.add(new RawSqlStatement(updateStatement));
        if (database instanceof SQLiteDatabase) {
            SQLiteDatabase.AlterTableVisitor alterTableVisitor = new SQLiteDatabase.AlterTableVisitor(){

                @Override
                public ColumnConfig[] getColumnsToAdd() {
                    ColumnConfig[] columnConfigs = new ColumnConfig[1];
                    ColumnConfig mergedColumn = new ColumnConfig();
                    mergedColumn.setName(MergeColumnChange.this.getFinalColumnName());
                    mergedColumn.setType(MergeColumnChange.this.getFinalColumnType());
                    columnConfigs[0] = mergedColumn;
                    return columnConfigs;
                }

                @Override
                public boolean copyThisColumn(ColumnConfig column) {
                    return !column.getName().equals(MergeColumnChange.this.getColumn1Name()) && !column.getName().equals(MergeColumnChange.this.getColumn2Name());
                }

                @Override
                public boolean createThisColumn(ColumnConfig column) {
                    return !column.getName().equals(MergeColumnChange.this.getColumn1Name()) && !column.getName().equals(MergeColumnChange.this.getColumn2Name());
                }

                @Override
                public boolean createThisIndex(Index index) {
                    for (Column column : index.getColumns()) {
                        if (!column.getName().equals(MergeColumnChange.this.getColumn1Name()) && !column.getName().equals(MergeColumnChange.this.getColumn2Name())) continue;
                        return false;
                    }
                    return true;
                }
            };
            try {
                List<SqlStatement> workAroundStatements = SQLiteDatabase.getAlterTableStatements(alterTableVisitor, database, this.getCatalogName(), this.getSchemaName(), this.getTableName());
                statements.addAll(workAroundStatements);
            }
            catch (DatabaseException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        } else {
            DropColumnChange dropColumn1Change = new DropColumnChange();
            dropColumn1Change.setSchemaName(this.schemaName);
            dropColumn1Change.setTableName(this.getTableName());
            dropColumn1Change.setColumnName(this.getColumn1Name());
            statements.addAll(Arrays.asList(dropColumn1Change.generateStatements(database)));
            DropColumnChange dropColumn2Change = new DropColumnChange();
            dropColumn2Change.setSchemaName(this.schemaName);
            dropColumn2Change.setTableName(this.getTableName());
            dropColumn2Change.setColumnName(this.getColumn2Name());
            statements.addAll(Arrays.asList(dropColumn2Change.generateStatements(database)));
        }
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    @Override
    public String getConfirmationMessage() {
        return "Columns " + this.getTableName() + "." + this.getColumn1Name() + " and " + this.getTableName() + "." + this.getColumn2Name() + " merged";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

