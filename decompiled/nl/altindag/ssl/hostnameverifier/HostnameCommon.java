/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.hostnameverifier;

import java.util.regex.Pattern;

final class HostnameCommon {
    private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    private HostnameCommon() {
    }

    static boolean canParseAsIpAddress(String value) {
        return VERIFY_AS_IP_ADDRESS.matcher(value).matches();
    }

    static boolean containsInvalidLabelLengths(String hostname) {
        if (hostname.length() < 1 || hostname.length() > 253) {
            return true;
        }
        int labelStart = 0;
        while (true) {
            int dot;
            int labelLength;
            if ((labelLength = (dot = hostname.indexOf(46, labelStart)) == -1 ? hostname.length() - labelStart : dot - labelStart) < 1 || labelLength > 63) {
                return true;
            }
            if (dot == -1 || dot == hostname.length() - 1) break;
            labelStart = dot + 1;
        }
        return false;
    }

    static boolean containsInvalidHostnameAsciiCodes(String hostname) {
        for (int i = 0; i < hostname.length(); ++i) {
            char c = hostname.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                return true;
            }
            if (" #%/:?@[\\]".indexOf(c) == -1) continue;
            return true;
        }
        return false;
    }
}

