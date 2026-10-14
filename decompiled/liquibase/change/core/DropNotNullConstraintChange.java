/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.AddNotNullConstraintChange;
import liquibase.database.Database;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.SetNullableStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;

@DatabaseChange(name="dropNotNullConstraint", description="Makes a column nullable", priority=1, appliesTo={"column"})
public class DropNotNullConstraintChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String columnDataType;
    private String constraintName;
    private Boolean shouldValidate;

    @DatabaseChangeProperty(since="3.0", mustEqualExisting="notNullConstraint.table.catalog", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="notNullConstraint.table.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(description="Name of the table containing the column to drop the constraint from", mustEqualExisting="notNullConstraint.table")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(description="Name of the column to drop the constraint from", mustEqualExisting="notNullConstraint.column")
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @DatabaseChangeProperty(description="Current data type of the column", requiredForDatabase={"informix", "mariadb", "mssql", "mysql"}, supportsDatabase={"all"})
    public String getColumnDataType() {
        return this.columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    @DatabaseChangeProperty(description="Name of the constraint to drop (if database supports names for NOT NULL constraints)")
    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new SetNullableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName(), this.getColumnDataType(), true, this.getConstraintName())};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            Column snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(new Column(Table.class, this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName()), database);
            Boolean nullable = snapshot.isNullable();
            return new ChangeStatus().assertComplete(nullable == null || nullable != false, "Column is not null");
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    protected Change[] createInverses() {
        AddNotNullConstraintChange inverse = new AddNotNullConstraintChange();
        inverse.setColumnName(this.getColumnName());
        inverse.setSchemaName(this.getSchemaName());
        inverse.setTableName(this.getTableName());
        inverse.setColumnDataType(this.getColumnDataType());
        inverse.setConstraintName(this.getConstraintName());
        return new Change[]{inverse};
    }

    @Override
    public String getConfirmationMessage() {
        return "Null constraint dropped from " + this.getTableName() + "." + this.getColumnName();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

