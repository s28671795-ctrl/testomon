/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.encryption;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.AESDecrypter;
import com.nimbusds.jose.crypto.AESEncrypter;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.jwt.config.encryption.AbstractEncryptionConfiguration;

public class SecretEncryptionConfiguration
extends AbstractEncryptionConfiguration {
    private byte[] secret;

    public SecretEncryptionConfiguration() {
        this.algorithm = JWEAlgorithm.DIR;
        this.method = EncryptionMethod.A256GCM;
    }

    public SecretEncryptionConfiguration(byte[] secret) {
        this();
        this.secret = Arrays.copyOf(secret, secret.length);
    }

    public SecretEncryptionConfiguration(String secret) {
        this(secret.getBytes(StandardCharsets.UTF_8));
    }

    public SecretEncryptionConfiguration(byte[] secret, JWEAlgorithm algorithm, EncryptionMethod method2) {
        this.secret = Arrays.copyOf(secret, secret.length);
        this.algorithm = algorithm;
        this.method = method2;
    }

    public SecretEncryptionConfiguration(String secret, JWEAlgorithm algorithm, EncryptionMethod method2) {
        this(secret.getBytes(StandardCharsets.UTF_8), algorithm, method2);
    }

    @Override
    public boolean supports(JWEAlgorithm algorithm, EncryptionMethod method2) {
        if (algorithm != null && method2 != null) {
            boolean isDirect = DirectDecrypter.SUPPORTED_ALGORITHMS.contains(algorithm) && DirectDecrypter.SUPPORTED_ENCRYPTION_METHODS.contains(method2);
            boolean isAes = AESDecrypter.SUPPORTED_ALGORITHMS.contains(algorithm) && AESDecrypter.SUPPORTED_ENCRYPTION_METHODS.contains(method2);
            return isDirect || isAes;
        }
        return false;
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("secret", this.secret);
        CommonHelper.assertNotNull("algorithm", this.algorithm);
        CommonHelper.assertNotNull("method", this.method);
        if (!this.supports(this.algorithm, this.method)) {
            throw new TechnicalException("Only the direct and AES algorithms are supported with the appropriate encryption method");
        }
    }

    @Override
    protected JWEEncrypter buildEncrypter() {
        try {
            if (DirectDecrypter.SUPPORTED_ALGORITHMS.contains(this.algorithm)) {
                return new DirectEncrypter(this.secret);
            }
            return new AESEncrypter(this.secret);
        }
        catch (KeyLengthException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    protected JWEDecrypter buildDecrypter() {
        try {
            if (DirectDecrypter.SUPPORTED_ALGORITHMS.contains(this.algorithm)) {
                return new DirectDecrypter(this.secret);
            }
            return new AESDecrypter(this.secret);
        }
        catch (KeyLengthException e) {
            throw new TechnicalException(e);
        }
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
        return CommonHelper.toNiceString(this.getClass(), "secret", "[protected]", "algorithm", this.algorithm, "method", this.method);
    }
}

