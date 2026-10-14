/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.crypto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.DestroyFailedException;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.util.Base64UrlUtility;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.common.util.CompressionUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.JavaUtils;
import org.apache.cxf.rt.security.crypto.KeyProperties;

public final class CryptoUtils {
    private CryptoUtils() {
    }

    public static void installBouncyCastleProvider() throws Exception {
        String bcClassName = "org.bouncycastle.jce.provider.BouncyCastleProvider";
        if (Security.getProvider("org.bouncycastle.jce.provider.BouncyCastleProvider") == null) {
            Security.addProvider((Provider)ClassLoaderUtils.loadClass("org.bouncycastle.jce.provider.BouncyCastleProvider", CryptoUtils.class).newInstance());
        }
    }

    public static void removeBouncyCastleProvider() {
        Security.removeProvider("org.bouncycastle.jce.provider.BouncyCastleProvider");
    }

    public static String encodeSecretKey(SecretKey key) throws SecurityException {
        return CryptoUtils.encodeBytes(key.getEncoded());
    }

    public static String encryptSecretKey(SecretKey secretKey, PublicKey publicKey) throws SecurityException {
        KeyProperties props = new KeyProperties(publicKey.getAlgorithm());
        return CryptoUtils.encryptSecretKey(secretKey, publicKey, props);
    }

    public static String encryptSecretKey(SecretKey secretKey, PublicKey publicKey, KeyProperties props) throws SecurityException {
        byte[] encryptedBytes = CryptoUtils.wrapSecretKey(secretKey, publicKey, props);
        return CryptoUtils.encodeBytes(encryptedBytes);
    }

    public static byte[] generateSecureRandomBytes(int size) {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[size];
        sr.nextBytes(bytes);
        return bytes;
    }

    public static RSAPublicKey getRSAPublicKey(String encodedModulus, String encodedPublicExponent) {
        try {
            return CryptoUtils.getRSAPublicKey(CryptoUtils.decodeSequence(encodedModulus), CryptoUtils.decodeSequence(encodedPublicExponent));
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static RSAPublicKey getRSAPublicKey(byte[] modulusBytes, byte[] publicExponentBytes) {
        try {
            return CryptoUtils.getRSAPublicKey(KeyFactory.getInstance("RSA"), modulusBytes, publicExponentBytes);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static RSAPublicKey getRSAPublicKey(KeyFactory factory2, byte[] modulusBytes, byte[] publicExponentBytes) {
        BigInteger modulus = CryptoUtils.toBigInteger(modulusBytes);
        BigInteger publicExponent = CryptoUtils.toBigInteger(publicExponentBytes);
        return CryptoUtils.getRSAPublicKey(factory2, modulus, publicExponent);
    }

    public static RSAPublicKey getRSAPublicKey(BigInteger modulusBytes, BigInteger publicExponentBytes) {
        try {
            return CryptoUtils.getRSAPublicKey(KeyFactory.getInstance("RSA"), modulusBytes, publicExponentBytes);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static RSAPublicKey getRSAPublicKey(KeyFactory factory2, BigInteger modulus, BigInteger publicExponent) {
        try {
            return (RSAPublicKey)factory2.generatePublic(new RSAPublicKeySpec(modulus, publicExponent));
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static RSAPrivateKey getRSAPrivateKey(String encodedModulus, String encodedPrivateExponent) {
        try {
            return CryptoUtils.getRSAPrivateKey(CryptoUtils.decodeSequence(encodedModulus), CryptoUtils.decodeSequence(encodedPrivateExponent));
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static RSAPrivateKey getRSAPrivateKey(byte[] modulusBytes, byte[] privateExponentBytes) {
        BigInteger modulus = CryptoUtils.toBigInteger(modulusBytes);
        BigInteger privateExponent = CryptoUtils.toBigInteger(privateExponentBytes);
        try {
            KeyFactory factory2 = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey)factory2.generatePrivate(new RSAPrivateKeySpec(modulus, privateExponent));
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static RSAPrivateKey getRSAPrivateKey(String encodedModulus, String encodedPublicExponent, String encodedPrivateExponent, String encodedPrimeP, String encodedPrimeQ, String encodedPrimeExpP, String encodedPrimeExpQ, String encodedCrtCoefficient) {
        try {
            return CryptoUtils.getRSAPrivateKey(CryptoUtils.decodeSequence(encodedModulus), CryptoUtils.decodeSequence(encodedPublicExponent), CryptoUtils.decodeSequence(encodedPrivateExponent), CryptoUtils.decodeSequence(encodedPrimeP), CryptoUtils.decodeSequence(encodedPrimeQ), CryptoUtils.decodeSequence(encodedPrimeExpP), CryptoUtils.decodeSequence(encodedPrimeExpQ), CryptoUtils.decodeSequence(encodedCrtCoefficient));
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static RSAPrivateKey getRSAPrivateKey(byte[] modulusBytes, byte[] publicExponentBytes, byte[] privateExponentBytes, byte[] primePBytes, byte[] primeQBytes, byte[] primeExpPBytes, byte[] primeExpQBytes, byte[] crtCoefficientBytes) {
        BigInteger modulus = CryptoUtils.toBigInteger(modulusBytes);
        BigInteger publicExponent = CryptoUtils.toBigInteger(publicExponentBytes);
        BigInteger privateExponent = CryptoUtils.toBigInteger(privateExponentBytes);
        BigInteger primeP = CryptoUtils.toBigInteger(primePBytes);
        BigInteger primeQ = CryptoUtils.toBigInteger(primeQBytes);
        BigInteger primeExpP = CryptoUtils.toBigInteger(primeExpPBytes);
        BigInteger primeExpQ = CryptoUtils.toBigInteger(primeExpQBytes);
        BigInteger crtCoefficient = CryptoUtils.toBigInteger(crtCoefficientBytes);
        try {
            KeyFactory factory2 = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey)factory2.generatePrivate(new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent, primeP, primeQ, primeExpP, primeExpQ, crtCoefficient));
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static ECPrivateKey getECPrivateKey(String curve, String encodedPrivateKey) {
        try {
            return CryptoUtils.getECPrivateKey(curve, CryptoUtils.decodeSequence(encodedPrivateKey));
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static ECPrivateKey getECPrivateKey(String curve, byte[] privateKey) {
        try {
            ECParameterSpec params = CryptoUtils.getECParameterSpec(curve, true);
            ECPrivateKeySpec keySpec = new ECPrivateKeySpec(CryptoUtils.toBigInteger(privateKey), params);
            KeyFactory kf = KeyFactory.getInstance("EC");
            return (ECPrivateKey)kf.generatePrivate(keySpec);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    private static ECParameterSpec getECParameterSpec(String curve, boolean isPrivate) throws Exception {
        KeyPair pair = CryptoUtils.generateECKeyPair(curve);
        return isPrivate ? ((ECPublicKey)pair.getPublic()).getParams() : ((ECPrivateKey)pair.getPrivate()).getParams();
    }

    public static KeyPair generateECKeyPair(String curve) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec kpgparams = new ECGenParameterSpec("sec" + curve.toLowerCase().replace("-", "") + "r1");
            kpg.initialize(kpgparams);
            return kpg.generateKeyPair();
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static ECPublicKey getECPublicKey(String curve, String encodedXPoint, String encodedYPoint) {
        try {
            return CryptoUtils.getECPublicKey(curve, CryptoUtils.decodeSequence(encodedXPoint), CryptoUtils.decodeSequence(encodedYPoint));
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static ECPublicKey getECPublicKey(String curve, byte[] xPoint, byte[] yPoint) {
        try {
            ECParameterSpec params = CryptoUtils.getECParameterSpec(curve, false);
            ECPoint ecPoint = new ECPoint(CryptoUtils.toBigInteger(xPoint), CryptoUtils.toBigInteger(yPoint));
            ECPublicKeySpec keySpec = new ECPublicKeySpec(ecPoint, params);
            KeyFactory kf = KeyFactory.getInstance("EC");
            return (ECPublicKey)kf.generatePublic(keySpec);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    private static BigInteger toBigInteger(byte[] bytes) {
        return new BigInteger(1, bytes);
    }

    public static AlgorithmParameterSpec getContentEncryptionCipherSpec(int authTagLength, byte[] iv) {
        if (authTagLength > 0) {
            return CryptoUtils.getGCMParameterSpec(authTagLength, iv);
        }
        if (iv.length > 0) {
            return new IvParameterSpec(iv);
        }
        return null;
    }

    public static AlgorithmParameterSpec getGCMParameterSpec(int authTagLength, byte[] iv) {
        return new GCMParameterSpec(authTagLength, iv);
    }

    public static byte[] signData(byte[] data, PrivateKey key, String signAlgo) {
        return CryptoUtils.signData(data, key, signAlgo, null, null);
    }

    public static byte[] signData(byte[] data, PrivateKey key, String signAlgo, SecureRandom random, AlgorithmParameterSpec params) {
        try {
            Signature s2 = CryptoUtils.getSignature(key, signAlgo, random, params);
            s2.update(data);
            return s2.sign();
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static Signature getSignature(PrivateKey key, String signAlgo, SecureRandom random, AlgorithmParameterSpec params) {
        try {
            Signature s2 = Signature.getInstance(signAlgo);
            if (random == null) {
                s2.initSign(key);
            } else {
                s2.initSign(key, random);
            }
            if (params != null) {
                s2.setParameter(params);
            }
            return s2;
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static Signature getVerificationSignature(PublicKey key, String signAlgo, AlgorithmParameterSpec params) {
        try {
            Signature s2 = Signature.getInstance(signAlgo);
            s2.initVerify(key);
            if (params != null) {
                s2.setParameter(params);
            }
            return s2;
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static boolean verifySignature(byte[] data, byte[] signature, PublicKey key, String signAlgo) {
        return CryptoUtils.verifySignature(data, signature, key, signAlgo, null);
    }

    public static boolean verifySignature(byte[] data, byte[] signature, PublicKey key, String signAlgo, AlgorithmParameterSpec params) {
        try {
            Signature s2 = CryptoUtils.getVerificationSignature(key, signAlgo, params);
            s2.update(data);
            return s2.verify(signature);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static SecretKey getSecretKey(String symEncAlgo) throws SecurityException {
        return CryptoUtils.getSecretKey(new KeyProperties(symEncAlgo));
    }

    public static SecretKey getSecretKey(String symEncAlgo, int keySize) throws SecurityException {
        return CryptoUtils.getSecretKey(new KeyProperties(symEncAlgo, keySize));
    }

    public static SecretKey getSecretKey(KeyProperties props) throws SecurityException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(props.getKeyAlgo());
            AlgorithmParameterSpec algoSpec = props.getAlgoSpec();
            SecureRandom random = props.getSecureRandom();
            if (algoSpec != null) {
                if (random != null) {
                    keyGen.init(algoSpec, random);
                } else {
                    keyGen.init(algoSpec);
                }
            } else {
                int keySize = props.getKeySize();
                if (keySize == -1) {
                    keySize = 128;
                }
                if (random != null) {
                    keyGen.init(keySize, random);
                } else {
                    keyGen.init(keySize);
                }
            }
            return keyGen.generateKey();
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static String decryptSequence(String encodedToken, String encodedSecretKey) throws SecurityException {
        return CryptoUtils.decryptSequence(encodedToken, encodedSecretKey, new KeyProperties("AES"));
    }

    public static String decryptSequence(String encodedData, String encodedSecretKey, KeyProperties props) throws SecurityException {
        SecretKey key = CryptoUtils.decodeSecretKey(encodedSecretKey, props.getKeyAlgo());
        return CryptoUtils.decryptSequence(encodedData, key, props);
    }

    public static String decryptSequence(String encodedData, Key secretKey) throws SecurityException {
        return CryptoUtils.decryptSequence(encodedData, secretKey, null);
    }

    public static String decryptSequence(String encodedData, Key secretKey, KeyProperties props) throws SecurityException {
        byte[] encryptedBytes = CryptoUtils.decodeSequence(encodedData);
        byte[] bytes = CryptoUtils.decryptBytes(encryptedBytes, secretKey, props);
        try {
            return new String(bytes, StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static String encryptSequence(String sequence2, Key secretKey) throws SecurityException {
        return CryptoUtils.encryptSequence(sequence2, secretKey, null);
    }

    public static String encryptSequence(String sequence2, Key secretKey, KeyProperties keyProps) throws SecurityException {
        try {
            byte[] bytes = CryptoUtils.encryptBytes(sequence2.getBytes(StandardCharsets.UTF_8), secretKey, keyProps);
            return CryptoUtils.encodeBytes(bytes);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static String encodeBytes(byte[] bytes) throws SecurityException {
        try {
            return Base64UrlUtility.encode(bytes);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static byte[] encryptBytes(byte[] bytes, Key secretKey) throws SecurityException {
        return CryptoUtils.encryptBytes(bytes, secretKey, null);
    }

    public static byte[] encryptBytes(byte[] bytes, Key secretKey, KeyProperties keyProps) throws SecurityException {
        return CryptoUtils.processBytes(bytes, secretKey, keyProps, 1);
    }

    public static byte[] decryptBytes(byte[] bytes, Key secretKey) throws SecurityException {
        return CryptoUtils.decryptBytes(bytes, secretKey, null);
    }

    public static byte[] decryptBytes(byte[] bytes, Key secretKey, KeyProperties keyProps) throws SecurityException {
        return CryptoUtils.processBytes(bytes, secretKey, keyProps, 2);
    }

    public static byte[] wrapSecretKey(byte[] keyBytes, String keyAlgo, Key wrapperKey, KeyProperties wrapperKeyProps) throws SecurityException {
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, CryptoUtils.convertJCECipherToSecretKeyName(keyAlgo));
        byte[] encryptedKey = CryptoUtils.wrapSecretKey(secretKey, wrapperKey, wrapperKeyProps);
        try {
            secretKey.destroy();
        }
        catch (DestroyFailedException destroyFailedException) {
            // empty catch block
        }
        return encryptedKey;
    }

    public static byte[] wrapSecretKey(Key secretKey, Key wrapperKey, KeyProperties keyProps) throws SecurityException {
        try {
            Cipher c = CryptoUtils.initCipher(wrapperKey, keyProps, 3);
            return c.wrap(secretKey);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static SecretKey unwrapSecretKey(byte[] wrappedBytes, String wrappedKeyAlgo, Key unwrapperKey, String unwrapperKeyAlgo) throws SecurityException {
        return CryptoUtils.unwrapSecretKey(wrappedBytes, wrappedKeyAlgo, unwrapperKey, new KeyProperties(unwrapperKeyAlgo));
    }

    public static SecretKey unwrapSecretKey(byte[] wrappedBytes, String wrappedKeyAlgo, Key unwrapperKey, KeyProperties keyProps) throws SecurityException {
        return (SecretKey)CryptoUtils.unwrapKey(wrappedBytes, wrappedKeyAlgo, unwrapperKey, keyProps, 3);
    }

    public static Key unwrapKey(byte[] wrappedBytes, String wrappedKeyAlgo, Key unwrapperKey, KeyProperties keyProps, int wrappedKeyType) throws SecurityException {
        try {
            Cipher c = CryptoUtils.initCipher(unwrapperKey, keyProps, 4);
            return c.unwrap(wrappedBytes, wrappedKeyAlgo, wrappedKeyType);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    private static byte[] processBytes(byte[] bytes, Key secretKey, KeyProperties keyProps, int mode) throws SecurityException {
        boolean compressionSupported;
        boolean bl = compressionSupported = keyProps != null && keyProps.isCompressionSupported();
        if (compressionSupported && mode == 1) {
            bytes = CompressionUtils.deflate(bytes, false);
        }
        try {
            int blockSize;
            Cipher c = CryptoUtils.initCipher(secretKey, keyProps, mode);
            byte[] result = new byte[]{};
            int n = blockSize = keyProps != null ? keyProps.getBlockSize() : -1;
            if (secretKey instanceof SecretKey && blockSize == -1) {
                result = c.doFinal(bytes);
            } else {
                if (blockSize == -1) {
                    blockSize = JavaUtils.isJava8Before161() ? (secretKey instanceof PublicKey ? 117 : 128) : (secretKey instanceof PublicKey ? 117 : 256);
                }
                boolean updateRequired = keyProps != null && keyProps.getAdditionalData() != null;
                int offset = 0;
                while (offset + blockSize < bytes.length) {
                    byte[] next = !updateRequired ? c.doFinal(bytes, offset, blockSize) : c.update(bytes, offset, blockSize);
                    result = CryptoUtils.addToResult(result, next);
                    offset += blockSize;
                }
                result = offset < bytes.length ? CryptoUtils.addToResult(result, c.doFinal(bytes, offset, bytes.length - offset)) : CryptoUtils.addToResult(result, c.doFinal());
            }
            if (compressionSupported && mode == 2) {
                result = IOUtils.readBytesFromStream(CompressionUtils.inflate(result, false));
            }
            return result;
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static Cipher initCipher(Key secretKey, KeyProperties keyProps, int mode) throws SecurityException {
        try {
            String algorithm = keyProps != null && keyProps.getKeyAlgo() != null ? keyProps.getKeyAlgo() : secretKey.getAlgorithm();
            Cipher c = Cipher.getInstance(algorithm);
            if (keyProps == null || keyProps.getAlgoSpec() == null && keyProps.getSecureRandom() == null) {
                c.init(mode, secretKey);
            } else {
                AlgorithmParameterSpec algoSpec = keyProps.getAlgoSpec();
                SecureRandom random = keyProps.getSecureRandom();
                if (algoSpec == null) {
                    c.init(mode, secretKey, random);
                } else if (random == null) {
                    c.init(mode, secretKey, algoSpec);
                } else {
                    c.init(mode, secretKey, algoSpec, random);
                }
            }
            if (keyProps != null && keyProps.getAdditionalData() != null) {
                c.updateAAD(keyProps.getAdditionalData());
            }
            return c;
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    private static byte[] addToResult(byte[] prefix, byte[] suffix) {
        if (suffix == null || suffix.length == 0) {
            return prefix;
        }
        if (prefix.length == 0) {
            return suffix;
        }
        byte[] result = new byte[prefix.length + suffix.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(suffix, 0, result, prefix.length, suffix.length);
        return result;
    }

    public static SecretKey decodeSecretKey(String encodedSecretKey) throws SecurityException {
        return CryptoUtils.decodeSecretKey(encodedSecretKey, "AES");
    }

    public static SecretKey decodeSecretKey(String encodedSecretKey, String secretKeyAlgo) throws SecurityException {
        byte[] secretKeyBytes = CryptoUtils.decodeSequence(encodedSecretKey);
        return CryptoUtils.createSecretKeySpec(secretKeyBytes, secretKeyAlgo);
    }

    public static SecretKey decryptSecretKey(String encodedEncryptedSecretKey, PrivateKey privateKey) {
        return CryptoUtils.decryptSecretKey(encodedEncryptedSecretKey, "AES", privateKey);
    }

    public static SecretKey decryptSecretKey(String encodedEncryptedSecretKey, String secretKeyAlgo, PrivateKey privateKey) throws SecurityException {
        KeyProperties props = new KeyProperties(privateKey.getAlgorithm());
        return CryptoUtils.decryptSecretKey(encodedEncryptedSecretKey, secretKeyAlgo, props, privateKey);
    }

    public static SecretKey decryptSecretKey(String encodedEncryptedSecretKey, String secretKeyAlgo, KeyProperties props, PrivateKey privateKey) throws SecurityException {
        byte[] encryptedBytes = CryptoUtils.decodeSequence(encodedEncryptedSecretKey);
        return CryptoUtils.unwrapSecretKey(encryptedBytes, secretKeyAlgo, (Key)privateKey, props);
    }

    public static SecretKey createSecretKeySpec(String encodedBytes, String algo) {
        return new SecretKeySpec(CryptoUtils.decodeSequence(encodedBytes), algo);
    }

    public static SecretKey createSecretKeySpec(byte[] bytes, String algo) {
        return new SecretKeySpec(bytes, CryptoUtils.convertJCECipherToSecretKeyName(algo));
    }

    public static byte[] decodeSequence(String encodedSequence) throws SecurityException {
        try {
            return Base64UrlUtility.decode(encodedSequence);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    private static String convertJCECipherToSecretKeyName(String jceCipherName) {
        if (jceCipherName != null) {
            if (jceCipherName.startsWith("AES")) {
                return "AES";
            }
            if (jceCipherName.startsWith("DESede")) {
                return "DESede";
            }
            if (jceCipherName.startsWith("SEED")) {
                return "SEED";
            }
            if (jceCipherName.startsWith("Camellia")) {
                return "Camellia";
            }
        }
        return null;
    }

    public static Certificate loadCertificate(InputStream storeLocation, char[] storePassword, String alias, String storeType) {
        KeyStore keyStore = CryptoUtils.loadKeyStore(storeLocation, storePassword, storeType);
        return CryptoUtils.loadCertificate(keyStore, alias);
    }

    public static Certificate loadCertificate(KeyStore keyStore, String alias) {
        try {
            if (alias == null) {
                throw new SecurityException("No keystore alias was defined");
            }
            if (!keyStore.containsAlias(alias)) {
                throw new SecurityException("No alias exists in the keystore for: " + alias);
            }
            return keyStore.getCertificate(alias);
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static String encodeCertificate(Certificate cert) {
        try {
            return Base64Utility.encode(cert.getEncoded());
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static Certificate decodeCertificate(String encodedCert) {
        try {
            byte[] decoded = Base64Utility.decode(encodedCert);
            return CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static PublicKey loadPublicKey(InputStream storeLocation, char[] storePassword, String alias, String storeType) {
        return CryptoUtils.loadCertificate(storeLocation, storePassword, alias, storeType).getPublicKey();
    }

    public static PublicKey loadPublicKey(KeyStore keyStore, String alias) {
        return CryptoUtils.loadCertificate(keyStore, alias).getPublicKey();
    }

    public static KeyStore loadKeyStore(InputStream storeLocation, char[] storePassword, String type) {
        try {
            KeyStore ks = KeyStore.getInstance(type == null ? KeyStore.getDefaultType() : type);
            ks.load(storeLocation, storePassword);
            return ks;
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    public static PrivateKey loadPrivateKey(InputStream storeLocation, char[] storePassword, char[] keyPassword, String alias, String storeType) {
        KeyStore keyStore = CryptoUtils.loadKeyStore(storeLocation, storePassword, storeType);
        return CryptoUtils.loadPrivateKey(keyStore, keyPassword, alias);
    }

    public static PrivateKey loadPrivateKey(KeyStore keyStore, char[] keyPassword, String alias) {
        try {
            if (alias == null) {
                throw new SecurityException("No keystore alias was defined");
            }
            if (!keyStore.containsAlias(alias)) {
                throw new SecurityException("No alias exists in the keystore for: " + alias);
            }
            if (!keyStore.isKeyEntry(alias)) {
                throw new SecurityException("The given alias " + alias + " is not a private key in the keystore.");
            }
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, new KeyStore.PasswordProtection(keyPassword));
            return pkEntry.getPrivateKey();
        }
        catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }
}

