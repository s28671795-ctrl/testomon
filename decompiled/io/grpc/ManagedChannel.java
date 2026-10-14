/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Channel;
import io.grpc.ConnectivityState;
import io.grpc.ExperimentalApi;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class ManagedChannel
extends Channel {
    public abstract ManagedChannel shutdown();

    public abstract boolean isShutdown();

    public abstract boolean isTerminated();

    public abstract ManagedChannel shutdownNow();

    public abstract boolean awaitTermination(long var1, TimeUnit var3) throws InterruptedException;

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4359")
    public ConnectivityState getState(boolean requestConnection) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4359")
    public void notifyWhenStateChanged(ConnectivityState source2, Runnable callback) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4056")
    public void resetConnectBackoff() {
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4056")
    public void enterIdle() {
    }
}

