/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

import io.grpc.netty.shaded.io.netty.internal.tcnative.NativeStaticallyReferencedJniMethods;

public interface CertificateCompressionAlgo {
    public static final int TLS_EXT_CERT_COMPRESSION_ZLIB = NativeStaticallyReferencedJniMethods.tlsExtCertCompressionZlib();
    public static final int TLS_EXT_CERT_COMPRESSION_BROTLI = NativeStaticallyReferencedJniMethods.tlsExtCertCompressionBrotli();
    public static final int TLS_EXT_CERT_COMPRESSION_ZSTD = NativeStaticallyReferencedJniMethods.tlsExtCertCompressionZstd();

    public byte[] compress(long var1, byte[] var3) throws Exception;

    public byte[] decompress(long var1, int var3, byte[] var4) throws Exception;

    public int algorithmId();
}

