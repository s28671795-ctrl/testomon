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
import liquibase.change.core.DropPrimaryKeyChange;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AddPrimaryKeyStatement;
import liquibase.statement.core.ReorganizeTableStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.PrimaryKey;

@DatabaseChange(name="addPrimaryKey", description="Adds a primary key out of an existing column or set of columns.", priority=1, appliesTo={"column"})
public class AddPrimaryKeyChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String tablespace;
    private String columnNames;
    private String constraintName;
    private Boolean clustered;
    private String forIndexName;
    private String forIndexSchemaName;
    private String forIndexCatalogName;
    private Boolean shouldValidate;

    @DatabaseChangeProperty(mustEqualExisting="column.relation", description="Name of the table to create the primary key on")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

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

    @DatabaseChangeProperty(mustEqualExisting="column", description="Name of the column(s) to create the primary key on. Comma separated if multiple")
    public String getColumnNames() {
        return this.columnNames;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    @DatabaseChangeProperty(description="Name of primary key constraint", exampleValue="pk_person")
    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @DatabaseChangeProperty(description="Name of the index to associate with the primary key")
    public String getForIndexName() {
        return this.forIndexName;
    }

    public void setForIndexName(String forIndexName) {
        this.forIndexName = forIndexName;
    }

    @DatabaseChangeProperty(description="Name of the database schema of the index to associate with the primary key")
    public String getForIndexSchemaName() {
        return this.forIndexSchemaName;
    }

    public void setForIndexSchemaName(String forIndexSchemaName) {
        this.forIndexSchemaName = forIndexSchemaName;
    }

    @DatabaseChangeProperty(description="Name of the database catalog of the index to associate with the primary key")
    public String getForIndexCatalogName() {
        return this.forIndexCatalogName;
    }

    public void setForIndexCatalogName(String forIndexCatalogName) {
        this.forIndexCatalogName = forIndexCatalogName;
    }

    @DatabaseChangeProperty(description="Tablespace to create the primary key in. Corresponds to file group in mssql")
    public String getTablespace() {
        return this.tablespace;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    @DatabaseChangeProperty(description="Whether to create a clustered index")
    public Boolean getClustered() {
        return this.clustered;
    }

    public void setClustered(Boolean clustered) {
        this.clustered = clustered;
    }

    @DatabaseChangeProperty(description="Defines whether to check if the primary key constraint refers to a valid row. This is true if the primary key has 'ENABLE VALIDATE' set, or false if the primary key has 'ENABLE NOVALIDATE' set.")
    public Boolean getValidate() {
        return this.shouldValidate;
    }

    public void setValidate(Boolean shouldValidate) {
        this.shouldValidate = shouldValidate;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        boolean shouldValidate = true;
        if (this.getValidate() != null) {
            shouldValidate = this.getValidate();
        }
        AddPrimaryKeyStatement statement = new AddPrimaryKeyStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), ColumnConfig.arrayFromNames(this.getColumnNames()), this.getConstraintName());
        statement.setTablespace(this.getTablespace());
        statement.setClustered(this.getClustered());
        statement.setForIndexName(this.getForIndexName());
        statement.setForIndexSchemaName(this.getForIndexSchemaName());
        statement.setForIndexCatalogName(this.getForIndexCatalogName());
        statement.setShouldValidate(shouldValidate);
        if (database instanceof DB2Database) {
            return new SqlStatement[]{statement, new ReorganizeTableStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName())};
        }
        return new SqlStatement[]{statement};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            PrimaryKey example = new PrimaryKey(this.getConstraintName(), this.getCatalogName(), this.getSchemaName(), this.getTableName(), Column.arrayFromNames(this.getColumnNames()));
            PrimaryKey snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(example, database);
            result.assertComplete(snapshot != null, "Primary key does not exist");
            return result;
        }
        catch (Exception e) {
            return result.unknown(e);
        }
    }

    @Override
    protected Change[] createInverses() {
        DropPrimaryKeyChange inverse = new DropPrimaryKeyChange();
        inverse.setSchemaName(this.getSchemaName());
        inverse.setTableName(this.getTableName());
        inverse.setConstraintName(this.getConstraintName());
        if (this.getForIndexName() != null) {
            inverse.setDropIndex(false);
        }
        return new Change[]{inverse};
    }

    @Override
    public String getConfirmationMessage() {
        return "Primary key added to " + this.getTableName() + " (" + this.getColumnNames() + ")";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

