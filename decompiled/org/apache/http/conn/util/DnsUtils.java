/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.conn.util;

public class DnsUtils {
    private DnsUtils() {
    }

    private static boolean isUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static String normalize(String s2) {
        int remaining;
        if (s2 == null) {
            return null;
        }
        int pos = 0;
        for (remaining = s2.length(); remaining > 0 && !DnsUtils.isUpper(s2.charAt(pos)); --remaining) {
            ++pos;
        }
        if (remaining > 0) {
            StringBuilder buf = new StringBuilder(s2.length());
            buf.append(s2, 0, pos);
            while (remaining > 0) {
                char c = s2.charAt(pos);
                if (DnsUtils.isUpper(c)) {
                    buf.append((char)(c + 32));
                } else {
                    buf.append(c);
                }
                ++pos;
                --remaining;
            }
            return buf.toString();
        }
        return s2;
    }
}

