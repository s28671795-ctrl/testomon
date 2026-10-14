/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OAEPParameters;

public class PSSParameters
extends AlgorithmParametersSpi {
    private PSSParameterSpec spec = PSSParameterSpec.DEFAULT;

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (!(algorithmParameterSpec instanceof PSSParameterSpec)) {
            throw new InvalidParameterSpecException("Only PSSParameterSpec is supported");
        }
        this.spec = (PSSParameterSpec)algorithmParameterSpec;
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
            int saltLength = 20;
            String hash = OAEPParameters.readHash(seqRef);
            String mgfHash = OAEPParameters.readMgfHash(seqRef);
            if (NativeCrypto.asn1_read_next_tag_is(seqRef, 2)) {
                long saltRef = 0L;
                try {
                    saltRef = NativeCrypto.asn1_read_tagged(seqRef);
                    saltLength = (int)NativeCrypto.asn1_read_uint64(saltRef);
                }
                finally {
                    NativeCrypto.asn1_read_free(saltRef);
                }
            }
            if (NativeCrypto.asn1_read_next_tag_is(seqRef, 3)) {
                long trailerField;
                long trailerRef = 0L;
                try {
                    trailerRef = NativeCrypto.asn1_read_tagged(seqRef);
                    trailerField = (int)NativeCrypto.asn1_read_uint64(trailerRef);
                }
                finally {
                    NativeCrypto.asn1_read_free(trailerRef);
                }
                if (trailerField != 1L) {
                    throw new IOException("Error reading ASN.1 encoding");
                }
            }
            if (!NativeCrypto.asn1_read_is_empty(seqRef) || !NativeCrypto.asn1_read_is_empty(readRef)) {
                throw new IOException("Error reading ASN.1 encoding");
            }
            this.spec = new PSSParameterSpec(hash, "MGF1", new MGF1ParameterSpec(mgfHash), saltLength, 1);
        }
        finally {
            NativeCrypto.asn1_read_free(seqRef);
            NativeCrypto.asn1_read_free(readRef);
        }
    }

    @Override
    protected void engineInit(byte[] bytes, String format) throws IOException {
        if (format != null && !format.equals("ASN.1") && !format.equals("X.509")) {
            throw new IOException("Unsupported format: " + format);
        }
        this.engineInit(bytes);
    }

    @Override
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> aClass) throws InvalidParameterSpecException {
        if (aClass != null && aClass == PSSParameterSpec.class) {
            return (T)this.spec;
        }
        throw new InvalidParameterSpecException("Unsupported class: " + aClass);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected byte[] engineGetEncoded() throws IOException {
        long cbbRef = 0L;
        long seqRef = 0L;
        try {
            cbbRef = NativeCrypto.asn1_write_init();
            seqRef = NativeCrypto.asn1_write_sequence(cbbRef);
            OAEPParameters.writeHashAndMgfHash(seqRef, this.spec.getDigestAlgorithm(), (MGF1ParameterSpec)this.spec.getMGFParameters());
            if (this.spec.getSaltLength() != 20) {
                long tagRef = 0L;
                try {
                    tagRef = NativeCrypto.asn1_write_tag(seqRef, 2);
                    NativeCrypto.asn1_write_uint64(tagRef, this.spec.getSaltLength());
                }
                finally {
                    NativeCrypto.asn1_write_flush(seqRef);
                    NativeCrypto.asn1_write_free(tagRef);
                }
            }
            byte[] tagRef = NativeCrypto.asn1_write_finish(cbbRef);
            return tagRef;
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
        if (format == null || format.equals("ASN.1") || format.equals("X.509")) {
            return this.engineGetEncoded();
        }
        throw new IOException("Unsupported format: " + format);
    }

    @Override
    protected String engineToString() {
        return "Conscrypt PSS AlgorithmParameters";
    }
}

