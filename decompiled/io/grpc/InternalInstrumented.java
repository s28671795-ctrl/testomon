/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.Internal;
import io.grpc.InternalWithLogId;

@Internal
public interface InternalInstrumented<T>
extends InternalWithLogId {
    public ListenableFuture<T> getStats();
}

