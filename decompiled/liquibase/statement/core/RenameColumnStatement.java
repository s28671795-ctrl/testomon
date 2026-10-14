/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class RenameColumnStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private String schemaName;
    private String tableName;
    private String oldColumnName;
    private String newColumnName;
    private String columnDataType;
    private String remarks;

    public RenameColumnStatement(String catalogName, String schemaName, String tableName, String oldColumnName, String newColumnName, String columnDataType) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.oldColumnName = oldColumnName;
        this.newColumnName = newColumnName;
        this.columnDataType = columnDataType;
    }

    public RenameColumnStatement(String catalogName, String schemaName, String tableName, String oldColumnName, String newColumnName, String columnDataType, String remarks) {
        this(catalogName, schemaName, tableName, oldColumnName, newColumnName, columnDataType);
        this.remarks = remarks;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOldColumnName() {
        return this.oldColumnName;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }

    public String getNewColumnName() {
        return this.newColumnName;
    }

    public void setNewColumnName(String newColumnName) {
        this.newColumnName = newColumnName;
    }

    public String getColumnDataType() {
        return this.columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

