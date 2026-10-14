/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.rtsp;

import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.handler.codec.http.FullHttpMessage;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpMessage;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpObjectEncoder;

@ChannelHandler.Sharable
@Deprecated
public abstract class RtspObjectEncoder<H extends HttpMessage>
extends HttpObjectEncoder<H> {
    protected RtspObjectEncoder() {
    }

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return msg instanceof FullHttpMessage;
    }
}

