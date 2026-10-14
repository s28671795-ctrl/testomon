/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.postgres;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jooq.EnumType;
import org.jooq.Record;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.Internal;
import org.jooq.tools.StringUtils;
import org.jooq.types.DayToSecond;
import org.jooq.types.YearToMonth;
import org.jooq.types.YearToSecond;
import org.jooq.util.postgres.PGInterval;

@ApiStatus.Internal
public class PostgresUtils {
    private static final String POSTGRESQL_HEX_STRING_PREFIX = "\\x";

    public static byte[] toBytes(String string) {
        if (string.startsWith(POSTGRESQL_HEX_STRING_PREFIX)) {
            return PostgresUtils.toBytesFromHexEncoding(string);
        }
        return PostgresUtils.toBytesFromOctalEncoding(string);
    }

    private static byte[] toBytesFromOctalEncoding(String string) {
        StringReader reader = new StringReader(string);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            PostgresUtils.convertOctalToBytes(reader, bytes);
            return bytes.toByteArray();
        }
        catch (IOException x) {
            throw new DataTypeException("failed to parse octal hex string: " + x.getMessage(), x);
        }
    }

    private static void convertOctalToBytes(Reader reader, OutputStream bytes) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1) {
            if (ch == 92) {
                ch = reader.read();
                if (ch == -1) {
                    throw new DataTypeException("unexpected end of stream after initial backslash");
                }
                if (ch == 92) {
                    bytes.write(92);
                    continue;
                }
                int val = PostgresUtils.octalValue(ch);
                ch = reader.read();
                if (ch == -1) {
                    throw new DataTypeException("unexpected end of octal value");
                }
                val <<= 3;
                val += PostgresUtils.octalValue(ch);
                ch = reader.read();
                if (ch == -1) {
                    throw new DataTypeException("unexpected end of octal value");
                }
                val <<= 3;
                bytes.write(val += PostgresUtils.octalValue(ch));
                continue;
            }
            bytes.write(ch);
        }
    }

    private static byte[] toBytesFromHexEncoding(String string) {
        String hex = string.substring(POSTGRESQL_HEX_STRING_PREFIX.length());
        StringReader input = new StringReader(hex);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(hex.length() / 2);
        try {
            int hexDigit;
            while ((hexDigit = input.read()) != -1) {
                int byteValue = PostgresUtils.hexValue(hexDigit) << 4;
                hexDigit = input.read();
                if (hexDigit != -1) {
                    bytes.write(byteValue += PostgresUtils.hexValue(hexDigit));
                    continue;
                }
                break;
            }
        }
        catch (IOException e) {
            throw new DataTypeException("Error while decoding hex string", e);
        }
        input.close();
        return bytes.toByteArray();
    }

    private static int hexValue(int hexDigit) {
        if (hexDigit >= 48 && hexDigit <= 57) {
            return hexDigit - 48;
        }
        if (hexDigit >= 97 && hexDigit <= 102) {
            return hexDigit - 97 + 10;
        }
        if (hexDigit >= 65 && hexDigit <= 70) {
            return hexDigit - 65 + 10;
        }
        throw new DataTypeException("unknown postgresql character format for hexValue: " + hexDigit);
    }

    private static int octalValue(int octalDigit) {
        if (octalDigit < 48 || octalDigit > 55) {
            throw new DataTypeException("unknown postgresql character format for octalValue: " + octalDigit);
        }
        return octalDigit - 48;
    }

    public static PGInterval toPGInterval(DayToSecond interval) {
        return new PGInterval(0, 0, interval.getSign() * interval.getDays(), interval.getSign() * interval.getHours(), interval.getSign() * interval.getMinutes(), (double)(interval.getSign() * interval.getSeconds()) + (double)(interval.getSign() * interval.getNano()) / 1.0E9);
    }

    public static PGInterval toPGInterval(YearToSecond interval) {
        return new PGInterval(interval.getSign() * interval.getYears(), interval.getSign() * interval.getMonths(), interval.getSign() * interval.getDays(), interval.getSign() * interval.getHours(), interval.getSign() * interval.getMinutes(), (double)(interval.getSign() * interval.getSeconds()) + (double)(interval.getSign() * interval.getNano()) / 1.0E9);
    }

    public static PGInterval toPGInterval(YearToMonth interval) {
        return new PGInterval(interval.getSign() * interval.getYears(), interval.getSign() * interval.getMonths(), 0, 0, 0, 0.0);
    }

    public static DayToSecond toDayToSecond(Object pgInterval) {
        if (pgInterval == null) {
            return null;
        }
        if (pgInterval instanceof PGInterval) {
            PGInterval i = (PGInterval)pgInterval;
            return PostgresUtils.toDayToSecond(i);
        }
        return PostgresUtils.toDayToSecond(new PGInterval(pgInterval.toString()));
    }

    public static DayToSecond toDayToSecond(PGInterval pgInterval) {
        boolean negative = pgInterval.toString().contains("-");
        if (negative) {
            pgInterval.scale(-1);
        }
        Double seconds = pgInterval.getSeconds();
        DayToSecond result = new DayToSecond(pgInterval.getDays(), pgInterval.getHours(), pgInterval.getMinutes(), seconds.intValue(), (int)(1.0E9 * (seconds - (double)seconds.intValue())));
        if (negative) {
            result = result.neg();
        }
        return result;
    }

    public static YearToMonth toYearToMonth(Object pgInterval) {
        if (pgInterval == null) {
            return null;
        }
        if (pgInterval instanceof PGInterval) {
            PGInterval i = (PGInterval)pgInterval;
            return PostgresUtils.toYearToMonth(i);
        }
        return PostgresUtils.toYearToMonth(new PGInterval(pgInterval.toString()));
    }

    public static YearToMonth toYearToMonth(PGInterval pgInterval) {
        boolean negative = pgInterval.toString().contains("-");
        if (negative) {
            pgInterval.scale(-1);
        }
        YearToMonth result = new YearToMonth(pgInterval.getYears(), pgInterval.getMonths());
        if (negative) {
            result = result.neg();
        }
        return result;
    }

    public static YearToSecond toYearToSecond(Object pgInterval) {
        return new YearToSecond(PostgresUtils.toYearToMonth(pgInterval), PostgresUtils.toDayToSecond(pgInterval));
    }

    public static List<String> toPGArray(String input) {
        if ("{}".equals(input)) {
            return Collections.emptyList();
        }
        return PostgresUtils.toPGObjectOrArray(input, '{', '}');
    }

    public static List<String> toPGObject(String input) {
        return PostgresUtils.toPGObjectOrArray(input, '(', ')');
    }

    private static List<String> toPGObjectOrArray(String input, char open, char close) {
        ArrayList<String> values2 = new ArrayList<String>();
        PGState state = PGState.PG_OBJECT_INIT;
        int nestLevel = 0;
        StringBuilder sb = null;
        block9: for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            switch (state) {
                case PG_OBJECT_INIT: {
                    if (c != open) continue block9;
                    state = PGState.PG_OBJECT_BEFORE_VALUE;
                    continue block9;
                }
                case PG_OBJECT_BEFORE_VALUE: {
                    sb = new StringBuilder();
                    if (c == ',') {
                        values2.add(null);
                        state = PGState.PG_OBJECT_BEFORE_VALUE;
                        continue block9;
                    }
                    if (c == close) {
                        values2.add(null);
                        state = PGState.PG_OBJECT_END;
                        continue block9;
                    }
                    if (c == '\"') {
                        state = PGState.PG_OBJECT_QUOTED_VALUE;
                        continue block9;
                    }
                    if ((c == 'n' || c == 'N') && i + 4 < input.length() && open == '{' && input.substring(i, i + 4).equalsIgnoreCase("null")) {
                        values2.add(null);
                        i += 3;
                        state = PGState.PG_OBJECT_AFTER_VALUE;
                        continue block9;
                    }
                    if (c == open) {
                        sb.append(c);
                        state = PGState.PG_OBJECT_NESTED_VALUE;
                        ++nestLevel;
                        continue block9;
                    }
                    sb.append(c);
                    state = PGState.PG_OBJECT_UNQUOTED_VALUE;
                    continue block9;
                }
                case PG_OBJECT_QUOTED_VALUE: {
                    char n;
                    if (c == '\"') {
                        if (input.charAt(i + 1) == '\"') {
                            sb.append(c);
                            ++i;
                            continue block9;
                        }
                        values2.add(sb.toString());
                        state = PGState.PG_OBJECT_AFTER_VALUE;
                        continue block9;
                    }
                    if (c == '\\') {
                        n = input.charAt(i + 1);
                        if (n == '\\' || n == '\"') {
                            sb.append(n);
                            ++i;
                            continue block9;
                        }
                        sb.append(c);
                        continue block9;
                    }
                    sb.append(c);
                    continue block9;
                }
                case PG_OBJECT_UNQUOTED_VALUE: {
                    if (c == close) {
                        values2.add(sb.toString());
                        state = PGState.PG_OBJECT_END;
                        continue block9;
                    }
                    if (c == ',') {
                        values2.add(sb.toString());
                        state = PGState.PG_OBJECT_BEFORE_VALUE;
                        continue block9;
                    }
                    sb.append(c);
                    continue block9;
                }
                case PG_OBJECT_NESTED_VALUE: {
                    if (c == close) {
                        sb.append(c);
                        if (--nestLevel != 0) continue block9;
                        values2.add(sb.toString());
                        state = PGState.PG_OBJECT_AFTER_VALUE;
                        continue block9;
                    }
                    if (c == open) {
                        ++nestLevel;
                        sb.append(c);
                        continue block9;
                    }
                    if (c == '\"') {
                        state = PGState.PG_OBJECT_NESTED_QUOTED_VALUE;
                        sb.append(c);
                        continue block9;
                    }
                    sb.append(c);
                    continue block9;
                }
                case PG_OBJECT_NESTED_QUOTED_VALUE: {
                    char n;
                    if (c == '\"') {
                        if (input.charAt(i + 1) == '\"') {
                            sb.append(c);
                            sb.append(c);
                            ++i;
                            continue block9;
                        }
                        sb.append(c);
                        state = PGState.PG_OBJECT_NESTED_VALUE;
                        continue block9;
                    }
                    if (c == '\\') {
                        n = input.charAt(i + 1);
                        if (n == '\\' || n == '\"') {
                            sb.append(c);
                            sb.append(n);
                            ++i;
                            continue block9;
                        }
                        sb.append(c);
                        continue block9;
                    }
                    sb.append(c);
                    continue block9;
                }
                case PG_OBJECT_AFTER_VALUE: {
                    if (c == close) {
                        state = PGState.PG_OBJECT_END;
                        continue block9;
                    }
                    if (c != ',') continue block9;
                    state = PGState.PG_OBJECT_BEFORE_VALUE;
                }
            }
        }
        return values2;
    }

    public static String toPGArrayString(Object[] value) {
        return PostgresUtils.toPGArrayString0(value, new StringBuilder()).toString();
    }

    private static StringBuilder toPGArrayString0(Object[] value, StringBuilder sb) {
        sb.append("{");
        String separator = "";
        for (Object o : value) {
            sb.append(separator);
            if (o == null) {
                sb.append(o);
            } else if (o instanceof Number) {
                sb.append(PostgresUtils.toPGString(o));
            } else if (o instanceof byte[]) {
                PostgresUtils.toPGString0((byte[])o, sb);
            } else if (o instanceof Object[]) {
                Object[] a = (Object[])o;
                if (!PostgresUtils.isDeepEmpty(a)) {
                    PostgresUtils.toPGArrayString0(a, sb);
                }
            } else {
                sb.append("\"").append(StringUtils.replace(StringUtils.replace(PostgresUtils.toPGString(o), "\\", "\\\\"), "\"", "\\\"")).append("\"");
            }
            separator = ",";
        }
        sb.append("}");
        return sb;
    }

    private static boolean isDeepEmpty(Object[] a) {
        Object object;
        if (a.length == 0) {
            return true;
        }
        if (a.length == 1 && (object = a[0]) instanceof Object[]) {
            Object[] b = (Object[])object;
            return PostgresUtils.isDeepEmpty(b);
        }
        return false;
    }

    public static String toPGString(Object o) {
        if (o instanceof byte[]) {
            return PostgresUtils.toPGString((byte[])o);
        }
        if (o instanceof Object[]) {
            return PostgresUtils.toPGArrayString((Object[])o);
        }
        if (o instanceof Record) {
            return PostgresUtils.toPGString((Record)o);
        }
        if (o instanceof EnumType) {
            return ((EnumType)o).getLiteral();
        }
        return String.valueOf(o);
    }

    public static String toPGString(Record r) {
        return PostgresUtils.toPGString0(r, new StringBuilder()).toString();
    }

    private static StringBuilder toPGString0(Record r, StringBuilder sb) {
        sb.append("(");
        String separator = "";
        for (int i = 0; i < r.size(); ++i) {
            Object a = r.field(i).getConverter().to(r.get(i), Internal.converterContext());
            sb.append(separator);
            if (a != null) {
                if (a instanceof byte[]) {
                    PostgresUtils.toPGString0((byte[])a, sb);
                } else {
                    sb.append("\"").append(StringUtils.replace(StringUtils.replace(PostgresUtils.toPGString(a), "\\", "\\\\"), "\"", "\\\"")).append("\"");
                }
            }
            separator = ",";
        }
        sb.append(")");
        return sb;
    }

    public static String toPGString(byte[] binary) {
        return PostgresUtils.toPGString0(binary, new StringBuilder()).toString();
    }

    private static StringBuilder toPGString0(byte[] binary, StringBuilder sb) {
        for (byte b : binary) {
            sb.append("\\\\");
            sb.append(StringUtils.leftPad(Integer.toOctalString(b & 0xFF), 3, '0'));
        }
        return sb;
    }

    static enum PGState {
        PG_OBJECT_INIT,
        PG_OBJECT_BEFORE_VALUE,
        PG_OBJECT_QUOTED_VALUE,
        PG_OBJECT_UNQUOTED_VALUE,
        PG_OBJECT_NESTED_VALUE,
        PG_OBJECT_NESTED_QUOTED_VALUE,
        PG_OBJECT_AFTER_VALUE,
        PG_OBJECT_END;

    }
}

