/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt.ct;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.conscrypt.ct.Serialization;
import org.conscrypt.ct.SerializationException;

public class DigitallySigned {
    private final HashAlgorithm hashAlgorithm;
    private final SignatureAlgorithm signatureAlgorithm;
    private final byte[] signature;

    public DigitallySigned(HashAlgorithm hashAlgorithm, SignatureAlgorithm signatureAlgorithm, byte[] signature) {
        this.hashAlgorithm = hashAlgorithm;
        this.signatureAlgorithm = signatureAlgorithm;
        this.signature = signature;
    }

    public DigitallySigned(int hashAlgorithm, int signatureAlgorithm, byte[] signature) {
        this(HashAlgorithm.valueOf(hashAlgorithm), SignatureAlgorithm.valueOf(signatureAlgorithm), signature);
    }

    public HashAlgorithm getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public String getAlgorithm() {
        return String.format("%swith%s", new Object[]{this.hashAlgorithm, this.signatureAlgorithm});
    }

    public static DigitallySigned decode(InputStream input) throws SerializationException {
        try {
            return new DigitallySigned(Serialization.readNumber(input, 1), Serialization.readNumber(input, 1), Serialization.readVariableBytes(input, 2));
        }
        catch (IllegalArgumentException e) {
            throw new SerializationException(e);
        }
    }

    public static DigitallySigned decode(byte[] input) throws SerializationException {
        return DigitallySigned.decode(new ByteArrayInputStream(input));
    }

    public static enum SignatureAlgorithm {
        ANONYMOUS,
        RSA,
        DSA,
        ECDSA;

        private static SignatureAlgorithm[] values;

        public static SignatureAlgorithm valueOf(int ord) {
            try {
                return values[ord];
            }
            catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid signature algorithm " + ord, e);
            }
        }

        static {
            values = SignatureAlgorithm.values();
        }
    }

    public static enum HashAlgorithm {
        NONE,
        MD5,
        SHA1,
        SHA224,
        SHA256,
        SHA384,
        SHA512;

        private static HashAlgorithm[] values;

        public static HashAlgorithm valueOf(int ord) {
            try {
                return values[ord];
            }
            catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid hash algorithm " + ord, e);
            }
        }

        static {
            values = HashAlgorithm.values();
        }
    }
}

