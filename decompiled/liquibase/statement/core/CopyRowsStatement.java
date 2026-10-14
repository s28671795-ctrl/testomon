/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.List;
import liquibase.change.ColumnConfig;
import liquibase.statement.AbstractSqlStatement;

public class CopyRowsStatement
extends AbstractSqlStatement {
    private final String sourceTable;
    private final String targetTable;
    private final List<ColumnConfig> copyColumns;

    public CopyRowsStatement(String sourceTable, String targetTable, List<ColumnConfig> copyColumns) {
        this.sourceTable = sourceTable;
        this.targetTable = targetTable;
        this.copyColumns = copyColumns;
    }

    public String getSourceTable() {
        return this.sourceTable;
    }

    public String getTargetTable() {
        return this.targetTable;
    }

    public List<ColumnConfig> getCopyColumns() {
        return this.copyColumns;
    }
}

