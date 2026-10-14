/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.signature;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.jwt.config.signature.AbstractSignatureConfiguration;
import org.pac4j.jwt.util.JWKHelper;

public class ECSignatureConfiguration
extends AbstractSignatureConfiguration {
    private ECPublicKey publicKey;
    private ECPrivateKey privateKey;

    public ECSignatureConfiguration() {
        this.algorithm = JWSAlgorithm.ES256;
    }

    public ECSignatureConfiguration(KeyPair keyPair) {
        this();
        this.setKeyPair(keyPair);
    }

    public ECSignatureConfiguration(KeyPair keyPair, JWSAlgorithm algorithm) {
        this.setKeyPair(keyPair);
        this.algorithm = algorithm;
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("algorithm", this.algorithm);
        if (!this.supports(this.algorithm)) {
            throw new TechnicalException("Only the ES256, ES384 and ES512 algorithms are supported for elliptic curve signature");
        }
    }

    @Override
    public boolean supports(JWSAlgorithm algorithm) {
        return algorithm != null && ECDSAVerifier.SUPPORTED_ALGORITHMS.contains(algorithm);
    }

    @Override
    public SignedJWT sign(JWTClaimsSet claims) {
        this.init();
        CommonHelper.assertNotNull("privateKey", this.privateKey);
        try {
            ECDSASigner signer = new ECDSASigner(this.privateKey);
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
        ECDSAVerifier verifier = new ECDSAVerifier(this.publicKey);
        return jwt.verify(verifier);
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
        return CommonHelper.toNiceString(this.getClass(), "keys", "[protected]", "algorithm", this.algorithm);
    }
}

