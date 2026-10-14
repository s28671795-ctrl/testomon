/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

import com.google.common.base.Preconditions;
import io.grpc.ExperimentalApi;
import io.grpc.stub.CallStreamObserver;

public abstract class ServerCallStreamObserver<RespT>
extends CallStreamObserver<RespT> {
    public abstract boolean isCancelled();

    public abstract void setOnCancelHandler(Runnable var1);

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/11021")
    public void setOnReadyThreshold(int numBytes) {
        Preconditions.checkArgument(numBytes > 0, "numBytes must be positive: %s", numBytes);
    }

    public abstract void setCompression(String var1);

    public void disableAutoRequest() {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract boolean isReady();

    @Override
    public abstract void setOnReadyHandler(Runnable var1);

    @Override
    public abstract void request(int var1);

    @Override
    public abstract void setMessageCompression(boolean var1);

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/8467")
    public void setOnCloseHandler(Runnable onCloseHandler) {
        throw new UnsupportedOperationException();
    }
}

