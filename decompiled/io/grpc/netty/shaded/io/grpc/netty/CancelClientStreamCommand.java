/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.Status;
import io.grpc.netty.shaded.io.grpc.netty.NettyClientStream;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import javax.annotation.Nullable;

class CancelClientStreamCommand
extends WriteQueue.AbstractQueuedCommand {
    private final NettyClientStream.TransportState stream;
    @Nullable
    private final Status reason;

    CancelClientStreamCommand(NettyClientStream.TransportState stream, Status reason) {
        this.stream = Preconditions.checkNotNull(stream, "stream");
        Preconditions.checkArgument(reason == null || !reason.isOk(), "Should not cancel with OK status");
        this.reason = reason;
    }

    NettyClientStream.TransportState stream() {
        return this.stream;
    }

    @Nullable
    Status reason() {
        return this.reason;
    }
}

