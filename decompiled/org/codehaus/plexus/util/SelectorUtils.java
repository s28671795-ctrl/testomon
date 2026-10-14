/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.codehaus.plexus.util.MatchPattern;

public final class SelectorUtils {
    public static final String PATTERN_HANDLER_PREFIX = "[";
    public static final String PATTERN_HANDLER_SUFFIX = "]";
    public static final String REGEX_HANDLER_PREFIX = "%regex[";
    public static final String ANT_HANDLER_PREFIX = "%ant[";
    private static SelectorUtils instance = new SelectorUtils();

    private SelectorUtils() {
    }

    public static SelectorUtils getInstance() {
        return instance;
    }

    public static boolean matchPatternStart(String pattern, String str) {
        return SelectorUtils.matchPatternStart(pattern, str, true);
    }

    public static boolean matchPatternStart(String pattern, String str, boolean isCaseSensitive) {
        if (SelectorUtils.isRegexPrefixedPattern(pattern)) {
            return true;
        }
        if (SelectorUtils.isAntPrefixedPattern(pattern)) {
            pattern = pattern.substring(ANT_HANDLER_PREFIX.length(), pattern.length() - PATTERN_HANDLER_SUFFIX.length());
        }
        String altStr = str.replace('\\', '/');
        return SelectorUtils.matchAntPathPatternStart(pattern, str, File.separator, isCaseSensitive) || SelectorUtils.matchAntPathPatternStart(pattern, altStr, "/", isCaseSensitive);
    }

    static boolean isAntPrefixedPattern(String pattern) {
        return pattern.length() > ANT_HANDLER_PREFIX.length() + PATTERN_HANDLER_SUFFIX.length() + 1 && pattern.startsWith(ANT_HANDLER_PREFIX) && pattern.endsWith(PATTERN_HANDLER_SUFFIX);
    }

    static boolean matchAntPathPatternStart(MatchPattern pattern, String str, String separator, boolean isCaseSensitive) {
        if (SelectorUtils.separatorPatternStartSlashMismatch(pattern, str, separator)) {
            return false;
        }
        return SelectorUtils.matchAntPathPatternStart(pattern.getTokenizedPathString(), str, separator, isCaseSensitive);
    }

    static boolean matchAntPathPatternStart(String pattern, String str, String separator, boolean isCaseSensitive) {
        if (SelectorUtils.separatorPatternStartSlashMismatch(pattern, str, separator)) {
            return false;
        }
        String[] patDirs = SelectorUtils.tokenizePathToString(pattern, separator);
        return SelectorUtils.matchAntPathPatternStart(patDirs, str, separator, isCaseSensitive);
    }

    private static boolean separatorPatternStartSlashMismatch(String pattern, String str, String separator) {
        return str.startsWith(separator) != pattern.startsWith(separator);
    }

    private static boolean separatorPatternStartSlashMismatch(MatchPattern matchPattern, String str, String separator) {
        return str.startsWith(separator) != matchPattern.startsWith(separator);
    }

    static boolean matchAntPathPatternStart(String[] patDirs, String str, String separator, boolean isCaseSensitive) {
        String patDir;
        int strIdxStart;
        String[] strDirs = SelectorUtils.tokenizePathToString(str, separator);
        int patIdxStart = 0;
        int patIdxEnd = patDirs.length - 1;
        int strIdxEnd = strDirs.length - 1;
        for (strIdxStart = 0; patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd && !(patDir = patDirs[patIdxStart]).equals("**"); ++patIdxStart, ++strIdxStart) {
            if (SelectorUtils.match(patDir, strDirs[strIdxStart], isCaseSensitive)) continue;
            return false;
        }
        return strIdxStart > strIdxEnd || patIdxStart <= patIdxEnd;
    }

    public static boolean matchPath(String pattern, String str) {
        return SelectorUtils.matchPath(pattern, str, true);
    }

    public static boolean matchPath(String pattern, String str, boolean isCaseSensitive) {
        return SelectorUtils.matchPath(pattern, str, File.separator, isCaseSensitive);
    }

    public static boolean matchPath(String pattern, String str, String separator, boolean isCaseSensitive) {
        if (SelectorUtils.isRegexPrefixedPattern(pattern)) {
            String localPattern = pattern.substring(REGEX_HANDLER_PREFIX.length(), pattern.length() - PATTERN_HANDLER_SUFFIX.length());
            return str.matches(localPattern);
        }
        String localPattern = SelectorUtils.isAntPrefixedPattern(pattern) ? pattern.substring(ANT_HANDLER_PREFIX.length(), pattern.length() - PATTERN_HANDLER_SUFFIX.length()) : pattern;
        String osRelatedPath = SelectorUtils.toOSRelatedPath(str, separator);
        String osRelatedPattern = SelectorUtils.toOSRelatedPath(localPattern, separator);
        return SelectorUtils.matchAntPathPattern(osRelatedPattern, osRelatedPath, separator, isCaseSensitive);
    }

    private static String toOSRelatedPath(String pattern, String separator) {
        if ("/".equals(separator)) {
            return pattern.replace("\\", separator);
        }
        if ("\\".equals(separator)) {
            return pattern.replace("/", separator);
        }
        return pattern;
    }

    static boolean isRegexPrefixedPattern(String pattern) {
        return pattern.length() > REGEX_HANDLER_PREFIX.length() + PATTERN_HANDLER_SUFFIX.length() + 1 && pattern.startsWith(REGEX_HANDLER_PREFIX) && pattern.endsWith(PATTERN_HANDLER_SUFFIX);
    }

    static boolean matchAntPathPattern(MatchPattern matchPattern, String str, String separator, boolean isCaseSensitive) {
        if (SelectorUtils.separatorPatternStartSlashMismatch(matchPattern, str, separator)) {
            return false;
        }
        String[] patDirs = matchPattern.getTokenizedPathString();
        String[] strDirs = SelectorUtils.tokenizePathToString(str, separator);
        return SelectorUtils.matchAntPathPattern(patDirs, strDirs, isCaseSensitive);
    }

    static boolean matchAntPathPattern(String pattern, String str, String separator, boolean isCaseSensitive) {
        if (SelectorUtils.separatorPatternStartSlashMismatch(pattern, str, separator)) {
            return false;
        }
        String[] patDirs = SelectorUtils.tokenizePathToString(pattern, separator);
        String[] strDirs = SelectorUtils.tokenizePathToString(str, separator);
        return SelectorUtils.matchAntPathPattern(patDirs, strDirs, isCaseSensitive);
    }

    static boolean matchAntPathPattern(String[] patDirs, String[] strDirs, boolean isCaseSensitive) {
        String patDir;
        int strIdxStart;
        int patIdxStart = 0;
        int patIdxEnd = patDirs.length - 1;
        int strIdxEnd = strDirs.length - 1;
        for (strIdxStart = 0; patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd && !(patDir = patDirs[patIdxStart]).equals("**"); ++patIdxStart, ++strIdxStart) {
            if (SelectorUtils.match(patDir, strDirs[strIdxStart], isCaseSensitive)) continue;
            return false;
        }
        if (strIdxStart > strIdxEnd) {
            for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                if (patDirs[i].equals("**")) continue;
                return false;
            }
            return true;
        }
        if (patIdxStart > patIdxEnd) {
            return false;
        }
        while (patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd && !(patDir = patDirs[patIdxEnd]).equals("**")) {
            if (!SelectorUtils.match(patDir, strDirs[strIdxEnd], isCaseSensitive)) {
                return false;
            }
            --patIdxEnd;
            --strIdxEnd;
        }
        if (strIdxStart > strIdxEnd) {
            for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                if (patDirs[i].equals("**")) continue;
                return false;
            }
            return true;
        }
        while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
            int patIdxTmp = -1;
            for (int i = patIdxStart + 1; i <= patIdxEnd; ++i) {
                if (!patDirs[i].equals("**")) continue;
                patIdxTmp = i;
                break;
            }
            if (patIdxTmp == patIdxStart + 1) {
                ++patIdxStart;
                continue;
            }
            int patLength = patIdxTmp - patIdxStart - 1;
            int strLength = strIdxEnd - strIdxStart + 1;
            int foundIdx = -1;
            block6: for (int i = 0; i <= strLength - patLength; ++i) {
                for (int j = 0; j < patLength; ++j) {
                    String subPat = patDirs[patIdxStart + j + 1];
                    String subStr = strDirs[strIdxStart + i + j];
                    if (!SelectorUtils.match(subPat, subStr, isCaseSensitive)) continue block6;
                }
                foundIdx = strIdxStart + i;
                break;
            }
            if (foundIdx == -1) {
                return false;
            }
            patIdxStart = patIdxTmp;
            strIdxStart = foundIdx + patLength;
        }
        for (int i = patIdxStart; i <= patIdxEnd; ++i) {
            if (patDirs[i].equals("**")) continue;
            return false;
        }
        return true;
    }

    static boolean matchAntPathPattern(char[][] patDirs, char[][] strDirs, boolean isCaseSensitive) {
        char[] patDir;
        int strIdxStart;
        int patIdxStart = 0;
        int patIdxEnd = patDirs.length - 1;
        int strIdxEnd = strDirs.length - 1;
        for (strIdxStart = 0; patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd && !SelectorUtils.isDoubleStar(patDir = patDirs[patIdxStart]); ++patIdxStart, ++strIdxStart) {
            if (SelectorUtils.match(patDir, strDirs[strIdxStart], isCaseSensitive)) continue;
            return false;
        }
        if (strIdxStart > strIdxEnd) {
            for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                if (SelectorUtils.isDoubleStar(patDirs[i])) continue;
                return false;
            }
            return true;
        }
        if (patIdxStart > patIdxEnd) {
            return false;
        }
        while (patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd && !SelectorUtils.isDoubleStar(patDir = patDirs[patIdxEnd])) {
            if (!SelectorUtils.match(patDir, strDirs[strIdxEnd], isCaseSensitive)) {
                return false;
            }
            --patIdxEnd;
            --strIdxEnd;
        }
        if (strIdxStart > strIdxEnd) {
            for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                if (SelectorUtils.isDoubleStar(patDirs[i])) continue;
                return false;
            }
            return true;
        }
        while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
            int patIdxTmp = -1;
            for (int i = patIdxStart + 1; i <= patIdxEnd; ++i) {
                if (!SelectorUtils.isDoubleStar(patDirs[i])) continue;
                patIdxTmp = i;
                break;
            }
            if (patIdxTmp == patIdxStart + 1) {
                ++patIdxStart;
                continue;
            }
            int patLength = patIdxTmp - patIdxStart - 1;
            int strLength = strIdxEnd - strIdxStart + 1;
            int foundIdx = -1;
            block6: for (int i = 0; i <= strLength - patLength; ++i) {
                for (int j = 0; j < patLength; ++j) {
                    char[] subPat = patDirs[patIdxStart + j + 1];
                    char[] subStr = strDirs[strIdxStart + i + j];
                    if (!SelectorUtils.match(subPat, subStr, isCaseSensitive)) continue block6;
                }
                foundIdx = strIdxStart + i;
                break;
            }
            if (foundIdx == -1) {
                return false;
            }
            patIdxStart = patIdxTmp;
            strIdxStart = foundIdx + patLength;
        }
        for (int i = patIdxStart; i <= patIdxEnd; ++i) {
            if (SelectorUtils.isDoubleStar(patDirs[i])) continue;
            return false;
        }
        return true;
    }

    private static boolean isDoubleStar(char[] patDir) {
        return patDir != null && patDir.length == 2 && patDir[0] == '*' && patDir[1] == '*';
    }

    public static boolean match(String pattern, String str) {
        return SelectorUtils.match(pattern, str, true);
    }

    public static boolean match(String pattern, String str, boolean isCaseSensitive) {
        char[] patArr = pattern.toCharArray();
        char[] strArr = str.toCharArray();
        return SelectorUtils.match(patArr, strArr, isCaseSensitive);
    }

    public static boolean match(char[] patArr, char[] strArr, boolean isCaseSensitive) {
        char ch;
        int patIdxStart = 0;
        int patIdxEnd = patArr.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strArr.length - 1;
        boolean containsStar = false;
        for (char aPatArr : patArr) {
            if (aPatArr != '*') continue;
            containsStar = true;
            break;
        }
        if (!containsStar) {
            if (patIdxEnd != strIdxEnd) {
                return false;
            }
            for (int i = 0; i <= patIdxEnd; ++i) {
                char ch2 = patArr[i];
                if (ch2 == '?' || SelectorUtils.equals(ch2, strArr[i], isCaseSensitive)) continue;
                return false;
            }
            return true;
        }
        if (patIdxEnd == 0) {
            return true;
        }
        while ((ch = patArr[patIdxStart]) != '*' && strIdxStart <= strIdxEnd) {
            if (ch != '?' && !SelectorUtils.equals(ch, strArr[strIdxStart], isCaseSensitive)) {
                return false;
            }
            ++patIdxStart;
            ++strIdxStart;
        }
        if (strIdxStart > strIdxEnd) {
            for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                if (patArr[i] == '*') continue;
                return false;
            }
            return true;
        }
        while ((ch = patArr[patIdxEnd]) != '*' && strIdxStart <= strIdxEnd) {
            if (ch != '?' && !SelectorUtils.equals(ch, strArr[strIdxEnd], isCaseSensitive)) {
                return false;
            }
            --patIdxEnd;
            --strIdxEnd;
        }
        if (strIdxStart > strIdxEnd) {
            for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                if (patArr[i] == '*') continue;
                return false;
            }
            return true;
        }
        while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
            int patIdxTmp = -1;
            for (int i = patIdxStart + 1; i <= patIdxEnd; ++i) {
                if (patArr[i] != '*') continue;
                patIdxTmp = i;
                break;
            }
            if (patIdxTmp == patIdxStart + 1) {
                ++patIdxStart;
                continue;
            }
            int patLength = patIdxTmp - patIdxStart - 1;
            int strLength = strIdxEnd - strIdxStart + 1;
            int foundIdx = -1;
            block8: for (int i = 0; i <= strLength - patLength; ++i) {
                for (int j = 0; j < patLength; ++j) {
                    ch = patArr[patIdxStart + j + 1];
                    if (ch != '?' && !SelectorUtils.equals(ch, strArr[strIdxStart + i + j], isCaseSensitive)) continue block8;
                }
                foundIdx = strIdxStart + i;
                break;
            }
            if (foundIdx == -1) {
                return false;
            }
            patIdxStart = patIdxTmp;
            strIdxStart = foundIdx + patLength;
        }
        for (int i = patIdxStart; i <= patIdxEnd; ++i) {
            if (patArr[i] == '*') continue;
            return false;
        }
        return true;
    }

    private static boolean equals(char c1, char c2, boolean isCaseSensitive) {
        if (c1 == c2) {
            return true;
        }
        return !isCaseSensitive && (Character.toUpperCase(c1) == Character.toUpperCase(c2) || Character.toLowerCase(c1) == Character.toLowerCase(c2));
    }

    private static String[] tokenizePathToString(String path, String separator) {
        ArrayList<String> ret = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(path, separator);
        while (st.hasMoreTokens()) {
            ret.add(st.nextToken());
        }
        return ret.toArray(new String[0]);
    }

    public static boolean isOutOfDate(File src, File target, int granularity) {
        if (!src.exists()) {
            return false;
        }
        if (!target.exists()) {
            return true;
        }
        return src.lastModified() - (long)granularity > target.lastModified();
    }

    public static String removeWhitespace(String input) {
        StringBuilder result = new StringBuilder();
        if (input != null) {
            StringTokenizer st = new StringTokenizer(input);
            while (st.hasMoreTokens()) {
                result.append(st.nextToken());
            }
        }
        return result.toString();
    }
}

