/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.h2.expression.function.ToCharFunction;
import org.h2.message.DbException;
import org.h2.mode.ToDateParser;
import org.h2.util.TimeZoneProvider;

final class ToDateTokenizer {
    static final Pattern PATTERN_INLINE = Pattern.compile("(\"[^\"]*\")");
    static final Pattern PATTERN_NUMBER = Pattern.compile("^([+-]?[0-9]+)");
    static final Pattern PATTERN_FOUR_DIGITS = Pattern.compile("^([+-]?[0-9]{4})");
    static final Pattern PATTERN_TWO_TO_FOUR_DIGITS = Pattern.compile("^([+-]?[0-9]{2,4})");
    static final Pattern PATTERN_THREE_DIGITS = Pattern.compile("^([+-]?[0-9]{3})");
    static final Pattern PATTERN_TWO_DIGITS = Pattern.compile("^([+-]?[0-9]{2})");
    static final Pattern PATTERN_TWO_DIGITS_OR_LESS = Pattern.compile("^([+-]?[0-9][0-9]?)");
    static final Pattern PATTERN_ONE_DIGIT = Pattern.compile("^([+-]?[0-9])");
    static final Pattern PATTERN_FF = Pattern.compile("^(FF[0-9]?)", 2);
    static final Pattern PATTERN_AM_PM = Pattern.compile("^(AM|A\\.M\\.|PM|P\\.M\\.)", 2);
    static final Pattern PATTERN_BC_AD = Pattern.compile("^(BC|B\\.C\\.|AD|A\\.D\\.)", 2);
    static final YearParslet PARSLET_YEAR = new YearParslet();
    static final MonthParslet PARSLET_MONTH = new MonthParslet();
    static final DayParslet PARSLET_DAY = new DayParslet();
    static final TimeParslet PARSLET_TIME = new TimeParslet();
    static final InlineParslet PARSLET_INLINE = new InlineParslet();

    static String matchStringOrThrow(Pattern pattern, ToDateParser toDateParser, Enum<?> enum_) {
        String string = toDateParser.getInputStr();
        Matcher matcher = pattern.matcher(string);
        if (!matcher.find()) {
            ToDateTokenizer.throwException(toDateParser, String.format("Issue happened when parsing token '%s'", enum_.name()));
        }
        return matcher.group(1);
    }

    static String setByName(ToDateParser toDateParser, int n) {
        String string = null;
        String string2 = toDateParser.getInputStr();
        Object[] objectArray = ToCharFunction.getDateNames(n);
        for (int i = 0; i < objectArray.length; ++i) {
            int n2;
            String string3 = objectArray[i];
            if (string3 == null || !string3.equalsIgnoreCase(string2.substring(0, n2 = string3.length()))) continue;
            switch (n) {
                case 0: 
                case 1: {
                    toDateParser.setMonth(i + 1);
                    break;
                }
                case 2: 
                case 3: {
                    break;
                }
                default: {
                    throw new IllegalArgumentException();
                }
            }
            string = string3;
            break;
        }
        if (string == null || string.isEmpty()) {
            ToDateTokenizer.throwException(toDateParser, String.format("Tried to parse one of '%s' but failed (may be an internal error?)", Arrays.toString(objectArray)));
        }
        return string;
    }

    static void throwException(ToDateParser toDateParser, String string) {
        throw DbException.get(90056, toDateParser.getFunctionName(), String.format(" %s. Details: %s", string, toDateParser));
    }

    private ToDateTokenizer() {
    }

    public static enum FormatTokenEnum {
        YYYY(PARSLET_YEAR),
        SYYYY(PARSLET_YEAR),
        YYY(PARSLET_YEAR),
        YY(PARSLET_YEAR),
        SCC(PARSLET_YEAR),
        CC(PARSLET_YEAR),
        RRRR(PARSLET_YEAR),
        RR(PARSLET_YEAR),
        BC_AD(PARSLET_YEAR, PATTERN_BC_AD),
        MONTH(PARSLET_MONTH),
        MON(PARSLET_MONTH),
        MM(PARSLET_MONTH),
        RM(PARSLET_MONTH),
        DDD(PARSLET_DAY),
        DAY(PARSLET_DAY),
        DD(PARSLET_DAY),
        DY(PARSLET_DAY),
        HH24(PARSLET_TIME),
        HH12(PARSLET_TIME),
        HH(PARSLET_TIME),
        MI(PARSLET_TIME),
        SSSSS(PARSLET_TIME),
        SS(PARSLET_TIME),
        FF(PARSLET_TIME, PATTERN_FF),
        TZH(PARSLET_TIME),
        TZM(PARSLET_TIME),
        TZR(PARSLET_TIME),
        TZD(PARSLET_TIME),
        AM_PM(PARSLET_TIME, PATTERN_AM_PM),
        EE(PARSLET_YEAR),
        E(PARSLET_YEAR),
        Y(PARSLET_YEAR),
        Q(PARSLET_MONTH),
        D(PARSLET_DAY),
        J(PARSLET_DAY),
        INLINE(PARSLET_INLINE, PATTERN_INLINE);

        private static final List<FormatTokenEnum> INLINE_LIST;
        private static final List<FormatTokenEnum>[] TOKENS;
        private final ToDateParslet toDateParslet;
        private final Pattern patternToUse;

        private FormatTokenEnum(ToDateParslet toDateParslet, Pattern pattern) {
            this.toDateParslet = toDateParslet;
            this.patternToUse = pattern;
        }

        private FormatTokenEnum(ToDateParslet toDateParslet) {
            this.toDateParslet = toDateParslet;
            this.patternToUse = Pattern.compile(String.format("^(%s)", this.name()), 2);
        }

        static List<FormatTokenEnum> getTokensInQuestion(String string) {
            if (string != null && !string.isEmpty()) {
                char c = Character.toUpperCase(string.charAt(0));
                if (c >= 'A' && c <= 'Y') {
                    return TOKENS[c - 65];
                }
                if (c == '\"') {
                    return INLINE_LIST;
                }
            }
            return null;
        }

        private static void putToCache(List<FormatTokenEnum>[] listArray, FormatTokenEnum formatTokenEnum, String string) {
            int n = Character.toUpperCase(string.charAt(0)) - 65;
            List<FormatTokenEnum> list = listArray[n];
            if (list == null) {
                listArray[n] = list = new ArrayList<FormatTokenEnum>(1);
            }
            list.add(formatTokenEnum);
        }

        boolean parseFormatStrWithToken(ToDateParser toDateParser) {
            Matcher matcher = this.patternToUse.matcher(toDateParser.getFormatStr());
            boolean bl = matcher.find();
            if (bl) {
                String string = matcher.group(1);
                this.toDateParslet.parse(toDateParser, this, string);
            }
            return bl;
        }

        static {
            INLINE_LIST = Collections.singletonList(INLINE);
            List[] listArray = new List[25];
            for (FormatTokenEnum formatTokenEnum : FormatTokenEnum.values()) {
                String string = formatTokenEnum.name();
                if (string.indexOf(95) >= 0) {
                    for (String string2 : string.split("_")) {
                        FormatTokenEnum.putToCache(listArray, formatTokenEnum, string2);
                    }
                    continue;
                }
                FormatTokenEnum.putToCache(listArray, formatTokenEnum, string);
            }
            TOKENS = listArray;
        }
    }

    static class InlineParslet
    implements ToDateParslet {
        InlineParslet() {
        }

        @Override
        public void parse(ToDateParser toDateParser, FormatTokenEnum formatTokenEnum, String string) {
            String string2 = null;
            switch (formatTokenEnum) {
                case INLINE: {
                    string2 = string.replace("\"", "");
                    break;
                }
                default: {
                    throw new IllegalArgumentException(String.format("%s: Internal Error. Unhandled case: %s", new Object[]{this.getClass().getSimpleName(), formatTokenEnum}));
                }
            }
            toDateParser.remove(string2, string);
        }
    }

    static class TimeParslet
    implements ToDateParslet {
        TimeParslet() {
        }

        @Override
        public void parse(ToDateParser toDateParser, FormatTokenEnum formatTokenEnum, String string) {
            String string2 = null;
            int n = 0;
            switch (formatTokenEnum) {
                case HH24: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS_OR_LESS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setHour(n);
                    break;
                }
                case HH12: 
                case HH: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS_OR_LESS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setHour12(n);
                    break;
                }
                case MI: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS_OR_LESS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setMinute(n);
                    break;
                }
                case SS: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS_OR_LESS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setSecond(n);
                    break;
                }
                case SSSSS: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_NUMBER, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    int n2 = n % 60;
                    int n3 = (n /= 60) % 60;
                    int n4 = (n /= 60) % 24;
                    toDateParser.setHour(n4);
                    toDateParser.setMinute(n3);
                    toDateParser.setSecond(n2);
                    break;
                }
                case FF: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_NUMBER, toDateParser, formatTokenEnum);
                    String string3 = String.format("%-9s", string2).replace(' ', '0');
                    string3 = string3.substring(0, 9);
                    double d = Double.parseDouble(string3);
                    toDateParser.setNanos((int)d);
                    break;
                }
                case AM_PM: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_AM_PM, toDateParser, formatTokenEnum);
                    if (string2.toUpperCase().startsWith("A")) {
                        toDateParser.setAmPm(true);
                        break;
                    }
                    toDateParser.setAmPm(false);
                    break;
                }
                case TZH: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS_OR_LESS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setTimeZoneHour(n);
                    break;
                }
                case TZM: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS_OR_LESS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setTimeZoneMinute(n);
                    break;
                }
                case TZR: 
                case TZD: {
                    String string4 = toDateParser.getInputStr();
                    toDateParser.setTimeZone(TimeZoneProvider.ofId(string4));
                    string2 = string4;
                    break;
                }
                default: {
                    throw new IllegalArgumentException(String.format("%s: Internal Error. Unhandled case: %s", new Object[]{this.getClass().getSimpleName(), formatTokenEnum}));
                }
            }
            toDateParser.remove(string2, string);
        }
    }

    static class DayParslet
    implements ToDateParslet {
        DayParslet() {
        }

        @Override
        public void parse(ToDateParser toDateParser, FormatTokenEnum formatTokenEnum, String string) {
            String string2 = null;
            int n = 0;
            switch (formatTokenEnum) {
                case DDD: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_NUMBER, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setDayOfYear(n);
                    break;
                }
                case DD: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS_OR_LESS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setDay(n);
                    break;
                }
                case D: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_ONE_DIGIT, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setDay(n);
                    break;
                }
                case DAY: {
                    string2 = ToDateTokenizer.setByName(toDateParser, 2);
                    break;
                }
                case DY: {
                    string2 = ToDateTokenizer.setByName(toDateParser, 3);
                    break;
                }
                case J: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_NUMBER, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    toDateParser.setAbsoluteDay(n + -2440588);
                    break;
                }
                default: {
                    throw new IllegalArgumentException(String.format("%s: Internal Error. Unhandled case: %s", new Object[]{this.getClass().getSimpleName(), formatTokenEnum}));
                }
            }
            toDateParser.remove(string2, string);
        }
    }

    static class MonthParslet
    implements ToDateParslet {
        private static final String[] ROMAN_MONTH = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};

        MonthParslet() {
        }

        @Override
        public void parse(ToDateParser toDateParser, FormatTokenEnum formatTokenEnum, String string) {
            String string2 = toDateParser.getInputStr();
            String string3 = null;
            int n = 0;
            switch (formatTokenEnum) {
                case MONTH: {
                    string3 = ToDateTokenizer.setByName(toDateParser, 0);
                    break;
                }
                case Q: {
                    ToDateTokenizer.throwException(toDateParser, String.format("token '%s' not supported yet.", formatTokenEnum.name()));
                    break;
                }
                case MON: {
                    string3 = ToDateTokenizer.setByName(toDateParser, 1);
                    break;
                }
                case MM: {
                    string3 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS_OR_LESS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string3);
                    toDateParser.setMonth(n);
                    break;
                }
                case RM: {
                    n = 0;
                    for (String string4 : ROMAN_MONTH) {
                        ++n;
                        int n2 = string4.length();
                        if (string2.length() < n2 || !string4.equalsIgnoreCase(string2.substring(0, n2))) continue;
                        toDateParser.setMonth(n + 1);
                        string3 = string4;
                        break;
                    }
                    if (string3 != null && !string3.isEmpty()) break;
                    ToDateTokenizer.throwException(toDateParser, String.format("Issue happened when parsing token '%s'. Expected one of: %s", formatTokenEnum.name(), Arrays.toString(ROMAN_MONTH)));
                    break;
                }
                default: {
                    throw new IllegalArgumentException(String.format("%s: Internal Error. Unhandled case: %s", new Object[]{this.getClass().getSimpleName(), formatTokenEnum}));
                }
            }
            toDateParser.remove(string3, string);
        }
    }

    static class YearParslet
    implements ToDateParslet {
        YearParslet() {
        }

        @Override
        public void parse(ToDateParser toDateParser, FormatTokenEnum formatTokenEnum, String string) {
            String string2 = null;
            int n = 0;
            switch (formatTokenEnum) {
                case SYYYY: 
                case YYYY: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_FOUR_DIGITS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    if (n == 0) {
                        ToDateTokenizer.throwException(toDateParser, "Year may not be zero");
                    }
                    toDateParser.setYear(n >= 0 ? n : n + 1);
                    break;
                }
                case YYY: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_THREE_DIGITS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    if (n > 999) {
                        ToDateTokenizer.throwException(toDateParser, "Year may have only three digits with specified format");
                    }
                    toDateParser.setYear((n += toDateParser.getCurrentYear() / 1000 * 1000) >= 0 ? n : n + 1);
                    break;
                }
                case RRRR: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_TO_FOUR_DIGITS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    if (string2.length() < 4) {
                        if (n < 50) {
                            n += 2000;
                        } else if (n < 100) {
                            n += 1900;
                        }
                    }
                    if (n == 0) {
                        ToDateTokenizer.throwException(toDateParser, "Year may not be zero");
                    }
                    toDateParser.setYear(n);
                    break;
                }
                case RR: {
                    int n2 = toDateParser.getCurrentYear() / 100;
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2) + n2 * 100;
                    toDateParser.setYear(n);
                    break;
                }
                case EE: {
                    ToDateTokenizer.throwException(toDateParser, String.format("token '%s' not supported yet.", formatTokenEnum.name()));
                    break;
                }
                case E: {
                    ToDateTokenizer.throwException(toDateParser, String.format("token '%s' not supported yet.", formatTokenEnum.name()));
                    break;
                }
                case YY: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    if (n > 99) {
                        ToDateTokenizer.throwException(toDateParser, "Year may have only two digits with specified format");
                    }
                    toDateParser.setYear((n += toDateParser.getCurrentYear() / 100 * 100) >= 0 ? n : n + 1);
                    break;
                }
                case SCC: 
                case CC: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_TWO_DIGITS, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2) * 100;
                    toDateParser.setYear(n);
                    break;
                }
                case Y: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_ONE_DIGIT, toDateParser, formatTokenEnum);
                    n = Integer.parseInt(string2);
                    if (n > 9) {
                        ToDateTokenizer.throwException(toDateParser, "Year may have only two digits with specified format");
                    }
                    toDateParser.setYear((n += toDateParser.getCurrentYear() / 10 * 10) >= 0 ? n : n + 1);
                    break;
                }
                case BC_AD: {
                    string2 = ToDateTokenizer.matchStringOrThrow(PATTERN_BC_AD, toDateParser, formatTokenEnum);
                    toDateParser.setBC(string2.toUpperCase().startsWith("B"));
                    break;
                }
                default: {
                    throw new IllegalArgumentException(String.format("%s: Internal Error. Unhandled case: %s", new Object[]{this.getClass().getSimpleName(), formatTokenEnum}));
                }
            }
            toDateParser.remove(string2, string);
        }
    }

    static interface ToDateParslet {
        public void parse(ToDateParser var1, FormatTokenEnum var2, String var3);
    }
}

