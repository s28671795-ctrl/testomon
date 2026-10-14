/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import java.util.ArrayList;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.index.IndexCondition;
import org.h2.index.SpatialIndex;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.value.Value;
import org.h2.value.ValueGeometry;
import org.h2.value.ValueNull;

public class IndexCursor
implements Cursor {
    private SessionLocal session;
    private Index index;
    private Table table;
    private IndexColumn[] indexColumns;
    private boolean alwaysFalse;
    private SearchRow start;
    private SearchRow end;
    private SearchRow intersects;
    private Cursor cursor;
    private Column inColumn;
    private int inListIndex;
    private Value[] inList;
    private ResultInterface inResult;

    public void setIndex(Index index) {
        this.index = index;
        this.table = index.getTable();
        Column[] columnArray = this.table.getColumns();
        this.indexColumns = new IndexColumn[columnArray.length];
        IndexColumn[] indexColumnArray = index.getIndexColumns();
        if (indexColumnArray != null) {
            int n = columnArray.length;
            for (int i = 0; i < n; ++i) {
                int n2 = index.getColumnIndex(columnArray[i]);
                if (n2 < 0) continue;
                this.indexColumns[i] = indexColumnArray[n2];
            }
        }
    }

    public void prepare(SessionLocal sessionLocal, ArrayList<IndexCondition> arrayList) {
        this.session = sessionLocal;
        this.alwaysFalse = false;
        this.end = null;
        this.start = null;
        this.inList = null;
        this.inColumn = null;
        this.inResult = null;
        this.intersects = null;
        block4: for (IndexCondition indexCondition : arrayList) {
            IndexColumn indexColumn;
            if (indexCondition.isAlwaysFalse()) {
                this.alwaysFalse = true;
                break;
            }
            if (this.index.isFindUsingFullTableScan()) continue;
            Column column = indexCondition.getColumn();
            switch (indexCondition.getCompareType()) {
                case 10: 
                case 11: {
                    if (this.start != null || this.end != null || !this.canUseIndexForIn(column)) continue block4;
                    this.inColumn = column;
                    this.inList = indexCondition.getCurrentValueList(sessionLocal);
                    this.inListIndex = 0;
                    continue block4;
                }
                case 12: {
                    if (this.start != null || this.end != null || !this.canUseIndexForIn(column)) continue block4;
                    this.inColumn = column;
                    this.inResult = indexCondition.getCurrentResult();
                    continue block4;
                }
            }
            Value value = indexCondition.getCurrentValue(sessionLocal);
            boolean bl = indexCondition.isStart();
            boolean bl2 = indexCondition.isEnd();
            boolean bl3 = indexCondition.isSpatialIntersects();
            int n = column.getColumnId();
            if (n != -1 && (indexColumn = this.indexColumns[n]) != null && (indexColumn.sortType & 1) != 0) {
                boolean bl4 = bl;
                bl = bl2;
                bl2 = bl4;
            }
            if (bl) {
                this.start = this.getSearchRow(this.start, n, value, true);
            }
            if (bl2) {
                this.end = this.getSearchRow(this.end, n, value, false);
            }
            if (bl3) {
                this.intersects = this.getSpatialSearchRow(this.intersects, n, value);
            }
            if (!bl && !bl2 || this.canUseIndexFor(this.inColumn)) continue;
            this.inColumn = null;
            this.inList = null;
            this.inResult = null;
        }
        if (this.inColumn != null) {
            this.start = this.table.getTemplateRow();
        }
    }

    public void find(SessionLocal sessionLocal, ArrayList<IndexCondition> arrayList) {
        this.prepare(sessionLocal, arrayList);
        if (this.inColumn != null) {
            return;
        }
        if (!this.alwaysFalse) {
            if (this.intersects != null && this.index instanceof SpatialIndex) {
                this.cursor = ((SpatialIndex)((Object)this.index)).findByGeometry(this.session, this.start, this.end, this.intersects);
            } else if (this.index != null) {
                this.cursor = this.index.find(this.session, this.start, this.end);
            }
        }
    }

    private boolean canUseIndexForIn(Column column) {
        if (this.inColumn != null) {
            return false;
        }
        return this.canUseIndexFor(column);
    }

    private boolean canUseIndexFor(Column column) {
        IndexColumn[] indexColumnArray = this.index.getIndexColumns();
        if (indexColumnArray == null) {
            return true;
        }
        IndexColumn indexColumn = indexColumnArray[0];
        return indexColumn == null || indexColumn.column == column;
    }

    private SearchRow getSpatialSearchRow(SearchRow searchRow, int n, Value value) {
        if (searchRow == null) {
            searchRow = this.table.getTemplateRow();
        } else if (searchRow.getValue(n) != null) {
            ValueGeometry valueGeometry = searchRow.getValue(n).convertToGeometry(null);
            value = value.convertToGeometry(null).getEnvelopeUnion(valueGeometry);
        }
        if (n == -1) {
            searchRow.setKey(value == ValueNull.INSTANCE ? Long.MIN_VALUE : value.getLong());
        } else {
            searchRow.setValue(n, value);
        }
        return searchRow;
    }

    private SearchRow getSearchRow(SearchRow searchRow, int n, Value value, boolean bl) {
        if (searchRow == null) {
            searchRow = this.table.getTemplateRow();
        } else {
            value = this.getMax(searchRow.getValue(n), value, bl);
        }
        if (n == -1) {
            searchRow.setKey(value == ValueNull.INSTANCE ? Long.MIN_VALUE : value.getLong());
        } else {
            searchRow.setValue(n, value);
        }
        return searchRow;
    }

    private Value getMax(Value value, Value value2, boolean bl) {
        if (value == null) {
            return value2;
        }
        if (value2 == null) {
            return value;
        }
        if (value == ValueNull.INSTANCE) {
            return value2;
        }
        if (value2 == ValueNull.INSTANCE) {
            return value;
        }
        int n = this.session.compare(value, value2);
        if (n == 0) {
            return value;
        }
        return n > 0 == bl ? value : value2;
    }

    public boolean isAlwaysFalse() {
        return this.alwaysFalse;
    }

    public SearchRow getStart() {
        return this.start;
    }

    public SearchRow getEnd() {
        return this.end;
    }

    @Override
    public Row get() {
        if (this.cursor == null) {
            return null;
        }
        return this.cursor.get();
    }

    @Override
    public SearchRow getSearchRow() {
        return this.cursor.getSearchRow();
    }

    @Override
    public boolean next() {
        while (true) {
            if (this.cursor == null) {
                this.nextCursor();
                if (this.cursor == null) {
                    return false;
                }
            }
            if (this.cursor.next()) {
                return true;
            }
            this.cursor = null;
        }
    }

    private void nextCursor() {
        block2: {
            block3: {
                if (this.inList == null) break block3;
                while (this.inListIndex < this.inList.length) {
                    Value value;
                    if ((value = this.inList[this.inListIndex++]) == ValueNull.INSTANCE) continue;
                    this.find(value);
                    break block2;
                }
                break block2;
            }
            if (this.inResult == null) break block2;
            while (this.inResult.next()) {
                Value value = this.inResult.currentRow()[0];
                if (value == ValueNull.INSTANCE) continue;
                this.find(value);
                break;
            }
        }
    }

    private void find(Value value) {
        value = this.inColumn.convert(this.session, value);
        int n = this.inColumn.getColumnId();
        this.start.setValue(n, value);
        this.cursor = this.index.find(this.session, this.start, this.start);
    }

    @Override
    public boolean previous() {
        throw DbException.getInternalError(this.toString());
    }
}

