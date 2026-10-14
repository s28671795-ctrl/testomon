/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.DateTimeFunction;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.util.StringUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueVarchar;

public final class ToCharFunction
extends FunctionN {
    public static final int JULIAN_EPOCH = -2440588;
    private static final int[] ROMAN_VALUES = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROMAN_NUMERALS = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    public static final int MONTHS = 0;
    public static final int SHORT_MONTHS = 1;
    public static final int WEEKDAYS = 2;
    public static final int SHORT_WEEKDAYS = 3;
    static final int AM_PM = 4;
    private static volatile String[][] NAMES;

    public static String toChar(BigDecimal bigDecimal, String string, String string2) {
        char c;
        char c2;
        int n;
        int n2;
        int n3;
        int n4;
        Integer n5;
        char c3;
        int n6;
        int n7;
        boolean bl;
        boolean bl2;
        boolean bl3;
        String string3;
        String string4 = string3 = string != null ? StringUtils.toUpperEnglish(string) : null;
        if (string3 == null || string3.equals("TM") || string3.equals("TM9")) {
            String string5 = bigDecimal.toPlainString();
            return string5.startsWith("0.") ? string5.substring(1) : string5;
        }
        if (string3.equals("TME")) {
            int n8 = bigDecimal.precision() - bigDecimal.scale() - 1;
            bigDecimal = bigDecimal.movePointLeft(n8);
            return bigDecimal.toPlainString() + "E" + (n8 < 0 ? (char)'-' : '+') + (Math.abs(n8) < 10 ? "0" : "") + Math.abs(n8);
        }
        if (string3.equals("RN")) {
            boolean bl4 = string.startsWith("r");
            String string6 = StringUtils.pad(ToCharFunction.toRomanNumeral(bigDecimal.intValue()), 15, " ", false);
            return bl4 ? string6.toLowerCase() : string6;
        }
        if (string3.equals("FMRN")) {
            boolean bl5 = string.charAt(2) == 'r';
            String string7 = ToCharFunction.toRomanNumeral(bigDecimal.intValue());
            return bl5 ? string7.toLowerCase() : string7;
        }
        if (string3.endsWith("X")) {
            return ToCharFunction.toHex(bigDecimal, string);
        }
        String string8 = string;
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        char c4 = decimalFormatSymbols.getGroupingSeparator();
        char c5 = decimalFormatSymbols.getDecimalSeparator();
        boolean bl6 = string3.startsWith("S");
        if (bl6) {
            string = string.substring(1);
        }
        if (bl3 = string3.endsWith("S")) {
            string = string.substring(0, string.length() - 1);
        }
        if (bl2 = string3.endsWith("MI")) {
            string = string.substring(0, string.length() - 2);
        }
        if (bl = string3.endsWith("PR")) {
            string = string.substring(0, string.length() - 2);
        }
        if ((n7 = string3.indexOf(86)) >= 0) {
            int n9 = 0;
            for (n6 = n7 + 1; n6 < string.length(); ++n6) {
                c3 = string.charAt(n6);
                if (c3 != '0' && c3 != '9') continue;
                ++n9;
            }
            bigDecimal = bigDecimal.movePointRight(n9);
            string = string.substring(0, n7) + string.substring(n7 + 1);
        }
        if (string.endsWith("EEEE")) {
            n5 = bigDecimal.precision() - bigDecimal.scale() - 1;
            bigDecimal = bigDecimal.movePointLeft(n5);
            string = string.substring(0, string.length() - 4);
        } else {
            n5 = null;
        }
        n6 = 1;
        char c6 = c3 = !string3.startsWith("FM") ? (char)'\u0001' : '\u0000';
        if (!c3) {
            string = string.substring(2);
        }
        if ((n4 = ToCharFunction.calculateScale(string = string.replaceAll("[Bb]", ""), n3 = ToCharFunction.findDecimalSeparator(string))) < (n2 = bigDecimal.scale())) {
            bigDecimal = bigDecimal.setScale(n4, RoundingMode.HALF_UP);
        } else if (n2 < 0) {
            bigDecimal = bigDecimal.setScale(0);
        }
        for (int i = string.indexOf(48); i >= 0 && i < n3; ++i) {
            if (string.charAt(i) != '9') continue;
            string = string.substring(0, i) + "0" + string.substring(i + 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string9 = (bigDecimal.abs().compareTo(BigDecimal.ONE) < 0 ? ToCharFunction.zeroesAfterDecimalSeparator(bigDecimal) : "") + bigDecimal.unscaledValue().abs().toString();
        int n10 = string9.length() - bigDecimal.scale() - 1;
        for (n = n3 - 1; n >= 0; --n) {
            c2 = string.charAt(n);
            ++n6;
            if (c2 == '9' || c2 == '0') {
                if (n10 >= 0) {
                    c = string9.charAt(n10);
                    stringBuilder.insert(0, c);
                    --n10;
                    continue;
                }
                if (c2 != '0' || n5 != null) continue;
                stringBuilder.insert(0, '0');
                continue;
            }
            if (c2 == ',') {
                if (n10 < 0 && (n <= 0 || string.charAt(n - 1) != '0')) continue;
                stringBuilder.insert(0, c2);
                continue;
            }
            if (c2 == 'G' || c2 == 'g') {
                if (n10 < 0 && (n <= 0 || string.charAt(n - 1) != '0')) continue;
                stringBuilder.insert(0, c4);
                continue;
            }
            if (c2 == 'C' || c2 == 'c') {
                Currency currency = ToCharFunction.getCurrency();
                stringBuilder.insert(0, currency.getCurrencyCode());
                n6 += 6;
                continue;
            }
            if (c2 == 'L' || c2 == 'l' || c2 == 'U' || c2 == 'u') {
                Currency currency = ToCharFunction.getCurrency();
                stringBuilder.insert(0, currency.getSymbol());
                n6 += 9;
                continue;
            }
            if (c2 == '$') {
                Currency currency = ToCharFunction.getCurrency();
                String string10 = currency.getSymbol();
                stringBuilder.insert(0, string10);
                continue;
            }
            throw DbException.get(90010, string8);
        }
        if (n10 >= 0) {
            return StringUtils.pad("", string.length() + 1, "#", true);
        }
        if (n3 < string.length()) {
            ++n6;
            c2 = string.charAt(n3);
            if (c2 == 'd' || c2 == 'D') {
                stringBuilder.append(c5);
            } else {
                stringBuilder.append(c2);
            }
            n10 = string9.length() - bigDecimal.scale();
            for (n = n3 + 1; n < string.length(); ++n) {
                c = string.charAt(n);
                ++n6;
                if (c == '9' || c == '0') {
                    if (n10 < string9.length()) {
                        char c7 = string9.charAt(n10);
                        stringBuilder.append(c7);
                        ++n10;
                        continue;
                    }
                    if (c != '0' && !c3) continue;
                    stringBuilder.append('0');
                    continue;
                }
                throw DbException.get(90010, string8);
            }
        }
        ToCharFunction.addSign(stringBuilder, bigDecimal.signum(), bl6, bl3, bl2, bl, c3 != '\u0000');
        if (n5 != null) {
            stringBuilder.append('E');
            stringBuilder.append(n5 < 0 ? (char)'-' : '+');
            stringBuilder.append(Math.abs(n5) < 10 ? "0" : "");
            stringBuilder.append(Math.abs(n5));
        }
        if (c3) {
            if (n5 != null) {
                stringBuilder.insert(0, ' ');
            } else {
                while (stringBuilder.length() < n6) {
                    stringBuilder.insert(0, ' ');
                }
            }
        }
        return stringBuilder.toString();
    }

    private static Currency getCurrency() {
        Locale locale = Locale.getDefault();
        return Currency.getInstance(locale.getCountry().length() == 2 ? locale : Locale.US);
    }

    private static String zeroesAfterDecimalSeparator(BigDecimal bigDecimal) {
        int n;
        String string = bigDecimal.toPlainString();
        int n2 = string.indexOf(46);
        if (n2 < 0) {
            return "";
        }
        boolean bl = true;
        int n3 = string.length();
        for (n = n2 + 1; n < n3; ++n) {
            if (string.charAt(n) == '0') continue;
            bl = false;
            break;
        }
        char[] cArray = new char[bl ? n3 - n2 - 1 : n - 1 - n2];
        Arrays.fill(cArray, '0');
        return String.valueOf(cArray);
    }

    private static void addSign(StringBuilder stringBuilder, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        if (bl4) {
            if (n < 0) {
                stringBuilder.insert(0, '<');
                stringBuilder.append('>');
            } else if (bl5) {
                stringBuilder.insert(0, ' ');
                stringBuilder.append(' ');
            }
        } else {
            String string = n == 0 ? "" : (n < 0 ? "-" : (bl || bl2 ? "+" : (bl5 ? " " : "")));
            if (bl3 || bl2) {
                stringBuilder.append(string);
            } else {
                stringBuilder.insert(0, string);
            }
        }
    }

    private static int findDecimalSeparator(String string) {
        int n = string.indexOf(46);
        if (n == -1 && (n = string.indexOf(68)) == -1 && (n = string.indexOf(100)) == -1) {
            n = string.length();
        }
        return n;
    }

    private static int calculateScale(String string, int n) {
        int n2 = 0;
        for (int i = n; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c != '0' && c != '9') continue;
            ++n2;
        }
        return n2;
    }

    private static String toRomanNumeral(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ROMAN_VALUES.length; ++i) {
            int n2 = ROMAN_VALUES[i];
            String string = ROMAN_NUMERALS[i];
            while (n >= n2) {
                stringBuilder.append(string);
                n -= n2;
            }
        }
        return stringBuilder.toString();
    }

    private static String toHex(BigDecimal bigDecimal, String string) {
        int n;
        boolean bl = !StringUtils.toUpperEnglish(string).startsWith("FM");
        boolean bl2 = !string.contains("x");
        boolean bl3 = string.startsWith("0");
        int n2 = 0;
        for (n = 0; n < string.length(); ++n) {
            char c = string.charAt(n);
            if (c != '0' && c != 'X' && c != 'x') continue;
            ++n2;
        }
        n = bigDecimal.setScale(0, RoundingMode.HALF_UP).intValue();
        String string2 = Integer.toHexString(n);
        if (n2 < string2.length()) {
            string2 = StringUtils.pad("", n2 + 1, "#", true);
        } else {
            if (bl2) {
                string2 = StringUtils.toUpperEnglish(string2);
            }
            if (bl3) {
                string2 = StringUtils.pad(string2, n2, "0", false);
            }
            if (bl) {
                string2 = StringUtils.pad(string2, string.length() + 1, " ", false);
            }
        }
        return string2;
    }

    public static String[] getDateNames(int n) {
        String[][] stringArray = NAMES;
        if (stringArray == null) {
            stringArray = new String[5][];
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance();
            stringArray[0] = dateFormatSymbols.getMonths();
            String[] stringArray2 = dateFormatSymbols.getShortMonths();
            for (int i = 0; i < 12; ++i) {
                String string = stringArray2[i];
                if (!string.endsWith(".")) continue;
                stringArray2[i] = string.substring(0, string.length() - 1);
            }
            stringArray[1] = stringArray2;
            stringArray[2] = dateFormatSymbols.getWeekdays();
            stringArray[3] = dateFormatSymbols.getShortWeekdays();
            stringArray[4] = dateFormatSymbols.getAmPmStrings();
            NAMES = stringArray;
        }
        return stringArray[n];
    }

    public static void clearNames() {
        NAMES = null;
    }

    private static String getTimeZone(SessionLocal sessionLocal, Value value, boolean bl) {
        if (value instanceof ValueTimestampTimeZone) {
            return DateTimeUtils.timeZoneNameFromOffsetSeconds(((ValueTimestampTimeZone)value).getTimeZoneOffsetSeconds());
        }
        if (value instanceof ValueTimeTimeZone) {
            return DateTimeUtils.timeZoneNameFromOffsetSeconds(((ValueTimeTimeZone)value).getTimeZoneOffsetSeconds());
        }
        TimeZoneProvider timeZoneProvider = sessionLocal.currentTimeZone();
        if (bl) {
            ValueTimestamp valueTimestamp = (ValueTimestamp)value.convertTo(TypeInfo.TYPE_TIMESTAMP, (CastDataProvider)sessionLocal);
            return timeZoneProvider.getShortId(timeZoneProvider.getEpochSecondsFromLocal(valueTimestamp.getDateValue(), valueTimestamp.getTimeNanos()));
        }
        return timeZoneProvider.getId();
    }

    public static String toCharDateTime(SessionLocal sessionLocal, Value value, String string, String string2) {
        boolean bl;
        long[] lArray = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
        long l = lArray[0];
        long l2 = lArray[1];
        int n = DateTimeUtils.yearFromDateValue(l);
        int n2 = DateTimeUtils.monthFromDateValue(l);
        int n3 = DateTimeUtils.dayFromDateValue(l);
        int n4 = Math.abs(n);
        int n5 = (int)(l2 / 1000000000L);
        int n6 = (int)(l2 - (long)(n5 * 1000000000));
        int n7 = n5 / 60;
        n5 -= n7 * 60;
        int n8 = n7 / 60;
        n7 -= n8 * 60;
        int n9 = (n8 + 11) % 12 + 1;
        boolean bl2 = bl = n8 < 12;
        if (string == null) {
            string = "DD-MON-YY HH.MI.SS.FF PM";
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = true;
        int n10 = 0;
        int n11 = string.length();
        block0: while (n10 < n11) {
            Capitalization capitalization = ToCharFunction.containsAt(string, n10, "A.D.", "B.C.");
            if (capitalization != null) {
                String string3 = n > 0 ? "A.D." : "B.C.";
                stringBuilder.append(capitalization.apply(string3));
                n10 += 4;
                continue;
            }
            capitalization = ToCharFunction.containsAt(string, n10, "AD", "BC");
            if (capitalization != null) {
                String string4 = n > 0 ? "AD" : "BC";
                stringBuilder.append(capitalization.apply(string4));
                n10 += 2;
                continue;
            }
            capitalization = ToCharFunction.containsAt(string, n10, "A.M.", "P.M.");
            if (capitalization != null) {
                String string5 = bl ? "A.M." : "P.M.";
                stringBuilder.append(capitalization.apply(string5));
                n10 += 4;
                continue;
            }
            capitalization = ToCharFunction.containsAt(string, n10, "AM", "PM");
            if (capitalization != null) {
                String string6 = bl ? "AM" : "PM";
                stringBuilder.append(capitalization.apply(string6));
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "DL") != null) {
                String string7 = ToCharFunction.getDateNames(2)[DateTimeUtils.getSundayDayOfWeek(l)];
                String string8 = ToCharFunction.getDateNames(0)[n2 - 1];
                stringBuilder.append(string7).append(", ").append(string8).append(' ').append(n3).append(", ");
                StringUtils.appendZeroPadded(stringBuilder, 4, n4);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "DS") != null) {
                StringUtils.appendTwoDigits(stringBuilder, n2).append('/');
                StringUtils.appendTwoDigits(stringBuilder, n3).append('/');
                StringUtils.appendZeroPadded(stringBuilder, 4, n4);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "TS") != null) {
                stringBuilder.append(n9).append(':');
                StringUtils.appendTwoDigits(stringBuilder, n7).append(':');
                StringUtils.appendTwoDigits(stringBuilder, n5).append(' ').append(ToCharFunction.getDateNames(4)[bl ? 0 : 1]);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "DDD") != null) {
                stringBuilder.append(DateTimeUtils.getDayOfYear(l));
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "DD") != null) {
                StringUtils.appendTwoDigits(stringBuilder, n3);
                n10 += 2;
                continue;
            }
            capitalization = ToCharFunction.containsAt(string, n10, "DY");
            if (capitalization != null) {
                String string9 = ToCharFunction.getDateNames(3)[DateTimeUtils.getSundayDayOfWeek(l)];
                stringBuilder.append(capitalization.apply(string9));
                n10 += 2;
                continue;
            }
            capitalization = ToCharFunction.containsAt(string, n10, "DAY");
            if (capitalization != null) {
                String string10 = ToCharFunction.getDateNames(2)[DateTimeUtils.getSundayDayOfWeek(l)];
                if (bl3) {
                    string10 = StringUtils.pad(string10, "Wednesday".length(), " ", true);
                }
                stringBuilder.append(capitalization.apply(string10));
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "D") != null) {
                stringBuilder.append(DateTimeUtils.getSundayDayOfWeek(l));
                ++n10;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "J") != null) {
                stringBuilder.append(DateTimeUtils.absoluteDayFromDateValue(l) - -2440588L);
                ++n10;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "HH24") != null) {
                StringUtils.appendTwoDigits(stringBuilder, n8);
                n10 += 4;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "HH12") != null) {
                StringUtils.appendTwoDigits(stringBuilder, n9);
                n10 += 4;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "HH") != null) {
                StringUtils.appendTwoDigits(stringBuilder, n9);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "MI") != null) {
                StringUtils.appendTwoDigits(stringBuilder, n7);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "SSSSS") != null) {
                int n12 = (int)(l2 / 1000000000L);
                stringBuilder.append(n12);
                n10 += 5;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "SS") != null) {
                StringUtils.appendTwoDigits(stringBuilder, n5);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "FF1", "FF2", "FF3", "FF4", "FF5", "FF6", "FF7", "FF8", "FF9") != null) {
                int n13 = string.charAt(n10 + 2) - 48;
                int n14 = (int)((double)n6 * Math.pow(10.0, n13 - 9));
                StringUtils.appendZeroPadded(stringBuilder, n13, n14);
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "FF") != null) {
                StringUtils.appendZeroPadded(stringBuilder, 9, n6);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "TZR") != null) {
                stringBuilder.append(ToCharFunction.getTimeZone(sessionLocal, value, false));
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "TZD") != null) {
                stringBuilder.append(ToCharFunction.getTimeZone(sessionLocal, value, true));
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "TZH") != null) {
                int n15 = DateTimeFunction.extractDateTime(sessionLocal, value, 6);
                stringBuilder.append(n15 < 0 ? (char)'-' : '+');
                StringUtils.appendTwoDigits(stringBuilder, Math.abs(n15));
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "TZM") != null) {
                StringUtils.appendTwoDigits(stringBuilder, Math.abs(DateTimeFunction.extractDateTime(sessionLocal, value, 7)));
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "WW") != null) {
                StringUtils.appendTwoDigits(stringBuilder, (DateTimeUtils.getDayOfYear(l) - 1) / 7 + 1);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "IW") != null) {
                StringUtils.appendTwoDigits(stringBuilder, DateTimeUtils.getIsoWeekOfYear(l));
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "W") != null) {
                stringBuilder.append((n3 - 1) / 7 + 1);
                ++n10;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "Y,YYY") != null) {
                stringBuilder.append(new DecimalFormat("#,###").format(n4));
                n10 += 5;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "SYYYY") != null) {
                if (n < 0) {
                    stringBuilder.append('-');
                }
                StringUtils.appendZeroPadded(stringBuilder, 4, n4);
                n10 += 5;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "YYYY", "RRRR") != null) {
                StringUtils.appendZeroPadded(stringBuilder, 4, n4);
                n10 += 4;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "IYYY") != null) {
                StringUtils.appendZeroPadded(stringBuilder, 4, Math.abs(DateTimeUtils.getIsoWeekYear(l)));
                n10 += 4;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "YYY") != null) {
                StringUtils.appendZeroPadded(stringBuilder, 3, n4 % 1000);
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "IYY") != null) {
                StringUtils.appendZeroPadded(stringBuilder, 3, Math.abs(DateTimeUtils.getIsoWeekYear(l)) % 1000);
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "YY", "RR") != null) {
                StringUtils.appendTwoDigits(stringBuilder, n4 % 100);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "IY") != null) {
                StringUtils.appendTwoDigits(stringBuilder, Math.abs(DateTimeUtils.getIsoWeekYear(l)) % 100);
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "Y") != null) {
                stringBuilder.append(n4 % 10);
                ++n10;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "I") != null) {
                stringBuilder.append(Math.abs(DateTimeUtils.getIsoWeekYear(l)) % 10);
                ++n10;
                continue;
            }
            capitalization = ToCharFunction.containsAt(string, n10, "MONTH");
            if (capitalization != null) {
                String string11 = ToCharFunction.getDateNames(0)[n2 - 1];
                if (bl3) {
                    string11 = StringUtils.pad(string11, "September".length(), " ", true);
                }
                stringBuilder.append(capitalization.apply(string11));
                n10 += 5;
                continue;
            }
            capitalization = ToCharFunction.containsAt(string, n10, "MON");
            if (capitalization != null) {
                String string12 = ToCharFunction.getDateNames(1)[n2 - 1];
                stringBuilder.append(capitalization.apply(string12));
                n10 += 3;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "MM") != null) {
                StringUtils.appendTwoDigits(stringBuilder, n2);
                n10 += 2;
                continue;
            }
            capitalization = ToCharFunction.containsAt(string, n10, "RM");
            if (capitalization != null) {
                stringBuilder.append(capitalization.apply(ToCharFunction.toRomanNumeral(n2)));
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "Q") != null) {
                int n16 = 1 + (n2 - 1) / 3;
                stringBuilder.append(n16);
                ++n10;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "X") != null) {
                char c = DecimalFormatSymbols.getInstance().getDecimalSeparator();
                stringBuilder.append(c);
                ++n10;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "FM") != null) {
                bl3 = !bl3;
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "FX") != null) {
                n10 += 2;
                continue;
            }
            if (ToCharFunction.containsAt(string, n10, "\"") != null) {
                ++n10;
                while (n10 < string.length()) {
                    char c = string.charAt(n10);
                    if (c == '\"') {
                        ++n10;
                        continue block0;
                    }
                    stringBuilder.append(c);
                    ++n10;
                }
                continue;
            }
            if (string.charAt(n10) == '-' || string.charAt(n10) == '/' || string.charAt(n10) == ',' || string.charAt(n10) == '.' || string.charAt(n10) == ';' || string.charAt(n10) == ':' || string.charAt(n10) == ' ') {
                stringBuilder.append(string.charAt(n10));
                ++n10;
                continue;
            }
            throw DbException.get(90010, string);
        }
        return stringBuilder.toString();
    }

    private static Capitalization containsAt(String string, int n, String ... stringArray) {
        for (String string2 : stringArray) {
            if (n + string2.length() > string.length()) continue;
            boolean bl = true;
            Boolean bl2 = null;
            Boolean bl3 = null;
            for (int i = 0; i < string2.length(); ++i) {
                char c;
                char c2 = string.charAt(n + i);
                if (c2 != (c = string2.charAt(i)) && Character.toUpperCase(c2) != Character.toUpperCase(c)) {
                    bl = false;
                    break;
                }
                if (!Character.isLetter(c2)) continue;
                if (bl2 == null) {
                    bl2 = Character.isUpperCase(c2);
                    continue;
                }
                if (bl3 != null) continue;
                bl3 = Character.isUpperCase(c2);
            }
            if (!bl) continue;
            return Capitalization.toCapitalization(bl2, bl3);
        }
        return null;
    }

    public ToCharFunction(Expression expression, Expression expression2, Expression expression3) {
        Expression[] expressionArray;
        if (expression2 == null) {
            Expression[] expressionArray2 = new Expression[1];
            expressionArray = expressionArray2;
            expressionArray2[0] = expression;
        } else if (expression3 == null) {
            Expression[] expressionArray3 = new Expression[2];
            expressionArray3[0] = expression;
            expressionArray = expressionArray3;
            expressionArray3[1] = expression2;
        } else {
            Expression[] expressionArray4 = new Expression[3];
            expressionArray4[0] = expression;
            expressionArray4[1] = expression2;
            expressionArray = expressionArray4;
            expressionArray4[2] = expression3;
        }
        super(expressionArray);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        switch (value.getValueType()) {
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: {
                value = ValueVarchar.get(ToCharFunction.toCharDateTime(sessionLocal, value, value2 == null ? null : value2.getString(), value3 == null ? null : value3.getString()), sessionLocal);
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: {
                value = ValueVarchar.get(ToCharFunction.toChar(value.getBigDecimal(), value2 == null ? null : value2.getString(), value3 == null ? null : value3.getString()), sessionLocal);
                break;
            }
            default: {
                value = ValueVarchar.get(value.getString(), sessionLocal);
            }
        }
        return value;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal, true);
        this.type = TypeInfo.TYPE_VARCHAR;
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return "TO_CHAR";
    }

    public static enum Capitalization {
        UPPERCASE,
        LOWERCASE,
        CAPITALIZE;


        static Capitalization toCapitalization(Boolean bl, Boolean bl2) {
            if (bl == null) {
                return CAPITALIZE;
            }
            if (bl2 == null) {
                return bl != false ? UPPERCASE : LOWERCASE;
            }
            if (bl.booleanValue()) {
                return bl2 != false ? UPPERCASE : CAPITALIZE;
            }
            return LOWERCASE;
        }

        public String apply(String string) {
            if (string == null || string.isEmpty()) {
                return string;
            }
            switch (this) {
                case UPPERCASE: {
                    return StringUtils.toUpperEnglish(string);
                }
                case LOWERCASE: {
                    return StringUtils.toLowerEnglish(string);
                }
                case CAPITALIZE: {
                    return Character.toUpperCase(string.charAt(0)) + (string.length() > 1 ? StringUtils.toLowerEnglish(string).substring(1) : "");
                }
            }
            throw new IllegalArgumentException("Unknown capitalization strategy: " + (Object)((Object)this));
        }
    }
}

