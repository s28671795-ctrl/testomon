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

public final class ValueSmallint
extends Value {
    static final int PRECISION = 16;
    public static final int DECIMAL_PRECISION = 5;
    static final int DISPLAY_SIZE = 6;
    private final short value;

    private ValueSmallint(short s2) {
        this.value = s2;
    }

    @Override
    public Value add(Value value) {
        ValueSmallint valueSmallint = (ValueSmallint)value;
        return ValueSmallint.checkRange(this.value + valueSmallint.value);
    }

    private static ValueSmallint checkRange(int n) {
        if ((short)n != n) {
            throw DbException.get(22003, Integer.toString(n));
        }
        return ValueSmallint.get((short)n);
    }

    @Override
    public int getSignum() {
        return Integer.signum(this.value);
    }

    @Override
    public Value negate() {
        return ValueSmallint.checkRange(-this.value);
    }

    @Override
    public Value subtract(Value value) {
        ValueSmallint valueSmallint = (ValueSmallint)value;
        return ValueSmallint.checkRange(this.value - valueSmallint.value);
    }

    @Override
    public Value multiply(Value value) {
        ValueSmallint valueSmallint = (ValueSmallint)value;
        return ValueSmallint.checkRange(this.value * valueSmallint.value);
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        ValueSmallint valueSmallint = (ValueSmallint)value;
        if (valueSmallint.value == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueSmallint.checkRange(this.value / valueSmallint.value);
    }

    @Override
    public Value modulus(Value value) {
        ValueSmallint valueSmallint = (ValueSmallint)value;
        if (valueSmallint.value == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueSmallint.get((short)(this.value % valueSmallint.value));
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            return stringBuilder.append("CAST(").append(this.value).append(" AS SMALLINT)");
        }
        return stringBuilder.append(this.value);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_SMALLINT;
    }

    @Override
    public int getValueType() {
        return 10;
    }

    @Override
    public byte[] getBytes() {
        short s2 = this.value;
        return new byte[]{(byte)(s2 >> 8), (byte)s2};
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
        return Integer.compare(this.value, ((ValueSmallint)value).value);
    }

    @Override
    public String getString() {
        return Integer.toString(this.value);
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    public static ValueSmallint get(short s2) {
        return (ValueSmallint)Value.cache(new ValueSmallint(s2));
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ValueSmallint && this.value == ((ValueSmallint)object).value;
    }
}

