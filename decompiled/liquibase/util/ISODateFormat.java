/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class ISODateFormat {
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final SimpleDateFormat dateTimeFormatWithSpace = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DATE_TIME_FORMAT_STRING_WITH_SPACE = "yyyy-MM-dd HH:mm:ss";

    public String format(java.sql.Date date) {
        return this.dateFormat.format(date);
    }

    public String format(Time date) {
        return this.timeFormat.format(date);
    }

    public String format(Timestamp date) {
        StringBuilder sb = new StringBuilder(this.dateTimeFormat.format(date));
        int nanos = date.getNanos();
        if (nanos != 0) {
            int lastNotNullIndex;
            String nanosString = String.format("%09d", nanos);
            for (lastNotNullIndex = 8; lastNotNullIndex > 0 && nanosString.charAt(lastNotNullIndex) == '0'; --lastNotNullIndex) {
            }
            sb.append('.');
            sb.append(nanosString, 0, lastNotNullIndex + 1);
        }
        return sb.toString();
    }

    public String format(Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof java.sql.Date) {
            return this.format((java.sql.Date)date);
        }
        if (date instanceof Time) {
            return this.format((Time)date);
        }
        if (date instanceof Timestamp) {
            return this.format((Timestamp)date);
        }
        if (date instanceof Date) {
            return this.format(new Timestamp(date.getTime()));
        }
        throw new RuntimeException("Unknown type: " + date.getClass().getName());
    }

    public Date parse(String dateAsString) throws ParseException {
        int nanos;
        if (dateAsString == null) {
            return null;
        }
        int length = dateAsString.length();
        switch (length) {
            case 8: {
                return new Time(this.timeFormat.parse(dateAsString).getTime());
            }
            case 10: {
                return new java.sql.Date(this.dateFormat.parse(dateAsString).getTime());
            }
            case 19: {
                if (dateAsString.contains(" ")) {
                    return new Timestamp(this.dateTimeFormatWithSpace.parse(dateAsString).getTime());
                }
                return new Timestamp(this.dateTimeFormat.parse(dateAsString).getTime());
            }
        }
        if (dateAsString.contains(":") && !dateAsString.contains("-")) {
            if (dateAsString.contains(".")) {
                throw new ParseException(String.format("Unknown date format to parse: %s.", dateAsString), 0);
            }
            return new Time(this.timeFormat.parse(dateAsString).getTime());
        }
        if (length < 19 || dateAsString.charAt(19) != '.') {
            throw new ParseException(String.format("Unknown date format to parse: %s.", dateAsString), 0);
        }
        long time = 0L;
        time = dateAsString.contains(" ") ? this.dateTimeFormatWithSpace.parse(dateAsString.substring(0, 19)).getTime() : this.dateTimeFormat.parse(dateAsString.substring(0, 19)).getTime();
        try {
            OffsetDateTime odt = OffsetDateTime.parse(dateAsString);
            ZonedDateTime zonedDateTime = odt.toZonedDateTime();
            nanos = zonedDateTime.getNano();
        }
        catch (DateTimeParseException dtpe) {
            if (dateAsString.contains(" ")) {
                dateAsString = dateAsString.replaceAll(" ", "T");
            }
            nanos = Integer.parseInt(dateAsString.substring(20));
            while (length < 29) {
                nanos *= 10;
                ++length;
            }
        }
        Timestamp timestamp = new Timestamp(time);
        timestamp.setNanos(nanos);
        return timestamp;
    }
}

