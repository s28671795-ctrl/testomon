/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.change.ColumnConfig;
import liquibase.statement.AbstractSqlStatement;

public class DropUniqueConstraintStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final String constraintName;
    private ColumnConfig[] uniqueColumns;

    public DropUniqueConstraintStatement(String catalogName, String schemaName, String tableName, String constraintName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.constraintName = constraintName;
    }

    public DropUniqueConstraintStatement(String catalogName, String schemaName, String tableName, String constraintName, ColumnConfig[] uniqueColumns) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.constraintName = constraintName;
        this.uniqueColumns = uniqueColumns;
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

    public String getConstraintName() {
        return this.constraintName;
    }

    public ColumnConfig[] getUniqueColumns() {
        return this.uniqueColumns;
    }

    public void setUniqueColumns(ColumnConfig[] uniqueColumns) {
        this.uniqueColumns = uniqueColumns;
    }
}

