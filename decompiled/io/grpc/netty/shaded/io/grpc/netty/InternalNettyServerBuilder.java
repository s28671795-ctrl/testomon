/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.Attributes;
import io.grpc.Internal;
import io.grpc.internal.SharedResourcePool;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.channel.socket.nio.NioServerSocketChannel;

@Internal
public final class InternalNettyServerBuilder {
    public static void setStatsEnabled(NettyServerBuilder builder, boolean value) {
        builder.setStatsEnabled(value);
    }

    public static void setStatsRecordStartedRpcs(NettyServerBuilder builder, boolean value) {
        builder.setStatsRecordStartedRpcs(value);
    }

    public static void setStatsRecordRealTimeMetrics(NettyServerBuilder builder, boolean value) {
        builder.setStatsRecordRealTimeMetrics(value);
    }

    public static void setTracingEnabled(NettyServerBuilder builder, boolean value) {
        builder.setTracingEnabled(value);
    }

    public static void setForceHeapBuffer(NettyServerBuilder builder, boolean value) {
        builder.setForceHeapBuffer(value);
    }

    public static void useNioTransport(NettyServerBuilder builder) {
        builder.channelType(NioServerSocketChannel.class);
        builder.bossEventLoopGroupPool(SharedResourcePool.forResource(Utils.NIO_BOSS_EVENT_LOOP_GROUP));
        builder.workerEventLoopGroupPool(SharedResourcePool.forResource(Utils.NIO_WORKER_EVENT_LOOP_GROUP));
    }

    public static void eagAttributes(NettyServerBuilder builder, Attributes eagAttributes) {
        builder.eagAttributes(eagAttributes);
    }

    private InternalNettyServerBuilder() {
    }
}

