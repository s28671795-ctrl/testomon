/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class RenameSequenceStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String oldSequenceName;
    private final String newSequenceName;

    public RenameSequenceStatement(String catalogName, String schemaName, String oldSequenceName, String newSequenceName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.oldSequenceName = oldSequenceName;
        this.newSequenceName = newSequenceName;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getOldSequenceName() {
        return this.oldSequenceName;
    }

    public String getNewSequenceName() {
        return this.newSequenceName;
    }
}

