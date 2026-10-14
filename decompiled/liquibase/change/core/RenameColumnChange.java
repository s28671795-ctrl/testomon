/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RenameColumnStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;

@DatabaseChange(name="renameColumn", description="Renames an existing column", priority=1, appliesTo={"column"})
public class RenameColumnChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String oldColumnName;
    private String newColumnName;
    private String columnDataType;
    private String remarks;

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

    @DatabaseChangeProperty(description="Name of the table containing the column to rename", mustEqualExisting="column.relation")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(description="Name of the existing column to rename", exampleValue="name", mustEqualExisting="column")
    public String getOldColumnName() {
        return this.oldColumnName;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }

    @DatabaseChangeProperty(description="New name for the column", exampleValue="full_name")
    public String getNewColumnName() {
        return this.newColumnName;
    }

    public void setNewColumnName(String newColumnName) {
        this.newColumnName = newColumnName;
    }

    @DatabaseChangeProperty(description="Data type of the column")
    public String getColumnDataType() {
        return this.columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    @DatabaseChangeProperty(description="A brief descriptive comment written to the column metadata")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new RenameColumnStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getOldColumnName(), this.getNewColumnName(), this.getColumnDataType(), this.getRemarks())};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            ChangeStatus changeStatus = new ChangeStatus();
            Column newColumn = SnapshotGeneratorFactory.getInstance().createSnapshot(new Column(Table.class, this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getNewColumnName()), database);
            Column oldColumn = SnapshotGeneratorFactory.getInstance().createSnapshot(new Column(Table.class, this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getOldColumnName()), database);
            if (newColumn == null && oldColumn == null) {
                return changeStatus.unknown("Neither column exists");
            }
            if (newColumn != null && oldColumn != null) {
                return changeStatus.unknown("Both columns exist");
            }
            changeStatus.assertComplete(newColumn != null, "New column does not exist");
            return changeStatus;
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    protected Change[] createInverses() {
        RenameColumnChange inverse = new RenameColumnChange();
        inverse.setSchemaName(this.getSchemaName());
        inverse.setTableName(this.getTableName());
        inverse.setOldColumnName(this.getNewColumnName());
        inverse.setNewColumnName(this.getOldColumnName());
        inverse.setColumnDataType(this.getColumnDataType());
        return new Change[]{inverse};
    }

    @Override
    public String getConfirmationMessage() {
        return "Column " + this.tableName + "." + this.oldColumnName + " renamed to " + this.newColumnName;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

