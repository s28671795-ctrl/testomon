/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ExperimentalApi;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class HandlerRegistry {
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2222")
    public List<ServerServiceDefinition> getServices() {
        return Collections.emptyList();
    }

    @Nullable
    public abstract ServerMethodDefinition<?, ?> lookupMethod(String var1, @Nullable String var2);

    @Nullable
    public final ServerMethodDefinition<?, ?> lookupMethod(String methodName) {
        return this.lookupMethod(methodName, null);
    }
}

