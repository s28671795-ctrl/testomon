/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

import io.grpc.netty.shaded.io.netty.internal.tcnative.AsyncSSLPrivateKeyMethod;
import io.grpc.netty.shaded.io.netty.internal.tcnative.AsyncTask;
import io.grpc.netty.shaded.io.netty.internal.tcnative.ResultCallback;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLTask;

abstract class SSLPrivateKeyMethodTask
extends SSLTask
implements AsyncTask {
    private static final byte[] EMPTY = new byte[0];
    private final AsyncSSLPrivateKeyMethod method;
    private byte[] resultBytes;

    SSLPrivateKeyMethodTask(long ssl, AsyncSSLPrivateKeyMethod method2) {
        super(ssl);
        this.method = method2;
    }

    @Override
    public final void runAsync(Runnable completeCallback) {
        this.run(completeCallback);
    }

    @Override
    protected final void runTask(long ssl, final SSLTask.TaskCallback callback) {
        this.runTask(ssl, this.method, new ResultCallback<byte[]>(){

            @Override
            public void onSuccess(long ssl, byte[] result) {
                SSLPrivateKeyMethodTask.access$002(SSLPrivateKeyMethodTask.this, result);
                callback.onResult(ssl, 1);
            }

            @Override
            public void onError(long ssl, Throwable cause) {
                SSLPrivateKeyMethodTask.access$002(SSLPrivateKeyMethodTask.this, EMPTY);
                callback.onResult(ssl, 0);
            }
        });
    }

    protected abstract void runTask(long var1, AsyncSSLPrivateKeyMethod var3, ResultCallback<byte[]> var4);

    static /* synthetic */ byte[] access$002(SSLPrivateKeyMethodTask x0, byte[] x1) {
        x0.resultBytes = x1;
        return x1;
    }
}

