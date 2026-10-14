/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigDecimalBase;

public final class ValueNumeric
extends ValueBigDecimalBase {
    public static final ValueNumeric ZERO = new ValueNumeric(BigDecimal.ZERO);
    public static final ValueNumeric ONE = new ValueNumeric(BigDecimal.ONE);
    public static final int DEFAULT_SCALE = 0;
    public static final int MAXIMUM_SCALE = 100000;

    private ValueNumeric(BigDecimal bigDecimal) {
        super(bigDecimal);
        if (bigDecimal == null) {
            throw new IllegalArgumentException("null");
        }
        int n = bigDecimal.scale();
        if (n < 0 || n > 100000) {
            throw DbException.get(90151, Integer.toString(n), "0", "100000");
        }
    }

    @Override
    public String getString() {
        return this.value.toPlainString();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        String string = this.getString();
        if ((n & 4) == 0 && string.indexOf(46) < 0 && this.value.compareTo(MAX_LONG_DECIMAL) <= 0 && this.value.compareTo(MIN_LONG_DECIMAL) >= 0) {
            return stringBuilder.append("CAST(").append(this.value).append(" AS NUMERIC(").append(this.value.precision()).append("))");
        }
        return stringBuilder.append(string);
    }

    @Override
    public TypeInfo getType() {
        TypeInfo typeInfo = this.type;
        if (typeInfo == null) {
            this.type = typeInfo = new TypeInfo(13, this.value.precision(), this.value.scale(), null);
        }
        return typeInfo;
    }

    @Override
    public int getValueType() {
        return 13;
    }

    @Override
    public Value add(Value value) {
        return ValueNumeric.get(this.value.add(((ValueNumeric)value).value));
    }

    @Override
    public Value subtract(Value value) {
        return ValueNumeric.get(this.value.subtract(((ValueNumeric)value).value));
    }

    @Override
    public Value negate() {
        return ValueNumeric.get(this.value.negate());
    }

    @Override
    public Value multiply(Value value) {
        return ValueNumeric.get(this.value.multiply(((ValueNumeric)value).value));
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        BigDecimal bigDecimal = ((ValueNumeric)value).value;
        if (bigDecimal.signum() == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueNumeric.get(this.value.divide(bigDecimal, typeInfo.getScale(), RoundingMode.HALF_DOWN));
    }

    @Override
    public Value modulus(Value value) {
        ValueNumeric valueNumeric = (ValueNumeric)value;
        if (valueNumeric.value.signum() == 0) {
            throw DbException.get(22012, this.getTraceSQL());
        }
        return ValueNumeric.get(this.value.remainder(valueNumeric.value));
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return this.value.compareTo(((ValueNumeric)value).value);
    }

    @Override
    public int getSignum() {
        return this.value.signum();
    }

    @Override
    public BigDecimal getBigDecimal() {
        return this.value;
    }

    @Override
    public float getFloat() {
        return this.value.floatValue();
    }

    @Override
    public double getDouble() {
        return this.value.doubleValue();
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode() * 31 + this.value.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ValueNumeric && this.value.equals(((ValueNumeric)object).value);
    }

    @Override
    public int getMemory() {
        return this.value.precision() + 120;
    }

    public static ValueNumeric get(BigDecimal bigDecimal) {
        if (BigDecimal.ZERO.equals(bigDecimal)) {
            return ZERO;
        }
        if (BigDecimal.ONE.equals(bigDecimal)) {
            return ONE;
        }
        return (ValueNumeric)Value.cache(new ValueNumeric(bigDecimal));
    }

    public static ValueNumeric getAnyScale(BigDecimal bigDecimal) {
        if (bigDecimal.scale() < 0) {
            bigDecimal = bigDecimal.setScale(0, RoundingMode.UNNECESSARY);
        }
        return ValueNumeric.get(bigDecimal);
    }

    public static ValueNumeric get(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return ZERO;
        }
        if (BigInteger.ONE.equals(bigInteger)) {
            return ONE;
        }
        return (ValueNumeric)Value.cache(new ValueNumeric(new BigDecimal(bigInteger)));
    }

    public static BigDecimal setScale(BigDecimal bigDecimal, int n) {
        if (n < 0 || n > 100000) {
            throw DbException.getInvalidValueException("scale", n);
        }
        return bigDecimal.setScale(n, RoundingMode.HALF_UP);
    }
}

