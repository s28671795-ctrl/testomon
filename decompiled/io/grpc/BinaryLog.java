/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Channel;
import io.grpc.ExperimentalApi;
import io.grpc.ServerMethodDefinition;
import java.io.Closeable;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4017")
public abstract class BinaryLog
implements Closeable {
    public abstract <ReqT, RespT> ServerMethodDefinition<?, ?> wrapMethodDefinition(ServerMethodDefinition<ReqT, RespT> var1);

    public abstract Channel wrapChannel(Channel var1);
}

