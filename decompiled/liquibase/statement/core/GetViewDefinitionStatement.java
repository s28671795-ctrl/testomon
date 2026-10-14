/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class GetViewDefinitionStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String viewName;

    public GetViewDefinitionStatement(String catalogName, String schemaName, String viewName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.viewName = viewName;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getViewName() {
        return this.viewName;
    }
}

