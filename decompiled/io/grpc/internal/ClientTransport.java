/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.CallOptions;
import io.grpc.ClientStreamTracer;
import io.grpc.InternalChannelz;
import io.grpc.InternalInstrumented;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.internal.ClientStream;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface ClientTransport
extends InternalInstrumented<InternalChannelz.SocketStats> {
    public ClientStream newStream(MethodDescriptor<?, ?> var1, Metadata var2, CallOptions var3, ClientStreamTracer[] var4);

    public void ping(PingCallback var1, Executor var2);

    public static interface PingCallback {
        public void onSuccess(long var1);

        public void onFailure(Throwable var1);
    }
}

