/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import org.conscrypt.Conscrypt;

final class CryptoUpcalls {
    private static final Logger logger = Logger.getLogger(CryptoUpcalls.class.getName());

    private CryptoUpcalls() {
    }

    private static ArrayList<Provider> getExternalProviders(String algorithm) {
        ArrayList<Provider> providers = new ArrayList<Provider>(1);
        for (Provider p : Security.getProviders(algorithm)) {
            if (Conscrypt.isConscrypt(p)) continue;
            providers.add(p);
        }
        if (providers.isEmpty()) {
            logger.warning("Could not find external provider for algorithm: " + algorithm);
        }
        return providers;
    }

    static byte[] ecSignDigestWithPrivateKey(PrivateKey javaKey, byte[] message) {
        String keyAlgorithm = javaKey.getAlgorithm();
        if (!"EC".equals(keyAlgorithm)) {
            throw new RuntimeException("Unexpected key type: " + javaKey.toString());
        }
        return CryptoUpcalls.signDigestWithPrivateKey(javaKey, message, "NONEwithECDSA");
    }

    private static byte[] signDigestWithPrivateKey(PrivateKey javaKey, byte[] message, String algorithm) {
        Signature signature;
        try {
            signature = Signature.getInstance(algorithm);
            signature.initSign(javaKey);
            if (Conscrypt.isConscrypt(signature.getProvider())) {
                signature = null;
            }
        }
        catch (NoSuchAlgorithmException e) {
            logger.warning("Unsupported signature algorithm: " + algorithm);
            return null;
        }
        catch (InvalidKeyException e) {
            logger.warning("Preferred provider doesn't support key:");
            e.printStackTrace();
            signature = null;
        }
        if (signature == null) {
            ArrayList<Provider> providers = CryptoUpcalls.getExternalProviders("Signature." + algorithm);
            RuntimeException savedRuntimeException = null;
            for (Provider p : providers) {
                try {
                    signature = Signature.getInstance(algorithm, p);
                    signature.initSign(javaKey);
                    break;
                }
                catch (InvalidKeyException | NoSuchAlgorithmException e) {
                    signature = null;
                }
                catch (RuntimeException e) {
                    signature = null;
                    if (savedRuntimeException != null) continue;
                    savedRuntimeException = e;
                }
            }
            if (signature == null) {
                if (savedRuntimeException != null) {
                    throw savedRuntimeException;
                }
                logger.warning("Could not find provider for algorithm: " + algorithm);
                return null;
            }
        }
        try {
            signature.update(message);
            return signature.sign();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Exception while signing message with " + javaKey.getAlgorithm() + " private key:", e);
            return null;
        }
    }

    static byte[] rsaSignDigestWithPrivateKey(PrivateKey javaKey, int openSSLPadding, byte[] message) {
        return CryptoUpcalls.rsaOpWithPrivateKey(javaKey, openSSLPadding, 1, message);
    }

    static byte[] rsaDecryptWithPrivateKey(PrivateKey javaKey, int openSSLPadding, byte[] input) {
        return CryptoUpcalls.rsaOpWithPrivateKey(javaKey, openSSLPadding, 2, input);
    }

    private static byte[] rsaOpWithPrivateKey(PrivateKey javaKey, int openSSLPadding, int cipherMode, byte[] input) {
        String jcaPadding;
        String keyAlgorithm = javaKey.getAlgorithm();
        if (!"RSA".equals(keyAlgorithm)) {
            logger.warning("Unexpected key type: " + keyAlgorithm);
            return null;
        }
        switch (openSSLPadding) {
            case 1: {
                jcaPadding = "PKCS1Padding";
                break;
            }
            case 3: {
                jcaPadding = "NoPadding";
                break;
            }
            case 4: {
                jcaPadding = "OAEPPadding";
                break;
            }
            default: {
                logger.warning("Unsupported OpenSSL/BoringSSL padding: " + openSSLPadding);
                return null;
            }
        }
        String transformation = "RSA/ECB/" + jcaPadding;
        Cipher c = null;
        try {
            c = Cipher.getInstance(transformation);
            c.init(cipherMode, javaKey);
            if (Conscrypt.isConscrypt(c.getProvider())) {
                c = null;
            }
        }
        catch (NoSuchAlgorithmException e) {
            logger.warning("Unsupported cipher algorithm: " + transformation);
            return null;
        }
        catch (NoSuchPaddingException e) {
            logger.warning("Unsupported cipher algorithm: " + transformation);
            return null;
        }
        catch (InvalidKeyException e) {
            logger.log(Level.WARNING, "Preferred provider doesn't support key:", e);
            c = null;
        }
        if (c == null) {
            ArrayList<Provider> providers = CryptoUpcalls.getExternalProviders("Cipher." + transformation);
            for (Provider p : providers) {
                try {
                    c = Cipher.getInstance(transformation, p);
                    c.init(cipherMode, javaKey);
                    break;
                }
                catch (NoSuchAlgorithmException e) {
                    c = null;
                }
                catch (InvalidKeyException e) {
                    c = null;
                }
                catch (NoSuchPaddingException e) {
                    c = null;
                }
            }
            if (c == null) {
                logger.warning("Could not find provider for algorithm: " + transformation);
                return null;
            }
        }
        try {
            return c.doFinal(input);
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Exception while decrypting message with " + javaKey.getAlgorithm() + " private key using " + transformation + ":", e);
            return null;
        }
    }
}

