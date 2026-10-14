/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeStatus;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.database.core.SybaseASADatabase;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DropUniqueConstraintStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.UniqueConstraint;

@DatabaseChange(name="dropUniqueConstraint", description="Drops an existing unique constraint", priority=1, appliesTo={"uniqueConstraint"})
public class DropUniqueConstraintChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String constraintName;
    private String uniqueColumns;

    @DatabaseChangeProperty(since="3.0", mustEqualExisting="uniqueConstraint.table.catalog", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="uniqueConstraint.table.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(description="Name of the table to drop the unique constraint from", mustEqualExisting="uniqueConstraint.table")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(description="Name of the unique constraint to drop", mustEqualExisting="uniqueConstraint")
    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @DatabaseChangeProperty(exampleValue="name", description="For SAP SQL Anywhere, a list of columns in the UNIQUE clause", supportsDatabase={"sybase"})
    public String getUniqueColumns() {
        return this.uniqueColumns;
    }

    public void setUniqueColumns(String uniqueColumns) {
        this.uniqueColumns = uniqueColumns;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        DropUniqueConstraintStatement statement = new DropUniqueConstraintStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getConstraintName());
        if (database instanceof SybaseASADatabase) {
            statement.setUniqueColumns(ColumnConfig.arrayFromNames(this.uniqueColumns));
        }
        return new SqlStatement[]{statement};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            UniqueConstraint example = new UniqueConstraint(this.getConstraintName(), this.getCatalogName(), this.getSchemaName(), this.getTableName(), new Column[0]);
            if (this.getUniqueColumns() != null) {
                for (String column : this.getUniqueColumns().split("\\s*,\\s*")) {
                    example.addColumn(example.getColumns().size(), new Column(column));
                }
            }
            return new ChangeStatus().assertComplete(!SnapshotGeneratorFactory.getInstance().has(example, database), "Unique constraint exists");
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Unique constraint " + this.getConstraintName() + " dropped from " + this.getTableName();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

