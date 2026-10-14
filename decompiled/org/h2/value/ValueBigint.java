/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.Bits;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ValueBigint
extends Value {
    public static final ValueBigint MIN = ValueBigint.get(Long.MIN_VALUE);
    public static final ValueBigint MAX = ValueBigint.get(Long.MAX_VALUE);
    public static final BigInteger MAX_BI = BigInteger.valueOf(Long.MAX_VALUE);
    static final int PRECISION = 64;
    public static final int DECIMAL_PRECISION = 19;
    public static final int DISPLAY_SIZE = 20;
    private static final int STATIC_SIZE = 100;
    private static final ValueBigint[] STATIC_CACHE = new ValueBigint[100];
    private final long value;

    private ValueBigint(long l) {
        this.value = l;
    }

    @Override
    public Value add(Value value) {
        long l = this.value;
        long l2 = ((ValueBigint)value).value;
        long l3 = l + l2;
        if (((l ^ l3) & (l2 ^ l3)) < 0L) {
            throw this.getOverflow();
        }
        return ValueBigint.get(l3);
    }

    @Override
    public int getSignum() {
        return Long.signum(this.value);
    }

    @Override
    public Value negate() {
        if (this.value == Long.MIN_VALUE) {
            throw this.getOverflow();
        }
        return ValueBigint.get(-this.value);
    }

    private DbException getOverflow() {
        return DbException.get(22003, Long.toString(this.value));
    }

    @Override
    public Value subtract(Value value) {
        long l = this.value;
        long l2 = ((ValueBigint)value).value;
        long l3 = l - l2;
        if (((l ^ l2) & (l ^ l3)) < 0L) {
            throw this.getOverflow();
        }
        return ValueBigint.get(l3);
    }

    @Override
    public Value multiply(Value value) {
        long l = this.value;
        long l2 = ((ValueBigint)value).value;
        long l3 = l * l2;
        if ((Math.abs(l) | Math.abs(l2)) >>> 31 != 0L && l2 != 0L && (l3 / l2 != l || l == Long.MIN_VALUE && l2 == -1L)) {
            throw this.getOverflow();
        }
        return ValueBigint.get(l3);
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        long l = ((ValueBigint)value).value;
        if (l == 0L) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        long l2 = this.value;
        if (l2 == Long.MIN_VALUE && l == -1L) {
            throw this.getOverflow();
        }
        return ValueBigint.get(l2 / l);
    }

    @Override
    public Value modulus(Value value) {
        ValueBigint valueBigint = (ValueBigint)value;
        if (valueBigint.value == 0L) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueBigint.get(this.value % valueBigint.value);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0 && this.value == (long)((int)this.value)) {
            return stringBuilder.append("CAST(").append(this.value).append(" AS BIGINT)");
        }
        return stringBuilder.append(this.value);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_BIGINT;
    }

    @Override
    public int getValueType() {
        return 12;
    }

    @Override
    public byte[] getBytes() {
        byte[] byArray = new byte[8];
        Bits.writeLong(byArray, 0, this.getLong());
        return byArray;
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
        return Long.compare(this.value, ((ValueBigint)value).value);
    }

    @Override
    public String getString() {
        return Long.toString(this.value);
    }

    @Override
    public int hashCode() {
        return (int)(this.value ^ this.value >> 32);
    }

    public static ValueBigint get(long l) {
        if (l >= 0L && l < 100L) {
            return STATIC_CACHE[(int)l];
        }
        return (ValueBigint)Value.cache(new ValueBigint(l));
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ValueBigint && this.value == ((ValueBigint)object).value;
    }

    static {
        for (int i = 0; i < 100; ++i) {
            ValueBigint.STATIC_CACHE[i] = new ValueBigint(i);
        }
    }
}

