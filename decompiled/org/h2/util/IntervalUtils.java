/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.math.BigInteger;
import org.h2.api.IntervalQualifier;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.util.StringUtils;
import org.h2.value.ValueInterval;

public class IntervalUtils {
    private static final BigInteger NANOS_PER_SECOND_BI = BigInteger.valueOf(1000000000L);
    private static final BigInteger NANOS_PER_MINUTE_BI = BigInteger.valueOf(60000000000L);
    private static final BigInteger NANOS_PER_HOUR_BI = BigInteger.valueOf(3600000000000L);
    public static final BigInteger NANOS_PER_DAY_BI = BigInteger.valueOf(86400000000000L);
    private static final BigInteger MONTHS_PER_YEAR_BI = BigInteger.valueOf(12L);
    private static final BigInteger HOURS_PER_DAY_BI = BigInteger.valueOf(24L);
    private static final BigInteger MINUTES_PER_DAY_BI = BigInteger.valueOf(1440L);
    private static final BigInteger MINUTES_PER_HOUR_BI = BigInteger.valueOf(60L);
    private static final BigInteger LEADING_MIN = BigInteger.valueOf(-999999999999999999L);
    private static final BigInteger LEADING_MAX = BigInteger.valueOf(999999999999999999L);

    private IntervalUtils() {
    }

    public static ValueInterval parseFormattedInterval(IntervalQualifier intervalQualifier, String string) {
        int n;
        int n2 = 0;
        if (!string.regionMatches(true, n2 = IntervalUtils.skipWS(string, n2), "INTERVAL", 0, 8)) {
            return IntervalUtils.parseInterval(intervalQualifier, false, string);
        }
        n2 = IntervalUtils.skipWS(string, n2 + 8);
        boolean bl = false;
        char c = string.charAt(n2);
        if (c == '-') {
            bl = true;
            n2 = IntervalUtils.skipWS(string, n2 + 1);
            c = string.charAt(n2);
        } else if (c == '+') {
            n2 = IntervalUtils.skipWS(string, n2 + 1);
            c = string.charAt(n2);
        }
        if (c != '\'') {
            throw new IllegalArgumentException(string);
        }
        int n3 = ++n2;
        int n4 = string.length();
        while (true) {
            if (n2 == n4) {
                throw new IllegalArgumentException(string);
            }
            if (string.charAt(n2) == '\'') break;
            ++n2;
        }
        String string2 = string.substring(n3, n2);
        n2 = IntervalUtils.skipWS(string, n2 + 1);
        if (string.regionMatches(true, n2, "YEAR", 0, 4)) {
            n = IntervalUtils.skipWSEnd(string, n2 += 4);
            if (n == n4) {
                return IntervalUtils.parseInterval(IntervalQualifier.YEAR, bl, string2);
            }
            if (n > n2 && string.regionMatches(true, n, "TO", 0, 2) && (n2 = IntervalUtils.skipWS(string, n += 2)) > n && string.regionMatches(true, n2, "MONTH", 0, 5) && IntervalUtils.skipWSEnd(string, n2 + 5) == n4) {
                return IntervalUtils.parseInterval(IntervalQualifier.YEAR_TO_MONTH, bl, string2);
            }
        } else if (string.regionMatches(true, n2, "MONTH", 0, 5) && IntervalUtils.skipWSEnd(string, n2 + 5) == n4) {
            return IntervalUtils.parseInterval(IntervalQualifier.MONTH, bl, string2);
        }
        if (string.regionMatches(true, n2, "DAY", 0, 3)) {
            n = IntervalUtils.skipWSEnd(string, n2 += 3);
            if (n == n4) {
                return IntervalUtils.parseInterval(IntervalQualifier.DAY, bl, string2);
            }
            if (n > n2 && string.regionMatches(true, n, "TO", 0, 2) && (n2 = IntervalUtils.skipWS(string, n += 2)) > n) {
                if (string.regionMatches(true, n2, "HOUR", 0, 4)) {
                    if (IntervalUtils.skipWSEnd(string, n2 + 4) == n4) {
                        return IntervalUtils.parseInterval(IntervalQualifier.DAY_TO_HOUR, bl, string2);
                    }
                } else if (string.regionMatches(true, n2, "MINUTE", 0, 6)) {
                    if (IntervalUtils.skipWSEnd(string, n2 + 6) == n4) {
                        return IntervalUtils.parseInterval(IntervalQualifier.DAY_TO_MINUTE, bl, string2);
                    }
                } else if (string.regionMatches(true, n2, "SECOND", 0, 6) && IntervalUtils.skipWSEnd(string, n2 + 6) == n4) {
                    return IntervalUtils.parseInterval(IntervalQualifier.DAY_TO_SECOND, bl, string2);
                }
            }
        }
        if (string.regionMatches(true, n2, "HOUR", 0, 4)) {
            n = IntervalUtils.skipWSEnd(string, n2 += 4);
            if (n == n4) {
                return IntervalUtils.parseInterval(IntervalQualifier.HOUR, bl, string2);
            }
            if (n > n2 && string.regionMatches(true, n, "TO", 0, 2) && (n2 = IntervalUtils.skipWS(string, n += 2)) > n) {
                if (string.regionMatches(true, n2, "MINUTE", 0, 6)) {
                    if (IntervalUtils.skipWSEnd(string, n2 + 6) == n4) {
                        return IntervalUtils.parseInterval(IntervalQualifier.HOUR_TO_MINUTE, bl, string2);
                    }
                } else if (string.regionMatches(true, n2, "SECOND", 0, 6) && IntervalUtils.skipWSEnd(string, n2 + 6) == n4) {
                    return IntervalUtils.parseInterval(IntervalQualifier.HOUR_TO_SECOND, bl, string2);
                }
            }
        }
        if (string.regionMatches(true, n2, "MINUTE", 0, 6)) {
            n = IntervalUtils.skipWSEnd(string, n2 += 6);
            if (n == n4) {
                return IntervalUtils.parseInterval(IntervalQualifier.MINUTE, bl, string2);
            }
            if (n > n2 && string.regionMatches(true, n, "TO", 0, 2) && (n2 = IntervalUtils.skipWS(string, n += 2)) > n && string.regionMatches(true, n2, "SECOND", 0, 6) && IntervalUtils.skipWSEnd(string, n2 + 6) == n4) {
                return IntervalUtils.parseInterval(IntervalQualifier.MINUTE_TO_SECOND, bl, string2);
            }
        }
        if (string.regionMatches(true, n2, "SECOND", 0, 6) && IntervalUtils.skipWSEnd(string, n2 + 6) == n4) {
            return IntervalUtils.parseInterval(IntervalQualifier.SECOND, bl, string2);
        }
        throw new IllegalArgumentException(string);
    }

    private static int skipWS(String string, int n) {
        int n2 = string.length();
        while (true) {
            if (n == n2) {
                throw new IllegalArgumentException(string);
            }
            if (!Character.isWhitespace(string.charAt(n))) {
                return n;
            }
            ++n;
        }
    }

    private static int skipWSEnd(String string, int n) {
        int n2 = string.length();
        while (n != n2) {
            if (!Character.isWhitespace(string.charAt(n))) {
                return n;
            }
            ++n;
        }
        return n;
    }

    public static ValueInterval parseInterval(IntervalQualifier intervalQualifier, boolean bl, String string) {
        long l;
        long l2;
        switch (intervalQualifier) {
            case YEAR: 
            case MONTH: 
            case DAY: 
            case HOUR: 
            case MINUTE: {
                l2 = IntervalUtils.parseIntervalLeading(string, 0, string.length(), bl);
                l = 0L;
                break;
            }
            case SECOND: {
                int n = string.indexOf(46);
                if (n < 0) {
                    l2 = IntervalUtils.parseIntervalLeading(string, 0, string.length(), bl);
                    l = 0L;
                    break;
                }
                l2 = IntervalUtils.parseIntervalLeading(string, 0, n, bl);
                l = DateTimeUtils.parseNanos(string, n + 1, string.length());
                break;
            }
            case YEAR_TO_MONTH: {
                return IntervalUtils.parseInterval2(intervalQualifier, string, '-', 11, bl);
            }
            case DAY_TO_HOUR: {
                return IntervalUtils.parseInterval2(intervalQualifier, string, ' ', 23, bl);
            }
            case DAY_TO_MINUTE: {
                int n = string.indexOf(32);
                if (n < 0) {
                    l2 = IntervalUtils.parseIntervalLeading(string, 0, string.length(), bl);
                    l = 0L;
                    break;
                }
                l2 = IntervalUtils.parseIntervalLeading(string, 0, n, bl);
                int n2 = string.indexOf(58, n + 1);
                if (n2 < 0) {
                    l = IntervalUtils.parseIntervalRemaining(string, n + 1, string.length(), 23) * 60L;
                    break;
                }
                l = IntervalUtils.parseIntervalRemaining(string, n + 1, n2, 23) * 60L + IntervalUtils.parseIntervalRemaining(string, n2 + 1, string.length(), 59);
                break;
            }
            case DAY_TO_SECOND: {
                int n = string.indexOf(32);
                if (n < 0) {
                    l2 = IntervalUtils.parseIntervalLeading(string, 0, string.length(), bl);
                    l = 0L;
                    break;
                }
                l2 = IntervalUtils.parseIntervalLeading(string, 0, n, bl);
                int n3 = string.indexOf(58, n + 1);
                if (n3 < 0) {
                    l = IntervalUtils.parseIntervalRemaining(string, n + 1, string.length(), 23) * 3600000000000L;
                    break;
                }
                int n4 = string.indexOf(58, n3 + 1);
                if (n4 < 0) {
                    l = IntervalUtils.parseIntervalRemaining(string, n + 1, n3, 23) * 3600000000000L + IntervalUtils.parseIntervalRemaining(string, n3 + 1, string.length(), 59) * 60000000000L;
                    break;
                }
                l = IntervalUtils.parseIntervalRemaining(string, n + 1, n3, 23) * 3600000000000L + IntervalUtils.parseIntervalRemaining(string, n3 + 1, n4, 59) * 60000000000L + IntervalUtils.parseIntervalRemainingSeconds(string, n4 + 1);
                break;
            }
            case HOUR_TO_MINUTE: {
                return IntervalUtils.parseInterval2(intervalQualifier, string, ':', 59, bl);
            }
            case HOUR_TO_SECOND: {
                int n = string.indexOf(58);
                if (n < 0) {
                    l2 = IntervalUtils.parseIntervalLeading(string, 0, string.length(), bl);
                    l = 0L;
                    break;
                }
                l2 = IntervalUtils.parseIntervalLeading(string, 0, n, bl);
                int n5 = string.indexOf(58, n + 1);
                if (n5 < 0) {
                    l = IntervalUtils.parseIntervalRemaining(string, n + 1, string.length(), 59) * 60000000000L;
                    break;
                }
                l = IntervalUtils.parseIntervalRemaining(string, n + 1, n5, 59) * 60000000000L + IntervalUtils.parseIntervalRemainingSeconds(string, n5 + 1);
                break;
            }
            case MINUTE_TO_SECOND: {
                int n = string.indexOf(58);
                if (n < 0) {
                    l2 = IntervalUtils.parseIntervalLeading(string, 0, string.length(), bl);
                    l = 0L;
                    break;
                }
                l2 = IntervalUtils.parseIntervalLeading(string, 0, n, bl);
                l = IntervalUtils.parseIntervalRemainingSeconds(string, n + 1);
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        boolean bl2 = bl = l2 < 0L;
        if (bl) {
            l2 = l2 != Long.MIN_VALUE ? -l2 : 0L;
        }
        return ValueInterval.from(intervalQualifier, bl, l2, l);
    }

    private static ValueInterval parseInterval2(IntervalQualifier intervalQualifier, String string, char c, int n, boolean bl) {
        long l;
        long l2;
        int n2 = string.indexOf(c, 1);
        if (n2 < 0) {
            l2 = IntervalUtils.parseIntervalLeading(string, 0, string.length(), bl);
            l = 0L;
        } else {
            l2 = IntervalUtils.parseIntervalLeading(string, 0, n2, bl);
            l = IntervalUtils.parseIntervalRemaining(string, n2 + 1, string.length(), n);
        }
        boolean bl2 = bl = l2 < 0L;
        if (bl) {
            l2 = l2 != Long.MIN_VALUE ? -l2 : 0L;
        }
        return ValueInterval.from(intervalQualifier, bl, l2, l);
    }

    private static long parseIntervalLeading(String string, int n, int n2, boolean bl) {
        long l = Long.parseLong(string.substring(n, n2));
        if (l == 0L) {
            return bl ^ string.charAt(n) == '-' ? Long.MIN_VALUE : 0L;
        }
        return bl ? -l : l;
    }

    private static long parseIntervalRemaining(String string, int n, int n2, int n3) {
        int n4 = StringUtils.parseUInt31(string, n, n2);
        if (n4 > n3) {
            throw new IllegalArgumentException(string);
        }
        return n4;
    }

    private static long parseIntervalRemainingSeconds(String string, int n) {
        int n2;
        int n3;
        int n4 = string.indexOf(46, n + 1);
        if (n4 < 0) {
            n3 = StringUtils.parseUInt31(string, n, string.length());
            n2 = 0;
        } else {
            n3 = StringUtils.parseUInt31(string, n, n4);
            n2 = DateTimeUtils.parseNanos(string, n4 + 1, string.length());
        }
        if (n3 > 59) {
            throw new IllegalArgumentException(string);
        }
        return (long)n3 * 1000000000L + (long)n2;
    }

    public static StringBuilder appendInterval(StringBuilder stringBuilder, IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        stringBuilder.append("INTERVAL '");
        if (bl) {
            stringBuilder.append('-');
        }
        switch (intervalQualifier) {
            case YEAR: 
            case MONTH: 
            case DAY: 
            case HOUR: 
            case MINUTE: {
                stringBuilder.append(l);
                break;
            }
            case SECOND: {
                DateTimeUtils.appendNanos(stringBuilder.append(l), (int)l2);
                break;
            }
            case YEAR_TO_MONTH: {
                stringBuilder.append(l).append('-').append(l2);
                break;
            }
            case DAY_TO_HOUR: {
                stringBuilder.append(l).append(' ');
                StringUtils.appendTwoDigits(stringBuilder, (int)l2);
                break;
            }
            case DAY_TO_MINUTE: {
                stringBuilder.append(l).append(' ');
                int n = (int)l2;
                StringUtils.appendTwoDigits(stringBuilder, n / 60).append(':');
                StringUtils.appendTwoDigits(stringBuilder, n % 60);
                break;
            }
            case DAY_TO_SECOND: {
                long l3 = l2 % 60000000000L;
                int n = (int)(l2 / 60000000000L);
                stringBuilder.append(l).append(' ');
                StringUtils.appendTwoDigits(stringBuilder, n / 60).append(':');
                StringUtils.appendTwoDigits(stringBuilder, n % 60).append(':');
                StringUtils.appendTwoDigits(stringBuilder, (int)(l3 / 1000000000L));
                DateTimeUtils.appendNanos(stringBuilder, (int)(l3 % 1000000000L));
                break;
            }
            case HOUR_TO_MINUTE: {
                stringBuilder.append(l).append(':');
                StringUtils.appendTwoDigits(stringBuilder, (int)l2);
                break;
            }
            case HOUR_TO_SECOND: {
                stringBuilder.append(l).append(':');
                StringUtils.appendTwoDigits(stringBuilder, (int)(l2 / 60000000000L)).append(':');
                long l4 = l2 % 60000000000L;
                StringUtils.appendTwoDigits(stringBuilder, (int)(l4 / 1000000000L));
                DateTimeUtils.appendNanos(stringBuilder, (int)(l4 % 1000000000L));
                break;
            }
            case MINUTE_TO_SECOND: {
                stringBuilder.append(l).append(':');
                StringUtils.appendTwoDigits(stringBuilder, (int)(l2 / 1000000000L));
                DateTimeUtils.appendNanos(stringBuilder, (int)(l2 % 1000000000L));
            }
        }
        return stringBuilder.append("' ").append((Object)intervalQualifier);
    }

    public static BigInteger intervalToAbsolute(ValueInterval valueInterval) {
        BigInteger bigInteger;
        switch (valueInterval.getQualifier()) {
            case YEAR: {
                bigInteger = BigInteger.valueOf(valueInterval.getLeading()).multiply(MONTHS_PER_YEAR_BI);
                break;
            }
            case MONTH: {
                bigInteger = BigInteger.valueOf(valueInterval.getLeading());
                break;
            }
            case DAY: {
                bigInteger = BigInteger.valueOf(valueInterval.getLeading()).multiply(NANOS_PER_DAY_BI);
                break;
            }
            case HOUR: {
                bigInteger = BigInteger.valueOf(valueInterval.getLeading()).multiply(NANOS_PER_HOUR_BI);
                break;
            }
            case MINUTE: {
                bigInteger = BigInteger.valueOf(valueInterval.getLeading()).multiply(NANOS_PER_MINUTE_BI);
                break;
            }
            case SECOND: {
                bigInteger = IntervalUtils.intervalToAbsolute(valueInterval, NANOS_PER_SECOND_BI);
                break;
            }
            case YEAR_TO_MONTH: {
                bigInteger = IntervalUtils.intervalToAbsolute(valueInterval, MONTHS_PER_YEAR_BI);
                break;
            }
            case DAY_TO_HOUR: {
                bigInteger = IntervalUtils.intervalToAbsolute(valueInterval, HOURS_PER_DAY_BI, NANOS_PER_HOUR_BI);
                break;
            }
            case DAY_TO_MINUTE: {
                bigInteger = IntervalUtils.intervalToAbsolute(valueInterval, MINUTES_PER_DAY_BI, NANOS_PER_MINUTE_BI);
                break;
            }
            case DAY_TO_SECOND: {
                bigInteger = IntervalUtils.intervalToAbsolute(valueInterval, NANOS_PER_DAY_BI);
                break;
            }
            case HOUR_TO_MINUTE: {
                bigInteger = IntervalUtils.intervalToAbsolute(valueInterval, MINUTES_PER_HOUR_BI, NANOS_PER_MINUTE_BI);
                break;
            }
            case HOUR_TO_SECOND: {
                bigInteger = IntervalUtils.intervalToAbsolute(valueInterval, NANOS_PER_HOUR_BI);
                break;
            }
            case MINUTE_TO_SECOND: {
                bigInteger = IntervalUtils.intervalToAbsolute(valueInterval, NANOS_PER_MINUTE_BI);
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        return valueInterval.isNegative() ? bigInteger.negate() : bigInteger;
    }

    private static BigInteger intervalToAbsolute(ValueInterval valueInterval, BigInteger bigInteger, BigInteger bigInteger2) {
        return IntervalUtils.intervalToAbsolute(valueInterval, bigInteger).multiply(bigInteger2);
    }

    private static BigInteger intervalToAbsolute(ValueInterval valueInterval, BigInteger bigInteger) {
        return BigInteger.valueOf(valueInterval.getLeading()).multiply(bigInteger).add(BigInteger.valueOf(valueInterval.getRemaining()));
    }

    public static ValueInterval intervalFromAbsolute(IntervalQualifier intervalQualifier, BigInteger bigInteger) {
        switch (intervalQualifier) {
            case YEAR: {
                return ValueInterval.from(intervalQualifier, bigInteger.signum() < 0, IntervalUtils.leadingExact(bigInteger.divide(MONTHS_PER_YEAR_BI)), 0L);
            }
            case MONTH: {
                return ValueInterval.from(intervalQualifier, bigInteger.signum() < 0, IntervalUtils.leadingExact(bigInteger), 0L);
            }
            case DAY: {
                return ValueInterval.from(intervalQualifier, bigInteger.signum() < 0, IntervalUtils.leadingExact(bigInteger.divide(NANOS_PER_DAY_BI)), 0L);
            }
            case HOUR: {
                return ValueInterval.from(intervalQualifier, bigInteger.signum() < 0, IntervalUtils.leadingExact(bigInteger.divide(NANOS_PER_HOUR_BI)), 0L);
            }
            case MINUTE: {
                return ValueInterval.from(intervalQualifier, bigInteger.signum() < 0, IntervalUtils.leadingExact(bigInteger.divide(NANOS_PER_MINUTE_BI)), 0L);
            }
            case SECOND: {
                return IntervalUtils.intervalFromAbsolute(intervalQualifier, bigInteger, NANOS_PER_SECOND_BI);
            }
            case YEAR_TO_MONTH: {
                return IntervalUtils.intervalFromAbsolute(intervalQualifier, bigInteger, MONTHS_PER_YEAR_BI);
            }
            case DAY_TO_HOUR: {
                return IntervalUtils.intervalFromAbsolute(intervalQualifier, bigInteger.divide(NANOS_PER_HOUR_BI), HOURS_PER_DAY_BI);
            }
            case DAY_TO_MINUTE: {
                return IntervalUtils.intervalFromAbsolute(intervalQualifier, bigInteger.divide(NANOS_PER_MINUTE_BI), MINUTES_PER_DAY_BI);
            }
            case DAY_TO_SECOND: {
                return IntervalUtils.intervalFromAbsolute(intervalQualifier, bigInteger, NANOS_PER_DAY_BI);
            }
            case HOUR_TO_MINUTE: {
                return IntervalUtils.intervalFromAbsolute(intervalQualifier, bigInteger.divide(NANOS_PER_MINUTE_BI), MINUTES_PER_HOUR_BI);
            }
            case HOUR_TO_SECOND: {
                return IntervalUtils.intervalFromAbsolute(intervalQualifier, bigInteger, NANOS_PER_HOUR_BI);
            }
            case MINUTE_TO_SECOND: {
                return IntervalUtils.intervalFromAbsolute(intervalQualifier, bigInteger, NANOS_PER_MINUTE_BI);
            }
        }
        throw new IllegalArgumentException();
    }

    private static ValueInterval intervalFromAbsolute(IntervalQualifier intervalQualifier, BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger[] bigIntegerArray = bigInteger.divideAndRemainder(bigInteger2);
        return ValueInterval.from(intervalQualifier, bigInteger.signum() < 0, IntervalUtils.leadingExact(bigIntegerArray[0]), Math.abs(bigIntegerArray[1].longValue()));
    }

    private static long leadingExact(BigInteger bigInteger) {
        if (bigInteger.compareTo(LEADING_MAX) > 0 || bigInteger.compareTo(LEADING_MIN) < 0) {
            throw DbException.get(22003, bigInteger.toString());
        }
        return Math.abs(bigInteger.longValue());
    }

    public static boolean validateInterval(IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        long l3;
        if (intervalQualifier == null) {
            throw new NullPointerException();
        }
        if (l == 0L && l2 == 0L) {
            return false;
        }
        switch (intervalQualifier) {
            case YEAR: 
            case MONTH: 
            case DAY: 
            case HOUR: 
            case MINUTE: {
                l3 = 1L;
                break;
            }
            case SECOND: {
                l3 = 1000000000L;
                break;
            }
            case YEAR_TO_MONTH: {
                l3 = 12L;
                break;
            }
            case DAY_TO_HOUR: {
                l3 = 24L;
                break;
            }
            case DAY_TO_MINUTE: {
                l3 = 1440L;
                break;
            }
            case DAY_TO_SECOND: {
                l3 = 86400000000000L;
                break;
            }
            case HOUR_TO_MINUTE: {
                l3 = 60L;
                break;
            }
            case HOUR_TO_SECOND: {
                l3 = 3600000000000L;
                break;
            }
            case MINUTE_TO_SECOND: {
                l3 = 60000000000L;
                break;
            }
            default: {
                throw DbException.getInvalidValueException("interval", (Object)intervalQualifier);
            }
        }
        if (l < 0L || l >= 1000000000000000000L) {
            throw DbException.getInvalidValueException("interval", Long.toString(l));
        }
        if (l2 < 0L || l2 >= l3) {
            throw DbException.getInvalidValueException("interval", Long.toString(l2));
        }
        return bl;
    }

    public static long yearsFromInterval(IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        if (intervalQualifier == IntervalQualifier.YEAR || intervalQualifier == IntervalQualifier.YEAR_TO_MONTH) {
            long l3 = l;
            if (bl) {
                l3 = -l3;
            }
            return l3;
        }
        return 0L;
    }

    public static long monthsFromInterval(IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        long l3;
        if (intervalQualifier == IntervalQualifier.MONTH) {
            l3 = l;
        } else if (intervalQualifier == IntervalQualifier.YEAR_TO_MONTH) {
            l3 = l2;
        } else {
            return 0L;
        }
        if (bl) {
            l3 = -l3;
        }
        return l3;
    }

    public static long daysFromInterval(IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        switch (intervalQualifier) {
            case DAY: 
            case DAY_TO_HOUR: 
            case DAY_TO_MINUTE: 
            case DAY_TO_SECOND: {
                long l3 = l;
                if (bl) {
                    l3 = -l3;
                }
                return l3;
            }
        }
        return 0L;
    }

    public static long hoursFromInterval(IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        long l3;
        switch (intervalQualifier) {
            case HOUR: 
            case HOUR_TO_MINUTE: 
            case HOUR_TO_SECOND: {
                l3 = l;
                break;
            }
            case DAY_TO_HOUR: {
                l3 = l2;
                break;
            }
            case DAY_TO_MINUTE: {
                l3 = l2 / 60L;
                break;
            }
            case DAY_TO_SECOND: {
                l3 = l2 / 3600000000000L;
                break;
            }
            default: {
                return 0L;
            }
        }
        if (bl) {
            l3 = -l3;
        }
        return l3;
    }

    public static long minutesFromInterval(IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        long l3;
        switch (intervalQualifier) {
            case MINUTE: 
            case MINUTE_TO_SECOND: {
                l3 = l;
                break;
            }
            case DAY_TO_MINUTE: {
                l3 = l2 % 60L;
                break;
            }
            case DAY_TO_SECOND: {
                l3 = l2 / 60000000000L % 60L;
                break;
            }
            case HOUR_TO_MINUTE: {
                l3 = l2;
                break;
            }
            case HOUR_TO_SECOND: {
                l3 = l2 / 60000000000L;
                break;
            }
            default: {
                return 0L;
            }
        }
        if (bl) {
            l3 = -l3;
        }
        return l3;
    }

    public static long nanosFromInterval(IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        long l3;
        switch (intervalQualifier) {
            case SECOND: {
                l3 = l * 1000000000L + l2;
                break;
            }
            case DAY_TO_SECOND: 
            case HOUR_TO_SECOND: {
                l3 = l2 % 60000000000L;
                break;
            }
            case MINUTE_TO_SECOND: {
                l3 = l2;
                break;
            }
            default: {
                return 0L;
            }
        }
        if (bl) {
            l3 = -l3;
        }
        return l3;
    }
}

