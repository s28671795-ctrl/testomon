/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ValueTinyint
extends Value {
    static final int PRECISION = 8;
    public static final int DECIMAL_PRECISION = 3;
    static final int DISPLAY_SIZE = 4;
    private static final ValueTinyint[] STATIC_CACHE;
    private final byte value;

    private ValueTinyint(byte by) {
        this.value = by;
    }

    @Override
    public Value add(Value value) {
        ValueTinyint valueTinyint = (ValueTinyint)value;
        return ValueTinyint.checkRange(this.value + valueTinyint.value);
    }

    private static ValueTinyint checkRange(int n) {
        if ((byte)n != n) {
            throw DbException.get(22003, Integer.toString(n));
        }
        return ValueTinyint.get((byte)n);
    }

    @Override
    public int getSignum() {
        return Integer.signum(this.value);
    }

    @Override
    public Value negate() {
        return ValueTinyint.checkRange(-this.value);
    }

    @Override
    public Value subtract(Value value) {
        ValueTinyint valueTinyint = (ValueTinyint)value;
        return ValueTinyint.checkRange(this.value - valueTinyint.value);
    }

    @Override
    public Value multiply(Value value) {
        ValueTinyint valueTinyint = (ValueTinyint)value;
        return ValueTinyint.checkRange(this.value * valueTinyint.value);
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        ValueTinyint valueTinyint = (ValueTinyint)value;
        if (valueTinyint.value == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueTinyint.checkRange(this.value / valueTinyint.value);
    }

    @Override
    public Value modulus(Value value) {
        ValueTinyint valueTinyint = (ValueTinyint)value;
        if (valueTinyint.value == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueTinyint.get((byte)(this.value % valueTinyint.value));
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            return stringBuilder.append("CAST(").append(this.value).append(" AS TINYINT)");
        }
        return stringBuilder.append(this.value);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_TINYINT;
    }

    @Override
    public int getValueType() {
        return 9;
    }

    @Override
    public int getMemory() {
        return 0;
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{this.value};
    }

    @Override
    public byte getByte() {
        return this.value;
    }

    @Override
    public short getShort() {
        return this.value;
    }

    @Override
    public int getInt() {
        return this.value;
    }

    @Override
    public long getLong() {
        return this.value;
    }

    @Override
    public BigDecimal getBigDecimal() {
        return BigDecimal.valueOf(this.value);
    }

    @Override
    public float getFloat() {
        return this.value;
    }

    @Override
    public double getDouble() {
        return this.value;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return Integer.compare(this.value, ((ValueTinyint)value).value);
    }

    @Override
    public String getString() {
        return Integer.toString(this.value);
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    public static ValueTinyint get(byte by) {
        return STATIC_CACHE[by + 128];
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ValueTinyint && this.value == ((ValueTinyint)object).value;
    }

    static {
        ValueTinyint[] valueTinyintArray = new ValueTinyint[256];
        for (int i = 0; i < 256; ++i) {
            valueTinyintArray[i] = new ValueTinyint((byte)(i - 128));
        }
        STATIC_CACHE = valueTinyintArray;
    }
}

