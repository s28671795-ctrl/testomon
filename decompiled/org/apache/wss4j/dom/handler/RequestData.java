/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.handler;

import java.security.Provider;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.EncryptionActionToken;
import org.apache.wss4j.common.SignatureActionToken;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.SOAPConstants;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.validate.Validator;
import org.apache.xml.security.encryption.Serializer;

public class RequestData {
    private Object msgContext;
    private SOAPConstants soapConstants;
    private String actor;
    private String username;
    private String pwType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
    private Crypto sigVerCrypto;
    private Crypto decCrypto;
    private SignatureActionToken signatureToken;
    private EncryptionActionToken encryptionToken;
    private WSSConfig wssConfig;
    private List<byte[]> signatureValues = new ArrayList<byte[]>();
    private WSSecHeader secHeader;
    private int derivedKeyIterations = 1000;
    private boolean useDerivedKeyForMAC = true;
    private CallbackHandler callback;
    private CallbackHandler attachmentCallbackHandler;
    private boolean enableRevocation;
    private boolean requireSignedEncryptedDataElements;
    private ReplayCache timestampReplayCache;
    private ReplayCache nonceReplayCache;
    private ReplayCache samlOneTimeUseReplayCache;
    private Collection<Pattern> subjectDNPatterns = new ArrayList<Pattern>();
    private Collection<Pattern> issuerDNPatterns = new ArrayList<Pattern>();
    private final List<BSPRule> ignoredBSPRules = new LinkedList<BSPRule>();
    private boolean appendSignatureAfterTimestamp;
    private int originalSignatureActionPosition;
    private AlgorithmSuite algorithmSuite;
    private AlgorithmSuite samlAlgorithmSuite;
    private boolean disableBSPEnforcement;
    private boolean allowRSA15KeyTransportAlgorithm;
    private boolean addUsernameTokenNonce;
    private boolean addUsernameTokenCreated;
    private Certificate[] tlsCerts;
    private PasswordEncryptor passwordEncryptor;
    private String derivedKeyTokenReference;
    private boolean use200512Namespace = true;
    private final List<String> audienceRestrictions = new ArrayList<String>();
    private boolean requireTimestampExpires;
    private boolean storeBytesInAttachment;
    private Serializer encryptionSerializer;
    private WSDocInfo wsDocInfo;
    private Provider signatureProvider;
    private boolean addInclusivePrefixes = true;
    private boolean precisionInMilliSeconds = true;
    private boolean enableSignatureConfirmation;
    private boolean timeStampStrict = true;
    private String requiredPasswordType;
    private boolean allowUsernameTokenNoPassword;
    private int timeStampTTL = 300;
    private int timeStampFutureTTL = 60;
    private int utTTL = 300;
    private int utFutureTTL = 60;
    private boolean handleCustomPasswordTypes;
    private boolean allowNamespaceQualifiedPasswordTypes;
    private boolean encodePasswords;
    private boolean validateSamlSubjectConfirmation = true;
    private boolean expandXopInclude;

    public Object getMsgContext() {
        return this.msgContext;
    }

    public void setMsgContext(Object msgContext) {
        this.msgContext = msgContext;
    }

    public SOAPConstants getSoapConstants() {
        return this.soapConstants;
    }

    public void setSoapConstants(SOAPConstants soapConstants) {
        this.soapConstants = soapConstants;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwType() {
        return this.pwType;
    }

    public void setPwType(String pwType) {
        this.pwType = pwType;
    }

    public Crypto getSigVerCrypto() {
        return this.sigVerCrypto;
    }

    public void setSigVerCrypto(Crypto sigVerCrypto) {
        this.sigVerCrypto = sigVerCrypto;
    }

    public Crypto getDecCrypto() {
        return this.decCrypto;
    }

    public void setDecCrypto(Crypto decCrypto) {
        this.decCrypto = decCrypto;
    }

    public WSSConfig getWssConfig() {
        return this.wssConfig;
    }

    public void setWssConfig(WSSConfig wssConfig) {
        this.wssConfig = wssConfig;
    }

    public List<byte[]> getSignatureValues() {
        return this.signatureValues;
    }

    public WSSecHeader getSecHeader() {
        return this.secHeader;
    }

    public void setSecHeader(WSSecHeader secHeader) {
        this.secHeader = secHeader;
    }

    public void setDerivedKeyIterations(int iterations) {
        this.derivedKeyIterations = iterations;
    }

    public int getDerivedKeyIterations() {
        return this.derivedKeyIterations;
    }

    public void setUseDerivedKeyForMAC(boolean useMac) {
        this.useDerivedKeyForMAC = useMac;
    }

    public boolean isUseDerivedKeyForMAC() {
        return this.useDerivedKeyForMAC;
    }

    public void setEnableRevocation(boolean enableRevocation) {
        this.enableRevocation = enableRevocation;
    }

    public boolean isRevocationEnabled() {
        return this.enableRevocation;
    }

    public boolean isRequireSignedEncryptedDataElements() {
        return this.requireSignedEncryptedDataElements;
    }

    public void setRequireSignedEncryptedDataElements(boolean requireSignedEncryptedDataElements) {
        this.requireSignedEncryptedDataElements = requireSignedEncryptedDataElements;
    }

    public void setCallbackHandler(CallbackHandler cb) {
        this.callback = cb;
    }

    public CallbackHandler getCallbackHandler() {
        return this.callback;
    }

    public CallbackHandler getAttachmentCallbackHandler() {
        return this.attachmentCallbackHandler;
    }

    public void setAttachmentCallbackHandler(CallbackHandler attachmentCallbackHandler) {
        this.attachmentCallbackHandler = attachmentCallbackHandler;
    }

    public Validator getValidator(QName qName) throws WSSecurityException {
        Map validatorMap;
        if (this.getMsgContext() instanceof Map && (validatorMap = (Map)((Map)this.getMsgContext()).get("validatorMap")) != null && validatorMap.containsKey(qName)) {
            return (Validator)validatorMap.get(qName);
        }
        if (this.wssConfig != null) {
            return this.wssConfig.getValidator(qName);
        }
        return null;
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

    public void setSubjectCertConstraints(Collection<Pattern> subjectCertConstraints) {
        if (subjectCertConstraints != null) {
            this.subjectDNPatterns.addAll(subjectCertConstraints);
        }
    }

    public Collection<Pattern> getSubjectCertConstraints() {
        return this.subjectDNPatterns;
    }

    public Collection<Pattern> getIssuerDNPatterns() {
        return this.issuerDNPatterns;
    }

    public void setIssuerDNPatterns(Collection<Pattern> issuerDNPatterns) {
        this.issuerDNPatterns = issuerDNPatterns;
    }

    public void setAudienceRestrictions(List<String> audienceRestrictions) {
        if (audienceRestrictions != null) {
            this.audienceRestrictions.addAll(audienceRestrictions);
        }
    }

    public List<String> getAudienceRestrictions() {
        return this.audienceRestrictions;
    }

    public void setIgnoredBSPRules(List<BSPRule> bspRules) {
        this.ignoredBSPRules.clear();
        this.ignoredBSPRules.addAll(bspRules);
    }

    public List<BSPRule> getIgnoredBSPRules() {
        return Collections.unmodifiableList(this.ignoredBSPRules);
    }

    public BSPEnforcer getBSPEnforcer() {
        if (this.disableBSPEnforcement) {
            return new BSPEnforcer(true);
        }
        return new BSPEnforcer(this.ignoredBSPRules);
    }

    public boolean isAppendSignatureAfterTimestamp() {
        return this.appendSignatureAfterTimestamp;
    }

    public void setAppendSignatureAfterTimestamp(boolean appendSignatureAfterTimestamp) {
        this.appendSignatureAfterTimestamp = appendSignatureAfterTimestamp;
    }

    public AlgorithmSuite getAlgorithmSuite() {
        return this.algorithmSuite;
    }

    public void setAlgorithmSuite(AlgorithmSuite algorithmSuite) {
        this.algorithmSuite = algorithmSuite;
    }

    public AlgorithmSuite getSamlAlgorithmSuite() {
        return this.samlAlgorithmSuite;
    }

    public void setSamlAlgorithmSuite(AlgorithmSuite samlAlgorithmSuite) {
        this.samlAlgorithmSuite = samlAlgorithmSuite;
    }

    public int getOriginalSignatureActionPosition() {
        return this.originalSignatureActionPosition;
    }

    public void setOriginalSignatureActionPosition(int originalSignatureActionPosition) {
        this.originalSignatureActionPosition = originalSignatureActionPosition;
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

    public Certificate[] getTlsCerts() {
        return this.tlsCerts;
    }

    public void setTlsCerts(Certificate[] tlsCerts) {
        this.tlsCerts = tlsCerts;
    }

    public PasswordEncryptor getPasswordEncryptor() {
        return this.passwordEncryptor;
    }

    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    public SignatureActionToken getSignatureToken() {
        return this.signatureToken;
    }

    public void setSignatureToken(SignatureActionToken signatureToken) {
        this.signatureToken = signatureToken;
    }

    public EncryptionActionToken getEncryptionToken() {
        return this.encryptionToken;
    }

    public void setEncryptionToken(EncryptionActionToken encryptionToken) {
        this.encryptionToken = encryptionToken;
    }

    public String getDerivedKeyTokenReference() {
        return this.derivedKeyTokenReference;
    }

    public void setDerivedKeyTokenReference(String derivedKeyTokenReference) {
        this.derivedKeyTokenReference = derivedKeyTokenReference;
    }

    public boolean isUse200512Namespace() {
        return this.use200512Namespace;
    }

    public void setUse200512Namespace(boolean use200512Namespace) {
        this.use200512Namespace = use200512Namespace;
    }

    public boolean isRequireTimestampExpires() {
        return this.requireTimestampExpires;
    }

    public void setRequireTimestampExpires(boolean requireTimestampExpires) {
        this.requireTimestampExpires = requireTimestampExpires;
    }

    public boolean isValidateSamlSubjectConfirmation() {
        return this.validateSamlSubjectConfirmation;
    }

    public void setValidateSamlSubjectConfirmation(boolean validateSamlSubjectConfirmation) {
        this.validateSamlSubjectConfirmation = validateSamlSubjectConfirmation;
    }

    public boolean isAllowNamespaceQualifiedPasswordTypes() {
        return this.allowNamespaceQualifiedPasswordTypes;
    }

    public void setAllowNamespaceQualifiedPasswordTypes(boolean allowNamespaceQualifiedPasswordTypes) {
        this.allowNamespaceQualifiedPasswordTypes = allowNamespaceQualifiedPasswordTypes;
    }

    public int getUtFutureTTL() {
        return this.utFutureTTL;
    }

    public void setUtFutureTTL(int utFutureTTL) {
        this.utFutureTTL = utFutureTTL;
    }

    public boolean isHandleCustomPasswordTypes() {
        return this.handleCustomPasswordTypes;
    }

    public void setHandleCustomPasswordTypes(boolean handleCustomPasswordTypes) {
        this.handleCustomPasswordTypes = handleCustomPasswordTypes;
    }

    public int getUtTTL() {
        return this.utTTL;
    }

    public void setUtTTL(int utTTL) {
        this.utTTL = utTTL;
    }

    public int getTimeStampTTL() {
        return this.timeStampTTL;
    }

    public void setTimeStampTTL(int timeStampTTL) {
        this.timeStampTTL = timeStampTTL;
    }

    public int getTimeStampFutureTTL() {
        return this.timeStampFutureTTL;
    }

    public void setTimeStampFutureTTL(int timeStampFutureTTL) {
        this.timeStampFutureTTL = timeStampFutureTTL;
    }

    public boolean isAllowUsernameTokenNoPassword() {
        return this.allowUsernameTokenNoPassword;
    }

    public void setAllowUsernameTokenNoPassword(boolean allowUsernameTokenNoPassword) {
        this.allowUsernameTokenNoPassword = allowUsernameTokenNoPassword;
    }

    public boolean isTimeStampStrict() {
        return this.timeStampStrict;
    }

    public void setTimeStampStrict(boolean timeStampStrict) {
        this.timeStampStrict = timeStampStrict;
    }

    public boolean isAddInclusivePrefixes() {
        return this.addInclusivePrefixes;
    }

    public void setAddInclusivePrefixes(boolean addInclusivePrefixes) {
        this.addInclusivePrefixes = addInclusivePrefixes;
    }

    public boolean isPrecisionInMilliSeconds() {
        return this.precisionInMilliSeconds;
    }

    public void setPrecisionInMilliSeconds(boolean precisionInMilliSeconds) {
        this.precisionInMilliSeconds = precisionInMilliSeconds;
    }

    public boolean isEnableSignatureConfirmation() {
        return this.enableSignatureConfirmation;
    }

    public void setEnableSignatureConfirmation(boolean enableSignatureConfirmation) {
        this.enableSignatureConfirmation = enableSignatureConfirmation;
    }

    public String getRequiredPasswordType() {
        return this.requiredPasswordType;
    }

    public void setRequiredPasswordType(String requiredPasswordType) {
        this.requiredPasswordType = requiredPasswordType;
    }

    public boolean isEncodePasswords() {
        return this.encodePasswords;
    }

    public void setEncodePasswords(boolean encodePasswords) {
        this.encodePasswords = encodePasswords;
    }

    public boolean isStoreBytesInAttachment() {
        return this.storeBytesInAttachment;
    }

    public void setStoreBytesInAttachment(boolean storeBytesInAttachment) {
        this.storeBytesInAttachment = storeBytesInAttachment;
    }

    public boolean isExpandXopInclude() {
        return this.expandXopInclude;
    }

    public void setExpandXopInclude(boolean expandXopInclude) {
        this.expandXopInclude = expandXopInclude;
    }

    public Serializer getEncryptionSerializer() {
        return this.encryptionSerializer;
    }

    public void setEncryptionSerializer(Serializer encryptionSerializer) {
        this.encryptionSerializer = encryptionSerializer;
    }

    public boolean isAddUsernameTokenCreated() {
        return this.addUsernameTokenCreated;
    }

    public void setAddUsernameTokenCreated(boolean addUsernameTokenCreated) {
        this.addUsernameTokenCreated = addUsernameTokenCreated;
    }

    public boolean isAddUsernameTokenNonce() {
        return this.addUsernameTokenNonce;
    }

    public void setAddUsernameTokenNonce(boolean addUsernameTokenNonce) {
        this.addUsernameTokenNonce = addUsernameTokenNonce;
    }

    public WSDocInfo getWsDocInfo() {
        return this.wsDocInfo;
    }

    public void setWsDocInfo(WSDocInfo wsDocInfo) {
        this.wsDocInfo = wsDocInfo;
    }

    public Provider getSignatureProvider() {
        return this.signatureProvider;
    }

    public void setSignatureProvider(Provider signatureProvider) {
        this.signatureProvider = signatureProvider;
    }
}

