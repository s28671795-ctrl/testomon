/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Stream;

public interface Http2FrameStream {
    public int id();

    public Http2Stream.State state();
}

