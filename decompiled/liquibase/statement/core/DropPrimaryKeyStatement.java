/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class DropPrimaryKeyStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final String constraintName;
    private Boolean dropIndex;

    public DropPrimaryKeyStatement(String catalogName, String schemaName, String tableName, String constraintName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.constraintName = constraintName;
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

    public String getConstraintName() {
        return this.constraintName;
    }

    public Boolean getDropIndex() {
        return this.dropIndex;
    }

    public void setDropIndex(Boolean dropIndex) {
        this.dropIndex = dropIndex;
    }
}

