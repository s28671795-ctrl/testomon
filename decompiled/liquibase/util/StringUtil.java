/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.ExtensibleObject;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.parser.LiquibaseSqlParser;
import liquibase.parser.SqlParserFactory;
import liquibase.util.StringClauses;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    private static final Pattern upperCasePattern = Pattern.compile(".*[A-Z].*");
    private static final Pattern lowerCasePattern = Pattern.compile(".*[a-z].*");
    private static final Pattern spacePattern = Pattern.compile(" ");

    @Deprecated
    public static String trimToEmpty(String string) {
        return StringUtils.trimToEmpty(string);
    }

    @Deprecated
    public static String trimToNull(String string) {
        return StringUtils.trimToNull(string);
    }

    public static String[] processMultiLineSQL(String multiLineSQL, boolean stripComments, boolean splitStatements, String endDelimiter) {
        return StringUtil.processMultiLineSQL(multiLineSQL, stripComments, splitStatements, endDelimiter, null);
    }

    public static String[] processMultiLineSQL(String multiLineSQL, boolean stripComments, boolean splitStatements, String endDelimiter, ChangeSet changeSet) {
        SqlParserFactory sqlParserFactory = Scope.getCurrentScope().getSingleton(SqlParserFactory.class);
        LiquibaseSqlParser sqlParser = sqlParserFactory.getSqlParser();
        StringClauses parsed = sqlParser.parse(multiLineSQL, true, !stripComments, changeSet);
        ArrayList<String> returnArray = new ArrayList<String>();
        StringBuilder currentString = new StringBuilder();
        String previousPiece = null;
        boolean previousDelimiter = false;
        List<Object> parsedArray = Arrays.asList(parsed.toArray(true));
        int isInClause = 0;
        List<Object> tokens = StringUtil.mergeTokens(parsedArray, endDelimiter);
        for (int i = 0; i < tokens.size(); ++i) {
            Object piece = tokens.get(i);
            String nextPiece = null;
            for (int nextIndex = i + 1; nextPiece == null && nextIndex < tokens.size(); ++nextIndex) {
                nextPiece = StringUtil.trimToNull(String.valueOf(tokens.get(nextIndex)));
            }
            if (!(!(piece instanceof String) || !((String)piece).equalsIgnoreCase("BEGIN") || "transaction".equalsIgnoreCase(nextPiece) || "trans".equalsIgnoreCase(nextPiece) || "tran".equalsIgnoreCase(nextPiece) || "dialog".equalsIgnoreCase(nextPiece) || "conversation".equalsIgnoreCase(nextPiece) || "distributed".equalsIgnoreCase(nextPiece))) {
                ++isInClause;
            }
            if (piece instanceof String && ((String)piece).equalsIgnoreCase("END") && isInClause > 0 && !"transaction".equalsIgnoreCase(nextPiece) && !"trans".equalsIgnoreCase(nextPiece) && !"tran".equalsIgnoreCase(nextPiece)) {
                --isInClause;
            }
            if (isInClause == 0 && splitStatements && piece instanceof String && StringUtil.isDelimiter((String)piece, previousPiece, endDelimiter)) {
                String trimmedString = StringUtil.trimToNull(currentString.toString());
                if (trimmedString != null) {
                    returnArray.add(trimmedString);
                }
                currentString = new StringBuilder();
                previousDelimiter = true;
            } else {
                if (!(previousDelimiter && StringUtil.trimToNull((String)piece) == null || currentString.length() <= 0 && StringUtil.trimToNull((String)piece) == null)) {
                    currentString.append(piece);
                }
                previousDelimiter = false;
            }
            previousPiece = (String)piece;
        }
        String trimmedString = StringUtil.trimToNull(currentString.toString());
        if (trimmedString != null) {
            returnArray.add(trimmedString);
        }
        return returnArray.toArray(new String[0]);
    }

    @Deprecated
    public static String[] processMutliLineSQL(String multiLineSQL, boolean stripComments, boolean splitStatements, String endDelimiter) {
        return StringUtil.processMultiLineSQL(multiLineSQL, stripComments, splitStatements, endDelimiter, null);
    }

    @Deprecated
    public static String[] processMutliLineSQL(String multiLineSQL, boolean stripComments, boolean splitStatements, String endDelimiter, ChangeSet changeSet) {
        return StringUtil.processMultiLineSQL(multiLineSQL, stripComments, splitStatements, endDelimiter, changeSet);
    }

    private static List<Object> mergeTokens(List<Object> parsedArray, String endDelimiter) {
        if (endDelimiter == null) {
            return parsedArray;
        }
        ArrayList<Object> returnList = new ArrayList<Object>();
        ArrayList<String> possibleMerge = new ArrayList<String>();
        for (Object obj : parsedArray) {
            if (possibleMerge.size() == 0) {
                if (obj instanceof String && endDelimiter.startsWith((String)obj)) {
                    possibleMerge.add((String)obj);
                    continue;
                }
                returnList.add(obj);
                continue;
            }
            String possibleMergeString = StringUtil.join(possibleMerge, "") + obj.toString();
            if (endDelimiter.equals(possibleMergeString)) {
                returnList.add(possibleMergeString);
                possibleMerge.clear();
                continue;
            }
            if (endDelimiter.startsWith(possibleMergeString)) {
                possibleMerge.add(obj.toString());
                continue;
            }
            returnList.addAll(possibleMerge);
            returnList.add(obj);
            possibleMerge.clear();
        }
        return returnList;
    }

    protected static boolean isDelimiter(String piece, String previousPiece, String endDelimiter) {
        if (endDelimiter == null) {
            return ";".equals(piece) || ("go".equalsIgnoreCase(piece) || "/".equals(piece)) && (previousPiece == null || previousPiece.endsWith("\n"));
        }
        if (endDelimiter.length() == 1) {
            if ("/".equals(endDelimiter) && previousPiece != null && !previousPiece.endsWith("\n")) {
                return false;
            }
            return StringUtils.equalsIgnoreCase(piece, endDelimiter);
        }
        return StringUtils.equalsIgnoreCase(piece, endDelimiter) || (previousPiece + piece).toLowerCase().matches("[\\s\n\r]*" + endDelimiter.toLowerCase());
    }

    @Deprecated
    public static String wrap(String inputStr, int wrapPoint, int extraLinePadding) {
        if (inputStr == null) {
            return null;
        }
        int inputLineLength = inputStr.length();
        int ptr = 0;
        int sizeOfMatch = -1;
        StringBuilder resultLine = new StringBuilder();
        while (ptr < inputLineLength) {
            int i;
            int matcherStart;
            Integer spaceToWrapAt = null;
            int min2 = ptr + wrapPoint + 1;
            Matcher matcher = spacePattern.matcher(inputStr.substring(ptr, Math.min(min2, inputLineLength)));
            if (matcher.find()) {
                matcherStart = matcher.start();
                if (matcherStart == 0) {
                    sizeOfMatch = matcher.end();
                    if (sizeOfMatch != 0) {
                        ptr += sizeOfMatch;
                        continue;
                    }
                    ++ptr;
                }
                spaceToWrapAt = matcherStart + ptr;
            }
            if (inputLineLength - ptr <= wrapPoint) break;
            while (matcher.find()) {
                spaceToWrapAt = matcher.start() + ptr;
            }
            if (spaceToWrapAt != null && spaceToWrapAt >= ptr) {
                resultLine.append(inputStr, ptr, (int)spaceToWrapAt);
                resultLine.append(System.lineSeparator());
                for (i = 0; i < extraLinePadding; ++i) {
                    resultLine.append(" ");
                }
                ptr = spaceToWrapAt + 1;
                continue;
            }
            matcher = spacePattern.matcher(inputStr.substring(ptr + wrapPoint));
            if (matcher.find()) {
                matcherStart = matcher.start();
                sizeOfMatch = matcher.end() - matcherStart;
                spaceToWrapAt = matcherStart + ptr + wrapPoint;
            }
            if (sizeOfMatch == 0 && ptr != 0) {
                --ptr;
            }
            if (spaceToWrapAt != null && spaceToWrapAt >= 0) {
                resultLine.append(inputStr, ptr, (int)spaceToWrapAt);
                resultLine.append(System.lineSeparator());
                for (i = 0; i < extraLinePadding; ++i) {
                    resultLine.append(" ");
                }
                ptr = spaceToWrapAt + 1;
                continue;
            }
            resultLine.append(inputStr, ptr, inputStr.length());
            ptr = inputLineLength;
            sizeOfMatch = -1;
        }
        if (sizeOfMatch == 0 && ptr < inputLineLength) {
            --ptr;
        }
        resultLine.append(inputStr, ptr, inputLineLength);
        return resultLine.toString();
    }

    public static String[] splitSQL(String multiLineSQL, String endDelimiter) {
        return StringUtil.splitSQL(multiLineSQL, endDelimiter, null);
    }

    public static String[] splitSQL(String multiLineSQL, String endDelimiter, ChangeSet changeSet) {
        return StringUtil.processMultiLineSQL(multiLineSQL, false, true, endDelimiter, changeSet);
    }

    public static String stripComments(String multiLineSQL) {
        return StringUtil.stripComments(multiLineSQL, null);
    }

    public static String stripComments(String multiLineSQL, ChangeSet changeSet) {
        if (StringUtil.isEmpty(multiLineSQL)) {
            return multiLineSQL;
        }
        SqlParserFactory sqlParserFactory = Scope.getCurrentScope().getSingleton(SqlParserFactory.class);
        LiquibaseSqlParser sqlParser = sqlParserFactory.getSqlParser();
        return sqlParser.parse(multiLineSQL, true, false, changeSet).toString().trim();
    }

    public static String join(Object[] array, String delimiter, StringUtilFormatter formatter) {
        if (array == null) {
            return null;
        }
        return StringUtil.join(Arrays.asList(array), delimiter, formatter);
    }

    public static String join(String[] array, String delimiter) {
        if (array == null) {
            return null;
        }
        return StringUtil.join(Arrays.asList(array), delimiter);
    }

    public static String join(Collection<String> collection, String delimiter) {
        return StringUtil.join(collection, delimiter, (StringUtilFormatter)new ToStringFormatter());
    }

    public static String join(Collection collection, String delimiter, StringUtilFormatter formatter) {
        if (collection == null) {
            return null;
        }
        if (collection.isEmpty()) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        for (Object val : collection) {
            buffer.append(formatter.toString(val)).append(delimiter);
        }
        String returnString = buffer.toString();
        return returnString.substring(0, returnString.length() - delimiter.length());
    }

    public static String join(Collection collection, String delimiter, StringUtilFormatter formatter, boolean sorted2) {
        if (sorted2) {
            TreeSet<String> sortedSet = new TreeSet<String>();
            for (Object obj : collection) {
                sortedSet.add(formatter.toString(obj));
            }
            return StringUtil.join(sortedSet, delimiter);
        }
        return StringUtil.join(collection, delimiter, formatter);
    }

    public static String join(Collection<String> collection, String delimiter, boolean sorted2) {
        if (sorted2) {
            return StringUtil.join(new TreeSet<String>(collection), delimiter);
        }
        return StringUtil.join(collection, delimiter);
    }

    public static String join(Map map, String delimiter) {
        return StringUtil.join(map, delimiter, (StringUtilFormatter)new ToStringFormatter());
    }

    public static String join(Map map, String delimiter, StringUtilFormatter formatter) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry entry : map.entrySet()) {
            list.add(entry.getKey().toString() + "=" + formatter.toString(entry.getValue()));
        }
        return StringUtil.join(list, delimiter);
    }

    public static String join(ExtensibleObject extensibleObject, String delimiter) {
        return StringUtil.join(extensibleObject, delimiter, (StringUtilFormatter)new ToStringFormatter());
    }

    public static String join(ExtensibleObject extensibleObject, String delimiter, StringUtilFormatter formatter) {
        ArrayList<String> list = new ArrayList<String>();
        for (String attribute : new TreeSet<String>(extensibleObject.getAttributes())) {
            String formattedValue = formatter.toString(extensibleObject.get(attribute, Object.class));
            if (formattedValue == null) continue;
            list.add(attribute + "=" + formattedValue);
        }
        return StringUtil.join(list, delimiter);
    }

    public static List<String> splitAndTrim(String s2, String regex2) {
        if (s2 == null) {
            return null;
        }
        ArrayList<String> returnList = new ArrayList<String>();
        for (String string : s2.split(regex2)) {
            returnList.add(string.trim());
        }
        return returnList;
    }

    @Deprecated
    public static String repeat(String string, int times) {
        return StringUtils.repeat(string, times);
    }

    @Deprecated
    public static String join(Integer[] array, String delimiter) {
        return StringUtils.join((Object[])array, delimiter);
    }

    @Deprecated
    public static String join(int[] array, String delimiter) {
        return StringUtils.join((Object[])ArrayUtils.toObject(array), delimiter);
    }

    public static String indent(String string) {
        return StringUtil.indent(string, 4);
    }

    public static String indent(String string, int padding) {
        if (string == null) {
            return null;
        }
        String pad = StringUtil.repeat(" ", padding);
        return pad + string.replaceAll("\n", "\n" + pad);
    }

    @Deprecated
    public static String lowerCaseFirst(String string) {
        return StringUtils.uncapitalize(string);
    }

    @Deprecated
    public static String upperCaseFirst(String string) {
        return StringUtils.capitalize(string);
    }

    public static boolean hasUpperCase(String string) {
        if (string == null) {
            return false;
        }
        return upperCasePattern.matcher(string).matches();
    }

    public static boolean hasLowerCase(String string) {
        if (string == null) {
            return false;
        }
        return lowerCasePattern.matcher(string).matches();
    }

    public static String standardizeLineEndings(String string) {
        if (string == null) {
            return null;
        }
        return string.replace("\r\n", "\n").replace("\r", "\n");
    }

    public static boolean isAscii(String string) {
        if (string == null) {
            return true;
        }
        for (char c : string.toCharArray()) {
            if (StringUtil.isAscii(c)) continue;
            return false;
        }
        return true;
    }

    @Deprecated
    public static boolean isAscii(char ch) {
        return CharUtils.isAscii(ch);
    }

    public static String escapeHtml(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            if (c > '\u007f') {
                out.append("&#");
                out.append(Integer.toString(c, 10));
                out.append(';');
                continue;
            }
            out.append(c);
        }
        return out.toString();
    }

    public static String pad(String value, int length) {
        if ((value = StringUtil.trimToEmpty(value)).length() >= length) {
            return value;
        }
        return value + StringUtil.repeat(" ", length - value.length());
    }

    public static String leftPad(String value, int length) {
        if ((value = StringUtil.trimToEmpty(value)).length() >= length) {
            return value;
        }
        return StringUtil.repeat(" ", length - value.length()) + value;
    }

    @Deprecated
    public static boolean contains(String value, String containsValue) {
        return StringUtils.contains((CharSequence)value, containsValue);
    }

    @Deprecated
    public static boolean isEmpty(String value) {
        return StringUtils.isEmpty(value);
    }

    @Deprecated
    public static boolean isNotEmpty(String value) {
        return StringUtils.isNotEmpty(value);
    }

    @Deprecated
    public static boolean startsWith(String value, String startsWith) {
        return StringUtils.startsWith(value, startsWith);
    }

    @Deprecated
    public static boolean endsWith(String value, String endsWith) {
        return StringUtils.endsWith(value, endsWith);
    }

    public static boolean isWhitespace(CharSequence string) {
        if (string == null) {
            return true;
        }
        return StringUtils.isWhitespace(string);
    }

    public static boolean isMinimumVersion(String minimumVersion, int candidateMajor, int candidateMinor, int candidatePatch) {
        int minPatch;
        if (minimumVersion == null) {
            return true;
        }
        String[] parts = minimumVersion.split("\\.", 3);
        int minMajor = Integer.parseInt(parts[0]);
        int minMinor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        int n = minPatch = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
        if (minMajor > candidateMajor) {
            return false;
        }
        if (minMajor == candidateMajor && minMinor > candidateMinor) {
            return false;
        }
        return minMajor != candidateMajor || minMinor != candidateMinor || minPatch <= candidatePatch;
    }

    public static String limitSize(String string, int maxLength) {
        if (string == null) {
            return null;
        }
        if (string.length() > maxLength) {
            return string.substring(0, maxLength - 3) + "...";
        }
        return string;
    }

    @Deprecated
    public static String randomIdentifer(int len) {
        return StringUtil.randomIdentifier(len);
    }

    public static String randomIdentifier(int len) {
        return RandomStringUtils.random(len, true, false);
    }

    public static String toKabobCase(String string) {
        if (string == null) {
            return null;
        }
        if (string.length() == 1) {
            return string;
        }
        StringBuilder outString = new StringBuilder();
        char[] charString = string.toCharArray();
        for (int i = 0; i < charString.length; ++i) {
            char letter = charString[i];
            if (i == 0) {
                outString.append(Character.toLowerCase(letter));
                continue;
            }
            if (Character.isUpperCase(letter)) {
                outString.append('-').append(Character.toLowerCase(letter));
                continue;
            }
            outString.append(letter);
        }
        return outString.toString();
    }

    public static String toCamelCase(String string) {
        if (string == null) {
            return null;
        }
        StringBuilder outString = new StringBuilder();
        char[] charString = string.toCharArray();
        boolean uppercaseNext = false;
        for (char letter : charString) {
            if (letter == '-' || letter == '_') {
                uppercaseNext = true;
                continue;
            }
            if (uppercaseNext) {
                outString.append(Character.toUpperCase(letter));
                uppercaseNext = false;
                continue;
            }
            outString.append(letter);
        }
        return outString.toString();
    }

    public static boolean equalsIgnoreCaseAndEmpty(String s1, String s2) {
        String clean1 = StringUtil.trimToNull(s1);
        String clean2 = StringUtil.trimToNull(s2);
        if (clean1 == null && clean2 == null) {
            return true;
        }
        return StringUtils.equalsIgnoreCase(s1, s2);
    }

    public static String trimRight(String string) {
        int i;
        if (string == null) {
            return null;
        }
        for (i = string.length() - 1; i >= 0 && Character.isWhitespace(string.charAt(i)); --i) {
        }
        return string.substring(0, i + 1);
    }

    public static String getLastBlockComment(String sqlString) {
        if (StringUtil.isEmpty(sqlString) || sqlString.length() < 4) {
            return null;
        }
        String reversedString = StringUtils.reverse(sqlString);
        int idxClosingLastChar = -1;
        int idxOpeningFirstChar = -1;
        for (int i = 0; i < reversedString.length(); ++i) {
            char s2;
            char c;
            if (idxClosingLastChar < 0) {
                c = reversedString.charAt(i);
                if (c == '/') {
                    s2 = reversedString.charAt(i + 1);
                    if (s2 != '*') continue;
                    idxClosingLastChar = i;
                    continue;
                }
                if (Character.isWhitespace(c)) continue;
                return null;
            }
            c = reversedString.charAt(i);
            if (c != '/') continue;
            s2 = reversedString.charAt(i - 1);
            char e = reversedString.charAt(i + 1);
            if (s2 != '*' || e == '\\') continue;
            idxOpeningFirstChar = i;
            break;
        }
        int idxOfLastBlockComment = sqlString.length() - (idxOpeningFirstChar + 1);
        return sqlString.substring(idxOfLastBlockComment);
    }

    public static String getLastLineComment(String sqlString) {
        if (StringUtil.isEmpty(sqlString) || sqlString.length() < 2) {
            return null;
        }
        boolean startOfNewLine = false;
        int idxOfDoubleDash = -1;
        for (int i = 0; i < sqlString.trim().length() - 1; ++i) {
            char c = sqlString.charAt(i);
            if (c == '-') {
                char s2 = sqlString.charAt(i + 1);
                if (s2 != '-') continue;
                if (idxOfDoubleDash < 0) {
                    idxOfDoubleDash = i;
                }
                startOfNewLine = false;
                continue;
            }
            if (!Character.isWhitespace(c)) {
                if (!startOfNewLine) continue;
                idxOfDoubleDash = -1;
                continue;
            }
            if (c != '\r' && c != '\n') continue;
            startOfNewLine = true;
            idxOfDoubleDash = -1;
        }
        if (idxOfDoubleDash < 0) {
            return null;
        }
        return sqlString.substring(idxOfDoubleDash);
    }

    public static String stripSqlCommentsAndWhitespacesFromTheEnd(String sqlString) {
        if (StringUtil.isEmpty(sqlString)) {
            return sqlString;
        }
        StringBuilder str = new StringBuilder(sqlString);
        boolean strModified = true;
        while (strModified) {
            String lastLineComment;
            strModified = false;
            String lastBlockComment = StringUtil.getLastBlockComment(str.toString());
            if (lastBlockComment != null && !lastBlockComment.isEmpty()) {
                str.setLength(str.length() - lastBlockComment.length());
                strModified = true;
            }
            if ((lastLineComment = StringUtil.getLastLineComment(str.toString())) == null || lastLineComment.isEmpty()) continue;
            str.setLength(str.length() - lastLineComment.length());
            strModified = true;
        }
        return StringUtil.trimRight(str.toString());
    }

    public static String concatConsistentCase(String baseString, String addition) {
        boolean hasLowerCase = StringUtil.hasLowerCase(baseString);
        boolean hasUpperCase = StringUtil.hasUpperCase(baseString);
        if (hasLowerCase && hasUpperCase || !hasLowerCase && !hasUpperCase) {
            return baseString + addition;
        }
        if (hasLowerCase) {
            return baseString + addition.toLowerCase();
        }
        return baseString + addition.toUpperCase();
    }

    public static String stripEnclosingQuotes(String string) {
        if (string.length() > 1 && (string.charAt(0) == '\"' || string.charAt(0) == '\'') && string.charAt(0) == string.charAt(string.length() - 1)) {
            return string.substring(1, string.length() - 1);
        }
        return string;
    }

    public static boolean equalsWordNull(String value) {
        return "NULL".equalsIgnoreCase(value);
    }

    @Deprecated
    public static String[] splitCamelCase(String str) {
        return StringUtils.splitByCharacterTypeCamelCase(str);
    }

    public static byte[] getBytesWithEncoding(String string) {
        if (string == null) {
            return null;
        }
        String encoding = null;
        try {
            encoding = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentConfiguredValue().getValue();
            if (encoding != null) {
                return string.getBytes(encoding);
            }
        }
        catch (UnsupportedEncodingException uoe) {
            Scope.getCurrentScope().getLog(StringUtil.class).warning("Error using encoding " + encoding);
        }
        return string.getBytes(StandardCharsets.UTF_8);
    }

    @Deprecated
    public static String toLowerWithoutWhitespaces(String value) {
        return StringUtils.toRootLowerCase(StringUtils.deleteWhitespace(value));
    }

    @Deprecated
    public static boolean isNumeric(CharSequence cs) {
        return StringUtils.isNumeric(cs);
    }

    @Deprecated
    public static boolean isEmpty(CharSequence cs) {
        return StringUtils.isEmpty(cs);
    }

    public static List<String> splitToChunks(String input, int chunkSize) {
        int length = input.length();
        if (length < chunkSize) {
            return Collections.singletonList(input);
        }
        ArrayList<String> chunks = new ArrayList<String>(length / chunkSize + 1);
        for (int i = 0; i < length; i += chunkSize) {
            int end = Math.min(i + chunkSize, length);
            String chunk = input.substring(i, end);
            chunks.add(chunk);
        }
        return chunks;
    }

    public static interface StringUtilFormatter<Type> {
        public String toString(Type var1);
    }

    public static class ToStringFormatter
    implements StringUtilFormatter {
        private final boolean shouldLowercase;

        public ToStringFormatter() {
            this(false);
        }

        public ToStringFormatter(boolean shouldLowercase) {
            this.shouldLowercase = shouldLowercase;
        }

        public String toString(Object obj) {
            if (obj == null) {
                return null;
            }
            String string = obj.toString();
            if (this.shouldLowercase) {
                string = string.toLowerCase();
            }
            return string;
        }
    }

    public static class DefaultFormatter
    implements StringUtilFormatter {
        public String toString(Object obj) {
            if (obj == null) {
                return null;
            }
            if (obj instanceof Class) {
                return ((Class)obj).getName();
            }
            if (obj instanceof Object[]) {
                if (((Object[])obj).length == 0) {
                    return null;
                }
                return "[" + StringUtil.join((Object[])obj, ", ", (StringUtilFormatter)this) + "]";
            }
            if (obj instanceof Collection) {
                if (((Collection)obj).size() == 0) {
                    return null;
                }
                return "[" + StringUtil.join((Collection)obj, ", ", (StringUtilFormatter)this) + "]";
            }
            return obj.toString();
        }
    }
}

