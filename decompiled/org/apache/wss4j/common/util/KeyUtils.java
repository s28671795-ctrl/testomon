/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.utils.JavaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KeyUtils {
    private static final Logger LOG = LoggerFactory.getLogger(KeyUtils.class);
    private static final int MAX_SYMMETRIC_KEY_SIZE = 1024;
    private static final Map<String, Integer> DEFAULT_DERIVED_KEY_LENGTHS = new HashMap<String, Integer>();
    public static final String RSA_ECB_OAEPWITH_SHA1_AND_MGF1_PADDING = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";
    private static MessageDigest digest;

    private KeyUtils() {
    }

    public static int getKeyLength(String algorithm) throws WSSecurityException {
        if (algorithm == null) {
            return 0;
        }
        int size = JCEMapper.getKeyLengthFromURI(algorithm);
        if (size == 0 && DEFAULT_DERIVED_KEY_LENGTHS.containsKey(algorithm)) {
            size = DEFAULT_DERIVED_KEY_LENGTHS.get(algorithm);
        }
        return size / 8;
    }

    public static SecretKey prepareSecretKey(String algorithm, byte[] rawKey) {
        int size = 0;
        try {
            size = JCEMapper.getKeyLengthFromURI(algorithm) / 8;
        }
        catch (Exception e) {
            LOG.debug(e.getMessage());
        }
        String keyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(algorithm);
        SecretKeySpec keySpec = size > 0 && !algorithm.endsWith("gcm") && !algorithm.contains("hmac-") ? new SecretKeySpec(rawKey, 0, rawKey.length > size ? size : rawKey.length, keyAlgorithm) : (rawKey.length > 1024 ? new SecretKeySpec(rawKey, 0, 1024, keyAlgorithm) : new SecretKeySpec(rawKey, keyAlgorithm));
        return keySpec;
    }

    public static KeyGenerator getKeyGenerator(String algorithm) throws WSSecurityException {
        try {
            String keyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(algorithm);
            if (keyAlgorithm == null || "".equals(keyAlgorithm)) {
                keyAlgorithm = JCEMapper.translateURItoJCEID(algorithm);
            }
            KeyGenerator keyGen = KeyGenerator.getInstance(keyAlgorithm);
            if (algorithm.equalsIgnoreCase("http://www.w3.org/2001/04/xmlenc#aes128-cbc") || algorithm.equalsIgnoreCase("http://www.w3.org/2009/xmlenc11#aes128-gcm")) {
                keyGen.init(128);
            } else if (algorithm.equalsIgnoreCase("http://www.w3.org/2001/04/xmlenc#aes192-cbc") || algorithm.equalsIgnoreCase("http://www.w3.org/2009/xmlenc11#aes192-gcm")) {
                keyGen.init(192);
            } else if (algorithm.equalsIgnoreCase("http://www.w3.org/2001/04/xmlenc#aes256-cbc") || algorithm.equalsIgnoreCase("http://www.w3.org/2009/xmlenc11#aes256-gcm")) {
                keyGen.init(256);
            }
            return keyGen;
        }
        catch (NoSuchAlgorithmException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, (Exception)e);
        }
    }

    public static Cipher getCipherInstance(String cipherAlgo) throws WSSecurityException {
        return KeyUtils.getCipherInstance(cipherAlgo, null);
    }

    public static Cipher getCipherInstance(String cipherAlgo, String provider) throws WSSecurityException {
        String keyAlgorithm = JCEMapper.translateURItoJCEID(cipherAlgo);
        if (keyAlgorithm == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, "unsupportedKeyTransp", new Object[]{"No such algorithm: \"" + cipherAlgo + "\""});
        }
        if (provider == null) {
            provider = JCEMapper.getProviderId();
        } else {
            JavaUtils.checkRegisterPermission();
        }
        try {
            if (provider == null) {
                return Cipher.getInstance(keyAlgorithm);
            }
            return Cipher.getInstance(keyAlgorithm, provider);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            if ("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(cipherAlgo)) {
                try {
                    if (provider == null) {
                        return Cipher.getInstance(RSA_ECB_OAEPWITH_SHA1_AND_MGF1_PADDING);
                    }
                    return Cipher.getInstance(RSA_ECB_OAEPWITH_SHA1_AND_MGF1_PADDING, provider);
                }
                catch (NoSuchProviderException ex1) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, ex1, "unsupportedKeyTransp", new Object[]{"No such provider \"" + JCEMapper.getProviderId() + "\" for \"" + RSA_ECB_OAEPWITH_SHA1_AND_MGF1_PADDING + "\""});
                }
                catch (NoSuchPaddingException ex1) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, e, "unsupportedKeyTransp", new Object[]{"No such padding: \"RSA/ECB/OAEPWithSHA1AndMGF1Padding\""});
                }
                catch (NoSuchAlgorithmException ex1) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, e, "unsupportedKeyTransp", new Object[]{"No such algorithm: \"RSA/ECB/OAEPWithSHA1AndMGF1Padding\""});
                }
            }
            if (e instanceof NoSuchAlgorithmException) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, e, "unsupportedKeyTransp", new Object[]{"No such algorithm: \"" + keyAlgorithm + "\""});
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, e, "unsupportedKeyTransp", new Object[]{"No such padding: \"" + keyAlgorithm + "\""});
        }
        catch (NoSuchProviderException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, ex, "unsupportedKeyTransp", new Object[]{"No such provider \"" + JCEMapper.getProviderId() + "\" for \"" + keyAlgorithm + "\""});
        }
    }

    public static synchronized byte[] generateDigest(byte[] inputBytes) throws WSSecurityException {
        try {
            if (digest == null) {
                digest = MessageDigest.getInstance("SHA-1");
            }
            return digest.digest(inputBytes);
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"Error in generating digest"});
        }
    }

    static {
        DEFAULT_DERIVED_KEY_LENGTHS.put("http://www.w3.org/2001/04/xmldsig-more#hmac-md5", 128);
        DEFAULT_DERIVED_KEY_LENGTHS.put("http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160", 160);
        DEFAULT_DERIVED_KEY_LENGTHS.put("http://www.w3.org/2000/09/xmldsig#hmac-sha1", 160);
        DEFAULT_DERIVED_KEY_LENGTHS.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha224", 224);
        DEFAULT_DERIVED_KEY_LENGTHS.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256", 256);
        DEFAULT_DERIVED_KEY_LENGTHS.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384", 384);
        DEFAULT_DERIVED_KEY_LENGTHS.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512", 512);
    }
}

