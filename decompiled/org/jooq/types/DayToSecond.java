/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.types;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.tools.StringUtils;
import org.jooq.types.Interval;
import org.jooq.types.YearToSecond;

public final class DayToSecond
extends Number
implements Interval,
Comparable<DayToSecond> {
    private static final Pattern PATTERN_DTS = Pattern.compile("^([+-])?(?:(\\d+) )?(\\d+):(\\d+):(\\d+)(?:\\.(\\d+))?$");
    private static final Pattern PATTERN_DTM = Pattern.compile("^([+-])?(?:(\\d+) )?(\\d+):(\\d+)()()$");
    private static final Pattern PATTERN_DTH = Pattern.compile("^([+-])?(?:(\\d+) )?(\\d+)()()()$");
    private static final Pattern PATTERN_HTS = Pattern.compile("^([+-])?()(\\d+):(\\d+):(\\d+)(?:\\.(\\d+))?$");
    private static final Pattern PATTERN_HTM = Pattern.compile("^([+-])?()(\\d+):(\\d+)()()$");
    private static final Pattern PATTERN_MTS = Pattern.compile("^([+-])?()()(\\d+):(\\d+)(?:\\.(\\d+))?$");
    private final boolean negative;
    private final int days;
    private final int hours;
    private final int minutes;
    private final int seconds;
    private final int nano;

    public DayToSecond() {
        this(0, 0, 0, 0, 0, false);
    }

    public DayToSecond(int days) {
        this(days, 0, 0, 0, 0, false);
    }

    public DayToSecond(int days, int hours) {
        this(days, hours, 0, 0, 0, false);
    }

    public DayToSecond(int days, int hours, int minutes) {
        this(days, hours, minutes, 0, 0, false);
    }

    public DayToSecond(int days, int hours, int minutes, int seconds) {
        this(days, hours, minutes, seconds, 0, false);
    }

    public DayToSecond(int days, int hours, int minutes, int seconds, int nano) {
        this(days, hours, minutes, seconds, nano, false);
    }

    DayToSecond(int days, int hours, int minutes, int seconds, int nano, boolean negative) {
        if (Math.abs(nano) >= 1000000000) {
            seconds += nano / 1000000000;
            nano %= 1000000000;
        }
        if (Math.abs(seconds) >= 60) {
            minutes += seconds / 60;
            seconds %= 60;
        }
        if (Math.abs(minutes) >= 60) {
            hours += minutes / 60;
            minutes %= 60;
        }
        if (Math.abs(hours) >= 24) {
            days += hours / 24;
            hours %= 24;
        }
        this.negative = negative;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.nano = nano;
    }

    public static DayToSecond valueOf(String string) {
        if (string != null) {
            try {
                return DayToSecond.valueOf(Double.parseDouble(string));
            }
            catch (NumberFormatException e) {
                DayToSecond result = DayToSecond.dayToSecond(string);
                if (result != null) {
                    return result;
                }
                try {
                    return DayToSecond.valueOf(Duration.parse(string));
                }
                catch (DateTimeParseException dateTimeParseException) {
                    // empty catch block
                }
            }
        }
        return null;
    }

    public static DayToSecond day(String string) {
        try {
            return string == null ? null : new DayToSecond(Integer.parseInt(string));
        }
        catch (NumberFormatException ignore) {
            return null;
        }
    }

    public static DayToSecond dayToHour(String string) {
        Matcher matcher;
        if (string != null && (matcher = PATTERN_DTH.matcher(string)).find()) {
            return YearToSecond.parseDS(matcher, 0);
        }
        return null;
    }

    public static DayToSecond dayToMinute(String string) {
        Matcher matcher;
        if (string != null && (matcher = PATTERN_DTM.matcher(string)).find()) {
            return YearToSecond.parseDS(matcher, 0);
        }
        return null;
    }

    public static DayToSecond dayToSecond(String string) {
        Matcher matcher;
        if (string != null && (matcher = PATTERN_DTS.matcher(string)).find()) {
            return YearToSecond.parseDS(matcher, 0);
        }
        return null;
    }

    public static DayToSecond hour(String string) {
        try {
            return string == null ? null : new DayToSecond(0, Integer.parseInt(string));
        }
        catch (NumberFormatException ignore) {
            return null;
        }
    }

    public static DayToSecond hourToMinute(String string) {
        Matcher matcher;
        if (string != null && (matcher = PATTERN_HTM.matcher(string)).find()) {
            return YearToSecond.parseDS(matcher, 0);
        }
        return null;
    }

    public static DayToSecond hourToSecond(String string) {
        Matcher matcher;
        if (string != null && (matcher = PATTERN_HTS.matcher(string)).find()) {
            return YearToSecond.parseDS(matcher, 0);
        }
        return null;
    }

    public static DayToSecond minute(String string) {
        try {
            return string == null ? null : new DayToSecond(0, 0, Integer.parseInt(string));
        }
        catch (NumberFormatException ignore) {
            return null;
        }
    }

    public static DayToSecond minuteToSecond(String string) {
        Matcher matcher;
        if (string != null && (matcher = PATTERN_MTS.matcher(string)).find()) {
            return YearToSecond.parseDS(matcher, 0);
        }
        return null;
    }

    public static DayToSecond second(String string) {
        try {
            return string == null ? null : DayToSecond.valueOf(Double.parseDouble(string) * 1000.0);
        }
        catch (NumberFormatException ignore) {
            return null;
        }
    }

    public static DayToSecond valueOf(double milli) {
        double abs = Math.abs(milli);
        int n = (int)(abs % 1000.0 * 1000000.0);
        abs = Math.floor(abs / 1000.0);
        int s2 = (int)(abs % 60.0);
        abs = Math.floor(abs / 60.0);
        int m4 = (int)(abs % 60.0);
        abs = Math.floor(abs / 60.0);
        int h2 = (int)(abs % 24.0);
        abs = Math.floor(abs / 24.0);
        int d = (int)abs;
        DayToSecond result = new DayToSecond(d, h2, m4, s2, n);
        if (milli < 0.0) {
            result = result.neg();
        }
        return result;
    }

    public static DayToSecond valueOf(long second, int nanos) {
        long abs = Math.abs(second);
        int s2 = (int)(abs % 60L);
        int m4 = (int)((abs /= 60L) % 60L);
        int h2 = (int)((abs /= 60L) % 24L);
        int d = (int)(abs /= 24L);
        DayToSecond result = new DayToSecond(d, h2, m4, s2, nanos);
        if (second < 0L) {
            result = result.neg();
        }
        return result;
    }

    public static DayToSecond valueOf(Duration duration) {
        if (duration == null) {
            return null;
        }
        long s2 = duration.get(ChronoUnit.SECONDS);
        int n = (int)duration.get(ChronoUnit.NANOS);
        if (s2 < 0L) {
            n = 1000000000 - n;
            ++s2;
        }
        return DayToSecond.valueOf(s2, n);
    }

    @Override
    public final Duration toDuration() {
        return Duration.ofSeconds((long)this.getTotalSeconds(), this.getSign() * this.getNano());
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
        return this.getTotalMilli();
    }

    @Override
    public final DayToSecond neg() {
        return new DayToSecond(this.days, this.hours, this.minutes, this.seconds, this.nano, !this.negative);
    }

    @Override
    public final DayToSecond abs() {
        return new DayToSecond(this.days, this.hours, this.minutes, this.seconds, this.nano, false);
    }

    public final int getDays() {
        return this.days;
    }

    public final int getHours() {
        return this.hours;
    }

    public final int getMinutes() {
        return this.minutes;
    }

    public final int getSeconds() {
        return this.seconds;
    }

    public final int getMilli() {
        return this.nano / 1000000;
    }

    public final int getMicro() {
        return this.nano / 1000;
    }

    public final int getNano() {
        return this.nano;
    }

    public final double getTotalDays() {
        return (double)this.getSign() * ((double)this.nano / 8.64E13 + (double)this.seconds / 86400.0 + (double)this.minutes / 1440.0 + (double)this.hours / 24.0 + (double)this.days);
    }

    public final double getTotalHours() {
        return (double)this.getSign() * ((double)this.nano / 3.6E12 + (double)this.seconds / 3600.0 + (double)this.minutes / 60.0 + (double)this.hours + 24.0 * (double)this.days);
    }

    public final double getTotalMinutes() {
        return (double)this.getSign() * ((double)this.nano / 6.0E10 + (double)this.seconds / 60.0 + (double)this.minutes + 60.0 * (double)this.hours + 1440.0 * (double)this.days);
    }

    public final double getTotalSeconds() {
        return (double)this.getSign() * ((double)this.nano / 1.0E9 + (double)this.seconds + 60.0 * (double)this.minutes + 3600.0 * (double)this.hours + 86400.0 * (double)this.days);
    }

    public final double getTotalMilli() {
        return (double)this.getSign() * ((double)this.nano / 1000000.0 + 1000.0 * (double)this.seconds + 60000.0 * (double)this.minutes + 3600000.0 * (double)this.hours + 8.64E7 * (double)this.days);
    }

    public final double getTotalMicro() {
        return (double)this.getSign() * ((double)this.nano / 1000.0 + 1000000.0 * (double)this.seconds + 6.0E7 * (double)this.minutes + 3.6E9 * (double)this.hours + 8.64E10 * (double)this.days);
    }

    public final double getTotalNano() {
        return (double)this.getSign() * ((double)this.nano + 1.0E9 * (double)this.seconds + 6.0E10 * (double)this.minutes + 3.6E12 * (double)this.hours + 8.64E13 * (double)this.days);
    }

    @Override
    public final int getSign() {
        return this.negative ? -1 : 1;
    }

    @Override
    public final int compareTo(DayToSecond that) {
        if (this.days < that.days) {
            return -1;
        }
        if (this.days > that.days) {
            return 1;
        }
        if (this.hours < that.hours) {
            return -1;
        }
        if (this.hours > that.hours) {
            return 1;
        }
        if (this.minutes < that.minutes) {
            return -1;
        }
        if (this.minutes > that.minutes) {
            return 1;
        }
        if (this.seconds < that.seconds) {
            return -1;
        }
        if (this.seconds > that.seconds) {
            return 1;
        }
        return Integer.compare(this.nano, that.nano);
    }

    public final int hashCode() {
        int prime = 31;
        int result = 0;
        if (this.days != 0) {
            result = 31 * result + this.days;
        }
        if (this.hours != 0) {
            result = 31 * result + this.hours;
        }
        if (this.minutes != 0) {
            result = 31 * result + this.minutes;
        }
        if (this.nano != 0) {
            result = 31 * result + this.nano;
        }
        if (this.seconds != 0) {
            result = 31 * result + this.seconds;
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
            DayToSecond other = (DayToSecond)obj;
            if (this.days != other.days) {
                return false;
            }
            if (this.hours != other.hours) {
                return false;
            }
            if (this.minutes != other.minutes) {
                return false;
            }
            if (this.nano != other.nano) {
                return false;
            }
            if (this.seconds != other.seconds) {
                return false;
            }
            return this.negative == other.negative || this.doubleValue() == 0.0;
        }
        if (obj instanceof YearToSecond) {
            return obj.equals(this);
        }
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.negative ? "-" : "+");
        sb.append(this.days);
        sb.append(" ");
        if (this.hours < 10) {
            sb.append("0");
        }
        sb.append(this.hours);
        sb.append(":");
        if (this.minutes < 10) {
            sb.append("0");
        }
        sb.append(this.minutes);
        sb.append(":");
        if (this.seconds < 10) {
            sb.append("0");
        }
        sb.append(this.seconds);
        sb.append(".");
        sb.append(StringUtils.leftPad("" + this.nano, 9, "0"));
        return sb.toString();
    }
}

