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

public final class ValueReal
extends Value {
    static final int PRECISION = 24;
    static final int DECIMAL_PRECISION = 7;
    static final int DISPLAY_SIZE = 15;
    public static final int ZERO_BITS = 0;
    public static final ValueReal ZERO = new ValueReal(0.0f);
    public static final ValueReal ONE = new ValueReal(1.0f);
    private static final ValueReal NAN = new ValueReal(Float.NaN);
    private final float value;

    private ValueReal(float f) {
        this.value = f;
    }

    @Override
    public Value add(Value value) {
        return ValueReal.get(this.value + ((ValueReal)value).value);
    }

    @Override
    public Value subtract(Value value) {
        return ValueReal.get(this.value - ((ValueReal)value).value);
    }

    @Override
    public Value negate() {
        return ValueReal.get(-this.value);
    }

    @Override
    public Value multiply(Value value) {
        return ValueReal.get(this.value * ((ValueReal)value).value);
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        ValueReal valueReal = (ValueReal)value;
        if ((double)valueReal.value == 0.0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueReal.get(this.value / valueReal.value);
    }

    @Override
    public Value modulus(Value value) {
        ValueReal valueReal = (ValueReal)value;
        if (valueReal.value == 0.0f) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueReal.get(this.value % valueReal.value);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            return this.getSQL(stringBuilder.append("CAST(")).append(" AS REAL)");
        }
        return this.getSQL(stringBuilder);
    }

    private StringBuilder getSQL(StringBuilder stringBuilder) {
        if (this.value == Float.POSITIVE_INFINITY) {
            return stringBuilder.append("'Infinity'");
        }
        if (this.value == Float.NEGATIVE_INFINITY) {
            return stringBuilder.append("'-Infinity'");
        }
        if (Float.isNaN(this.value)) {
            return stringBuilder.append("'NaN'");
        }
        return stringBuilder.append(this.value);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_REAL;
    }

    @Override
    public int getValueType() {
        return 14;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return Float.compare(this.value, ((ValueReal)value).value);
    }

    @Override
    public int getSignum() {
        return this.value == 0.0f || Float.isNaN(this.value) ? 0 : (this.value < 0.0f ? -1 : 1);
    }

    @Override
    public BigDecimal getBigDecimal() {
        if (Float.isFinite(this.value)) {
            return new BigDecimal(Float.toString(this.value));
        }
        throw DbException.get(22018, Float.toString(this.value));
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
    public String getString() {
        return Float.toString(this.value);
    }

    @Override
    public int hashCode() {
        return Float.floatToRawIntBits(this.value);
    }

    public static ValueReal get(float f) {
        if (f == 1.0f) {
            return ONE;
        }
        if (f == 0.0f) {
            return ZERO;
        }
        if (Float.isNaN(f)) {
            return NAN;
        }
        return (ValueReal)Value.cache(new ValueReal(f));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ValueReal)) {
            return false;
        }
        return this.compareTypeSafe((ValueReal)object, null, null) == 0;
    }
}

