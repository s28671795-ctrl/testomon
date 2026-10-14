/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.h2.api.Interval;
import org.h2.api.IntervalQualifier;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.util.IntervalUtils;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ValueInterval
extends Value {
    public static final int DEFAULT_PRECISION = 2;
    public static final int MAXIMUM_PRECISION = 18;
    public static final int DEFAULT_SCALE = 6;
    public static final int MAXIMUM_SCALE = 9;
    private static final long[] MULTIPLIERS = new long[]{1000000000L, 12L, 24L, 1440L, 86400000000000L, 60L, 3600000000000L, 60000000000L};
    private final int valueType;
    private final boolean negative;
    private final long leading;
    private final long remaining;

    public static ValueInterval from(IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        bl = IntervalUtils.validateInterval(intervalQualifier, bl, l, l2);
        return (ValueInterval)Value.cache(new ValueInterval(intervalQualifier.ordinal() + 22, bl, l, l2));
    }

    public static int getDisplaySize(int n, int n2, int n3) {
        switch (n) {
            case 22: 
            case 25: {
                return 17 + n2;
            }
            case 23: {
                return 18 + n2;
            }
            case 24: {
                return 16 + n2;
            }
            case 26: {
                return 19 + n2;
            }
            case 27: {
                return n3 > 0 ? 20 + n2 + n3 : 19 + n2;
            }
            case 28: {
                return 29 + n2;
            }
            case 29: {
                return 27 + n2;
            }
            case 30: {
                return 32 + n2;
            }
            case 31: {
                return n3 > 0 ? 36 + n2 + n3 : 35 + n2;
            }
            case 32: {
                return 30 + n2;
            }
            case 33: {
                return n3 > 0 ? 34 + n2 + n3 : 33 + n2;
            }
            case 34: {
                return n3 > 0 ? 33 + n2 + n3 : 32 + n2;
            }
        }
        throw DbException.getUnsupportedException(Integer.toString(n));
    }

    private ValueInterval(int n, boolean bl, long l, long l2) {
        this.valueType = n;
        this.negative = bl;
        this.leading = l;
        this.remaining = l2;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return IntervalUtils.appendInterval(stringBuilder, this.getQualifier(), this.negative, this.leading, this.remaining);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.getTypeInfo(this.valueType);
    }

    @Override
    public int getValueType() {
        return this.valueType;
    }

    @Override
    public int getMemory() {
        return 48;
    }

    boolean checkPrecision(long l) {
        if (l < 18L) {
            long l2 = this.leading;
            long l3 = 0L;
            for (long i = 1L; l2 >= i; i *= 10L) {
                if (++l3 <= l) continue;
                return false;
            }
        }
        return true;
    }

    ValueInterval setPrecisionAndScale(TypeInfo typeInfo, Object object) {
        ValueInterval valueInterval;
        block10: {
            int n = typeInfo.getScale();
            valueInterval = this;
            if (n < 9) {
                long l;
                switch (this.valueType) {
                    case 27: {
                        l = 1000000000L;
                        break;
                    }
                    case 31: {
                        l = 86400000000000L;
                        break;
                    }
                    case 33: {
                        l = 3600000000000L;
                        break;
                    }
                    case 34: {
                        l = 60000000000L;
                        break;
                    }
                    default: {
                        break block10;
                    }
                }
                long l2 = this.leading;
                long l3 = DateTimeUtils.convertScale(this.remaining, n, l2 == 999999999999999999L ? l : Long.MAX_VALUE);
                if (l3 != this.remaining) {
                    if (l3 >= l) {
                        ++l2;
                        l3 -= l;
                    }
                    valueInterval = ValueInterval.from(valueInterval.getQualifier(), valueInterval.isNegative(), l2, l3);
                }
            }
        }
        if (!valueInterval.checkPrecision(typeInfo.getPrecision())) {
            throw valueInterval.getValueTooLongException(typeInfo, object);
        }
        return valueInterval;
    }

    @Override
    public String getString() {
        return IntervalUtils.appendInterval(new StringBuilder(), this.getQualifier(), this.negative, this.leading, this.remaining).toString();
    }

    @Override
    public long getLong() {
        long l = this.leading;
        if (this.valueType >= 27 && this.remaining != 0L && this.remaining >= MULTIPLIERS[this.valueType - 27] >> 1) {
            ++l;
        }
        return this.negative ? -l : l;
    }

    @Override
    public BigDecimal getBigDecimal() {
        if (this.valueType < 27 || this.remaining == 0L) {
            return BigDecimal.valueOf(this.negative ? -this.leading : this.leading);
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(MULTIPLIERS[this.valueType - 27]);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(this.leading).add(BigDecimal.valueOf(this.remaining).divide(bigDecimal, bigDecimal.precision(), RoundingMode.HALF_DOWN)).stripTrailingZeros();
        return this.negative ? bigDecimal2.negate() : bigDecimal2;
    }

    @Override
    public float getFloat() {
        if (this.valueType < 27 || this.remaining == 0L) {
            return this.negative ? (float)(-this.leading) : (float)this.leading;
        }
        return this.getBigDecimal().floatValue();
    }

    @Override
    public double getDouble() {
        if (this.valueType < 27 || this.remaining == 0L) {
            return this.negative ? (double)(-this.leading) : (double)this.leading;
        }
        return this.getBigDecimal().doubleValue();
    }

    public Interval getInterval() {
        return new Interval(this.getQualifier(), this.negative, this.leading, this.remaining);
    }

    public IntervalQualifier getQualifier() {
        return IntervalQualifier.valueOf(this.valueType - 22);
    }

    public boolean isNegative() {
        return this.negative;
    }

    public long getLeading() {
        return this.leading;
    }

    public long getRemaining() {
        return this.remaining;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = 31 * n + this.valueType;
        n = 31 * n + (this.negative ? 1231 : 1237);
        n = 31 * n + (int)(this.leading ^ this.leading >>> 32);
        n = 31 * n + (int)(this.remaining ^ this.remaining >>> 32);
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ValueInterval)) {
            return false;
        }
        ValueInterval valueInterval = (ValueInterval)object;
        return this.valueType == valueInterval.valueType && this.negative == valueInterval.negative && this.leading == valueInterval.leading && this.remaining == valueInterval.remaining;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        ValueInterval valueInterval = (ValueInterval)value;
        if (this.negative != valueInterval.negative) {
            return this.negative ? -1 : 1;
        }
        int n = Long.compare(this.leading, valueInterval.leading);
        if (n == 0) {
            n = Long.compare(this.remaining, valueInterval.remaining);
        }
        return this.negative ? -n : n;
    }

    @Override
    public int getSignum() {
        return this.negative ? -1 : (this.leading == 0L && this.remaining == 0L ? 0 : 1);
    }

    @Override
    public Value add(Value value) {
        return IntervalUtils.intervalFromAbsolute(this.getQualifier(), IntervalUtils.intervalToAbsolute(this).add(IntervalUtils.intervalToAbsolute((ValueInterval)value)));
    }

    @Override
    public Value subtract(Value value) {
        return IntervalUtils.intervalFromAbsolute(this.getQualifier(), IntervalUtils.intervalToAbsolute(this).subtract(IntervalUtils.intervalToAbsolute((ValueInterval)value)));
    }

    @Override
    public Value negate() {
        if (this.leading == 0L && this.remaining == 0L) {
            return this;
        }
        return Value.cache(new ValueInterval(this.valueType, !this.negative, this.leading, this.remaining));
    }
}

