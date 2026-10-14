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
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLRSAPrivateCrtKey;
import org.conscrypt.OpenSSLRSAPrivateKey;
import org.conscrypt.OpenSSLRSAPublicKey;

public final class OpenSSLRSAKeyFactory
extends KeyFactorySpi {
    @Override
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec == null) {
            throw new InvalidKeySpecException("keySpec == null");
        }
        if (keySpec instanceof RSAPublicKeySpec) {
            return new OpenSSLRSAPublicKey((RSAPublicKeySpec)keySpec);
        }
        if (keySpec instanceof X509EncodedKeySpec) {
            return OpenSSLKey.getPublicKey((X509EncodedKeySpec)keySpec, 6);
        }
        throw new InvalidKeySpecException("Must use RSAPublicKeySpec or X509EncodedKeySpec; was " + keySpec.getClass().getName());
    }

    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec == null) {
            throw new InvalidKeySpecException("keySpec == null");
        }
        if (keySpec instanceof RSAPrivateCrtKeySpec) {
            return new OpenSSLRSAPrivateCrtKey((RSAPrivateCrtKeySpec)keySpec);
        }
        if (keySpec instanceof RSAPrivateKeySpec) {
            return new OpenSSLRSAPrivateKey((RSAPrivateKeySpec)keySpec);
        }
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            return OpenSSLKey.getPrivateKey((PKCS8EncodedKeySpec)keySpec, 6);
        }
        throw new InvalidKeySpecException("Must use RSAPublicKeySpec or PKCS8EncodedKeySpec; was " + keySpec.getClass().getName());
    }

    @Override
    protected <T extends KeySpec> T engineGetKeySpec(Key key, Class<T> keySpec) throws InvalidKeySpecException {
        if (key == null) {
            throw new InvalidKeySpecException("key == null");
        }
        if (keySpec == null) {
            throw new InvalidKeySpecException("keySpec == null");
        }
        if (!"RSA".equals(key.getAlgorithm())) {
            throw new InvalidKeySpecException("Key must be a RSA key");
        }
        if (key instanceof RSAPublicKey && RSAPublicKeySpec.class.isAssignableFrom(keySpec)) {
            RSAPublicKey rsaKey = (RSAPublicKey)key;
            RSAPublicKeySpec result = new RSAPublicKeySpec(rsaKey.getModulus(), rsaKey.getPublicExponent());
            return (T)result;
        }
        if (key instanceof PublicKey && RSAPublicKeySpec.class.isAssignableFrom(keySpec)) {
            byte[] encoded = key.getEncoded();
            if (!"X.509".equals(key.getFormat()) || encoded == null) {
                throw new InvalidKeySpecException("Not a valid X.509 encoding");
            }
            RSAPublicKey rsaKey = (RSAPublicKey)this.engineGeneratePublic(new X509EncodedKeySpec(encoded));
            RSAPublicKeySpec result = new RSAPublicKeySpec(rsaKey.getModulus(), rsaKey.getPublicExponent());
            return (T)result;
        }
        if (key instanceof RSAPrivateCrtKey && RSAPrivateCrtKeySpec.class.isAssignableFrom(keySpec)) {
            RSAPrivateCrtKey rsaKey = (RSAPrivateCrtKey)key;
            RSAPrivateCrtKeySpec result = new RSAPrivateCrtKeySpec(rsaKey.getModulus(), rsaKey.getPublicExponent(), rsaKey.getPrivateExponent(), rsaKey.getPrimeP(), rsaKey.getPrimeQ(), rsaKey.getPrimeExponentP(), rsaKey.getPrimeExponentQ(), rsaKey.getCrtCoefficient());
            return (T)result;
        }
        if (key instanceof RSAPrivateCrtKey && RSAPrivateKeySpec.class.isAssignableFrom(keySpec)) {
            RSAPrivateCrtKey rsaKey = (RSAPrivateCrtKey)key;
            RSAPrivateKeySpec result = new RSAPrivateKeySpec(rsaKey.getModulus(), rsaKey.getPrivateExponent());
            return (T)result;
        }
        if (key instanceof RSAPrivateKey && RSAPrivateKeySpec.class.isAssignableFrom(keySpec)) {
            RSAPrivateKey rsaKey = (RSAPrivateKey)key;
            RSAPrivateKeySpec result = new RSAPrivateKeySpec(rsaKey.getModulus(), rsaKey.getPrivateExponent());
            return (T)result;
        }
        if (key instanceof PrivateKey && RSAPrivateCrtKeySpec.class.isAssignableFrom(keySpec)) {
            byte[] encoded = key.getEncoded();
            if (!"PKCS#8".equals(key.getFormat()) || encoded == null) {
                throw new InvalidKeySpecException("Not a valid PKCS#8 encoding");
            }
            RSAPrivateKey privKey = (RSAPrivateKey)this.engineGeneratePrivate(new PKCS8EncodedKeySpec(encoded));
            if (privKey instanceof RSAPrivateCrtKey) {
                RSAPrivateCrtKey rsaKey = (RSAPrivateCrtKey)privKey;
                RSAPrivateCrtKeySpec result = new RSAPrivateCrtKeySpec(rsaKey.getModulus(), rsaKey.getPublicExponent(), rsaKey.getPrivateExponent(), rsaKey.getPrimeP(), rsaKey.getPrimeQ(), rsaKey.getPrimeExponentP(), rsaKey.getPrimeExponentQ(), rsaKey.getCrtCoefficient());
                return (T)result;
            }
            throw new InvalidKeySpecException("Encoded key is not an RSAPrivateCrtKey");
        }
        if (key instanceof PrivateKey && RSAPrivateKeySpec.class.isAssignableFrom(keySpec)) {
            byte[] encoded = key.getEncoded();
            if (!"PKCS#8".equals(key.getFormat()) || encoded == null) {
                throw new InvalidKeySpecException("Not a valid PKCS#8 encoding");
            }
            RSAPrivateKey rsaKey = (RSAPrivateKey)this.engineGeneratePrivate(new PKCS8EncodedKeySpec(encoded));
            RSAPrivateKeySpec result = new RSAPrivateKeySpec(rsaKey.getModulus(), rsaKey.getPrivateExponent());
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
        if (key instanceof OpenSSLRSAPublicKey || key instanceof OpenSSLRSAPrivateKey) {
            return key;
        }
        if (key instanceof RSAPublicKey) {
            RSAPublicKey rsaKey = (RSAPublicKey)key;
            try {
                return this.engineGeneratePublic(new RSAPublicKeySpec(rsaKey.getModulus(), rsaKey.getPublicExponent()));
            }
            catch (InvalidKeySpecException e) {
                throw new InvalidKeyException(e);
            }
        }
        if (key instanceof RSAPrivateCrtKey) {
            RSAPrivateCrtKey rsaKey = (RSAPrivateCrtKey)key;
            BigInteger modulus = rsaKey.getModulus();
            BigInteger publicExponent = rsaKey.getPublicExponent();
            BigInteger privateExponent = rsaKey.getPrivateExponent();
            BigInteger primeP = rsaKey.getPrimeP();
            BigInteger primeQ = rsaKey.getPrimeQ();
            BigInteger primeExponentP = rsaKey.getPrimeExponentP();
            BigInteger primeExponentQ = rsaKey.getPrimeExponentQ();
            BigInteger crtCoefficient = rsaKey.getCrtCoefficient();
            try {
                return this.engineGeneratePrivate(new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent, primeP, primeQ, primeExponentP, primeExponentQ, crtCoefficient));
            }
            catch (InvalidKeySpecException e) {
                throw new InvalidKeyException(e);
            }
        }
        if (key instanceof RSAPrivateKey) {
            RSAPrivateKey rsaKey = (RSAPrivateKey)key;
            BigInteger modulus = rsaKey.getModulus();
            BigInteger privateExponent = rsaKey.getPrivateExponent();
            try {
                return this.engineGeneratePrivate(new RSAPrivateKeySpec(modulus, privateExponent));
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
        throw new InvalidKeyException("Key must be an RSA public or private key; was " + key.getClass().getName());
    }
}

