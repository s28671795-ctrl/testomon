/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.signature;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.jwt.config.signature.AbstractSignatureConfiguration;

public class SecretSignatureConfiguration
extends AbstractSignatureConfiguration {
    private byte[] secret;

    public SecretSignatureConfiguration() {
        this.algorithm = JWSAlgorithm.HS256;
    }

    public SecretSignatureConfiguration(String secret) {
        this(secret.getBytes(StandardCharsets.UTF_8));
    }

    public SecretSignatureConfiguration(byte[] secret) {
        this();
        this.secret = Arrays.copyOf(secret, secret.length);
    }

    public SecretSignatureConfiguration(String secret, JWSAlgorithm algorithm) {
        this(secret.getBytes(StandardCharsets.UTF_8), algorithm);
    }

    public SecretSignatureConfiguration(byte[] secret, JWSAlgorithm algorithm) {
        this.secret = Arrays.copyOf(secret, secret.length);
        this.algorithm = algorithm;
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("algorithm", this.algorithm);
        CommonHelper.assertNotNull("secret", this.secret);
        if (!this.supports(this.algorithm)) {
            throw new TechnicalException("Only the HS256, HS384 and HS512 algorithms are supported for HMac signature");
        }
    }

    @Override
    public boolean supports(JWSAlgorithm algorithm) {
        return algorithm != null && MACVerifier.SUPPORTED_ALGORITHMS.contains(algorithm);
    }

    @Override
    public SignedJWT sign(JWTClaimsSet claims) {
        this.init();
        try {
            MACSigner signer = new MACSigner(this.secret);
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
        MACVerifier verifier = new MACVerifier(this.secret);
        return jwt.verify(verifier);
    }

    public String getSecret() {
        return new String(this.secret, StandardCharsets.UTF_8);
    }

    public void setSecret(String secret) {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getSecretBytes() {
        return Arrays.copyOf(this.secret, this.secret.length);
    }

    public void setSecretBytes(byte[] secretBytes) {
        this.secret = Arrays.copyOf(secretBytes, secretBytes.length);
    }

    public String getSecretBase64() {
        return Base64.encode(this.secret).toString();
    }

    public void setSecretBase64(String secret) {
        this.secret = new Base64(secret).decode();
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "secret", "[protected]", "algorithm", this.algorithm);
    }
}

