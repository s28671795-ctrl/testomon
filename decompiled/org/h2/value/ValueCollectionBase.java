/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.value.CompareMode;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public abstract class ValueCollectionBase
extends Value {
    final Value[] values;
    private int hash;

    ValueCollectionBase(Value[] valueArray) {
        this.values = valueArray;
    }

    public Value[] getList() {
        return this.values;
    }

    @Override
    public int hashCode() {
        if (this.hash != 0) {
            return this.hash;
        }
        int n = this.getValueType();
        for (Value value : this.values) {
            n = n * 31 + value.hashCode();
        }
        this.hash = n;
        return n;
    }

    @Override
    public int compareWithNull(Value value, boolean bl, CastDataProvider castDataProvider, CompareMode compareMode) {
        if (value == ValueNull.INSTANCE) {
            return Integer.MIN_VALUE;
        }
        ValueCollectionBase valueCollectionBase = this;
        int n = valueCollectionBase.getValueType();
        int n2 = value.getValueType();
        if (n2 != n) {
            throw value.getDataConversionError(n);
        }
        ValueCollectionBase valueCollectionBase2 = (ValueCollectionBase)value;
        Value[] valueArray = valueCollectionBase.values;
        int n3 = valueArray.length;
        Value[] valueArray2 = valueCollectionBase2.values;
        int n4 = valueArray2.length;
        if (n3 != n4) {
            if (n == 41) {
                throw DbException.get(21002);
            }
            if (bl) {
                return 1;
            }
        }
        if (bl) {
            boolean bl2 = false;
            for (int i = 0; i < n3; ++i) {
                Value value2 = valueArray[i];
                Value value3 = valueArray2[i];
                int n5 = value2.compareWithNull(value3, bl, castDataProvider, compareMode);
                if (n5 == 0) continue;
                if (n5 != Integer.MIN_VALUE) {
                    return n5;
                }
                bl2 = true;
            }
            return bl2 ? Integer.MIN_VALUE : 0;
        }
        int n6 = Math.min(n3, n4);
        for (int i = 0; i < n6; ++i) {
            Value value4 = valueArray[i];
            Value value5 = valueArray2[i];
            int n7 = value4.compareWithNull(value5, bl, castDataProvider, compareMode);
            if (n7 == 0) continue;
            return n7;
        }
        return Integer.compare(n3, n4);
    }

    @Override
    public boolean containsNull() {
        for (Value value : this.values) {
            if (!value.containsNull()) continue;
            return true;
        }
        return false;
    }

    @Override
    Value getValueWithFirstNullImpl(Value value) {
        ValueCollectionBase valueCollectionBase = (ValueCollectionBase)value;
        Value[] valueArray = this.values;
        Value[] valueArray2 = valueCollectionBase.values;
        int n = valueArray.length;
        int n2 = valueArray2.length;
        int n3 = Math.min(n, n2);
        for (int i = 0; i < n3; ++i) {
            Value value2 = valueArray[i];
            Value value3 = valueArray2[i];
            Value value4 = value2.getValueWithFirstNull(value3);
            if (value4 == value2) {
                return this;
            }
            if (value4 != value3) continue;
            return value;
        }
        return null;
    }

    @Override
    public int getMemory() {
        int n = 72 + this.values.length * 8;
        for (Value value : this.values) {
            n += value.getMemory();
        }
        return n;
    }
}

