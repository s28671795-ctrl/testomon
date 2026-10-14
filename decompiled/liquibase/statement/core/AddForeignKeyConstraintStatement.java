/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.change.ColumnConfig;
import liquibase.statement.AbstractSqlStatement;
import liquibase.util.StringUtil;

public class AddForeignKeyConstraintStatement
extends AbstractSqlStatement {
    private final String baseTableCatalogName;
    private final String baseTableSchemaName;
    private final String baseTableName;
    private final ColumnConfig[] baseColumns;
    private final String referencedTableCatalogName;
    private final String referencedTableSchemaName;
    private final String referencedTableName;
    private final ColumnConfig[] referencedColumns;
    private final String constraintName;
    private boolean deferrable;
    private boolean initiallyDeferred;
    private boolean shouldValidate = true;
    private String onDelete;
    private String onUpdate;

    public AddForeignKeyConstraintStatement(String constraintName, String baseTableCatalogName, String baseTableSchemaName, String baseTableName, ColumnConfig[] baseColumns, String referencedTableCatalogName, String referencedTableSchemaName, String referencedTableName, ColumnConfig[] referencedColumns) {
        this.baseTableCatalogName = baseTableCatalogName;
        this.baseTableSchemaName = baseTableSchemaName;
        this.baseTableName = baseTableName;
        this.baseColumns = baseColumns;
        this.referencedTableCatalogName = referencedTableCatalogName;
        this.referencedTableSchemaName = referencedTableSchemaName;
        this.referencedTableName = referencedTableName;
        this.referencedColumns = referencedColumns;
        this.constraintName = constraintName;
    }

    public String getBaseColumnNames() {
        return StringUtil.join(this.baseColumns, ", ", ColumnConfig::getName);
    }

    public String getReferencedColumnNames() {
        return StringUtil.join(this.referencedColumns, ", ", ColumnConfig::getName);
    }

    public AddForeignKeyConstraintStatement setDeferrable(boolean deferrable) {
        this.deferrable = deferrable;
        return this;
    }

    public AddForeignKeyConstraintStatement setInitiallyDeferred(boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
        return this;
    }

    public AddForeignKeyConstraintStatement setOnUpdate(String updateRule) {
        this.onUpdate = updateRule;
        return this;
    }

    public AddForeignKeyConstraintStatement setOnDelete(String deleteRule) {
        this.onDelete = deleteRule;
        return this;
    }

    public boolean shouldValidate() {
        return this.shouldValidate;
    }

    public AddForeignKeyConstraintStatement setShouldValidate(boolean shouldValidate) {
        this.shouldValidate = shouldValidate;
        return this;
    }

    public String getBaseTableCatalogName() {
        return this.baseTableCatalogName;
    }

    public String getBaseTableSchemaName() {
        return this.baseTableSchemaName;
    }

    public String getBaseTableName() {
        return this.baseTableName;
    }

    public ColumnConfig[] getBaseColumns() {
        return this.baseColumns;
    }

    public String getReferencedTableCatalogName() {
        return this.referencedTableCatalogName;
    }

    public String getReferencedTableSchemaName() {
        return this.referencedTableSchemaName;
    }

    public String getReferencedTableName() {
        return this.referencedTableName;
    }

    public ColumnConfig[] getReferencedColumns() {
        return this.referencedColumns;
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    public boolean isDeferrable() {
        return this.deferrable;
    }

    public boolean isInitiallyDeferred() {
        return this.initiallyDeferred;
    }

    public String getOnDelete() {
        return this.onDelete;
    }

    public String getOnUpdate() {
        return this.onUpdate;
    }
}

