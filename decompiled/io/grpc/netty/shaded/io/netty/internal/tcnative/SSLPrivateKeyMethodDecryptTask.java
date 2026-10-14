/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

import io.grpc.netty.shaded.io.netty.internal.tcnative.AsyncSSLPrivateKeyMethod;
import io.grpc.netty.shaded.io.netty.internal.tcnative.ResultCallback;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLPrivateKeyMethodTask;

final class SSLPrivateKeyMethodDecryptTask
extends SSLPrivateKeyMethodTask {
    private final byte[] input;

    SSLPrivateKeyMethodDecryptTask(long ssl, byte[] input, AsyncSSLPrivateKeyMethod method2) {
        super(ssl, method2);
        this.input = input;
    }

    @Override
    protected void runTask(long ssl, AsyncSSLPrivateKeyMethod method2, ResultCallback<byte[]> resultCallback) {
        method2.decrypt(ssl, this.input, resultCallback);
    }
}

