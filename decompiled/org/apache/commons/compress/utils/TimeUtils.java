/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.file.attribute.FileTimes
 */
package org.apache.commons.compress.utils;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.file.attribute.FileTimes;

public final class TimeUtils {
    static final long HUNDRED_NANOS_PER_MILLISECOND = TimeUnit.MILLISECONDS.toNanos(1L) / 100L;
    static final long WINDOWS_EPOCH_OFFSET = -116444736000000000L;

    public static boolean isUnixTime(FileTime time) {
        return TimeUtils.isUnixTime(TimeUtils.toUnixTime(time));
    }

    public static boolean isUnixTime(long seconds) {
        return Integer.MIN_VALUE <= seconds && seconds <= Integer.MAX_VALUE;
    }

    @Deprecated
    public static Date ntfsTimeToDate(long ntfsTime) {
        return FileTimes.ntfsTimeToDate((long)ntfsTime);
    }

    @Deprecated
    public static FileTime ntfsTimeToFileTime(long ntfsTime) {
        return FileTimes.ntfsTimeToFileTime((long)ntfsTime);
    }

    @Deprecated
    public static Date toDate(FileTime fileTime) {
        return FileTimes.toDate((FileTime)fileTime);
    }

    @Deprecated
    public static FileTime toFileTime(Date date) {
        return FileTimes.toFileTime((Date)date);
    }

    @Deprecated
    public static long toNtfsTime(Date date) {
        return FileTimes.toNtfsTime((Date)date);
    }

    @Deprecated
    public static long toNtfsTime(FileTime fileTime) {
        return FileTimes.toNtfsTime((FileTime)fileTime);
    }

    public static long toNtfsTime(long javaTime) {
        long javaHundredNanos = javaTime * HUNDRED_NANOS_PER_MILLISECOND;
        return Math.subtractExact(javaHundredNanos, -116444736000000000L);
    }

    public static long toUnixTime(FileTime fileTime) {
        return fileTime != null ? fileTime.to(TimeUnit.SECONDS) : 0L;
    }

    public static FileTime truncateToHundredNanos(FileTime fileTime) {
        Instant instant = fileTime.toInstant();
        return FileTime.from(Instant.ofEpochSecond(instant.getEpochSecond(), instant.getNano() / 100 * 100));
    }

    public static FileTime unixTimeToFileTime(long time) {
        return FileTime.from(time, TimeUnit.SECONDS);
    }

    private TimeUtils() {
    }
}

