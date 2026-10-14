/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RenameTableStatement;
import liquibase.statement.core.ReorganizeTableStatement;
import liquibase.structure.core.Table;

@DatabaseChange(name="renameTable", description="Renames an existing table", priority=1, appliesTo={"table"})
public class RenameTableChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String oldTableName;
    private String newTableName;

    @DatabaseChangeProperty(mustEqualExisting="table.catalog", description="Name of the database catalog")
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

    @DatabaseChangeProperty(mustEqualExisting="table", description="Name of the existing table to rename", exampleValue="person")
    public String getOldTableName() {
        return this.oldTableName;
    }

    public void setOldTableName(String oldTableName) {
        this.oldTableName = oldTableName;
    }

    @DatabaseChangeProperty(description="New name for the table", exampleValue="employee")
    public String getNewTableName() {
        return this.newTableName;
    }

    public void setNewTableName(String newTableName) {
        this.newTableName = newTableName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        ArrayList<AbstractSqlStatement> statements = new ArrayList<AbstractSqlStatement>();
        statements.add(new RenameTableStatement(this.getCatalogName(), this.getSchemaName(), this.getOldTableName(), this.getNewTableName()));
        if (database instanceof DB2Database) {
            statements.add(new ReorganizeTableStatement(this.getCatalogName(), this.getSchemaName(), this.getNewTableName()));
        }
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            ChangeStatus changeStatus = new ChangeStatus();
            Table newTable = SnapshotGeneratorFactory.getInstance().createSnapshot(new Table(this.getCatalogName(), this.getSchemaName(), this.getNewTableName()), database);
            Table oldTable = SnapshotGeneratorFactory.getInstance().createSnapshot(new Table(this.getCatalogName(), this.getSchemaName(), this.getOldTableName()), database);
            if (newTable == null && oldTable == null) {
                return changeStatus.unknown("Neither table exists");
            }
            if (newTable != null && oldTable != null) {
                return changeStatus.unknown("Both tables exist");
            }
            changeStatus.assertComplete(newTable != null, "New table does not exist");
            return changeStatus;
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    protected Change[] createInverses() {
        RenameTableChange inverse = new RenameTableChange();
        inverse.setSchemaName(this.getSchemaName());
        inverse.setOldTableName(this.getNewTableName());
        inverse.setNewTableName(this.getOldTableName());
        return new Change[]{inverse};
    }

    @Override
    public String getConfirmationMessage() {
        return "Table " + this.oldTableName + " renamed to " + this.newTableName;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

