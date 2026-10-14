/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class DropDefaultValueStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final String columnName;
    private final String columnDataType;

    public DropDefaultValueStatement(String catalogName, String schemaName, String tableName, String columnName, String columnDataType) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnDataType = columnDataType;
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

    public String getColumnDataType() {
        return this.columnDataType;
    }
}

