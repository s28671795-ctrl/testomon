/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.text.ParseException;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;

public final class JWKHelper {
    public static String buildSecretFromJwk(String json) {
        CommonHelper.assertNotBlank("json", json);
        try {
            OctetSequenceKey octetSequenceKey = OctetSequenceKey.parse(json);
            return new String(octetSequenceKey.toByteArray(), "UTF-8");
        }
        catch (UnsupportedEncodingException | ParseException e) {
            throw new TechnicalException(e);
        }
    }

    public static KeyPair buildRSAKeyPairFromJwk(String json) {
        CommonHelper.assertNotBlank("json", json);
        try {
            RSAKey rsaKey = RSAKey.parse(json);
            return rsaKey.toKeyPair();
        }
        catch (JOSEException | ParseException e) {
            throw new TechnicalException(e);
        }
    }

    public static KeyPair buildECKeyPairFromJwk(String json) {
        CommonHelper.assertNotBlank("json", json);
        try {
            ECKey ecKey = ECKey.parse(json);
            return ecKey.toKeyPair();
        }
        catch (JOSEException | ParseException e) {
            throw new TechnicalException(e);
        }
    }
}

