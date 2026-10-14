/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;

public class DateTimeUtils {
    public static final int STEP = 60000;
    public static final long MINUTE = 60000L;
    public static final long HOUR = 3600000L;
    public static final long DAY = 86400000L;
    public static final long WEEK = 604800000L;
    public static final long MONTH = 2592000000L;
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_TIME_OPTIONAL_FORMAT = "dd-MM-yyyy[ HH:mm:ss]";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String ZONED_DATE_TIME_FORMAT_WITH_TIMEZONE = "dd-MM-yyyy[ HH:mm:ss][ z]";
    public static final String ZONED_DATE_TIME_FORMAT_WITHOUT_TIMEZONE = "dd-MM-yyyy[ HH:mm:ss]";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy[ HH:mm:ss]").parseDefaulting(ChronoField.HOUR_OF_DAY, 0L).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0L).parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0L).toFormatter().withZone(ZoneId.systemDefault());
    private static final DateTimeFormatter DATE_TIME_FORMATTER_UTC = new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy[ HH:mm:ss]").parseDefaulting(ChronoField.HOUR_OF_DAY, 0L).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0L).parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0L).toFormatter().withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy").toFormatter().withZone(ZoneId.systemDefault());
    private static final DateTimeFormatter DATE_FORMATTER_UTC = new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy").toFormatter().withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter().withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter TIMESTAMP_FORMATTER_UTC = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter().withZone(ZoneId.of("UTC"));

    public static int getHourOfDay(Instant date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.toEpochMilli());
        return calendar.get(11);
    }

    public static int getDayOfWeek(Instant date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.toEpochMilli());
        int dayOfWeek = calendar.get(7);
        if (--dayOfWeek < 0) {
            dayOfWeek = 6;
        }
        if (--dayOfWeek < 0) {
            dayOfWeek = 6;
        }
        return dayOfWeek;
    }

    public static String getDay(Instant date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.toEpochMilli());
        return String.format("%d.%d.%d", calendar.get(5), calendar.get(2), calendar.get(1));
    }

    public static String getWeek(Instant date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.toEpochMilli());
        int dayOfWeek = calendar.get(7);
        if (dayOfWeek < 2) {
            calendar.add(3, -1);
        }
        calendar.set(7, 2);
        String week = calendar.get(5) + "." + (calendar.get(2) + 1) + "." + calendar.get(1);
        calendar.setTimeInMillis(calendar.toInstant().toEpochMilli() + 518400000L);
        week = week + " - " + calendar.get(5) + "." + (calendar.get(2) + 1) + "." + calendar.get(1);
        return week;
    }

    public static String getMonth(Instant date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.toEpochMilli());
        calendar.set(5, 1);
        String month = calendar.get(5) + "." + (calendar.get(2) + 1) + "." + calendar.get(1);
        calendar.set(5, calendar.getActualMaximum(5));
        month = month + " - " + calendar.get(5) + "." + (calendar.get(2) + 1) + "." + calendar.get(1);
        return month;
    }

    public static String convertTimestampToDateTime(String timestamp) {
        String[] dateTime = timestamp.split(" ");
        String[] date = dateTime[0].split("-");
        return date[2] + "-" + date[1] + "-" + date[0] + " " + dateTime[1];
    }

    public static String formatDateTime(Instant date) {
        return DateTimeUtils.formatDateTime(date, false);
    }

    public static String formatDateTime(Instant date, boolean utc) {
        if (utc) {
            return DATE_TIME_FORMATTER_UTC.format(date);
        }
        return DATE_TIME_FORMATTER.format(date);
    }

    public static String formatDate(Instant date) {
        return DateTimeUtils.formatDate(date, false);
    }

    public static String formatDate(Instant date, boolean utc) {
        if (utc) {
            return DATE_FORMATTER_UTC.format(date);
        }
        return DATE_FORMATTER.format(date);
    }

    public static Instant parseDateTime(String date) {
        return DateTimeUtils.parseDateTime(date, false);
    }

    public static Instant parseDateTime(String date, boolean utc) {
        if (utc) {
            return Instant.from(DATE_TIME_FORMATTER_UTC.parse(date));
        }
        return Instant.from(DATE_TIME_FORMATTER.parse(date));
    }

    public static Instant parseDate(String date) {
        return DateTimeUtils.parseDate(date, false);
    }

    public static Instant parseDate(String date, boolean utc) {
        if (utc) {
            return Instant.from(DATE_FORMATTER_UTC.parse(date));
        }
        return Instant.from(DATE_FORMATTER.parse(date));
    }

    public static String toTimestamp(Instant instant) {
        return DateTimeUtils.toTimestamp(instant, false);
    }

    public static String toTimestamp(Instant date, boolean convertToUtc) {
        if (convertToUtc) {
            return TIMESTAMP_FORMATTER_UTC.format(date);
        }
        return TIMESTAMP_FORMATTER.format(date);
    }

    public static Instant fromTimestamp(String timestamp) {
        return DateTimeUtils.fromTimestamp(timestamp, false);
    }

    public static Instant fromTimestamp(String timestamp, boolean convertFromUtc) {
        if (convertFromUtc) {
            return Instant.from(TIMESTAMP_FORMATTER_UTC.parse(timestamp));
        }
        return Instant.from(TIMESTAMP_FORMATTER_UTC.parse(timestamp));
    }

    public static class InstantToDateTimeSerializer
    implements ObjectSerializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            if (object instanceof Instant) {
                serializer.write(DateTimeUtils.formatDateTime((Instant)object));
            } else {
                serializer.write(null);
            }
        }
    }
}

