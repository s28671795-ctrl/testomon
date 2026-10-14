/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.Collections;
import java.util.List;
import org.h2.command.ddl.CreateTableData;
import org.h2.engine.Database;
import org.h2.index.IndexType;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.util.StringUtils;

public abstract class TableBase
extends Table {
    private final String tableEngine;
    private final List<String> tableEngineParams;
    private final boolean globalTemporary;

    public static int getMainIndexColumn(IndexType indexType, IndexColumn[] indexColumnArray) {
        if (!indexType.isPrimaryKey() || indexColumnArray.length != 1) {
            return -1;
        }
        IndexColumn indexColumn = indexColumnArray[0];
        if ((indexColumn.sortType & 1) != 0) {
            return -1;
        }
        switch (indexColumn.column.getType().getValueType()) {
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                return indexColumn.column.getColumnId();
            }
        }
        return -1;
    }

    public TableBase(CreateTableData createTableData) {
        super(createTableData.schema, createTableData.id, createTableData.tableName, createTableData.persistIndexes, createTableData.persistData);
        this.tableEngine = createTableData.tableEngine;
        this.globalTemporary = createTableData.globalTemporary;
        this.tableEngineParams = createTableData.tableEngineParams != null ? createTableData.tableEngineParams : Collections.emptyList();
        this.setTemporary(createTableData.temporary);
        this.setColumns(createTableData.columns.toArray(new Column[0]));
    }

    @Override
    public String getDropSQL() {
        StringBuilder stringBuilder = new StringBuilder("DROP TABLE IF EXISTS ");
        this.getSQL(stringBuilder, 0).append(" CASCADE");
        return stringBuilder.toString();
    }

    @Override
    public String getCreateSQLForMeta() {
        return this.getCreateSQL(true);
    }

    @Override
    public String getCreateSQL() {
        return this.getCreateSQL(false);
    }

    private String getCreateSQL(boolean bl) {
        String string;
        int n;
        Database database = this.getDatabase();
        if (database == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("CREATE ");
        if (this.isTemporary()) {
            if (this.isGlobalTemporary()) {
                stringBuilder.append("GLOBAL ");
            } else {
                stringBuilder.append("LOCAL ");
            }
            stringBuilder.append("TEMPORARY ");
        } else if (this.isPersistIndexes()) {
            stringBuilder.append("CACHED ");
        } else {
            stringBuilder.append("MEMORY ");
        }
        stringBuilder.append("TABLE ");
        if (this.isHidden) {
            stringBuilder.append("IF NOT EXISTS ");
        }
        this.getSQL(stringBuilder, 0);
        if (this.comment != null) {
            stringBuilder.append(" COMMENT ");
            StringUtils.quoteStringSQL(stringBuilder, this.comment);
        }
        stringBuilder.append("(\n    ");
        int n2 = this.columns.length;
        for (n = 0; n < n2; ++n) {
            if (n > 0) {
                stringBuilder.append(",\n    ");
            }
            stringBuilder.append(this.columns[n].getCreateSQL(bl));
        }
        stringBuilder.append("\n)");
        if (!(this.tableEngine == null || (string = database.getSettings().defaultTableEngine) != null && this.tableEngine.endsWith(string))) {
            stringBuilder.append("\nENGINE ");
            StringUtils.quoteIdentifier(stringBuilder, this.tableEngine);
        }
        if (!this.tableEngineParams.isEmpty()) {
            stringBuilder.append("\nWITH ");
            n2 = this.tableEngineParams.size();
            for (n = 0; n < n2; ++n) {
                if (n > 0) {
                    stringBuilder.append(", ");
                }
                StringUtils.quoteIdentifier(stringBuilder, this.tableEngineParams.get(n));
            }
        }
        if (!this.isPersistIndexes() && !this.isPersistData()) {
            stringBuilder.append("\nNOT PERSISTENT");
        }
        if (this.isHidden) {
            stringBuilder.append("\nHIDDEN");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean isGlobalTemporary() {
        return this.globalTemporary;
    }
}

