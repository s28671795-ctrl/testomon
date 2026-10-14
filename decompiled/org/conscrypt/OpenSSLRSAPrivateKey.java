/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLKeyHolder;
import org.conscrypt.OpenSSLRSAPrivateCrtKey;
import org.conscrypt.Platform;

class OpenSSLRSAPrivateKey
implements RSAPrivateKey,
OpenSSLKeyHolder {
    private static final long serialVersionUID = 4872170254439578735L;
    transient OpenSSLKey key;
    transient boolean fetchedParams;
    BigInteger modulus;
    BigInteger privateExponent;

    OpenSSLRSAPrivateKey(OpenSSLKey key) {
        this.key = key;
    }

    OpenSSLRSAPrivateKey(OpenSSLKey key, byte[][] params) {
        this(key);
        this.readParams(params);
        this.fetchedParams = true;
    }

    @Override
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    public OpenSSLRSAPrivateKey(RSAPrivateKeySpec rsaKeySpec) throws InvalidKeySpecException {
        this(OpenSSLRSAPrivateKey.init(rsaKeySpec));
    }

    private static OpenSSLKey init(RSAPrivateKeySpec rsaKeySpec) throws InvalidKeySpecException {
        BigInteger modulus = rsaKeySpec.getModulus();
        BigInteger privateExponent = rsaKeySpec.getPrivateExponent();
        if (modulus == null) {
            throw new InvalidKeySpecException("modulus == null");
        }
        if (privateExponent == null) {
            throw new InvalidKeySpecException("privateExponent == null");
        }
        try {
            return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(modulus.toByteArray(), null, privateExponent.toByteArray(), null, null, null, null, null));
        }
        catch (Exception e) {
            throw new InvalidKeySpecException(e);
        }
    }

    static OpenSSLRSAPrivateKey getInstance(OpenSSLKey key) {
        byte[][] params = NativeCrypto.get_RSA_private_params(key.getNativeRef());
        if (params[1] != null) {
            return new OpenSSLRSAPrivateCrtKey(key, params);
        }
        return new OpenSSLRSAPrivateKey(key, params);
    }

    static OpenSSLKey wrapPlatformKey(RSAPrivateKey rsaPrivateKey) throws InvalidKeyException {
        OpenSSLKey wrapper = Platform.wrapRsaKey(rsaPrivateKey);
        if (wrapper != null) {
            return wrapper;
        }
        return new OpenSSLKey(NativeCrypto.getRSAPrivateKeyWrapper(rsaPrivateKey, rsaPrivateKey.getModulus().toByteArray()), true);
    }

    static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey privateKey, PublicKey publicKey) throws InvalidKeyException {
        BigInteger modulus = null;
        if (privateKey instanceof RSAKey) {
            modulus = ((RSAKey)((Object)privateKey)).getModulus();
        } else if (publicKey instanceof RSAKey) {
            modulus = ((RSAKey)((Object)publicKey)).getModulus();
        }
        if (modulus == null) {
            throw new InvalidKeyException("RSA modulus not available. Private: " + privateKey + ", public: " + publicKey);
        }
        return new OpenSSLKey(NativeCrypto.getRSAPrivateKeyWrapper(privateKey, modulus.toByteArray()), true);
    }

    static OpenSSLKey getInstance(RSAPrivateKey rsaPrivateKey) throws InvalidKeyException {
        if (rsaPrivateKey.getFormat() == null) {
            return OpenSSLRSAPrivateKey.wrapPlatformKey(rsaPrivateKey);
        }
        BigInteger modulus = rsaPrivateKey.getModulus();
        BigInteger privateExponent = rsaPrivateKey.getPrivateExponent();
        if (modulus == null) {
            throw new InvalidKeyException("modulus == null");
        }
        if (privateExponent == null) {
            throw new InvalidKeyException("privateExponent == null");
        }
        try {
            return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(modulus.toByteArray(), null, privateExponent.toByteArray(), null, null, null, null, null));
        }
        catch (Exception e) {
            throw new InvalidKeyException(e);
        }
    }

    final synchronized void ensureReadParams() {
        if (this.fetchedParams) {
            return;
        }
        this.readParams(NativeCrypto.get_RSA_private_params(this.key.getNativeRef()));
        this.fetchedParams = true;
    }

    void readParams(byte[][] params) {
        if (params[0] == null) {
            throw new NullPointerException("modulus == null");
        }
        if (params[2] == null && !this.key.isHardwareBacked()) {
            throw new NullPointerException("privateExponent == null");
        }
        this.modulus = new BigInteger(params[0]);
        if (params[2] != null) {
            this.privateExponent = new BigInteger(params[2]);
        }
    }

    @Override
    public final BigInteger getPrivateExponent() {
        if (this.key.isHardwareBacked()) {
            throw new UnsupportedOperationException("Private exponent cannot be extracted");
        }
        this.ensureReadParams();
        return this.privateExponent;
    }

    @Override
    public final BigInteger getModulus() {
        this.ensureReadParams();
        return this.modulus;
    }

    @Override
    public final byte[] getEncoded() {
        if (this.key.isHardwareBacked()) {
            return null;
        }
        return NativeCrypto.EVP_marshal_private_key(this.key.getNativeRef());
    }

    @Override
    public final String getFormat() {
        if (this.key.isHardwareBacked()) {
            return null;
        }
        return "PKCS#8";
    }

    @Override
    public final String getAlgorithm() {
        return "RSA";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof OpenSSLRSAPrivateKey) {
            OpenSSLRSAPrivateKey other = (OpenSSLRSAPrivateKey)o;
            return this.key.equals(other.getOpenSSLKey());
        }
        if (o instanceof RSAPrivateKey) {
            this.ensureReadParams();
            RSAPrivateKey other = (RSAPrivateKey)o;
            return this.modulus.equals(other.getModulus()) && this.privateExponent.equals(other.getPrivateExponent());
        }
        return false;
    }

    public int hashCode() {
        this.ensureReadParams();
        int hash = 1;
        hash = hash * 3 + this.modulus.hashCode();
        if (this.privateExponent != null) {
            hash = hash * 7 + this.privateExponent.hashCode();
        }
        return hash;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("OpenSSLRSAPrivateKey{");
        this.ensureReadParams();
        sb.append("modulus=");
        sb.append(this.modulus.toString(16));
        return sb.toString();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(this.modulus.toByteArray(), null, this.privateExponent.toByteArray(), null, null, null, null, null));
        this.fetchedParams = true;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        if (this.key.isHardwareBacked()) {
            throw new NotSerializableException("Hardware backed keys can not be serialized");
        }
        this.ensureReadParams();
        stream.defaultWriteObject();
    }
}

