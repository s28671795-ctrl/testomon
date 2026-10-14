/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;

    public static String defaultString(String str) {
        return str == null ? EMPTY : str;
    }

    public static String defaultString(String str, String defaultStr) {
        return str == null ? defaultStr : str;
    }

    public static String defaultIfEmpty(String str, String defaultStr) {
        return StringUtils.isEmpty(str) ? defaultStr : str;
    }

    public static String defaultIfBlank(String str, String defaultStr) {
        return StringUtils.isBlank(str) ? defaultStr : str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; ++i) {
            if (Character.isWhitespace(str.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static int countMatches(String str, String sub) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            ++count;
            idx += sub.length();
        }
        return count;
    }

    public static String rightPad(String str, int size) {
        return StringUtils.rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int strSize, int size) {
        return StringUtils.rightPad(str, strSize, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        return StringUtils.rightPad(str, str.length(), size, padChar);
    }

    public static String rightPad(String str, int strSize, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - strSize;
        if (pads <= 0) {
            return str;
        }
        if (pads > 8192) {
            return StringUtils.rightPad(str, strSize, size, String.valueOf(padChar));
        }
        return str.concat(StringUtils.padding(pads, padChar));
    }

    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        return StringUtils.rightPad(str, str.length(), size, padStr);
    }

    public static String rightPad(String str, int strSize, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (StringUtils.isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = strSize;
        int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        if (padLen == 1 && pads <= 8192) {
            return StringUtils.rightPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return str.concat(padStr);
        }
        if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; ++i) {
            padding[i] = padChars[i % padLen];
        }
        return str.concat(new String(padding));
    }

    public static String leftPad(String str, int size) {
        return StringUtils.leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int strSize, int size) {
        return StringUtils.leftPad(str, strSize, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        return StringUtils.leftPad(str, str.length(), size, padChar);
    }

    public static String leftPad(String str, int strSize, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - strSize;
        if (pads <= 0) {
            return str;
        }
        if (pads > 8192) {
            return StringUtils.leftPad(str, strSize, size, String.valueOf(padChar));
        }
        return StringUtils.padding(pads, padChar).concat(str);
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        return StringUtils.leftPad(str, str.length(), size, padStr);
    }

    public static String leftPad(String str, int strSize, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (StringUtils.isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = strSize;
        int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        if (padLen == 1 && pads <= 8192) {
            return StringUtils.leftPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return padStr.concat(str);
        }
        if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; ++i) {
            padding[i] = padChars[i % padLen];
        }
        return new String(padding).concat(str);
    }

    private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; ++i) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    public static String abbreviate(String str, int maxWidth) {
        return StringUtils.abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth) {
        if (str == null) {
            return null;
        }
        if (maxWidth < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        }
        if (str.length() <= maxWidth) {
            return str;
        }
        if (offset > str.length()) {
            offset = str.length();
        }
        if (str.length() - offset < maxWidth - 3) {
            offset = str.length() - (maxWidth - 3);
        }
        if (offset <= 4) {
            return str.substring(0, maxWidth - 3) + "...";
        }
        if (maxWidth < 7) {
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
        }
        if (offset + (maxWidth - 3) < str.length()) {
            return "..." + StringUtils.abbreviate(str.substring(offset), maxWidth - 3);
        }
        return "..." + str.substring(str.length() - (maxWidth - 3));
    }

    public static boolean containsAny(String str, char ... searchChars) {
        if (str == null || str.length() == 0 || searchChars == null || searchChars.length == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            for (int j = 0; j < searchChars.length; ++j) {
                if (searchChars[j] != ch) continue;
                return true;
            }
        }
        return false;
    }

    public static String replace(String text, String searchString, String replacement) {
        return StringUtils.replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start2 = 0;
        int end = text.indexOf(searchString, start2);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        int n = increase = increase < 0 ? 0 : increase;
        StringBuilder buf = new StringBuilder(text.length() + (increase *= max < 0 ? 16 : (max > 64 ? 64 : max)));
        while (end != -1) {
            buf.append(text, start2, end).append(replacement);
            start2 = end + replLength;
            if (--max == 0) break;
            end = text.indexOf(searchString, start2);
        }
        buf.append(text, start2, text.length());
        return buf.toString();
    }

    public static String replaceEach(String text, String[] searchList, String[] replacementList) {
        return StringUtils.replaceEach(text, searchList, replacementList, false, 0);
    }

    private static String replaceEach(String text, String[] searchList, String[] replacementList, boolean repeat, int timeToLive) {
        if (text == null || text.length() == 0 || searchList == null || searchList.length == 0 || replacementList == null || replacementList.length == 0) {
            return text;
        }
        if (timeToLive < 0) {
            throw new IllegalStateException("TimeToLive of " + timeToLive + " is less than 0: " + text);
        }
        int searchLength = searchList.length;
        int replacementLength = replacementList.length;
        if (searchLength != replacementLength) {
            throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength);
        }
        boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];
        int textIndex = -1;
        int replaceIndex = -1;
        int tempIndex = -1;
        for (int i = 0; i < searchLength; ++i) {
            if (noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].length() == 0 || replacementList[i] == null) continue;
            tempIndex = text.indexOf(searchList[i]);
            if (tempIndex == -1) {
                noMoreMatchesForReplIndex[i] = true;
                continue;
            }
            if (textIndex != -1 && tempIndex >= textIndex) continue;
            textIndex = tempIndex;
            replaceIndex = i;
        }
        if (textIndex == -1) {
            return text;
        }
        int start2 = 0;
        int increase = 0;
        for (int i = 0; i < searchList.length; ++i) {
            int greater2 = replacementList[i].length() - searchList[i].length();
            if (greater2 <= 0) continue;
            increase += 3 * greater2;
        }
        increase = Math.min(increase, text.length() / 5);
        StringBuffer buf = new StringBuffer(text.length() + increase);
        while (textIndex != -1) {
            int i;
            for (i = start2; i < textIndex; ++i) {
                buf.append(text.charAt(i));
            }
            buf.append(replacementList[replaceIndex]);
            start2 = textIndex + searchList[replaceIndex].length();
            textIndex = -1;
            replaceIndex = -1;
            tempIndex = -1;
            for (i = 0; i < searchLength; ++i) {
                if (noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].length() == 0 || replacementList[i] == null) continue;
                tempIndex = text.indexOf(searchList[i], start2);
                if (tempIndex == -1) {
                    noMoreMatchesForReplIndex[i] = true;
                    continue;
                }
                if (textIndex != -1 && tempIndex >= textIndex) continue;
                textIndex = tempIndex;
                replaceIndex = i;
            }
        }
        int textLength = text.length();
        for (int i = start2; i < textLength; ++i) {
            buf.append(text.charAt(i));
        }
        String result = buf.toString();
        if (!repeat) {
            return result;
        }
        return StringUtils.replaceEach(result, searchList, replacementList, repeat, timeToLive - 1);
    }

    @SafeVarargs
    public static <T> String join(T ... elements) {
        return StringUtils.join((Object[])elements, null);
    }

    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; ++i) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] == null) continue;
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        int noOfItems;
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }
        if ((noOfItems = endIndex - startIndex) <= 0) {
            return EMPTY;
        }
        StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; ++i) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] == null) continue;
            buf.append(array[i]);
        }
        return buf.toString();
    }

    private StringUtils() {
    }

    public static boolean equals(String o1, String o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.getClass().isArray()) {
            if (o1 instanceof Object[] && o2 instanceof Object[]) {
                return Arrays.deepEquals((Object[])o1, (Object[])o2);
            }
            if (o1 instanceof byte[] && o2 instanceof byte[]) {
                return Arrays.equals((byte[])o1, (byte[])o2);
            }
            if (o1 instanceof short[] && o2 instanceof short[]) {
                return Arrays.equals((short[])o1, (short[])o2);
            }
            if (o1 instanceof int[] && o2 instanceof int[]) {
                return Arrays.equals((int[])o1, (int[])o2);
            }
            if (o1 instanceof long[] && o2 instanceof long[]) {
                return Arrays.equals((long[])o1, (long[])o2);
            }
            if (o1 instanceof float[] && o2 instanceof float[]) {
                return Arrays.equals((float[])o1, (float[])o2);
            }
            if (o1 instanceof double[] && o2 instanceof double[]) {
                return Arrays.equals((double[])o1, (double[])o2);
            }
            if (o1 instanceof char[] && o2 instanceof char[]) {
                return Arrays.equals((char[])o1, (char[])o2);
            }
            if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
                return Arrays.equals((boolean[])o1, (boolean[])o2);
            }
            return false;
        }
        return o1.equals(o2);
    }

    public static <T> T defaultIfNull(T object, T defaultValue) {
        return object != null ? object : defaultValue;
    }

    @SafeVarargs
    public static <T> T firstNonNull(T ... objects) {
        for (T object : objects) {
            if (object == null) continue;
            return object;
        }
        return null;
    }

    public static String toCamelCase(String string) {
        StringBuilder result = new StringBuilder();
        for (String word : string.split("_", -1)) {
            if (word.length() > 0) {
                if (Character.isDigit(word.charAt(0))) {
                    result.append("_");
                }
                result.append(word.substring(0, 1).toUpperCase());
                result.append(word.substring(1).toLowerCase());
                continue;
            }
            result.append("_");
        }
        return result.toString();
    }

    public static String toCamelCaseLC(String string) {
        return StringUtils.toLC(StringUtils.toCamelCase(string));
    }

    public static String toLC(String string) {
        return StringUtils.toLC(string, Locale.getDefault());
    }

    public static String toLC(String string, Locale locale) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        return string.substring(0, 1).toLowerCase(locale) + string.substring(1);
    }

    public static String toUC(String string) {
        return StringUtils.toUC(string, Locale.getDefault());
    }

    public static String toUC(String string, Locale locale) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        return string.substring(0, 1).toUpperCase(locale) + string.substring(1);
    }

    public static String[] split(String regex2, CharSequence input) {
        int index = 0;
        ArrayList<String> matchList = new ArrayList<String>();
        Matcher m4 = Pattern.compile(regex2).matcher(input);
        while (m4.find()) {
            matchList.add(input.subSequence(index, m4.start()).toString());
            matchList.add(input.subSequence(m4.start(), m4.end()).toString());
            index = m4.end();
        }
        if (index == 0) {
            return new String[]{input.toString()};
        }
        matchList.add(input.subSequence(index, input.length()).toString());
        Iterator it = matchList.iterator();
        while (it.hasNext()) {
            if (!EMPTY.equals(it.next())) continue;
            it.remove();
        }
        String[] result = new String[matchList.size()];
        return matchList.toArray(result);
    }
}

