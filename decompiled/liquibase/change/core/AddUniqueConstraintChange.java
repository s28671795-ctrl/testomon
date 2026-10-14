/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DropUniqueConstraintChange;
import liquibase.database.Database;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AddUniqueConstraintStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.UniqueConstraint;

@DatabaseChange(name="addUniqueConstraint", description="Adds a unique constraint to an existing column or set of columns.", priority=1, appliesTo={"column"})
public class AddUniqueConstraintChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnNames;
    private String constraintName;
    private String tablespace;
    private Boolean clustered;
    private Boolean shouldValidate;
    private String forIndexName;
    private String forIndexSchemaName;
    private String forIndexCatalogName;
    private Boolean deferrable;
    private Boolean initiallyDeferred;
    private Boolean disabled;

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

    @DatabaseChangeProperty(mustEqualExisting="column.relation", description="Name of the table to create the unique constraint on")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column", description="Name of the column(s) to create the unique constraint on. Comma separated if multiple")
    public String getColumnNames() {
        return this.columnNames;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    @DatabaseChangeProperty(description="Name of the unique constraint")
    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @DatabaseChangeProperty(description="Tablespace to create the index in. Corresponds to file group in mssql")
    public String getTablespace() {
        return this.tablespace;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    @DatabaseChangeProperty(description="Defines whether the constraint is deferrable")
    public Boolean getDeferrable() {
        return this.deferrable;
    }

    public void setDeferrable(Boolean deferrable) {
        this.deferrable = deferrable;
    }

    @DatabaseChangeProperty(description="Defines whether the constraint is initially deferred")
    public Boolean getInitiallyDeferred() {
        return this.initiallyDeferred;
    }

    public void setInitiallyDeferred(Boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
    }

    @DatabaseChangeProperty(description="Specifies whether the constraint is disabled")
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @DatabaseChangeProperty(description="Defines whether to check if the unique constraint refers to a valid row. This is true if the constraint has 'ENABLE VALIDATE' set, or false if the constraint has 'ENABLE NOVALIDATE' set.")
    public Boolean getValidate() {
        return this.shouldValidate;
    }

    public void setValidate(Boolean validate) {
        this.shouldValidate = validate;
    }

    @DatabaseChangeProperty(description="Whether to create a clustered index")
    public Boolean getClustered() {
        return this.clustered;
    }

    public void setClustered(Boolean clustered) {
        this.clustered = clustered;
    }

    @DatabaseChangeProperty(description="Name of the index to associate with the constraint")
    public String getForIndexName() {
        return this.forIndexName;
    }

    public void setForIndexName(String forIndexName) {
        this.forIndexName = forIndexName;
    }

    @DatabaseChangeProperty(description="Name of the schema of the index to associate with the constraint")
    public String getForIndexSchemaName() {
        return this.forIndexSchemaName;
    }

    public void setForIndexSchemaName(String forIndexSchemaName) {
        this.forIndexSchemaName = forIndexSchemaName;
    }

    @DatabaseChangeProperty(description="Name of the catalog of the index to associate with the constraint")
    public String getForIndexCatalogName() {
        return this.forIndexCatalogName;
    }

    public void setForIndexCatalogName(String forIndexCatalogName) {
        this.forIndexCatalogName = forIndexCatalogName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        boolean deferrable = false;
        if (this.getDeferrable() != null) {
            deferrable = this.getDeferrable();
        }
        boolean initiallyDeferred = false;
        if (this.getInitiallyDeferred() != null) {
            initiallyDeferred = this.getInitiallyDeferred();
        }
        boolean disabled = false;
        if (this.getDisabled() != null) {
            disabled = this.getDisabled();
        }
        boolean clustered = false;
        if (this.getClustered() != null) {
            clustered = this.getClustered();
        }
        boolean shouldValidate = true;
        if (this.getValidate() != null) {
            shouldValidate = this.getValidate();
        }
        AddUniqueConstraintStatement statement = this.createAddUniqueConstraintStatement();
        statement.setTablespace(this.getTablespace()).setDeferrable(deferrable).setInitiallyDeferred(initiallyDeferred).setDisabled(disabled).setClustered(clustered).setShouldValidate(shouldValidate);
        statement.setForIndexName(this.getForIndexName());
        statement.setForIndexSchemaName(this.getForIndexSchemaName());
        statement.setForIndexCatalogName(this.getForIndexCatalogName());
        return new SqlStatement[]{statement};
    }

    protected AddUniqueConstraintStatement createAddUniqueConstraintStatement() {
        return new AddUniqueConstraintStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), ColumnConfig.arrayFromNames(this.getColumnNames()), this.getConstraintName());
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            UniqueConstraint example = new UniqueConstraint(this.getConstraintName(), this.getCatalogName(), this.getSchemaName(), this.getTableName(), Column.arrayFromNames(this.getColumnNames()));
            UniqueConstraint snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(example, database);
            result.assertComplete(snapshot != null, "Unique constraint does not exist");
            return result;
        }
        catch (Exception e) {
            return result.unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Unique constraint added to " + this.getTableName() + "(" + this.getColumnNames() + ")";
    }

    @Override
    protected Change[] createInverses() {
        DropUniqueConstraintChange inverse = new DropUniqueConstraintChange();
        inverse.setSchemaName(this.getSchemaName());
        inverse.setTableName(this.getTableName());
        inverse.setConstraintName(this.getConstraintName());
        inverse.setUniqueColumns(this.getColumnNames());
        return new Change[]{inverse};
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

