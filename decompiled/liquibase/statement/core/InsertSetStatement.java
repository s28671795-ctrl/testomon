/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.LinkedList;
import java.util.List;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.core.InsertStatement;

public class InsertSetStatement
extends AbstractSqlStatement {
    private final LinkedList<InsertStatement> inserts = new LinkedList();
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final int batchSize;

    public InsertSetStatement(String catalogName, String schemaName, String tableName) {
        this(catalogName, schemaName, tableName, 50);
    }

    public InsertSetStatement(String catalogName, String schemaName, String tableName, int batchSize) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.batchSize = batchSize;
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

    public int getBatchThreshold() {
        return this.batchSize;
    }

    public InsertSetStatement addInsertStatement(InsertStatement statement) {
        this.inserts.add(statement);
        return this;
    }

    public InsertStatement peek() {
        return this.inserts.peek();
    }

    public List<InsertStatement> getStatements() {
        return this.inserts;
    }

    public InsertStatement[] getStatementsArray() {
        return this.inserts.toArray(new InsertStatement[0]);
    }
}

