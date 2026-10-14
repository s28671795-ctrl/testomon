/*
 * Decompiled with CFR 0.152.
 */
package com.license4j;

import com.license4j.LicenseKey;
import com.license4j.LicenseKeyPair;
import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.licensetext.LicenseText;
import com.license4j.util.Base32;
import com.license4j.util.Hex;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseManager {
    private static final Logger log = LoggerFactory.getLogger(LicenseManager.class);
    private static final String LICENSE_SECURITY_ERROR = "License security error. ";
    private static final String SHA_256 = "SHA-256";

    public static LicenseKeyPair createKeyPair(String name, String description, String keyPairAlgorithm, String signatureAlgorithm, int keySize) throws LicenseSecurityException {
        KeyPair keyPair = LicenseManager.generateKeyPair(keyPairAlgorithm, keySize);
        KeyPair extraKeyPair = LicenseManager.generateExtraKeyPair();
        String combinedPublic = LicenseManager.combineEncodedKeys(keyPair, extraKeyPair, keyPairAlgorithm, signatureAlgorithm, keySize);
        String combinedPrivate = LicenseManager.combineEncodedKeys(keyPair, extraKeyPair);
        String id = String.valueOf(System.currentTimeMillis());
        return new LicenseKeyPair(id, name, description, combinedPublic, combinedPrivate, keyPairAlgorithm, signatureAlgorithm, keySize);
    }

    public static String generateLicenseKey(String publicKey, LicenseKey licenseKeyObject) {
        publicKey = LicenseManager.validatePublicKey(publicKey);
        String activationString = LicenseManager.buildActivationString(licenseKeyObject);
        LicenseManager.sanitizeLicenseKeyObject(licenseKeyObject);
        String baseString = LicenseManager.buildBaseString(publicKey, licenseKeyObject, activationString);
        byte[] randomBytes = LicenseManager.generateRandomBytes();
        publicKey = Base32.encode(baseString.getBytes(StandardCharsets.UTF_8));
        byte[] baseBytes = publicKey.getBytes(StandardCharsets.UTF_8);
        byte[] input = LicenseManager.buildInputArray(randomBytes, baseBytes);
        LicenseManager.addDigestToRandomBytes(input, randomBytes);
        int sum = LicenseManager.calculateSum(input);
        char[] encodingChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();
        return LicenseManager.formatLicenseKey(Base32.encode(randomBytes) + encodingChars[sum & 0x1F]);
    }

    public static String generateLicenseText(LicenseKeyPair keypair, LicenseText licenseTextObject) throws LicenseSecurityException {
        LicenseManager.updateLicenseTextObject(keypair, licenseTextObject);
        String privateKeyStr = LicenseManager.sanitizePrivateKey(keypair.getPrivateKey());
        PrivateKey privateKey = LicenseManager.getPrivateKeyFromStr(keypair, privateKeyStr);
        Signature signature = LicenseManager.getSignatureInstance(keypair, privateKey);
        String signedString = LicenseManager.getSignedString(licenseTextObject, signature);
        licenseTextObject.setLicenseSignature(signedString);
        return licenseTextObject.getLicenseFileType() == 1 ? licenseTextObject.getContent(null) : licenseTextObject.getContent(keypair.getPublicKey());
    }

    public static LicenseKeyPair loadKeyPair(String fileName) throws LicenseSecurityException {
        return LicenseKeyPair.loadFromFile(fileName);
    }

    private static KeyPair generateKeyPair(String keyPairAlgorithm, int keySize) throws LicenseSecurityException {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyPairAlgorithm);
            keyPairGenerator.initialize(keySize);
            return keyPairGenerator.genKeyPair();
        }
        catch (NoSuchAlgorithmException e) {
            log.debug("EX4101", e);
            throw new LicenseSecurityException(LICENSE_SECURITY_ERROR + e.getLocalizedMessage());
        }
    }

    private static KeyPair generateExtraKeyPair() throws LicenseSecurityException {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
            keyPairGenerator.initialize(ecSpec);
            return keyPairGenerator.generateKeyPair();
        }
        catch (Exception e) {
            log.debug("EX4102", e);
            throw new LicenseSecurityException(LICENSE_SECURITY_ERROR + e.getLocalizedMessage());
        }
    }

    private static String combineEncodedKeys(KeyPair keyPair1, KeyPair keyPair2, String keyPairAlgorithm, String signatureAlgorithm, int keySize) {
        String encodedKey1 = Hex.encodeHexString(keyPair1.getPublic().getEncoded());
        String encodedKey2 = Hex.encodeHexString(keyPair2.getPublic().getEncoded());
        encodedKey1 = LicenseManager.formatLicenseKey(keyPairAlgorithm, signatureAlgorithm, keySize, encodedKey1);
        encodedKey2 = LicenseManager.formatLicenseKey("EC", "SHA512withECDSA", 112, encodedKey2);
        return encodedKey1.substring(0, 50) + encodedKey2 + "G" + encodedKey1.substring(50);
    }

    private static String combineEncodedKeys(KeyPair keyPair1, KeyPair keyPair2) {
        String encodedKey1 = Hex.encodeHexString(keyPair1.getPrivate().getEncoded());
        String encodedKey2 = Hex.encodeHexString(keyPair2.getPrivate().getEncoded());
        return encodedKey1.substring(0, 50) + encodedKey2 + "G" + encodedKey1.substring(50);
    }

    private static void updateLicenseTextObject(LicenseKeyPair keypair, LicenseText licenseTextObject) {
        long licenseId = licenseTextObject.getLicenseID() == 0L ? System.currentTimeMillis() : licenseTextObject.getLicenseID();
        licenseTextObject.setLicenseID(licenseId);
        if (licenseTextObject.getLicenseFileType() == 1) {
            licenseTextObject.setLicensePublicKey(keypair.getPublicKey());
        }
    }

    private static String sanitizePrivateKey(String privateKeyStr) {
        return privateKeyStr.contains("G") ? privateKeyStr.substring(0, 50) + privateKeyStr.substring(privateKeyStr.indexOf("G") + 1) : privateKeyStr;
    }

    private static PrivateKey getPrivateKeyFromStr(LicenseKeyPair keypair, String privateKeyStr) throws LicenseSecurityException {
        try {
            byte[] privateKeyHex = Hex.decodeHex(privateKeyStr.toCharArray());
            KeyFactory keyFactory = KeyFactory.getInstance(keypair.getLicenseTextKeyPairAlgorithm());
            PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(privateKeyHex);
            return keyFactory.generatePrivate(encodedKeySpec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.debug("EX4113", e);
            throw new LicenseSecurityException(LICENSE_SECURITY_ERROR + e.getLocalizedMessage());
        }
    }

    private static Signature getSignatureInstance(LicenseKeyPair keypair, PrivateKey privateKey) throws LicenseSecurityException {
        try {
            Signature signature = Signature.getInstance(keypair.getLicenseTextSignatureAlgorithm());
            signature.initSign(privateKey);
            return signature;
        }
        catch (InvalidKeyException | NoSuchAlgorithmException e) {
            log.debug("EX4112", e);
            throw new LicenseSecurityException(LICENSE_SECURITY_ERROR + e.getLocalizedMessage());
        }
    }

    private static String getSignedString(LicenseText licenseTextObject, Signature signature) throws LicenseSecurityException {
        try {
            signature.update(licenseTextObject.getBaseProperties().getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(signature.sign());
        }
        catch (SignatureException e) {
            log.debug("EX4116", e);
            throw new LicenseSecurityException(LICENSE_SECURITY_ERROR + e.getLocalizedMessage());
        }
    }

    private static String buildActivationString(LicenseKey licenseKeyObject) {
        Object activationString = "";
        if (licenseKeyObject.isActivationCompleted()) {
            activationString = "-ac";
        } else if (licenseKeyObject.isActivationRequired()) {
            activationString = "-ar";
            String timestamp = Optional.ofNullable(licenseKeyObject.getGenerationDateTime()).map(Instant::toEpochMilli).map(Date::new).map(date -> new SimpleDateFormat("yyyy.MM.dd").format((Date)date)).orElseThrow(() -> new IllegalArgumentException("Generation date must not be null."));
            activationString = (String)activationString + "-" + licenseKeyObject.getActivationPeriod() + "-" + timestamp;
        }
        return activationString;
    }

    private static String validatePublicKey(String publicKey) {
        if (publicKey == null) {
            throw new IllegalArgumentException("Public key must be not null.");
        }
        if (publicKey.length() < 100) {
            throw new IllegalArgumentException("Public key error.");
        }
        return publicKey.trim().replace("\n", "");
    }

    private static void sanitizeLicenseKeyObject(LicenseKey licenseKeyObject) {
        licenseKeyObject.setNameForValidation(Objects.requireNonNullElse(licenseKeyObject.getNameForValidation(), ""));
        licenseKeyObject.setCompanyForValidation(Objects.requireNonNullElse(licenseKeyObject.getCompanyForValidation(), ""));
        licenseKeyObject.setHardwareID(Objects.requireNonNullElse(licenseKeyObject.getHardwareID(), ""));
    }

    private static String buildBaseString(String publicKey, LicenseKey licenseKeyObject, String activationString) {
        return String.valueOf((licenseKeyObject.getInternalString() + (publicKey + publicKey.substring(16, 90)).hashCode() + activationString + licenseKeyObject.getNameForValidation() + licenseKeyObject.getCompanyForValidation() + licenseKeyObject.getHardwareID()).hashCode());
    }

    private static byte[] generateRandomBytes() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[15];
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }

    private static byte[] buildInputArray(byte[] randomBytes, byte[] baseBytes) {
        byte[] input = new byte[5 + baseBytes.length];
        System.arraycopy(randomBytes, 0, input, 0, 5);
        System.arraycopy(baseBytes, 0, input, 5, baseBytes.length);
        return input;
    }

    private static void addDigestToRandomBytes(byte[] input, byte[] randomBytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
            messageDigest.reset();
            System.arraycopy(messageDigest.digest(input), 0, randomBytes, 5, 10);
        }
        catch (NoSuchAlgorithmException e) {
            log.debug("EX4130", e);
        }
    }

    private static int calculateSum(byte[] input) {
        int sum = 17;
        for (byte b : input) {
            sum += b;
        }
        return sum;
    }

    private static String formatLicenseKey(String str) {
        int segmentLength = ((String)(str = ((String)str).replace('0', '8').replace('O', '9'))).length() == 55 ? 50 : 20;
        String initialSegment = ((String)str).substring(0, 5);
        int offset = ((String)str).length() == 55 ? 10 : 0;
        str = ((String)str).charAt(10) + ((String)str).substring(offset + 13, offset + 14) + ((String)str).charAt(offset + 17) + ((String)str).charAt(offset + 20) + ((String)str).charAt(offset + 23) + ((String)str).substring(5, 10) + initialSegment.charAt(0) + ((String)str).substring(11, offset + 13) + initialSegment.charAt(1) + ((String)str).substring(offset + 14, offset + 17) + initialSegment.charAt(2) + ((String)str).substring(offset + 18, offset + 20) + initialSegment.charAt(3) + ((String)str).substring(offset + 21, offset + 23) + initialSegment.charAt(4) + ((String)str).substring(offset + 24);
        StringBuilder segmentBuilder = new StringBuilder();
        for (int j = 0; j < segmentLength; j += 5) {
            segmentBuilder.append((CharSequence)str, j, j + 5).append("-");
        }
        String formattedString = segmentBuilder.toString();
        return formattedString + ((String)str).substring(segmentLength, segmentLength + 5);
    }

    private static String formatLicenseKey(String licenseKeyPart1, String licenseKeyPart2, int version, String additionalData) {
        String lengthPart1 = String.format("%02d", licenseKeyPart1.length());
        String lengthPart2 = String.format("%02d", licenseKeyPart2.length());
        String versionLength = String.valueOf(version);
        StringBuilder formattedKey = new StringBuilder();
        formattedKey.append(lengthPart1);
        formattedKey.append(licenseKeyPart1);
        formattedKey.append(versionLength.length());
        formattedKey.append(versionLength);
        formattedKey.append(lengthPart2);
        formattedKey.append(licenseKeyPart2);
        int additionalDataSplitIndex = additionalData.length() < 250 ? 25 : 250;
        String additionalDataPart1 = additionalData.substring(0, additionalDataSplitIndex);
        String additionalDataPart2 = additionalData.substring(additionalDataSplitIndex);
        return additionalDataPart1 + String.valueOf(formattedKey) + additionalDataPart2;
    }
}

