/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import kotlin.Metadata;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lio/javalin/openapi/HttpMethod;", "", "(Ljava/lang/String;I)V", "POST", "GET", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS", "TRACE", "openapi-specification"})
public final class HttpMethod
extends Enum<HttpMethod> {
    public static final /* enum */ HttpMethod POST = new HttpMethod();
    public static final /* enum */ HttpMethod GET = new HttpMethod();
    public static final /* enum */ HttpMethod PUT = new HttpMethod();
    public static final /* enum */ HttpMethod PATCH = new HttpMethod();
    public static final /* enum */ HttpMethod DELETE = new HttpMethod();
    public static final /* enum */ HttpMethod HEAD = new HttpMethod();
    public static final /* enum */ HttpMethod OPTIONS = new HttpMethod();
    public static final /* enum */ HttpMethod TRACE = new HttpMethod();
    private static final /* synthetic */ HttpMethod[] $VALUES;

    public static HttpMethod[] values() {
        return (HttpMethod[])$VALUES.clone();
    }

    public static HttpMethod valueOf(String value) {
        return Enum.valueOf(HttpMethod.class, value);
    }

    static {
        $VALUES = httpMethodArray = new HttpMethod[]{HttpMethod.POST, HttpMethod.GET, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS, HttpMethod.TRACE};
    }
}

