/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLAeadCipher;
import org.conscrypt.OpenSSLCipher;

public class OpenSSLAeadCipherChaCha20
extends OpenSSLAeadCipher {
    public OpenSSLAeadCipherChaCha20() {
        super(OpenSSLCipher.Mode.POLY1305);
    }

    @Override
    void checkSupportedKeySize(int keyLength) throws InvalidKeyException {
        if (keyLength != 32) {
            throw new InvalidKeyException("Unsupported key size: " + keyLength + " bytes (must be 32)");
        }
    }

    @Override
    String getBaseCipherName() {
        return "ChaCha20";
    }

    @Override
    int getCipherBlockSize() {
        return 0;
    }

    @Override
    void checkSupportedMode(OpenSSLCipher.Mode mode) throws NoSuchAlgorithmException {
        if (mode != OpenSSLCipher.Mode.POLY1305) {
            throw new NoSuchAlgorithmException("Mode must be Poly1305");
        }
    }

    @Override
    long getEVP_AEAD(int keyLength) throws InvalidKeyException {
        if (keyLength == 32) {
            return NativeCrypto.EVP_aead_chacha20_poly1305();
        }
        throw new RuntimeException("Unexpected key length: " + keyLength);
    }

    @Override
    int getOutputSizeForFinal(int inputLen) {
        if (this.isEncrypting()) {
            return this.bufCount + inputLen + 16;
        }
        return Math.max(0, this.bufCount + inputLen - 16);
    }
}

