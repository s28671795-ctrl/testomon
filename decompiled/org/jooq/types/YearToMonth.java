/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.types;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.types.Interval;
import org.jooq.types.YearToSecond;

public final class YearToMonth
extends Number
implements Interval,
Comparable<YearToMonth> {
    private static final Pattern PATTERN_SQL = Pattern.compile("^([+-])?(\\d+)-(\\d+)$");
    private static final Pattern PATTERN_ISO = Pattern.compile("^([+-])?P(?:([+-]?\\d+)Y)?(?:([+-]?\\d+)M)?$", 2);
    private final boolean negative;
    private final int years;
    private final int months;

    public YearToMonth() {
        this(0, 0, false);
    }

    public YearToMonth(int years) {
        this(years, 0, false);
    }

    public YearToMonth(int years, int months) {
        this(years, months, false);
    }

    YearToMonth(int years, int months, boolean negative) {
        if (Math.abs(months) >= 12) {
            years += months / 12;
            months %= 12;
        }
        this.negative = negative;
        this.years = years;
        this.months = months;
    }

    public static YearToMonth valueOf(String string) {
        if (string != null) {
            Matcher matcher = PATTERN_SQL.matcher(string);
            if (matcher.find()) {
                return YearToSecond.parseYM(matcher, 0);
            }
            matcher = PATTERN_ISO.matcher(string);
            if (matcher.find()) {
                boolean negative = "-".equals(matcher.group(1));
                String group2 = matcher.group(2);
                String group3 = matcher.group(3);
                int years = group2 == null ? 0 : Integer.parseInt(group2);
                int months = group3 == null ? 0 : Integer.parseInt(group3);
                return new YearToMonth(years, months, negative);
            }
            return YearToMonth.yearToMonth(string);
        }
        return null;
    }

    public static YearToMonth year(String string) {
        try {
            return string == null ? null : new YearToMonth(Integer.parseInt(string));
        }
        catch (NumberFormatException ignore) {
            return null;
        }
    }

    public static YearToMonth yearToMonth(String string) {
        Matcher matcher;
        if (string != null && (matcher = PATTERN_SQL.matcher(string)).find()) {
            return YearToSecond.parseYM(matcher, 0);
        }
        return null;
    }

    public static YearToMonth month(String string) {
        try {
            return string == null ? null : new YearToMonth(0, Integer.parseInt(string));
        }
        catch (NumberFormatException ignore) {
            return null;
        }
    }

    @Override
    public final Duration toDuration() {
        long hours = (long)this.years * 8766L + (long)this.months * 720L;
        if (this.negative) {
            hours = -hours;
        }
        return Duration.ofHours(hours);
    }

    @Override
    public final YearToMonth neg() {
        return new YearToMonth(this.years, this.months, !this.negative);
    }

    @Override
    public final YearToMonth abs() {
        return new YearToMonth(this.years, this.months, false);
    }

    public final int getYears() {
        return this.years;
    }

    public final int getMonths() {
        return this.months;
    }

    @Override
    public final int getSign() {
        return this.negative ? -1 : 1;
    }

    @Override
    public final int intValue() {
        return (this.negative ? -1 : 1) * (12 * this.years + this.months);
    }

    @Override
    public final long longValue() {
        return this.intValue();
    }

    @Override
    public final float floatValue() {
        return this.intValue();
    }

    @Override
    public final double doubleValue() {
        return this.intValue();
    }

    @Override
    public final int compareTo(YearToMonth that) {
        if (this.years < that.years) {
            return -1;
        }
        if (this.years > that.years) {
            return 1;
        }
        if (this.months < that.months) {
            return -1;
        }
        if (this.months > that.months) {
            return 1;
        }
        return 0;
    }

    public final int hashCode() {
        int prime = 31;
        int result = 0;
        if (this.months != 0) {
            result = 31 * result + this.months;
        }
        if (this.years != 0) {
            result = 31 * result + this.years;
        }
        return result;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() == obj.getClass()) {
            YearToMonth other = (YearToMonth)obj;
            if (this.months != other.months) {
                return false;
            }
            if (this.years != other.years) {
                return false;
            }
            return this.negative == other.negative || this.intValue() == 0;
        }
        if (obj instanceof YearToSecond) {
            return obj.equals(this);
        }
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.negative ? "-" : "+");
        sb.append(this.years);
        sb.append("-");
        sb.append(this.months);
        return sb.toString();
    }
}

