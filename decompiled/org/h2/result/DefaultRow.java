/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.value.Value;
import org.h2.value.ValueBigint;

public class DefaultRow
extends Row {
    public static final int MEMORY_CALCULATE = -1;
    protected final Value[] data;
    private int memory;

    DefaultRow(int n) {
        this.data = new Value[n];
        this.memory = -1;
    }

    public DefaultRow(Value[] valueArray) {
        this.data = valueArray;
        this.memory = -1;
    }

    public DefaultRow(Value[] valueArray, int n) {
        this.data = valueArray;
        this.memory = n;
    }

    @Override
    public Value getValue(int n) {
        return n == -1 ? ValueBigint.get(this.key) : this.data[n];
    }

    @Override
    public void setValue(int n, Value value) {
        if (n == -1) {
            this.key = value.getLong();
        } else {
            this.data[n] = value;
        }
    }

    @Override
    public int getColumnCount() {
        return this.data.length;
    }

    @Override
    public int getMemory() {
        if (this.memory != -1) {
            return this.memory;
        }
        this.memory = this.calculateMemory();
        return this.memory;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("( /* key:").append(this.key).append(" */ ");
        int n = this.data.length;
        for (int i = 0; i < n; ++i) {
            Value value;
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append((value = this.data[i]) == null ? "null" : value.getTraceSQL());
        }
        return stringBuilder.append(')').toString();
    }

    protected int calculateMemory() {
        int n = 64 + this.data.length * 8;
        for (Value value : this.data) {
            if (value == null) continue;
            n += value.getMemory();
        }
        return n;
    }

    @Override
    public Value[] getValueList() {
        return this.data;
    }

    @Override
    public boolean hasSharedData(Row row) {
        return row instanceof DefaultRow && this.data == ((DefaultRow)row).data;
    }

    @Override
    public void copyFrom(SearchRow searchRow) {
        this.setKey(searchRow.getKey());
        for (int i = 0; i < this.getColumnCount(); ++i) {
            this.setValue(i, searchRow.getValue(i));
        }
    }
}

