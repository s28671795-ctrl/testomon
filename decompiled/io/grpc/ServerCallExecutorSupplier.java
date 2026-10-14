/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ExperimentalApi;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/8274")
public interface ServerCallExecutorSupplier {
    @Nullable
    public <ReqT, RespT> Executor getExecutor(ServerCall<ReqT, RespT> var1, Metadata var2);
}

