/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class DropForeignKeyConstraintStatement
extends AbstractSqlStatement {
    private final String baseTableCatalogName;
    private final String baseTableSchemaName;
    private final String baseTableName;
    private final String constraintName;

    public DropForeignKeyConstraintStatement(String baseTableCatalogName, String baseTableSchemaName, String baseTableName, String constraintName) {
        this.baseTableCatalogName = baseTableCatalogName;
        this.baseTableSchemaName = baseTableSchemaName;
        this.baseTableName = baseTableName;
        this.constraintName = constraintName;
    }

    public String getBaseTableCatalogName() {
        return this.baseTableCatalogName;
    }

    public String getBaseTableSchemaName() {
        return this.baseTableSchemaName;
    }

    public String getBaseTableName() {
        return this.baseTableName;
    }

    public String getConstraintName() {
        return this.constraintName;
    }
}

