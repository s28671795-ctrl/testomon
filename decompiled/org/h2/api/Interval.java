/*
 * Decompiled with CFR 0.152.
 */
package org.h2.api;

import org.h2.api.IntervalQualifier;
import org.h2.message.DbException;
import org.h2.util.IntervalUtils;

public final class Interval {
    private final IntervalQualifier qualifier;
    private final boolean negative;
    private final long leading;
    private final long remaining;

    public static Interval ofYears(long l) {
        return new Interval(IntervalQualifier.YEAR, l < 0L, Math.abs(l), 0L);
    }

    public static Interval ofMonths(long l) {
        return new Interval(IntervalQualifier.MONTH, l < 0L, Math.abs(l), 0L);
    }

    public static Interval ofDays(long l) {
        return new Interval(IntervalQualifier.DAY, l < 0L, Math.abs(l), 0L);
    }

    public static Interval ofHours(long l) {
        return new Interval(IntervalQualifier.HOUR, l < 0L, Math.abs(l), 0L);
    }

    public static Interval ofMinutes(long l) {
        return new Interval(IntervalQualifier.MINUTE, l < 0L, Math.abs(l), 0L);
    }

    public static Interval ofSeconds(long l) {
        return new Interval(IntervalQualifier.SECOND, l < 0L, Math.abs(l), 0L);
    }

    public static Interval ofSeconds(long l, int n) {
        boolean bl;
        boolean bl2 = bl = (l | (long)n) < 0L;
        if (bl) {
            if (l > 0L || n > 0) {
                throw new IllegalArgumentException();
            }
            l = -l;
            n = -n;
        }
        return new Interval(IntervalQualifier.SECOND, bl, l, n);
    }

    public static Interval ofNanos(long l) {
        boolean bl;
        boolean bl2 = bl = l < 0L;
        if (bl && (l = -l) < 0L) {
            return new Interval(IntervalQualifier.SECOND, true, 9223372036L, 854775808L);
        }
        return new Interval(IntervalQualifier.SECOND, bl, l / 1000000000L, l % 1000000000L);
    }

    public static Interval ofYearsMonths(long l, int n) {
        boolean bl;
        boolean bl2 = bl = (l | (long)n) < 0L;
        if (bl) {
            if (l > 0L || n > 0) {
                throw new IllegalArgumentException();
            }
            l = -l;
            n = -n;
        }
        return new Interval(IntervalQualifier.YEAR_TO_MONTH, bl, l, n);
    }

    public static Interval ofDaysHours(long l, int n) {
        boolean bl;
        boolean bl2 = bl = (l | (long)n) < 0L;
        if (bl) {
            if (l > 0L || n > 0) {
                throw new IllegalArgumentException();
            }
            l = -l;
            n = -n;
        }
        return new Interval(IntervalQualifier.DAY_TO_HOUR, bl, l, n);
    }

    public static Interval ofDaysHoursMinutes(long l, int n, int n2) {
        boolean bl;
        boolean bl2 = bl = (l | (long)n | (long)n2) < 0L;
        if (bl) {
            if (l > 0L || n > 0 || n2 > 0) {
                throw new IllegalArgumentException();
            }
            l = -l;
            if (((n = -n) | (n2 = -n2)) < 0) {
                throw new IllegalArgumentException();
            }
        }
        if (n2 >= 60) {
            throw new IllegalArgumentException();
        }
        return new Interval(IntervalQualifier.DAY_TO_MINUTE, bl, l, (long)n * 60L + (long)n2);
    }

    public static Interval ofDaysHoursMinutesSeconds(long l, int n, int n2, int n3) {
        return Interval.ofDaysHoursMinutesNanos(l, n, n2, (long)n3 * 1000000000L);
    }

    public static Interval ofDaysHoursMinutesNanos(long l, int n, int n2, long l2) {
        boolean bl;
        boolean bl2 = bl = (l | (long)n | (long)n2 | l2) < 0L;
        if (bl) {
            if (l > 0L || n > 0 || n2 > 0 || l2 > 0L) {
                throw new IllegalArgumentException();
            }
            l = -l;
            if (((long)((n = -n) | (n2 = -n2)) | (l2 = -l2)) < 0L) {
                throw new IllegalArgumentException();
            }
        }
        if (n2 >= 60 || l2 >= 60000000000L) {
            throw new IllegalArgumentException();
        }
        return new Interval(IntervalQualifier.DAY_TO_SECOND, bl, l, ((long)n * 60L + (long)n2) * 60000000000L + l2);
    }

    public static Interval ofHoursMinutes(long l, int n) {
        boolean bl;
        boolean bl2 = bl = (l | (long)n) < 0L;
        if (bl) {
            if (l > 0L || n > 0) {
                throw new IllegalArgumentException();
            }
            l = -l;
            n = -n;
        }
        return new Interval(IntervalQualifier.HOUR_TO_MINUTE, bl, l, n);
    }

    public static Interval ofHoursMinutesSeconds(long l, int n, int n2) {
        return Interval.ofHoursMinutesNanos(l, n, (long)n2 * 1000000000L);
    }

    public static Interval ofHoursMinutesNanos(long l, int n, long l2) {
        boolean bl;
        boolean bl2 = bl = (l | (long)n | l2) < 0L;
        if (bl) {
            if (l > 0L || n > 0 || l2 > 0L) {
                throw new IllegalArgumentException();
            }
            l = -l;
            if (((long)(n = -n) | (l2 = -l2)) < 0L) {
                throw new IllegalArgumentException();
            }
        }
        if (l2 >= 60000000000L) {
            throw new IllegalArgumentException();
        }
        return new Interval(IntervalQualifier.HOUR_TO_SECOND, bl, l, (long)n * 60000000000L + l2);
    }

    public static Interval ofMinutesSeconds(long l, int n) {
        return Interval.ofMinutesNanos(l, (long)n * 1000000000L);
    }

    public static Interval ofMinutesNanos(long l, long l2) {
        boolean bl;
        boolean bl2 = bl = (l | l2) < 0L;
        if (bl) {
            if (l > 0L || l2 > 0L) {
                throw new IllegalArgumentException();
            }
            l = -l;
            l2 = -l2;
        }
        return new Interval(IntervalQualifier.MINUTE_TO_SECOND, bl, l, l2);
    }

    public Interval(IntervalQualifier intervalQualifier, boolean bl, long l, long l2) {
        this.qualifier = intervalQualifier;
        try {
            this.negative = IntervalUtils.validateInterval(intervalQualifier, bl, l, l2);
        }
        catch (DbException dbException) {
            throw new IllegalArgumentException();
        }
        this.leading = l;
        this.remaining = l2;
    }

    public IntervalQualifier getQualifier() {
        return this.qualifier;
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

    public long getYears() {
        return IntervalUtils.yearsFromInterval(this.qualifier, this.negative, this.leading, this.remaining);
    }

    public long getMonths() {
        return IntervalUtils.monthsFromInterval(this.qualifier, this.negative, this.leading, this.remaining);
    }

    public long getDays() {
        return IntervalUtils.daysFromInterval(this.qualifier, this.negative, this.leading, this.remaining);
    }

    public long getHours() {
        return IntervalUtils.hoursFromInterval(this.qualifier, this.negative, this.leading, this.remaining);
    }

    public long getMinutes() {
        return IntervalUtils.minutesFromInterval(this.qualifier, this.negative, this.leading, this.remaining);
    }

    public long getSeconds() {
        if (this.qualifier == IntervalQualifier.SECOND) {
            return this.negative ? -this.leading : this.leading;
        }
        return this.getSecondsAndNanos() / 1000000000L;
    }

    public long getNanosOfSecond() {
        if (this.qualifier == IntervalQualifier.SECOND) {
            return this.negative ? -this.remaining : this.remaining;
        }
        return this.getSecondsAndNanos() % 1000000000L;
    }

    public long getSecondsAndNanos() {
        return IntervalUtils.nanosFromInterval(this.qualifier, this.negative, this.leading, this.remaining);
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.qualifier.hashCode();
        n = 31 * n + (this.negative ? 1231 : 1237);
        n = 31 * n + (int)(this.leading ^ this.leading >>> 32);
        n = 31 * n + (int)(this.remaining ^ this.remaining >>> 32);
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Interval)) {
            return false;
        }
        Interval interval = (Interval)object;
        return this.qualifier == interval.qualifier && this.negative == interval.negative && this.leading == interval.leading && this.remaining == interval.remaining;
    }

    public String toString() {
        return IntervalUtils.appendInterval(new StringBuilder(), this.getQualifier(), this.negative, this.leading, this.remaining).toString();
    }
}

