/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

import io.grpc.stub.CallStreamObserver;
import javax.annotation.Nullable;

public abstract class ClientCallStreamObserver<ReqT>
extends CallStreamObserver<ReqT> {
    public abstract void cancel(@Nullable String var1, @Nullable Throwable var2);

    public void disableAutoRequestWithInitial(int request) {
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
}

