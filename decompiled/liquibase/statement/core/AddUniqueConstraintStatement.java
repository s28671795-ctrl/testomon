/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.change.ColumnConfig;
import liquibase.statement.AbstractSqlStatement;
import liquibase.util.StringUtil;

public class AddUniqueConstraintStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final ColumnConfig[] columns;
    private final String constraintName;
    private String tablespace;
    private boolean clustered;
    private boolean shouldValidate = true;
    private boolean deferrable;
    private boolean initiallyDeferred;
    private boolean disabled;
    private String forIndexName;
    private String forIndexSchemaName;
    private String forIndexCatalogName;

    public AddUniqueConstraintStatement(String catalogName, String schemaName, String tableName, ColumnConfig[] columns, String constraintName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columns = columns;
        this.constraintName = constraintName;
    }

    public String getColumnNames() {
        return StringUtil.join(this.columns, ", ", obj -> obj.getName() + (obj.getDescending() != null && obj.getDescending() != false ? " DESC" : ""));
    }

    public AddUniqueConstraintStatement setTablespace(String tablespace) {
        this.tablespace = tablespace;
        return this;
    }

    public AddUniqueConstraintStatement setDeferrable(boolean deferrable) {
        this.deferrable = deferrable;
        return this;
    }

    public AddUniqueConstraintStatement setInitiallyDeferred(boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
        return this;
    }

    public AddUniqueConstraintStatement setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public AddUniqueConstraintStatement setClustered(boolean clustered) {
        this.clustered = clustered;
        return this;
    }

    public boolean shouldValidate() {
        return this.shouldValidate;
    }

    public AddUniqueConstraintStatement setShouldValidate(boolean shouldValidate) {
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

    public ColumnConfig[] getColumns() {
        return this.columns;
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    public String getTablespace() {
        return this.tablespace;
    }

    public boolean isClustered() {
        return this.clustered;
    }

    public boolean isDeferrable() {
        return this.deferrable;
    }

    public boolean isInitiallyDeferred() {
        return this.initiallyDeferred;
    }

    public boolean isDisabled() {
        return this.disabled;
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

