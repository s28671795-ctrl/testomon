/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.index.Cursor;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.value.Value;

class DualCursor
implements Cursor {
    private Row currentRow;

    DualCursor() {
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
        if (this.currentRow == null) {
            this.currentRow = Row.get(Value.EMPTY_VALUES, 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean previous() {
        throw DbException.getInternalError(this.toString());
    }
}

