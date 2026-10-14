/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.DestroyFailedException;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.Base64UrlUtility;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.rt.security.crypto.CryptoUtils;

public final class HmacUtils {
    private static final Logger LOG = LogUtils.getL7dLogger(CryptoUtils.class);

    private HmacUtils() {
    }

    public static String encodeHmacString(String macSecret, String macAlgoJavaName, String data) {
        return Base64Utility.encode(HmacUtils.computeHmac(macSecret, macAlgoJavaName, data));
    }

    public static String encodeHmacString(String macSecret, String macAlgoJavaName, String data, boolean urlSafe) {
        byte[] bytes = HmacUtils.computeHmac(macSecret, macAlgoJavaName, data);
        return urlSafe ? Base64UrlUtility.encode(bytes) : Base64Utility.encode(bytes);
    }

    public static Mac getMac(String macAlgoJavaName) {
        return HmacUtils.getMac(macAlgoJavaName, (String)null);
    }

    public static Mac getMac(String macAlgoJavaName, String provider) {
        try {
            return provider == null ? Mac.getInstance(macAlgoJavaName) : Mac.getInstance(macAlgoJavaName, provider);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new SecurityException(e);
        }
    }

    public static Mac getMac(String macAlgoJavaName, Provider provider) {
        try {
            return Mac.getInstance(macAlgoJavaName, provider);
        }
        catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] computeHmac(String key, String macAlgoJavaName, String data) {
        Mac mac = HmacUtils.getMac(macAlgoJavaName);
        return HmacUtils.computeHmac(key, mac, data);
    }

    public static byte[] computeHmac(byte[] key, String macAlgoJavaName, String data) {
        return HmacUtils.computeHmac(key, macAlgoJavaName, null, data);
    }

    public static byte[] computeHmac(byte[] key, String macAlgoJavaName, AlgorithmParameterSpec spec, String data) {
        Mac mac = HmacUtils.getMac(macAlgoJavaName);
        SecretKeySpec secretKey = new SecretKeySpec(key, mac.getAlgorithm());
        byte[] digest = HmacUtils.computeHmac(secretKey, mac, spec, data);
        try {
            secretKey.destroy();
        }
        catch (DestroyFailedException e) {
            LOG.log(Level.FINE, "Error destroying key: {}", e.getMessage());
        }
        return digest;
    }

    public static byte[] computeHmac(String key, Mac hmac, String data) {
        return HmacUtils.computeHmac(key.getBytes(StandardCharsets.UTF_8), hmac, data);
    }

    public static byte[] computeHmac(byte[] key, Mac hmac, String data) {
        SecretKeySpec secretKey = new SecretKeySpec(key, hmac.getAlgorithm());
        byte[] digest = HmacUtils.computeHmac(secretKey, hmac, data);
        try {
            secretKey.destroy();
        }
        catch (DestroyFailedException destroyFailedException) {
            // empty catch block
        }
        return digest;
    }

    public static byte[] computeHmac(Key secretKey, Mac hmac, String data) {
        return HmacUtils.computeHmac(secretKey, hmac, null, data);
    }

    public static byte[] computeHmac(Key secretKey, Mac hmac, AlgorithmParameterSpec spec, String data) {
        HmacUtils.initMac(hmac, secretKey, spec);
        return hmac.doFinal(data.getBytes());
    }

    public static Mac getInitializedMac(byte[] key, String algo, AlgorithmParameterSpec spec) {
        Mac hmac = HmacUtils.getMac(algo);
        HmacUtils.initMac(hmac, key, spec);
        return hmac;
    }

    private static void initMac(Mac hmac, byte[] key, AlgorithmParameterSpec spec) {
        HmacUtils.initMac(hmac, new SecretKeySpec(key, hmac.getAlgorithm()), spec);
    }

    private static void initMac(Mac hmac, Key secretKey, AlgorithmParameterSpec spec) {
        try {
            if (spec == null) {
                hmac.init(secretKey);
            } else {
                hmac.init(secretKey, spec);
            }
        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new SecurityException(e);
        }
    }

    public static String generateKey(String algo) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(algo);
            SecretKey secretKey = keyGen.generateKey();
            byte[] encodedSecretKey = secretKey.getEncoded();
            String encodedKey = Base64Utility.encode(encodedSecretKey);
            Arrays.fill(encodedSecretKey, (byte)0);
            try {
                secretKey.destroy();
            }
            catch (DestroyFailedException destroyFailedException) {
                // empty catch block
            }
            return encodedKey;
        }
        catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
    }
}

