/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.WeekFields;
import java.util.Locale;
import org.h2.api.IntervalQualifier;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Mode;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function1_2;
import org.h2.message.DbException;
import org.h2.mvstore.db.Store;
import org.h2.util.DateTimeUtils;
import org.h2.util.IntervalUtils;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueDate;
import org.h2.value.ValueInteger;
import org.h2.value.ValueInterval;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;

public final class DateTimeFunction
extends Function1_2 {
    public static final int EXTRACT = 0;
    public static final int DATE_TRUNC = 1;
    public static final int DATEADD = 2;
    public static final int DATEDIFF = 3;
    public static final int LAST_DAY = 4;
    private static final String[] NAMES = new String[]{"EXTRACT", "DATE_TRUNC", "DATEADD", "DATEDIFF", "LAST_DAY"};
    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;
    public static final int HOUR = 3;
    public static final int MINUTE = 4;
    public static final int SECOND = 5;
    public static final int TIMEZONE_HOUR = 6;
    public static final int TIMEZONE_MINUTE = 7;
    public static final int TIMEZONE_SECOND = 8;
    public static final int MILLENNIUM = 9;
    public static final int CENTURY = 10;
    public static final int DECADE = 11;
    public static final int QUARTER = 12;
    public static final int MILLISECOND = 13;
    public static final int MICROSECOND = 14;
    public static final int NANOSECOND = 15;
    public static final int DAY_OF_YEAR = 16;
    public static final int ISO_DAY_OF_WEEK = 17;
    public static final int ISO_WEEK = 18;
    public static final int ISO_WEEK_YEAR = 19;
    public static final int DAY_OF_WEEK = 20;
    public static final int WEEK = 21;
    public static final int WEEK_YEAR = 22;
    public static final int EPOCH = 23;
    public static final int DOW = 24;
    private static final int FIELDS_COUNT = 25;
    private static final String[] FIELD_NAMES = new String[]{"YEAR", "MONTH", "DAY", "HOUR", "MINUTE", "SECOND", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TIMEZONE_SECOND", "MILLENNIUM", "CENTURY", "DECADE", "QUARTER", "MILLISECOND", "MICROSECOND", "NANOSECOND", "DAY_OF_YEAR", "ISO_DAY_OF_WEEK", "ISO_WEEK", "ISO_WEEK_YEAR", "DAY_OF_WEEK", "WEEK", "WEEK_YEAR", "EPOCH", "DOW"};
    private static final BigDecimal BD_SECONDS_PER_DAY = new BigDecimal(86400L);
    private static final BigInteger BI_SECONDS_PER_DAY = BigInteger.valueOf(86400L);
    private static final BigDecimal BD_NANOS_PER_SECOND = new BigDecimal(1000000000L);
    private static volatile WeekFields WEEK_FIELDS;
    private final int function;
    private final int field;

    public static int getField(String string) {
        switch (StringUtils.toUpperEnglish(string)) {
            case "YEAR": 
            case "YY": 
            case "YYYY": 
            case "SQL_TSI_YEAR": {
                return 0;
            }
            case "MONTH": 
            case "M": 
            case "MM": 
            case "SQL_TSI_MONTH": {
                return 1;
            }
            case "DAY": 
            case "D": 
            case "DD": 
            case "SQL_TSI_DAY": {
                return 2;
            }
            case "HOUR": 
            case "HH": 
            case "SQL_TSI_HOUR": {
                return 3;
            }
            case "MINUTE": 
            case "MI": 
            case "N": 
            case "SQL_TSI_MINUTE": {
                return 4;
            }
            case "SECOND": 
            case "S": 
            case "SS": 
            case "SQL_TSI_SECOND": {
                return 5;
            }
            case "TIMEZONE_HOUR": {
                return 6;
            }
            case "TIMEZONE_MINUTE": {
                return 7;
            }
            case "TIMEZONE_SECOND": {
                return 8;
            }
            case "MILLENNIUM": {
                return 9;
            }
            case "CENTURY": {
                return 10;
            }
            case "DECADE": {
                return 11;
            }
            case "QUARTER": {
                return 12;
            }
            case "MILLISECOND": 
            case "MILLISECONDS": 
            case "MS": {
                return 13;
            }
            case "MICROSECOND": 
            case "MICROSECONDS": 
            case "MCS": {
                return 14;
            }
            case "NANOSECOND": 
            case "NS": {
                return 15;
            }
            case "DAY_OF_YEAR": 
            case "DAYOFYEAR": 
            case "DY": 
            case "DOY": {
                return 16;
            }
            case "ISO_DAY_OF_WEEK": 
            case "ISODOW": {
                return 17;
            }
            case "ISO_WEEK": {
                return 18;
            }
            case "ISO_WEEK_YEAR": 
            case "ISO_YEAR": 
            case "ISOYEAR": {
                return 19;
            }
            case "DAY_OF_WEEK": 
            case "DAYOFWEEK": {
                return 20;
            }
            case "WEEK": 
            case "WK": 
            case "WW": 
            case "SQL_TSI_WEEK": {
                return 21;
            }
            case "WEEK_YEAR": {
                return 22;
            }
            case "EPOCH": {
                return 23;
            }
            case "DOW": {
                return 24;
            }
        }
        throw DbException.getInvalidValueException("date-time field", string);
    }

    public static String getFieldName(int n) {
        if (n < 0 || n >= 25) {
            throw DbException.getUnsupportedException("datetime field " + n);
        }
        return FIELD_NAMES[n];
    }

    public DateTimeFunction(int n, int n2, Expression expression, Expression expression2) {
        super(expression, expression2);
        this.function = n;
        this.field = n2;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        switch (this.function) {
            case 0: {
                value = this.field == 23 ? DateTimeFunction.extractEpoch(sessionLocal, value) : ValueInteger.get(DateTimeFunction.extractInteger(sessionLocal, value, this.field));
                break;
            }
            case 1: {
                value = DateTimeFunction.truncateDate(sessionLocal, this.field, value);
                break;
            }
            case 2: {
                value = DateTimeFunction.dateadd(sessionLocal, this.field, value.getLong(), value2);
                break;
            }
            case 3: {
                value = ValueBigint.get(DateTimeFunction.datediff(sessionLocal, this.field, value, value2));
                break;
            }
            case 4: {
                value = DateTimeFunction.lastDay(sessionLocal, value);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    private static int extractInteger(SessionLocal sessionLocal, Value value, int n) {
        return value instanceof ValueInterval ? DateTimeFunction.extractInterval(value, n) : DateTimeFunction.extractDateTime(sessionLocal, value, n);
    }

    private static int extractInterval(Value value, int n) {
        long l;
        ValueInterval valueInterval = (ValueInterval)value;
        IntervalQualifier intervalQualifier = valueInterval.getQualifier();
        boolean bl = valueInterval.isNegative();
        long l2 = valueInterval.getLeading();
        long l3 = valueInterval.getRemaining();
        switch (n) {
            case 0: {
                l = IntervalUtils.yearsFromInterval(intervalQualifier, bl, l2, l3);
                break;
            }
            case 1: {
                l = IntervalUtils.monthsFromInterval(intervalQualifier, bl, l2, l3);
                break;
            }
            case 2: 
            case 16: {
                l = IntervalUtils.daysFromInterval(intervalQualifier, bl, l2, l3);
                break;
            }
            case 3: {
                l = IntervalUtils.hoursFromInterval(intervalQualifier, bl, l2, l3);
                break;
            }
            case 4: {
                l = IntervalUtils.minutesFromInterval(intervalQualifier, bl, l2, l3);
                break;
            }
            case 5: {
                l = IntervalUtils.nanosFromInterval(intervalQualifier, bl, l2, l3) / 1000000000L;
                break;
            }
            case 13: {
                l = IntervalUtils.nanosFromInterval(intervalQualifier, bl, l2, l3) / 1000000L % 1000L;
                break;
            }
            case 14: {
                l = IntervalUtils.nanosFromInterval(intervalQualifier, bl, l2, l3) / 1000L % 1000000L;
                break;
            }
            case 15: {
                l = IntervalUtils.nanosFromInterval(intervalQualifier, bl, l2, l3) % 1000000000L;
                break;
            }
            default: {
                throw DbException.getUnsupportedException("getDatePart(" + value + ", " + n + ')');
            }
        }
        return (int)l;
    }

    static int extractDateTime(SessionLocal sessionLocal, Value value, int n) {
        long[] lArray = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
        long l = lArray[0];
        long l2 = lArray[1];
        switch (n) {
            case 0: {
                return DateTimeUtils.yearFromDateValue(l);
            }
            case 1: {
                return DateTimeUtils.monthFromDateValue(l);
            }
            case 2: {
                return DateTimeUtils.dayFromDateValue(l);
            }
            case 3: {
                return (int)(l2 / 3600000000000L % 24L);
            }
            case 4: {
                return (int)(l2 / 60000000000L % 60L);
            }
            case 5: {
                return (int)(l2 / 1000000000L % 60L);
            }
            case 13: {
                return (int)(l2 / 1000000L % 1000L);
            }
            case 14: {
                return (int)(l2 / 1000L % 1000000L);
            }
            case 15: {
                return (int)(l2 % 1000000000L);
            }
            case 9: {
                return DateTimeFunction.millennium(DateTimeUtils.yearFromDateValue(l));
            }
            case 10: {
                return DateTimeFunction.century(DateTimeUtils.yearFromDateValue(l));
            }
            case 11: {
                return DateTimeFunction.decade(DateTimeUtils.yearFromDateValue(l));
            }
            case 16: {
                return DateTimeUtils.getDayOfYear(l);
            }
            case 24: {
                if (sessionLocal.getMode().getEnum() == Mode.ModeEnum.PostgreSQL) {
                    return DateTimeUtils.getSundayDayOfWeek(l) - 1;
                }
            }
            case 20: {
                return DateTimeFunction.getLocalDayOfWeek(l);
            }
            case 21: {
                return DateTimeFunction.getLocalWeekOfYear(l);
            }
            case 22: {
                WeekFields weekFields = DateTimeFunction.getWeekFields();
                return DateTimeUtils.getWeekYear(l, weekFields.getFirstDayOfWeek().getValue(), weekFields.getMinimalDaysInFirstWeek());
            }
            case 12: {
                return (DateTimeUtils.monthFromDateValue(l) - 1) / 3 + 1;
            }
            case 19: {
                return DateTimeUtils.getIsoWeekYear(l);
            }
            case 18: {
                return DateTimeUtils.getIsoWeekOfYear(l);
            }
            case 17: {
                return DateTimeUtils.getIsoDayOfWeek(l);
            }
            case 6: 
            case 7: 
            case 8: {
                int n2 = value instanceof ValueTimestampTimeZone ? ((ValueTimestampTimeZone)value).getTimeZoneOffsetSeconds() : (value instanceof ValueTimeTimeZone ? ((ValueTimeTimeZone)value).getTimeZoneOffsetSeconds() : sessionLocal.currentTimeZone().getTimeZoneOffsetLocal(l, l2));
                if (n == 6) {
                    return n2 / 3600;
                }
                if (n == 7) {
                    return n2 % 3600 / 60;
                }
                return n2 % 60;
            }
        }
        throw DbException.getUnsupportedException("EXTRACT(" + DateTimeFunction.getFieldName(n) + " FROM " + value + ')');
    }

    private static Value truncateDate(SessionLocal sessionLocal, int n, Value value) {
        Object object;
        long[] lArray = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
        long l = lArray[0];
        long l2 = lArray[1];
        switch (n) {
            case 14: {
                l2 = l2 / 1000L * 1000L;
                break;
            }
            case 13: {
                l2 = l2 / 1000000L * 1000000L;
                break;
            }
            case 5: {
                l2 = l2 / 1000000000L * 1000000000L;
                break;
            }
            case 4: {
                l2 = l2 / 60000000000L * 60000000000L;
                break;
            }
            case 3: {
                l2 = l2 / 3600000000000L * 3600000000000L;
                break;
            }
            case 2: {
                l2 = 0L;
                break;
            }
            case 18: {
                l = DateTimeFunction.truncateToWeek(l, 1);
                l2 = 0L;
                break;
            }
            case 21: {
                l = DateTimeFunction.truncateToWeek(l, DateTimeFunction.getWeekFields().getFirstDayOfWeek().getValue());
                l2 = 0L;
                break;
            }
            case 19: {
                l = DateTimeFunction.truncateToWeekYear(l, 1, 4);
                l2 = 0L;
                break;
            }
            case 22: {
                object = DateTimeFunction.getWeekFields();
                l = DateTimeFunction.truncateToWeekYear(l, ((WeekFields)object).getFirstDayOfWeek().getValue(), ((WeekFields)object).getMinimalDaysInFirstWeek());
                break;
            }
            case 1: {
                l = l & 0xFFFFFFFFFFFFFFE0L | 1L;
                l2 = 0L;
                break;
            }
            case 12: {
                l = DateTimeUtils.dateValue(DateTimeUtils.yearFromDateValue(l), (DateTimeUtils.monthFromDateValue(l) - 1) / 3 * 3 + 1, 1);
                l2 = 0L;
                break;
            }
            case 0: {
                l = l & 0xFFFFFFFFFFFFFE00L | 0x21L;
                l2 = 0L;
                break;
            }
            case 11: {
                int n2 = DateTimeUtils.yearFromDateValue(l);
                n2 = n2 >= 0 ? n2 / 10 * 10 : (n2 - 9) / 10 * 10;
                l = DateTimeUtils.dateValue(n2, 1, 1);
                l2 = 0L;
                break;
            }
            case 10: {
                int n3 = DateTimeUtils.yearFromDateValue(l);
                n3 = n3 > 0 ? (n3 - 1) / 100 * 100 + 1 : n3 / 100 * 100 - 99;
                l = DateTimeUtils.dateValue(n3, 1, 1);
                l2 = 0L;
                break;
            }
            case 9: {
                int n4 = DateTimeUtils.yearFromDateValue(l);
                n4 = n4 > 0 ? (n4 - 1) / 1000 * 1000 + 1 : n4 / 1000 * 1000 - 999;
                l = DateTimeUtils.dateValue(n4, 1, 1);
                l2 = 0L;
                break;
            }
            default: {
                throw DbException.getUnsupportedException("DATE_TRUNC " + DateTimeFunction.getFieldName(n));
            }
        }
        object = DateTimeUtils.dateTimeToValue(value, l, l2);
        if (sessionLocal.getMode().getEnum() == Mode.ModeEnum.PostgreSQL && ((Value)object).getValueType() == 17) {
            object = ((Value)object).convertTo(21, (CastDataProvider)sessionLocal);
        }
        return object;
    }

    private static long truncateToWeek(long l, int n) {
        long l2 = DateTimeUtils.absoluteDayFromDateValue(l);
        int n2 = DateTimeUtils.getDayOfWeekFromAbsolute(l2, n);
        if (n2 != 1) {
            l = DateTimeUtils.dateValueFromAbsoluteDay(l2 - (long)n2 + 1L);
        }
        return l;
    }

    private static long truncateToWeekYear(long l, int n, int n2) {
        long l2;
        int n3;
        long l3;
        long l4 = DateTimeUtils.absoluteDayFromDateValue(l);
        if (l4 < (l3 = DateTimeUtils.getWeekYearAbsoluteStart(n3 = DateTimeUtils.yearFromDateValue(l), n, n2))) {
            l3 = DateTimeUtils.getWeekYearAbsoluteStart(n3 - 1, n, n2);
        } else if (DateTimeUtils.monthFromDateValue(l) == 12 && 24 + n2 < DateTimeUtils.dayFromDateValue(l) && l4 >= (l2 = DateTimeUtils.getWeekYearAbsoluteStart(n3 + 1, n, n2))) {
            l3 = l2;
        }
        return DateTimeUtils.dateValueFromAbsoluteDay(l3);
    }

    public static Value dateadd(SessionLocal sessionLocal, int n, long l, Value value) {
        if (n != 13 && n != 14 && n != 15 && (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE)) {
            throw DbException.getInvalidValueException("DATEADD count", l);
        }
        long[] lArray = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
        long l2 = lArray[0];
        long l3 = lArray[1];
        int n2 = value.getValueType();
        switch (n) {
            case 9: {
                return DateTimeFunction.addYearsMonths(n, true, l * 1000L, value, n2, l2, l3);
            }
            case 10: {
                return DateTimeFunction.addYearsMonths(n, true, l * 100L, value, n2, l2, l3);
            }
            case 11: {
                return DateTimeFunction.addYearsMonths(n, true, l * 10L, value, n2, l2, l3);
            }
            case 0: {
                return DateTimeFunction.addYearsMonths(n, true, l, value, n2, l2, l3);
            }
            case 12: {
                return DateTimeFunction.addYearsMonths(n, false, l *= 3L, value, n2, l2, l3);
            }
            case 1: {
                return DateTimeFunction.addYearsMonths(n, false, l, value, n2, l2, l3);
            }
            case 18: 
            case 21: {
                l *= 7L;
            }
            case 2: 
            case 16: 
            case 17: 
            case 20: 
            case 24: {
                if (n2 == 18 || n2 == 19) {
                    throw DbException.getInvalidValueException("DATEADD time part", DateTimeFunction.getFieldName(n));
                }
                l2 = DateTimeUtils.dateValueFromAbsoluteDay(DateTimeUtils.absoluteDayFromDateValue(l2) + l);
                return DateTimeUtils.dateTimeToValue(value, l2, l3);
            }
            case 3: {
                l *= 3600000000000L;
                break;
            }
            case 4: {
                l *= 60000000000L;
                break;
            }
            case 5: 
            case 23: {
                l *= 1000000000L;
                break;
            }
            case 13: {
                l *= 1000000L;
                break;
            }
            case 14: {
                l *= 1000L;
                break;
            }
            case 15: {
                break;
            }
            case 6: {
                return DateTimeFunction.addToTimeZone(n, l * 3600L, value, n2, l2, l3);
            }
            case 7: {
                return DateTimeFunction.addToTimeZone(n, l * 60L, value, n2, l2, l3);
            }
            case 8: {
                return DateTimeFunction.addToTimeZone(n, l, value, n2, l2, l3);
            }
            default: {
                throw DbException.getUnsupportedException("DATEADD " + DateTimeFunction.getFieldName(n));
            }
        }
        if ((l3 += l) >= 86400000000000L || l3 < 0L) {
            long l4 = l3 >= 86400000000000L ? l3 / 86400000000000L : (l3 - 86400000000000L + 1L) / 86400000000000L;
            l2 = DateTimeUtils.dateValueFromAbsoluteDay(DateTimeUtils.absoluteDayFromDateValue(l2) + l4);
            l3 -= l4 * 86400000000000L;
        }
        if (n2 == 17) {
            return ValueTimestamp.fromDateValueAndNanos(l2, l3);
        }
        return DateTimeUtils.dateTimeToValue(value, l2, l3);
    }

    private static Value addYearsMonths(int n, boolean bl, long l, Value value, int n2, long l2, long l3) {
        if (n2 == 18 || n2 == 19) {
            throw DbException.getInvalidValueException("DATEADD time part", DateTimeFunction.getFieldName(n));
        }
        long l4 = DateTimeUtils.yearFromDateValue(l2);
        long l5 = DateTimeUtils.monthFromDateValue(l2);
        if (bl) {
            l4 += l;
        } else {
            l5 += l;
        }
        return DateTimeUtils.dateTimeToValue(value, DateTimeUtils.dateValueFromDenormalizedDate(l4, l5, DateTimeUtils.dayFromDateValue(l2)), l3);
    }

    private static Value addToTimeZone(int n, long l, Value value, int n2, long l2, long l3) {
        if (n2 == 21) {
            return ValueTimestampTimeZone.fromDateValueAndNanos(l2, l3, MathUtils.convertLongToInt(l + (long)((ValueTimestampTimeZone)value).getTimeZoneOffsetSeconds()));
        }
        if (n2 == 19) {
            return ValueTimeTimeZone.fromNanos(l3, MathUtils.convertLongToInt(l + (long)((ValueTimeTimeZone)value).getTimeZoneOffsetSeconds()));
        }
        throw DbException.getUnsupportedException("DATEADD " + DateTimeFunction.getFieldName(n));
    }

    private static long datediff(SessionLocal sessionLocal, int n, Value value, Value value2) {
        long[] lArray = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
        long l = lArray[0];
        long l2 = DateTimeUtils.absoluteDayFromDateValue(l);
        long[] lArray2 = DateTimeUtils.dateAndTimeFromValue(value2, sessionLocal);
        long l3 = lArray2[0];
        long l4 = DateTimeUtils.absoluteDayFromDateValue(l3);
        switch (n) {
            case 3: 
            case 4: 
            case 5: 
            case 13: 
            case 14: 
            case 15: 
            case 23: {
                long l5 = lArray[1];
                long l6 = lArray2[1];
                switch (n) {
                    case 15: {
                        return (l4 - l2) * 86400000000000L + (l6 - l5);
                    }
                    case 14: {
                        return (l4 - l2) * 86400000000L + (l6 / 1000L - l5 / 1000L);
                    }
                    case 13: {
                        return (l4 - l2) * 86400000L + (l6 / 1000000L - l5 / 1000000L);
                    }
                    case 5: 
                    case 23: {
                        return (l4 - l2) * 86400L + (l6 / 1000000000L - l5 / 1000000000L);
                    }
                    case 4: {
                        return (l4 - l2) * 1440L + (l6 / 60000000000L - l5 / 60000000000L);
                    }
                    case 3: {
                        return (l4 - l2) * 24L + (l6 / 3600000000000L - l5 / 3600000000000L);
                    }
                }
            }
            case 2: 
            case 16: 
            case 17: 
            case 20: 
            case 24: {
                return l4 - l2;
            }
            case 21: {
                return DateTimeFunction.weekdiff(l2, l4, DateTimeFunction.getWeekFields().getFirstDayOfWeek().getValue());
            }
            case 18: {
                return DateTimeFunction.weekdiff(l2, l4, 1);
            }
            case 1: {
                return (DateTimeUtils.yearFromDateValue(l3) - DateTimeUtils.yearFromDateValue(l)) * 12 + DateTimeUtils.monthFromDateValue(l3) - DateTimeUtils.monthFromDateValue(l);
            }
            case 12: {
                return (DateTimeUtils.yearFromDateValue(l3) - DateTimeUtils.yearFromDateValue(l)) * 4 + (DateTimeUtils.monthFromDateValue(l3) - 1) / 3 - (DateTimeUtils.monthFromDateValue(l) - 1) / 3;
            }
            case 9: {
                return DateTimeFunction.millennium(DateTimeUtils.yearFromDateValue(l3)) - DateTimeFunction.millennium(DateTimeUtils.yearFromDateValue(l));
            }
            case 10: {
                return DateTimeFunction.century(DateTimeUtils.yearFromDateValue(l3)) - DateTimeFunction.century(DateTimeUtils.yearFromDateValue(l));
            }
            case 11: {
                return DateTimeFunction.decade(DateTimeUtils.yearFromDateValue(l3)) - DateTimeFunction.decade(DateTimeUtils.yearFromDateValue(l));
            }
            case 0: {
                return DateTimeUtils.yearFromDateValue(l3) - DateTimeUtils.yearFromDateValue(l);
            }
            case 6: 
            case 7: 
            case 8: {
                int n2 = value instanceof ValueTimestampTimeZone ? ((ValueTimestampTimeZone)value).getTimeZoneOffsetSeconds() : (value instanceof ValueTimeTimeZone ? ((ValueTimeTimeZone)value).getTimeZoneOffsetSeconds() : sessionLocal.currentTimeZone().getTimeZoneOffsetLocal(l, lArray[1]));
                int n3 = value2 instanceof ValueTimestampTimeZone ? ((ValueTimestampTimeZone)value2).getTimeZoneOffsetSeconds() : (value2 instanceof ValueTimeTimeZone ? ((ValueTimeTimeZone)value2).getTimeZoneOffsetSeconds() : sessionLocal.currentTimeZone().getTimeZoneOffsetLocal(l3, lArray2[1]));
                if (n == 6) {
                    return n3 / 3600 - n2 / 3600;
                }
                if (n == 7) {
                    return n3 / 60 - n2 / 60;
                }
                return n3 - n2;
            }
        }
        throw DbException.getUnsupportedException("DATEDIFF " + DateTimeFunction.getFieldName(n));
    }

    private static long weekdiff(long l, long l2, int n) {
        long l3 = (l += (long)(4 - n)) / 7L;
        if (l < 0L && l3 * 7L != l) {
            --l3;
        }
        long l4 = (l2 += (long)(4 - n)) / 7L;
        if (l2 < 0L && l4 * 7L != l2) {
            --l4;
        }
        return l4 - l3;
    }

    private static int millennium(int n) {
        return n > 0 ? (n + 999) / 1000 : n / 1000;
    }

    private static int century(int n) {
        return n > 0 ? (n + 99) / 100 : n / 100;
    }

    private static int decade(int n) {
        return n >= 0 ? n / 10 : (n - 9) / 10;
    }

    private static int getLocalDayOfWeek(long l) {
        return DateTimeUtils.getDayOfWeek(l, DateTimeFunction.getWeekFields().getFirstDayOfWeek().getValue());
    }

    private static int getLocalWeekOfYear(long l) {
        WeekFields weekFields = DateTimeFunction.getWeekFields();
        return DateTimeUtils.getWeekOfYear(l, weekFields.getFirstDayOfWeek().getValue(), weekFields.getMinimalDaysInFirstWeek());
    }

    private static WeekFields getWeekFields() {
        WeekFields weekFields = WEEK_FIELDS;
        if (weekFields == null) {
            WEEK_FIELDS = weekFields = WeekFields.of(Locale.getDefault());
        }
        return weekFields;
    }

    private static ValueNumeric extractEpoch(SessionLocal sessionLocal, Value value) {
        ValueNumeric valueNumeric;
        if (value instanceof ValueInterval) {
            ValueInterval valueInterval = (ValueInterval)value;
            if (valueInterval.getQualifier().isYearMonth()) {
                valueInterval = (ValueInterval)valueInterval.convertTo(TypeInfo.TYPE_INTERVAL_YEAR_TO_MONTH);
                long l = valueInterval.getLeading();
                long l2 = valueInterval.getRemaining();
                BigInteger bigInteger = BigInteger.valueOf(l).multiply(BigInteger.valueOf(31557600L)).add(BigInteger.valueOf(l2 * 2592000L));
                if (valueInterval.isNegative()) {
                    bigInteger = bigInteger.negate();
                }
                return ValueNumeric.get(bigInteger);
            }
            return ValueNumeric.get(new BigDecimal(IntervalUtils.intervalToAbsolute(valueInterval)).divide(BD_NANOS_PER_SECOND));
        }
        long[] lArray = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
        long l = lArray[0];
        long l3 = lArray[1];
        if (value instanceof ValueTime) {
            valueNumeric = ValueNumeric.get(BigDecimal.valueOf(l3).divide(BD_NANOS_PER_SECOND));
        } else if (value instanceof ValueDate) {
            valueNumeric = ValueNumeric.get(BigInteger.valueOf(DateTimeUtils.absoluteDayFromDateValue(l)).multiply(BI_SECONDS_PER_DAY));
        } else {
            BigDecimal bigDecimal = BigDecimal.valueOf(l3).divide(BD_NANOS_PER_SECOND).add(BigDecimal.valueOf(DateTimeUtils.absoluteDayFromDateValue(l)).multiply(BD_SECONDS_PER_DAY));
            valueNumeric = value instanceof ValueTimestampTimeZone ? ValueNumeric.get(bigDecimal.subtract(BigDecimal.valueOf(((ValueTimestampTimeZone)value).getTimeZoneOffsetSeconds()))) : (value instanceof ValueTimeTimeZone ? ValueNumeric.get(bigDecimal.subtract(BigDecimal.valueOf(((ValueTimeTimeZone)value).getTimeZoneOffsetSeconds()))) : ValueNumeric.get(bigDecimal));
        }
        return valueNumeric;
    }

    private static Value lastDay(SessionLocal sessionLocal, Value value) {
        long l;
        int n = value.getValueType();
        switch (n) {
            case 17: {
                l = ((ValueDate)value).getDateValue();
                break;
            }
            case 20: {
                l = ((ValueTimestamp)value).getDateValue();
                break;
            }
            case 21: {
                l = ((ValueTimestampTimeZone)value).getDateValue();
                break;
            }
            default: {
                l = ((ValueTimestampTimeZone)DateTimeUtils.parseTimestamp(value.getString(), sessionLocal, true)).getDateValue();
            }
        }
        int n2 = DateTimeUtils.yearFromDateValue(l);
        int n3 = DateTimeUtils.monthFromDateValue(l);
        int n4 = DateTimeUtils.getDaysInMonth(n2, n3);
        long l2 = DateTimeUtils.dateValue(n2, n3, n4);
        if (l2 == l && n == 17) {
            return value;
        }
        return ValueDate.fromDateValue(l2);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        switch (this.function) {
            case 0: {
                this.type = this.field == 23 ? TypeInfo.getTypeInfo(13, 28L, 9, null) : TypeInfo.TYPE_INTEGER;
                break;
            }
            case 1: {
                this.type = this.left.getType();
                int n = this.type.getValueType();
                if (!DataType.isDateTimeType(n)) {
                    throw Store.getInvalidExpressionTypeException("DATE_TRUNC datetime argument", this.left);
                }
                if (sessionLocal.getMode().getEnum() != Mode.ModeEnum.PostgreSQL || n != 17) break;
                this.type = TypeInfo.TYPE_TIMESTAMP_TZ;
                break;
            }
            case 2: {
                int n = this.right.getType().getValueType();
                if (n == 17) {
                    switch (this.field) {
                        case 3: 
                        case 4: 
                        case 5: 
                        case 13: 
                        case 14: 
                        case 15: 
                        case 23: {
                            n = 20;
                        }
                    }
                }
                this.type = TypeInfo.getTypeInfo(n);
                break;
            }
            case 3: {
                this.type = TypeInfo.TYPE_BIGINT;
                break;
            }
            case 4: {
                this.type = TypeInfo.TYPE_DATE;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        if (this.left.isConstant() && (this.right == null || this.right.isConstant())) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.getName()).append('(');
        if (this.function == 4) {
            this.left.getUnenclosedSQL(stringBuilder, n);
        } else {
            stringBuilder.append(DateTimeFunction.getFieldName(this.field));
            switch (this.function) {
                case 0: {
                    this.left.getUnenclosedSQL(stringBuilder.append(" FROM "), n);
                    break;
                }
                case 1: {
                    this.left.getUnenclosedSQL(stringBuilder.append(", "), n);
                    break;
                }
                case 2: 
                case 3: {
                    this.left.getUnenclosedSQL(stringBuilder.append(", "), n).append(", ");
                    this.right.getUnenclosedSQL(stringBuilder, n);
                    break;
                }
                default: {
                    throw DbException.getInternalError("function=" + this.function);
                }
            }
        }
        return stringBuilder.append(')');
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

