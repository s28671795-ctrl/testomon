/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.Internal;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.SharedResourcePool;
import io.grpc.internal.TransportTracer;
import io.grpc.netty.shaded.io.grpc.netty.InternalProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;

@Internal
public final class InternalNettyChannelBuilder {
    public static void disableCheckAuthority(NettyChannelBuilder builder) {
        builder.disableCheckAuthority();
    }

    public static void enableCheckAuthority(NettyChannelBuilder builder) {
        builder.enableCheckAuthority();
    }

    public static void setProtocolNegotiatorFactory(NettyChannelBuilder builder, final ProtocolNegotiatorFactory protocolNegotiator) {
        builder.protocolNegotiatorFactory(new ProtocolNegotiator.ClientFactory(){

            @Override
            public ProtocolNegotiator newNegotiator() {
                return protocolNegotiator.buildProtocolNegotiator();
            }

            @Override
            public int getDefaultPort() {
                return 443;
            }
        });
    }

    public static void setProtocolNegotiatorFactory(NettyChannelBuilder builder, InternalProtocolNegotiator.ClientFactory protocolNegotiator) {
        builder.protocolNegotiatorFactory(protocolNegotiator);
    }

    public static void setStatsEnabled(NettyChannelBuilder builder, boolean value) {
        builder.setStatsEnabled(value);
    }

    public static void setTracingEnabled(NettyChannelBuilder builder, boolean value) {
        builder.setTracingEnabled(value);
    }

    public static void setStatsRecordStartedRpcs(NettyChannelBuilder builder, boolean value) {
        builder.setStatsRecordStartedRpcs(value);
    }

    public static void setStatsRecordFinishedRpcs(NettyChannelBuilder builder, boolean value) {
        builder.setStatsRecordFinishedRpcs(value);
    }

    public static void setStatsRecordRealTimeMetrics(NettyChannelBuilder builder, boolean value) {
        builder.setStatsRecordRealTimeMetrics(value);
    }

    public static void setStatsRecordRetryMetrics(NettyChannelBuilder builder, boolean value) {
        builder.setStatsRecordRetryMetrics(value);
    }

    public static void useNioTransport(NettyChannelBuilder builder) {
        builder.channelType(NioSocketChannel.class, InetSocketAddress.class);
        builder.eventLoopGroupPool(SharedResourcePool.forResource(Utils.NIO_WORKER_EVENT_LOOP_GROUP));
    }

    public static ClientTransportFactory buildTransportFactory(NettyChannelBuilder builder) {
        return builder.buildTransportFactory();
    }

    @VisibleForTesting
    public static void setTransportTracerFactory(NettyChannelBuilder builder, TransportTracer.Factory factory2) {
        builder.setTransportTracerFactory(factory2);
    }

    private InternalNettyChannelBuilder() {
    }

    public static interface ProtocolNegotiatorFactory {
        public InternalProtocolNegotiator.ProtocolNegotiator buildProtocolNegotiator();
    }
}

