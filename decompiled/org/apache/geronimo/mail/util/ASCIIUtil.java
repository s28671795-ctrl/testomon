/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASCIIUtil {
    public static boolean isAscii(String s2) {
        for (int i = 0; i < s2.length(); ++i) {
            if (ASCIIUtil.isAscii(s2.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isAscii(int ch) {
        if (ch == 13 || ch == 10 || ch == 9) {
            return true;
        }
        return ch < 127 && ch >= 32;
    }

    public static String getTextTransferEncoding(InputStream content) throws IOException {
        int ch;
        BufferedInputStream in = new BufferedInputStream(content, 4096);
        int span = 0;
        boolean containsLongLines = false;
        int asciiChars = 0;
        int nonAsciiChars = 0;
        while ((ch = in.read()) != -1) {
            if (ch == 10 || ch == 13) {
                span = 0;
                continue;
            }
            if (++span > 998) {
                containsLongLines = true;
            }
            if (!ASCIIUtil.isAscii(ch)) {
                ++nonAsciiChars;
                continue;
            }
            ++asciiChars;
        }
        if (nonAsciiChars == 0) {
            if (containsLongLines) {
                return "quoted-printable";
            }
            return "7bit";
        }
        if (nonAsciiChars > asciiChars) {
            return "base64";
        }
        return "quoted-printable";
    }

    public static String getTextTransferEncoding(String content) {
        int asciiChars = 0;
        int nonAsciiChars = 0;
        for (int i = 0; i < content.length(); ++i) {
            char ch = content.charAt(i);
            if (!ASCIIUtil.isAscii(ch)) {
                ++nonAsciiChars;
                continue;
            }
            ++asciiChars;
        }
        if (nonAsciiChars == 0) {
            return "7bit";
        }
        if (nonAsciiChars > asciiChars) {
            return "base64";
        }
        return "quoted-printable";
    }

    public static String getBinaryTransferEncoding(InputStream content) throws IOException {
        BufferedInputStream in = new BufferedInputStream(content, 4096);
        int previousChar = 0;
        int span = 0;
        int ch;
        while ((ch = in.read()) != -1) {
            if (ch == 10) {
                if (previousChar != 13) {
                    return "base64";
                }
                span = 0;
            } else {
                if (++span > 998) {
                    return "base64";
                }
                if (!ASCIIUtil.isAscii(ch)) {
                    return "base64";
                }
            }
            previousChar = ch;
        }
        return "7bit";
    }
}

