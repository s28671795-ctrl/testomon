/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.engine.CastDataProvider;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public abstract class SearchRow
extends Value {
    public static final int ROWID_INDEX = -1;
    public static long MATCH_ALL_ROW_KEY = -9223372036854775807L;
    public static final int MEMORY_CALCULATE = -1;
    protected long key;

    public abstract int getColumnCount();

    public boolean isNull(int n) {
        return this.getValue(n) == ValueNull.INSTANCE;
    }

    public abstract Value getValue(int var1);

    public abstract void setValue(int var1, Value var2);

    public void setKey(long l) {
        this.key = l;
    }

    public long getKey() {
        return this.key;
    }

    @Override
    public abstract int getMemory();

    public abstract void copyFrom(SearchRow var1);

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_ROW_EMPTY;
    }

    @Override
    public int getValueType() {
        return 41;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append("ROW (");
        int n2 = this.getColumnCount();
        for (int i = 0; i < n2; ++i) {
            if (i != 0) {
                stringBuilder.append(", ");
            }
            this.getValue(i).getSQL(stringBuilder, n);
        }
        return stringBuilder.append(')');
    }

    @Override
    public String getString() {
        return this.getTraceSQL();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        throw new UnsupportedOperationException();
    }
}

