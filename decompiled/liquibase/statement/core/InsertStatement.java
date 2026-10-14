/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.LinkedHashMap;
import java.util.Map;
import liquibase.change.ColumnConfig;
import liquibase.statement.AbstractSqlStatement;

public class InsertStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final Map<String, Object> columnValues = new LinkedHashMap<String, Object>();

    public InsertStatement(String catalogName, String schemaName, String tableName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
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

    public InsertStatement addColumnValue(String columnName, Object newValue) {
        this.columnValues.put(columnName, newValue);
        return this;
    }

    public Object getColumnValue(String columnName) {
        return this.columnValues.get(columnName);
    }

    public Map<String, Object> getColumnValues() {
        return this.columnValues;
    }

    public InsertStatement addColumn(ColumnConfig columnConfig) {
        return this.addColumnValue(columnConfig.getName(), columnConfig.getValueObject());
    }
}

