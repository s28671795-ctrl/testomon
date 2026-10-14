/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Frame;

public interface Http2PingFrame
extends Http2Frame {
    public boolean ack();

    public long content();
}

