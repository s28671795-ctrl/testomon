/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.cxf.common.util.StringUtils;

public final class UrlUtils {
    private static final int RADIX = 16;
    private static final byte ESCAPE_CHAR = 37;
    private static final byte PLUS_CHAR = 43;

    private UrlUtils() {
    }

    public static String urlEncode(String value) {
        return UrlUtils.urlEncode(value, StandardCharsets.UTF_8.name());
    }

    public static String urlEncode(String value, String enc) {
        try {
            return URLEncoder.encode(value, enc);
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String urlDecode(String value, String enc) {
        return UrlUtils.urlDecode(value, enc, false);
    }

    private static String urlDecode(String value, String enc, boolean isPath) {
        boolean needDecode = false;
        int escapesCount = 0;
        int i = 0;
        int length = value.length();
        while (i < length) {
            char ch;
            if ((ch = value.charAt(i++)) == '%') {
                ++escapesCount;
                i += 2;
                needDecode = true;
                continue;
            }
            if (isPath || ch != '+') continue;
            needDecode = true;
        }
        if (needDecode) {
            byte[] valueBytes = StringUtils.toBytes(value, enc);
            ByteBuffer in = ByteBuffer.wrap(valueBytes);
            ByteBuffer out = ByteBuffer.allocate(in.capacity() - 2 * escapesCount + 1);
            while (in.hasRemaining()) {
                byte b = in.get();
                if (!isPath && b == 43) {
                    out.put((byte)32);
                    continue;
                }
                if (b == 37) {
                    try {
                        int u = UrlUtils.digit16(in.get());
                        int l = UrlUtils.digit16(in.get());
                        out.put((byte)((u << 4) + l));
                        continue;
                    }
                    catch (BufferUnderflowException e) {
                        throw new IllegalArgumentException("Invalid URL encoding: Incomplete trailing escape (%) pattern");
                    }
                }
                out.put(b);
            }
            ((Buffer)out).flip();
            return Charset.forName(enc).decode(out).toString();
        }
        return value;
    }

    private static int digit16(byte b) {
        int i = Character.digit((char)b, 16);
        if (i == -1) {
            throw new IllegalArgumentException("Invalid URL encoding: not a valid digit (radix 16): " + b);
        }
        return i;
    }

    public static String urlDecode(String value) {
        return UrlUtils.urlDecode(value, StandardCharsets.UTF_8.name());
    }

    public static String pathDecode(String value) {
        return UrlUtils.urlDecode(value, StandardCharsets.UTF_8.name(), true);
    }

    public static Map<String, String> parseQueryString(String s2) {
        HashMap<String, String> ht = new HashMap<String, String>();
        StringTokenizer st = new StringTokenizer(s2, "&");
        while (st.hasMoreTokens()) {
            String pair = st.nextToken();
            int pos = pair.indexOf(61);
            if (pos == -1) {
                ht.put(pair.toLowerCase(), "");
                continue;
            }
            ht.put(pair.substring(0, pos).toLowerCase(), pair.substring(pos + 1));
        }
        return ht;
    }

    public static String getStem(String baseURI) {
        int idx = baseURI.lastIndexOf(47);
        String result = baseURI;
        if (idx != -1) {
            result = baseURI.substring(0, idx);
        }
        return result;
    }
}

