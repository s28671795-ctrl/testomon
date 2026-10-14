/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.plugin.commons.params.models.GroupBy;
import com.incoresoft.plugin.commons.utils.DateTimeUtils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatisticUtils {
    public static final int DEFAULT_PAGINATION = 20;
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
    public static final String[] DEFAULT_DATE_FORMATS = new String[]{"dd-MM-yyyy HH:mm:ss", "dd-MM-yyyy HH:mm", "dd-MM-yyyy HH", "dd-MM-yyyy", "MM-yyyy", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd", "yyyy-MM"};

    public static Set<Integer> getCamerasInRadius(Collection<? extends Stream> streams, double lat, double lng, double radius) {
        return streams.stream().filter(it -> {
            double cos;
            double sin = Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(it.getLocation().getLatitude()));
            return Math.acos(sin + (cos = Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(it.getLocation().getLatitude())) * Math.cos(Math.toRadians(it.getLocation().getLongitude()) - Math.toRadians(lng)))) * 6371.0 < radius;
        }).map(Stream::getId).collect(Collectors.toSet());
    }

    @NotNull
    public static String getKey(@NotNull GroupBy grouping, @Nullable Integer streamId, @NotNull Date date) {
        Objects.requireNonNull(grouping, "grouping must be not null");
        Objects.requireNonNull(date, "date must be not null");
        switch (grouping) {
            case stream: {
                Objects.requireNonNull(streamId, "stream id must be not null if grouping by stream");
                return String.valueOf(streamId);
            }
            case day: {
                String[] split = StatisticUtils.toTimestamp(date).split(" ")[0].split("-");
                return String.format("%s-%s-%s", split[2], split[1], split[0]);
            }
            case week: {
                return StatisticUtils.getWeek(date);
            }
            case month: {
                return StatisticUtils.getMonth(date);
            }
            case hour: {
                return String.valueOf(StatisticUtils.getDayOfWeek(date));
            }
        }
        throw new IllegalArgumentException(String.format("Cannot create key for grouping '%s'", new Object[]{grouping}));
    }

    @NotNull
    public static String getKey(@NotNull GroupBy grouping, @Nullable Integer streamId, @NotNull Instant instant) {
        switch (grouping) {
            case stream: {
                Objects.requireNonNull(streamId, "stream id must be not null if grouping by stream");
                return String.valueOf(streamId);
            }
            case day: {
                String[] split = DateTimeUtils.toTimestamp(instant).split(" ")[0].split("-");
                return String.format("%s-%s-%s", split[2], split[1], split[0]);
            }
            case week: {
                return DateTimeUtils.getWeek(instant);
            }
            case month: {
                return DateTimeUtils.getMonth(instant);
            }
            case hour: {
                String[] dateTime = DateTimeUtils.toTimestamp(instant, true).split(" ");
                String[] days = dateTime[0].split("-");
                String[] hours = dateTime[1].split(":");
                return String.format("%s:%s", hours[0], hours[1]);
            }
        }
        throw new IllegalArgumentException(String.format("Cannot create key for grouping '%s'", new Object[]{grouping}));
    }

    public static int getHourOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(11);
    }

    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(7);
        if (--dayOfWeek < 0) {
            dayOfWeek = 6;
        }
        if (--dayOfWeek < 0) {
            dayOfWeek = 6;
        }
        return dayOfWeek;
    }

    private static String getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format("%d.%d.%d", calendar.get(5), calendar.get(2), calendar.get(1));
    }

    private static String getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(7);
        if (dayOfWeek < 2) {
            calendar.add(3, -1);
        }
        calendar.set(7, 2);
        String week = calendar.get(5) + "." + (calendar.get(2) + 1) + "." + calendar.get(1);
        calendar.setTime(new Date(calendar.getTime().getTime() + 518400000L));
        week = week + " - " + calendar.get(5) + "." + (calendar.get(2) + 1) + "." + calendar.get(1);
        return week;
    }

    private static String getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
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

    public static String formatDateTime(Date date) {
        return StatisticUtils.formatDateTime(date, false);
    }

    public static String formatDateTime(Date date, boolean utc) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        if (utc) {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    public static String formatDate(Date date) {
        return StatisticUtils.formatDate(date, false);
    }

    public static String formatDate(Date date, boolean utc) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        if (utc) {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    public static Date parseDateTime(String date) throws ParseException {
        return StatisticUtils.parseDateTime(date, false);
    }

    public static Date parseDateTime(String date, boolean utc) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        if (utc) {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        return sdf.parse(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return StatisticUtils.parseDate(date, false);
    }

    public static Date parseDate(String date, boolean utc) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        if (utc) {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        return sdf.parse(date);
    }

    @NotNull
    public static Timestamp parseTimestamp(String timestamp) throws ParseException {
        return new Timestamp(StatisticUtils.fromTimestamp(timestamp, true).getTime());
    }

    public static String toTimestamp(Date date) {
        return StatisticUtils.toTimestamp(date, false);
    }

    public static String toTimestamp(Date date, boolean convertToUtc) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
        if (convertToUtc) {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    public static Date fromTimestamp(String timestamp) throws ParseException {
        return StatisticUtils.fromTimestamp(timestamp, false);
    }

    public static Date fromTimestamp(String timestamp, boolean convertFromUtc) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
        if (convertFromUtc) {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        return sdf.parse(timestamp);
    }

    @NotNull
    public static Timestamp zonedDateTimeToTimestamp(ZonedDateTime startDate) {
        return Timestamp.from(startDate.toInstant());
    }
}

