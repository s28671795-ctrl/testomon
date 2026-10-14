/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

import io.grpc.netty.shaded.io.netty.internal.tcnative.AsyncSSLPrivateKeyMethod;
import io.grpc.netty.shaded.io.netty.internal.tcnative.ResultCallback;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLPrivateKeyMethodTask;

final class SSLPrivateKeyMethodSignTask
extends SSLPrivateKeyMethodTask {
    private final int signatureAlgorithm;
    private final byte[] digest;

    SSLPrivateKeyMethodSignTask(long ssl, int signatureAlgorithm, byte[] digest, AsyncSSLPrivateKeyMethod method2) {
        super(ssl, method2);
        this.signatureAlgorithm = signatureAlgorithm;
        this.digest = digest;
    }

    @Override
    protected void runTask(long ssl, AsyncSSLPrivateKeyMethod method2, ResultCallback<byte[]> resultCallback) {
        method2.sign(ssl, this.signatureAlgorithm, this.digest, resultCallback);
    }
}

