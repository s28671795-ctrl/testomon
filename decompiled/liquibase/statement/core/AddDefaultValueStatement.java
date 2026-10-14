/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class AddDefaultValueStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final String columnName;
    private final String columnDataType;
    private final Object defaultValue;
    private String defaultValueConstraintName;

    public AddDefaultValueStatement(String catalogName, String schemaName, String tableName, String columnName, String columnDataType) {
        this(catalogName, schemaName, tableName, columnName, columnDataType, null);
    }

    public AddDefaultValueStatement(String catalogName, String schemaName, String tableName, String columnName, String columnDataType, Object defaultValue) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnDataType = columnDataType;
        this.defaultValue = defaultValue;
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

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public String getDefaultValueConstraintName() {
        return this.defaultValueConstraintName;
    }

    public void setDefaultValueConstraintName(String defaultValueConstraintName) {
        this.defaultValueConstraintName = defaultValueConstraintName;
    }
}

