/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.SetColumnRemarksStatement;

@DatabaseChange(name="setColumnRemarks", description="Set remarks on a column", priority=1)
public class SetColumnRemarksChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String remarks;
    private String columnDataType;
    private String columnParentType;

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.addAll(super.validate(database));
        return validationErrors;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new SetColumnRemarksStatement(this.catalogName, this.schemaName, this.tableName, this.columnName, this.remarks, this.columnDataType, this.columnParentType)};
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

    @DatabaseChangeProperty(description="Name of the table containing the column to set remarks on")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(description="Name of the column to set remarks on")
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @DatabaseChangeProperty(description="A brief descriptive comment written to the column metadata.")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String getConfirmationMessage() {
        return "Remarks set on " + this.tableName + "." + this.columnName;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @DatabaseChangeProperty(description="Data type of the column")
    public String getColumnDataType() {
        return this.columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    @DatabaseChangeProperty(description="Indicates the parent object type of the column we are setting remarks against. Valid values are VIEW and TABLE. Default: TABLE.")
    public String getColumnParentType() {
        return this.columnParentType;
    }

    public void setColumnParentType(String columnParentType) {
        this.columnParentType = columnParentType;
    }
}

