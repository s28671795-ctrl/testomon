/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.IntPredicate;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.util.Utils;

public class StringUtils {
    private static SoftReference<String[]> softCache;
    private static long softCacheCreatedNs;
    private static final char[] HEX;
    private static final int[] HEX_DECODE;
    private static final int TO_UPPER_CACHE_LENGTH = 2048;
    private static final int TO_UPPER_CACHE_MAX_ENTRY_LENGTH = 64;
    private static final String[][] TO_UPPER_CACHE;

    private StringUtils() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String[] getCache() {
        String[] stringArray;
        if (softCache != null && (stringArray = softCache.get()) != null) {
            return stringArray;
        }
        long l = System.nanoTime();
        if (softCacheCreatedNs != 0L && l - softCacheCreatedNs < TimeUnit.SECONDS.toNanos(5L)) {
            return null;
        }
        try {
            stringArray = new String[SysProperties.OBJECT_CACHE_SIZE];
            softCache = new SoftReference<String[]>(stringArray);
            String[] stringArray2 = stringArray;
            return stringArray2;
        }
        finally {
            softCacheCreatedNs = System.nanoTime();
        }
    }

    public static String toUpperEnglish(String string) {
        if (string.length() > 64) {
            return string.toUpperCase(Locale.ENGLISH);
        }
        int n = string.hashCode() & 0x7FF;
        String[] stringArray = TO_UPPER_CACHE[n];
        if (stringArray != null && stringArray[0].equals(string)) {
            return stringArray[1];
        }
        String string2 = string.toUpperCase(Locale.ENGLISH);
        stringArray = new String[]{string, string2};
        StringUtils.TO_UPPER_CACHE[n] = stringArray;
        return string2;
    }

    public static String toLowerEnglish(String string) {
        return string.toLowerCase(Locale.ENGLISH);
    }

    public static String quoteStringSQL(String string) {
        if (string == null) {
            return "NULL";
        }
        return StringUtils.quoteStringSQL(new StringBuilder(string.length() + 2), string).toString();
    }

    public static StringBuilder quoteStringSQL(StringBuilder stringBuilder, String string) {
        if (string == null) {
            return stringBuilder.append("NULL");
        }
        return StringUtils.quoteIdentifierOrLiteral(stringBuilder, string, '\'');
    }

    public static String decodeUnicodeStringSQL(String string, int n) {
        int n2 = string.length();
        StringBuilder stringBuilder = new StringBuilder(n2);
        int n3 = 0;
        while (n3 < n2) {
            int n4 = string.codePointAt(n3);
            n3 += Character.charCount(n4);
            if (n4 == n) {
                if (n3 >= n2) {
                    throw StringUtils.getFormatException(string, n3);
                }
                n4 = string.codePointAt(n3);
                if (n4 == n) {
                    n3 += Character.charCount(n4);
                } else {
                    if (n3 + 4 > n2) {
                        throw StringUtils.getFormatException(string, n3);
                    }
                    char c = string.charAt(n3);
                    try {
                        if (c == '+') {
                            if (n3 + 7 > n2) {
                                throw StringUtils.getFormatException(string, n3);
                            }
                            n4 = Integer.parseUnsignedInt(string.substring(n3 + 1, n3 += 7), 16);
                        } else {
                            n4 = Integer.parseUnsignedInt(string.substring(n3, n3 += 4), 16);
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw StringUtils.getFormatException(string, n3);
                    }
                }
            }
            stringBuilder.appendCodePoint(n4);
        }
        return stringBuilder.toString();
    }

    public static String javaEncode(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        StringUtils.javaEncode(string, stringBuilder, false);
        return stringBuilder.toString();
    }

    public static void javaEncode(String string, StringBuilder stringBuilder, boolean bl) {
        int n = string.length();
        block9: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '\t': {
                    stringBuilder.append("\\t");
                    continue block9;
                }
                case '\n': {
                    stringBuilder.append("\\n");
                    continue block9;
                }
                case '\f': {
                    stringBuilder.append("\\f");
                    continue block9;
                }
                case '\r': {
                    stringBuilder.append("\\r");
                    continue block9;
                }
                case '\"': {
                    stringBuilder.append("\\\"");
                    continue block9;
                }
                case '\'': {
                    if (bl) {
                        stringBuilder.append('\'');
                    }
                    stringBuilder.append('\'');
                    continue block9;
                }
                case '\\': {
                    stringBuilder.append("\\\\");
                    continue block9;
                }
                default: {
                    if (c >= ' ' && c < '\u0080') {
                        stringBuilder.append(c);
                        continue block9;
                    }
                    stringBuilder.append("\\u").append(HEX[c >>> 12]).append(HEX[c >>> 8 & 0xF]).append(HEX[c >>> 4 & 0xF]).append(HEX[c & 0xF]);
                }
            }
        }
    }

    public static String addAsterisk(String string, int n) {
        if (string != null) {
            int n2 = string.length();
            n = Math.min(n, n2);
            string = new StringBuilder(n2 + 3).append(string, 0, n).append("[*]").append(string, n, n2).toString();
        }
        return string;
    }

    private static DbException getFormatException(String string, int n) {
        return DbException.get(90095, StringUtils.addAsterisk(string, n));
    }

    public static String javaDecode(String string) {
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        block17: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '\\') {
                if (i + 1 >= string.length()) {
                    throw StringUtils.getFormatException(string, i);
                }
                c = string.charAt(++i);
                switch (c) {
                    case 't': {
                        stringBuilder.append('\t');
                        continue block17;
                    }
                    case 'r': {
                        stringBuilder.append('\r');
                        continue block17;
                    }
                    case 'n': {
                        stringBuilder.append('\n');
                        continue block17;
                    }
                    case 'b': {
                        stringBuilder.append('\b');
                        continue block17;
                    }
                    case 'f': {
                        stringBuilder.append('\f');
                        continue block17;
                    }
                    case '#': {
                        stringBuilder.append('#');
                        continue block17;
                    }
                    case '=': {
                        stringBuilder.append('=');
                        continue block17;
                    }
                    case ':': {
                        stringBuilder.append(':');
                        continue block17;
                    }
                    case '\"': {
                        stringBuilder.append('\"');
                        continue block17;
                    }
                    case '\\': {
                        stringBuilder.append('\\');
                        continue block17;
                    }
                    case 'u': {
                        if (i + 4 >= n) {
                            throw StringUtils.getFormatException(string, i);
                        }
                        try {
                            c = (char)Integer.parseInt(string.substring(i + 1, i + 5), 16);
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw StringUtils.getFormatException(string, i);
                        }
                        i += 4;
                        stringBuilder.append(c);
                        continue block17;
                    }
                    default: {
                        if (c >= '0' && c <= '9' && i + 2 < n) {
                            try {
                                c = (char)Integer.parseInt(string.substring(i, i + 3), 8);
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw StringUtils.getFormatException(string, i);
                            }
                            i += 2;
                            stringBuilder.append(c);
                            continue block17;
                        }
                        throw StringUtils.getFormatException(string, i);
                    }
                }
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String quoteJavaString(String string) {
        if (string == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + 2).append('\"');
        StringUtils.javaEncode(string, stringBuilder, false);
        return stringBuilder.append('\"').toString();
    }

    public static String quoteJavaStringArray(String[] stringArray) {
        if (stringArray == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder("new String[]{");
        for (int i = 0; i < stringArray.length; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(StringUtils.quoteJavaString(stringArray[i]));
        }
        return stringBuilder.append('}').toString();
    }

    public static String quoteJavaIntArray(int[] nArray) {
        if (nArray == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder("new int[]{");
        for (int i = 0; i < nArray.length; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(nArray[i]);
        }
        return stringBuilder.append('}').toString();
    }

    public static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        }
        catch (Exception exception) {
            throw DbException.convert(exception);
        }
    }

    public static String urlDecode(String string) {
        int n = string.length();
        byte[] byArray = new byte[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '+') {
                byArray[n2++] = 32;
                continue;
            }
            if (c == '%') {
                byArray[n2++] = (byte)Integer.parseInt(string.substring(i + 1, i + 3), 16);
                i += 2;
                continue;
            }
            if (c <= '\u007f' && c >= ' ') {
                byArray[n2++] = (byte)c;
                continue;
            }
            throw new IllegalArgumentException("Unexpected char " + c + " decoding " + string);
        }
        return new String(byArray, 0, n2, StandardCharsets.UTF_8);
    }

    public static String[] arraySplit(String string, char c, boolean bl) {
        if (string == null) {
            return null;
        }
        int n = string.length();
        if (n == 0) {
            return new String[0];
        }
        ArrayList<String> arrayList = Utils.newSmallArrayList();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c2 = string.charAt(i);
            if (c2 == c) {
                String string2 = stringBuilder.toString();
                arrayList.add(bl ? string2.trim() : string2);
                stringBuilder.setLength(0);
                continue;
            }
            if (c2 == '\\' && i < n - 1) {
                stringBuilder.append(string.charAt(++i));
                continue;
            }
            stringBuilder.append(c2);
        }
        String string3 = stringBuilder.toString();
        arrayList.add(bl ? string3.trim() : string3);
        return arrayList.toArray(new String[0]);
    }

    public static String arrayCombine(String[] stringArray, char c) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringArray.length; ++i) {
            String string;
            if (i > 0) {
                stringBuilder.append(c);
            }
            if ((string = stringArray[i]) == null) continue;
            int n = string.length();
            for (int j = 0; j < n; ++j) {
                char c2 = string.charAt(j);
                if (c2 == '\\' || c2 == c) {
                    stringBuilder.append('\\');
                }
                stringBuilder.append(c2);
            }
        }
        return stringBuilder.toString();
    }

    public static String xmlAttr(String string, String string2) {
        return " " + string + "=\"" + StringUtils.xmlText(string2) + "\"";
    }

    public static String xmlNode(String string, String string2, String string3) {
        return StringUtils.xmlNode(string, string2, string3, true);
    }

    public static String xmlNode(String string, String string2, String string3, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('<').append(string);
        if (string2 != null) {
            stringBuilder.append(string2);
        }
        if (string3 == null) {
            stringBuilder.append("/>\n");
            return stringBuilder.toString();
        }
        stringBuilder.append('>');
        if (bl && string3.indexOf(10) >= 0) {
            stringBuilder.append('\n');
            StringUtils.indent(stringBuilder, string3, 4, true);
        } else {
            stringBuilder.append(string3);
        }
        stringBuilder.append("</").append(string).append(">\n");
        return stringBuilder.toString();
    }

    public static StringBuilder indent(StringBuilder stringBuilder, String string, int n, boolean bl) {
        int n2 = 0;
        int n3 = string.length();
        while (n2 < n3) {
            int n4;
            for (n4 = 0; n4 < n; ++n4) {
                stringBuilder.append(' ');
            }
            n4 = string.indexOf(10, n2);
            n4 = n4 < 0 ? n3 : n4 + 1;
            stringBuilder.append(string, n2, n4);
            n2 = n4;
        }
        if (bl && !string.endsWith("\n")) {
            stringBuilder.append('\n');
        }
        return stringBuilder;
    }

    public static String xmlComment(String string) {
        int n = 0;
        while ((n = string.indexOf("--", n)) >= 0) {
            string = string.substring(0, n + 1) + " " + string.substring(n + 1);
        }
        if (string.indexOf(10) >= 0) {
            StringBuilder stringBuilder = new StringBuilder(string.length() + 18).append("<!--\n");
            return StringUtils.indent(stringBuilder, string, 4, true).append("-->\n").toString();
        }
        return "<!-- " + string + " -->\n";
    }

    public static String xmlCData(String string) {
        if (string.contains("]]>")) {
            return StringUtils.xmlText(string);
        }
        boolean bl = string.endsWith("\n");
        string = "<![CDATA[" + string + "]]>";
        return bl ? string + "\n" : string;
    }

    public static String xmlStartDoc() {
        return "<?xml version=\"1.0\"?>\n";
    }

    public static String xmlText(String string) {
        return StringUtils.xmlText(string, false);
    }

    public static String xmlText(String string, boolean bl) {
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        block9: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '<': {
                    stringBuilder.append("&lt;");
                    continue block9;
                }
                case '>': {
                    stringBuilder.append("&gt;");
                    continue block9;
                }
                case '&': {
                    stringBuilder.append("&amp;");
                    continue block9;
                }
                case '\'': {
                    stringBuilder.append("&#39;");
                    continue block9;
                }
                case '\"': {
                    stringBuilder.append("&quot;");
                    continue block9;
                }
                case '\n': 
                case '\r': {
                    if (bl) {
                        stringBuilder.append("&#x").append(Integer.toHexString(c)).append(';');
                        continue block9;
                    }
                    stringBuilder.append(c);
                    continue block9;
                }
                case '\t': {
                    stringBuilder.append(c);
                    continue block9;
                }
                default: {
                    if (c < ' ' || c > '\u007f') {
                        stringBuilder.append("&#x").append(Integer.toHexString(c)).append(';');
                        continue block9;
                    }
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String replaceAll(String string, String string2, String string3) {
        int n = string.indexOf(string2);
        if (n < 0 || string2.isEmpty()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() - string2.length() + string3.length());
        int n2 = 0;
        do {
            stringBuilder.append(string, n2, n).append(string3);
        } while ((n = string.indexOf(string2, n2 = n + string2.length())) >= 0);
        stringBuilder.append(string, n2, string.length());
        return stringBuilder.toString();
    }

    public static String quoteIdentifier(String string) {
        return StringUtils.quoteIdentifierOrLiteral(new StringBuilder(string.length() + 2), string, '\"').toString();
    }

    public static StringBuilder quoteIdentifier(StringBuilder stringBuilder, String string) {
        return StringUtils.quoteIdentifierOrLiteral(stringBuilder, string, '\"');
    }

    private static StringBuilder quoteIdentifierOrLiteral(StringBuilder stringBuilder, String string, char c) {
        int n = stringBuilder.length();
        stringBuilder.append(c);
        int n2 = 0;
        int n3 = string.length();
        while (n2 < n3) {
            int n4 = string.codePointAt(n2);
            n2 += Character.charCount(n4);
            if (n4 < 32 || n4 > 127) {
                stringBuilder.setLength(n);
                stringBuilder.append("U&").append(c);
                n2 = 0;
                while (n2 < n3) {
                    n4 = string.codePointAt(n2);
                    n2 += Character.charCount(n4);
                    if (n4 >= 32 && n4 < 127) {
                        char c2 = (char)n4;
                        if (c2 == c || c2 == '\\') {
                            stringBuilder.append(c2);
                        }
                        stringBuilder.append(c2);
                        continue;
                    }
                    if (n4 <= 65535) {
                        StringUtils.appendHex(stringBuilder.append('\\'), n4, 2);
                        continue;
                    }
                    StringUtils.appendHex(stringBuilder.append("\\+"), n4, 3);
                }
                break;
            }
            if (n4 == c) {
                stringBuilder.append(c);
            }
            stringBuilder.append((char)n4);
        }
        return stringBuilder.append(c);
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String pad(String string, int n, String string2, boolean bl) {
        if (n < 0) {
            n = 0;
        }
        if (n < string.length()) {
            return string.substring(0, n);
        }
        if (n == string.length()) {
            return string;
        }
        int n2 = string2 == null || string2.isEmpty() ? 32 : string2.codePointAt(0);
        StringBuilder stringBuilder = new StringBuilder(n);
        n -= string.length();
        if (Character.isSupplementaryCodePoint(n2)) {
            n >>= 1;
        }
        if (bl) {
            stringBuilder.append(string);
        }
        for (int i = 0; i < n; ++i) {
            stringBuilder.appendCodePoint(n2);
        }
        if (!bl) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static char[] cloneCharArray(char[] cArray) {
        if (cArray == null) {
            return null;
        }
        int n = cArray.length;
        if (n == 0) {
            return cArray;
        }
        return Arrays.copyOf(cArray, n);
    }

    public static String trim(String string, boolean bl, boolean bl2, String string2) {
        IntPredicate intPredicate;
        if (string2 == null || string2.isEmpty()) {
            return StringUtils.trim(string, bl, bl2, ' ');
        }
        int n = string2.length();
        if (n == 1) {
            return StringUtils.trim(string, bl, bl2, string2.charAt(0));
        }
        int n4 = string2.codePointCount(0, n);
        if (n4 <= 2) {
            int n5;
            int n6 = string2.codePointAt(0);
            intPredicate = n4 > 1 && n6 != (n5 = string2.codePointAt(Character.charCount(n6))) ? n3 -> n3 == n6 || n3 == n5 : n2 -> n2 == n6;
        } else {
            HashSet hashSet = new HashSet();
            string2.codePoints().forEach(hashSet::add);
            intPredicate = hashSet::contains;
        }
        return StringUtils.trim(string, bl, bl2, intPredicate);
    }

    private static String trim(String string, boolean bl, boolean bl2, IntPredicate intPredicate) {
        int n;
        int n2;
        int n3 = string.length();
        if (bl) {
            for (n = 0; n < n3 && intPredicate.test(n2 = string.codePointAt(n)); n += Character.charCount(n2)) {
            }
        }
        if (bl2) {
            while (n3 > n && intPredicate.test(n2 = string.codePointBefore(n3))) {
                n3 -= Character.charCount(n2);
            }
        }
        return string.substring(n, n3);
    }

    public static String trim(String string, boolean bl, boolean bl2, char c) {
        int n;
        int n2 = string.length();
        if (bl) {
            for (n = 0; n < n2 && string.charAt(n) == c; ++n) {
            }
        }
        if (bl2) {
            while (n2 > n && string.charAt(n2 - 1) == c) {
                --n2;
            }
        }
        return string.substring(n, n2);
    }

    public static String trimSubstring(String string, int n) {
        return StringUtils.trimSubstring(string, n, string.length());
    }

    public static String trimSubstring(String string, int n, int n2) {
        while (n < n2 && string.charAt(n) <= ' ') {
            ++n;
        }
        while (n < n2 && string.charAt(n2 - 1) <= ' ') {
            --n2;
        }
        return string.substring(n, n2);
    }

    public static StringBuilder trimSubstring(StringBuilder stringBuilder, String string, int n, int n2) {
        while (n < n2 && string.charAt(n) <= ' ') {
            ++n;
        }
        while (n < n2 && string.charAt(n2 - 1) <= ' ') {
            --n2;
        }
        return stringBuilder.append(string, n, n2);
    }

    public static String truncateString(String string, int n) {
        if (string.length() > n) {
            string = n > 0 ? string.substring(0, Character.isSurrogatePair(string.charAt(n - 1), string.charAt(n)) ? n - 1 : n) : "";
        }
        return string;
    }

    public static String cache(String string) {
        if (!SysProperties.OBJECT_CACHE) {
            return string;
        }
        if (string == null) {
            return string;
        }
        if (string.isEmpty()) {
            return "";
        }
        String[] stringArray = StringUtils.getCache();
        if (stringArray != null) {
            int n = string.hashCode();
            int n2 = n & SysProperties.OBJECT_CACHE_SIZE - 1;
            String string2 = stringArray[n2];
            if (string.equals(string2)) {
                return string2;
            }
            stringArray[n2] = string;
        }
        return string;
    }

    public static void clearCache() {
        softCache = null;
    }

    public static int parseUInt31(String string, int n, int n2) {
        if (n2 > string.length() || n < 0 || n > n2) {
            throw new IndexOutOfBoundsException();
        }
        if (n == n2) {
            throw new NumberFormatException("");
        }
        int n3 = 0;
        for (int i = n; i < n2; ++i) {
            char c = string.charAt(i);
            if (c < '0' || c > '9' || n3 > 0xCCCCCCC) {
                throw new NumberFormatException(string.substring(n, n2));
            }
            if ((n3 = n3 * 10 + c - 48) >= 0) continue;
            throw new NumberFormatException(string.substring(n, n2));
        }
        return n3;
    }

    public static byte[] convertHexToBytes(String string) {
        int n = string.length();
        if (n % 2 != 0) {
            throw DbException.get(90003, string);
        }
        byte[] byArray = new byte[n /= 2];
        int n2 = 0;
        int[] nArray = HEX_DECODE;
        try {
            for (int i = 0; i < n; ++i) {
                int n3 = nArray[string.charAt(i + i)] << 4 | nArray[string.charAt(i + i + 1)];
                n2 |= n3;
                byArray[i] = (byte)n3;
            }
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw DbException.get(90004, string);
        }
        if ((n2 & 0xFFFFFF00) != 0) {
            throw DbException.get(90004, string);
        }
        return byArray;
    }

    public static ByteArrayOutputStream convertHexWithSpacesToBytes(ByteArrayOutputStream byteArrayOutputStream, String string, int n, int n2) {
        if (byteArrayOutputStream == null) {
            byteArrayOutputStream = new ByteArrayOutputStream(n2 - n >>> 1);
        }
        int n3 = 0;
        int[] nArray = HEX_DECODE;
        try {
            int n4 = n;
            while (n4 < n2) {
                char c;
                char c2;
                if ((c2 = string.charAt(n4++)) == ' ') continue;
                do {
                    if (n4 < n2) continue;
                    if (((n3 | nArray[c2]) & 0xFFFFFF00) != 0) {
                        throw StringUtils.getHexStringException(90004, string, n, n2);
                    }
                    throw StringUtils.getHexStringException(90003, string, n, n2);
                } while ((c = string.charAt(n4++)) == ' ');
                int n5 = nArray[c2] << 4 | nArray[c];
                n3 |= n5;
                byteArrayOutputStream.write(n5);
            }
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw StringUtils.getHexStringException(90004, string, n, n2);
        }
        if ((n3 & 0xFFFFFF00) != 0) {
            throw StringUtils.getHexStringException(90004, string, n, n2);
        }
        return byteArrayOutputStream;
    }

    private static DbException getHexStringException(int n, String string, int n2, int n3) {
        return DbException.get(n, string.substring(n2, n3));
    }

    public static String convertBytesToHex(byte[] byArray) {
        return StringUtils.convertBytesToHex(byArray, byArray.length);
    }

    public static String convertBytesToHex(byte[] byArray, int n) {
        byte[] byArray2 = new byte[n * 2];
        char[] cArray = HEX;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = byArray[i] & 0xFF;
            byArray2[n2++] = (byte)cArray[n3 >> 4];
            byArray2[n2++] = (byte)cArray[n3 & 0xF];
        }
        return new String(byArray2, StandardCharsets.ISO_8859_1);
    }

    public static StringBuilder convertBytesToHex(StringBuilder stringBuilder, byte[] byArray) {
        return StringUtils.convertBytesToHex(stringBuilder, byArray, byArray.length);
    }

    public static StringBuilder convertBytesToHex(StringBuilder stringBuilder, byte[] byArray, int n) {
        char[] cArray = HEX;
        for (int i = 0; i < n; ++i) {
            int n2 = byArray[i] & 0xFF;
            stringBuilder.append(cArray[n2 >>> 4]).append(cArray[n2 & 0xF]);
        }
        return stringBuilder;
    }

    public static StringBuilder appendHex(StringBuilder stringBuilder, long l, int n) {
        char[] cArray = HEX;
        int n2 = n * 8;
        while (n2 > 0) {
            stringBuilder.append(cArray[(int)(l >> (n2 -= 4)) & 0xF]).append(cArray[(int)(l >> (n2 -= 4)) & 0xF]);
        }
        return stringBuilder;
    }

    public static boolean isNumber(String string) {
        int n = string.length();
        if (n == 0) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (Character.isDigit(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isWhitespaceOrEmpty(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (string.charAt(i) <= ' ') continue;
            return false;
        }
        return true;
    }

    public static StringBuilder appendTwoDigits(StringBuilder stringBuilder, int n) {
        if (n < 10) {
            stringBuilder.append('0');
        }
        return stringBuilder.append(n);
    }

    public static StringBuilder appendZeroPadded(StringBuilder stringBuilder, int n, int n2) {
        String string = Integer.toString(n2);
        n -= string.length();
        while (n > 0) {
            stringBuilder.append('0');
            --n;
        }
        return stringBuilder.append(string);
    }

    public static StringBuilder appendToLength(StringBuilder stringBuilder, String string, int n) {
        int n2 = stringBuilder.length();
        if (n2 < n) {
            int n3 = n - n2;
            if (n3 >= string.length()) {
                stringBuilder.append(string);
            } else {
                stringBuilder.append(string, 0, n3);
            }
        }
        return stringBuilder;
    }

    public static String escapeMetaDataPattern(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        return StringUtils.replaceAll(string, "\\", "\\\\");
    }

    static {
        int n;
        HEX = "0123456789abcdef".toCharArray();
        HEX_DECODE = new int[103];
        TO_UPPER_CACHE = new String[2048][];
        for (n = 0; n < HEX_DECODE.length; ++n) {
            StringUtils.HEX_DECODE[n] = -1;
        }
        for (n = 0; n <= 9; ++n) {
            StringUtils.HEX_DECODE[n + 48] = n;
        }
        for (n = 0; n <= 5; ++n) {
            int n2 = n + 10;
            StringUtils.HEX_DECODE[n + 65] = n2;
            StringUtils.HEX_DECODE[n + 97] = n2;
        }
    }
}

