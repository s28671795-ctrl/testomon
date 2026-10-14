/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.handler.codec.http.Cookie;

@Deprecated
public final class ClientCookieEncoder {
    @Deprecated
    public static String encode(String name, String value) {
        return io.grpc.netty.shaded.io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(name, value);
    }

    @Deprecated
    public static String encode(Cookie cookie) {
        return io.grpc.netty.shaded.io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode((io.grpc.netty.shaded.io.netty.handler.codec.http.cookie.Cookie)cookie);
    }

    @Deprecated
    public static String encode(Cookie ... cookies) {
        return io.grpc.netty.shaded.io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(cookies);
    }

    @Deprecated
    public static String encode(Iterable<Cookie> cookies) {
        return io.grpc.netty.shaded.io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(cookies);
    }

    private ClientCookieEncoder() {
    }
}

