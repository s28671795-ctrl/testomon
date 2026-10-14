/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DropDefaultValueStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;

@DatabaseChange(name="dropDefaultValue", description="Removes the database default value for a column", priority=1, appliesTo={"column"})
public class DropDefaultValueChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String columnDataType;

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

    @DatabaseChangeProperty(mustEqualExisting="column.relation", description="Name of the table containing the column")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column", description="Name of the column to drop the default value from")
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @DatabaseChangeProperty(supportsDatabase={"all"}, description="Data type of the column")
    public String getColumnDataType() {
        return this.columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new DropDefaultValueStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName(), this.getColumnDataType())};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            Column snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(new Column(Table.class, this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName()), database);
            return new ChangeStatus().assertComplete(snapshot.getDefaultValue() == null, "Column has a default value");
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Default value dropped from " + this.getTableName() + "." + this.getColumnName();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

