/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DropNotNullConstraintChange;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.BooleanType;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.ReorganizeTableStatement;
import liquibase.statement.core.SetNullableStatement;
import liquibase.statement.core.UpdateStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.util.BooleanUtil;
import liquibase.util.StringUtil;

@DatabaseChange(name="addNotNullConstraint", description="Adds a NOT NULL constraint to an existing table. If a defaultNullValue attribute is passed, all null values for the column will be updated to the passed value before the constraint is applied.", priority=1, appliesTo={"column"})
public class AddNotNullConstraintChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String defaultNullValue;
    private String columnDataType;
    private String constraintName;
    private Boolean shouldValidate;

    @DatabaseChangeProperty(since="3.0", mustEqualExisting="column.relation.catalog", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column.relation.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(description="Name of the table to add a NOT NULL constraint to.", mustEqualExisting="column.relation")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(description="Name of the column to add the constraint to", mustEqualExisting="column.relation.column")
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @DatabaseChangeProperty(description="Value to set all currently null values to. If defaultNullValue is not set and null values exist, the change fails")
    public String getDefaultNullValue() {
        return this.defaultNullValue;
    }

    public void setDefaultNullValue(String defaultNullValue) {
        this.defaultNullValue = defaultNullValue;
    }

    @DatabaseChangeProperty(description="Current data type of the column")
    public String getColumnDataType() {
        return this.columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    @DatabaseChangeProperty(description="Name of the constraint to add (if database supports names for NOT NULL constraints)")
    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        ArrayList<AbstractSqlStatement> statements = new ArrayList<AbstractSqlStatement>();
        if (this.defaultNullValue != null && !this.defaultNullValue.equalsIgnoreCase("null")) {
            LiquibaseDataType datatype;
            String columnDataType = this.getColumnDataType();
            Object finalDefaultNullValue = this.defaultNullValue;
            if (columnDataType != null && (datatype = DataTypeFactory.getInstance().fromDescription(columnDataType, database)) instanceof BooleanType) {
                if ((finalDefaultNullValue = datatype.objectToSql(finalDefaultNullValue, database)).equals("0")) {
                    finalDefaultNullValue = 0;
                } else if (finalDefaultNullValue.equals("1")) {
                    finalDefaultNullValue = 1;
                }
                if (columnDataType.toLowerCase().contains("bit")) {
                    finalDefaultNullValue = BooleanUtil.parseBoolean(finalDefaultNullValue.toString()) ? Integer.valueOf(1) : Integer.valueOf(0);
                }
                if (database instanceof PostgresDatabase) {
                    if (finalDefaultNullValue.equals(0)) {
                        finalDefaultNullValue = new DatabaseFunction("B'0'");
                    } else if (finalDefaultNullValue.equals(1)) {
                        finalDefaultNullValue = new DatabaseFunction("B'1'");
                    }
                }
            }
            statements.add(new UpdateStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName()).addNewColumnValue(this.getColumnName(), finalDefaultNullValue).setWhereClause(database.escapeObjectName(this.getColumnName(), Column.class) + " IS NULL"));
        }
        statements.add(new SetNullableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName(), this.getColumnDataType(), false, this.getConstraintName(), this.shouldValidate));
        if (database instanceof DB2Database) {
            statements.add(new ReorganizeTableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName()));
        }
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    @Override
    protected Change[] createInverses() {
        DropNotNullConstraintChange inverse = new DropNotNullConstraintChange();
        inverse.setColumnName(this.getColumnName());
        inverse.setSchemaName(this.getSchemaName());
        inverse.setTableName(this.getTableName());
        inverse.setColumnDataType(this.getColumnDataType());
        return new Change[]{inverse};
    }

    @Override
    public String getConfirmationMessage() {
        return "NOT NULL constraint " + (StringUtil.trimToNull(this.getConstraintName()) != null ? String.format("\"%s\" ", this.getConstraintName()) : "") + "has been added to " + this.getTableName() + "." + this.getColumnName();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @DatabaseChangeProperty(description="Defines whether to check if the NOT NULL constraint refers to a valid row. This is true if the constraint has 'ENABLE VALIDATE' set, or false if the constraint has 'ENABLE NOVALIDATE' set.")
    public Boolean getValidate() {
        return this.shouldValidate;
    }

    public void setValidate(Boolean shouldValidate) {
        this.shouldValidate = shouldValidate;
    }

    class SQLiteAlterTableVisitor
    implements SQLiteDatabase.AlterTableVisitor {
        SQLiteAlterTableVisitor() {
        }

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
            if (column.getName().equals(AddNotNullConstraintChange.this.getColumnName())) {
                column.getConstraints().setNullable(false);
            }
            return true;
        }

        @Override
        public boolean createThisIndex(Index index) {
            return true;
        }
    }
}

