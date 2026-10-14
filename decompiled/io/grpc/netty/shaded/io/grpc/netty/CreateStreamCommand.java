/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientStream;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Headers;

class CreateStreamCommand
extends WriteQueue.AbstractQueuedCommand {
    private final Http2Headers headers;
    private final NettyClientStream.TransportState stream;
    private final boolean shouldBeCountedForInUse;
    private final boolean get;

    CreateStreamCommand(Http2Headers headers, NettyClientStream.TransportState stream, boolean shouldBeCountedForInUse, boolean get2) {
        this.stream = Preconditions.checkNotNull(stream, "stream");
        this.headers = Preconditions.checkNotNull(headers, "headers");
        this.shouldBeCountedForInUse = shouldBeCountedForInUse;
        this.get = get2;
    }

    NettyClientStream.TransportState stream() {
        return this.stream;
    }

    Http2Headers headers() {
        return this.headers;
    }

    boolean shouldBeCountedForInUse() {
        return this.shouldBeCountedForInUse;
    }

    boolean isGet() {
        return this.get;
    }
}

