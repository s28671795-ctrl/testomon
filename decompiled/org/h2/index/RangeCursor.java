/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.index.Cursor;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.value.Value;
import org.h2.value.ValueBigint;

class RangeCursor
implements Cursor {
    private boolean beforeFirst;
    private long current;
    private Row currentRow;
    private final long start;
    private final long end;
    private final long step;

    RangeCursor(long l, long l2, long l3) {
        this.start = l;
        this.end = l2;
        this.step = l3;
        this.beforeFirst = true;
    }

    @Override
    public Row get() {
        return this.currentRow;
    }

    @Override
    public SearchRow getSearchRow() {
        return this.currentRow;
    }

    @Override
    public boolean next() {
        if (this.beforeFirst) {
            this.beforeFirst = false;
            this.current = this.start;
        } else {
            this.current += this.step;
        }
        this.currentRow = Row.get(new Value[]{ValueBigint.get(this.current)}, 1);
        return this.step > 0L ? this.current <= this.end : this.current >= this.end;
    }

    @Override
    public boolean previous() {
        throw DbException.getInternalError(this.toString());
    }
}

