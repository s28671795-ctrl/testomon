/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.signature;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public interface SignatureConfiguration {
    public boolean supports(JWSAlgorithm var1);

    public SignedJWT sign(JWTClaimsSet var1);

    public boolean verify(SignedJWT var1) throws JOSEException;
}

