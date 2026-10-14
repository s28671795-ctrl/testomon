/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.util.ParserUtil;

public class IndexColumn {
    public static final int SQL_NO_ORDER = Integer.MIN_VALUE;
    public final String columnName;
    public Column column;
    public int sortType = 0;

    public static StringBuilder writeColumns(StringBuilder stringBuilder, IndexColumn[] indexColumnArray, int n) {
        return IndexColumn.writeColumns(stringBuilder, indexColumnArray, 0, indexColumnArray.length, n);
    }

    public static StringBuilder writeColumns(StringBuilder stringBuilder, IndexColumn[] indexColumnArray, int n, int n2, int n3) {
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(", ");
            }
            indexColumnArray[i].getSQL(stringBuilder, n3);
        }
        return stringBuilder;
    }

    public static StringBuilder writeColumns(StringBuilder stringBuilder, IndexColumn[] indexColumnArray, String string, String string2, int n) {
        int n2 = indexColumnArray.length;
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                stringBuilder.append(string);
            }
            indexColumnArray[i].getSQL(stringBuilder, n).append(string2);
        }
        return stringBuilder;
    }

    public IndexColumn(String string) {
        this.columnName = string;
    }

    public IndexColumn(String string, int n) {
        this.columnName = string;
        this.sortType = n;
    }

    public IndexColumn(Column column) {
        this.columnName = null;
        this.column = column;
    }

    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if (this.column != null) {
            this.column.getSQL(stringBuilder, n);
        } else {
            ParserUtil.quoteIdentifier(stringBuilder, this.columnName, n);
        }
        if ((n & Integer.MIN_VALUE) == 0) {
            SortOrder.typeToString(stringBuilder, this.sortType);
        }
        return stringBuilder;
    }

    public static IndexColumn[] wrap(Column[] columnArray) {
        IndexColumn[] indexColumnArray = new IndexColumn[columnArray.length];
        for (int i = 0; i < indexColumnArray.length; ++i) {
            indexColumnArray[i] = new IndexColumn(columnArray[i]);
        }
        return indexColumnArray;
    }

    public static void mapColumns(IndexColumn[] indexColumnArray, Table table) {
        for (IndexColumn indexColumn : indexColumnArray) {
            indexColumn.column = table.getColumn(indexColumn.columnName);
        }
    }

    public String toString() {
        return this.getSQL(new StringBuilder("IndexColumn "), 3).toString();
    }
}

