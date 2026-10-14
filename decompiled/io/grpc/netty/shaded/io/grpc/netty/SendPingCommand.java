/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.internal.ClientTransport;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import java.util.concurrent.Executor;

class SendPingCommand
extends WriteQueue.AbstractQueuedCommand {
    private final ClientTransport.PingCallback callback;
    private final Executor executor;

    SendPingCommand(ClientTransport.PingCallback callback, Executor executor2) {
        this.callback = callback;
        this.executor = executor2;
    }

    ClientTransport.PingCallback callback() {
        return this.callback;
    }

    Executor executor() {
        return this.executor;
    }
}

