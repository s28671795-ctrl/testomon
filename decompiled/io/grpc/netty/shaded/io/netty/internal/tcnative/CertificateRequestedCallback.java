/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

@Deprecated
public interface CertificateRequestedCallback {
    public static final byte TLS_CT_RSA_SIGN = 1;
    public static final byte TLS_CT_DSS_SIGN = 2;
    public static final byte TLS_CT_RSA_FIXED_DH = 3;
    public static final byte TLS_CT_DSS_FIXED_DH = 4;
    public static final byte TLS_CT_ECDSA_SIGN = 64;
    public static final byte TLS_CT_RSA_FIXED_ECDH = 65;
    public static final byte TLS_CT_ECDSA_FIXED_ECDH = 66;

    public void requested(long var1, long var3, long var5, byte[] var7, byte[][] var8) throws Exception;
}

