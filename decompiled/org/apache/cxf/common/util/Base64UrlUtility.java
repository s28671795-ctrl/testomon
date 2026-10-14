/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;

public final class Base64UrlUtility {
    private Base64UrlUtility() {
    }

    public static byte[] decode(String encoded) throws Base64Exception {
        return Base64Utility.decode(encoded, true);
    }

    public static String encode(String str) {
        return Base64UrlUtility.encode(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String encode(byte[] id) {
        return Base64UrlUtility.encodeChunk(id, 0, id.length);
    }

    public static String encodeChunk(byte[] id, int offset, int length) {
        char[] chunk = Base64Utility.encodeChunk(id, offset, length, true);
        if (chunk != null) {
            return new String(chunk);
        }
        return null;
    }

    public static void encodeAndStream(byte[] id, int o, int l, OutputStream os) throws IOException {
        Base64Utility.encodeAndStream(id, o, l, true, os);
    }
}

