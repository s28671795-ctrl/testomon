/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.util;

public class StringUtils {
    public static String replace(String text, String repl, String with) {
        return StringUtils.replace(text, repl, with, -1);
    }

    public static String replace(String text, String repl, String with, int max) {
        int end;
        if (text == null || repl == null || with == null || repl.length() == 0) {
            return text;
        }
        StringBuilder buf = new StringBuilder(text.length());
        int start2 = 0;
        while ((end = text.indexOf(repl, start2)) != -1) {
            buf.append(text, start2, end).append(with);
            start2 = end + repl.length();
            if (--max != 0) continue;
        }
        buf.append(text, start2, text.length());
        return buf.toString();
    }

    public static String capitalizeFirstLetter(String data) {
        char titleCase;
        char firstChar = data.charAt(0);
        if (firstChar == (titleCase = Character.toTitleCase(firstChar))) {
            return data;
        }
        StringBuilder result = new StringBuilder(data.length());
        result.append(titleCase);
        result.append(data, 1, data.length());
        return result.toString();
    }
}

