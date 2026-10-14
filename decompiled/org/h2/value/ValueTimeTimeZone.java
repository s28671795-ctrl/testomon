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

public final class ValueTimeTimeZone
extends Value {
    public static final int DEFAULT_PRECISION = 14;
    public static final int MAXIMUM_PRECISION = 24;
    private final long nanos;
    private final int timeZoneOffsetSeconds;

    private ValueTimeTimeZone(long l, int n) {
        this.nanos = l;
        this.timeZoneOffsetSeconds = n;
    }

    public static ValueTimeTimeZone fromNanos(long l, int n) {
        if (l < 0L || l >= 86400000000000L) {
            throw DbException.get(22007, "TIME WITH TIME ZONE", DateTimeUtils.appendTime(new StringBuilder(), l).toString());
        }
        if (n < -64800 || n > 64800) {
            throw new IllegalArgumentException("timeZoneOffsetSeconds " + n);
        }
        return (ValueTimeTimeZone)Value.cache(new ValueTimeTimeZone(l, n));
    }

    public static ValueTimeTimeZone parse(String string, CastDataProvider castDataProvider) {
        try {
            return (ValueTimeTimeZone)DateTimeUtils.parseTime(string, castDataProvider, true);
        }
        catch (Exception exception) {
            throw DbException.get(22007, exception, "TIME WITH TIME ZONE", string);
        }
    }

    public long getNanos() {
        return this.nanos;
    }

    public int getTimeZoneOffsetSeconds() {
        return this.timeZoneOffsetSeconds;
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_TIME_TZ;
    }

    @Override
    public int getValueType() {
        return 19;
    }

    @Override
    public int getMemory() {
        return 32;
    }

    @Override
    public String getString() {
        return this.toString(new StringBuilder(24)).toString();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return this.toString(stringBuilder.append("TIME WITH TIME ZONE '")).append('\'');
    }

    private StringBuilder toString(StringBuilder stringBuilder) {
        return DateTimeUtils.appendTimeZone(DateTimeUtils.appendTime(stringBuilder, this.nanos), this.timeZoneOffsetSeconds);
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value;
        return Long.compare(this.nanos - (long)this.timeZoneOffsetSeconds * 1000000000L, valueTimeTimeZone.nanos - (long)valueTimeTimeZone.timeZoneOffsetSeconds * 1000000000L);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ValueTimeTimeZone)) {
            return false;
        }
        ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)object;
        return this.nanos == valueTimeTimeZone.nanos && this.timeZoneOffsetSeconds == valueTimeTimeZone.timeZoneOffsetSeconds;
    }

    @Override
    public int hashCode() {
        return (int)(this.nanos ^ this.nanos >>> 32 ^ (long)this.timeZoneOffsetSeconds);
    }
}

