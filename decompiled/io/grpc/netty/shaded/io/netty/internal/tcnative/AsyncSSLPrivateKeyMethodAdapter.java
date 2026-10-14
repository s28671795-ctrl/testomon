/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

import io.grpc.netty.shaded.io.netty.internal.tcnative.AsyncSSLPrivateKeyMethod;
import io.grpc.netty.shaded.io.netty.internal.tcnative.ResultCallback;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLPrivateKeyMethod;

final class AsyncSSLPrivateKeyMethodAdapter
implements AsyncSSLPrivateKeyMethod {
    private final SSLPrivateKeyMethod method;

    AsyncSSLPrivateKeyMethodAdapter(SSLPrivateKeyMethod method2) {
        if (method2 == null) {
            throw new NullPointerException("method");
        }
        this.method = method2;
    }

    @Override
    public void sign(long ssl, int signatureAlgorithm, byte[] input, ResultCallback<byte[]> resultCallback) {
        byte[] result;
        try {
            result = this.method.sign(ssl, signatureAlgorithm, input);
        }
        catch (Throwable cause) {
            resultCallback.onError(ssl, cause);
            return;
        }
        resultCallback.onSuccess(ssl, result);
    }

    @Override
    public void decrypt(long ssl, byte[] input, ResultCallback<byte[]> resultCallback) {
        byte[] result;
        try {
            result = this.method.decrypt(ssl, input);
        }
        catch (Throwable cause) {
            resultCallback.onError(ssl, cause);
            return;
        }
        resultCallback.onSuccess(ssl, result);
    }
}

