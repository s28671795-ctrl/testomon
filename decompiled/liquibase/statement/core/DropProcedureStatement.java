/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class DropProcedureStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String procedureName;

    public DropProcedureStatement(String catalogName, String schemaName, String procedureName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.procedureName = procedureName;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getProcedureName() {
        return this.procedureName;
    }
}

