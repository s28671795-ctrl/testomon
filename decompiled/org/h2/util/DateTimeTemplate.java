/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.ArrayList;
import java.util.Arrays;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.util.SmallLRUCache;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDate;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;

public final class DateTimeTemplate {
    private static final SmallLRUCache<String, DateTimeTemplate> CACHE = SmallLRUCache.newInstance(100);
    private final Part[] parts;
    private final boolean containsDate;
    private final boolean containsTime;
    private final boolean containsTimeZone;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DateTimeTemplate of(String string) {
        DateTimeTemplate dateTimeTemplate;
        Object object = CACHE;
        synchronized (object) {
            dateTimeTemplate = (DateTimeTemplate)CACHE.get(string);
            if (dateTimeTemplate != null) {
                return dateTimeTemplate;
            }
        }
        object = DateTimeTemplate.parseTemplate(string);
        SmallLRUCache<String, DateTimeTemplate> smallLRUCache = CACHE;
        synchronized (smallLRUCache) {
            dateTimeTemplate = CACHE.putIfAbsent(string, (DateTimeTemplate)object);
        }
        return dateTimeTemplate != null ? dateTimeTemplate : object;
    }

    private static DateTimeTemplate parseTemplate(String string) {
        int n;
        ArrayList<Delimiter> arrayList;
        block35: {
            block34: {
                int n2;
                arrayList = new ArrayList<Delimiter>();
                Scanner scanner = new Scanner(string);
                n = 0;
                while ((n2 = scanner.readChar()) >= 0) {
                    Part part;
                    switch (n2) {
                        case 45: {
                            part = Delimiter.MINUS_SIGN;
                            break;
                        }
                        case 46: {
                            part = Delimiter.PERIOD;
                            break;
                        }
                        case 47: {
                            part = Delimiter.SOLIDUS;
                            break;
                        }
                        case 44: {
                            part = Delimiter.COMMA;
                            break;
                        }
                        case 39: {
                            part = Delimiter.APOSTROPHE;
                            break;
                        }
                        case 59: {
                            part = Delimiter.SEMICOLON;
                            break;
                        }
                        case 58: {
                            part = Delimiter.COLON;
                            break;
                        }
                        case 32: {
                            part = Delimiter.SPACE;
                            break;
                        }
                        case 89: {
                            n = DateTimeTemplate.checkUsed(n, 0, string);
                            if (scanner.readCharIf('Y')) {
                                if (scanner.readCharIf('Y')) {
                                    part = scanner.readCharIf('Y') ? Field.YYYY : Field.YYY;
                                    break;
                                }
                                part = Field.YY;
                                break;
                            }
                            part = Field.Y;
                            break;
                        }
                        case 82: {
                            n = DateTimeTemplate.checkUsed(n, 0, string);
                            scanner.readChar('R');
                            if (scanner.readCharIf('R')) {
                                scanner.readChar('R');
                                part = Field.RRRR;
                                break;
                            }
                            part = Field.RR;
                            break;
                        }
                        case 77: {
                            if (scanner.readCharIf('I')) {
                                n = DateTimeTemplate.checkUsed(n, 7, string);
                                part = Field.MI;
                                break;
                            }
                            scanner.readChar('M');
                            n = DateTimeTemplate.checkUsed(n, 2, string);
                            part = Field.MM;
                            break;
                        }
                        case 68: {
                            scanner.readChar('D');
                            if (scanner.readCharIf('D')) {
                                n = DateTimeTemplate.checkUsed(n, 4, string);
                                part = Field.DDD;
                                break;
                            }
                            n = DateTimeTemplate.checkUsed(n, 3, string);
                            part = Field.DD;
                            break;
                        }
                        case 72: {
                            scanner.readChar('H');
                            if (scanner.readCharIf('2')) {
                                scanner.readChar('4');
                                n = DateTimeTemplate.checkUsed(n, 6, string);
                                part = Field.HH24;
                                break;
                            }
                            if (scanner.readCharIf('1')) {
                                scanner.readChar('2');
                            }
                            n = DateTimeTemplate.checkUsed(n, 5, string);
                            part = Field.HH12;
                            break;
                        }
                        case 83: {
                            scanner.readChar('S');
                            if (scanner.readCharIf('S')) {
                                scanner.readChar('S');
                                scanner.readChar('S');
                                n = DateTimeTemplate.checkUsed(n, 9, string);
                                part = Field.SSSSS;
                                break;
                            }
                            n = DateTimeTemplate.checkUsed(n, 8, string);
                            part = Field.SS;
                            break;
                        }
                        case 70: {
                            scanner.readChar('F');
                            n2 = scanner.readChar();
                            if (n2 < 49 || n2 > 57) {
                                throw DbException.get(90014, string);
                            }
                            n = DateTimeTemplate.checkUsed(n, 10, string);
                            part = Field.ff(n2 - 48);
                            break;
                        }
                        case 65: 
                        case 80: {
                            scanner.readChar('.');
                            scanner.readChar('M');
                            scanner.readChar('.');
                            n = DateTimeTemplate.checkUsed(n, 11, string);
                            part = Field.AM_PM;
                            break;
                        }
                        case 84: {
                            scanner.readChar('Z');
                            if (scanner.readCharIf('H')) {
                                n = DateTimeTemplate.checkUsed(n, 12, string);
                                part = Field.TZH;
                                break;
                            }
                            if (scanner.readCharIf('M')) {
                                n = DateTimeTemplate.checkUsed(n, 13, string);
                                part = Field.TZM;
                                break;
                            }
                            scanner.readChar('S');
                            n = DateTimeTemplate.checkUsed(n, 14, string);
                            part = Field.TZS;
                            break;
                        }
                        default: {
                            throw DbException.get(90014, string);
                        }
                    }
                    arrayList.add((Delimiter)part);
                }
                if ((n & 0x10) != 0 && (n & 0xC) != 0) break block34;
                boolean bl = (n & 0x20) != 0;
                boolean bl2 = (n & 0x800) != 0;
                if (!(bl != bl2 || (n & 0x40) != 0 && (n & 0x20) != 0 || (n & 0x200) != 0 && (n & 0x1E0) != 0 || (n & 0x4000) != 0 && (n & 0x2000) == 0) && ((n & 0x2000) == 0 || (n & 0x1000) != 0)) break block35;
            }
            throw DbException.get(90014, string);
        }
        return new DateTimeTemplate(arrayList.toArray(new Part[0]), (n & 0x1D) != 0, (n & 0xBE0) != 0, (n & 0x7000) != 0);
    }

    private static int checkUsed(int n, int n2, String string) {
        int n3 = n | 1 << n2;
        if (n == n3) {
            throw DbException.get(90014, string);
        }
        return n3;
    }

    private DateTimeTemplate(Part[] partArray, boolean bl, boolean bl2, boolean bl3) {
        this.parts = partArray;
        this.containsDate = bl;
        this.containsTime = bl2;
        this.containsTimeZone = bl3;
    }

    public String format(Value value) {
        Object object;
        int n;
        long l;
        long l2;
        switch (value.getValueType()) {
            case 0: {
                return null;
            }
            case 17: {
                if (this.containsTime || this.containsTimeZone) {
                    throw DbException.get(90014, "time or time zone fields with DATE");
                }
                l2 = ((ValueDate)value).getDateValue();
                l = 0L;
                n = 0;
                break;
            }
            case 18: {
                if (this.containsDate || this.containsTimeZone) {
                    throw DbException.get(90014, "date or time zone fields with TIME");
                }
                l2 = 0L;
                l = ((ValueTime)value).getNanos();
                n = 0;
                break;
            }
            case 19: {
                if (this.containsDate) {
                    throw DbException.get(90014, "date fields with TIME WITH TIME ZONE");
                }
                object = (ValueTimeTimeZone)value;
                l2 = 0L;
                l = ((ValueTimeTimeZone)object).getNanos();
                n = ((ValueTimeTimeZone)object).getTimeZoneOffsetSeconds();
                break;
            }
            case 20: {
                if (this.containsTimeZone) {
                    throw DbException.get(90014, "time zone fields with TIMESTAMP");
                }
                object = (ValueTimestamp)value;
                l2 = ((ValueTimestamp)object).getDateValue();
                l = ((ValueTimestamp)object).getTimeNanos();
                n = 0;
                break;
            }
            case 21: {
                object = (ValueTimestampTimeZone)value;
                l2 = ((ValueTimestampTimeZone)object).getDateValue();
                l = ((ValueTimestampTimeZone)object).getTimeNanos();
                n = ((ValueTimestampTimeZone)object).getTimeZoneOffsetSeconds();
                break;
            }
            default: {
                throw DbException.getUnsupportedException(value.getType().getTraceSQL());
            }
        }
        object = new StringBuilder();
        for (Part part : this.parts) {
            part.format((StringBuilder)object, l2, l, n);
        }
        return ((StringBuilder)object).toString();
    }

    public Value parse(String string, TypeInfo typeInfo, CastDataProvider castDataProvider) {
        switch (typeInfo.getValueType()) {
            case 17: {
                if (this.containsTime || this.containsTimeZone) {
                    throw DbException.get(90014, "time or time zone fields with DATE");
                }
                int[] nArray = DateTimeTemplate.yearMonth(castDataProvider);
                return ValueDate.fromDateValue(DateTimeTemplate.constructDate(this.parse(string, nArray[0]), nArray));
            }
            case 18: {
                if (this.containsDate || this.containsTimeZone) {
                    throw DbException.get(90014, "date or time zone fields with TIME");
                }
                return ValueTime.fromNanos(DateTimeTemplate.constructTime(this.parse(string, 0)));
            }
            case 19: {
                if (this.containsDate) {
                    throw DbException.get(90014, "date fields with TIME WITH TIME ZONE");
                }
                int[] nArray = this.parse(string, 0);
                return ValueTimeTimeZone.fromNanos(DateTimeTemplate.constructTime(nArray), DateTimeTemplate.constructOffset(nArray));
            }
            case 20: {
                if (this.containsTimeZone) {
                    throw DbException.get(90014, "time zone fields with TIMESTAMP");
                }
                int[] nArray = DateTimeTemplate.yearMonth(castDataProvider);
                int[] nArray2 = this.parse(string, nArray[0]);
                return ValueTimestamp.fromDateValueAndNanos(DateTimeTemplate.constructDate(nArray2, nArray), DateTimeTemplate.constructTime(nArray2));
            }
            case 21: {
                int[] nArray = DateTimeTemplate.yearMonth(castDataProvider);
                int[] nArray3 = this.parse(string, nArray[0]);
                return ValueTimestampTimeZone.fromDateValueAndNanos(DateTimeTemplate.constructDate(nArray3, nArray), DateTimeTemplate.constructTime(nArray3), DateTimeTemplate.constructOffset(nArray3));
            }
        }
        throw DbException.getUnsupportedException(typeInfo.getTraceSQL());
    }

    private static int[] yearMonth(CastDataProvider castDataProvider) {
        long l = castDataProvider.currentTimestamp().getDateValue();
        return new int[]{DateTimeUtils.yearFromDateValue(l), DateTimeUtils.monthFromDateValue(l)};
    }

    private int[] parse(String string, int n) {
        int[] nArray = new int[15];
        Arrays.fill(nArray, Integer.MIN_VALUE);
        Scanner scanner = new Scanner(string);
        int n2 = this.parts.length - 1;
        for (int i = 0; i <= n2; ++i) {
            Part part = this.parts[i];
            part.parse(nArray, scanner, !(i != 0 && (1 << part.type() & 0x1800) == 0 && (1 << this.parts[i - 1].type() & 0x8800) == 0 || i != n2 && part.type() != 11 && (1 << this.parts[i + 1].type() & 0x9800) == 0), n);
        }
        return nArray;
    }

    private static long constructDate(int[] nArray, int[] nArray2) {
        int n;
        int n2;
        int n3 = nArray[0];
        if (n3 == Integer.MIN_VALUE) {
            n3 = nArray[1];
        }
        if (n3 == Integer.MIN_VALUE) {
            n3 = nArray2[0];
        }
        if ((n2 = nArray[4]) != Integer.MIN_VALUE) {
            if (n2 < 1 || n2 > (DateTimeUtils.isLeapYear(n3) ? 366 : 365)) {
                throw DbException.get(90014, "Day of year " + n2);
            }
            return DateTimeUtils.dateValueFromAbsoluteDay(DateTimeUtils.absoluteDayFromYear(n3) + (long)n2 - 1L);
        }
        int n4 = nArray[2];
        if (n4 == Integer.MIN_VALUE) {
            n4 = nArray2[1];
        }
        if ((n = nArray[3]) == Integer.MIN_VALUE) {
            n = 1;
        }
        if (!DateTimeUtils.isValidDate(n3, n4, n)) {
            throw DbException.get(90014, "Invalid date, year=" + n3 + ", month=" + n4 + ", day=" + n);
        }
        return DateTimeUtils.dateValue(n3, n4, n);
    }

    private static long constructTime(int[] nArray) {
        int n;
        int n2 = nArray[9];
        if (n2 == Integer.MIN_VALUE) {
            n = nArray[6];
            if (n == Integer.MIN_VALUE) {
                n = nArray[5];
                if (n == Integer.MIN_VALUE) {
                    n = 0;
                } else {
                    if (n < 1 || n > 12) {
                        throw DbException.get(90014, "Hour(12) " + n);
                    }
                    if (n == 12) {
                        n = 0;
                    }
                    n += nArray[11] * 12;
                }
            } else if (n < 0 || n > 23) {
                throw DbException.get(90014, "Hour(24) " + n);
            }
            int n3 = nArray[7];
            if (n3 == Integer.MIN_VALUE) {
                n3 = 0;
            } else if (n3 < 0 || n3 > 59) {
                throw DbException.get(90014, "Minute " + n3);
            }
            int n4 = nArray[8];
            if (n4 == Integer.MIN_VALUE) {
                n4 = 0;
            } else if (n4 < 0 || n4 > 59) {
                throw DbException.get(90014, "Second of minute " + n4);
            }
            n2 = (n * 60 + n3) * 60 + n4;
        } else if (n2 < 0 || (long)n2 >= 86400L) {
            throw DbException.get(90014, "Second of day " + n2);
        }
        n = nArray[10];
        if (n == Integer.MIN_VALUE) {
            n = 0;
        }
        return (long)n2 * 1000000000L + (long)n;
    }

    private static int constructOffset(int[] nArray) {
        int n;
        boolean bl;
        int n2 = nArray[12];
        if (n2 == Integer.MIN_VALUE) {
            return 0;
        }
        boolean bl2 = bl = n2 < 0;
        if (bl) {
            n2 = n2 == -100 ? 0 : -n2;
        }
        if ((n = nArray[13]) == Integer.MIN_VALUE) {
            n = 0;
        } else if (n > 59) {
            throw DbException.get(90014, "Time zone minute " + n);
        }
        int n3 = nArray[14];
        if (n3 == Integer.MIN_VALUE) {
            n3 = 0;
        } else if (n3 > 59) {
            throw DbException.get(90014, "Time zone second " + n3);
        }
        int n4 = (n2 * 60 + n) * 60 + n3;
        if (n4 > 64800) {
            throw DbException.get(90014, "Time zone offset is too large");
        }
        return bl ? -n4 : n4;
    }

    private static final class Field
    extends Part {
        static final Field Y = new Field(0, 1);
        static final Field YY = new Field(0, 2);
        static final Field YYY = new Field(0, 3);
        static final Field YYYY = new Field(0, 4);
        static final Field RR = new Field(1, 2);
        static final Field RRRR = new Field(1, 4);
        static final Field MM = new Field(2, 2);
        static final Field DD = new Field(3, 2);
        static final Field DDD = new Field(4, 3);
        static final Field HH12 = new Field(5, 2);
        static final Field HH24 = new Field(6, 2);
        static final Field MI = new Field(7, 2);
        static final Field SS = new Field(8, 2);
        static final Field SSSSS = new Field(9, 5);
        private static final Field[] FF;
        static final Field AM_PM;
        static final Field TZH;
        static final Field TZM;
        static final Field TZS;
        private final int type;
        private final int digits;

        static Field ff(int n) {
            return FF[n - 1];
        }

        Field(int n, int n2) {
            this.type = n;
            this.digits = n2;
        }

        @Override
        int type() {
            return this.type;
        }

        @Override
        void format(StringBuilder stringBuilder, long l, long l2, int n) {
            switch (this.type) {
                case 0: 
                case 1: {
                    int n2 = DateTimeUtils.yearFromDateValue(l);
                    if (n2 < 0) {
                        stringBuilder.append('-');
                        n2 = -n2;
                    }
                    switch (this.digits) {
                        case 1: {
                            n2 %= 10;
                            break;
                        }
                        case 2: {
                            n2 %= 100;
                            break;
                        }
                        case 3: {
                            n2 %= 1000;
                        }
                    }
                    Field.formatLast(stringBuilder, n2, this.digits);
                    break;
                }
                case 2: {
                    StringUtils.appendTwoDigits(stringBuilder, DateTimeUtils.monthFromDateValue(l));
                    break;
                }
                case 3: {
                    StringUtils.appendTwoDigits(stringBuilder, DateTimeUtils.dayFromDateValue(l));
                    break;
                }
                case 4: {
                    StringUtils.appendZeroPadded(stringBuilder, 3, DateTimeUtils.getDayOfYear(l));
                    break;
                }
                case 5: {
                    int n3 = (int)(l2 / 3600000000000L);
                    if (n3 == 0) {
                        n3 = 12;
                    } else if (n3 > 12) {
                        n3 -= 12;
                    }
                    StringUtils.appendTwoDigits(stringBuilder, n3);
                    break;
                }
                case 6: {
                    StringUtils.appendTwoDigits(stringBuilder, (int)(l2 / 3600000000000L));
                    break;
                }
                case 7: {
                    StringUtils.appendTwoDigits(stringBuilder, (int)(l2 / 60000000000L % 60L));
                    break;
                }
                case 8: {
                    StringUtils.appendTwoDigits(stringBuilder, (int)(l2 / 1000000000L % 60L));
                    break;
                }
                case 9: {
                    StringUtils.appendZeroPadded(stringBuilder, 5, (int)(l2 / 1000000000L));
                    break;
                }
                case 10: {
                    Field.formatLast(stringBuilder, (int)(l2 % 1000000000L) / DateTimeUtils.FRACTIONAL_SECONDS_TABLE[this.digits], this.digits);
                    break;
                }
                case 11: {
                    int n4 = (int)(l2 / 3600000000000L);
                    stringBuilder.append(n4 < 12 ? "A.M." : "P.M.");
                    break;
                }
                case 12: {
                    int n5 = n / 3600;
                    if (n >= 0) {
                        stringBuilder.append('+');
                    } else {
                        n5 = -n5;
                        stringBuilder.append('-');
                    }
                    StringUtils.appendTwoDigits(stringBuilder, n5);
                    break;
                }
                case 13: {
                    StringUtils.appendTwoDigits(stringBuilder, Math.abs(n % 3600 / 60));
                    break;
                }
                case 14: {
                    StringUtils.appendTwoDigits(stringBuilder, Math.abs(n % 60));
                }
            }
        }

        private static void formatLast(StringBuilder stringBuilder, int n, int n2) {
            if (n2 == 2) {
                StringUtils.appendTwoDigits(stringBuilder, n);
            } else {
                StringUtils.appendZeroPadded(stringBuilder, n2, n);
            }
        }

        @Override
        void parse(int[] nArray, Scanner scanner, boolean bl, int n) {
            switch (this.type) {
                case 0: 
                case 1: {
                    boolean bl2 = scanner.readCharIf('-');
                    if (!bl2) {
                        scanner.readCharIf('+');
                    }
                    int n2 = scanner.readPositiveInt(this.digits, bl);
                    if (bl2) {
                        if (this.digits < 4 || this.type == 1) {
                            throw DbException.get(90014, scanner.string);
                        }
                        n2 = -n2;
                    } else if (this.digits < 4) {
                        if (this.digits == 1) {
                            if (n2 > 9) {
                                throw DbException.get(90014, scanner.string);
                            }
                            n2 += n / 10 * 10;
                        } else if (this.digits == 2) {
                            if (n2 > 99) {
                                throw DbException.get(90014, scanner.string);
                            }
                            n2 += n / 100 * 100;
                            if (this.type == 1) {
                                if (n2 > n + 50) {
                                    n2 -= 100;
                                } else if (n2 < n - 49) {
                                    n += 100;
                                }
                            }
                        } else if (this.digits == 3) {
                            if (n2 > 999) {
                                throw DbException.get(90014, scanner.string);
                            }
                            n2 += n / 1000 * 1000;
                        }
                    }
                    nArray[this.type] = n2;
                    break;
                }
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: 
                case 13: 
                case 14: {
                    nArray[this.type] = scanner.readPositiveInt(this.digits, bl);
                    break;
                }
                case 10: {
                    nArray[10] = scanner.readNanos(this.digits, bl);
                    break;
                }
                case 11: {
                    int n3;
                    if (scanner.readCharIf('A')) {
                        n3 = 0;
                    } else {
                        scanner.readChar('P');
                        n3 = 1;
                    }
                    scanner.readChar('.');
                    scanner.readChar('M');
                    scanner.readChar('.');
                    nArray[11] = n3;
                    break;
                }
                case 12: {
                    int n4;
                    boolean bl3 = scanner.readCharIf('-');
                    if (!bl3 && !scanner.readCharIf('+')) {
                        scanner.readChar(' ');
                    }
                    if ((n4 = scanner.readPositiveInt(this.digits, bl)) > 18) {
                        throw DbException.get(90014, scanner.string);
                    }
                    nArray[12] = bl3 ? (n4 == 0 ? -100 : -n4) : n4;
                }
            }
        }

        static {
            AM_PM = new Field(11, 4);
            TZH = new Field(12, 2);
            TZM = new Field(13, 2);
            TZS = new Field(14, 2);
            Field[] fieldArray = new Field[9];
            int n = 0;
            while (n < 9) {
                fieldArray[n++] = new Field(10, n);
            }
            FF = fieldArray;
        }
    }

    private static final class Delimiter
    extends Part {
        static final Delimiter MINUS_SIGN = new Delimiter('-');
        static final Delimiter PERIOD = new Delimiter('.');
        static final Delimiter SOLIDUS = new Delimiter('/');
        static final Delimiter COMMA = new Delimiter(',');
        static final Delimiter APOSTROPHE = new Delimiter('\'');
        static final Delimiter SEMICOLON = new Delimiter(';');
        static final Delimiter COLON = new Delimiter(':');
        static final Delimiter SPACE = new Delimiter(' ');
        private final char delimiter;

        private Delimiter(char c) {
            this.delimiter = c;
        }

        @Override
        int type() {
            return 15;
        }

        @Override
        public void format(StringBuilder stringBuilder, long l, long l2, int n) {
            stringBuilder.append(this.delimiter);
        }

        @Override
        public void parse(int[] nArray, Scanner scanner, boolean bl, int n) {
            scanner.readChar(this.delimiter);
        }
    }

    private static abstract class Part {
        Part() {
        }

        abstract int type();

        abstract void format(StringBuilder var1, long var2, long var4, int var6);

        abstract void parse(int[] var1, Scanner var2, boolean var3, int var4);
    }

    private static final class Scanner {
        final String string;
        private int offset;
        private final int length;

        Scanner(String string) {
            this.string = string;
            this.length = string.length();
        }

        int readChar() {
            return this.offset < this.length ? (int)this.string.charAt(this.offset++) : -1;
        }

        void readChar(char c) {
            if (this.offset >= this.length || this.string.charAt(this.offset) != c) {
                throw DbException.get(90014, this.string);
            }
            ++this.offset;
        }

        boolean readCharIf(char c) {
            if (this.offset < this.length && this.string.charAt(this.offset) == c) {
                ++this.offset;
                return true;
            }
            return false;
        }

        int readPositiveInt(int n, boolean bl) {
            int n2;
            int n3 = this.offset;
            if (bl) {
                char c;
                for (n2 = n3; n2 < this.length && (c = this.string.charAt(n2)) >= '0' && c <= '9'; ++n2) {
                }
                if (n3 == n2) {
                    throw DbException.get(90014, this.string);
                }
            } else {
                n2 = n3 + n;
                if (n2 > this.length) {
                    throw DbException.get(90014, this.string);
                }
            }
            try {
                this.offset = n2;
                return StringUtils.parseUInt31(this.string, n3, this.offset);
            }
            catch (NumberFormatException numberFormatException) {
                throw DbException.get(90014, this.string);
            }
        }

        int readNanos(int n, boolean bl) {
            int n2;
            int n3 = n2 = this.offset;
            int n4 = 0;
            int n5 = 100000000;
            if (bl) {
                char c;
                for (n3 = n2; n3 < this.length && (c = this.string.charAt(n3)) >= '0' && c <= '9'; ++n3) {
                    n4 += n5 * (c - 48);
                    n5 /= 10;
                }
                if (n2 == n3) {
                    throw DbException.get(90014, this.string);
                }
            } else {
                n3 = n2 + n;
                if (n3 > this.length) {
                    throw DbException.get(90014, this.string);
                }
                while (n2 < n3) {
                    char c = this.string.charAt(n2);
                    if (c < '0' || c > '9') {
                        throw DbException.get(90014, this.string);
                    }
                    n4 += n5 * (c - 48);
                    n5 /= 10;
                    ++n2;
                }
            }
            this.offset = n3;
            return n4;
        }
    }

    public static final class FieldType {
        static final int YEAR = 0;
        static final int ROUNDED_YEAR = 1;
        static final int MONTH = 2;
        static final int DAY_OF_MONTH = 3;
        static final int DAY_OF_YEAR = 4;
        static final int HOUR12 = 5;
        static final int HOUR24 = 6;
        static final int MINUTE = 7;
        static final int SECOND_OF_MINUTE = 8;
        static final int SECOND_OF_DAY = 9;
        static final int FRACTION = 10;
        static final int AMPM = 11;
        static final int TIME_ZONE_HOUR = 12;
        static final int TIME_ZONE_MINUTE = 13;
        static final int TIME_ZONE_SECOND = 14;
        static final int DELIMITER = 15;
    }
}

