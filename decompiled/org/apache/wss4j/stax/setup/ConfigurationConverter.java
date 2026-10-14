/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.setup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.JasyptPasswordEncryptor;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.Loader;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.validate.Validator;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigurationConverter {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationConverter.class);

    private ConfigurationConverter() {
    }

    public static WSSSecurityProperties convert(Map<String, Object> config) {
        WSSSecurityProperties properties = new WSSSecurityProperties();
        if (config == null) {
            return properties;
        }
        ConfigurationConverter.parseActions(config, properties);
        ConfigurationConverter.parseUserProperties(config, properties);
        ConfigurationConverter.parseCallback(config, properties);
        ConfigurationConverter.parseCrypto(config, properties);
        ConfigurationConverter.parseBooleanProperties(config, properties);
        ConfigurationConverter.parseNonBooleanProperties(config, properties);
        return properties;
    }

    public static void parseActions(Map<String, Object> config, WSSSecurityProperties properties) {
        String action = ConfigurationConverter.getString("action", config);
        String actionToParse = action;
        if (actionToParse == null) {
            return;
        }
        if ("".equals(actionToParse = actionToParse.trim())) {
            return;
        }
        String[] single = actionToParse.split("\\s");
        ArrayList<XMLSecurityConstants.Action> actions = new ArrayList<XMLSecurityConstants.Action>();
        for (int i = 0; i < single.length; ++i) {
            if (single[i].equals("UsernameToken")) {
                actions.add(WSSConstants.USERNAMETOKEN);
                continue;
            }
            if (single[i].equals("Signature")) {
                actions.add(WSSConstants.SIGNATURE);
                continue;
            }
            if (single[i].equals("Encrypt")) {
                actions.add(WSSConstants.ENCRYPT);
                continue;
            }
            if (single[i].equals("SAMLTokenUnsigned")) {
                actions.add(WSSConstants.SAML_TOKEN_UNSIGNED);
                continue;
            }
            if (single[i].equals("SAMLTokenSigned")) {
                actions.add(WSSConstants.SAML_TOKEN_SIGNED);
                continue;
            }
            if (single[i].equals("Timestamp")) {
                actions.add(WSSConstants.TIMESTAMP);
                continue;
            }
            if (single[i].equals("UsernameTokenSignature")) {
                actions.add(WSSConstants.USERNAMETOKEN_SIGNED);
                continue;
            }
            if (single[i].equals("SignatureDerived")) {
                actions.add(WSSConstants.SIGNATURE_WITH_DERIVED_KEY);
                continue;
            }
            if (single[i].equals("EncryptDerived")) {
                actions.add(WSSConstants.ENCRYPT_WITH_DERIVED_KEY);
                continue;
            }
            if (single[i].equals("SignatureWithKerberosToken")) {
                actions.add(WSSConstants.SIGNATURE_WITH_KERBEROS_TOKEN);
                continue;
            }
            if (single[i].equals("EncryptWithKerberosToken")) {
                actions.add(WSSConstants.ENCRYPT_WITH_KERBEROS_TOKEN);
                continue;
            }
            if (single[i].equals("KerberosToken")) {
                actions.add(WSSConstants.KERBEROS_TOKEN);
                continue;
            }
            if (!single[i].equals("CustomToken")) continue;
            actions.add(WSSConstants.CUSTOM_TOKEN);
        }
        boolean sigConf = ConfigurationConverter.decodeBooleanConfigValue("enableSignatureConfirmation", false, config);
        if (sigConf) {
            actions.add(WSSConstants.SIGNATURE_CONFIRMATION);
        }
        properties.setActions(actions);
    }

    public static void parseUserProperties(Map<String, Object> config, WSSSecurityProperties properties) {
        String sigUser;
        String user = ConfigurationConverter.getString("user", config);
        properties.setTokenUser(user);
        String actor = ConfigurationConverter.getString("actor", config);
        properties.setActor(actor);
        String encUser = ConfigurationConverter.getString("encryptionUser", config);
        if (encUser == null) {
            encUser = user;
        }
        properties.setEncryptionUser(encUser);
        if ("useReqSigCert".equals(encUser)) {
            properties.setUseReqSigCertForEncryption(true);
        }
        if ((sigUser = ConfigurationConverter.getString("signatureUser", config)) == null) {
            sigUser = user;
        }
        properties.setSignatureUser(sigUser);
    }

    public static void parseCrypto(Map<String, Object> config, WSSSecurityProperties properties) {
        String encPropFile;
        String encPropFile2;
        String sigPropFile;
        String sigPropFile2;
        CallbackHandler callbackHandler;
        Object passwordEncryptorObj = config.get("passwordEncryptorInstance");
        PasswordEncryptor passwordEncryptor = null;
        if (passwordEncryptorObj instanceof PasswordEncryptor) {
            passwordEncryptor = (PasswordEncryptor)passwordEncryptorObj;
        }
        if (passwordEncryptor == null && (callbackHandler = properties.getCallbackHandler()) != null) {
            passwordEncryptor = new JasyptPasswordEncryptor(callbackHandler);
        }
        String sigPropRef = ConfigurationConverter.getString("signaturePropRefId", config);
        boolean foundSigRef = false;
        if (sigPropRef != null) {
            Object sigRef = config.get(sigPropRef);
            if (sigRef instanceof Crypto) {
                foundSigRef = true;
                properties.setSignatureCrypto((Crypto)sigRef);
            } else if (sigRef instanceof Properties) {
                foundSigRef = true;
                properties.setSignatureCryptoProperties((Properties)sigRef, passwordEncryptor);
            }
            if (foundSigRef && properties.getSignatureUser() == null) {
                properties.setSignatureUser(ConfigurationConverter.getDefaultX509Identifier(properties, true));
            }
        }
        if (!foundSigRef && (sigPropFile2 = ConfigurationConverter.getString("signaturePropFile", config)) != null) {
            try {
                Properties sigProperties = CryptoFactory.getProperties(sigPropFile2, ConfigurationConverter.getClassLoader());
                properties.setSignatureCryptoProperties(sigProperties, passwordEncryptor);
                if (properties.getSignatureUser() == null) {
                    properties.setSignatureUser(ConfigurationConverter.getDefaultX509Identifier(properties, true));
                }
            }
            catch (WSSecurityException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        String sigVerPropRef = ConfigurationConverter.getString("signatureVerificationPropRefId", config);
        boolean foundSigVerRef = false;
        if (sigVerPropRef != null) {
            Object sigVerRef = config.get(sigVerPropRef);
            if (sigVerRef instanceof Crypto) {
                foundSigVerRef = true;
                properties.setSignatureVerificationCrypto((Crypto)sigVerRef);
            } else if (sigVerRef instanceof Properties) {
                foundSigVerRef = true;
                properties.setSignatureVerificationCryptoProperties((Properties)sigVerRef, passwordEncryptor);
            }
        }
        if (!foundSigVerRef && (sigPropFile = ConfigurationConverter.getString("signatureVerificationPropFile", config)) != null) {
            try {
                Properties sigProperties = CryptoFactory.getProperties(sigPropFile, ConfigurationConverter.getClassLoader());
                properties.setSignatureVerificationCryptoProperties(sigProperties, passwordEncryptor);
            }
            catch (WSSecurityException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        String encPropRef = ConfigurationConverter.getString("encryptionPropRefId", config);
        boolean foundEncRef = false;
        if (encPropRef != null) {
            Object encRef = config.get(encPropRef);
            if (encRef instanceof Crypto) {
                foundEncRef = true;
                properties.setEncryptionCrypto((Crypto)encRef);
            } else if (encRef instanceof Properties) {
                foundEncRef = true;
                properties.setEncryptionCryptoProperties((Properties)encRef, passwordEncryptor);
            }
        }
        if (!foundEncRef && (encPropFile2 = ConfigurationConverter.getString("encryptionPropFile", config)) != null) {
            try {
                Properties encProperties = CryptoFactory.getProperties(encPropFile2, ConfigurationConverter.getClassLoader());
                properties.setEncryptionCryptoProperties(encProperties, passwordEncryptor);
            }
            catch (WSSecurityException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        String decPropRef = ConfigurationConverter.getString("decryptionPropRefId", config);
        boolean foundDecRef = false;
        if (decPropRef != null) {
            Object decRef = config.get(decPropRef);
            if (decRef instanceof Crypto) {
                foundDecRef = true;
                properties.setDecryptionCrypto((Crypto)decRef);
            } else if (decRef instanceof Properties) {
                foundDecRef = true;
                properties.setDecryptionCryptoProperties((Properties)decRef, passwordEncryptor);
            }
        }
        if (!foundDecRef && (encPropFile = ConfigurationConverter.getString("decryptionPropFile", config)) != null) {
            try {
                Properties encProperties = CryptoFactory.getProperties(encPropFile, ConfigurationConverter.getClassLoader());
                properties.setDecryptionCryptoProperties(encProperties, passwordEncryptor);
            }
            catch (WSSecurityException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public static String getDefaultX509Identifier(WSSSecurityProperties properties, boolean signature) {
        try {
            Crypto crypto = null;
            crypto = signature ? properties.getSignatureCrypto() : properties.getEncryptionCrypto();
            if (crypto != null) {
                return crypto.getDefaultX509Identifier();
            }
        }
        catch (WSSecurityException e) {
            LOG.debug(e.getMessage(), e);
        }
        return null;
    }

    public static void parseCallback(Map<String, Object> config, WSSSecurityProperties properties) {
        Object pwPropRef = config.get("passwordCallbackRef");
        if (pwPropRef instanceof CallbackHandler) {
            properties.setCallbackHandler((CallbackHandler)pwPropRef);
        } else {
            String pwCallback = ConfigurationConverter.getString("passwordCallbackClass", config);
            if (pwCallback != null) {
                try {
                    CallbackHandler pwCallbackHandler = ConfigurationConverter.loadCallbackHandler(pwCallback);
                    properties.setCallbackHandler(pwCallbackHandler);
                }
                catch (WSSecurityException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        Object samlPropRef = config.get("samlCallbackRef");
        if (samlPropRef instanceof CallbackHandler) {
            properties.setSamlCallbackHandler((CallbackHandler)samlPropRef);
        } else {
            String samlCallback = ConfigurationConverter.getString("samlCallbackClass", config);
            if (samlCallback != null) {
                try {
                    CallbackHandler samlCallbackHandler = ConfigurationConverter.loadCallbackHandler(samlCallback);
                    properties.setSamlCallbackHandler(samlCallbackHandler);
                }
                catch (WSSecurityException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    public static CallbackHandler loadCallbackHandler(String callbackHandlerClass) throws WSSecurityException {
        Class<CallbackHandler> cbClass = null;
        CallbackHandler cbHandler = null;
        try {
            cbClass = Loader.loadClass(ConfigurationConverter.getClassLoader(), callbackHandlerClass, CallbackHandler.class);
        }
        catch (ClassNotFoundException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"WSHandler: cannot load callback handler class: " + callbackHandlerClass});
        }
        try {
            cbHandler = cbClass.newInstance();
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"WSHandler: cannot create instance of callback handler: " + callbackHandlerClass});
        }
        return cbHandler;
    }

    private static ClassLoader getClassLoader() {
        try {
            return Loader.getTCL();
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static void parseBooleanProperties(Map<String, Object> config, WSSSecurityProperties properties) {
        boolean sigConf = ConfigurationConverter.decodeBooleanConfigValue("enableSignatureConfirmation", false, config);
        properties.setEnableSignatureConfirmationVerification(sigConf);
        boolean mustUnderstand = ConfigurationConverter.decodeBooleanConfigValue("mustUnderstand", true, config);
        properties.setMustUnderstand(mustUnderstand);
        boolean bspCompliant = ConfigurationConverter.decodeBooleanConfigValue("isBSPCompliant", true, config);
        properties.setDisableBSPEnforcement(!bspCompliant);
        boolean inclPrefixes = ConfigurationConverter.decodeBooleanConfigValue("addInclusivePrefixes", true, config);
        properties.setAddExcC14NInclusivePrefixes(inclPrefixes);
        boolean nonce = ConfigurationConverter.decodeBooleanConfigValue("addUsernameTokenNonce", false, config);
        properties.setAddUsernameTokenNonce(nonce);
        boolean created = ConfigurationConverter.decodeBooleanConfigValue("addUsernameTokenCreated", false, config);
        properties.setAddUsernameTokenCreated(created);
        boolean customPasswordTypes = ConfigurationConverter.decodeBooleanConfigValue("handleCustomPasswordTypes", false, config);
        properties.setHandleCustomPasswordTypes(customPasswordTypes);
        boolean allowNoPassword = ConfigurationConverter.decodeBooleanConfigValue("allowUsernameTokenNoPassword", false, config);
        properties.setAllowUsernameTokenNoPassword(allowNoPassword);
        boolean enableRevocation = ConfigurationConverter.decodeBooleanConfigValue("enableRevocation", false, config);
        properties.setEnableRevocation(enableRevocation);
        boolean singleCert = ConfigurationConverter.decodeBooleanConfigValue("useSingleCertificate", true, config);
        properties.setUseSingleCert(singleCert);
        boolean derivedKeyMAC = ConfigurationConverter.decodeBooleanConfigValue("useDerivedKeyForMAC", true, config);
        properties.setUseDerivedKeyForMAC(derivedKeyMAC);
        boolean timestampStrict = ConfigurationConverter.decodeBooleanConfigValue("timestampStrict", true, config);
        properties.setStrictTimestampCheck(timestampStrict);
        boolean allowRSA15 = ConfigurationConverter.decodeBooleanConfigValue("allowRSA15KeyTransportAlgorithm", false, config);
        properties.setAllowRSA15KeyTransportAlgorithm(allowRSA15);
        boolean validateSamlSubjectConf = ConfigurationConverter.decodeBooleanConfigValue("validateSamlSubjectConfirmation", true, config);
        properties.setValidateSamlSubjectConfirmation(validateSamlSubjectConf);
        boolean includeSignatureToken = ConfigurationConverter.decodeBooleanConfigValue("includeSignatureToken", false, config);
        properties.setIncludeSignatureToken(includeSignatureToken);
        boolean includeEncryptionToken = ConfigurationConverter.decodeBooleanConfigValue("includeEncryptionToken", false, config);
        properties.setIncludeEncryptionToken(includeEncryptionToken);
        boolean encryptSymmetricEncryptionKey = ConfigurationConverter.decodeBooleanConfigValue("encryptSymmetricEncryptionKey", true, config);
        properties.setEncryptSymmetricEncryptionKey(encryptSymmetricEncryptionKey);
        boolean use200512Namespace = ConfigurationConverter.decodeBooleanConfigValue("use200512Namespace", true, config);
        properties.setUse200512Namespace(use200512Namespace);
        boolean requireTimestampExpires = ConfigurationConverter.decodeBooleanConfigValue("requireTimestampExpires", false, config);
        properties.setRequireTimestampExpires(requireTimestampExpires);
    }

    public static void parseNonBooleanProperties(Map<String, Object> config, WSSSecurityProperties properties) {
        String derivedKeyIdentifier;
        SecurityTokenConstants.KeyIdentifier convertedDerivedKeyIdentifier;
        String derivedTokenReference;
        WSSConstants.DerivedKeyTokenReference convertedDerivedTokenReference;
        String derivedEncryptionKeyLength;
        String derivedSignatureKeyLength;
        ReplayCache samlOneTimeUseCache;
        ReplayCache replayCache;
        ReplayCache nonceCache;
        String issuerCertConstraintsString;
        String encKeyIdentifier;
        SecurityTokenConstants.KeyIdentifier convEncKeyIdentifier;
        String pwType = ConfigurationConverter.getString("passwordType", config);
        if ("PasswordDigest".equals(pwType)) {
            properties.setUsernameTokenPasswordType(WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST);
        } else if ("PasswordText".equals(pwType)) {
            properties.setUsernameTokenPasswordType(WSSConstants.UsernameTokenPasswordType.PASSWORD_TEXT);
        } else if ("PasswordNone".equals(pwType)) {
            properties.setUsernameTokenPasswordType(WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE);
        }
        String signatureKeyIdentifier = ConfigurationConverter.getString("signatureKeyIdentifier", config);
        SecurityTokenConstants.KeyIdentifier convSigKeyIdentifier = ConfigurationConverter.convertKeyIdentifier(signatureKeyIdentifier);
        if (convSigKeyIdentifier != null) {
            properties.setSignatureKeyIdentifier(convSigKeyIdentifier);
        }
        String sigAlgo = ConfigurationConverter.getString("signatureAlgorithm", config);
        properties.setSignatureAlgorithm(sigAlgo);
        String sigDigestAlgo = ConfigurationConverter.getString("signatureDigestAlgorithm", config);
        properties.setSignatureDigestAlgorithm(sigDigestAlgo);
        String sigC14nAlgo = ConfigurationConverter.getString("signatureC14nAlgorithm", config);
        properties.setSignatureCanonicalizationAlgorithm(sigC14nAlgo);
        Object sigParts = config.get("signatureParts");
        ConfigurationConverter.configureParts(sigParts, properties, sigDigestAlgo, true, true);
        sigParts = config.get("optionalSignatureParts");
        ConfigurationConverter.configureParts(sigParts, properties, sigDigestAlgo, false, true);
        String iterations = ConfigurationConverter.getString("derivedKeyIterations", config);
        if (iterations != null) {
            int iIterations = Integer.parseInt(iterations);
            properties.setDerivedKeyIterations(iIterations);
        }
        if ((convEncKeyIdentifier = ConfigurationConverter.convertKeyIdentifier(encKeyIdentifier = ConfigurationConverter.getString("encryptionKeyIdentifier", config))) != null) {
            properties.setEncryptionKeyIdentifier(convEncKeyIdentifier);
        }
        Object encParts = config.get("encryptionParts");
        ConfigurationConverter.configureParts(encParts, properties, null, true, false);
        encParts = config.get("optionalEncryptionParts");
        ConfigurationConverter.configureParts(encParts, properties, null, false, false);
        String encSymcAlgo = ConfigurationConverter.getString("encryptionSymAlgorithm", config);
        properties.setEncryptionSymAlgorithm(encSymcAlgo);
        String encKeyTransport = ConfigurationConverter.getString("encryptionKeyTransportAlgorithm", config);
        properties.setEncryptionKeyTransportAlgorithm(encKeyTransport);
        String encDigestAlgo = ConfigurationConverter.getString("encryptionDigestAlgorithm", config);
        properties.setEncryptionKeyTransportDigestAlgorithm(encDigestAlgo);
        String encMGFAlgo = ConfigurationConverter.getString("encryptionMGFAlgorithm", config);
        properties.setEncryptionKeyTransportMGFAlgorithm(encMGFAlgo);
        String certConstraints = ConfigurationConverter.getString("sigSubjectCertConstraints", config);
        if (certConstraints != null) {
            String certConstraintsSeparator = ConfigurationConverter.getString("sigCertConstraintsSeparator", config);
            if (certConstraintsSeparator == null || certConstraintsSeparator.isEmpty()) {
                certConstraintsSeparator = ",";
            }
            Collection<Pattern> subjectCertConstraints = ConfigurationConverter.getCertConstraints(certConstraints, certConstraintsSeparator);
            properties.setSubjectCertConstraints(subjectCertConstraints);
        }
        if ((issuerCertConstraintsString = ConfigurationConverter.getString("sigIssuerCertConstraints", config)) != null) {
            String certConstraintsSeparator = ConfigurationConverter.getString("sigCertConstraintsSeparator", config);
            if (certConstraintsSeparator == null || certConstraintsSeparator.isEmpty()) {
                certConstraintsSeparator = ",";
            }
            Collection<Pattern> issuerCertConstraints = ConfigurationConverter.getCertConstraints(certConstraints, certConstraintsSeparator);
            properties.setIssuerDNConstraints(issuerCertConstraints);
        }
        properties.setUtTTL(ConfigurationConverter.decodeTimeToLive(config, false));
        properties.setUtFutureTTL(ConfigurationConverter.decodeFutureTimeToLive(config, false));
        properties.setTimestampTTL(ConfigurationConverter.decodeTimeToLive(config, true));
        properties.setTimeStampFutureTTL(ConfigurationConverter.decodeFutureTimeToLive(config, true));
        Map validatorMap = (Map)config.get("validatorMap");
        if (validatorMap != null) {
            for (Map.Entry entry : validatorMap.entrySet()) {
                properties.addValidator((QName)entry.getKey(), (Validator)entry.getValue());
            }
        }
        if ((nonceCache = (ReplayCache)config.get("nonceCacheInstance")) != null) {
            properties.setNonceReplayCache(nonceCache);
        }
        if ((replayCache = (ReplayCache)config.get("timestampCacheInstance")) != null) {
            properties.setTimestampReplayCache(replayCache);
        }
        if ((samlOneTimeUseCache = (ReplayCache)config.get("samlOneTimeUseCacheInstance")) != null) {
            properties.setSamlOneTimeUseReplayCache(samlOneTimeUseCache);
        }
        if ((derivedSignatureKeyLength = ConfigurationConverter.getString("derivedSignatureKeyLength", config)) != null) {
            int sigLength = Integer.parseInt(derivedSignatureKeyLength);
            properties.setDerivedSignatureKeyLength(sigLength);
        }
        if ((derivedEncryptionKeyLength = ConfigurationConverter.getString("derivedEncryptionKeyLength", config)) != null) {
            int encLength = Integer.parseInt(derivedEncryptionKeyLength);
            properties.setDerivedEncryptionKeyLength(encLength);
        }
        if ((convertedDerivedTokenReference = ConfigurationConverter.convertDerivedReference(derivedTokenReference = ConfigurationConverter.getString("derivedTokenReference", config))) != null) {
            properties.setDerivedKeyTokenReference(convertedDerivedTokenReference);
        }
        if ((convertedDerivedKeyIdentifier = ConfigurationConverter.convertKeyIdentifier(derivedKeyIdentifier = ConfigurationConverter.getString("derivedTokenKeyIdentifier", config))) != null) {
            properties.setDerivedKeyKeyIdentifier(convertedDerivedKeyIdentifier);
        }
    }

    private static Collection<Pattern> getCertConstraints(String certConstraints, String certConstraintsSeparator) {
        String[] certConstraintsList = certConstraints.split(certConstraintsSeparator);
        if (certConstraintsList != null && certConstraintsList.length > 0) {
            ArrayList<Pattern> certConstraintsCollection = new ArrayList<Pattern>(certConstraintsList.length);
            for (String certConstraint : certConstraintsList) {
                try {
                    certConstraintsCollection.add(Pattern.compile(certConstraint.trim()));
                }
                catch (PatternSyntaxException ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            }
            return certConstraintsCollection;
        }
        return Collections.emptyList();
    }

    private static void configureParts(Object secureParts, WSSSecurityProperties properties, String digestAlgo, boolean required, boolean signature) {
        block4: {
            block5: {
                if (secureParts == null) break block4;
                if (!(secureParts instanceof String)) break block5;
                ArrayList<SecurePart> parts = new ArrayList<SecurePart>();
                ConfigurationConverter.splitEncParts((String)secureParts, parts, "http://schemas.xmlsoap.org/soap/envelope/");
                for (SecurePart part : parts) {
                    part.setRequired(required);
                    if (signature) {
                        part.setDigestMethod(digestAlgo);
                        properties.addSignaturePart(part);
                        continue;
                    }
                    properties.addEncryptionPart(part);
                }
                break block4;
            }
            if (!(secureParts instanceof List)) break block4;
            List sigPartsList = (List)secureParts;
            for (Object obj : sigPartsList) {
                if (!(obj instanceof SecurePart)) continue;
                SecurePart securePart = (SecurePart)obj;
                securePart.setRequired(required);
                if (signature) {
                    securePart.setDigestMethod(digestAlgo);
                    properties.addSignaturePart(securePart);
                    continue;
                }
                properties.addEncryptionPart(securePart);
            }
        }
    }

    public static WSSConstants.DerivedKeyTokenReference convertDerivedReference(String derivedTokenReference) {
        if ("EncryptedKey".equals(derivedTokenReference)) {
            return WSSConstants.DerivedKeyTokenReference.EncryptedKey;
        }
        if ("DirectReference".equals(derivedTokenReference)) {
            return WSSConstants.DerivedKeyTokenReference.DirectReference;
        }
        if ("SecurityContextToken".equals(derivedTokenReference)) {
            return WSSConstants.DerivedKeyTokenReference.SecurityContextToken;
        }
        return null;
    }

    public static SecurityTokenConstants.KeyIdentifier convertKeyIdentifier(String keyIdentifier) {
        if ("IssuerSerial".equals(keyIdentifier)) {
            return WSSecurityTokenConstants.KeyIdentifier_IssuerSerial;
        }
        if ("DirectReference".equals(keyIdentifier)) {
            return WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE;
        }
        if ("X509KeyIdentifier".equals(keyIdentifier)) {
            return WSSecurityTokenConstants.KeyIdentifier_X509KeyIdentifier;
        }
        if ("Thumbprint".equals(keyIdentifier)) {
            return WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER;
        }
        if ("SKIKeyIdentifier".equals(keyIdentifier)) {
            return WSSecurityTokenConstants.KeyIdentifier_SkiKeyIdentifier;
        }
        if ("EncryptedKeySHA1".equals(keyIdentifier)) {
            return WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER;
        }
        if ("EncryptedKey".equals(keyIdentifier)) {
            return WSSecurityTokenConstants.KeyIdentifier_EncryptedKey;
        }
        if ("KeyValue".equals(keyIdentifier)) {
            return WSSecurityTokenConstants.KeyIdentifier_KeyValue;
        }
        if ("KerberosSHA1".equals(keyIdentifier)) {
            return WSSecurityTokenConstants.KEYIDENTIFIER_KERBEROS_SHA1_IDENTIFIER;
        }
        return null;
    }

    private static int decodeTimeToLive(Map<String, Object> config, boolean timestamp) {
        String tag = "timeToLive";
        if (!timestamp) {
            tag = "utTimeToLive";
        }
        String ttl = ConfigurationConverter.getString(tag, config);
        int defaultTimeToLive = 300;
        if (ttl != null) {
            try {
                int ttlI = Integer.parseInt(ttl);
                if (ttlI < 0) {
                    return defaultTimeToLive;
                }
                return ttlI;
            }
            catch (NumberFormatException e) {
                return defaultTimeToLive;
            }
        }
        return defaultTimeToLive;
    }

    private static int decodeFutureTimeToLive(Map<String, Object> config, boolean timestamp) {
        String tag = "futureTimeToLive";
        if (!timestamp) {
            tag = "utFutureTimeToLive";
        }
        String ttl = ConfigurationConverter.getString(tag, config);
        int defaultFutureTimeToLive = 60;
        if (ttl != null) {
            try {
                int ttlI = Integer.parseInt(ttl);
                if (ttlI < 0) {
                    return defaultFutureTimeToLive;
                }
                return ttlI;
            }
            catch (NumberFormatException e) {
                return defaultFutureTimeToLive;
            }
        }
        return defaultFutureTimeToLive;
    }

    private static String getString(String tag, Map<String, Object> config) {
        Object value = config.get(tag);
        if (value instanceof String) {
            return (String)value;
        }
        return null;
    }

    private static boolean decodeBooleanConfigValue(String tag, boolean defaultToTrue, Map<String, Object> config) {
        String value = ConfigurationConverter.getString(tag, config);
        if ("0".equals(value) || "false".equals(value)) {
            return false;
        }
        if ("1".equals(value) || "true".equals(value)) {
            return true;
        }
        return defaultToTrue;
    }

    private static void splitEncParts(String tmpS, List<SecurePart> parts, String soapNS) {
        SecurePart encPart = null;
        String[] rawParts = tmpS.split(";");
        for (int i = 0; i < rawParts.length; ++i) {
            String mode;
            String[] partDef = rawParts[i].split("}");
            if (partDef.length == 1) {
                QName qname = new QName(soapNS, partDef[0].trim());
                encPart = new SecurePart(qname, SecurePart.Modifier.Content);
            } else if (partDef.length == 2) {
                mode = partDef[0].trim().substring(1);
                String element = partDef[1].trim();
                encPart = "Content".equals(mode) ? new SecurePart(element, SecurePart.Modifier.Content) : new SecurePart(element, SecurePart.Modifier.Element);
            } else if (partDef.length == 3) {
                mode = partDef[0].trim();
                mode = mode.length() <= 1 ? "Content" : mode.substring(1);
                String nmSpace = partDef[1].trim();
                if (nmSpace.length() <= 1) {
                    nmSpace = soapNS;
                } else if ("Null".equals(nmSpace = nmSpace.substring(1))) {
                    nmSpace = null;
                }
                String element = partDef[2].trim();
                QName qname = new QName(nmSpace, element);
                encPart = "Content".equals(mode) ? new SecurePart(qname, SecurePart.Modifier.Content) : new SecurePart(qname, SecurePart.Modifier.Element);
            }
            parts.add(encPart);
        }
    }
}

