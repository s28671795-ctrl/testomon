/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.ldaptive.LdapUtils;
import org.ldaptive.ResultCode;
import org.ldaptive.filter.FilterParseException;

public final class FilterUtils {
    private FilterUtils() {
    }

    public static String escape(String s2) {
        StringBuilder sb = new StringBuilder(s2.length());
        byte[] utf8 = LdapUtils.utf8Encode(s2, false);
        if (s2.length() == utf8.length) {
            for (byte b : utf8) {
                if (b <= 31 || b == 40 || b == 41 || b == 42 || b == 92 || b == 127) {
                    sb.append('\\').append(LdapUtils.hexEncode(b));
                    continue;
                }
                sb.append((char)b);
            }
        } else {
            int multiByte = 0;
            for (byte b : utf8) {
                if (multiByte > 0) {
                    sb.append('\\').append(LdapUtils.hexEncode(b));
                    --multiByte;
                    continue;
                }
                if ((b & 0x7F) == b) {
                    if (b <= 31 || b == 40 || b == 41 || b == 42 || b == 92 || b == 127) {
                        sb.append('\\').append(LdapUtils.hexEncode(b));
                        continue;
                    }
                    sb.append((char)b);
                    continue;
                }
                if ((b & 0xE0) == 192) {
                    multiByte = 1;
                } else if ((b & 0xF0) == 224) {
                    multiByte = 2;
                } else if ((b & 0xF8) == 240) {
                    multiByte = 3;
                } else {
                    throw new IllegalStateException("Could not read UTF-8 string encoding");
                }
                sb.append('\\').append(LdapUtils.hexEncode(b));
            }
        }
        return sb.toString();
    }

    public static byte[][] parseAssertionValue(String ... value) throws FilterParseException {
        byte[][] bytes = new byte[value.length][];
        for (int i = 0; i < value.length; ++i) {
            bytes[i] = FilterUtils.parseAssertionValue(value[i]);
        }
        return bytes;
    }

    public static byte[] parseAssertionValue(String value) throws FilterParseException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(value.length());
        for (int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if (c == '\u0000' || c == '(' || c == ')') {
                throw new FilterParseException(ResultCode.FILTER_ERROR, "Assertion value contains unescaped characters");
            }
            if (c == '\\') {
                char[] hexValue = new char[]{value.charAt(++i), value.charAt(++i)};
                try {
                    bytes.write(LdapUtils.hexDecode(hexValue));
                    continue;
                }
                catch (IOException e) {
                    throw new FilterParseException(ResultCode.FILTER_ERROR, "Could not hex decode " + Arrays.toString(hexValue) + " in " + value);
                }
            }
            bytes.write(c);
        }
        return bytes.toByteArray();
    }
}

