/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import java.util.List;
import org.h2.engine.SessionLocal;
import org.h2.mode.ToDateTokenizer;
import org.h2.util.DateTimeUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;

public final class ToDateParser {
    private final SessionLocal session;
    private final String unmodifiedInputStr;
    private final String unmodifiedFormatStr;
    private final ConfigParam functionName;
    private String inputStr;
    private String formatStr;
    private boolean doyValid = false;
    private boolean absoluteDayValid = false;
    private boolean hour12Valid = false;
    private boolean timeZoneHMValid = false;
    private boolean bc;
    private long absoluteDay;
    private int year;
    private int month;
    private int day = 1;
    private int dayOfYear;
    private int hour;
    private int minute;
    private int second;
    private int nanos;
    private int hour12;
    private boolean isAM = true;
    private TimeZoneProvider timeZone;
    private int timeZoneHour;
    private int timeZoneMinute;
    private int currentYear;
    private int currentMonth;

    private ToDateParser(SessionLocal sessionLocal, ConfigParam configParam, String string, String string2) {
        this.session = sessionLocal;
        this.functionName = configParam;
        this.unmodifiedInputStr = this.inputStr = string.trim();
        this.formatStr = string2 == null || string2.isEmpty() ? configParam.getDefaultFormatStr() : string2.trim();
        this.unmodifiedFormatStr = this.formatStr;
    }

    private static ToDateParser getTimestampParser(SessionLocal sessionLocal, ConfigParam configParam, String string, String string2) {
        ToDateParser toDateParser = new ToDateParser(sessionLocal, configParam, string, string2);
        ToDateParser.parse(toDateParser);
        return toDateParser;
    }

    private ValueTimestamp getResultingValue() {
        int n;
        long l;
        if (this.absoluteDayValid) {
            l = DateTimeUtils.dateValueFromAbsoluteDay(this.absoluteDay);
        } else {
            n = this.year;
            if (n == 0) {
                n = this.getCurrentYear();
            }
            if (this.bc) {
                n = 1 - n;
            }
            if (this.doyValid) {
                l = DateTimeUtils.dateValueFromAbsoluteDay(DateTimeUtils.absoluteDayFromYear(n) + (long)this.dayOfYear - 1L);
            } else {
                int n2 = this.month;
                if (n2 == 0) {
                    n2 = this.getCurrentMonth();
                }
                l = DateTimeUtils.dateValue(n, n2, this.day);
            }
        }
        if (this.hour12Valid) {
            n = this.hour12 % 12;
            if (!this.isAM) {
                n += 12;
            }
        } else {
            n = this.hour;
        }
        long l2 = (long)((n * 60 + this.minute) * 60 + this.second) * 1000000000L + (long)this.nanos;
        return ValueTimestamp.fromDateValueAndNanos(l, l2);
    }

    private ValueTimestampTimeZone getResultingValueWithTimeZone() {
        ValueTimestamp valueTimestamp = this.getResultingValue();
        long l = valueTimestamp.getDateValue();
        long l2 = valueTimestamp.getTimeNanos();
        int n = this.timeZoneHMValid ? (this.timeZoneHour * 60 + (this.timeZoneHour >= 0 ? this.timeZoneMinute : -this.timeZoneMinute)) * 60 : (this.timeZone != null ? this.timeZone : this.session.currentTimeZone()).getTimeZoneOffsetLocal(l, l2);
        return ValueTimestampTimeZone.fromDateValueAndNanos(l, valueTimestamp.getTimeNanos(), n);
    }

    String getInputStr() {
        return this.inputStr;
    }

    String getFormatStr() {
        return this.formatStr;
    }

    String getFunctionName() {
        return this.functionName.name();
    }

    private void queryCurrentYearAndMonth() {
        long l = this.session.currentTimestamp().getDateValue();
        this.currentYear = DateTimeUtils.yearFromDateValue(l);
        this.currentMonth = DateTimeUtils.monthFromDateValue(l);
    }

    int getCurrentYear() {
        if (this.currentYear == 0) {
            this.queryCurrentYearAndMonth();
        }
        return this.currentYear;
    }

    int getCurrentMonth() {
        if (this.currentMonth == 0) {
            this.queryCurrentYearAndMonth();
        }
        return this.currentMonth;
    }

    void setAbsoluteDay(int n) {
        this.doyValid = false;
        this.absoluteDayValid = true;
        this.absoluteDay = n;
    }

    void setBC(boolean bl) {
        this.doyValid = false;
        this.absoluteDayValid = false;
        this.bc = bl;
    }

    void setYear(int n) {
        this.doyValid = false;
        this.absoluteDayValid = false;
        this.year = n;
    }

    void setMonth(int n) {
        this.doyValid = false;
        this.absoluteDayValid = false;
        this.month = n;
        if (this.year == 0) {
            this.year = 1970;
        }
    }

    void setDay(int n) {
        this.doyValid = false;
        this.absoluteDayValid = false;
        this.day = n;
        if (this.year == 0) {
            this.year = 1970;
        }
    }

    void setDayOfYear(int n) {
        this.doyValid = true;
        this.absoluteDayValid = false;
        this.dayOfYear = n;
    }

    void setHour(int n) {
        this.hour12Valid = false;
        this.hour = n;
    }

    void setMinute(int n) {
        this.minute = n;
    }

    void setSecond(int n) {
        this.second = n;
    }

    void setNanos(int n) {
        this.nanos = n;
    }

    void setAmPm(boolean bl) {
        this.hour12Valid = true;
        this.isAM = bl;
    }

    void setHour12(int n) {
        this.hour12Valid = true;
        this.hour12 = n;
    }

    void setTimeZone(TimeZoneProvider timeZoneProvider) {
        this.timeZoneHMValid = false;
        this.timeZone = timeZoneProvider;
    }

    void setTimeZoneHour(int n) {
        this.timeZoneHMValid = true;
        this.timeZoneHour = n;
    }

    void setTimeZoneMinute(int n) {
        this.timeZoneHMValid = true;
        this.timeZoneMinute = n;
    }

    private boolean hasToParseData() {
        return !this.formatStr.isEmpty();
    }

    private void removeFirstChar() {
        if (!this.formatStr.isEmpty()) {
            this.formatStr = this.formatStr.substring(1);
        }
        if (!this.inputStr.isEmpty()) {
            this.inputStr = this.inputStr.substring(1);
        }
    }

    private static ToDateParser parse(ToDateParser toDateParser) {
        while (toDateParser.hasToParseData()) {
            List<ToDateTokenizer.FormatTokenEnum> list = ToDateTokenizer.FormatTokenEnum.getTokensInQuestion(toDateParser.getFormatStr());
            if (list == null) {
                toDateParser.removeFirstChar();
                continue;
            }
            boolean bl = false;
            for (ToDateTokenizer.FormatTokenEnum formatTokenEnum : list) {
                if (!formatTokenEnum.parseFormatStrWithToken(toDateParser)) continue;
                bl = true;
                break;
            }
            if (bl) continue;
            toDateParser.removeFirstChar();
        }
        return toDateParser;
    }

    void remove(String string, String string2) {
        if (string != null && this.inputStr.length() >= string.length()) {
            this.inputStr = this.inputStr.substring(string.length());
        }
        if (string2 != null && this.formatStr.length() >= string2.length()) {
            this.formatStr = this.formatStr.substring(string2.length());
        }
    }

    public String toString() {
        int n = this.inputStr.length();
        int n2 = this.unmodifiedInputStr.length();
        int n3 = n2 - n;
        int n4 = n <= 0 ? n : n - 1;
        int n5 = this.unmodifiedFormatStr.length();
        int n6 = n5 - this.formatStr.length();
        return String.format("\n    %s('%s', '%s')", new Object[]{this.functionName, this.unmodifiedInputStr, this.unmodifiedFormatStr}) + String.format("\n      %s^%s ,  %s^ <-- Parsing failed at this point", String.format("%" + (this.functionName.name().length() + n3) + "s", ""), n4 <= 0 ? "" : String.format("%" + n4 + "s", ""), n6 <= 0 ? "" : String.format("%" + n6 + "s", ""));
    }

    public static ValueTimestamp toTimestamp(SessionLocal sessionLocal, String string, String string2) {
        ToDateParser toDateParser = ToDateParser.getTimestampParser(sessionLocal, ConfigParam.TO_TIMESTAMP, string, string2);
        return toDateParser.getResultingValue();
    }

    public static ValueTimestampTimeZone toTimestampTz(SessionLocal sessionLocal, String string, String string2) {
        ToDateParser toDateParser = ToDateParser.getTimestampParser(sessionLocal, ConfigParam.TO_TIMESTAMP_TZ, string, string2);
        return toDateParser.getResultingValueWithTimeZone();
    }

    public static ValueTimestamp toDate(SessionLocal sessionLocal, String string, String string2) {
        ToDateParser toDateParser = ToDateParser.getTimestampParser(sessionLocal, ConfigParam.TO_DATE, string, string2);
        return toDateParser.getResultingValue();
    }

    private static enum ConfigParam {
        TO_DATE("DD MON YYYY"),
        TO_TIMESTAMP("DD MON YYYY HH:MI:SS"),
        TO_TIMESTAMP_TZ("DD MON YYYY HH:MI:SS TZR");

        private final String defaultFormatStr;

        private ConfigParam(String string2) {
            this.defaultFormatStr = string2;
        }

        String getDefaultFormatStr() {
            return this.defaultFormatStr;
        }
    }
}

