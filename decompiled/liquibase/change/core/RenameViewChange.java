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
import liquibase.statement.core.RenameViewStatement;
import liquibase.structure.core.View;

@DatabaseChange(name="renameView", description="Renames an existing view", priority=1, appliesTo={"view"})
public class RenameViewChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String oldViewName;
    private String newViewName;

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

    @DatabaseChangeProperty(mustEqualExisting="view", description="Name of the existing view to rename")
    public String getOldViewName() {
        return this.oldViewName;
    }

    public void setOldViewName(String oldViewName) {
        this.oldViewName = oldViewName;
    }

    @DatabaseChangeProperty(description="New name for the view")
    public String getNewViewName() {
        return this.newViewName;
    }

    public void setNewViewName(String newViewName) {
        this.newViewName = newViewName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new RenameViewStatement(this.getCatalogName(), this.getSchemaName(), this.getOldViewName(), this.getNewViewName())};
    }

    @Override
    protected Change[] createInverses() {
        RenameViewChange inverse = new RenameViewChange();
        inverse.setOldViewName(this.getNewViewName());
        inverse.setNewViewName(this.getOldViewName());
        return new Change[]{inverse};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            ChangeStatus changeStatus = new ChangeStatus();
            View newView = SnapshotGeneratorFactory.getInstance().createSnapshot(new View(this.getCatalogName(), this.getSchemaName(), this.getNewViewName()), database);
            View oldView = SnapshotGeneratorFactory.getInstance().createSnapshot(new View(this.getCatalogName(), this.getSchemaName(), this.getOldViewName()), database);
            if (newView == null && oldView == null) {
                return changeStatus.unknown("Neither view exists");
            }
            if (newView != null && oldView != null) {
                return changeStatus.unknown("Both views exist");
            }
            changeStatus.assertComplete(newView != null, "New view does not exist");
            return changeStatus;
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "View " + this.oldViewName + " renamed to " + this.newViewName;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

