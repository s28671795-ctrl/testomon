/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.Bits;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ValueInteger
extends Value {
    public static final int PRECISION = 32;
    public static final int DECIMAL_PRECISION = 10;
    public static final int DISPLAY_SIZE = 11;
    private static final int STATIC_SIZE = 128;
    private static final int DYNAMIC_SIZE = 256;
    private static final ValueInteger[] STATIC_CACHE = new ValueInteger[128];
    private static final ValueInteger[] DYNAMIC_CACHE = new ValueInteger[256];
    private final int value;

    private ValueInteger(int n) {
        this.value = n;
    }

    public static ValueInteger get(int n) {
        if (n >= 0 && n < 128) {
            return STATIC_CACHE[n];
        }
        ValueInteger valueInteger = DYNAMIC_CACHE[n & 0xFF];
        if (valueInteger == null || valueInteger.value != n) {
            ValueInteger.DYNAMIC_CACHE[n & 0xFF] = valueInteger = new ValueInteger(n);
        }
        return valueInteger;
    }

    @Override
    public Value add(Value value) {
        ValueInteger valueInteger = (ValueInteger)value;
        return ValueInteger.checkRange((long)this.value + (long)valueInteger.value);
    }

    private static ValueInteger checkRange(long l) {
        if ((long)((int)l) != l) {
            throw DbException.get(22003, Long.toString(l));
        }
        return ValueInteger.get((int)l);
    }

    @Override
    public int getSignum() {
        return Integer.signum(this.value);
    }

    @Override
    public Value negate() {
        return ValueInteger.checkRange(-((long)this.value));
    }

    @Override
    public Value subtract(Value value) {
        ValueInteger valueInteger = (ValueInteger)value;
        return ValueInteger.checkRange((long)this.value - (long)valueInteger.value);
    }

    @Override
    public Value multiply(Value value) {
        ValueInteger valueInteger = (ValueInteger)value;
        return ValueInteger.checkRange((long)this.value * (long)valueInteger.value);
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        int n = ((ValueInteger)value).value;
        if (n == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        int n2 = this.value;
        if (n2 == Integer.MIN_VALUE && n == -1) {
            throw DbException.get(22003, "2147483648");
        }
        return ValueInteger.get(n2 / n);
    }

    @Override
    public Value modulus(Value value) {
        ValueInteger valueInteger = (ValueInteger)value;
        if (valueInteger.value == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueInteger.get(this.value % valueInteger.value);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(this.value);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_INTEGER;
    }

    @Override
    public int getValueType() {
        return 11;
    }

    @Override
    public byte[] getBytes() {
        byte[] byArray = new byte[4];
        Bits.writeInt(byArray, 0, this.getInt());
        return byArray;
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
        return Integer.compare(this.value, ((ValueInteger)value).value);
    }

    @Override
    public String getString() {
        return Integer.toString(this.value);
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ValueInteger && this.value == ((ValueInteger)object).value;
    }

    static {
        for (int i = 0; i < 128; ++i) {
            ValueInteger.STATIC_CACHE[i] = new ValueInteger(i);
        }
    }
}

