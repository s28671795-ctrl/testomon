/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.exception.DateParseException;

public final class NowAndTodayUtil {
    private static final String NOW = "now";
    private static final int NOW_LENGTH = "now".length();
    private static final Pattern NOW_OFFSET_PATTERN = Pattern.compile("^(now)([+-])(\\d+)([mhdy].*)$");
    private static final String TODAY = "today";
    private static final int TODAY_LENGTH = "today".length();
    private static final Pattern TODAY_OFFSET_PATTERN = Pattern.compile("^(today)([+\\-])(\\d+)$");

    private NowAndTodayUtil() {
    }

    public static boolean isNowOrTodayFormat(String value) {
        boolean ret = false;
        if (value != null) {
            String lowerValue = value.toLowerCase();
            if (lowerValue.length() >= NOW_LENGTH && lowerValue.startsWith(NOW)) {
                ret = true;
            } else if (lowerValue.length() >= TODAY_LENGTH && lowerValue.startsWith(TODAY)) {
                ret = true;
            }
        }
        return ret;
    }

    public static Date doNowOrToday(String value, String colType) throws DateParseException {
        if (!NowAndTodayUtil.isNowOrTodayFormat(value)) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        String lowerValue = value.toLowerCase();
        if (lowerValue.startsWith(NOW)) {
            NowAndTodayUtil.parseNow(value, lowerValue, c);
        } else if (lowerValue.startsWith(TODAY)) {
            NowAndTodayUtil.parseToday(value, lowerValue, c);
        } else {
            throw new DateParseException("Programmer error: " + value + " does not start with 'NOW' or 'TODAY'");
        }
        Date today = c.getTime();
        if (colType == null || colType.length() == 0) {
            throw new DateParseException("Must supply non-null column type when using 'NOW' or 'TODAY' value.");
        }
        if (colType.equalsIgnoreCase("timestamp") || colType.equalsIgnoreCase("datetime")) {
            return new Timestamp(today.getTime());
        }
        if (colType.equalsIgnoreCase("time")) {
            return new Time(today.getTime());
        }
        if (colType.equalsIgnoreCase("date")) {
            return new java.sql.Date(today.getTime());
        }
        throw new DateParseException("Unrecognized colType " + colType + " when using 'NOW' or 'TODAY' value; expected one of date, time, datetime, or timestamp");
    }

    private static void parseNow(String value, String lowerValue, Calendar c) throws DateParseException {
        if (lowerValue.length() > NOW_LENGTH) {
            int calendarField;
            String units;
            int offset;
            Matcher matcher = NOW_OFFSET_PATTERN.matcher(lowerValue);
            if (!matcher.find()) {
                throw new DateParseException("Improper value in 'NOW' value: " + value + ". 'NOW' must be followed by + or -, then numeric offset, then units (h{our{s}}, m{inute{s}}, d{ay{s}}, or y{ears}");
            }
            if (matcher.groupCount() != 4) {
                throw new DateParseException("Improper value in 'NOW' value: " + value + ". Pattern match returned " + matcher.groupCount() + " instead of 4");
            }
            char sign = matcher.group(2).charAt(0);
            if (sign != '+' && sign != '-') {
                throw new DateParseException("Improper sign in 'NOW' value '" + lowerValue + "'");
            }
            String offsetStr = matcher.group(3);
            try {
                offset = Integer.parseInt(offsetStr);
            }
            catch (NumberFormatException e) {
                throw new DateParseException("Improper offset in 'NOW' value '" + value + "'");
            }
            if (sign == '-') {
                offset = -offset;
            }
            if ("years".startsWith(units = matcher.group(4))) {
                calendarField = 1;
            } else if ("days".startsWith(units)) {
                calendarField = 5;
            } else if ("hours".startsWith(units)) {
                calendarField = 10;
            } else if ("minutes".startsWith(units)) {
                calendarField = 12;
            } else {
                throw new DateParseException("Improper units in 'NOW' value: '" + units + "', must be y{ear{s}}, d{ay{s}}, h{our{s}}, or m{inute{s}}");
            }
            c.add(calendarField, offset);
        }
    }

    private static void parseToday(String value, String lowerValue, Calendar c) throws DateParseException {
        if (lowerValue.length() > TODAY_LENGTH) {
            int offset;
            Matcher matcher = TODAY_OFFSET_PATTERN.matcher(lowerValue);
            if (!matcher.find()) {
                throw new DateParseException("Improper value in 'TODAY' value: " + value + ". 'TODAY' must be followed by + or -, then numeric offset");
            }
            if (matcher.groupCount() != 3) {
                throw new DateParseException("Improper value in 'TODAY' value: " + value + ". Pattern match returned " + matcher.groupCount() + " instead of 3");
            }
            char sign = matcher.group(2).charAt(0);
            if (sign != '+' && sign != '-') {
                throw new DateParseException("Improper sign in 'TODAY' value '" + lowerValue + "'");
            }
            String offsetStr = matcher.group(3);
            try {
                offset = Integer.parseInt(offsetStr);
            }
            catch (NumberFormatException e) {
                throw new DateParseException("Improper offset in 'TODAY' value '" + value + "'");
            }
            if (sign == '-') {
                offset = -offset;
            }
            c.add(5, offset);
        }
    }
}

