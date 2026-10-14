/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

public interface CertificateCallback {
    public static final byte TLS_CT_RSA_SIGN = 1;
    public static final byte TLS_CT_DSS_SIGN = 2;
    public static final byte TLS_CT_RSA_FIXED_DH = 3;
    public static final byte TLS_CT_DSS_FIXED_DH = 4;
    public static final byte TLS_CT_ECDSA_SIGN = 64;
    public static final byte TLS_CT_RSA_FIXED_ECDH = 65;
    public static final byte TLS_CT_ECDSA_FIXED_ECDH = 66;

    public void handle(long var1, byte[] var3, byte[][] var4) throws Exception;
}

