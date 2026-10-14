/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm;

import com.google.common.base.Strings;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.crypto.KeySupport;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.AlgorithmRegistry;
import org.opensaml.xmlsec.algorithm.KeyLengthSpecifiedAlgorithm;
import org.opensaml.xmlsec.algorithm.KeySpecifiedAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AlgorithmSupport {
    private static final Logger LOG = LoggerFactory.getLogger(AlgorithmSupport.class);

    private AlgorithmSupport() {
    }

    @Nullable
    public static AlgorithmRegistry getGlobalAlgorithmRegistry() {
        return ConfigurationService.get(AlgorithmRegistry.class);
    }

    public static boolean isKeyEncryptionAlgorithm(@Nullable AlgorithmDescriptor algorithm) {
        if (algorithm == null) {
            return false;
        }
        switch (algorithm.getType()) {
            case KeyTransport: 
            case SymmetricKeyWrap: {
                return true;
            }
        }
        return false;
    }

    public static boolean isDataEncryptionAlgorithm(@Nullable AlgorithmDescriptor algorithm) {
        if (algorithm == null) {
            return false;
        }
        switch (algorithm.getType()) {
            case BlockEncryption: {
                return true;
            }
        }
        return false;
    }

    public static boolean credentialSupportsAlgorithmForSigning(@Nullable Credential credential, @Nullable AlgorithmDescriptor algorithm) {
        if (credential == null || algorithm == null) {
            return false;
        }
        Key key = CredentialSupport.extractSigningKey(credential);
        if (key == null) {
            return false;
        }
        switch (algorithm.getType()) {
            case Signature: {
                if (key instanceof PrivateKey) break;
                return false;
            }
            case Mac: {
                if (key instanceof SecretKey) break;
                return false;
            }
            default: {
                return false;
            }
        }
        return AlgorithmSupport.checkKeyAlgorithmAndLength(key, algorithm);
    }

    public static boolean credentialSupportsAlgorithmForEncryption(@Nullable Credential credential, @Nullable AlgorithmDescriptor algorithm) {
        if (credential == null || algorithm == null) {
            return false;
        }
        Key key = CredentialSupport.extractEncryptionKey(credential);
        if (key == null) {
            return false;
        }
        switch (algorithm.getType()) {
            case KeyTransport: {
                if (key instanceof PublicKey) break;
                return false;
            }
            case SymmetricKeyWrap: 
            case BlockEncryption: {
                if (key instanceof SecretKey) break;
                return false;
            }
            default: {
                return false;
            }
        }
        return AlgorithmSupport.checkKeyAlgorithmAndLength(key, algorithm);
    }

    public static boolean checkKeyAlgorithmAndLength(@Nonnull Key key, @Nonnull AlgorithmDescriptor algorithm) {
        Integer specifiedKeyLength;
        String specifiedKey;
        if (algorithm instanceof KeySpecifiedAlgorithm && !(specifiedKey = ((KeySpecifiedAlgorithm)algorithm).getKey()).equals(key.getAlgorithm())) {
            return false;
        }
        return !(algorithm instanceof KeyLengthSpecifiedAlgorithm) || (specifiedKeyLength = ((KeyLengthSpecifiedAlgorithm)algorithm).getKeyLength()).equals(KeySupport.getKeyLength(key));
    }

    @Nullable
    public static String getAlgorithmID(@Nonnull String algorithmURI) {
        AlgorithmDescriptor descriptor;
        AlgorithmRegistry registry = AlgorithmSupport.getGlobalAlgorithmRegistry();
        if (registry != null && (descriptor = registry.get(algorithmURI)) != null) {
            return descriptor.getJCAAlgorithmID();
        }
        return null;
    }

    public static boolean isRSAOAEP(@Nonnull String keyTransportAlgorithm) {
        return "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(keyTransportAlgorithm) || "http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(keyTransportAlgorithm);
    }

    public static boolean isHMAC(@Nonnull String signatureAlgorithm) {
        AlgorithmDescriptor descriptor;
        AlgorithmRegistry registry = AlgorithmSupport.getGlobalAlgorithmRegistry();
        if (registry != null && (descriptor = registry.get(signatureAlgorithm)) != null) {
            return descriptor.getType().equals((Object)AlgorithmDescriptor.AlgorithmType.Mac);
        }
        return false;
    }

    @Nullable
    public static String getKeyAlgorithm(@Nonnull String algorithmURI) {
        AlgorithmDescriptor descriptor;
        AlgorithmRegistry registry = AlgorithmSupport.getGlobalAlgorithmRegistry();
        if (registry != null && (descriptor = registry.get(algorithmURI)) != null && descriptor instanceof KeySpecifiedAlgorithm) {
            return ((KeySpecifiedAlgorithm)descriptor).getKey();
        }
        return null;
    }

    @Nullable
    public static Integer getKeyLength(@Nonnull String algorithmURI) {
        AlgorithmDescriptor descriptor;
        Logger log = AlgorithmSupport.getLogger();
        AlgorithmRegistry registry = AlgorithmSupport.getGlobalAlgorithmRegistry();
        if (registry != null && (descriptor = registry.get(algorithmURI)) != null && descriptor instanceof KeyLengthSpecifiedAlgorithm) {
            return ((KeyLengthSpecifiedAlgorithm)descriptor).getKeyLength();
        }
        log.info("Mapping from algorithm URI {} to key length not available", (Object)algorithmURI);
        return null;
    }

    @Nonnull
    public static SecretKey generateSymmetricKey(@Nonnull String algoURI) throws NoSuchAlgorithmException, KeyException {
        Logger log = AlgorithmSupport.getLogger();
        String jceAlgorithmName = AlgorithmSupport.getKeyAlgorithm(algoURI);
        if (Strings.isNullOrEmpty(jceAlgorithmName)) {
            log.error("Mapping from algorithm URI '" + algoURI + "' to key algorithm not available, key generation failed");
            throw new NoSuchAlgorithmException("Algorithm URI'" + algoURI + "' is invalid for key generation");
        }
        Integer keyLength = null;
        switch (algoURI) {
            case "http://www.w3.org/2001/04/xmlenc#tripledes-cbc": 
            case "http://www.w3.org/2001/04/xmlenc#kw-tripledes": {
                keyLength = 168;
                break;
            }
            default: {
                keyLength = AlgorithmSupport.getKeyLength(algoURI);
            }
        }
        if (keyLength == null) {
            log.error("Key length could not be determined from algorithm URI, can't generate key");
            throw new KeyException("Key length not determinable from algorithm URI, could not generate new key");
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(jceAlgorithmName);
        keyGenerator.init(keyLength);
        return keyGenerator.generateKey();
    }

    @Nonnull
    public static KeyPair generateKeyPair(@Nonnull String algoURI, int keyLength) throws NoSuchAlgorithmException, NoSuchProviderException {
        String jceAlgorithmName = AlgorithmSupport.getKeyAlgorithm(algoURI);
        return KeySupport.generateKeyPair(jceAlgorithmName, keyLength, null);
    }

    @Nonnull
    public static Credential generateSymmetricKeyAndCredential(@Nonnull String algorithmURI) throws NoSuchAlgorithmException, KeyException {
        SecretKey key = AlgorithmSupport.generateSymmetricKey(algorithmURI);
        return new BasicCredential(key);
    }

    @Nonnull
    public static Credential generateKeyPairAndCredential(@Nonnull String algorithmURI, int keyLength, boolean includePrivate) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPair keyPair = AlgorithmSupport.generateKeyPair(algorithmURI, keyLength);
        BasicCredential credential = new BasicCredential(keyPair.getPublic());
        if (includePrivate) {
            credential.setPrivateKey(keyPair.getPrivate());
        }
        return credential;
    }

    public static boolean validateAlgorithmURI(@Nonnull String algorithmURI, @Nullable Collection<String> whitelistedAlgorithmURIs, @Nullable Collection<String> blacklistedAlgorithmURIs) {
        if (blacklistedAlgorithmURIs != null) {
            LOG.debug("Saw non-null algorithm blacklist: {}", (Object)blacklistedAlgorithmURIs);
            if (blacklistedAlgorithmURIs.contains(algorithmURI)) {
                LOG.warn("Algorithm failed blacklist validation: {}", (Object)algorithmURI);
                return false;
            }
            LOG.debug("Algorithm passed blacklist validation: {}", (Object)algorithmURI);
        } else {
            LOG.debug("Saw null algorithm blacklist, nothing to evaluate");
        }
        if (whitelistedAlgorithmURIs != null) {
            LOG.debug("Saw non-null algorithm whitelist: {}", (Object)whitelistedAlgorithmURIs);
            if (!whitelistedAlgorithmURIs.isEmpty()) {
                if (!whitelistedAlgorithmURIs.contains(algorithmURI)) {
                    LOG.warn("Algorithm failed whitelist validation: {}", (Object)algorithmURI);
                    return false;
                }
                LOG.debug("Algorithm passed whitelist validation: {}", (Object)algorithmURI);
            } else {
                LOG.debug("Non-null algorithm whitelist was empty, skipping evaluation");
            }
        } else {
            LOG.debug("Saw null algorithm whitelist, nothing to evaluate");
        }
        return true;
    }

    @Nonnull
    private static Logger getLogger() {
        return LoggerFactory.getLogger(AlgorithmSupport.class);
    }
}

