/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.util.ArrayList;
import java.util.Comparator;
import org.h2.engine.Session;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class SimpleResult
implements ResultInterface,
ResultTarget {
    private final ArrayList<Column> columns;
    private final ArrayList<Value[]> rows;
    private final String schemaName;
    private final String tableName;
    private int rowId;

    public SimpleResult() {
        this("", "");
    }

    public SimpleResult(String string, String string2) {
        this.columns = Utils.newSmallArrayList();
        this.rows = new ArrayList();
        this.schemaName = string;
        this.tableName = string2;
        this.rowId = -1;
    }

    private SimpleResult(ArrayList<Column> arrayList, ArrayList<Value[]> arrayList2, String string, String string2) {
        this.columns = arrayList;
        this.rows = arrayList2;
        this.schemaName = string;
        this.tableName = string2;
        this.rowId = -1;
    }

    public void addColumn(String string, String string2, int n, long l, int n2) {
        this.addColumn(string, string2, TypeInfo.getTypeInfo(n, l, n2, null));
    }

    public void addColumn(String string, TypeInfo typeInfo) {
        this.addColumn(new Column(string, string, typeInfo));
    }

    public void addColumn(String string, String string2, TypeInfo typeInfo) {
        this.addColumn(new Column(string, string2, typeInfo));
    }

    void addColumn(Column column) {
        assert (this.rows.isEmpty());
        this.columns.add(column);
    }

    @Override
    public void addRow(Value ... valueArray) {
        assert (valueArray.length == this.columns.size());
        this.rows.add(valueArray);
    }

    @Override
    public void reset() {
        this.rowId = -1;
    }

    @Override
    public Value[] currentRow() {
        return this.rows.get(this.rowId);
    }

    @Override
    public boolean next() {
        int n = this.rows.size();
        if (this.rowId < n) {
            return ++this.rowId < n;
        }
        return false;
    }

    @Override
    public long getRowId() {
        return this.rowId;
    }

    @Override
    public boolean isAfterLast() {
        return this.rowId >= this.rows.size();
    }

    @Override
    public int getVisibleColumnCount() {
        return this.columns.size();
    }

    @Override
    public long getRowCount() {
        return this.rows.size();
    }

    @Override
    public boolean hasNext() {
        return this.rowId < this.rows.size() - 1;
    }

    @Override
    public boolean needToClose() {
        return false;
    }

    @Override
    public void close() {
    }

    @Override
    public String getAlias(int n) {
        return this.columns.get((int)n).alias;
    }

    @Override
    public String getSchemaName(int n) {
        return this.schemaName;
    }

    @Override
    public String getTableName(int n) {
        return this.tableName;
    }

    @Override
    public String getColumnName(int n) {
        return this.columns.get((int)n).columnName;
    }

    @Override
    public TypeInfo getColumnType(int n) {
        return this.columns.get((int)n).columnType;
    }

    @Override
    public boolean isIdentity(int n) {
        return false;
    }

    @Override
    public int getNullable(int n) {
        return 2;
    }

    @Override
    public void setFetchSize(int n) {
    }

    @Override
    public int getFetchSize() {
        return 1;
    }

    @Override
    public boolean isLazy() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public SimpleResult createShallowCopy(Session session) {
        return new SimpleResult(this.columns, this.rows, this.schemaName, this.tableName);
    }

    @Override
    public void limitsWereApplied() {
    }

    public void sortRows(Comparator<? super Value[]> comparator) {
        this.rows.sort(comparator);
    }

    static final class Column {
        final String alias;
        final String columnName;
        final TypeInfo columnType;

        Column(String string, String string2, TypeInfo typeInfo) {
            if (string == null || string2 == null) {
                throw new NullPointerException();
            }
            this.alias = string;
            this.columnName = string2;
            this.columnType = typeInfo;
        }

        public int hashCode() {
            int n = 1;
            n = 31 * n + this.alias.hashCode();
            n = 31 * n + this.columnName.hashCode();
            return n;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            Column column = (Column)object;
            return this.alias.equals(column.alias) && this.columnName.equals(column.columnName);
        }

        public String toString() {
            if (this.alias.equals(this.columnName)) {
                return this.columnName;
            }
            return this.columnName + ' ' + this.alias;
        }
    }
}

