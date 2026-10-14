/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.encryption;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.jwt.config.encryption.AbstractEncryptionConfiguration;
import org.pac4j.jwt.util.JWKHelper;

public class RSAEncryptionConfiguration
extends AbstractEncryptionConfiguration {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAEncryptionConfiguration() {
    }

    public RSAEncryptionConfiguration(KeyPair keyPair) {
        this.setKeyPair(keyPair);
    }

    public RSAEncryptionConfiguration(KeyPair keyPair, JWEAlgorithm algorithm, EncryptionMethod method2) {
        this.setKeyPair(keyPair);
        this.algorithm = algorithm;
        this.method = method2;
    }

    @Override
    public boolean supports(JWEAlgorithm algorithm, EncryptionMethod method2) {
        return algorithm != null && method2 != null && RSADecrypter.SUPPORTED_ALGORITHMS.contains(algorithm) && RSADecrypter.SUPPORTED_ENCRYPTION_METHODS.contains(method2);
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("algorithm", this.algorithm);
        CommonHelper.assertNotNull("method", this.method);
        if (!this.supports(this.algorithm, this.method)) {
            throw new TechnicalException("Only RSA algorithms are supported with the appropriate encryption method");
        }
    }

    @Override
    protected JWEEncrypter buildEncrypter() {
        CommonHelper.assertNotNull("publicKey", this.publicKey);
        return new RSAEncrypter(this.publicKey);
    }

    @Override
    protected JWEDecrypter buildDecrypter() {
        CommonHelper.assertNotNull("privateKey", this.privateKey);
        return new RSADecrypter(this.privateKey);
    }

    public void setKeyPair(KeyPair keyPair) {
        CommonHelper.assertNotNull("keyPair", keyPair);
        this.privateKey = (RSAPrivateKey)keyPair.getPrivate();
        this.publicKey = (RSAPublicKey)keyPair.getPublic();
    }

    public RSAPublicKey getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setKeysFromJwk(String json) {
        KeyPair pair = JWKHelper.buildRSAKeyPairFromJwk(json);
        this.publicKey = (RSAPublicKey)pair.getPublic();
        this.privateKey = (RSAPrivateKey)pair.getPrivate();
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "keys", "[protected]", "algorithm", this.algorithm, "method", this.method);
    }
}

