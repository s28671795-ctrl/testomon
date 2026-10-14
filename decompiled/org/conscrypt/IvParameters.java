/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import org.conscrypt.NativeCrypto;

public class IvParameters
extends AlgorithmParametersSpi {
    private byte[] iv;

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
            throw new InvalidParameterSpecException("Only IvParameterSpec is supported");
        }
        this.iv = (byte[])((IvParameterSpec)algorithmParameterSpec).getIV().clone();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void engineInit(byte[] bytes) throws IOException {
        long readRef = 0L;
        try {
            readRef = NativeCrypto.asn1_read_init(bytes);
            byte[] newIv = NativeCrypto.asn1_read_octetstring(readRef);
            if (!NativeCrypto.asn1_read_is_empty(readRef)) {
                throw new IOException("Error reading ASN.1 encoding");
            }
            this.iv = newIv;
        }
        finally {
            NativeCrypto.asn1_read_free(readRef);
        }
    }

    @Override
    protected void engineInit(byte[] bytes, String format) throws IOException {
        if (format == null || format.equals("ASN.1")) {
            this.engineInit(bytes);
        } else if (format.equals("RAW")) {
            this.iv = (byte[])bytes.clone();
        } else {
            throw new IOException("Unsupported format: " + format);
        }
    }

    @Override
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> aClass) throws InvalidParameterSpecException {
        if (aClass != IvParameterSpec.class) {
            throw new InvalidParameterSpecException("Incompatible AlgorithmParametersSpec class: " + aClass);
        }
        return (T)new IvParameterSpec(this.iv);
    }

    @Override
    protected byte[] engineGetEncoded() throws IOException {
        long cbbRef = 0L;
        try {
            cbbRef = NativeCrypto.asn1_write_init();
            NativeCrypto.asn1_write_octetstring(cbbRef, this.iv);
            byte[] byArray = NativeCrypto.asn1_write_finish(cbbRef);
            return byArray;
        }
        catch (IOException e) {
            NativeCrypto.asn1_write_cleanup(cbbRef);
            throw e;
        }
        finally {
            NativeCrypto.asn1_write_free(cbbRef);
        }
    }

    @Override
    protected byte[] engineGetEncoded(String format) throws IOException {
        if (format == null || format.equals("ASN.1")) {
            return this.engineGetEncoded();
        }
        if (format.equals("RAW")) {
            return (byte[])this.iv.clone();
        }
        throw new IOException("Unsupported format: " + format);
    }

    @Override
    protected String engineToString() {
        return "Conscrypt IV AlgorithmParameters";
    }

    public static class ChaCha20
    extends IvParameters {
    }

    public static class DESEDE
    extends IvParameters {
    }

    public static class AES
    extends IvParameters {
    }
}

