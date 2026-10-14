/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.conscrypt.NativeCrypto;
import org.conscrypt.Platform;

public final class GCMParameters
extends AlgorithmParametersSpi {
    private static final int DEFAULT_TLEN = 96;
    private int tLen;
    private byte[] iv;

    public GCMParameters() {
    }

    GCMParameters(int tLen, byte[] iv) {
        this.tLen = tLen;
        this.iv = iv;
    }

    int getTLen() {
        return this.tLen;
    }

    byte[] getIV() {
        return this.iv;
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        GCMParameters params = Platform.fromGCMParameterSpec(algorithmParameterSpec);
        if (params == null) {
            throw new InvalidParameterSpecException("Only GCMParameterSpec is supported");
        }
        this.tLen = params.tLen;
        this.iv = params.iv;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void engineInit(byte[] bytes) throws IOException {
        long readRef = 0L;
        long seqRef = 0L;
        try {
            readRef = NativeCrypto.asn1_read_init(bytes);
            seqRef = NativeCrypto.asn1_read_sequence(readRef);
            byte[] newIv = NativeCrypto.asn1_read_octetstring(seqRef);
            int newTlen = 96;
            if (!NativeCrypto.asn1_read_is_empty(seqRef)) {
                newTlen = 8 * (int)NativeCrypto.asn1_read_uint64(seqRef);
            }
            if (!NativeCrypto.asn1_read_is_empty(seqRef) || !NativeCrypto.asn1_read_is_empty(readRef)) {
                throw new IOException("Error reading ASN.1 encoding");
            }
            this.iv = newIv;
            this.tLen = newTlen;
        }
        finally {
            NativeCrypto.asn1_read_free(seqRef);
            NativeCrypto.asn1_read_free(readRef);
        }
    }

    @Override
    protected void engineInit(byte[] bytes, String format) throws IOException {
        if (format != null && !format.equals("ASN.1")) {
            throw new IOException("Unsupported format: " + format);
        }
        this.engineInit(bytes);
    }

    @Override
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> aClass) throws InvalidParameterSpecException {
        if (aClass != null && aClass.getName().equals("javax.crypto.spec.GCMParameterSpec")) {
            return (T)((AlgorithmParameterSpec)aClass.cast(Platform.toGCMParameterSpec(this.tLen, this.iv)));
        }
        throw new InvalidParameterSpecException("Unsupported class: " + aClass);
    }

    @Override
    protected byte[] engineGetEncoded() throws IOException {
        long cbbRef = 0L;
        long seqRef = 0L;
        try {
            cbbRef = NativeCrypto.asn1_write_init();
            seqRef = NativeCrypto.asn1_write_sequence(cbbRef);
            NativeCrypto.asn1_write_octetstring(seqRef, this.iv);
            if (this.tLen != 96) {
                NativeCrypto.asn1_write_uint64(seqRef, this.tLen / 8);
            }
            byte[] byArray = NativeCrypto.asn1_write_finish(cbbRef);
            return byArray;
        }
        catch (IOException e) {
            NativeCrypto.asn1_write_cleanup(cbbRef);
            throw e;
        }
        finally {
            NativeCrypto.asn1_write_free(seqRef);
            NativeCrypto.asn1_write_free(cbbRef);
        }
    }

    @Override
    protected byte[] engineGetEncoded(String format) throws IOException {
        if (format == null || format.equals("ASN.1")) {
            return this.engineGetEncoded();
        }
        throw new IOException("Unsupported format: " + format);
    }

    @Override
    protected String engineToString() {
        return "Conscrypt GCM AlgorithmParameters";
    }
}

