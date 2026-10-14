/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class HttpHeaderHelper {
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_ID = "Content-ID";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    public static final String COOKIE = "Cookie";
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String CHUNKED = "chunked";
    public static final String CONNECTION = "Connection";
    public static final String CLOSE = "close";
    public static final String AUTHORIZATION = "Authorization";
    static final String ISO88591 = StandardCharsets.ISO_8859_1.name();
    private static Map<String, String> internalHeaders = new HashMap<String, String>();
    private static ConcurrentHashMap<String, String> encodings = new ConcurrentHashMap();

    private HttpHeaderHelper() {
    }

    public static List<String> getHeader(Map<String, List<String>> headerMap, String key) {
        return headerMap.get(HttpHeaderHelper.getHeaderKey(key));
    }

    public static String getHeaderKey(String key) {
        String headerKey = internalHeaders.get(key);
        return headerKey == null ? key : headerKey;
    }

    public static String findCharset(String contentType) {
        if (contentType == null) {
            return null;
        }
        int idx = contentType.indexOf("charset=");
        if (idx != -1) {
            String charset = contentType.substring(idx + 8);
            if (charset.indexOf(59) != -1) {
                charset = charset.substring(0, charset.indexOf(59)).trim();
            }
            if (charset.isEmpty()) {
                return null;
            }
            if (charset.charAt(0) == '\"') {
                charset = charset.substring(1, charset.length() - 1);
            }
            return charset;
        }
        return null;
    }

    public static String mapCharset(String enc) {
        return HttpHeaderHelper.mapCharset(enc, ISO88591);
    }

    public static String mapCharset(String enc, String deflt) {
        if (enc == null) {
            return deflt;
        }
        int idx = enc.indexOf(59);
        if (idx != -1) {
            enc = enc.substring(0, idx);
        }
        if ((enc = enc.replace('\"', ' ').replace('\'', ' ').trim()).isEmpty()) {
            return deflt;
        }
        String newenc = encodings.get(enc);
        if (newenc == null) {
            try {
                newenc = Charset.forName(enc).name();
            }
            catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
                return null;
            }
            String tmpenc = encodings.putIfAbsent(enc, newenc);
            if (tmpenc != null) {
                newenc = tmpenc;
            }
        }
        return newenc;
    }

    static {
        internalHeaders.put(ACCEPT_ENCODING, "accept-encoding");
        internalHeaders.put(CONTENT_ENCODING, "content-encoding");
        internalHeaders.put(CONTENT_TYPE, "content-type");
        internalHeaders.put(CONTENT_ID, "content-id");
        internalHeaders.put(CONTENT_TRANSFER_ENCODING, "content-transfer-encoding");
        internalHeaders.put(TRANSFER_ENCODING, "transfer-encoding");
        internalHeaders.put(CONNECTION, "connection");
        internalHeaders.put("authorization", AUTHORIZATION);
        internalHeaders.put("soapaction", "SOAPAction");
        internalHeaders.put("accept", "Accept");
        internalHeaders.put("content-length", CONTENT_LENGTH);
    }
}

