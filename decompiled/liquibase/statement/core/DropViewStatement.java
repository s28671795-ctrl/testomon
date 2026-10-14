/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class DropViewStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String viewName;
    private final Boolean ifExists;

    public DropViewStatement(String catalogName, String schemaName, String viewName) {
        this(catalogName, schemaName, viewName, null);
    }

    public DropViewStatement(String catalogName, String schemaName, String viewName, Boolean ifExists) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.viewName = viewName;
        this.ifExists = ifExists;
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

    public Boolean isIfExists() {
        return this.ifExists;
    }
}

