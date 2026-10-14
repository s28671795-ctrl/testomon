/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.encryption;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.InitializableObject;
import org.pac4j.jwt.config.encryption.EncryptionConfiguration;

public abstract class AbstractEncryptionConfiguration
extends InitializableObject
implements EncryptionConfiguration {
    protected JWEAlgorithm algorithm;
    protected EncryptionMethod method;

    @Override
    public String encrypt(JWT jwt) {
        this.init();
        if (jwt instanceof SignedJWT) {
            JWEObject jweObject = new JWEObject(new JWEHeader.Builder(this.algorithm, this.method).contentType("JWT").build(), new Payload((SignedJWT)jwt));
            try {
                jweObject.encrypt(this.buildEncrypter());
            }
            catch (JOSEException e) {
                throw new TechnicalException(e);
            }
            return jweObject.serialize();
        }
        JWEHeader header = new JWEHeader(this.algorithm, this.method);
        try {
            EncryptedJWT encryptedJwt = new EncryptedJWT(header, jwt.getJWTClaimsSet());
            encryptedJwt.encrypt(this.buildEncrypter());
            return encryptedJwt.serialize();
        }
        catch (JOSEException | ParseException e) {
            throw new TechnicalException(e);
        }
    }

    protected abstract JWEEncrypter buildEncrypter();

    @Override
    public void decrypt(EncryptedJWT encryptedJWT) throws JOSEException {
        this.init();
        encryptedJWT.decrypt(this.buildDecrypter());
    }

    protected abstract JWEDecrypter buildDecrypter();

    public JWEAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(JWEAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public EncryptionMethod getMethod() {
        return this.method;
    }

    public void setMethod(EncryptionMethod method2) {
        this.method = method2;
    }
}

