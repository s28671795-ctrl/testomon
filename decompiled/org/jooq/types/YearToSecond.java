/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.types;

import java.time.Duration;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.tools.Convert;
import org.jooq.tools.StringUtils;
import org.jooq.types.DayToSecond;
import org.jooq.types.Interval;
import org.jooq.types.YearToMonth;

public final class YearToSecond
extends Number
implements Interval,
Comparable<YearToSecond> {
    private static final Pattern PATTERN = Pattern.compile("^([+-])?(\\d+)-(\\d+) ([+-])?(?:(\\d+) )?(\\d+):(\\d+):(\\d+)(?:\\.(\\d+))?$");
    private final YearToMonth yearToMonth;
    private final DayToSecond dayToSecond;

    public YearToSecond(YearToMonth yearToMonth, DayToSecond dayToSecond) {
        this.yearToMonth = yearToMonth == null ? new YearToMonth() : yearToMonth;
        this.dayToSecond = dayToSecond == null ? new DayToSecond() : dayToSecond;
    }

    public static YearToSecond valueOf(double milli) {
        double abs = Math.abs(milli);
        int y = (int)(abs / 3.15576E10);
        int m4 = (int)((abs %= 3.15576E10) / 2.592E9);
        YearToSecond result = new YearToSecond(new YearToMonth(y, m4), DayToSecond.valueOf(abs %= 2.592E9));
        if (milli < 0.0) {
            result = result.neg();
        }
        return result;
    }

    public static YearToSecond valueOf(Duration duration) {
        return duration == null ? null : YearToSecond.valueOf(duration.toMillis());
    }

    @Override
    public final Duration toDuration() {
        return this.yearToMonth.toDuration().plus(this.dayToSecond.toDuration());
    }

    public static YearToSecond valueOf(Period period) {
        return period == null ? null : new YearToSecond(new YearToMonth(period.getYears(), period.getMonths()), new DayToSecond(period.getDays()));
    }

    public static YearToSecond valueOf(String string) {
        if (string != null) {
            try {
                return YearToSecond.valueOf(Double.parseDouble(string));
            }
            catch (NumberFormatException e) {
                Matcher matcher = PATTERN.matcher(string);
                if (matcher.find()) {
                    return new YearToSecond(YearToSecond.parseYM(matcher, 0), YearToSecond.parseDS(matcher, 3));
                }
                try {
                    return YearToSecond.valueOf(Duration.parse(string));
                }
                catch (DateTimeParseException dateTimeParseException) {
                    // empty catch block
                }
            }
        }
        return null;
    }

    static YearToMonth parseYM(Matcher matcher, int groupOffset) {
        boolean negativeYM = "-".equals(matcher.group(groupOffset + 1));
        int years = Integer.parseInt(matcher.group(groupOffset + 2));
        int months = Integer.parseInt(matcher.group(groupOffset + 3));
        return new YearToMonth(years, months, negativeYM);
    }

    static DayToSecond parseDS(Matcher matcher, int groupOffset) {
        boolean negativeDS = "-".equals(matcher.group(groupOffset + 1));
        int days = Convert.convert((Object)matcher.group(groupOffset + 2), Integer.TYPE);
        int hours = Convert.convert((Object)matcher.group(groupOffset + 3), Integer.TYPE);
        int minutes = Convert.convert((Object)matcher.group(groupOffset + 4), Integer.TYPE);
        int seconds = Convert.convert((Object)matcher.group(groupOffset + 5), Integer.TYPE);
        int nano = Convert.convert((Object)StringUtils.rightPad(matcher.group(groupOffset + 6), 9, "0"), Integer.TYPE);
        return new DayToSecond(days, hours, minutes, seconds, nano, negativeDS);
    }

    @Override
    public final YearToSecond neg() {
        return new YearToSecond(this.yearToMonth.neg(), this.dayToSecond.neg());
    }

    @Override
    public final YearToSecond abs() {
        return new YearToSecond(this.yearToMonth.abs(), this.dayToSecond.abs());
    }

    public final YearToMonth getYearToMonth() {
        return this.yearToMonth;
    }

    public final DayToSecond getDayToSecond() {
        return this.dayToSecond;
    }

    public final int getYears() {
        return this.yearToMonth.getYears();
    }

    public final int getMonths() {
        return this.yearToMonth.getMonths();
    }

    public final int getDays() {
        return this.dayToSecond.getDays();
    }

    public final int getHours() {
        return this.dayToSecond.getHours();
    }

    public final int getMinutes() {
        return this.dayToSecond.getMinutes();
    }

    public final int getSeconds() {
        return this.dayToSecond.getSeconds();
    }

    public final int getMilli() {
        return this.dayToSecond.getMilli();
    }

    public final int getMicro() {
        return this.dayToSecond.getMicro();
    }

    public final int getNano() {
        return this.dayToSecond.getNano();
    }

    @Override
    public final int getSign() {
        double value = this.doubleValue();
        return value > 0.0 ? 1 : (value < 0.0 ? -1 : 0);
    }

    @Override
    public final int intValue() {
        return (int)this.doubleValue();
    }

    @Override
    public final long longValue() {
        return (long)this.doubleValue();
    }

    @Override
    public final float floatValue() {
        return (float)this.doubleValue();
    }

    @Override
    public final double doubleValue() {
        return ((double)this.yearToMonth.getYears() * 365.25 + (double)(this.yearToMonth.getMonths() * 30)) * 8.64E7 * (double)this.yearToMonth.getSign() + this.dayToSecond.doubleValue();
    }

    @Override
    public final int compareTo(YearToSecond that) {
        return Double.compare(this.doubleValue(), that.doubleValue());
    }

    public int hashCode() {
        int prime = 31;
        int result = 0;
        int h1 = this.dayToSecond.hashCode();
        int h2 = this.yearToMonth.hashCode();
        if (h1 != 0) {
            result = 31 * result + h1;
        }
        if (h2 != 0) {
            result = 31 * result + h2;
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() == obj.getClass()) {
            YearToSecond other = (YearToSecond)obj;
            if (this.dayToSecond == null ? other.dayToSecond != null : !this.dayToSecond.equals(other.dayToSecond)) {
                return false;
            }
            return !(this.yearToMonth == null ? other.yearToMonth != null : !this.yearToMonth.equals(other.yearToMonth));
        }
        if (obj instanceof YearToMonth) {
            YearToMonth other = (YearToMonth)obj;
            return this.getDayToSecond().intValue() == 0 && this.getYearToMonth().equals(other);
        }
        if (obj instanceof DayToSecond) {
            DayToSecond other = (DayToSecond)obj;
            return this.getYearToMonth().intValue() == 0 && this.getDayToSecond().equals(other);
        }
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.yearToMonth);
        sb.append(' ');
        sb.append(this.dayToSecond);
        return sb.toString();
    }
}

