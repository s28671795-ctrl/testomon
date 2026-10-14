/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Rfc5987Util {
    private static final Pattern ENCODED_VALUE_PATTERN = Pattern.compile("%[0-9a-f]{2}|\\S", 2);

    private Rfc5987Util() {
    }

    public static String encode(String s2) throws UnsupportedEncodingException {
        return Rfc5987Util.encode(s2, StandardCharsets.UTF_8.name());
    }

    public static String encode(String s2, String encoding) throws UnsupportedEncodingException {
        byte[] rawBytes = s2.getBytes(encoding);
        int len = rawBytes.length;
        StringBuilder sb = new StringBuilder(len << 1);
        char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] attributeChars = new byte[]{33, 35, 36, 38, 43, 45, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 124, 126};
        for (byte b : rawBytes) {
            if (Arrays.binarySearch(attributeChars, b) >= 0) {
                sb.append((char)b);
                continue;
            }
            sb.append('%');
            sb.append(digits[0xF & b >>> 4]);
            sb.append(digits[b & 0xF]);
        }
        return sb.toString();
    }

    public static String decode(String s2, String encoding) throws UnsupportedEncodingException {
        Matcher matcher = ENCODED_VALUE_PATTERN.matcher(s2);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (matcher.find()) {
            String matched = matcher.group();
            if (matched.startsWith("%")) {
                Integer value = Integer.parseInt(matched.substring(1), 16);
                bos.write(value);
                continue;
            }
            bos.write(matched.charAt(0));
        }
        return new String(bos.toByteArray(), encoding);
    }
}

