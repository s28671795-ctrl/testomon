/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.ext;

import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertStore;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.Merlin;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConfigurationException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSCrypto;
import org.apache.wss4j.stax.validate.Validator;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class WSSSecurityProperties
extends XMLSecurityProperties {
    private boolean mustUnderstand = true;
    private String actor;
    private CallbackHandler callbackHandler;
    private CallbackHandler samlCallbackHandler;
    private final List<BSPRule> ignoredBSPRules = new LinkedList<BSPRule>();
    private boolean disableBSPEnforcement;
    private final Map<QName, Validator> validators = new HashMap<QName, Validator>();
    private Integer timestampTTL = 300;
    private Integer timeStampFutureTTL = 60;
    private boolean strictTimestampCheck = true;
    private Integer utTTL = 300;
    private Integer utFutureTTL = 60;
    private Integer derivedKeyIterations = 1000;
    private boolean addUsernameTokenNonce;
    private boolean addUsernameTokenCreated;
    private boolean encryptSymmetricEncrytionKey = true;
    private boolean use200512Namespace = true;
    private boolean handleCustomPasswordTypes = false;
    private boolean allowUsernameTokenNoPassword = false;
    private boolean allowRSA15KeyTransportAlgorithm = false;
    private boolean useDerivedKeyForMAC = true;
    private WSSConstants.UsernameTokenPasswordType usernameTokenPasswordType;
    private String tokenUser;
    private SecurityTokenConstants.KeyIdentifier derivedKeyKeyIdentifier;
    private WSSConstants.DerivedKeyTokenReference derivedKeyTokenReference;
    private int derivedSignatureKeyLength;
    private int derivedEncryptionKeyLength;
    private WSSCrypto signatureWSSCrypto;
    private String signatureUser;
    private boolean enableSignatureConfirmationVerification = false;
    private boolean includeSignatureToken;
    private boolean includeEncryptionToken;
    private WSSCrypto signatureVerificationWSSCrypto;
    private CertStore crlCertStore;
    private WSSCrypto decryptionWSSCrypto;
    private WSSCrypto encryptionWSSCrypto;
    private String encryptionUser;
    private boolean useReqSigCertForEncryption = false;
    private String encryptionCompressionAlgorithm;
    private boolean enableRevocation = false;
    private ReplayCache timestampReplayCache;
    private ReplayCache nonceReplayCache;
    private ReplayCache samlOneTimeUseReplayCache;
    private boolean validateSamlSubjectConfirmation = true;
    private Collection<Pattern> subjectDNPatterns = new ArrayList<Pattern>();
    private Collection<Pattern> issuerDNPatterns = new ArrayList<Pattern>();
    private List<String> audienceRestrictions = new ArrayList<String>();
    private boolean requireTimestampExpires;
    private CallbackHandler attachmentCallbackHandler;
    private Object msgContext;

    public WSSSecurityProperties() {
        this.setAddExcC14NInclusivePrefixes(true);
    }

    public WSSSecurityProperties(WSSSecurityProperties wssSecurityProperties) {
        super(wssSecurityProperties);
        this.mustUnderstand = wssSecurityProperties.mustUnderstand;
        this.actor = wssSecurityProperties.actor;
        this.callbackHandler = wssSecurityProperties.callbackHandler;
        this.samlCallbackHandler = wssSecurityProperties.samlCallbackHandler;
        this.ignoredBSPRules.addAll(wssSecurityProperties.ignoredBSPRules);
        this.disableBSPEnforcement = wssSecurityProperties.disableBSPEnforcement;
        this.validators.putAll(wssSecurityProperties.validators);
        this.timestampTTL = wssSecurityProperties.timestampTTL;
        this.timeStampFutureTTL = wssSecurityProperties.timeStampFutureTTL;
        this.utTTL = wssSecurityProperties.utTTL;
        this.utFutureTTL = wssSecurityProperties.utFutureTTL;
        this.strictTimestampCheck = wssSecurityProperties.strictTimestampCheck;
        this.handleCustomPasswordTypes = wssSecurityProperties.handleCustomPasswordTypes;
        this.usernameTokenPasswordType = wssSecurityProperties.usernameTokenPasswordType;
        this.allowUsernameTokenNoPassword = wssSecurityProperties.allowUsernameTokenNoPassword;
        this.tokenUser = wssSecurityProperties.tokenUser;
        this.use200512Namespace = wssSecurityProperties.use200512Namespace;
        this.derivedKeyKeyIdentifier = wssSecurityProperties.derivedKeyKeyIdentifier;
        this.derivedKeyTokenReference = wssSecurityProperties.derivedKeyTokenReference;
        this.derivedSignatureKeyLength = wssSecurityProperties.derivedSignatureKeyLength;
        this.derivedEncryptionKeyLength = wssSecurityProperties.derivedEncryptionKeyLength;
        this.signatureWSSCrypto = wssSecurityProperties.signatureWSSCrypto;
        this.signatureUser = wssSecurityProperties.signatureUser;
        this.enableSignatureConfirmationVerification = wssSecurityProperties.enableSignatureConfirmationVerification;
        this.includeSignatureToken = wssSecurityProperties.includeSignatureToken;
        this.includeEncryptionToken = wssSecurityProperties.includeEncryptionToken;
        this.signatureVerificationWSSCrypto = wssSecurityProperties.signatureVerificationWSSCrypto;
        this.crlCertStore = wssSecurityProperties.crlCertStore;
        this.decryptionWSSCrypto = wssSecurityProperties.decryptionWSSCrypto;
        this.encryptionWSSCrypto = wssSecurityProperties.encryptionWSSCrypto;
        this.encryptionUser = wssSecurityProperties.encryptionUser;
        this.useReqSigCertForEncryption = wssSecurityProperties.useReqSigCertForEncryption;
        this.encryptionCompressionAlgorithm = wssSecurityProperties.encryptionCompressionAlgorithm;
        this.enableRevocation = wssSecurityProperties.enableRevocation;
        this.timestampReplayCache = wssSecurityProperties.timestampReplayCache;
        this.nonceReplayCache = wssSecurityProperties.nonceReplayCache;
        this.samlOneTimeUseReplayCache = wssSecurityProperties.samlOneTimeUseReplayCache;
        this.allowRSA15KeyTransportAlgorithm = wssSecurityProperties.allowRSA15KeyTransportAlgorithm;
        this.derivedKeyIterations = wssSecurityProperties.derivedKeyIterations;
        this.useDerivedKeyForMAC = wssSecurityProperties.useDerivedKeyForMAC;
        this.addUsernameTokenNonce = wssSecurityProperties.addUsernameTokenNonce;
        this.addUsernameTokenCreated = wssSecurityProperties.addUsernameTokenCreated;
        this.validateSamlSubjectConfirmation = wssSecurityProperties.validateSamlSubjectConfirmation;
        this.encryptSymmetricEncrytionKey = wssSecurityProperties.encryptSymmetricEncrytionKey;
        this.subjectDNPatterns = wssSecurityProperties.subjectDNPatterns;
        this.issuerDNPatterns = wssSecurityProperties.issuerDNPatterns;
        this.attachmentCallbackHandler = wssSecurityProperties.attachmentCallbackHandler;
        this.msgContext = wssSecurityProperties.msgContext;
        this.audienceRestrictions = wssSecurityProperties.audienceRestrictions;
        this.requireTimestampExpires = wssSecurityProperties.requireTimestampExpires;
    }

    public CallbackHandler getCallbackHandler() {
        return this.callbackHandler;
    }

    public void setCallbackHandler(CallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    public Integer getTimestampTTL() {
        return this.timestampTTL;
    }

    public void setTimestampTTL(Integer timestampTTL) {
        this.timestampTTL = timestampTTL;
    }

    public boolean isStrictTimestampCheck() {
        return this.strictTimestampCheck;
    }

    public void setStrictTimestampCheck(boolean strictTimestampCheck) {
        this.strictTimestampCheck = strictTimestampCheck;
    }

    public void setHandleCustomPasswordTypes(boolean handleCustomTypes) {
        this.handleCustomPasswordTypes = handleCustomTypes;
    }

    public boolean getHandleCustomPasswordTypes() {
        return this.handleCustomPasswordTypes;
    }

    public String getTokenUser() {
        return this.tokenUser;
    }

    public void setTokenUser(String tokenUser) {
        this.tokenUser = tokenUser;
    }

    public WSSConstants.UsernameTokenPasswordType getUsernameTokenPasswordType() {
        return this.usernameTokenPasswordType;
    }

    public void setUsernameTokenPasswordType(WSSConstants.UsernameTokenPasswordType usernameTokenPasswordType) {
        this.usernameTokenPasswordType = usernameTokenPasswordType;
    }

    public boolean isEnableSignatureConfirmationVerification() {
        return this.enableSignatureConfirmationVerification;
    }

    public void setEnableSignatureConfirmationVerification(boolean enableSignatureConfirmationVerification) {
        this.enableSignatureConfirmationVerification = enableSignatureConfirmationVerification;
    }

    public boolean isUseReqSigCertForEncryption() {
        return this.useReqSigCertForEncryption;
    }

    public void setUseReqSigCertForEncryption(boolean useReqSigCertForEncryption) {
        this.useReqSigCertForEncryption = useReqSigCertForEncryption;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public SecurityTokenConstants.KeyIdentifier getDerivedKeyKeyIdentifier() {
        return this.derivedKeyKeyIdentifier;
    }

    public void setDerivedKeyKeyIdentifier(SecurityTokenConstants.KeyIdentifier derivedKeyKeyIdentifier) {
        this.derivedKeyKeyIdentifier = derivedKeyKeyIdentifier;
    }

    public WSSConstants.DerivedKeyTokenReference getDerivedKeyTokenReference() {
        return this.derivedKeyTokenReference;
    }

    public void setDerivedKeyTokenReference(WSSConstants.DerivedKeyTokenReference derivedKeyTokenReference) {
        this.derivedKeyTokenReference = derivedKeyTokenReference;
    }

    public void addIgnoreBSPRule(BSPRule bspRule) {
        this.ignoredBSPRules.add(bspRule);
    }

    public List<BSPRule> getIgnoredBSPRules() {
        return Collections.unmodifiableList(this.ignoredBSPRules);
    }

    public void addValidator(QName qName, Validator validator) {
        this.validators.put(qName, validator);
    }

    public <T extends Validator> T getValidator(QName qName) {
        return (T)this.validators.get(qName);
    }

    public void setSignatureUser(String signatureUser) {
        this.signatureUser = signatureUser;
    }

    public String getSignatureUser() {
        return this.signatureUser;
    }

    public KeyStore getSignatureKeyStore() {
        if (this.signatureWSSCrypto != null) {
            return this.signatureWSSCrypto.getKeyStore();
        }
        return null;
    }

    public void loadSignatureKeyStore(URL url2, char[] keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(url2.openStream(), keyStorePassword);
        if (this.signatureWSSCrypto == null) {
            this.signatureWSSCrypto = new WSSCrypto();
        }
        this.signatureWSSCrypto.setKeyStore(keyStore);
    }

    public Properties getSignatureCryptoProperties() {
        if (this.signatureWSSCrypto != null) {
            return this.signatureWSSCrypto.getCryptoProperties();
        }
        return null;
    }

    public void setSignatureCryptoProperties(Properties cryptoProperties) {
        this.setSignatureCryptoProperties(cryptoProperties, null);
    }

    public void setSignatureCryptoProperties(Properties cryptoProperties, PasswordEncryptor passwordEncryptor) {
        if (this.signatureWSSCrypto == null) {
            this.signatureWSSCrypto = new WSSCrypto();
        }
        this.signatureWSSCrypto.setCryptoProperties(cryptoProperties);
        this.signatureWSSCrypto.setPasswordEncryptor(passwordEncryptor);
    }

    public Class<? extends Merlin> getSignatureCryptoClass() {
        if (this.signatureWSSCrypto != null) {
            return this.signatureWSSCrypto.getCryptoClass();
        }
        return Merlin.class;
    }

    public void setSignatureCryptoClass(Class<? extends Merlin> signatureCryptoClass) {
        if (this.signatureWSSCrypto == null) {
            this.signatureWSSCrypto = new WSSCrypto();
        }
        this.signatureWSSCrypto.setCryptoClass(signatureCryptoClass);
    }

    public Crypto getSignatureCrypto() throws WSSConfigurationException {
        if (this.signatureWSSCrypto == null) {
            return null;
        }
        return this.signatureWSSCrypto.getCrypto();
    }

    public void setSignatureCrypto(Crypto sigCrypto) {
        if (this.signatureWSSCrypto == null) {
            this.signatureWSSCrypto = new WSSCrypto();
        }
        this.signatureWSSCrypto.setCrypto(sigCrypto);
    }

    public KeyStore getSignatureVerificationKeyStore() {
        if (this.signatureVerificationWSSCrypto != null) {
            return this.signatureVerificationWSSCrypto.getKeyStore();
        }
        return null;
    }

    public void loadSignatureVerificationKeystore(URL url2, char[] keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(url2.openStream(), keyStorePassword);
        if (this.signatureVerificationWSSCrypto == null) {
            this.signatureVerificationWSSCrypto = new WSSCrypto();
        }
        this.signatureVerificationWSSCrypto.setKeyStore(keyStore);
    }

    public void loadCRLCertStore(URL url2) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509CRL crl = (X509CRL)cf.generateCRL(url2.openStream());
        this.crlCertStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(Collections.singletonList(crl)));
    }

    public Properties getSignatureVerificationCryptoProperties() {
        if (this.signatureVerificationWSSCrypto != null) {
            return this.signatureVerificationWSSCrypto.getCryptoProperties();
        }
        return null;
    }

    public void setSignatureVerificationCryptoProperties(Properties cryptoProperties) {
        this.setSignatureVerificationCryptoProperties(cryptoProperties, null);
    }

    public void setSignatureVerificationCryptoProperties(Properties cryptoProperties, PasswordEncryptor passwordEncryptor) {
        if (this.signatureVerificationWSSCrypto == null) {
            this.signatureVerificationWSSCrypto = new WSSCrypto();
        }
        this.signatureVerificationWSSCrypto.setCryptoProperties(cryptoProperties);
        this.signatureVerificationWSSCrypto.setPasswordEncryptor(passwordEncryptor);
    }

    public Class<? extends Merlin> getSignatureVerificationCryptoClass() {
        if (this.signatureVerificationWSSCrypto != null) {
            return this.signatureVerificationWSSCrypto.getCryptoClass();
        }
        return Merlin.class;
    }

    public void setSignatureVerificationCryptoClass(Class<? extends Merlin> signatureVerificationCryptoClass) {
        if (this.signatureVerificationWSSCrypto == null) {
            this.signatureVerificationWSSCrypto = new WSSCrypto();
        }
        this.signatureVerificationWSSCrypto.setCryptoClass(signatureVerificationCryptoClass);
    }

    public Crypto getSignatureVerificationCrypto() throws WSSConfigurationException {
        if (this.signatureVerificationWSSCrypto == null) {
            return null;
        }
        this.signatureVerificationWSSCrypto.setCrlCertStore(this.crlCertStore);
        return this.signatureVerificationWSSCrypto.getCrypto();
    }

    public void setSignatureVerificationCrypto(Crypto sigVerCrypto) {
        if (this.signatureVerificationWSSCrypto == null) {
            this.signatureVerificationWSSCrypto = new WSSCrypto();
        }
        this.signatureVerificationWSSCrypto.setCrypto(sigVerCrypto);
    }

    public KeyStore getDecryptionKeyStore() {
        if (this.decryptionWSSCrypto != null) {
            return this.decryptionWSSCrypto.getKeyStore();
        }
        return null;
    }

    public void loadDecryptionKeystore(URL url2, char[] keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(url2.openStream(), keyStorePassword);
        if (this.decryptionWSSCrypto == null) {
            this.decryptionWSSCrypto = new WSSCrypto();
        }
        this.decryptionWSSCrypto.setKeyStore(keyStore);
    }

    public Properties getDecryptionCryptoProperties() {
        if (this.decryptionWSSCrypto != null) {
            return this.decryptionWSSCrypto.getCryptoProperties();
        }
        return null;
    }

    public void setDecryptionCryptoProperties(Properties cryptoProperties) {
        this.setDecryptionCryptoProperties(cryptoProperties, null);
    }

    public void setDecryptionCryptoProperties(Properties cryptoProperties, PasswordEncryptor passwordEncryptor) {
        if (this.decryptionWSSCrypto == null) {
            this.decryptionWSSCrypto = new WSSCrypto();
        }
        this.decryptionWSSCrypto.setCryptoProperties(cryptoProperties);
        this.decryptionWSSCrypto.setPasswordEncryptor(passwordEncryptor);
    }

    public Class<? extends Merlin> getDecryptionCryptoClass() {
        if (this.decryptionWSSCrypto != null) {
            return this.decryptionWSSCrypto.getCryptoClass();
        }
        return Merlin.class;
    }

    public void setDecryptionCryptoClass(Class<? extends Merlin> decryptionCryptoClass) {
        if (this.decryptionWSSCrypto == null) {
            this.decryptionWSSCrypto = new WSSCrypto();
        }
        this.decryptionWSSCrypto.setCryptoClass(decryptionCryptoClass);
    }

    public Crypto getDecryptionCrypto() throws WSSConfigurationException {
        if (this.decryptionWSSCrypto == null) {
            return null;
        }
        return this.decryptionWSSCrypto.getCrypto();
    }

    public void setDecryptionCrypto(Crypto decCrypto) {
        if (this.decryptionWSSCrypto == null) {
            this.decryptionWSSCrypto = new WSSCrypto();
        }
        this.decryptionWSSCrypto.setCrypto(decCrypto);
    }

    public KeyStore getEncryptionKeyStore() {
        if (this.encryptionWSSCrypto != null) {
            return this.encryptionWSSCrypto.getKeyStore();
        }
        return null;
    }

    public void loadEncryptionKeystore(URL url2, char[] keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(url2.openStream(), keyStorePassword);
        if (this.encryptionWSSCrypto == null) {
            this.encryptionWSSCrypto = new WSSCrypto();
        }
        this.encryptionWSSCrypto.setKeyStore(keyStore);
    }

    public Properties getEncryptionCryptoProperties() {
        if (this.encryptionWSSCrypto != null) {
            return this.encryptionWSSCrypto.getCryptoProperties();
        }
        return null;
    }

    public void setEncryptionCryptoProperties(Properties cryptoProperties) {
        this.setEncryptionCryptoProperties(cryptoProperties, null);
    }

    public void setEncryptionCryptoProperties(Properties cryptoProperties, PasswordEncryptor passwordEncryptor) {
        if (this.encryptionWSSCrypto == null) {
            this.encryptionWSSCrypto = new WSSCrypto();
        }
        this.encryptionWSSCrypto.setCryptoProperties(cryptoProperties);
        this.encryptionWSSCrypto.setPasswordEncryptor(passwordEncryptor);
    }

    public Class<? extends Merlin> getEncryptionCryptoClass() {
        if (this.encryptionWSSCrypto != null) {
            return this.encryptionWSSCrypto.getCryptoClass();
        }
        return Merlin.class;
    }

    public void setEncryptionCryptoClass(Class<? extends Merlin> encryptionCryptoClass) {
        if (this.encryptionWSSCrypto == null) {
            this.encryptionWSSCrypto = new WSSCrypto();
        }
        this.encryptionWSSCrypto.setCryptoClass(encryptionCryptoClass);
    }

    public Crypto getEncryptionCrypto() throws WSSConfigurationException {
        if (this.encryptionWSSCrypto == null) {
            return null;
        }
        this.encryptionWSSCrypto.setCrlCertStore(this.getCrlCertStore());
        return this.encryptionWSSCrypto.getCrypto();
    }

    public void setEncryptionCrypto(Crypto encCrypto) {
        if (this.encryptionWSSCrypto == null) {
            this.encryptionWSSCrypto = new WSSCrypto();
        }
        this.encryptionWSSCrypto.setCrypto(encCrypto);
    }

    public String getEncryptionUser() {
        return this.encryptionUser;
    }

    public void setEncryptionUser(String encryptionUser) {
        this.encryptionUser = encryptionUser;
    }

    public String getEncryptionCompressionAlgorithm() {
        return this.encryptionCompressionAlgorithm;
    }

    public void setEncryptionCompressionAlgorithm(String encryptionCompressionAlgorithm) {
        this.encryptionCompressionAlgorithm = encryptionCompressionAlgorithm;
    }

    public boolean isAllowUsernameTokenNoPassword() {
        return this.allowUsernameTokenNoPassword;
    }

    public void setAllowUsernameTokenNoPassword(boolean allowUsernameTokenNoPassword) {
        this.allowUsernameTokenNoPassword = allowUsernameTokenNoPassword;
    }

    public boolean isEnableRevocation() {
        return this.enableRevocation;
    }

    public void setEnableRevocation(boolean enableRevocation) {
        this.enableRevocation = enableRevocation;
    }

    public CertStore getCrlCertStore() {
        return this.crlCertStore;
    }

    public void setCrlCertStore(CertStore crlCertStore) {
        this.crlCertStore = crlCertStore;
    }

    public Integer getTimeStampFutureTTL() {
        return this.timeStampFutureTTL;
    }

    public void setTimeStampFutureTTL(Integer timeStampFutureTTL) {
        this.timeStampFutureTTL = timeStampFutureTTL;
    }

    public Integer getUtTTL() {
        return this.utTTL;
    }

    public void setUtTTL(Integer utTTL) {
        this.utTTL = utTTL;
    }

    public Integer getUtFutureTTL() {
        return this.utFutureTTL;
    }

    public void setUtFutureTTL(Integer utFutureTTL) {
        this.utFutureTTL = utFutureTTL;
    }

    public void setTimestampReplayCache(ReplayCache newCache) {
        this.timestampReplayCache = newCache;
    }

    public ReplayCache getTimestampReplayCache() throws WSSecurityException {
        return this.timestampReplayCache;
    }

    public void setNonceReplayCache(ReplayCache newCache) {
        this.nonceReplayCache = newCache;
    }

    public ReplayCache getNonceReplayCache() throws WSSecurityException {
        return this.nonceReplayCache;
    }

    public void setSamlOneTimeUseReplayCache(ReplayCache newCache) {
        this.samlOneTimeUseReplayCache = newCache;
    }

    public ReplayCache getSamlOneTimeUseReplayCache() throws WSSecurityException {
        return this.samlOneTimeUseReplayCache;
    }

    public boolean isDisableBSPEnforcement() {
        return this.disableBSPEnforcement;
    }

    public void setDisableBSPEnforcement(boolean disableBSPEnforcement) {
        this.disableBSPEnforcement = disableBSPEnforcement;
    }

    public boolean isAllowRSA15KeyTransportAlgorithm() {
        return this.allowRSA15KeyTransportAlgorithm;
    }

    public void setAllowRSA15KeyTransportAlgorithm(boolean allowRSA15KeyTransportAlgorithm) {
        this.allowRSA15KeyTransportAlgorithm = allowRSA15KeyTransportAlgorithm;
    }

    public Integer getDerivedKeyIterations() {
        return this.derivedKeyIterations;
    }

    public void setDerivedKeyIterations(Integer derivedKeyIterations) {
        this.derivedKeyIterations = derivedKeyIterations;
    }

    public boolean isUseDerivedKeyForMAC() {
        return this.useDerivedKeyForMAC;
    }

    public void setUseDerivedKeyForMAC(boolean useDerivedKeyForMAC) {
        this.useDerivedKeyForMAC = useDerivedKeyForMAC;
    }

    public boolean isAddUsernameTokenNonce() {
        return this.addUsernameTokenNonce;
    }

    public void setAddUsernameTokenNonce(boolean addUsernameTokenNonce) {
        this.addUsernameTokenNonce = addUsernameTokenNonce;
    }

    public boolean isAddUsernameTokenCreated() {
        return this.addUsernameTokenCreated;
    }

    public void setAddUsernameTokenCreated(boolean addUsernameTokenCreated) {
        this.addUsernameTokenCreated = addUsernameTokenCreated;
    }

    public CallbackHandler getSamlCallbackHandler() {
        return this.samlCallbackHandler;
    }

    public void setSamlCallbackHandler(CallbackHandler samlCallbackHandler) {
        this.samlCallbackHandler = samlCallbackHandler;
    }

    public boolean isValidateSamlSubjectConfirmation() {
        return this.validateSamlSubjectConfirmation;
    }

    public void setValidateSamlSubjectConfirmation(boolean validateSamlSubjectConfirmation) {
        this.validateSamlSubjectConfirmation = validateSamlSubjectConfirmation;
    }

    public boolean isMustUnderstand() {
        return this.mustUnderstand;
    }

    public void setMustUnderstand(boolean mustUnderstand) {
        this.mustUnderstand = mustUnderstand;
    }

    public boolean isIncludeSignatureToken() {
        return this.includeSignatureToken;
    }

    public void setIncludeSignatureToken(boolean includeSignatureToken) {
        this.includeSignatureToken = includeSignatureToken;
    }

    public boolean isIncludeEncryptionToken() {
        return this.includeEncryptionToken;
    }

    public void setIncludeEncryptionToken(boolean includeEncryptionToken) {
        this.includeEncryptionToken = includeEncryptionToken;
    }

    public boolean isEncryptSymmetricEncryptionKey() {
        return this.encryptSymmetricEncrytionKey;
    }

    public void setEncryptSymmetricEncryptionKey(boolean encryptSymmetricEncrytionKey) {
        this.encryptSymmetricEncrytionKey = encryptSymmetricEncrytionKey;
    }

    public void setSubjectCertConstraints(Collection<Pattern> subjectCertConstraints) {
        if (subjectCertConstraints != null) {
            this.subjectDNPatterns.addAll(subjectCertConstraints);
        }
    }

    public Collection<Pattern> getSubjectCertConstraints() {
        return this.subjectDNPatterns;
    }

    public void setIssuerDNConstraints(Collection<Pattern> issuerDNPatterns) {
        this.issuerDNPatterns = issuerDNPatterns;
    }

    public Collection<Pattern> getIssuerDNConstraints() {
        return this.issuerDNPatterns;
    }

    public void setAudienceRestrictions(List<String> audienceRestrictions) {
        if (audienceRestrictions != null) {
            this.audienceRestrictions.addAll(audienceRestrictions);
        }
    }

    public List<String> getAudienceRestrictions() {
        return this.audienceRestrictions;
    }

    public int getDerivedSignatureKeyLength() {
        return this.derivedSignatureKeyLength;
    }

    public void setDerivedSignatureKeyLength(int derivedSignatureKeyLength) {
        this.derivedSignatureKeyLength = derivedSignatureKeyLength;
    }

    public int getDerivedEncryptionKeyLength() {
        return this.derivedEncryptionKeyLength;
    }

    public void setDerivedEncryptionKeyLength(int derivedEncryptionKeyLength) {
        this.derivedEncryptionKeyLength = derivedEncryptionKeyLength;
    }

    public boolean isUse200512Namespace() {
        return this.use200512Namespace;
    }

    public void setUse200512Namespace(boolean use200512Namespace) {
        this.use200512Namespace = use200512Namespace;
    }

    public CallbackHandler getAttachmentCallbackHandler() {
        return this.attachmentCallbackHandler;
    }

    public void setAttachmentCallbackHandler(CallbackHandler attachmentCallbackHandler) {
        this.attachmentCallbackHandler = attachmentCallbackHandler;
    }

    public Object getMsgContext() {
        return this.msgContext;
    }

    public void setMsgContext(Object msgContext) {
        this.msgContext = msgContext;
    }

    public boolean isRequireTimestampExpires() {
        return this.requireTimestampExpires;
    }

    public void setRequireTimestampExpires(boolean requireTimestampExpires) {
        this.requireTimestampExpires = requireTimestampExpires;
    }
}

