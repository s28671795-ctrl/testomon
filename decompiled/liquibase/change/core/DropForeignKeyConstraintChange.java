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
import liquibase.statement.core.DropForeignKeyConstraintStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;

@DatabaseChange(name="dropForeignKeyConstraint", description="Drops an existing foreign key", priority=1, appliesTo={"foreignKey"})
public class DropForeignKeyConstraintChange
extends AbstractChange {
    private String baseTableCatalogName;
    private String baseTableSchemaName;
    private String baseTableName;
    private String constraintName;

    @DatabaseChangeProperty(mustEqualExisting="foreignKey.table.catalog", since="3.0", description="Name of the database catalog of the base table")
    public String getBaseTableCatalogName() {
        return this.baseTableCatalogName;
    }

    public void setBaseTableCatalogName(String baseTableCatalogName) {
        this.baseTableCatalogName = baseTableCatalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="foreignKey.table.schema", description="Name of the database schema of the base table")
    public String getBaseTableSchemaName() {
        return this.baseTableSchemaName;
    }

    public void setBaseTableSchemaName(String baseTableSchemaName) {
        this.baseTableSchemaName = baseTableSchemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="foreignKey.table", description="Name of the table containing the column constrained by the foreign key")
    public String getBaseTableName() {
        return this.baseTableName;
    }

    public void setBaseTableName(String baseTableName) {
        this.baseTableName = baseTableName;
    }

    @DatabaseChangeProperty(mustEqualExisting="foreignKey", description="Name of the foreign key constraint to drop", exampleValue="fk_address_person")
    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new DropForeignKeyConstraintStatement(this.getBaseTableCatalogName(), this.getBaseTableSchemaName(), this.getBaseTableName(), this.getConstraintName())};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            return new ChangeStatus().assertComplete(!SnapshotGeneratorFactory.getInstance().has(new ForeignKey(this.getConstraintName(), this.getBaseTableCatalogName(), this.getBaseTableSchemaName(), this.getBaseTableCatalogName(), new Column[0]), database), "Foreign key exists");
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Foreign key " + this.getConstraintName() + " dropped";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

