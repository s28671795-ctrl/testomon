/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Attributes;
import io.grpc.Status;
import io.grpc.internal.ClientTransport;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface ManagedClientTransport
extends ClientTransport {
    @CheckReturnValue
    @Nullable
    public Runnable start(Listener var1);

    public void shutdown(Status var1);

    public void shutdownNow(Status var1);

    public static interface Listener {
        public void transportShutdown(Status var1);

        public void transportTerminated();

        public void transportReady();

        public void transportInUse(boolean var1);

        default public Attributes filterTransport(Attributes attributes) {
            return attributes;
        }
    }
}

