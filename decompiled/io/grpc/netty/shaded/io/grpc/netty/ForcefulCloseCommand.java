/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.Status;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;

class ForcefulCloseCommand
extends WriteQueue.AbstractQueuedCommand {
    private final Status status;

    public ForcefulCloseCommand(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }
}

