/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.grpc.Status;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerStream;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;

final class CancelServerStreamCommand
extends WriteQueue.AbstractQueuedCommand {
    private final NettyServerStream.TransportState stream;
    private final Status reason;
    private final PeerNotify peerNotify;

    private CancelServerStreamCommand(NettyServerStream.TransportState stream, Status reason, PeerNotify peerNotify) {
        this.stream = Preconditions.checkNotNull(stream, "stream");
        this.reason = Preconditions.checkNotNull(reason, "reason");
        this.peerNotify = Preconditions.checkNotNull(peerNotify, "peerNotify");
    }

    static CancelServerStreamCommand withReset(NettyServerStream.TransportState stream, Status reason) {
        return new CancelServerStreamCommand(stream, reason, PeerNotify.RESET);
    }

    static CancelServerStreamCommand withReason(NettyServerStream.TransportState stream, Status reason) {
        return new CancelServerStreamCommand(stream, reason, PeerNotify.BEST_EFFORT_STATUS);
    }

    NettyServerStream.TransportState stream() {
        return this.stream;
    }

    Status reason() {
        return this.reason;
    }

    boolean wantsHeaders() {
        return this.peerNotify == PeerNotify.BEST_EFFORT_STATUS;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CancelServerStreamCommand that = (CancelServerStreamCommand)o;
        return Objects.equal(this.stream, that.stream) && Objects.equal(this.reason, that.reason);
    }

    public int hashCode() {
        return Objects.hashCode(this.stream, this.reason);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("stream", this.stream).add("reason", this.reason).toString();
    }

    private static enum PeerNotify {
        RESET,
        BEST_EFFORT_STATUS;

    }
}

