/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.h2.engine.CastDataProvider;
import org.h2.util.DateTimeUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDate;
import org.h2.value.ValueNull;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;

public final class LegacyDateTimeUtils {
    public static final java.sql.Date PROLEPTIC_GREGORIAN_CHANGE = new java.sql.Date(Long.MIN_VALUE);
    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    private LegacyDateTimeUtils() {
    }

    public static ValueDate fromDate(CastDataProvider castDataProvider, TimeZone timeZone, java.sql.Date date) {
        long l = date.getTime();
        return ValueDate.fromDateValue(LegacyDateTimeUtils.dateValueFromLocalMillis(l + (long)(timeZone == null ? LegacyDateTimeUtils.getTimeZoneOffsetMillis(castDataProvider, l) : timeZone.getOffset(l))));
    }

    public static ValueTime fromTime(CastDataProvider castDataProvider, TimeZone timeZone, Time time) {
        long l = time.getTime();
        return ValueTime.fromNanos(LegacyDateTimeUtils.nanosFromLocalMillis(l + (long)(timeZone == null ? LegacyDateTimeUtils.getTimeZoneOffsetMillis(castDataProvider, l) : timeZone.getOffset(l))));
    }

    public static ValueTimestamp fromTimestamp(CastDataProvider castDataProvider, TimeZone timeZone, Timestamp timestamp) {
        long l = timestamp.getTime();
        return LegacyDateTimeUtils.timestampFromLocalMillis(l + (long)(timeZone == null ? LegacyDateTimeUtils.getTimeZoneOffsetMillis(castDataProvider, l) : timeZone.getOffset(l)), timestamp.getNanos() % 1000000);
    }

    public static ValueTimestamp fromTimestamp(CastDataProvider castDataProvider, long l, int n) {
        return LegacyDateTimeUtils.timestampFromLocalMillis(l + (long)LegacyDateTimeUtils.getTimeZoneOffsetMillis(castDataProvider, l), n);
    }

    private static ValueTimestamp timestampFromLocalMillis(long l, int n) {
        long l2 = LegacyDateTimeUtils.dateValueFromLocalMillis(l);
        long l3 = (long)n + LegacyDateTimeUtils.nanosFromLocalMillis(l);
        return ValueTimestamp.fromDateValueAndNanos(l2, l3);
    }

    public static long dateValueFromLocalMillis(long l) {
        long l2 = l / 86400000L;
        if (l < 0L && l2 * 86400000L != l) {
            --l2;
        }
        return DateTimeUtils.dateValueFromAbsoluteDay(l2);
    }

    public static long nanosFromLocalMillis(long l) {
        if ((l %= 86400000L) < 0L) {
            l += 86400000L;
        }
        return l * 1000000L;
    }

    public static java.sql.Date toDate(CastDataProvider castDataProvider, TimeZone timeZone, Value value) {
        return value != ValueNull.INSTANCE ? new java.sql.Date(LegacyDateTimeUtils.getMillis(castDataProvider, timeZone, value.convertToDate(castDataProvider).getDateValue(), 0L)) : null;
    }

    public static Time toTime(CastDataProvider castDataProvider, TimeZone timeZone, Value value) {
        switch (value.getValueType()) {
            case 0: {
                return null;
            }
            default: {
                value = value.convertTo(TypeInfo.TYPE_TIME, castDataProvider);
            }
            case 18: 
        }
        return new Time(LegacyDateTimeUtils.getMillis(castDataProvider, timeZone, 1008673L, ((ValueTime)value).getNanos()));
    }

    public static Timestamp toTimestamp(CastDataProvider castDataProvider, TimeZone timeZone, Value value) {
        switch (value.getValueType()) {
            case 0: {
                return null;
            }
            default: {
                value = value.convertTo(TypeInfo.TYPE_TIMESTAMP, castDataProvider);
            }
            case 20: {
                ValueTimestamp valueTimestamp = (ValueTimestamp)value;
                long l = valueTimestamp.getTimeNanos();
                Timestamp timestamp = new Timestamp(LegacyDateTimeUtils.getMillis(castDataProvider, timeZone, valueTimestamp.getDateValue(), l));
                timestamp.setNanos((int)(l % 1000000000L));
                return timestamp;
            }
            case 21: 
        }
        ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value;
        long l = valueTimestampTimeZone.getTimeNanos();
        Timestamp timestamp = new Timestamp(DateTimeUtils.absoluteDayFromDateValue(valueTimestampTimeZone.getDateValue()) * 86400000L + l / 1000000L - (long)(valueTimestampTimeZone.getTimeZoneOffsetSeconds() * 1000));
        timestamp.setNanos((int)(l % 1000000000L));
        return timestamp;
    }

    public static long getMillis(CastDataProvider castDataProvider, TimeZone timeZone, long l, long l2) {
        return (timeZone == null ? (castDataProvider != null ? castDataProvider.currentTimeZone() : DateTimeUtils.getTimeZone()) : TimeZoneProvider.ofId(timeZone.getID())).getEpochSecondsFromLocal(l, l2) * 1000L + l2 / 1000000L % 1000L;
    }

    public static int getTimeZoneOffsetMillis(CastDataProvider castDataProvider, long l) {
        long l2 = l / 1000L;
        if (l < 0L && l2 * 1000L != l) {
            --l2;
        }
        return (castDataProvider != null ? castDataProvider.currentTimeZone() : DateTimeUtils.getTimeZone()).getTimeZoneOffsetUTC(l2) * 1000;
    }

    public static Value legacyObjectToValue(CastDataProvider castDataProvider, Object object) {
        if (object instanceof java.sql.Date) {
            return LegacyDateTimeUtils.fromDate(castDataProvider, null, (java.sql.Date)object);
        }
        if (object instanceof Time) {
            return LegacyDateTimeUtils.fromTime(castDataProvider, null, (Time)object);
        }
        if (object instanceof Timestamp) {
            return LegacyDateTimeUtils.fromTimestamp(castDataProvider, null, (Timestamp)object);
        }
        if (object instanceof Date) {
            return LegacyDateTimeUtils.fromTimestamp(castDataProvider, ((Date)object).getTime(), 0);
        }
        if (object instanceof Calendar) {
            Calendar calendar = (Calendar)object;
            long l = calendar.getTimeInMillis();
            return LegacyDateTimeUtils.timestampFromLocalMillis(l + (long)calendar.getTimeZone().getOffset(l), 0);
        }
        return null;
    }

    public static <T> T valueToLegacyType(Class<T> clazz, Value value, CastDataProvider castDataProvider) {
        if (clazz == java.sql.Date.class) {
            return (T)LegacyDateTimeUtils.toDate(castDataProvider, null, value);
        }
        if (clazz == Time.class) {
            return (T)LegacyDateTimeUtils.toTime(castDataProvider, null, value);
        }
        if (clazz == Timestamp.class) {
            return (T)LegacyDateTimeUtils.toTimestamp(castDataProvider, null, value);
        }
        if (clazz == Date.class) {
            return (T)new Date(LegacyDateTimeUtils.toTimestamp(castDataProvider, null, value).getTime());
        }
        if (clazz == Calendar.class) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setGregorianChange(PROLEPTIC_GREGORIAN_CHANGE);
            gregorianCalendar.setTime(LegacyDateTimeUtils.toTimestamp(castDataProvider, gregorianCalendar.getTimeZone(), value));
            return (T)gregorianCalendar;
        }
        return null;
    }

    public static TypeInfo legacyClassToType(Class<?> clazz) {
        if (java.sql.Date.class.isAssignableFrom(clazz)) {
            return TypeInfo.TYPE_DATE;
        }
        if (Time.class.isAssignableFrom(clazz)) {
            return TypeInfo.TYPE_TIME;
        }
        if (Date.class.isAssignableFrom(clazz) || Calendar.class.isAssignableFrom(clazz)) {
            return TypeInfo.TYPE_TIMESTAMP;
        }
        return null;
    }
}

