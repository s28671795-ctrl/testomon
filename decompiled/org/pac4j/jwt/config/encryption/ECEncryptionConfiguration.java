/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.encryption;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.crypto.ECDHDecrypter;
import com.nimbusds.jose.crypto.ECDHEncrypter;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.jwt.config.encryption.AbstractEncryptionConfiguration;
import org.pac4j.jwt.util.JWKHelper;

public class ECEncryptionConfiguration
extends AbstractEncryptionConfiguration {
    private ECPublicKey publicKey;
    private ECPrivateKey privateKey;

    public ECEncryptionConfiguration() {
    }

    public ECEncryptionConfiguration(KeyPair keyPair) {
        this.setKeyPair(keyPair);
    }

    public ECEncryptionConfiguration(KeyPair keyPair, JWEAlgorithm algorithm, EncryptionMethod method2) {
        this.setKeyPair(keyPair);
        this.algorithm = algorithm;
        this.method = method2;
    }

    @Override
    public boolean supports(JWEAlgorithm algorithm, EncryptionMethod method2) {
        return algorithm != null && method2 != null && ECDHDecrypter.SUPPORTED_ALGORITHMS.contains(algorithm) && ECDHDecrypter.SUPPORTED_ENCRYPTION_METHODS.contains(method2);
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("algorithm", this.algorithm);
        CommonHelper.assertNotNull("method", this.method);
        if (!this.supports(this.algorithm, this.method)) {
            throw new TechnicalException("Only Elliptic-curve algorithms are supported with the appropriate encryption method");
        }
    }

    @Override
    protected JWEEncrypter buildEncrypter() {
        CommonHelper.assertNotNull("publicKey", this.publicKey);
        try {
            return new ECDHEncrypter(this.publicKey);
        }
        catch (JOSEException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    protected JWEDecrypter buildDecrypter() {
        CommonHelper.assertNotNull("privateKey", this.privateKey);
        try {
            return new ECDHDecrypter(this.privateKey);
        }
        catch (JOSEException e) {
            throw new TechnicalException(e);
        }
    }

    public void setKeyPair(KeyPair keyPair) {
        CommonHelper.assertNotNull("keyPair", keyPair);
        this.privateKey = (ECPrivateKey)keyPair.getPrivate();
        this.publicKey = (ECPublicKey)keyPair.getPublic();
    }

    public ECPublicKey getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(ECPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public ECPrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(ECPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setKeysFromJwk(String json) {
        KeyPair pair = JWKHelper.buildECKeyPairFromJwk(json);
        this.publicKey = (ECPublicKey)pair.getPublic();
        this.privateKey = (ECPrivateKey)pair.getPrivate();
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "keys", "[protected]", "algorithm", this.algorithm, "method", this.method);
    }
}

