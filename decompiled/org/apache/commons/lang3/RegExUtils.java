/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.lang3;

import java.util.regex.Pattern;
import org.apache.commons.lang3.ObjectUtils;

public class RegExUtils {
    public static String removeAll(String text, Pattern regex2) {
        return RegExUtils.replaceAll(text, regex2, "");
    }

    public static String removeAll(String text, String regex2) {
        return RegExUtils.replaceAll(text, regex2, "");
    }

    public static String removeFirst(String text, Pattern regex2) {
        return RegExUtils.replaceFirst(text, regex2, "");
    }

    public static String removeFirst(String text, String regex2) {
        return RegExUtils.replaceFirst(text, regex2, "");
    }

    public static String removePattern(String text, String regex2) {
        return RegExUtils.replacePattern(text, regex2, "");
    }

    public static String replaceAll(String text, Pattern regex2, String replacement) {
        if (ObjectUtils.anyNull(text, regex2, replacement)) {
            return text;
        }
        return regex2.matcher(text).replaceAll(replacement);
    }

    public static String replaceAll(String text, String regex2, String replacement) {
        if (ObjectUtils.anyNull(text, regex2, replacement)) {
            return text;
        }
        return text.replaceAll(regex2, replacement);
    }

    public static String replaceFirst(String text, Pattern regex2, String replacement) {
        if (text == null || regex2 == null || replacement == null) {
            return text;
        }
        return regex2.matcher(text).replaceFirst(replacement);
    }

    public static String replaceFirst(String text, String regex2, String replacement) {
        if (text == null || regex2 == null || replacement == null) {
            return text;
        }
        return text.replaceFirst(regex2, replacement);
    }

    public static String replacePattern(String text, String regex2, String replacement) {
        if (ObjectUtils.anyNull(text, regex2, replacement)) {
            return text;
        }
        return Pattern.compile(regex2, 32).matcher(text).replaceAll(replacement);
    }
}

