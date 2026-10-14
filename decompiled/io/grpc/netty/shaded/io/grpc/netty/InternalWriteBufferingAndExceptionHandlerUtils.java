/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.Internal;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientHandler;
import io.grpc.netty.shaded.io.netty.channel.Channel;

@Internal
public final class InternalWriteBufferingAndExceptionHandlerUtils {
    public static void writeBufferingAndRemove(Channel channel) {
        NettyClientHandler.writeBufferingAndRemove(channel);
    }
}

