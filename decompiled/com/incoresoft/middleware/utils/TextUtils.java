/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.utils;

public class TextUtils {
    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        return charSequence.length() == 0;
    }

    public static boolean isBlank(CharSequence charSequence) {
        if (charSequence != null) {
            for (int i = 0; i < charSequence.length(); ++i) {
                if (Character.isWhitespace(charSequence.charAt(i))) continue;
                return false;
            }
        }
        return true;
    }
}

