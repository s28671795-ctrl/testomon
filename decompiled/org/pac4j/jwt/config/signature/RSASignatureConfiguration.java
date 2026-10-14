/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.signature;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.jwt.config.signature.AbstractSignatureConfiguration;
import org.pac4j.jwt.util.JWKHelper;

public class RSASignatureConfiguration
extends AbstractSignatureConfiguration {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSASignatureConfiguration() {
        this.algorithm = JWSAlgorithm.RS256;
    }

    public RSASignatureConfiguration(KeyPair keyPair) {
        this();
        this.setKeyPair(keyPair);
    }

    public RSASignatureConfiguration(KeyPair keyPair, JWSAlgorithm algorithm) {
        this.setKeyPair(keyPair);
        this.algorithm = algorithm;
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("algorithm", this.algorithm);
        if (!this.supports(this.algorithm)) {
            throw new TechnicalException("Only the RS256, RS384, RS512, PS256, PS384 and PS512 algorithms are supported for RSA signature");
        }
    }

    @Override
    public boolean supports(JWSAlgorithm algorithm) {
        return algorithm != null && RSASSAVerifier.SUPPORTED_ALGORITHMS.contains(algorithm);
    }

    @Override
    public SignedJWT sign(JWTClaimsSet claims) {
        this.init();
        CommonHelper.assertNotNull("privateKey", this.privateKey);
        try {
            RSASSASigner signer = new RSASSASigner(this.privateKey);
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(this.algorithm), claims);
            signedJWT.sign(signer);
            return signedJWT;
        }
        catch (JOSEException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public boolean verify(SignedJWT jwt) throws JOSEException {
        this.init();
        CommonHelper.assertNotNull("publicKey", this.publicKey);
        RSASSAVerifier verifier = new RSASSAVerifier(this.publicKey);
        return jwt.verify(verifier);
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
        return CommonHelper.toNiceString(this.getClass(), "keys", "[protected]", "algorithm", this.algorithm);
    }
}

