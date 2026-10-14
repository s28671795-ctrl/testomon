/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class CreateViewStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String viewName;
    private final String selectQuery;
    private final boolean replaceIfExists;
    private boolean fullDefinition;

    public CreateViewStatement(String catalogName, String schemaName, String viewName, String selectQuery, boolean replaceIfExists) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.viewName = viewName;
        this.selectQuery = selectQuery;
        this.replaceIfExists = replaceIfExists;
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

    public String getSelectQuery() {
        return this.selectQuery;
    }

    public boolean isReplaceIfExists() {
        return this.replaceIfExists;
    }

    public boolean isFullDefinition() {
        return this.fullDefinition;
    }

    public CreateViewStatement setFullDefinition(boolean fullDefinition) {
        this.fullDefinition = fullDefinition;
        return this;
    }
}

