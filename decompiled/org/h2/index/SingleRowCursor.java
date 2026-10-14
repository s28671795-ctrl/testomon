/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.index.Cursor;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;

public class SingleRowCursor
implements Cursor {
    private Row row;
    private boolean end;

    public SingleRowCursor(Row row) {
        this.row = row;
    }

    @Override
    public Row get() {
        return this.row;
    }

    @Override
    public SearchRow getSearchRow() {
        return this.row;
    }

    @Override
    public boolean next() {
        if (this.row == null || this.end) {
            this.row = null;
            return false;
        }
        this.end = true;
        return true;
    }

    @Override
    public boolean previous() {
        throw DbException.getInternalError(this.toString());
    }
}

