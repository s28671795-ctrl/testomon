/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

public class StringUtils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !StringUtils.isNullOrEmpty(str);
    }

    public static String format(String str, Object ... args2) {
        str = str.replaceAll("\\{}", "%s");
        return String.format(str, args2);
    }

    public static String addStart(String str, String add) {
        if (StringUtils.isNullOrEmpty(add)) {
            return str;
        }
        if (StringUtils.isNullOrEmpty(str)) {
            return add;
        }
        if (!str.startsWith(add)) {
            return add + str;
        }
        return str;
    }
}

