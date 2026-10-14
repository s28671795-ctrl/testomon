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

public final class ValueTime
extends Value {
    public static final int DEFAULT_PRECISION = 8;
    public static final int MAXIMUM_PRECISION = 18;
    public static final int DEFAULT_SCALE = 0;
    public static final int MAXIMUM_SCALE = 9;
    private static final ValueTime[] STATIC_CACHE;
    private final long nanos;

    private ValueTime(long l) {
        this.nanos = l;
    }

    public static ValueTime fromNanos(long l) {
        if (l < 0L || l >= 86400000000000L) {
            throw DbException.get(22007, "TIME", DateTimeUtils.appendTime(new StringBuilder(), l).toString());
        }
        if (l % 3600000000000L == 0L) {
            return STATIC_CACHE[(int)(l / 3600000000000L)];
        }
        return (ValueTime)Value.cache(new ValueTime(l));
    }

    public static ValueTime parse(String string, CastDataProvider castDataProvider) {
        try {
            return (ValueTime)DateTimeUtils.parseTime(string, castDataProvider, false);
        }
        catch (Exception exception) {
            throw DbException.get(22007, exception, "TIME", string);
        }
    }

    public long getNanos() {
        return this.nanos;
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_TIME;
    }

    @Override
    public int getValueType() {
        return 18;
    }

    @Override
    public String getString() {
        return DateTimeUtils.appendTime(new StringBuilder(18), this.nanos).toString();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return DateTimeUtils.appendTime(stringBuilder.append("TIME '"), this.nanos).append('\'');
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return Long.compare(this.nanos, ((ValueTime)value).nanos);
    }

    @Override
    public boolean equals(Object object) {
        return this == object || object instanceof ValueTime && this.nanos == ((ValueTime)object).nanos;
    }

    @Override
    public int hashCode() {
        return (int)(this.nanos ^ this.nanos >>> 32);
    }

    @Override
    public Value add(Value value) {
        ValueTime valueTime = (ValueTime)value;
        return ValueTime.fromNanos(this.nanos + valueTime.getNanos());
    }

    @Override
    public Value subtract(Value value) {
        ValueTime valueTime = (ValueTime)value;
        return ValueTime.fromNanos(this.nanos - valueTime.getNanos());
    }

    @Override
    public Value multiply(Value value) {
        return ValueTime.fromNanos((long)((double)this.nanos * value.getDouble()));
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        return ValueTime.fromNanos((long)((double)this.nanos / value.getDouble()));
    }

    static {
        ValueTime[] valueTimeArray = new ValueTime[24];
        for (int i = 0; i < 24; ++i) {
            valueTimeArray[i] = new ValueTime((long)i * 3600000000000L);
        }
        STATIC_CACHE = valueTimeArray;
    }
}

