/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;
import liquibase.util.ColumnParentType;

public class SetColumnRemarksStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final String columnName;
    private final String remarks;
    private final String columnDataType;
    private String columnParentType;

    public SetColumnRemarksStatement(String catalogName, String schemaName, String tableName, String columnName, String remarks) {
        this(catalogName, schemaName, tableName, columnName, remarks, null);
    }

    public SetColumnRemarksStatement(String catalogName, String schemaName, String tableName, String columnName, String remarks, String columnDataType) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.remarks = remarks;
        this.columnDataType = columnDataType;
    }

    public SetColumnRemarksStatement(String catalogName, String schemaName, String tableName, String columnName, String remarks, String columnDataType, String columnParentType) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.remarks = remarks;
        this.columnDataType = columnDataType;
        this.columnParentType = columnParentType;
    }

    public String getColumnDataType() {
        return this.columnDataType;
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

    public String getColumnName() {
        return this.columnName;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public ColumnParentType getColumnParentType() {
        return this.columnParentType != null ? ColumnParentType.valueOf(this.columnParentType) : null;
    }
}

