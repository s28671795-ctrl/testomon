/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpHeaderNames;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpHeaderValues;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpHeaders;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpMessage;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpMethod;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpRequest;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpResponse;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpVersion;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.grpc.netty.shaded.io.netty.util.NetUtil;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class HttpUtil {
    private static final AsciiString CHARSET_EQUALS = AsciiString.of(HttpHeaderValues.CHARSET + "=");
    private static final AsciiString SEMICOLON = AsciiString.cached(";");
    private static final String COMMA_STRING = String.valueOf(',');

    private HttpUtil() {
    }

    public static boolean isOriginForm(URI uri) {
        return HttpUtil.isOriginForm(uri.toString());
    }

    public static boolean isOriginForm(String uri) {
        return uri.startsWith("/");
    }

    public static boolean isAsteriskForm(URI uri) {
        return HttpUtil.isAsteriskForm(uri.toString());
    }

    public static boolean isAsteriskForm(String uri) {
        return "*".equals(uri);
    }

    public static boolean isKeepAlive(HttpMessage message) {
        return !message.headers().containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE, true) && (message.protocolVersion().isKeepAliveDefault() || message.headers().containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE, true));
    }

    public static void setKeepAlive(HttpMessage message, boolean keepAlive) {
        HttpUtil.setKeepAlive(message.headers(), message.protocolVersion(), keepAlive);
    }

    public static void setKeepAlive(HttpHeaders h2, HttpVersion httpVersion, boolean keepAlive) {
        if (httpVersion.isKeepAliveDefault()) {
            if (keepAlive) {
                h2.remove(HttpHeaderNames.CONNECTION);
            } else {
                h2.set((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.CLOSE);
            }
        } else if (keepAlive) {
            h2.set((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.KEEP_ALIVE);
        } else {
            h2.remove(HttpHeaderNames.CONNECTION);
        }
    }

    public static long getContentLength(HttpMessage message) {
        String value = message.headers().get(HttpHeaderNames.CONTENT_LENGTH);
        if (value != null) {
            return Long.parseLong(value);
        }
        long webSocketContentLength = HttpUtil.getWebSocketContentLength(message);
        if (webSocketContentLength >= 0L) {
            return webSocketContentLength;
        }
        throw new NumberFormatException("header not found: " + HttpHeaderNames.CONTENT_LENGTH);
    }

    public static long getContentLength(HttpMessage message, long defaultValue) {
        String value = message.headers().get(HttpHeaderNames.CONTENT_LENGTH);
        if (value != null) {
            return Long.parseLong(value);
        }
        long webSocketContentLength = HttpUtil.getWebSocketContentLength(message);
        if (webSocketContentLength >= 0L) {
            return webSocketContentLength;
        }
        return defaultValue;
    }

    public static int getContentLength(HttpMessage message, int defaultValue) {
        return (int)Math.min(Integer.MAX_VALUE, HttpUtil.getContentLength(message, (long)defaultValue));
    }

    private static int getWebSocketContentLength(HttpMessage message) {
        HttpResponse res;
        HttpHeaders h2 = message.headers();
        if (message instanceof HttpRequest) {
            HttpRequest req = (HttpRequest)message;
            if (HttpMethod.GET.equals(req.method()) && h2.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY1) && h2.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY2)) {
                return 8;
            }
        } else if (message instanceof HttpResponse && (res = (HttpResponse)message).status().code() == 101 && h2.contains(HttpHeaderNames.SEC_WEBSOCKET_ORIGIN) && h2.contains(HttpHeaderNames.SEC_WEBSOCKET_LOCATION)) {
            return 16;
        }
        return -1;
    }

    public static void setContentLength(HttpMessage message, long length) {
        message.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)length);
    }

    public static boolean isContentLengthSet(HttpMessage m4) {
        return m4.headers().contains(HttpHeaderNames.CONTENT_LENGTH);
    }

    public static boolean is100ContinueExpected(HttpMessage message) {
        return HttpUtil.isExpectHeaderValid(message) && message.headers().contains(HttpHeaderNames.EXPECT, HttpHeaderValues.CONTINUE, true);
    }

    static boolean isUnsupportedExpectation(HttpMessage message) {
        if (!HttpUtil.isExpectHeaderValid(message)) {
            return false;
        }
        String expectValue = message.headers().get(HttpHeaderNames.EXPECT);
        return expectValue != null && !HttpHeaderValues.CONTINUE.toString().equalsIgnoreCase(expectValue);
    }

    private static boolean isExpectHeaderValid(HttpMessage message) {
        return message instanceof HttpRequest && message.protocolVersion().compareTo(HttpVersion.HTTP_1_1) >= 0;
    }

    public static void set100ContinueExpected(HttpMessage message, boolean expected) {
        if (expected) {
            message.headers().set((CharSequence)HttpHeaderNames.EXPECT, (Object)HttpHeaderValues.CONTINUE);
        } else {
            message.headers().remove(HttpHeaderNames.EXPECT);
        }
    }

    public static boolean isTransferEncodingChunked(HttpMessage message) {
        return message.headers().containsValue(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED, true);
    }

    public static void setTransferEncodingChunked(HttpMessage m4, boolean chunked) {
        if (chunked) {
            m4.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, (Object)HttpHeaderValues.CHUNKED);
            m4.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
        } else {
            List<String> encodings = m4.headers().getAll(HttpHeaderNames.TRANSFER_ENCODING);
            if (encodings.isEmpty()) {
                return;
            }
            ArrayList<String> values2 = new ArrayList<String>(encodings);
            Iterator valuesIt = values2.iterator();
            while (valuesIt.hasNext()) {
                CharSequence value = (CharSequence)valuesIt.next();
                if (!HttpHeaderValues.CHUNKED.contentEqualsIgnoreCase(value)) continue;
                valuesIt.remove();
            }
            if (values2.isEmpty()) {
                m4.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            } else {
                m4.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, values2);
            }
        }
    }

    public static Charset getCharset(HttpMessage message) {
        return HttpUtil.getCharset(message, CharsetUtil.ISO_8859_1);
    }

    public static Charset getCharset(CharSequence contentTypeValue) {
        if (contentTypeValue != null) {
            return HttpUtil.getCharset(contentTypeValue, CharsetUtil.ISO_8859_1);
        }
        return CharsetUtil.ISO_8859_1;
    }

    public static Charset getCharset(HttpMessage message, Charset defaultCharset) {
        String contentTypeValue = message.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (contentTypeValue != null) {
            return HttpUtil.getCharset(contentTypeValue, defaultCharset);
        }
        return defaultCharset;
    }

    public static Charset getCharset(CharSequence contentTypeValue, Charset defaultCharset) {
        CharSequence charsetRaw;
        if (contentTypeValue != null && (charsetRaw = HttpUtil.getCharsetAsSequence(contentTypeValue)) != null) {
            if (charsetRaw.length() > 2 && charsetRaw.charAt(0) == '\"' && charsetRaw.charAt(charsetRaw.length() - 1) == '\"') {
                charsetRaw = charsetRaw.subSequence(1, charsetRaw.length() - 1);
            }
            try {
                return Charset.forName(charsetRaw.toString());
            }
            catch (IllegalCharsetNameException illegalCharsetNameException) {
            }
            catch (UnsupportedCharsetException unsupportedCharsetException) {
                // empty catch block
            }
        }
        return defaultCharset;
    }

    @Deprecated
    public static CharSequence getCharsetAsString(HttpMessage message) {
        return HttpUtil.getCharsetAsSequence(message);
    }

    public static CharSequence getCharsetAsSequence(HttpMessage message) {
        String contentTypeValue = message.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (contentTypeValue != null) {
            return HttpUtil.getCharsetAsSequence(contentTypeValue);
        }
        return null;
    }

    public static CharSequence getCharsetAsSequence(CharSequence contentTypeValue) {
        ObjectUtil.checkNotNull(contentTypeValue, "contentTypeValue");
        int indexOfCharset = AsciiString.indexOfIgnoreCaseAscii(contentTypeValue, CHARSET_EQUALS, 0);
        if (indexOfCharset == -1) {
            return null;
        }
        int indexOfEncoding = indexOfCharset + CHARSET_EQUALS.length();
        if (indexOfEncoding < contentTypeValue.length()) {
            CharSequence charsetCandidate = contentTypeValue.subSequence(indexOfEncoding, contentTypeValue.length());
            int indexOfSemicolon = AsciiString.indexOfIgnoreCaseAscii(charsetCandidate, SEMICOLON, 0);
            if (indexOfSemicolon == -1) {
                return charsetCandidate;
            }
            return charsetCandidate.subSequence(0, indexOfSemicolon);
        }
        return null;
    }

    public static CharSequence getMimeType(HttpMessage message) {
        String contentTypeValue = message.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (contentTypeValue != null) {
            return HttpUtil.getMimeType(contentTypeValue);
        }
        return null;
    }

    public static CharSequence getMimeType(CharSequence contentTypeValue) {
        ObjectUtil.checkNotNull(contentTypeValue, "contentTypeValue");
        int indexOfSemicolon = AsciiString.indexOfIgnoreCaseAscii(contentTypeValue, SEMICOLON, 0);
        if (indexOfSemicolon != -1) {
            return contentTypeValue.subSequence(0, indexOfSemicolon);
        }
        return contentTypeValue.length() > 0 ? contentTypeValue : null;
    }

    public static String formatHostnameForHttp(InetSocketAddress addr) {
        String hostString = NetUtil.getHostname(addr);
        if (NetUtil.isValidIpV6Address(hostString)) {
            if (!addr.isUnresolved()) {
                hostString = NetUtil.toAddressString(addr.getAddress());
            }
            return '[' + hostString + ']';
        }
        return hostString;
    }

    public static long normalizeAndGetContentLength(List<? extends CharSequence> contentLengthFields, boolean isHttp10OrEarlier, boolean allowDuplicateContentLengths) {
        boolean multipleContentLengths;
        if (contentLengthFields.isEmpty()) {
            return -1L;
        }
        String firstField = contentLengthFields.get(0).toString();
        boolean bl = multipleContentLengths = contentLengthFields.size() > 1 || firstField.indexOf(44) >= 0;
        if (multipleContentLengths && !isHttp10OrEarlier) {
            if (allowDuplicateContentLengths) {
                String firstValue = null;
                for (CharSequence charSequence : contentLengthFields) {
                    String[] tokens;
                    for (String token : tokens = charSequence.toString().split(COMMA_STRING, -1)) {
                        String trimmed = token.trim();
                        if (firstValue == null) {
                            firstValue = trimmed;
                            continue;
                        }
                        if (trimmed.equals(firstValue)) continue;
                        throw new IllegalArgumentException("Multiple Content-Length values found: " + contentLengthFields);
                    }
                }
                firstField = firstValue;
            } else {
                throw new IllegalArgumentException("Multiple Content-Length values found: " + contentLengthFields);
            }
        }
        if (firstField.isEmpty() || !Character.isDigit(firstField.charAt(0))) {
            throw new IllegalArgumentException("Content-Length value is not a number: " + firstField);
        }
        try {
            long value = Long.parseLong(firstField);
            return ObjectUtil.checkPositiveOrZero(value, "Content-Length value");
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Content-Length value is not a number: " + firstField, e);
        }
    }
}

