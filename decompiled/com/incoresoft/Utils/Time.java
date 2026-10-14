/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Time {
    private static Time instance;
    public SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    public SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public SimpleDateFormat sdfShort = new SimpleDateFormat("MM-dd HH:mm");
    public SimpleDateFormat sdfShortWithSec = new SimpleDateFormat("MM-dd HH:mm:ss");
    public SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Time getInstance() {
        Time localInstance = instance;
        if (localInstance != null) return localInstance;
        Class<Time> clazz = Time.class;
        synchronized (Time.class) {
            localInstance = instance;
            if (localInstance != null) return localInstance;
            instance = localInstance = new Time();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return localInstance;
        }
    }

    public static long timeToUnix(Date time) {
        return time.getTime() / 1000L;
    }

    public static String serverTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String timeToString(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(time);
    }

    public static String dateToString(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    public static long currentUnixTime() {
        return new Date().getTime() / 1000L;
    }

    public static String timeToStringShort(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(time);
    }

    public static Date stringToDate(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd_HH:mm:ss");
        return format.parse(time);
    }
}

