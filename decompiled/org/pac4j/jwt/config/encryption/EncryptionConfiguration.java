/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.encryption;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWT;

public interface EncryptionConfiguration {
    public boolean supports(JWEAlgorithm var1, EncryptionMethod var2);

    public String encrypt(JWT var1);

    public void decrypt(EncryptedJWT var1) throws JOSEException;
}

