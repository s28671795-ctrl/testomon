/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Context;
import io.grpc.ExperimentalApi;
import io.grpc.ServerServiceDefinition;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class Server {
    static final Context.Key<Server> SERVER_CONTEXT_KEY = Context.key("io.grpc.Server");

    public abstract Server start() throws IOException;

    public int getPort() {
        return -1;
    }

    public List<? extends SocketAddress> getListenSockets() {
        throw new UnsupportedOperationException();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2222")
    public List<ServerServiceDefinition> getServices() {
        return Collections.emptyList();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2222")
    public List<ServerServiceDefinition> getImmutableServices() {
        return Collections.emptyList();
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2222")
    public List<ServerServiceDefinition> getMutableServices() {
        return Collections.emptyList();
    }

    public abstract Server shutdown();

    public abstract Server shutdownNow();

    public abstract boolean isShutdown();

    public abstract boolean isTerminated();

    public abstract boolean awaitTermination(long var1, TimeUnit var3) throws InterruptedException;

    public abstract void awaitTermination() throws InterruptedException;
}

