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

public final class ValueDouble
extends Value {
    static final int PRECISION = 53;
    public static final int DECIMAL_PRECISION = 17;
    public static final int DISPLAY_SIZE = 24;
    public static final long ZERO_BITS = 0L;
    public static final ValueDouble ZERO = new ValueDouble(0.0);
    public static final ValueDouble ONE = new ValueDouble(1.0);
    private static final ValueDouble NAN = new ValueDouble(Double.NaN);
    private final double value;

    private ValueDouble(double d) {
        this.value = d;
    }

    @Override
    public Value add(Value value) {
        return ValueDouble.get(this.value + ((ValueDouble)value).value);
    }

    @Override
    public Value subtract(Value value) {
        return ValueDouble.get(this.value - ((ValueDouble)value).value);
    }

    @Override
    public Value negate() {
        return ValueDouble.get(-this.value);
    }

    @Override
    public Value multiply(Value value) {
        return ValueDouble.get(this.value * ((ValueDouble)value).value);
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        ValueDouble valueDouble = (ValueDouble)value;
        if (valueDouble.value == 0.0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueDouble.get(this.value / valueDouble.value);
    }

    @Override
    public ValueDouble modulus(Value value) {
        ValueDouble valueDouble = (ValueDouble)value;
        if (valueDouble.value == 0.0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueDouble.get(this.value % valueDouble.value);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            return this.getSQL(stringBuilder.append("CAST(")).append(" AS DOUBLE PRECISION)");
        }
        return this.getSQL(stringBuilder);
    }

    private StringBuilder getSQL(StringBuilder stringBuilder) {
        if (this.value == Double.POSITIVE_INFINITY) {
            return stringBuilder.append("'Infinity'");
        }
        if (this.value == Double.NEGATIVE_INFINITY) {
            return stringBuilder.append("'-Infinity'");
        }
        if (Double.isNaN(this.value)) {
            return stringBuilder.append("'NaN'");
        }
        return stringBuilder.append(this.value);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_DOUBLE;
    }

    @Override
    public int getValueType() {
        return 15;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return Double.compare(this.value, ((ValueDouble)value).value);
    }

    @Override
    public int getSignum() {
        return this.value == 0.0 || Double.isNaN(this.value) ? 0 : (this.value < 0.0 ? -1 : 1);
    }

    @Override
    public BigDecimal getBigDecimal() {
        if (Double.isFinite(this.value)) {
            return BigDecimal.valueOf(this.value);
        }
        throw DbException.get(22018, Double.toString(this.value));
    }

    @Override
    public float getFloat() {
        return (float)this.value;
    }

    @Override
    public double getDouble() {
        return this.value;
    }

    @Override
    public String getString() {
        return Double.toString(this.value);
    }

    @Override
    public int hashCode() {
        long l = Double.doubleToRawLongBits(this.value);
        return (int)(l ^ l >>> 32);
    }

    public static ValueDouble get(double d) {
        if (d == 1.0) {
            return ONE;
        }
        if (d == 0.0) {
            return ZERO;
        }
        if (Double.isNaN(d)) {
            return NAN;
        }
        return (ValueDouble)Value.cache(new ValueDouble(d));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ValueDouble)) {
            return false;
        }
        return this.compareTypeSafe((ValueDouble)object, null, null) == 0;
    }
}

