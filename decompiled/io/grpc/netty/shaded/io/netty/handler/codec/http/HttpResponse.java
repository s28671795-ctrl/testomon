/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpMessage;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpResponseStatus;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpVersion;

public interface HttpResponse
extends HttpMessage {
    @Deprecated
    public HttpResponseStatus getStatus();

    public HttpResponseStatus status();

    public HttpResponse setStatus(HttpResponseStatus var1);

    @Override
    public HttpResponse setProtocolVersion(HttpVersion var1);
}

