/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.zone.ZoneRules;
import java.util.Locale;
import java.util.Objects;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.util.JSR310Utils;
import org.h2.util.SmallLRUCache;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueVarchar;

public final class DateTimeFormatFunction
extends FunctionN {
    public static final int FORMATDATETIME = 0;
    public static final int PARSEDATETIME = 1;
    private static final String[] NAMES = new String[]{"FORMATDATETIME", "PARSEDATETIME"};
    private static final SmallLRUCache<CacheKey, CacheValue> CACHE = SmallLRUCache.newInstance(100);
    private final int function;

    public DateTimeFormatFunction(int n) {
        super(new Expression[4]);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        String string;
        String string2;
        String string3 = value2.getString();
        if (value3 != null) {
            string2 = value3.getString();
            string = this.args.length > 3 ? this.args[3].getValue(sessionLocal).getString() : null;
        } else {
            string2 = null;
            string = null;
        }
        switch (this.function) {
            case 0: {
                value = ValueVarchar.get(DateTimeFormatFunction.formatDateTime(sessionLocal, value, string3, string2, string));
                break;
            }
            case 1: {
                value = DateTimeFormatFunction.parseDateTime(sessionLocal, value.getString(), string3, string2, string);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    public static String formatDateTime(SessionLocal sessionLocal, Value value, String string, String string2, String string3) {
        Comparable<LocalTime> comparable;
        CacheValue cacheValue = DateTimeFormatFunction.getDateFormat(string, string2, string3);
        ZoneId zoneId = cacheValue.zoneId;
        switch (value.getValueType()) {
            case 17: 
            case 20: {
                comparable = JSR310Utils.valueToLocalDateTime(value, sessionLocal).atZone(zoneId != null ? zoneId : ZoneId.of(sessionLocal.currentTimeZone().getId()));
                break;
            }
            case 18: {
                Comparable<LocalTime> comparable2 = JSR310Utils.valueToLocalTime(value, sessionLocal);
                comparable = zoneId != null ? ((LocalTime)comparable2).atOffset(DateTimeFormatFunction.getTimeOffset(zoneId, string3)) : comparable2;
                break;
            }
            case 19: {
                Comparable<LocalTime> comparable2 = JSR310Utils.valueToOffsetTime(value, sessionLocal);
                comparable = zoneId != null ? ((OffsetTime)comparable2).withOffsetSameInstant(DateTimeFormatFunction.getTimeOffset(zoneId, string3)) : comparable2;
                break;
            }
            case 21: {
                ZoneOffset zoneOffset;
                Comparable<LocalTime> comparable2 = JSR310Utils.valueToOffsetDateTime(value, sessionLocal);
                ZoneId zoneId2 = zoneId != null ? zoneId : ZoneId.ofOffset((zoneOffset = ((OffsetDateTime)comparable2).getOffset()).getTotalSeconds() == 0 ? "UTC" : "GMT", zoneOffset);
                comparable = ((OffsetDateTime)comparable2).atZoneSameInstant(zoneId2);
                break;
            }
            default: {
                throw DbException.getInvalidValueException("dateTime", value.getTraceSQL());
            }
        }
        try {
            return cacheValue.formatter.format((TemporalAccessor)((Object)comparable));
        }
        catch (DateTimeException dateTimeException) {
            throw DbException.getInvalidValueException(dateTimeException, "format", string);
        }
    }

    private static ZoneOffset getTimeOffset(ZoneId zoneId, String string) {
        if (zoneId instanceof ZoneOffset) {
            return (ZoneOffset)zoneId;
        }
        ZoneRules zoneRules = zoneId.getRules();
        if (!zoneRules.isFixedOffset()) {
            throw DbException.getInvalidValueException("timeZone", string);
        }
        return zoneRules.getOffset(Instant.EPOCH);
    }

    public static ValueTimestampTimeZone parseDateTime(SessionLocal sessionLocal, String string, String string2, String string3, String string4) {
        CacheValue cacheValue = DateTimeFormatFunction.getDateFormat(string2, string3, string4);
        try {
            ValueTimestampTimeZone valueTimestampTimeZone;
            TemporalAccessor temporalAccessor = cacheValue.formatter.parse(string);
            ZoneId zoneId = temporalAccessor.query(TemporalQueries.zoneId());
            if (temporalAccessor.isSupported(ChronoField.OFFSET_SECONDS)) {
                valueTimestampTimeZone = JSR310Utils.offsetDateTimeToValue(OffsetDateTime.from(temporalAccessor));
            } else if (temporalAccessor.isSupported(ChronoField.INSTANT_SECONDS)) {
                Instant instant = Instant.from(temporalAccessor);
                if (zoneId == null) {
                    zoneId = cacheValue.zoneId;
                }
                valueTimestampTimeZone = zoneId != null ? JSR310Utils.zonedDateTimeToValue(instant.atZone(zoneId)) : JSR310Utils.offsetDateTimeToValue(instant.atOffset(ZoneOffset.ofTotalSeconds(sessionLocal.currentTimeZone().getTimeZoneOffsetUTC(instant.getEpochSecond()))));
            } else {
                LocalDate localDate = temporalAccessor.query(TemporalQueries.localDate());
                LocalTime localTime = temporalAccessor.query(TemporalQueries.localTime());
                if (zoneId == null) {
                    zoneId = cacheValue.zoneId;
                }
                if (localDate != null) {
                    LocalDateTime localDateTime = localTime != null ? LocalDateTime.of(localDate, localTime) : localDate.atStartOfDay();
                    valueTimestampTimeZone = zoneId != null ? JSR310Utils.zonedDateTimeToValue(localDateTime.atZone(zoneId)) : (ValueTimestampTimeZone)JSR310Utils.localDateTimeToValue(localDateTime).convertTo(21, (CastDataProvider)sessionLocal);
                } else {
                    valueTimestampTimeZone = zoneId != null ? JSR310Utils.zonedDateTimeToValue(JSR310Utils.valueToInstant(sessionLocal.currentTimestamp(), sessionLocal).atZone(zoneId).with(localTime)) : (ValueTimestampTimeZone)ValueTime.fromNanos(localTime.toNanoOfDay()).convertTo(21, (CastDataProvider)sessionLocal);
                }
            }
            return valueTimestampTimeZone;
        }
        catch (RuntimeException runtimeException) {
            throw DbException.get(90014, runtimeException, string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static CacheValue getDateFormat(String string, String string2, String string3) {
        Exception exception = null;
        if (string.length() <= 100) {
            try {
                CacheValue cacheValue;
                CacheKey cacheKey = new CacheKey(string, string2, string3);
                SmallLRUCache<CacheKey, CacheValue> smallLRUCache = CACHE;
                synchronized (smallLRUCache) {
                    cacheValue = (CacheValue)CACHE.get(cacheKey);
                    if (cacheValue == null) {
                        ZoneId zoneId;
                        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(string).toFormatter(string2 == null ? Locale.getDefault(Locale.Category.FORMAT) : new Locale(string2));
                        if (string3 != null) {
                            zoneId = DateTimeFormatFunction.getZoneId(string3);
                            dateTimeFormatter = dateTimeFormatter.withZone(zoneId);
                        } else {
                            zoneId = null;
                        }
                        cacheValue = new CacheValue(dateTimeFormatter, zoneId);
                        CACHE.put(cacheKey, cacheValue);
                    }
                }
                return cacheValue;
            }
            catch (Exception exception2) {
                exception = exception2;
            }
        }
        throw DbException.get(90014, exception, string + '/' + string2);
    }

    private static ZoneId getZoneId(String string) {
        try {
            return ZoneId.of(string, ZoneId.SHORT_IDS);
        }
        catch (RuntimeException runtimeException) {
            throw DbException.getInvalidValueException("TIME ZONE", string);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal, true);
        switch (this.function) {
            case 0: {
                this.type = TypeInfo.TYPE_VARCHAR;
                break;
            }
            case 1: {
                this.type = TypeInfo.TYPE_TIMESTAMP_TZ;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }

    private static final class CacheValue {
        final DateTimeFormatter formatter;
        final ZoneId zoneId;

        CacheValue(DateTimeFormatter dateTimeFormatter, ZoneId zoneId) {
            this.formatter = dateTimeFormatter;
            this.zoneId = zoneId;
        }
    }

    private static final class CacheKey {
        private final String format;
        private final String locale;
        private final String timeZone;

        CacheKey(String string, String string2, String string3) {
            this.format = string;
            this.locale = string2;
            this.timeZone = string3;
        }

        public int hashCode() {
            int n = 1;
            n = 31 * n + this.format.hashCode();
            n = 31 * n + (this.locale == null ? 0 : this.locale.hashCode());
            n = 31 * n + (this.timeZone == null ? 0 : this.timeZone.hashCode());
            return n;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof CacheKey)) {
                return false;
            }
            CacheKey cacheKey = (CacheKey)object;
            return this.format.equals(cacheKey.format) && Objects.equals(this.locale, cacheKey.locale) && Objects.equals(this.timeZone, cacheKey.timeZone);
        }
    }
}

