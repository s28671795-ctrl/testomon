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
import liquibase.statement.core.DropIndexStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;

@DatabaseChange(name="dropIndex", description="Drops an existing index", priority=1, appliesTo={"index"})
public class DropIndexChange
extends AbstractChange {
    private String schemaName;
    private String indexName;
    private String tableName;
    private String associatedWith;
    private String catalogName;

    @DatabaseChangeProperty(mustEqualExisting="index.schema", description="Name of the database schema", supportsDatabase={"all"})
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="index", description="Name of the index to drop", supportsDatabase={"all"})
    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    @DatabaseChangeProperty(mustEqualExisting="index.table", description="Name of the indexed table", requiredForDatabase={"sybase", "mysql", "mssql", "mariadb", "asany"}, supportsDatabase={"all"})
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new DropIndexStatement(this.getIndexName(), this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getAssociatedWith())};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            return new ChangeStatus().assertComplete(!SnapshotGeneratorFactory.getInstance().has(new Index(this.getIndexName(), this.getCatalogName(), this.getSchemaName(), this.getTableName(), new Column[0]), database), "Index exists");
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Index " + this.getIndexName() + " dropped from table " + this.getTableName();
    }

    @DatabaseChangeProperty(isChangeProperty=false, description="Index associations. Valid values: primaryKey, foreignKey, uniqueConstriant, none")
    public String getAssociatedWith() {
        return this.associatedWith;
    }

    public void setAssociatedWith(String associatedWith) {
        this.associatedWith = associatedWith;
    }

    @DatabaseChangeProperty(mustEqualExisting="index.catalog", since="3.0", description="Name of the database catalog", supportsDatabase={"all"})
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

