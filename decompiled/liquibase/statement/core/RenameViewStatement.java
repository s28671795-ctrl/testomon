/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class RenameViewStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String oldViewName;
    private final String newViewName;

    public RenameViewStatement(String catalogName, String schemaName, String oldViewName, String newViewName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.oldViewName = oldViewName;
        this.newViewName = newViewName;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getOldViewName() {
        return this.oldViewName;
    }

    public String getNewViewName() {
        return this.newViewName;
    }
}

