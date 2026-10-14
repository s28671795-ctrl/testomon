/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.result.DefaultRow;
import org.h2.result.SearchRow;
import org.h2.value.Value;
import org.h2.value.ValueBigint;

public final class Sparse
extends DefaultRow {
    private final int columnCount;
    private final int[] map;

    Sparse(int n, int n2, int[] nArray) {
        super(new Value[n2]);
        this.columnCount = n;
        this.map = nArray;
    }

    @Override
    public int getColumnCount() {
        return this.columnCount;
    }

    @Override
    public Value getValue(int n) {
        if (n == -1) {
            return ValueBigint.get(this.getKey());
        }
        int n2 = this.map[n];
        return n2 > 0 ? super.getValue(n2 - 1) : null;
    }

    @Override
    public void setValue(int n, Value value) {
        int n2;
        if (n == -1) {
            this.setKey(value.getLong());
        }
        if ((n2 = this.map[n]) > 0) {
            super.setValue(n2 - 1, value);
        }
    }

    @Override
    public void copyFrom(SearchRow searchRow) {
        this.setKey(searchRow.getKey());
        for (int i = 0; i < this.map.length; ++i) {
            int n = this.map[i];
            if (n <= 0) continue;
            super.setValue(n - 1, searchRow.getValue(i));
        }
    }
}

