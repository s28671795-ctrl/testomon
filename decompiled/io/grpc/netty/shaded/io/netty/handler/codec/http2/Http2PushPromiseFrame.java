/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FrameStream;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2StreamFrame;

public interface Http2PushPromiseFrame
extends Http2StreamFrame {
    public Http2StreamFrame pushStream(Http2FrameStream var1);

    public Http2FrameStream pushStream();

    public Http2Headers http2Headers();

    public int padding();

    public int promisedStreamId();

    @Override
    public Http2PushPromiseFrame stream(Http2FrameStream var1);
}

