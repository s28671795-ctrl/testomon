/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util.internal;

import java.util.Objects;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isBlank(CharSequence charSequence) {
        int length;
        int n = length = Objects.isNull(charSequence) ? 0 : charSequence.length();
        if (length != 0) {
            for (int i = 0; i < length; ++i) {
                if (Character.isWhitespace(charSequence.charAt(i))) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !StringUtils.isBlank(charSequence);
    }
}

