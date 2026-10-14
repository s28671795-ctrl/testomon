/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class TableIsEmptyStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public TableIsEmptyStatement(String catalogName, String schemaName, String tableName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
    }
}

