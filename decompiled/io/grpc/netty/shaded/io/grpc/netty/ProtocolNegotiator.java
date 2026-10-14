/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.internal.ObjectPool;
import io.grpc.netty.shaded.io.grpc.netty.GrpcHttp2ConnectionHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.util.AsciiString;
import java.util.concurrent.Executor;

interface ProtocolNegotiator {
    public AsciiString scheme();

    public ChannelHandler newHandler(GrpcHttp2ConnectionHandler var1);

    public void close();

    public static interface ServerFactory {
        public ProtocolNegotiator newNegotiator(ObjectPool<? extends Executor> var1);
    }

    public static interface ClientFactory {
        public ProtocolNegotiator newNegotiator();

        public int getDefaultPort();
    }
}

