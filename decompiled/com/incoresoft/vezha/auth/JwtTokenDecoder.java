/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.auth;

import com.google.inject.Inject;
import com.incoresoft.vezha.auth.JwtAuthClaims;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.javalin.http.UnauthorizedResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import org.pac4j.jwt.config.signature.RSASignatureConfiguration;

public class JwtTokenDecoder {
    public static final String PUBLIC_AUTH_KEY_PATH = "public-auth-key.pem";
    private final RSASignatureConfiguration signatureConfiguration;

    @Inject
    public JwtTokenDecoder() {
        File publicKeyFile = new File(PUBLIC_AUTH_KEY_PATH);
        if (!publicKeyFile.exists()) {
            throw new IllegalArgumentException("JwtTokenDecoder cannot be initialized: public auth key file not found");
        }
        try {
            RSAPublicKey publicKey = JwtTokenDecoder.readPublicKey(publicKeyFile);
            this.signatureConfiguration = new RSASignatureConfiguration();
            this.signatureConfiguration.setPublicKey(publicKey);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("JwtTokenDecoder cannot be initialized: auth key file cannot be read", e);
        }
    }

    public JwtAuthClaims decode(String token) {
        try {
            SignedJWT signed = SignedJWT.parse(token);
            boolean isValid = this.signatureConfiguration.verify(signed);
            if (!isValid) {
                throw new IllegalArgumentException("Invalid token signature");
            }
            JWTClaimsSet claims = signed.getJWTClaimsSet();
            return new JwtAuthClaims(claims.getStringClaim("email"), Instant.ofEpochMilli(claims.getLongClaim("expiration")));
        }
        catch (Exception e) {
            throw new UnauthorizedResponse("Cannot decode jwt token: " + e.getMessage());
        }
    }

    private static RSAPublicKey readPublicKey(File publicKeyFile) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
    }
}

