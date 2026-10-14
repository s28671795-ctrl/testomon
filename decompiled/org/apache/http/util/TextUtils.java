/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.util;

public final class TextUtils {
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

    public static boolean containsBlanks(CharSequence s2) {
        if (s2 == null) {
            return false;
        }
        for (int i = 0; i < s2.length(); ++i) {
            if (!Character.isWhitespace(s2.charAt(i))) continue;
            return true;
        }
        return false;
    }
}

