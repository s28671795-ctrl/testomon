/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.result.SearchRow;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueNull;

public class SimpleRowValue
extends SearchRow {
    private int index;
    private final int virtualColumnCount;
    private Value data;

    public SimpleRowValue(int n) {
        this.virtualColumnCount = n;
    }

    public SimpleRowValue(int n, int n2) {
        this.virtualColumnCount = n;
        this.index = n2;
    }

    @Override
    public int getColumnCount() {
        return this.virtualColumnCount;
    }

    @Override
    public Value getValue(int n) {
        if (n == -1) {
            return ValueBigint.get(this.getKey());
        }
        return n == this.index ? this.data : null;
    }

    @Override
    public void setValue(int n, Value value) {
        if (n == -1) {
            this.setKey(value.getLong());
        }
        this.index = n;
        this.data = value;
    }

    @Override
    public String toString() {
        return "( /* " + this.key + " */ " + (this.data == null ? "null" : this.data.getTraceSQL()) + " )";
    }

    @Override
    public int getMemory() {
        return 40 + (this.data == null ? 0 : this.data.getMemory());
    }

    @Override
    public boolean isNull(int n) {
        return n != this.index || this.data == null || this.data == ValueNull.INSTANCE;
    }

    @Override
    public void copyFrom(SearchRow searchRow) {
        this.setKey(searchRow.getKey());
        this.setValue(this.index, searchRow.getValue(this.index));
    }
}

