/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.index.Cursor;
import org.h2.index.VirtualTableIndex;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.value.Value;

class VirtualTableCursor
implements Cursor {
    private final VirtualTableIndex index;
    private final SearchRow first;
    private final SearchRow last;
    private final ResultInterface result;
    Value[] values;
    Row row;

    VirtualTableCursor(VirtualTableIndex virtualTableIndex, SearchRow searchRow, SearchRow searchRow2, ResultInterface resultInterface) {
        this.index = virtualTableIndex;
        this.first = searchRow;
        this.last = searchRow2;
        this.result = resultInterface;
    }

    @Override
    public Row get() {
        if (this.values == null) {
            return null;
        }
        if (this.row == null) {
            this.row = Row.get(this.values, 1);
        }
        return this.row;
    }

    @Override
    public SearchRow getSearchRow() {
        return this.get();
    }

    @Override
    public boolean next() {
        SearchRow searchRow = this.first;
        SearchRow searchRow2 = this.last;
        if (searchRow == null && searchRow2 == null) {
            return this.nextImpl();
        }
        while (this.nextImpl()) {
            int n;
            Row row = this.get();
            if (searchRow != null && (n = this.index.compareRows(row, searchRow)) < 0 || searchRow2 != null && (n = this.index.compareRows(row, searchRow2)) > 0) continue;
            return true;
        }
        return false;
    }

    private boolean nextImpl() {
        this.row = null;
        this.values = this.result != null && this.result.next() ? this.result.currentRow() : null;
        return this.values != null;
    }

    @Override
    public boolean previous() {
        throw DbException.getInternalError(this.toString());
    }
}

