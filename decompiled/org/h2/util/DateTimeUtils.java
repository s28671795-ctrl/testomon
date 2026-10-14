/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.time.Instant;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.StringUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDate;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;

public class DateTimeUtils {
    public static final long MILLIS_PER_DAY = 86400000L;
    public static final long SECONDS_PER_DAY = 86400L;
    public static final long NANOS_PER_SECOND = 1000000000L;
    public static final long NANOS_PER_MINUTE = 60000000000L;
    public static final long NANOS_PER_HOUR = 3600000000000L;
    public static final long NANOS_PER_DAY = 86400000000000L;
    public static final int SHIFT_YEAR = 9;
    public static final int SHIFT_MONTH = 5;
    public static final int EPOCH_DATE_VALUE = 1008673;
    public static final long MIN_DATE_VALUE = -511999999967L;
    public static final long MAX_DATE_VALUE = 512000000415L;
    private static final int[] NORMAL_DAYS_PER_MONTH = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static final int[] FRACTIONAL_SECONDS_TABLE = new int[]{1000000000, 100000000, 10000000, 1000000, 100000, 10000, 1000, 100, 10, 1};
    private static volatile TimeZoneProvider LOCAL;

    private DateTimeUtils() {
    }

    public static void resetCalendar() {
        LOCAL = null;
    }

    public static TimeZoneProvider getTimeZone() {
        TimeZoneProvider timeZoneProvider = LOCAL;
        if (timeZoneProvider == null) {
            LOCAL = timeZoneProvider = TimeZoneProvider.getDefault();
        }
        return timeZoneProvider;
    }

    public static ValueTimestampTimeZone currentTimestamp(TimeZoneProvider timeZoneProvider) {
        return DateTimeUtils.currentTimestamp(timeZoneProvider, Instant.now());
    }

    public static ValueTimestampTimeZone currentTimestamp(TimeZoneProvider timeZoneProvider, Instant instant) {
        long l = instant.getEpochSecond();
        int n = timeZoneProvider.getTimeZoneOffsetUTC(l);
        return ValueTimestampTimeZone.fromDateValueAndNanos(DateTimeUtils.dateValueFromAbsoluteDay((l += (long)n) / 86400L), l % 86400L * 1000000000L + (long)instant.getNano(), n);
    }

    public static long parseDateValue(String string, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        if (string.charAt(n) == '+') {
            ++n;
        }
        if ((n6 = string.indexOf(45, n + 1)) > 0) {
            n5 = n6 + 1;
            n4 = string.indexOf(45, n5);
            if (n4 <= n5) {
                throw new IllegalArgumentException(string);
            }
            n3 = n4 + 1;
        } else {
            n3 = n2 - 2;
            n4 = n3;
            n5 = n4 - 2;
            n6 = n5;
            if (n6 < n + 3) {
                throw new IllegalArgumentException(string);
            }
        }
        int n7 = Integer.parseInt(string.substring(n, n6));
        int n8 = StringUtils.parseUInt31(string, n5, n4);
        int n9 = StringUtils.parseUInt31(string, n3, n2);
        if (!DateTimeUtils.isValidDate(n7, n8, n9)) {
            throw new IllegalArgumentException(n7 + "-" + n8 + "-" + n9);
        }
        return DateTimeUtils.dateValue(n7, n8, n9);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static long parseTimeNanos(String string, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9 = string.indexOf(58, n);
        if (n9 > 0) {
            n8 = n9 + 1;
            n7 = string.indexOf(58, n8);
            if (n7 >= n8) {
                n6 = n7 + 1;
                n5 = string.indexOf(46, n6);
            } else {
                n7 = n2;
                n5 = -1;
                n6 = -1;
            }
        } else {
            int n10 = string.indexOf(46, n);
            if (n10 < 0) {
                n9 = n8 = n + 2;
                n7 = n8 + 2;
                int n11 = n2 - n;
                if (n11 == 6) {
                    n6 = n7;
                    n5 = -1;
                } else {
                    if (n11 != 4) throw new IllegalArgumentException(string);
                    n5 = -1;
                    n6 = -1;
                }
            } else if (n10 >= n + 6) {
                if (n10 - n != 6) {
                    throw new IllegalArgumentException(string);
                }
                n9 = n8 = n + 2;
                n7 = n6 = n8 + 2;
                n5 = n10;
            } else {
                n9 = n10;
                n8 = n9 + 1;
                n7 = string.indexOf(46, n8);
                if (n7 <= n8) {
                    throw new IllegalArgumentException(string);
                }
                n6 = n7 + 1;
                n5 = string.indexOf(46, n6);
            }
        }
        int n12 = StringUtils.parseUInt31(string, n, n9);
        if (n12 >= 24) {
            throw new IllegalArgumentException(string);
        }
        int n13 = StringUtils.parseUInt31(string, n8, n7);
        if (n6 > 0) {
            if (n5 < 0) {
                n4 = StringUtils.parseUInt31(string, n6, n2);
                n3 = 0;
            } else {
                n4 = StringUtils.parseUInt31(string, n6, n5);
                n3 = DateTimeUtils.parseNanos(string, n5 + 1, n2);
            }
        } else {
            n3 = 0;
            n4 = 0;
        }
        if (n13 < 60 && n4 < 60) return (((long)n12 * 60L + (long)n13) * 60L + (long)n4) * 1000000000L + (long)n3;
        throw new IllegalArgumentException(string);
    }

    static int parseNanos(String string, int n, int n2) {
        if (n >= n2) {
            throw new IllegalArgumentException(string);
        }
        int n3 = 0;
        int n4 = 100000000;
        do {
            char c;
            if ((c = string.charAt(n)) < '0' || c > '9') {
                throw new IllegalArgumentException(string);
            }
            n3 += n4 * (c - 48);
            n4 /= 10;
        } while (++n < n2);
        return n3;
    }

    public static Value parseTimestamp(String string, CastDataProvider castDataProvider, boolean bl) {
        int n;
        long l;
        int n2;
        int n3 = string.indexOf(32);
        if (n3 < 0 && (n3 = string.indexOf(84)) < 0 && castDataProvider != null && castDataProvider.getMode().allowDB2TimestampFormat) {
            n3 = string.indexOf(45, string.indexOf(45, string.indexOf(45) + 1) + 1);
        }
        if (n3 < 0) {
            n3 = string.length();
            n2 = -1;
        } else {
            n2 = n3 + 1;
        }
        long l2 = DateTimeUtils.parseDateValue(string, 0, n3);
        TimeZoneProvider timeZoneProvider = null;
        if (n2 < 0) {
            l = 0L;
        } else {
            ++n3;
            if (string.endsWith("Z")) {
                timeZoneProvider = TimeZoneProvider.UTC;
                n = string.length() - 1;
            } else {
                int n4 = string.indexOf(43, n3);
                if (n4 < 0) {
                    n4 = string.indexOf(45, n3);
                }
                if (n4 >= 0) {
                    int n5 = string.indexOf(91, n4 + 1);
                    if (n5 < 0) {
                        n5 = string.length();
                    }
                    timeZoneProvider = TimeZoneProvider.ofId(string.substring(n4, n5));
                    if (string.charAt(n4 - 1) == ' ') {
                        --n4;
                    }
                    n = n4;
                } else {
                    n4 = string.indexOf(32, n3);
                    if (n4 > 0) {
                        timeZoneProvider = TimeZoneProvider.ofId(string.substring(n4 + 1));
                        n = n4;
                    } else {
                        n = string.length();
                    }
                }
            }
            l = DateTimeUtils.parseTimeNanos(string, n3, n);
        }
        if (bl) {
            if (timeZoneProvider == null) {
                timeZoneProvider = castDataProvider != null ? castDataProvider.currentTimeZone() : DateTimeUtils.getTimeZone();
            }
            n = timeZoneProvider != TimeZoneProvider.UTC ? timeZoneProvider.getTimeZoneOffsetUTC(timeZoneProvider.getEpochSecondsFromLocal(l2, l)) : 0;
            return ValueTimestampTimeZone.fromDateValueAndNanos(l2, l, n);
        }
        if (timeZoneProvider != null) {
            long l3 = timeZoneProvider.getEpochSecondsFromLocal(l2, l);
            l3 += (long)(castDataProvider != null ? castDataProvider.currentTimeZone() : DateTimeUtils.getTimeZone()).getTimeZoneOffsetUTC(l3);
            l2 = DateTimeUtils.dateValueFromLocalSeconds(l3);
            l = l % 1000000000L + DateTimeUtils.nanosFromLocalSeconds(l3);
        }
        return ValueTimestamp.fromDateValueAndNanos(l2, l);
    }

    public static Value parseTime(String string, CastDataProvider castDataProvider, boolean bl) {
        int n;
        TimeZoneProvider timeZoneProvider = null;
        if (string.endsWith("Z")) {
            timeZoneProvider = TimeZoneProvider.UTC;
            n = string.length() - 1;
        } else {
            int n2 = string.indexOf(43, 1);
            if (n2 < 0) {
                n2 = string.indexOf(45, 1);
            }
            if (n2 >= 0) {
                timeZoneProvider = TimeZoneProvider.ofId(string.substring(n2));
                if (string.charAt(n2 - 1) == ' ') {
                    --n2;
                }
                n = n2;
            } else {
                n2 = string.indexOf(32, 1);
                if (n2 > 0) {
                    timeZoneProvider = TimeZoneProvider.ofId(string.substring(n2 + 1));
                    n = n2;
                } else {
                    n = string.length();
                }
            }
            if (timeZoneProvider != null && !timeZoneProvider.hasFixedOffset()) {
                throw DbException.get(22007, "TIME WITH TIME ZONE", string);
            }
        }
        long l = DateTimeUtils.parseTimeNanos(string, 0, n);
        if (bl) {
            return ValueTimeTimeZone.fromNanos(l, timeZoneProvider != null ? timeZoneProvider.getTimeZoneOffsetUTC(0L) : (castDataProvider != null ? castDataProvider.currentTimestamp() : DateTimeUtils.currentTimestamp(DateTimeUtils.getTimeZone())).getTimeZoneOffsetSeconds());
        }
        if (timeZoneProvider != null) {
            l = DateTimeUtils.normalizeNanosOfDay(l + (long)((castDataProvider != null ? castDataProvider.currentTimestamp() : DateTimeUtils.currentTimestamp(DateTimeUtils.getTimeZone())).getTimeZoneOffsetSeconds() - timeZoneProvider.getTimeZoneOffsetUTC(0L)) * 1000000000L);
        }
        return ValueTime.fromNanos(l);
    }

    public static long getEpochSeconds(long l, long l2, int n) {
        return DateTimeUtils.absoluteDayFromDateValue(l) * 86400L + l2 / 1000000000L - (long)n;
    }

    public static long[] dateAndTimeFromValue(Value value, CastDataProvider castDataProvider) {
        long l = 1008673L;
        long l2 = 0L;
        if (value instanceof ValueTimestamp) {
            ValueTimestamp valueTimestamp = (ValueTimestamp)value;
            l = valueTimestamp.getDateValue();
            l2 = valueTimestamp.getTimeNanos();
        } else if (value instanceof ValueDate) {
            l = ((ValueDate)value).getDateValue();
        } else if (value instanceof ValueTime) {
            l2 = ((ValueTime)value).getNanos();
        } else if (value instanceof ValueTimestampTimeZone) {
            ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value;
            l = valueTimestampTimeZone.getDateValue();
            l2 = valueTimestampTimeZone.getTimeNanos();
        } else if (value instanceof ValueTimeTimeZone) {
            l2 = ((ValueTimeTimeZone)value).getNanos();
        } else {
            ValueTimestamp valueTimestamp = (ValueTimestamp)value.convertTo(TypeInfo.TYPE_TIMESTAMP, castDataProvider);
            l = valueTimestamp.getDateValue();
            l2 = valueTimestamp.getTimeNanos();
        }
        return new long[]{l, l2};
    }

    public static Value dateTimeToValue(Value value, long l, long l2) {
        switch (value.getValueType()) {
            case 17: {
                return ValueDate.fromDateValue(l);
            }
            case 18: {
                return ValueTime.fromNanos(l2);
            }
            case 19: {
                return ValueTimeTimeZone.fromNanos(l2, ((ValueTimeTimeZone)value).getTimeZoneOffsetSeconds());
            }
            default: {
                return ValueTimestamp.fromDateValueAndNanos(l, l2);
            }
            case 21: 
        }
        return ValueTimestampTimeZone.fromDateValueAndNanos(l, l2, ((ValueTimestampTimeZone)value).getTimeZoneOffsetSeconds());
    }

    public static int getDayOfWeek(long l, int n) {
        return DateTimeUtils.getDayOfWeekFromAbsolute(DateTimeUtils.absoluteDayFromDateValue(l), n);
    }

    public static int getDayOfWeekFromAbsolute(long l, int n) {
        return l >= 0L ? (int)((l - (long)n + 11L) % 7L) + 1 : (int)((l - (long)n - 2L) % 7L) + 7;
    }

    public static int getDayOfYear(long l) {
        int n = DateTimeUtils.monthFromDateValue(l);
        int n2 = (367 * n - 362) / 12 + DateTimeUtils.dayFromDateValue(l);
        if (n > 2) {
            --n2;
            long l2 = DateTimeUtils.yearFromDateValue(l);
            if ((l2 & 3L) != 0L || l2 % 100L == 0L && l2 % 400L != 0L) {
                --n2;
            }
        }
        return n2;
    }

    public static int getIsoDayOfWeek(long l) {
        return DateTimeUtils.getDayOfWeek(l, 1);
    }

    public static int getIsoWeekOfYear(long l) {
        return DateTimeUtils.getWeekOfYear(l, 1, 4);
    }

    public static int getIsoWeekYear(long l) {
        return DateTimeUtils.getWeekYear(l, 1, 4);
    }

    public static int getSundayDayOfWeek(long l) {
        return DateTimeUtils.getDayOfWeek(l, 0);
    }

    public static int getWeekOfYear(long l, int n, int n2) {
        int n3;
        long l2;
        long l3 = DateTimeUtils.absoluteDayFromDateValue(l);
        if (l3 - (l2 = DateTimeUtils.getWeekYearAbsoluteStart(n3 = DateTimeUtils.yearFromDateValue(l), n, n2)) < 0L) {
            l2 = DateTimeUtils.getWeekYearAbsoluteStart(n3 - 1, n, n2);
        } else if (DateTimeUtils.monthFromDateValue(l) == 12 && 24 + n2 < DateTimeUtils.dayFromDateValue(l) && l3 >= DateTimeUtils.getWeekYearAbsoluteStart(n3 + 1, n, n2)) {
            return 1;
        }
        return (int)((l3 - l2) / 7L) + 1;
    }

    public static long getWeekYearAbsoluteStart(int n, int n2, int n3) {
        long l = DateTimeUtils.absoluteDayFromYear(n);
        int n4 = 8 - DateTimeUtils.getDayOfWeekFromAbsolute(l, n2);
        long l2 = l + (long)n4;
        if (n4 >= n3) {
            l2 -= 7L;
        }
        return l2;
    }

    public static int getWeekYear(long l, int n, int n2) {
        int n3;
        long l2;
        long l3 = DateTimeUtils.absoluteDayFromDateValue(l);
        if (l3 < (l2 = DateTimeUtils.getWeekYearAbsoluteStart(n3 = DateTimeUtils.yearFromDateValue(l), n, n2))) {
            return n3 - 1;
        }
        if (DateTimeUtils.monthFromDateValue(l) == 12 && 24 + n2 < DateTimeUtils.dayFromDateValue(l) && l3 >= DateTimeUtils.getWeekYearAbsoluteStart(n3 + 1, n, n2)) {
            return n3 + 1;
        }
        return n3;
    }

    public static int getDaysInMonth(int n, int n2) {
        if (n2 != 2) {
            return NORMAL_DAYS_PER_MONTH[n2];
        }
        return DateTimeUtils.isLeapYear(n) ? 29 : 28;
    }

    static boolean isLeapYear(int n) {
        return (n & 3) == 0 && (n % 100 != 0 || n % 400 == 0);
    }

    public static boolean isValidDate(int n, int n2, int n3) {
        return n2 >= 1 && n2 <= 12 && n3 >= 1 && n3 <= DateTimeUtils.getDaysInMonth(n, n2);
    }

    public static int yearFromDateValue(long l) {
        return (int)(l >>> 9);
    }

    public static int monthFromDateValue(long l) {
        return (int)(l >>> 5) & 0xF;
    }

    public static int dayFromDateValue(long l) {
        return (int)(l & 0x1FL);
    }

    public static long dateValue(long l, int n, int n2) {
        return l << 9 | (long)(n << 5) | (long)n2;
    }

    public static long dateValueFromDenormalizedDate(long l, long l2, int n) {
        long l3 = l2 - 1L;
        long l4 = l3 / 12L;
        if (l3 < 0L && l4 * 12L != l3) {
            --l4;
        }
        int n2 = (int)(l + l4);
        int n3 = (int)(l2 - l4 * 12L);
        if (n < 1) {
            n = 1;
        } else {
            int n4 = DateTimeUtils.getDaysInMonth(n2, n3);
            if (n > n4) {
                n = n4;
            }
        }
        return DateTimeUtils.dateValue(n2, n3, n);
    }

    public static long dateValueFromLocalSeconds(long l) {
        long l2 = l / 86400L;
        if (l < 0L && l2 * 86400L != l) {
            --l2;
        }
        return DateTimeUtils.dateValueFromAbsoluteDay(l2);
    }

    public static long nanosFromLocalSeconds(long l) {
        if ((l %= 86400L) < 0L) {
            l += 86400L;
        }
        return l * 1000000000L;
    }

    public static long normalizeNanosOfDay(long l) {
        if ((l %= 86400000000000L) < 0L) {
            l += 86400000000000L;
        }
        return l;
    }

    public static long absoluteDayFromYear(long l) {
        long l2 = 365L * l - 719528L;
        l2 = l >= 0L ? (l2 += (l + 3L) / 4L - (l + 99L) / 100L + (l + 399L) / 400L) : (l2 -= l / -4L - l / -100L + l / -400L);
        return l2;
    }

    public static long absoluteDayFromDateValue(long l) {
        return DateTimeUtils.absoluteDay(DateTimeUtils.yearFromDateValue(l), DateTimeUtils.monthFromDateValue(l), DateTimeUtils.dayFromDateValue(l));
    }

    static long absoluteDay(long l, int n, int n2) {
        long l2 = DateTimeUtils.absoluteDayFromYear(l) + (long)((367 * n - 362) / 12) + (long)n2 - 1L;
        if (n > 2) {
            --l2;
            if ((l & 3L) != 0L || l % 100L == 0L && l % 400L != 0L) {
                --l2;
            }
        }
        return l2;
    }

    public static long dateValueFromAbsoluteDay(long l) {
        long l2;
        int n;
        long l3 = l + 719468L;
        long l4 = 0L;
        if (l3 < 0L) {
            l4 = (l3 + 1L) / 146097L - 1L;
            l3 -= l4 * 146097L;
            l4 *= 400L;
        }
        if ((n = (int)(l3 - (365L * (l2 = (400L * l3 + 591L) / 146097L) + l2 / 4L - l2 / 100L + l2 / 400L))) < 0) {
            n = (int)(l3 - (365L * --l2 + l2 / 4L - l2 / 100L + l2 / 400L));
        }
        l2 += l4;
        int n2 = (n * 5 + 2) / 153;
        n -= (n2 * 306 + 5) / 10 - 1;
        if (n2 >= 10) {
            ++l2;
            n2 -= 12;
        }
        return DateTimeUtils.dateValue(l2, n2 + 3, n);
    }

    public static long incrementDateValue(long l) {
        int n;
        int n2 = DateTimeUtils.dayFromDateValue(l);
        if (n2 < 28) {
            return l + 1L;
        }
        int n3 = DateTimeUtils.yearFromDateValue(l);
        if (n2 < DateTimeUtils.getDaysInMonth(n3, n = DateTimeUtils.monthFromDateValue(l))) {
            return l + 1L;
        }
        if (n < 12) {
            ++n;
        } else {
            n = 1;
            ++n3;
        }
        return DateTimeUtils.dateValue(n3, n, 1);
    }

    public static long decrementDateValue(long l) {
        if (DateTimeUtils.dayFromDateValue(l) > 1) {
            return l - 1L;
        }
        int n = DateTimeUtils.yearFromDateValue(l);
        int n2 = DateTimeUtils.monthFromDateValue(l);
        if (n2 > 1) {
            --n2;
        } else {
            n2 = 12;
            --n;
        }
        return DateTimeUtils.dateValue(n, n2, DateTimeUtils.getDaysInMonth(n, n2));
    }

    public static StringBuilder appendDate(StringBuilder stringBuilder, long l) {
        int n = DateTimeUtils.yearFromDateValue(l);
        if (n < 1000 && n > -1000) {
            if (n < 0) {
                stringBuilder.append('-');
                n = -n;
            }
            StringUtils.appendZeroPadded(stringBuilder, 4, n);
        } else {
            stringBuilder.append(n);
        }
        StringUtils.appendTwoDigits(stringBuilder.append('-'), DateTimeUtils.monthFromDateValue(l)).append('-');
        return StringUtils.appendTwoDigits(stringBuilder, DateTimeUtils.dayFromDateValue(l));
    }

    public static StringBuilder appendTime(StringBuilder stringBuilder, long l) {
        if (l < 0L) {
            stringBuilder.append('-');
            l = -l;
        }
        long l2 = -l / -1000000000L;
        l -= l2 * 1000000000L;
        int n = (int)(l2 / 60L);
        l2 -= (long)(n * 60);
        int n2 = n / 60;
        StringUtils.appendTwoDigits(stringBuilder, n2).append(':');
        StringUtils.appendTwoDigits(stringBuilder, n -= n2 * 60).append(':');
        StringUtils.appendTwoDigits(stringBuilder, (int)l2);
        return DateTimeUtils.appendNanos(stringBuilder, (int)l);
    }

    static StringBuilder appendNanos(StringBuilder stringBuilder, int n) {
        if (n > 0) {
            stringBuilder.append('.');
            int n2 = 1;
            while (n < FRACTIONAL_SECONDS_TABLE[n2]) {
                stringBuilder.append('0');
                ++n2;
            }
            if (n % 1000 == 0 && (n /= 1000) % 1000 == 0) {
                n /= 1000;
            }
            if (n % 10 == 0 && (n /= 10) % 10 == 0) {
                n /= 10;
            }
            stringBuilder.append(n);
        }
        return stringBuilder;
    }

    public static StringBuilder appendTimeZone(StringBuilder stringBuilder, int n) {
        if (n < 0) {
            stringBuilder.append('-');
            n = -n;
        } else {
            stringBuilder.append('+');
        }
        int n2 = n / 3600;
        StringUtils.appendTwoDigits(stringBuilder, n2);
        if ((n -= n2 * 3600) != 0) {
            n2 = n / 60;
            StringUtils.appendTwoDigits(stringBuilder.append(':'), n2);
            if ((n -= n2 * 60) != 0) {
                StringUtils.appendTwoDigits(stringBuilder.append(':'), n);
            }
        }
        return stringBuilder;
    }

    public static String timeZoneNameFromOffsetSeconds(int n) {
        if (n == 0) {
            return "UTC";
        }
        StringBuilder stringBuilder = new StringBuilder(12);
        stringBuilder.append("GMT");
        if (n < 0) {
            stringBuilder.append('-');
            n = -n;
        } else {
            stringBuilder.append('+');
        }
        StringUtils.appendTwoDigits(stringBuilder, n / 3600).append(':');
        StringUtils.appendTwoDigits(stringBuilder, (n %= 3600) / 60);
        if ((n %= 60) != 0) {
            stringBuilder.append(':');
            StringUtils.appendTwoDigits(stringBuilder, n);
        }
        return stringBuilder.toString();
    }

    public static long convertScale(long l, int n, long l2) {
        long l3;
        if (n >= 9) {
            return l;
        }
        int n2 = FRACTIONAL_SECONDS_TABLE[n];
        long l4 = l % (long)n2;
        if (l4 >= (long)(n2 >>> 1)) {
            l += (long)n2;
        }
        if ((l3 = l - l4) >= l2) {
            l3 = l2 - (long)n2;
        }
        return l3;
    }

    public static ValueTimestampTimeZone timestampTimeZoneAtOffset(long l, long l2, int n, int n2) {
        if ((l2 += (long)(n2 - n) * 1000000000L) < 0L) {
            l = DateTimeUtils.decrementDateValue(l);
            if ((l2 += 86400000000000L) < 0L) {
                l2 += 86400000000000L;
                l = DateTimeUtils.decrementDateValue(l);
            }
        } else if (l2 >= 86400000000000L) {
            l = DateTimeUtils.incrementDateValue(l);
            if ((l2 -= 86400000000000L) >= 86400000000000L) {
                l2 -= 86400000000000L;
                l = DateTimeUtils.incrementDateValue(l);
            }
        }
        return ValueTimestampTimeZone.fromDateValueAndNanos(l, l2, n2);
    }
}

