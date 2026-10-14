/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class SetNullableStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final String columnName;
    private final String columnDataType;
    private final boolean nullable;
    private String constraintName;
    private boolean validate = true;

    public SetNullableStatement(String catalogName, String schemaName, String tableName, String columnName, String columnDataType, boolean nullable) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnDataType = columnDataType;
        this.nullable = nullable;
    }

    public SetNullableStatement(String catalogName, String schemaName, String tableName, String columnName, String columnDataType, boolean nullable, String constraintName) {
        this(catalogName, schemaName, tableName, columnName, columnDataType, nullable);
        this.constraintName = constraintName;
    }

    public SetNullableStatement(String catalogName, String schemaName, String tableName, String columnName, String columnDataType, boolean nullable, String constraintName, Boolean validate) {
        this(catalogName, schemaName, tableName, columnName, columnDataType, nullable);
        this.constraintName = constraintName;
        if (validate != null) {
            this.validate = validate;
        }
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

    public boolean isNullable() {
        return this.nullable;
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    public boolean isValidate() {
        return this.validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }
}

