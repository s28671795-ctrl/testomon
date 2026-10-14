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
import liquibase.statement.core.DropViewStatement;
import liquibase.structure.core.View;

@DatabaseChange(name="dropView", description="Drops an existing view", priority=1, appliesTo={"view"})
public class DropViewChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String viewName;
    private Boolean ifExists;

    @DatabaseChangeProperty(mustEqualExisting="view.catalog", since="3.0", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="view.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="view", description="Name of the view to drop")
    public String getViewName() {
        return this.viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @DatabaseChangeProperty(since="4.19.0", supportsDatabase={"all"}, description="Appends IF EXISTS to the DROP VIEW statement. If ifExists=true, the view is only dropped if it already exists, but the migration continues even if the view does not exist. If ifExists=false and the view does not exist, the database returns an error. Default: false.")
    public Boolean isIfExists() {
        return this.ifExists;
    }

    public void setIfExists(Boolean ifExists) {
        this.ifExists = ifExists;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new DropViewStatement(this.getCatalogName(), this.getSchemaName(), this.getViewName(), this.isIfExists())};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            return new ChangeStatus().assertComplete(!SnapshotGeneratorFactory.getInstance().has(new View(this.getCatalogName(), this.getSchemaName(), this.getViewName()), database), "View exists");
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "View " + this.getViewName() + " dropped";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

