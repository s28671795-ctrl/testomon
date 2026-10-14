/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.statement.AbstractSqlStatement;

public class DropColumnStatement
extends AbstractSqlStatement {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private final List<DropColumnStatement> columns = new ArrayList<DropColumnStatement>();

    public DropColumnStatement(String catalogName, String schemaName, String tableName, String columnName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public DropColumnStatement(List<DropColumnStatement> columns) {
        this.columns.addAll(columns);
    }

    public boolean isMultiple() {
        return !this.columns.isEmpty();
    }

    public List<DropColumnStatement> getColumns() {
        return this.columns;
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
}

