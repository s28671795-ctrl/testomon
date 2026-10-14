/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.index.Cursor;
import org.h2.index.QueryExpressionIndex;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.table.Table;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public class QueryExpressionCursor
implements Cursor {
    private final Table table;
    private final QueryExpressionIndex index;
    private final ResultInterface result;
    private final SearchRow first;
    private final SearchRow last;
    private Row current;

    public QueryExpressionCursor(QueryExpressionIndex queryExpressionIndex, ResultInterface resultInterface, SearchRow searchRow, SearchRow searchRow2) {
        this.table = queryExpressionIndex.getTable();
        this.index = queryExpressionIndex;
        this.result = resultInterface;
        this.first = searchRow;
        this.last = searchRow2;
    }

    @Override
    public Row get() {
        return this.current;
    }

    @Override
    public SearchRow getSearchRow() {
        return this.current;
    }

    @Override
    public boolean next() {
        int n;
        do {
            boolean bl;
            if (!(bl = this.result.next())) {
                if (this.index.isRecursive()) {
                    this.result.reset();
                } else {
                    this.result.close();
                }
                this.current = null;
                return false;
            }
            this.current = this.table.getTemplateRow();
            Value[] valueArray = this.result.currentRow();
            int n2 = this.current.getColumnCount();
            for (n = 0; n < n2; ++n) {
                ValueNull valueNull = n < valueArray.length ? valueArray[n] : ValueNull.INSTANCE;
                this.current.setValue(n, valueNull);
            }
        } while (this.first != null && (n = this.index.compareRows(this.current, this.first)) < 0 || this.last != null && (n = this.index.compareRows(this.current, this.last)) > 0);
        return true;
    }

    @Override
    public boolean previous() {
        throw DbException.getInternalError(this.toString());
    }
}

