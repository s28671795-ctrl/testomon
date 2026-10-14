/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {
    public static final long SECOND = 1000L;
    public static final long MINUTE = 60000L;
    public static final long HOUR = 3600000L;
    public static final long DAY = 86400000L;
    public static final long WEEK = 604800000L;
    public static final long MONTH = 2592000000L;
    private static Clock instance = Clock.systemUTC();

    public static Clock getClock() {
        return instance;
    }

    public static void setClock(Clock clock) {
        instance = clock;
    }

    public static String getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        String month = calendar.get(5) + "." + (calendar.get(2) + 1) + "." + calendar.get(1);
        calendar.set(5, calendar.getActualMaximum(5));
        month = month + " - " + calendar.get(5) + "." + (calendar.get(2) + 1) + "." + calendar.get(1);
        return month;
    }

    public static String formatDateTime(Date date) {
        return DateTimeUtils.formatDateTime(date, false);
    }

    public static String formatDateTime(Date date, boolean utc) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (utc) {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    public static String formatDate(Date date) {
        return DateTimeUtils.formatDate(date, false);
    }

    public static String formatDate(Date date, boolean utc) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if (utc) {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    public static Date parseDate(String date) throws ParseException {
        return DateTimeUtils.parseDate(date, false);
    }

    public static Date parseDate(String date, boolean utc) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if (utc) {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        return sdf.parse(date);
    }
}

