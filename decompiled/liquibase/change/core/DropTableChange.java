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
import liquibase.statement.core.DropTableStatement;
import liquibase.structure.core.Table;

@DatabaseChange(name="dropTable", description="Drops an existing table", priority=1, appliesTo={"table"})
public class DropTableChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private Boolean cascadeConstraints;

    @DatabaseChangeProperty(mustEqualExisting="table.catalog", since="3.0", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="table.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="table", description="Name of the table to drop")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(description="Whether to add CASCADE CONSTRAINTS to the SQL statement")
    public Boolean isCascadeConstraints() {
        return this.cascadeConstraints;
    }

    public void setCascadeConstraints(Boolean cascadeConstraints) {
        this.cascadeConstraints = cascadeConstraints;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        boolean constraints = false;
        if (this.isCascadeConstraints() != null) {
            constraints = this.isCascadeConstraints();
        }
        return new SqlStatement[]{new DropTableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), constraints)};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            return new ChangeStatus().assertComplete(!SnapshotGeneratorFactory.getInstance().has(new Table(this.getCatalogName(), this.getSchemaName(), this.getTableName()), database), "Table exists");
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Table " + this.getTableName() + " dropped";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

