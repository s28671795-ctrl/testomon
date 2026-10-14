/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http;

import java.nio.ByteBuffer;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Index;

public enum HttpScheme {
    HTTP("http", 80),
    HTTPS("https", 443),
    WS("ws", 80),
    WSS("wss", 443);

    public static final Index<HttpScheme> CACHE;
    private final String _string;
    private final ByteBuffer _buffer;
    private final int _defaultPort;

    private HttpScheme(String s2, int port) {
        this._string = s2;
        this._buffer = BufferUtil.toBuffer(s2);
        this._defaultPort = port;
    }

    public ByteBuffer asByteBuffer() {
        return this._buffer.asReadOnlyBuffer();
    }

    public boolean is(String s2) {
        return this._string.equalsIgnoreCase(s2);
    }

    public String asString() {
        return this._string;
    }

    public int getDefaultPort() {
        return this._defaultPort;
    }

    public int normalizePort(int port) {
        return port == this._defaultPort ? 0 : port;
    }

    public String toString() {
        return this._string;
    }

    public static int getDefaultPort(String scheme2) {
        HttpScheme httpScheme = scheme2 == null ? null : CACHE.get(scheme2);
        return httpScheme == null ? HTTP.getDefaultPort() : httpScheme.getDefaultPort();
    }

    public static int normalizePort(String scheme2, int port) {
        HttpScheme httpScheme = scheme2 == null ? null : CACHE.get(scheme2);
        return httpScheme == null ? port : httpScheme.normalizePort(port);
    }

    static {
        CACHE = new Index.Builder().caseSensitive(false).withAll(HttpScheme.values(), HttpScheme::asString).build();
    }
}

