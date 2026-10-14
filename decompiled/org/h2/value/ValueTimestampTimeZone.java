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

public final class ValueTimestampTimeZone
extends Value {
    public static final int DEFAULT_PRECISION = 32;
    public static final int MAXIMUM_PRECISION = 35;
    private final long dateValue;
    private final long timeNanos;
    private final int timeZoneOffsetSeconds;

    private ValueTimestampTimeZone(long l, long l2, int n) {
        if (l < -511999999967L || l > 512000000415L) {
            throw new IllegalArgumentException("dateValue out of range " + l);
        }
        if (l2 < 0L || l2 >= 86400000000000L) {
            throw new IllegalArgumentException("timeNanos out of range " + l2);
        }
        if (n < -64800 || n > 64800) {
            throw new IllegalArgumentException("timeZoneOffsetSeconds out of range " + n);
        }
        this.dateValue = l;
        this.timeNanos = l2;
        this.timeZoneOffsetSeconds = n;
    }

    public static ValueTimestampTimeZone fromDateValueAndNanos(long l, long l2, int n) {
        return (ValueTimestampTimeZone)Value.cache(new ValueTimestampTimeZone(l, l2, n));
    }

    public static ValueTimestampTimeZone parse(String string, CastDataProvider castDataProvider) {
        try {
            return (ValueTimestampTimeZone)DateTimeUtils.parseTimestamp(string, castDataProvider, true);
        }
        catch (Exception exception) {
            throw DbException.get(22007, exception, "TIMESTAMP WITH TIME ZONE", string);
        }
    }

    public long getDateValue() {
        return this.dateValue;
    }

    public long getTimeNanos() {
        return this.timeNanos;
    }

    public int getTimeZoneOffsetSeconds() {
        return this.timeZoneOffsetSeconds;
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_TIMESTAMP_TZ;
    }

    @Override
    public int getValueType() {
        return 21;
    }

    @Override
    public int getMemory() {
        return 40;
    }

    @Override
    public String getString() {
        return this.toString(new StringBuilder(35), false).toString();
    }

    public String getISOString() {
        return this.toString(new StringBuilder(35), true).toString();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return this.toString(stringBuilder.append("TIMESTAMP WITH TIME ZONE '"), false).append('\'');
    }

    private StringBuilder toString(StringBuilder stringBuilder, boolean bl) {
        DateTimeUtils.appendDate(stringBuilder, this.dateValue).append(bl ? (char)'T' : ' ');
        DateTimeUtils.appendTime(stringBuilder, this.timeNanos);
        return DateTimeUtils.appendTimeZone(stringBuilder, this.timeZoneOffsetSeconds);
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value;
        long l = this.dateValue;
        long l2 = this.timeNanos - (long)this.timeZoneOffsetSeconds * 1000000000L;
        if (l2 < 0L) {
            l2 += 86400000000000L;
            l = DateTimeUtils.decrementDateValue(l);
        } else if (l2 >= 86400000000000L) {
            l2 -= 86400000000000L;
            l = DateTimeUtils.incrementDateValue(l);
        }
        long l3 = valueTimestampTimeZone.dateValue;
        long l4 = valueTimestampTimeZone.timeNanos - (long)valueTimestampTimeZone.timeZoneOffsetSeconds * 1000000000L;
        if (l4 < 0L) {
            l4 += 86400000000000L;
            l3 = DateTimeUtils.decrementDateValue(l3);
        } else if (l4 >= 86400000000000L) {
            l4 -= 86400000000000L;
            l3 = DateTimeUtils.incrementDateValue(l3);
        }
        int n = Long.compare(l, l3);
        if (n != 0) {
            return n;
        }
        return Long.compare(l2, l4);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ValueTimestampTimeZone)) {
            return false;
        }
        ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)object;
        return this.dateValue == valueTimestampTimeZone.dateValue && this.timeNanos == valueTimestampTimeZone.timeNanos && this.timeZoneOffsetSeconds == valueTimestampTimeZone.timeZoneOffsetSeconds;
    }

    @Override
    public int hashCode() {
        return (int)(this.dateValue ^ this.dateValue >>> 32 ^ this.timeNanos ^ this.timeNanos >>> 32 ^ (long)this.timeZoneOffsetSeconds);
    }
}

