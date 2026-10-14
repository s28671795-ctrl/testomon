/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.h2.api.IntervalQualifier;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.util.IntervalUtils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDate;
import org.h2.value.ValueInterval;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;

public class JSR310Utils {
    private static final long MIN_DATE_VALUE = -511999999455L;
    private static final long MAX_DATE_VALUE = 511999999903L;
    private static final long MIN_INSTANT_SECOND = -31557014167219200L;
    private static final long MAX_INSTANT_SECOND = 31556889864403199L;

    private JSR310Utils() {
    }

    public static LocalDate valueToLocalDate(Value value, CastDataProvider castDataProvider) {
        long l = value.convertToDate(castDataProvider).getDateValue();
        if (l > 511999999903L) {
            return LocalDate.MAX;
        }
        if (l < -511999999455L) {
            return LocalDate.MIN;
        }
        return LocalDate.of(DateTimeUtils.yearFromDateValue(l), DateTimeUtils.monthFromDateValue(l), DateTimeUtils.dayFromDateValue(l));
    }

    public static LocalTime valueToLocalTime(Value value, CastDataProvider castDataProvider) {
        return LocalTime.ofNanoOfDay(((ValueTime)value.convertTo(TypeInfo.TYPE_TIME, castDataProvider)).getNanos());
    }

    public static LocalDateTime valueToLocalDateTime(Value value, CastDataProvider castDataProvider) {
        ValueTimestamp valueTimestamp = (ValueTimestamp)value.convertTo(TypeInfo.TYPE_TIMESTAMP, castDataProvider);
        return JSR310Utils.localDateTimeFromDateNanos(valueTimestamp.getDateValue(), valueTimestamp.getTimeNanos());
    }

    public static Instant valueToInstant(Value value, CastDataProvider castDataProvider) {
        ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value.convertTo(TypeInfo.TYPE_TIMESTAMP_TZ, castDataProvider);
        long l = valueTimestampTimeZone.getTimeNanos();
        long l2 = DateTimeUtils.absoluteDayFromDateValue(valueTimestampTimeZone.getDateValue()) * 86400L + l / 1000000000L - (long)valueTimestampTimeZone.getTimeZoneOffsetSeconds();
        if (l2 > 31556889864403199L) {
            return Instant.MAX;
        }
        if (l2 < -31557014167219200L) {
            return Instant.MIN;
        }
        return Instant.ofEpochSecond(l2, l % 1000000000L);
    }

    public static OffsetDateTime valueToOffsetDateTime(Value value, CastDataProvider castDataProvider) {
        ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value.convertTo(TypeInfo.TYPE_TIMESTAMP_TZ, castDataProvider);
        return OffsetDateTime.of(JSR310Utils.localDateTimeFromDateNanos(valueTimestampTimeZone.getDateValue(), valueTimestampTimeZone.getTimeNanos()), ZoneOffset.ofTotalSeconds(valueTimestampTimeZone.getTimeZoneOffsetSeconds()));
    }

    public static ZonedDateTime valueToZonedDateTime(Value value, CastDataProvider castDataProvider) {
        ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value.convertTo(TypeInfo.TYPE_TIMESTAMP_TZ, castDataProvider);
        return ZonedDateTime.of(JSR310Utils.localDateTimeFromDateNanos(valueTimestampTimeZone.getDateValue(), valueTimestampTimeZone.getTimeNanos()), ZoneOffset.ofTotalSeconds(valueTimestampTimeZone.getTimeZoneOffsetSeconds()));
    }

    public static OffsetTime valueToOffsetTime(Value value, CastDataProvider castDataProvider) {
        ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value.convertTo(TypeInfo.TYPE_TIME_TZ, castDataProvider);
        return OffsetTime.of(LocalTime.ofNanoOfDay(valueTimeTimeZone.getNanos()), ZoneOffset.ofTotalSeconds(valueTimeTimeZone.getTimeZoneOffsetSeconds()));
    }

    public static Period valueToPeriod(Value value) {
        if (!(value instanceof ValueInterval)) {
            value = value.convertTo(TypeInfo.TYPE_INTERVAL_YEAR_TO_MONTH);
        }
        if (!DataType.isYearMonthIntervalType(value.getValueType())) {
            throw DbException.get(22018, (Throwable)null, value.getString());
        }
        ValueInterval valueInterval = (ValueInterval)value;
        IntervalQualifier intervalQualifier = valueInterval.getQualifier();
        boolean bl = valueInterval.isNegative();
        long l = valueInterval.getLeading();
        long l2 = valueInterval.getRemaining();
        int n = Value.convertToInt(IntervalUtils.yearsFromInterval(intervalQualifier, bl, l, l2), null);
        int n2 = Value.convertToInt(IntervalUtils.monthsFromInterval(intervalQualifier, bl, l, l2), null);
        return Period.of(n, n2, 0);
    }

    public static Duration valueToDuration(Value value) {
        if (!(value instanceof ValueInterval)) {
            value = value.convertTo(TypeInfo.TYPE_INTERVAL_DAY_TO_SECOND);
        }
        if (DataType.isYearMonthIntervalType(value.getValueType())) {
            throw DbException.get(22018, (Throwable)null, value.getString());
        }
        BigInteger[] bigIntegerArray = IntervalUtils.intervalToAbsolute((ValueInterval)value).divideAndRemainder(BigInteger.valueOf(1000000000L));
        return Duration.ofSeconds(bigIntegerArray[0].longValue(), bigIntegerArray[1].longValue());
    }

    public static ValueDate localDateToValue(LocalDate localDate) {
        return ValueDate.fromDateValue(DateTimeUtils.dateValue(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()));
    }

    public static ValueTime localTimeToValue(LocalTime localTime) {
        return ValueTime.fromNanos(localTime.toNanoOfDay());
    }

    public static ValueTimestamp localDateTimeToValue(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return ValueTimestamp.fromDateValueAndNanos(DateTimeUtils.dateValue(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()), localDateTime.toLocalTime().toNanoOfDay());
    }

    public static ValueTimestampTimeZone instantToValue(Instant instant) {
        long l = instant.getEpochSecond();
        int n = instant.getNano();
        long l2 = l / 86400L;
        if (l < 0L && l2 * 86400L != l) {
            --l2;
        }
        long l3 = (l - l2 * 86400L) * 1000000000L + (long)n;
        return ValueTimestampTimeZone.fromDateValueAndNanos(DateTimeUtils.dateValueFromAbsoluteDay(l2), l3, 0);
    }

    public static ValueTimestampTimeZone offsetDateTimeToValue(OffsetDateTime offsetDateTime) {
        LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
        LocalDate localDate = localDateTime.toLocalDate();
        return ValueTimestampTimeZone.fromDateValueAndNanos(DateTimeUtils.dateValue(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()), localDateTime.toLocalTime().toNanoOfDay(), offsetDateTime.getOffset().getTotalSeconds());
    }

    public static ValueTimestampTimeZone zonedDateTimeToValue(ZonedDateTime zonedDateTime) {
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        LocalDate localDate = localDateTime.toLocalDate();
        return ValueTimestampTimeZone.fromDateValueAndNanos(DateTimeUtils.dateValue(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()), localDateTime.toLocalTime().toNanoOfDay(), zonedDateTime.getOffset().getTotalSeconds());
    }

    public static ValueTimeTimeZone offsetTimeToValue(OffsetTime offsetTime) {
        return ValueTimeTimeZone.fromNanos(offsetTime.toLocalTime().toNanoOfDay(), offsetTime.getOffset().getTotalSeconds());
    }

    private static LocalDateTime localDateTimeFromDateNanos(long l, long l2) {
        if (l > 511999999903L) {
            return LocalDateTime.MAX;
        }
        if (l < -511999999455L) {
            return LocalDateTime.MIN;
        }
        return LocalDateTime.of(LocalDate.of(DateTimeUtils.yearFromDateValue(l), DateTimeUtils.monthFromDateValue(l), DateTimeUtils.dayFromDateValue(l)), LocalTime.ofNanoOfDay(l2));
    }

    public static ValueInterval periodToValue(Period period) {
        IntervalQualifier intervalQualifier;
        int n = period.getDays();
        if (n != 0) {
            throw DbException.getInvalidValueException("Period.days", n);
        }
        int n2 = period.getYears();
        int n3 = period.getMonths();
        boolean bl = false;
        long l = 0L;
        long l2 = 0L;
        if (n2 == 0) {
            if ((long)n3 == 0L) {
                intervalQualifier = IntervalQualifier.YEAR_TO_MONTH;
            } else {
                intervalQualifier = IntervalQualifier.MONTH;
                l = n3;
                if (l < 0L) {
                    l = -l;
                    bl = true;
                }
            }
        } else if ((long)n3 == 0L) {
            intervalQualifier = IntervalQualifier.YEAR;
            l = n2;
            if (l < 0L) {
                l = -l;
                bl = true;
            }
        } else {
            intervalQualifier = IntervalQualifier.YEAR_TO_MONTH;
            l = n2 * 12 + n3;
            if (l < 0L) {
                l = -l;
                bl = true;
            }
            l2 = l % 12L;
            l /= 12L;
        }
        return ValueInterval.from(intervalQualifier, bl, l, l2);
    }

    public static ValueInterval durationToValue(Duration duration) {
        long l = duration.getSeconds();
        int n = duration.getNano();
        boolean bl = l < 0L;
        l = Math.abs(l);
        if (bl && n != 0) {
            n = 1000000000 - n;
            --l;
        }
        return ValueInterval.from(IntervalQualifier.SECOND, bl, l, n);
    }
}

