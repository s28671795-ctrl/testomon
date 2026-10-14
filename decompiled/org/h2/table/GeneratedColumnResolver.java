/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.HashMap;
import org.h2.result.Row;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.Table;
import org.h2.value.Value;
import org.h2.value.ValueBigint;

class GeneratedColumnResolver
implements ColumnResolver {
    private final Table table;
    private Column[] columns;
    private HashMap<String, Column> columnMap;
    private Row current;

    GeneratedColumnResolver(Table table) {
        this.table = table;
    }

    void set(Row row) {
        this.current = row;
    }

    @Override
    public Column[] getColumns() {
        Column[] columnArray = this.columns;
        if (columnArray == null) {
            columnArray = this.createColumns();
            this.columns = columnArray;
        }
        return columnArray;
    }

    private Column[] createColumns() {
        int n;
        Column[] columnArray = this.table.getColumns();
        int n2 = n = columnArray.length;
        for (int i = 0; i < n; ++i) {
            if (!columnArray[i].isGenerated()) continue;
            --n2;
        }
        Column[] columnArray2 = new Column[n2];
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            Column column = columnArray[i];
            if (column.isGenerated()) continue;
            columnArray2[n3++] = column;
        }
        return columnArray2;
    }

    @Override
    public Column findColumn(String string) {
        HashMap<String, Column> hashMap = this.columnMap;
        if (hashMap == null) {
            hashMap = this.table.getDatabase().newStringMap();
            for (Column column : this.getColumns()) {
                hashMap.put(column.getName(), column);
            }
            this.columnMap = hashMap;
        }
        return hashMap.get(string);
    }

    @Override
    public Value getValue(Column column) {
        int n = column.getColumnId();
        if (n == -1) {
            return ValueBigint.get(this.current.getKey());
        }
        return this.current.getValue(n);
    }

    @Override
    public Column getRowIdColumn() {
        return this.table.getRowIdColumn();
    }
}

