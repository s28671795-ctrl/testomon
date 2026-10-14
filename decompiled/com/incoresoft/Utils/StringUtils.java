/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

public final class StringUtils {
    public static boolean isEmpty(CharSequence s2) {
        if (s2 == null) {
            return true;
        }
        return s2.length() == 0;
    }

    public static boolean isBlank(CharSequence s2) {
        if (s2 == null) {
            return true;
        }
        for (int i = 0; i < s2.length(); ++i) {
            if (Character.isWhitespace(s2.charAt(i))) continue;
            return false;
        }
        return true;
    }
}

