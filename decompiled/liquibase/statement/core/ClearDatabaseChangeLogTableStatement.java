/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class ClearDatabaseChangeLogTableStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;

    public ClearDatabaseChangeLogTableStatement(String catalogName, String schemaName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }
}

