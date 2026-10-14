/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FrameStream;

public interface Http2FrameStreamVisitor {
    public boolean visit(Http2FrameStream var1);
}

