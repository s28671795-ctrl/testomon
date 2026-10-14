/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.ShortBufferWithoutStackTraceException;

public final class OpenSSLECDHKeyAgreement
extends KeyAgreementSpi {
    private OpenSSLKey mOpenSslPrivateKey;
    private int mExpectedResultLength;
    private byte[] mResult;

    @Override
    public Key engineDoPhase(Key key, boolean lastPhase) throws InvalidKeyException {
        byte[] result;
        if (this.mOpenSslPrivateKey == null) {
            throw new IllegalStateException("Not initialized");
        }
        if (!lastPhase) {
            throw new IllegalStateException("ECDH only has one phase");
        }
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if (!(key instanceof PublicKey)) {
            throw new InvalidKeyException("Not a public key: " + key.getClass());
        }
        byte[] buffer = new byte[this.mExpectedResultLength];
        OpenSSLKey openSslPublicKey = OpenSSLKey.fromPublicKey((PublicKey)key);
        int actualResultLength = NativeCrypto.ECDH_compute_key(buffer, 0, openSslPublicKey.getNativeRef(), this.mOpenSslPrivateKey.getNativeRef());
        if (actualResultLength == -1) {
            throw new RuntimeException("Engine returned " + actualResultLength);
        }
        if (actualResultLength == this.mExpectedResultLength) {
            result = buffer;
        } else if (actualResultLength < this.mExpectedResultLength) {
            result = new byte[actualResultLength];
            System.arraycopy(buffer, 0, this.mResult, 0, this.mResult.length);
        } else {
            throw new RuntimeException("Engine produced a longer than expected result. Expected: " + this.mExpectedResultLength + ", actual: " + actualResultLength);
        }
        this.mResult = result;
        return null;
    }

    @Override
    protected int engineGenerateSecret(byte[] sharedSecret, int offset) throws ShortBufferException {
        this.checkCompleted();
        int available = sharedSecret.length - offset;
        if (this.mResult.length > available) {
            throw new ShortBufferWithoutStackTraceException("Needed: " + this.mResult.length + ", available: " + available);
        }
        System.arraycopy(this.mResult, 0, sharedSecret, offset, this.mResult.length);
        return this.mResult.length;
    }

    @Override
    protected byte[] engineGenerateSecret() {
        this.checkCompleted();
        return this.mResult;
    }

    @Override
    protected SecretKey engineGenerateSecret(String algorithm) {
        this.checkCompleted();
        return new SecretKeySpec(this.engineGenerateSecret(), algorithm);
    }

    @Override
    protected void engineInit(Key key, SecureRandom random) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if (!(key instanceof PrivateKey)) {
            throw new InvalidKeyException("Not a private key: " + key.getClass());
        }
        OpenSSLKey openSslKey = OpenSSLKey.fromPrivateKey((PrivateKey)key);
        int fieldSizeBits = NativeCrypto.EC_GROUP_get_degree(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(openSslKey.getNativeRef())));
        this.mExpectedResultLength = (fieldSizeBits + 7) / 8;
        this.mOpenSslPrivateKey = openSslKey;
    }

    @Override
    protected void engineInit(Key key, AlgorithmParameterSpec params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (params != null) {
            throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
        }
        this.engineInit(key, random);
    }

    private void checkCompleted() {
        if (this.mResult == null) {
            throw new IllegalStateException("Key agreement not completed");
        }
    }
}

