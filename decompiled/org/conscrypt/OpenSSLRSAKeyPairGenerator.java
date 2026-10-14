/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLRSAPrivateKey;
import org.conscrypt.OpenSSLRSAPublicKey;

public final class OpenSSLRSAKeyPairGenerator
extends KeyPairGeneratorSpi {
    private byte[] publicExponent = new byte[]{1, 0, 1};
    private int modulusBits = 2048;

    @Override
    public KeyPair generateKeyPair() {
        OpenSSLKey key = new OpenSSLKey(NativeCrypto.RSA_generate_key_ex(this.modulusBits, this.publicExponent));
        OpenSSLRSAPrivateKey privKey = OpenSSLRSAPrivateKey.getInstance(key);
        OpenSSLRSAPublicKey pubKey = new OpenSSLRSAPublicKey(key);
        return new KeyPair(pubKey, privKey);
    }

    @Override
    public void initialize(int keysize, SecureRandom random) {
        this.modulusBits = keysize;
    }

    @Override
    public void initialize(AlgorithmParameterSpec params, SecureRandom random) throws InvalidAlgorithmParameterException {
        if (!(params instanceof RSAKeyGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Only RSAKeyGenParameterSpec supported");
        }
        RSAKeyGenParameterSpec spec = (RSAKeyGenParameterSpec)params;
        BigInteger publicExponent = spec.getPublicExponent();
        if (publicExponent != null) {
            this.publicExponent = publicExponent.toByteArray();
        }
        this.modulusBits = spec.getKeysize();
    }
}

