/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.Status;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;

class GracefulCloseCommand
extends WriteQueue.AbstractQueuedCommand {
    private final Status status;

    public GracefulCloseCommand(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }
}

