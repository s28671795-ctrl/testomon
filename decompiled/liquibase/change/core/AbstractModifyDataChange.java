/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.change.AbstractChange;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChangeProperty;
import liquibase.serializer.LiquibaseSerializable;

public abstract class AbstractModifyDataChange
extends AbstractChange {
    protected String catalogName;
    protected String schemaName;
    protected String tableName;
    protected List<ColumnConfig> whereParams = new ArrayList<ColumnConfig>();
    protected String where;

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

    @DatabaseChangeProperty(mustEqualExisting="table", description="Name of the table")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(serializationType=LiquibaseSerializable.SerializationType.NESTED_OBJECT, exampleValue="name='Bob'", description="SQL WHERE condition string")
    public String getWhere() {
        return this.where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    @DatabaseChangeProperty(isChangeProperty=false, description="Deprecated. Use 'where'")
    @Deprecated
    public String getWhereClause() {
        return this.where;
    }

    @Deprecated
    public void setWhereClause(String where) {
        this.where = where;
    }

    public void addWhereParam(ColumnConfig param2) {
        this.whereParams.add(param2);
    }

    public void removeWhereParam(ColumnConfig param2) {
        this.whereParams.remove(param2);
    }

    @DatabaseChangeProperty(description="Parameters for the \"where\" condition. Inserted in place of the :name and :value placeholders in the WHERE clause. If multiple, inserted in the order they are defined.")
    public List<ColumnConfig> getWhereParams() {
        return this.whereParams;
    }
}

