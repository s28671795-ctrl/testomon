/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

import io.grpc.netty.shaded.io.netty.internal.tcnative.CertificateCallback;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLTask;

final class CertificateCallbackTask
extends SSLTask {
    private final byte[] keyTypeBytes;
    private final byte[][] asn1DerEncodedPrincipals;
    private final CertificateCallback callback;

    CertificateCallbackTask(long ssl, byte[] keyTypeBytes, byte[][] asn1DerEncodedPrincipals, CertificateCallback callback) {
        super(ssl);
        this.keyTypeBytes = keyTypeBytes;
        this.asn1DerEncodedPrincipals = asn1DerEncodedPrincipals;
        this.callback = callback;
    }

    @Override
    protected void runTask(long ssl, SSLTask.TaskCallback taskCallback) {
        try {
            this.callback.handle(ssl, this.keyTypeBytes, this.asn1DerEncodedPrincipals);
            taskCallback.onResult(ssl, 1);
        }
        catch (Exception e) {
            taskCallback.onResult(ssl, 0);
        }
    }
}

