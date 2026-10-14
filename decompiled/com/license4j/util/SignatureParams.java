/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.util;

import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.util.Hex;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureParams {
    private static final Logger log = LoggerFactory.getLogger(SignatureParams.class);
    private final Signature signature;
    private final String publicKey;
    private final String signatureAlgorithm;
    private final String keypairAlgorithm;
    private final int keySize;

    public SignatureParams(String publicKey) throws LicenseSecurityException {
        this.publicKey = publicKey;
        String updatedPublicKey = this.updatePublicKey(publicKey);
        int startIndex = updatedPublicKey.length() < 250 ? 25 : 250;
        int keyAlgorithmLength = Integer.parseInt(updatedPublicKey.substring(startIndex, startIndex + 2));
        this.keypairAlgorithm = updatedPublicKey.substring(startIndex + 2, startIndex + 2 + keyAlgorithmLength);
        int keySizeLength = Integer.parseInt(updatedPublicKey.substring(startIndex + 2 + keyAlgorithmLength, startIndex + 2 + keyAlgorithmLength + 1));
        this.keySize = Integer.parseInt(updatedPublicKey.substring(startIndex + 2 + keyAlgorithmLength + 1, startIndex + 2 + keyAlgorithmLength + 1 + keySizeLength));
        int signatureAlgorithmLength = Integer.parseInt(updatedPublicKey.substring(startIndex + 2 + keyAlgorithmLength + 1 + keySizeLength, startIndex + 2 + keyAlgorithmLength + 1 + keySizeLength + 2));
        this.signatureAlgorithm = updatedPublicKey.substring(startIndex + 2 + keyAlgorithmLength + 1 + keySizeLength + 2, startIndex + 2 + keyAlgorithmLength + 1 + keySizeLength + 2 + signatureAlgorithmLength);
        String validatingString = updatedPublicKey.substring(0, 250) + updatedPublicKey.substring(keyAlgorithmLength + 252 + 1 + keySizeLength + 2 + signatureAlgorithmLength);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(this.keypairAlgorithm);
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(Hex.decodeHex(validatingString.toCharArray()));
            PublicKey publicKeyFactory = keyFactory.generatePublic(encodedKeySpec);
            this.signature = Signature.getInstance(this.signatureAlgorithm);
            this.signature.initVerify(publicKeyFactory);
        }
        catch (NoSuchAlgorithmException e) {
            log.debug("EX1170", e);
            throw new LicenseSecurityException(e.getMessage());
        }
        catch (InvalidKeyException e) {
            log.debug("EX1172", e);
            throw new LicenseSecurityException(e.getMessage());
        }
        catch (InvalidKeySpecException e) {
            log.debug("EX1173", e);
            throw new LicenseSecurityException(e.getMessage());
        }
    }

    private String updatePublicKey(String publicKey) {
        Object result = publicKey;
        if (publicKey.contains("SHA512withECDSA")) {
            result = publicKey.substring(0, 50) + publicKey.substring(publicKey.indexOf("G") + 1);
        } else if (publicKey.contains("NOEC")) {
            result = publicKey.replace("NOECG", "");
        }
        return result;
    }

    public boolean validateLicenseText(String baseProperties, String licenseSignature) throws LicenseSecurityException {
        try {
            this.signature.update(baseProperties.getBytes(StandardCharsets.UTF_8));
            return this.signature.verify(Hex.decodeHex(licenseSignature.toCharArray()));
        }
        catch (SignatureException e) {
            log.debug("EX1171", e);
            throw new LicenseSecurityException(e.getMessage());
        }
    }

    public Signature getSignature() {
        return this.signature;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public String getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public String getKeypairAlgorithm() {
        return this.keypairAlgorithm;
    }

    public int getKeySize() {
        return this.keySize;
    }
}

