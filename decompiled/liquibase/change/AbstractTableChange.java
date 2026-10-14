/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import liquibase.change.AbstractChange;
import liquibase.change.DatabaseChangeProperty;

public abstract class AbstractTableChange
extends AbstractChange {
    protected String catalogName;
    protected String schemaName;
    protected String tableName;

    @DatabaseChangeProperty(since="3.0", mustEqualExisting="table.catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="table.schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(description="Name of the table", mustEqualExisting="table", requiredForDatabase={"all"})
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

