/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.engine.Session;
import org.h2.result.ResultInterface;
import org.h2.value.Value;

public abstract class FetchedResult
implements ResultInterface {
    long rowId = -1L;
    Value[] currentRow;
    Value[] nextRow;
    boolean afterLast;

    FetchedResult() {
    }

    @Override
    public final Value[] currentRow() {
        return this.currentRow;
    }

    @Override
    public final boolean next() {
        if (this.hasNext()) {
            ++this.rowId;
            this.currentRow = this.nextRow;
            this.nextRow = null;
            return true;
        }
        if (!this.afterLast) {
            ++this.rowId;
            this.currentRow = null;
            this.afterLast = true;
        }
        return false;
    }

    @Override
    public final boolean isAfterLast() {
        return this.afterLast;
    }

    @Override
    public final long getRowId() {
        return this.rowId;
    }

    @Override
    public final boolean needToClose() {
        return true;
    }

    @Override
    public final ResultInterface createShallowCopy(Session session) {
        return null;
    }
}

