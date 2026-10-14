/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRules;
import java.util.Locale;
import org.h2.util.DateTimeUtils;

public abstract class TimeZoneProvider {
    public static final TimeZoneProvider UTC = new Simple(0);
    public static TimeZoneProvider[] CACHE;
    private static final int CACHE_SIZE = 32;

    public static TimeZoneProvider ofOffset(int n) {
        if (n == 0) {
            return UTC;
        }
        if (n < -64800 || n > 64800) {
            throw new IllegalArgumentException("Time zone offset " + n + " seconds is out of range");
        }
        return new Simple(n);
    }

    public static TimeZoneProvider ofId(String string) throws RuntimeException {
        TimeZoneProvider timeZoneProvider;
        int n;
        int n2 = string.length();
        if (n2 == 1 && string.charAt(0) == 'Z') {
            return UTC;
        }
        int n3 = 0;
        if (string.startsWith("GMT") || string.startsWith("UTC")) {
            if (n2 == 3) {
                return UTC;
            }
            n3 = 3;
        }
        if (n2 > n3) {
            n = 0;
            char c = string.charAt(n3);
            if (n2 > n3 + 1) {
                if (c == '+') {
                    c = string.charAt(++n3);
                } else if (c == '-') {
                    n = 1;
                    c = string.charAt(++n3);
                }
            }
            if (n3 != 3 && c >= '0' && c <= '9') {
                int n4 = c - 48;
                if (++n3 < n2 && (c = string.charAt(n3)) >= '0' && c <= '9') {
                    n4 = n4 * 10 + c - 48;
                    ++n3;
                }
                if (n3 == n2) {
                    int n5 = n4 * 3600;
                    return TimeZoneProvider.ofOffset(n != 0 ? -n5 : n5);
                }
                if (string.charAt(n3) == ':' && ++n3 < n2 && (c = string.charAt(n3)) >= '0' && c <= '9') {
                    int n6 = c - 48;
                    if (++n3 < n2 && (c = string.charAt(n3)) >= '0' && c <= '9') {
                        n6 = n6 * 10 + c - 48;
                        ++n3;
                    }
                    if (n3 == n2) {
                        int n7 = (n4 * 60 + n6) * 60;
                        return TimeZoneProvider.ofOffset(n != 0 ? -n7 : n7);
                    }
                    if (string.charAt(n3) == ':' && ++n3 < n2 && (c = string.charAt(n3)) >= '0' && c <= '9') {
                        int n8 = c - 48;
                        if (++n3 < n2 && (c = string.charAt(n3)) >= '0' && c <= '9') {
                            n8 = n8 * 10 + c - 48;
                            ++n3;
                        }
                        if (n3 == n2) {
                            int n9 = (n4 * 60 + n6) * 60 + n8;
                            return TimeZoneProvider.ofOffset(n != 0 ? -n9 : n9);
                        }
                    }
                }
            }
            if (n3 > 0) {
                throw new IllegalArgumentException(string);
            }
        }
        n = string.hashCode() & 0x1F;
        TimeZoneProvider[] timeZoneProviderArray = CACHE;
        if (timeZoneProviderArray != null && (timeZoneProvider = timeZoneProviderArray[n]) != null && timeZoneProvider.getId().equals(string)) {
            return timeZoneProvider;
        }
        WithTimeZone withTimeZone = new WithTimeZone(ZoneId.of(string, ZoneId.SHORT_IDS));
        if (timeZoneProviderArray == null) {
            CACHE = timeZoneProviderArray = new TimeZoneProvider[32];
        }
        timeZoneProviderArray[n] = withTimeZone;
        return withTimeZone;
    }

    public static TimeZoneProvider getDefault() {
        ZoneOffset zoneOffset;
        ZoneId zoneId = ZoneId.systemDefault();
        if (zoneId instanceof ZoneOffset) {
            zoneOffset = (ZoneOffset)zoneId;
        } else {
            ZoneRules zoneRules = zoneId.getRules();
            if (!zoneRules.isFixedOffset()) {
                return new WithTimeZone(zoneId);
            }
            zoneOffset = zoneRules.getOffset(Instant.EPOCH);
        }
        return TimeZoneProvider.ofOffset(zoneOffset.getTotalSeconds());
    }

    public abstract int getTimeZoneOffsetUTC(long var1);

    public abstract int getTimeZoneOffsetLocal(long var1, long var3);

    public abstract long getEpochSecondsFromLocal(long var1, long var3);

    public abstract String getId();

    public abstract String getShortId(long var1);

    public boolean hasFixedOffset() {
        return false;
    }

    static final class WithTimeZone
    extends TimeZoneProvider {
        static final long SECONDS_PER_PERIOD = 12622780800L;
        static final long SECONDS_PER_YEAR = 31556952L;
        private static volatile DateTimeFormatter TIME_ZONE_FORMATTER;
        private final ZoneId zoneId;

        WithTimeZone(ZoneId zoneId) {
            this.zoneId = zoneId;
        }

        public int hashCode() {
            return this.zoneId.hashCode() + 951689;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || object.getClass() != WithTimeZone.class) {
                return false;
            }
            return this.zoneId.equals(((WithTimeZone)object).zoneId);
        }

        @Override
        public int getTimeZoneOffsetUTC(long l) {
            if (l > 31556889832715999L) {
                l -= 12622780800L;
            } else if (l < -31557014135532000L) {
                l += 12622780800L;
            }
            return this.zoneId.getRules().getOffset(Instant.ofEpochSecond(l)).getTotalSeconds();
        }

        @Override
        public int getTimeZoneOffsetLocal(long l, long l2) {
            int n = (int)(l2 / 1000000000L);
            int n2 = n / 60;
            n -= n2 * 60;
            int n3 = n2 / 60;
            return ZonedDateTime.of(LocalDateTime.of(WithTimeZone.yearForCalendar(DateTimeUtils.yearFromDateValue(l)), DateTimeUtils.monthFromDateValue(l), DateTimeUtils.dayFromDateValue(l), n3, n2 -= n3 * 60, n), this.zoneId).getOffset().getTotalSeconds();
        }

        @Override
        public long getEpochSecondsFromLocal(long l, long l2) {
            int n = (int)(l2 / 1000000000L);
            int n2 = n / 60;
            n -= n2 * 60;
            int n3 = n2 / 60;
            int n4 = DateTimeUtils.yearFromDateValue(l);
            int n5 = WithTimeZone.yearForCalendar(n4);
            long l3 = ZonedDateTime.of(LocalDateTime.of(n5, DateTimeUtils.monthFromDateValue(l), DateTimeUtils.dayFromDateValue(l), n3, n2 -= n3 * 60, n), this.zoneId).toOffsetDateTime().toEpochSecond();
            return l3 + (long)(n4 - n5) * 31556952L;
        }

        @Override
        public String getId() {
            return this.zoneId.getId();
        }

        @Override
        public String getShortId(long l) {
            DateTimeFormatter dateTimeFormatter = TIME_ZONE_FORMATTER;
            if (dateTimeFormatter == null) {
                TIME_ZONE_FORMATTER = dateTimeFormatter = DateTimeFormatter.ofPattern("z", Locale.ENGLISH);
            }
            return ZonedDateTime.ofInstant(Instant.ofEpochSecond(l), this.zoneId).format(dateTimeFormatter);
        }

        private static int yearForCalendar(int n) {
            if (n > 999999999) {
                n -= 400;
            } else if (n < -999999999) {
                n += 400;
            }
            return n;
        }

        public String toString() {
            return "TimeZoneProvider " + this.zoneId.getId();
        }
    }

    private static final class Simple
    extends TimeZoneProvider {
        private final int offset;
        private volatile String id;

        Simple(int n) {
            this.offset = n;
        }

        public int hashCode() {
            return this.offset + 129607;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || object.getClass() != Simple.class) {
                return false;
            }
            return this.offset == ((Simple)object).offset;
        }

        @Override
        public int getTimeZoneOffsetUTC(long l) {
            return this.offset;
        }

        @Override
        public int getTimeZoneOffsetLocal(long l, long l2) {
            return this.offset;
        }

        @Override
        public long getEpochSecondsFromLocal(long l, long l2) {
            return DateTimeUtils.getEpochSeconds(l, l2, this.offset);
        }

        @Override
        public String getId() {
            String string = this.id;
            if (string == null) {
                this.id = string = DateTimeUtils.timeZoneNameFromOffsetSeconds(this.offset);
            }
            return string;
        }

        @Override
        public String getShortId(long l) {
            return this.getId();
        }

        @Override
        public boolean hasFixedOffset() {
            return true;
        }

        public String toString() {
            return "TimeZoneProvider " + this.getId();
        }
    }
}

