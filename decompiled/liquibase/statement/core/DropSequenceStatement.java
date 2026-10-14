/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class DropSequenceStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String sequenceName;

    public DropSequenceStatement(String catalogName, String schemaName, String sequenceName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.sequenceName = sequenceName;
    }

    @Override
    public boolean skipOnUnsupported() {
        return true;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }
}

