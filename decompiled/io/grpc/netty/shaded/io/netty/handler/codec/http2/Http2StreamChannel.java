/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FrameStream;

public interface Http2StreamChannel
extends Channel {
    public Http2FrameStream stream();
}

