/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.change.AddColumnConfig;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.CompoundStatement;

public class CreateIndexStatement
extends AbstractSqlStatement
implements CompoundStatement {
    private final String tableCatalogName;
    private final String tableSchemaName;
    private final String indexName;
    private final String tableName;
    private final AddColumnConfig[] columns;
    private String tablespace;
    private final Boolean unique;
    private String associatedWith;
    private Boolean clustered;

    public CreateIndexStatement(String indexName, String tableCatalogName, String tableSchemaName, String tableName, Boolean isUnique, String associatedWith, AddColumnConfig ... columns) {
        this.indexName = indexName;
        this.tableCatalogName = tableCatalogName;
        this.tableSchemaName = tableSchemaName;
        this.tableName = tableName;
        this.columns = columns;
        this.unique = isUnique;
        this.associatedWith = associatedWith;
    }

    public String getTableCatalogName() {
        return this.tableCatalogName;
    }

    public String getTableSchemaName() {
        return this.tableSchemaName;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public AddColumnConfig[] getColumns() {
        return this.columns;
    }

    public String getTablespace() {
        return this.tablespace;
    }

    public CreateIndexStatement setTablespace(String tablespace) {
        this.tablespace = tablespace;
        return this;
    }

    public Boolean isUnique() {
        return this.unique;
    }

    public String getAssociatedWith() {
        return this.associatedWith;
    }

    public void setAssociatedWith(String associatedWith) {
        this.associatedWith = associatedWith;
    }

    public Boolean isClustered() {
        return this.clustered;
    }

    public CreateIndexStatement setClustered(Boolean clustered) {
        this.clustered = clustered == null ? Boolean.valueOf(false) : clustered;
        return this;
    }
}

