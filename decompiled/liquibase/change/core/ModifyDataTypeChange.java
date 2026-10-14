/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.ModifyDataTypeStatement;
import liquibase.statement.core.ReorganizeTableStatement;

@DatabaseChange(name="modifyDataType", description="Modify the data type of a column", priority=1, appliesTo={"column"})
public class ModifyDataTypeChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String newDataType;

    @Override
    public boolean supports(Database database) {
        return !(database instanceof Db2zDatabase) && super.supports(database);
    }

    @Override
    public String getConfirmationMessage() {
        return this.tableName + "." + this.columnName + " datatype was changed to " + this.newDataType;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        ModifyDataTypeStatement modifyDataTypeStatement = new ModifyDataTypeStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName(), this.getNewDataType());
        if (database instanceof DB2Database) {
            return new SqlStatement[]{modifyDataTypeStatement, new ReorganizeTableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName())};
        }
        return new SqlStatement[]{modifyDataTypeStatement};
    }

    @DatabaseChangeProperty(mustEqualExisting="column.relation.catalog", since="3.0", description="Name of the database catalog")
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

    @DatabaseChangeProperty(mustEqualExisting="column.relation", description="Name of the table containing the column whose data type you want to change")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column")
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @DatabaseChangeProperty(description="Data type to convert the column to. Only modifies the data type itself and cannot define constraints")
    public String getNewDataType() {
        return this.newDataType;
    }

    public void setNewDataType(String newDataType) {
        this.newDataType = newDataType;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

