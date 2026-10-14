/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import java.util.ArrayList;
import org.h2.index.Cursor;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;

public class MetaCursor
implements Cursor {
    private Row current;
    private final ArrayList<Row> rows;
    private int index;

    MetaCursor(ArrayList<Row> arrayList) {
        this.rows = arrayList;
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
        this.current = this.index >= this.rows.size() ? null : this.rows.get(this.index++);
        return this.current != null;
    }

    @Override
    public boolean previous() {
        throw DbException.getInternalError(this.toString());
    }
}

