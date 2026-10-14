/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.util;

public final class TextUtils {
    public static String quote(String text) {
        if (text == null) {
            return "<null>";
        }
        return "\"" + text + "\"";
    }

    public static String hint(String text) {
        if (text == null) {
            return "<null>";
        }
        return "\"" + TextUtils.maxStringLength(30, text) + "\"";
    }

    public static String maxStringLength(int max, String raw) {
        int length = raw.length();
        if (length <= max) {
            return raw;
        }
        if (max < 9) {
            return raw.substring(0, max);
        }
        StringBuilder ret = new StringBuilder();
        int startLen = (int)Math.round((double)max / 3.0);
        ret.append(raw.substring(0, startLen));
        ret.append("...");
        ret.append(raw.substring(length - (max - startLen - 3)));
        return ret.toString();
    }
}

