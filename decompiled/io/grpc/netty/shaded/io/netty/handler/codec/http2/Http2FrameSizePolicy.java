/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Exception;

public interface Http2FrameSizePolicy {
    public void maxFrameSize(int var1) throws Http2Exception;

    public int maxFrameSize();
}

