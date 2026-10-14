/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ValueTimestamp
extends Value {
    public static final int DEFAULT_PRECISION = 26;
    public static final int MAXIMUM_PRECISION = 29;
    public static final int DEFAULT_SCALE = 6;
    public static final int MAXIMUM_SCALE = 9;
    private final long dateValue;
    private final long timeNanos;

    private ValueTimestamp(long l, long l2) {
        if (l < -511999999967L || l > 512000000415L) {
            throw new IllegalArgumentException("dateValue out of range " + l);
        }
        if (l2 < 0L || l2 >= 86400000000000L) {
            throw new IllegalArgumentException("timeNanos out of range " + l2);
        }
        this.dateValue = l;
        this.timeNanos = l2;
    }

    public static ValueTimestamp fromDateValueAndNanos(long l, long l2) {
        return (ValueTimestamp)Value.cache(new ValueTimestamp(l, l2));
    }

    public static ValueTimestamp parse(String string, CastDataProvider castDataProvider) {
        try {
            return (ValueTimestamp)DateTimeUtils.parseTimestamp(string, castDataProvider, false);
        }
        catch (Exception exception) {
            throw DbException.get(22007, exception, "TIMESTAMP", string);
        }
    }

    public long getDateValue() {
        return this.dateValue;
    }

    public long getTimeNanos() {
        return this.timeNanos;
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_TIMESTAMP;
    }

    @Override
    public int getValueType() {
        return 20;
    }

    @Override
    public int getMemory() {
        return 32;
    }

    @Override
    public String getString() {
        return this.toString(new StringBuilder(29), false).toString();
    }

    public String getISOString() {
        return this.toString(new StringBuilder(29), true).toString();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return this.toString(stringBuilder.append("TIMESTAMP '"), false).append('\'');
    }

    private StringBuilder toString(StringBuilder stringBuilder, boolean bl) {
        DateTimeUtils.appendDate(stringBuilder, this.dateValue).append(bl ? (char)'T' : ' ');
        return DateTimeUtils.appendTime(stringBuilder, this.timeNanos);
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        ValueTimestamp valueTimestamp = (ValueTimestamp)value;
        int n = Long.compare(this.dateValue, valueTimestamp.dateValue);
        if (n != 0) {
            return n;
        }
        return Long.compare(this.timeNanos, valueTimestamp.timeNanos);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ValueTimestamp)) {
            return false;
        }
        ValueTimestamp valueTimestamp = (ValueTimestamp)object;
        return this.dateValue == valueTimestamp.dateValue && this.timeNanos == valueTimestamp.timeNanos;
    }

    @Override
    public int hashCode() {
        return (int)(this.dateValue ^ this.dateValue >>> 32 ^ this.timeNanos ^ this.timeNanos >>> 32);
    }

    @Override
    public Value add(Value value) {
        ValueTimestamp valueTimestamp = (ValueTimestamp)value;
        long l = DateTimeUtils.absoluteDayFromDateValue(this.dateValue) + DateTimeUtils.absoluteDayFromDateValue(valueTimestamp.dateValue);
        long l2 = this.timeNanos + valueTimestamp.timeNanos;
        if (l2 >= 86400000000000L) {
            l2 -= 86400000000000L;
            ++l;
        }
        return ValueTimestamp.fromDateValueAndNanos(DateTimeUtils.dateValueFromAbsoluteDay(l), l2);
    }

    @Override
    public Value subtract(Value value) {
        ValueTimestamp valueTimestamp = (ValueTimestamp)value;
        long l = DateTimeUtils.absoluteDayFromDateValue(this.dateValue) - DateTimeUtils.absoluteDayFromDateValue(valueTimestamp.dateValue);
        long l2 = this.timeNanos - valueTimestamp.timeNanos;
        if (l2 < 0L) {
            l2 += 86400000000000L;
            --l;
        }
        return ValueTimestamp.fromDateValueAndNanos(DateTimeUtils.dateValueFromAbsoluteDay(l), l2);
    }
}

