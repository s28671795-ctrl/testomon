/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.conscrypt.OpenSSLECPrivateKey;
import org.conscrypt.OpenSSLECPublicKey;
import org.conscrypt.OpenSSLKey;

public final class OpenSSLECKeyFactory
extends KeyFactorySpi {
    @Override
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec == null) {
            throw new InvalidKeySpecException("keySpec == null");
        }
        if (keySpec instanceof ECPublicKeySpec) {
            return new OpenSSLECPublicKey((ECPublicKeySpec)keySpec);
        }
        if (keySpec instanceof X509EncodedKeySpec) {
            return OpenSSLKey.getPublicKey((X509EncodedKeySpec)keySpec, 408);
        }
        throw new InvalidKeySpecException("Must use ECPublicKeySpec or X509EncodedKeySpec; was " + keySpec.getClass().getName());
    }

    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec == null) {
            throw new InvalidKeySpecException("keySpec == null");
        }
        if (keySpec instanceof ECPrivateKeySpec) {
            return new OpenSSLECPrivateKey((ECPrivateKeySpec)keySpec);
        }
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            return OpenSSLKey.getPrivateKey((PKCS8EncodedKeySpec)keySpec, 408);
        }
        throw new InvalidKeySpecException("Must use ECPrivateKeySpec or PKCS8EncodedKeySpec; was " + keySpec.getClass().getName());
    }

    @Override
    protected <T extends KeySpec> T engineGetKeySpec(Key key, Class<T> keySpec) throws InvalidKeySpecException {
        if (key == null) {
            throw new InvalidKeySpecException("key == null");
        }
        if (keySpec == null) {
            throw new InvalidKeySpecException("keySpec == null");
        }
        if (!"EC".equals(key.getAlgorithm())) {
            throw new InvalidKeySpecException("Key must be an EC key");
        }
        if (key instanceof ECPublicKey && ECPublicKeySpec.class.isAssignableFrom(keySpec)) {
            ECPublicKey ecKey = (ECPublicKey)key;
            ECPublicKeySpec result = new ECPublicKeySpec(ecKey.getW(), ecKey.getParams());
            return (T)result;
        }
        if (key instanceof PublicKey && ECPublicKeySpec.class.isAssignableFrom(keySpec)) {
            byte[] encoded = key.getEncoded();
            if (!"X.509".equals(key.getFormat()) || encoded == null) {
                throw new InvalidKeySpecException("Not a valid X.509 encoding");
            }
            ECPublicKey ecKey = (ECPublicKey)this.engineGeneratePublic(new X509EncodedKeySpec(encoded));
            ECPublicKeySpec result = new ECPublicKeySpec(ecKey.getW(), ecKey.getParams());
            return (T)result;
        }
        if (key instanceof ECPrivateKey && ECPrivateKeySpec.class.isAssignableFrom(keySpec)) {
            ECPrivateKey ecKey = (ECPrivateKey)key;
            ECPrivateKeySpec result = new ECPrivateKeySpec(ecKey.getS(), ecKey.getParams());
            return (T)result;
        }
        if (key instanceof PrivateKey && ECPrivateKeySpec.class.isAssignableFrom(keySpec)) {
            byte[] encoded = key.getEncoded();
            if (!"PKCS#8".equals(key.getFormat()) || encoded == null) {
                throw new InvalidKeySpecException("Not a valid PKCS#8 encoding");
            }
            ECPrivateKey ecKey = (ECPrivateKey)this.engineGeneratePrivate(new PKCS8EncodedKeySpec(encoded));
            ECPrivateKeySpec result = new ECPrivateKeySpec(ecKey.getS(), ecKey.getParams());
            return (T)result;
        }
        if (key instanceof PrivateKey && PKCS8EncodedKeySpec.class.isAssignableFrom(keySpec)) {
            byte[] encoded = key.getEncoded();
            if (!"PKCS#8".equals(key.getFormat())) {
                throw new InvalidKeySpecException("Encoding type must be PKCS#8; was " + key.getFormat());
            }
            if (encoded == null) {
                throw new InvalidKeySpecException("Key is not encodable");
            }
            PKCS8EncodedKeySpec result = new PKCS8EncodedKeySpec(encoded);
            return (T)result;
        }
        if (key instanceof PublicKey && X509EncodedKeySpec.class.isAssignableFrom(keySpec)) {
            byte[] encoded = key.getEncoded();
            if (!"X.509".equals(key.getFormat())) {
                throw new InvalidKeySpecException("Encoding type must be X.509; was " + key.getFormat());
            }
            if (encoded == null) {
                throw new InvalidKeySpecException("Key is not encodable");
            }
            X509EncodedKeySpec result = new X509EncodedKeySpec(encoded);
            return (T)result;
        }
        throw new InvalidKeySpecException("Unsupported key type and key spec combination; key=" + key.getClass().getName() + ", keySpec=" + keySpec.getName());
    }

    @Override
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if (key instanceof OpenSSLECPublicKey || key instanceof OpenSSLECPrivateKey) {
            return key;
        }
        if (key instanceof ECPublicKey) {
            ECPublicKey ecKey = (ECPublicKey)key;
            ECPoint w = ecKey.getW();
            ECParameterSpec params = ecKey.getParams();
            try {
                return this.engineGeneratePublic(new ECPublicKeySpec(w, params));
            }
            catch (InvalidKeySpecException e) {
                throw new InvalidKeyException(e);
            }
        }
        if (key instanceof ECPrivateKey) {
            ECPrivateKey ecKey = (ECPrivateKey)key;
            BigInteger s2 = ecKey.getS();
            ECParameterSpec params = ecKey.getParams();
            try {
                return this.engineGeneratePrivate(new ECPrivateKeySpec(s2, params));
            }
            catch (InvalidKeySpecException e) {
                throw new InvalidKeyException(e);
            }
        }
        if (key instanceof PrivateKey && "PKCS#8".equals(key.getFormat())) {
            byte[] encoded = key.getEncoded();
            if (encoded == null) {
                throw new InvalidKeyException("Key does not support encoding");
            }
            try {
                return this.engineGeneratePrivate(new PKCS8EncodedKeySpec(encoded));
            }
            catch (InvalidKeySpecException e) {
                throw new InvalidKeyException(e);
            }
        }
        if (key instanceof PublicKey && "X.509".equals(key.getFormat())) {
            byte[] encoded = key.getEncoded();
            if (encoded == null) {
                throw new InvalidKeyException("Key does not support encoding");
            }
            try {
                return this.engineGeneratePublic(new X509EncodedKeySpec(encoded));
            }
            catch (InvalidKeySpecException e) {
                throw new InvalidKeyException(e);
            }
        }
        throw new InvalidKeyException("Key must be EC public or private key; was " + key.getClass().getName());
    }
}

