/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.change.ColumnConfig;
import liquibase.statement.AbstractSqlStatement;
import liquibase.util.StringUtil;

public class AddPrimaryKeyStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private String tablespace;
    private final ColumnConfig[] columns;
    private final String constraintName;
    private Boolean clustered;
    private String forIndexName;
    private String forIndexSchemaName;
    private String forIndexCatalogName;
    private boolean shouldValidate = true;

    public AddPrimaryKeyStatement(String catalogName, String schemaName, String tableName, String columnNames, String constraintName) {
        this(catalogName, schemaName, tableName, ColumnConfig.arrayFromNames(columnNames), constraintName);
    }

    public AddPrimaryKeyStatement(String catalogName, String schemaName, String tableName, ColumnConfig[] columns, String constraintName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columns = columns;
        this.constraintName = constraintName;
    }

    public AddPrimaryKeyStatement setTablespace(String tablespace) {
        this.tablespace = tablespace;
        return this;
    }

    public String getColumnNames() {
        return StringUtil.join(this.columns, ", ", obj -> obj.getName() + (obj.getDescending() != null && obj.getDescending() != false ? " DESC" : ""));
    }

    public Boolean isClustered() {
        return this.clustered;
    }

    public AddPrimaryKeyStatement setClustered(Boolean clustered) {
        this.clustered = clustered;
        return this;
    }

    public boolean shouldValidate() {
        return this.shouldValidate;
    }

    public AddPrimaryKeyStatement setShouldValidate(boolean shouldValidate) {
        this.shouldValidate = shouldValidate;
        return this;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getTablespace() {
        return this.tablespace;
    }

    public ColumnConfig[] getColumns() {
        return this.columns;
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    public String getForIndexName() {
        return this.forIndexName;
    }

    public void setForIndexName(String forIndexName) {
        this.forIndexName = forIndexName;
    }

    public String getForIndexSchemaName() {
        return this.forIndexSchemaName;
    }

    public void setForIndexSchemaName(String forIndexSchemaName) {
        this.forIndexSchemaName = forIndexSchemaName;
    }

    public String getForIndexCatalogName() {
        return this.forIndexCatalogName;
    }

    public void setForIndexCatalogName(String forIndexCatalogName) {
        this.forIndexCatalogName = forIndexCatalogName;
    }
}

