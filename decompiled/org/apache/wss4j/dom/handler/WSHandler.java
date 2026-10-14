/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.handler;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.EncryptionActionToken;
import org.apache.wss4j.common.SignatureActionToken;
import org.apache.wss4j.common.SignatureEncryptionActionToken;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.JasyptPasswordEncryptor;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.Loader;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.HandlerAction;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.token.SignatureConfirmation;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public abstract class WSHandler {
    private static final Logger LOG = LoggerFactory.getLogger(WSHandler.class);
    protected Map<String, Crypto> cryptos = new ConcurrentHashMap<String, Crypto>();

    protected void doSenderAction(Document doc, RequestData reqData, List<HandlerAction> actions, boolean isRequest) throws WSSecurityException {
        String done;
        WSSConfig wssConfig = reqData.getWssConfig();
        if (wssConfig == null) {
            wssConfig = WSSConfig.getNewInstance();
            reqData.setWssConfig(wssConfig);
        }
        if (reqData.getWsDocInfo() == null) {
            WSDocInfo wsDocInfo = new WSDocInfo(doc);
            reqData.setWsDocInfo(wsDocInfo);
        }
        Object mc = reqData.getMsgContext();
        reqData.setEncodePasswords(this.decodeBooleanConfigValue(mc, "useEncodedPasswords", false));
        reqData.setPrecisionInMilliSeconds(this.decodeBooleanConfigValue(mc, "precisionInMilliseconds", true));
        reqData.setAddInclusivePrefixes(this.decodeBooleanConfigValue(mc, "addInclusivePrefixes", true));
        reqData.setEnableSignatureConfirmation(this.decodeBooleanConfigValue(mc, "enableSignatureConfirmation", false));
        reqData.setTimeStampTTL(this.decodeTimeToLive(reqData, true));
        String actor = this.getString("actor", mc);
        reqData.setActor(actor);
        boolean mu = this.decodeBooleanConfigValue(mc, "mustUnderstand", true);
        WSSecHeader secHeader = new WSSecHeader(actor, mu, doc);
        secHeader.insertSecurityHeader();
        reqData.setSecHeader(secHeader);
        reqData.setSoapConstants(WSSecurityUtil.getSOAPConstants(doc.getDocumentElement()));
        if (reqData.getCallbackHandler() == null) {
            CallbackHandler passwordCallbackHandler = this.getPasswordCallbackHandler(reqData);
            reqData.setCallbackHandler(passwordCallbackHandler);
        }
        if (!reqData.isStoreBytesInAttachment()) {
            boolean storeBytesInAttachment = this.decodeBooleanConfigValue(mc, "storeBytesInAttachment", false);
            reqData.setStoreBytesInAttachment(storeBytesInAttachment);
        }
        boolean encryptionFound = false;
        for (HandlerAction actionToDo : actions) {
            SignatureEncryptionActionToken actionToken;
            if (actionToDo.getAction() == 128) {
                reqData.setEnableSignatureConfirmation(true);
                continue;
            }
            if ((actionToDo.getAction() == 1 || actionToDo.getAction() == 8192) && actionToDo.getActionToken() == null) {
                this.decodeUTParameter(reqData);
                if (actionToDo.getAction() != 8192) continue;
                reqData.setPwType(null);
                continue;
            }
            if (actionToDo.getAction() == 64 && actionToDo.getActionToken() == null) {
                this.decodeUTParameter(reqData);
                this.decodeSignatureParameter(reqData);
                continue;
            }
            if ((actionToDo.getAction() == 2 || actionToDo.getAction() == 32768) && actionToDo.getActionToken() == null) {
                actionToken = reqData.getSignatureToken();
                if (actionToken == null) {
                    actionToken = new SignatureActionToken();
                    reqData.setSignatureToken((SignatureActionToken)actionToken);
                }
                if (actionToken.getCrypto() == null) {
                    actionToken.setCrypto(this.loadSignatureCrypto(reqData));
                }
                this.decodeSignatureParameter(reqData);
                if (!encryptionFound || !reqData.isStoreBytesInAttachment()) continue;
                LOG.warn("Turning off storeBytesInAttachment as we have encryption before signature. The danger here is that the actual encryption bytes will not be signed");
                reqData.setStoreBytesInAttachment(false);
                continue;
            }
            if (actionToDo.getAction() == 16 && actionToDo.getActionToken() == null) {
                this.decodeSignatureParameter(reqData);
                continue;
            }
            if (actionToDo.getAction() != 4 && actionToDo.getAction() != 65536 || actionToDo.getActionToken() != null) continue;
            encryptionFound = true;
            actionToken = reqData.getEncryptionToken();
            if (actionToken == null) {
                actionToken = new EncryptionActionToken();
                reqData.setEncryptionToken((EncryptionActionToken)actionToken);
            }
            if (actionToken.getCrypto() == null) {
                actionToken.setCrypto(this.loadEncryptionCrypto(reqData));
            }
            this.decodeEncryptionParameter(reqData);
        }
        SignatureActionToken signatureToken = reqData.getSignatureToken();
        if (signatureToken == null) {
            signatureToken = new SignatureActionToken();
            reqData.setSignatureToken(signatureToken);
        }
        if (signatureToken.getParts().isEmpty()) {
            signatureToken.getParts().add(WSSecurityUtil.getDefaultEncryptionPart(doc));
        }
        if (reqData.isEnableSignatureConfirmation() && !isRequest && (done = (String)this.getProperty(reqData.getMsgContext(), "_sigConfDone_")) == null) {
            wssConfig.getAction(128).execute(this, null, reqData);
        }
        List<HandlerAction> actionsToPerform = actions;
        HandlerAction signingAction = this.getSignatureActionThatSignsATimestamp(actions, reqData);
        if (signingAction != null) {
            actionsToPerform = new ArrayList<HandlerAction>(actions);
            Collections.copy(actionsToPerform, actions);
            int signatureIndex = actions.indexOf(signingAction);
            actionsToPerform.remove(signingAction);
            actionsToPerform.add(signingAction);
            reqData.setAppendSignatureAfterTimestamp(true);
            reqData.setOriginalSignatureActionPosition(signatureIndex);
        }
        for (HandlerAction actionToDo : actionsToPerform) {
            LOG.debug("Performing Action: {}", (Object)actionToDo.getAction());
            if (0 == actionToDo.getAction()) continue;
            wssConfig.getAction(actionToDo.getAction()).execute(this, actionToDo.getActionToken(), reqData);
        }
        if (reqData.isEnableSignatureConfirmation() && isRequest && !reqData.getSignatureValues().isEmpty()) {
            HashSet<Integer> savedSignatures = (HashSet<Integer>)this.getProperty(reqData.getMsgContext(), "_sendSignatureValues_");
            if (savedSignatures == null) {
                savedSignatures = new HashSet<Integer>();
                this.setProperty(reqData.getMsgContext(), "_sendSignatureValues_", savedSignatures);
            }
            for (byte[] signatureValue : reqData.getSignatureValues()) {
                savedSignatures.add(Arrays.hashCode(signatureValue));
            }
        }
    }

    private HandlerAction getSignatureActionThatSignsATimestamp(List<HandlerAction> actions, RequestData reqData) {
        for (HandlerAction action : actions) {
            if (action.getAction() == 32) {
                return null;
            }
            if (action.getAction() != 2) continue;
            if (action.getActionToken() != null && ((SignatureEncryptionActionToken)action.getActionToken()).getParts() != null) {
                for (WSEncryptionPart encP : ((SignatureEncryptionActionToken)action.getActionToken()).getParts()) {
                    if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(encP.getNamespace()) || !"Timestamp".equals(encP.getName())) continue;
                    return action;
                }
                continue;
            }
            for (WSEncryptionPart encP : reqData.getSignatureToken().getParts()) {
                if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(encP.getNamespace()) || !"Timestamp".equals(encP.getName())) continue;
                return action;
            }
        }
        return null;
    }

    protected void doReceiverAction(List<Integer> actions, RequestData reqData) throws WSSecurityException {
        Object mc;
        boolean enableSigConf;
        WSSConfig wssConfig = reqData.getWssConfig();
        if (wssConfig == null) {
            wssConfig = WSSConfig.getNewInstance();
            reqData.setWssConfig(wssConfig);
        }
        reqData.setEnableSignatureConfirmation((enableSigConf = this.decodeBooleanConfigValue(mc = reqData.getMsgContext(), "enableSignatureConfirmation", false)) || actions.contains(128));
        reqData.setTimeStampStrict(this.decodeBooleanConfigValue(mc, "timestampStrict", true));
        reqData.setRequiredPasswordType(this.decodePasswordType(reqData));
        reqData.setTimeStampTTL(this.decodeTimeToLive(reqData, true));
        reqData.setTimeStampFutureTTL(this.decodeFutureTimeToLive(reqData, true));
        reqData.setUtTTL(this.decodeTimeToLive(reqData, false));
        reqData.setUtFutureTTL(this.decodeFutureTimeToLive(reqData, false));
        reqData.setHandleCustomPasswordTypes(this.decodeBooleanConfigValue(mc, "handleCustomPasswordTypes", false));
        reqData.setEncodePasswords(this.decodeBooleanConfigValue(mc, "useEncodedPasswords", false));
        reqData.setAllowNamespaceQualifiedPasswordTypes(this.decodeBooleanConfigValue(mc, "allowNamespaceQualifiedPasswordTypes", false));
        reqData.setAllowUsernameTokenNoPassword(this.decodeBooleanConfigValue(mc, "allowUsernameTokenNoPassword", false));
        reqData.setValidateSamlSubjectConfirmation(this.decodeBooleanConfigValue(mc, "validateSamlSubjectConfirmation", true));
        boolean bspCompliant = this.decodeBooleanConfigValue(mc, "isBSPCompliant", true);
        if (!bspCompliant) {
            reqData.setDisableBSPEnforcement(true);
        }
        if (reqData.getCallbackHandler() == null) {
            CallbackHandler passwordCallbackHandler = this.getPasswordCallbackHandler(reqData);
            reqData.setCallbackHandler(passwordCallbackHandler);
        }
        if (actions.contains(2) || actions.contains(16) || actions.contains(8)) {
            this.decodeSignatureParameter2(reqData);
        }
        if (actions.contains(4)) {
            this.decodeDecryptionParameter(reqData);
        }
        reqData.setRequireSignedEncryptedDataElements(this.decodeBooleanConfigValue(mc, "requireSignedEncryptedDataElements", false));
        reqData.setRequireTimestampExpires(this.decodeBooleanConfigValue(mc, "requireTimestampExpires", false));
    }

    protected boolean checkReceiverResults(List<WSSecurityEngineResult> wsResult, List<Integer> actions) {
        int size = actions.size();
        int ai = 0;
        for (WSSecurityEngineResult result : wsResult) {
            int act;
            Integer actInt = (Integer)result.get("action");
            if (actInt == null || (act = actInt.intValue()) == 128 || act == 4096 || ai < size && actions.get(ai++) == act) continue;
            return false;
        }
        return ai == size;
    }

    protected boolean checkReceiverResultsAnyOrder(List<WSSecurityEngineResult> wsResult, List<Integer> actions) {
        ArrayList<Integer> recordedActions = new ArrayList<Integer>(actions.size());
        for (Integer action : actions) {
            recordedActions.add(action);
        }
        for (WSSecurityEngineResult result : wsResult) {
            int act;
            Integer actInt = (Integer)result.get("action");
            if (actInt == null || (act = actInt.intValue()) == 128 || act == 4096 || act == 4 && (result.get("data-ref-uris") == null || ((List)result.get("data-ref-uris")).isEmpty()) || recordedActions.remove(actInt)) continue;
            return false;
        }
        return recordedActions.isEmpty();
    }

    protected void checkSignatureConfirmation(RequestData reqData, WSHandlerResult handlerResults) throws WSSecurityException {
        LOG.debug("Check Signature confirmation");
        Set savedSignatures = (Set)this.getProperty(reqData.getMsgContext(), "_sendSignatureValues_");
        List<WSSecurityEngineResult> sigConf = handlerResults.getActionResults().get(128);
        if (sigConf != null) {
            for (WSSecurityEngineResult result : sigConf) {
                SignatureConfirmation sc = (SignatureConfirmation)result.get("signature-confirmation");
                if (sc == null || sc.getSignatureValue() == null) continue;
                if (savedSignatures == null || savedSignatures.isEmpty()) {
                    if (sc.getSignatureValue().length == 0) continue;
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"Received a SignatureConfirmation element, but there are no stored signature values"});
                }
                Integer hash = Arrays.hashCode(sc.getSignatureValue());
                if (savedSignatures.contains(hash)) {
                    savedSignatures.remove(hash);
                    continue;
                }
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Received a SignatureConfirmation element, but there are no matching stored signature values"});
            }
        }
        if (savedSignatures != null && !savedSignatures.isEmpty()) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Check Signature confirmation: the stored signature values list is not empty"});
        }
    }

    protected void decodeUTParameter(RequestData reqData) throws WSSecurityException {
        String iterations;
        Object mc = reqData.getMsgContext();
        String type = this.getString("passwordType", mc);
        if (type != null) {
            if ("PasswordText".equals(type)) {
                reqData.setPwType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
            } else if ("PasswordDigest".equals(type)) {
                reqData.setPwType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
            } else if ("PasswordNone".equals(type)) {
                reqData.setPwType(null);
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Unknown password type encoding: " + type});
            }
        }
        boolean addNonce = this.decodeBooleanConfigValue(mc, "addUsernameTokenNonce", false);
        reqData.setAddUsernameTokenNonce(addNonce);
        boolean addCreated = this.decodeBooleanConfigValue(mc, "addUsernameTokenCreated", false);
        reqData.setAddUsernameTokenCreated(addCreated);
        String derivedMAC = this.getString("useDerivedKeyForMAC", mc);
        boolean useDerivedKeyForMAC = Boolean.parseBoolean(derivedMAC);
        if (useDerivedKeyForMAC) {
            reqData.setUseDerivedKeyForMAC(useDerivedKeyForMAC);
        }
        if ((iterations = this.getString("derivedKeyIterations", mc)) != null) {
            int iIterations = Integer.parseInt(iterations);
            reqData.setDerivedKeyIterations(iIterations);
        }
    }

    protected void decodeSignatureParameter(RequestData reqData) throws WSSecurityException {
        String derivedKeyLength;
        Object mc = reqData.getMsgContext();
        String signatureUser = this.getString("signatureUser", mc);
        SignatureActionToken actionToken = reqData.getSignatureToken();
        if (actionToken == null) {
            actionToken = new SignatureActionToken();
            reqData.setSignatureToken(actionToken);
        }
        if (signatureUser != null) {
            actionToken.setUser(signatureUser);
        } else {
            actionToken.setUser(reqData.getUsername());
        }
        String keyId = this.getString("signatureKeyIdentifier", mc);
        if (keyId != null) {
            Integer id = WSHandlerConstants.getKeyIdentifier(keyId);
            if (id == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"WSHandler: Signature: unknown key identification"});
            }
            int tmp = id;
            if (tmp != 2 && tmp != 1 && tmp != 3 && tmp != 4 && tmp != 8 && tmp != 10 && tmp != 13) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"WSHandler: Signature: illegal key identification"});
            }
            actionToken.setKeyIdentifierId(tmp);
        }
        String algo = this.getString("signatureAlgorithm", mc);
        actionToken.setSignatureAlgorithm(algo);
        String derivedKeyReference = this.getString("derivedTokenReference", mc);
        actionToken.setDerivedKeyTokenReference(derivedKeyReference);
        String derivedKeyIdentifier = this.getString("derivedTokenKeyIdentifier", mc);
        if (derivedKeyIdentifier != null) {
            Integer id = WSHandlerConstants.getKeyIdentifier(derivedKeyIdentifier);
            actionToken.setDerivedKeyIdentifier(id);
        }
        if ((derivedKeyLength = this.getString("derivedSignatureKeyLength", mc)) != null) {
            try {
                int dKL = Integer.parseInt(derivedKeyLength);
                if (dKL > 0) {
                    actionToken.setDerivedKeyLength(dKL);
                }
            }
            catch (NumberFormatException e) {
                LOG.warn("Error in configuring a derived key length: " + e.getMessage());
            }
        }
        String digestAlgo = this.getString("signatureDigestAlgorithm", mc);
        actionToken.setDigestAlgorithm(digestAlgo);
        String c14nAlgo = this.getString("signatureC14nAlgorithm", mc);
        actionToken.setC14nAlgorithm(c14nAlgo);
        boolean use200512Namespace = this.decodeBooleanConfigValue(mc, "use200512Namespace", true);
        reqData.setUse200512Namespace(use200512Namespace);
        String parts = this.getString("signatureParts", mc);
        if (parts != null) {
            this.splitEncParts(true, parts, actionToken.getParts(), reqData);
        }
        if ((parts = this.getString("optionalSignatureParts", mc)) != null) {
            this.splitEncParts(false, parts, actionToken.getParts(), reqData);
        }
        boolean useSingleCert = this.decodeBooleanConfigValue(mc, "useSingleCertificate", true);
        actionToken.setUseSingleCert(useSingleCert);
        boolean includeToken = this.decodeBooleanConfigValue(mc, "includeSignatureToken", false);
        actionToken.setIncludeToken(includeToken);
        if (!reqData.isExpandXopInclude()) {
            boolean expandXOP = this.decodeBooleanConfigValue(reqData.getMsgContext(), "expandXOPInclude", false);
            reqData.setExpandXopInclude(expandXOP);
        }
    }

    protected void decodeAlgorithmSuite(RequestData reqData) throws WSSecurityException {
        String transportAlgorithm;
        String encrAlgorithm;
        String signatureDigestAlgorithm;
        Object mc = reqData.getMsgContext();
        if (mc == null || reqData.getAlgorithmSuite() != null) {
            return;
        }
        AlgorithmSuite algorithmSuite = new AlgorithmSuite();
        String signatureAlgorithm = this.getString("signatureAlgorithm", mc);
        if (signatureAlgorithm != null && !"".equals(signatureAlgorithm)) {
            algorithmSuite.addSignatureMethod(signatureAlgorithm);
        }
        if ((signatureDigestAlgorithm = this.getString("signatureDigestAlgorithm", mc)) != null && !"".equals(signatureDigestAlgorithm)) {
            algorithmSuite.addDigestAlgorithm(signatureDigestAlgorithm);
        }
        if ((encrAlgorithm = this.getString("encryptionSymAlgorithm", mc)) != null && !"".equals(encrAlgorithm)) {
            algorithmSuite.addEncryptionMethod(encrAlgorithm);
        }
        if ((transportAlgorithm = this.getString("encryptionKeyTransportAlgorithm", mc)) != null && !"".equals(transportAlgorithm)) {
            algorithmSuite.addKeyWrapAlgorithm(transportAlgorithm);
        }
        reqData.setAlgorithmSuite(algorithmSuite);
    }

    protected void decodeEncryptionParameter(RequestData reqData) throws WSSecurityException {
        String encUser;
        String derivedKeyLength;
        String encKeyId;
        Object mc = reqData.getMsgContext();
        EncryptionActionToken actionToken = reqData.getEncryptionToken();
        if (actionToken == null) {
            actionToken = new EncryptionActionToken();
            reqData.setEncryptionToken(actionToken);
        }
        if ((encKeyId = this.getString("encryptionKeyIdentifier", mc)) != null) {
            Integer id = WSHandlerConstants.getKeyIdentifier(encKeyId);
            if (id == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"WSHandler: Encryption: unknown key identification"});
            }
            int tmp = id;
            actionToken.setKeyIdentifierId(tmp);
            if (tmp != 2 && tmp != 3 && tmp != 4 && tmp != 1 && tmp != 8 && tmp != 10) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"WSHandler: Encryption: illegal key identification"});
            }
        }
        String encSymAlgo = this.getString("encryptionSymAlgorithm", mc);
        actionToken.setSymmetricAlgorithm(encSymAlgo);
        String encKeyTransport = this.getString("encryptionKeyTransportAlgorithm", mc);
        actionToken.setKeyTransportAlgorithm(encKeyTransport);
        String derivedKeyReference = this.getString("derivedTokenReference", mc);
        actionToken.setDerivedKeyTokenReference(derivedKeyReference);
        String derivedKeyIdentifier = this.getString("derivedTokenKeyIdentifier", mc);
        if (derivedKeyIdentifier != null) {
            Integer id = WSHandlerConstants.getKeyIdentifier(derivedKeyIdentifier);
            actionToken.setDerivedKeyIdentifier(id);
        }
        if ((derivedKeyLength = this.getString("derivedEncryptionKeyLength", mc)) != null) {
            try {
                int dKL = Integer.parseInt(derivedKeyLength);
                if (dKL > 0) {
                    actionToken.setDerivedKeyLength(dKL);
                }
            }
            catch (NumberFormatException e) {
                LOG.warn("Error in configuring a derived key length: " + e.getMessage());
            }
        }
        boolean use200512Namespace = this.decodeBooleanConfigValue(mc, "use200512Namespace", true);
        reqData.setUse200512Namespace(use200512Namespace);
        boolean getSecretKeyFromCallbackHandler = this.decodeBooleanConfigValue(mc, "getSecretKeyFromCallbackHandler", false);
        actionToken.setGetSymmetricKeyFromCallbackHandler(getSecretKeyFromCallbackHandler);
        String digestAlgo = this.getString("encryptionDigestAlgorithm", mc);
        actionToken.setDigestAlgorithm(digestAlgo);
        String mgfAlgo = this.getString("encryptionMGFAlgorithm", mc);
        actionToken.setMgfAlgorithm(mgfAlgo);
        String encSymEncKey = this.getString("encryptSymmetricEncryptionKey", mc);
        if (encSymEncKey != null) {
            boolean encSymEndKeyBoolean = Boolean.parseBoolean(encSymEncKey);
            actionToken.setEncSymmetricEncryptionKey(encSymEndKeyBoolean);
        }
        if ((encUser = this.getString("encryptionUser", mc)) != null) {
            actionToken.setUser(encUser);
        } else {
            actionToken.setUser(reqData.getUsername());
        }
        if (actionToken.isEncSymmetricEncryptionKey() && actionToken.getUser() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"WSHandler: Encryption: no username"});
        }
        this.handleSpecialUser(reqData);
        String encParts = this.getString("encryptionParts", mc);
        if (encParts != null) {
            this.splitEncParts(true, encParts, actionToken.getParts(), reqData);
        }
        if ((encParts = this.getString("optionalEncryptionParts", mc)) != null) {
            this.splitEncParts(false, encParts, actionToken.getParts(), reqData);
        }
        boolean includeToken = this.decodeBooleanConfigValue(mc, "includeEncryptionToken", false);
        actionToken.setIncludeToken(includeToken);
    }

    public int decodeTimeToLive(RequestData reqData, boolean timestamp) {
        String tag = "timeToLive";
        if (!timestamp) {
            tag = "utTimeToLive";
        }
        String ttl = this.getString(tag, reqData.getMsgContext());
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

    protected int decodeFutureTimeToLive(RequestData reqData, boolean timestamp) {
        String tag = "futureTimeToLive";
        if (!timestamp) {
            tag = "utFutureTimeToLive";
        }
        String ttl = this.getString(tag, reqData.getMsgContext());
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

    protected String decodePasswordType(RequestData reqData) throws WSSecurityException {
        String type = this.getString("passwordType", reqData.getMsgContext());
        if (type != null) {
            if ("PasswordText".equals(type)) {
                return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
            }
            if ("PasswordDigest".equals(type)) {
                return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
            }
        }
        return null;
    }

    protected boolean decodeBooleanConfigValue(Object messageContext, String configTag, boolean defaultToTrue) throws WSSecurityException {
        String value = this.getString(configTag, messageContext);
        if (value == null) {
            return defaultToTrue;
        }
        if ("0".equals(value) || "false".equals(value)) {
            return false;
        }
        if ("1".equals(value) || "true".equals(value)) {
            return true;
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"WSHandler: illegal " + configTag + " parameter"});
    }

    public Crypto loadSignatureCrypto(RequestData requestData) throws WSSecurityException {
        return this.loadCrypto("signaturePropFile", "signaturePropRefId", requestData);
    }

    public Crypto loadSignatureVerificationCrypto(RequestData requestData) throws WSSecurityException {
        return this.loadCrypto("signatureVerificationPropFile", "signatureVerificationPropRefId", requestData);
    }

    protected Crypto loadDecryptionCrypto(RequestData requestData) throws WSSecurityException {
        return this.loadCrypto("decryptionPropFile", "decryptionPropRefId", requestData);
    }

    protected Crypto loadEncryptionCrypto(RequestData requestData) throws WSSecurityException {
        return this.loadCrypto("encryptionPropFile", "encryptionPropRefId", requestData);
    }

    protected Crypto loadCrypto(String cryptoPropertyFile, String cryptoPropertyRefId, RequestData requestData) throws WSSecurityException {
        String propFile;
        Object mc = requestData.getMsgContext();
        Crypto crypto = null;
        String refId = this.getString(cryptoPropertyRefId, mc);
        if (refId != null) {
            crypto = this.cryptos.get(refId);
            if (crypto == null) {
                Object obj = this.getProperty(mc, refId);
                if (obj instanceof Properties) {
                    crypto = CryptoFactory.getInstance((Properties)obj, Loader.getClassLoader(CryptoFactory.class), this.getPasswordEncryptor(requestData));
                    this.cryptos.put(refId, crypto);
                } else if (obj instanceof Crypto) {
                    crypto = (Crypto)obj;
                }
            }
            if (crypto == null) {
                LOG.warn("The Crypto reference " + refId + " specified by " + cryptoPropertyRefId + " could not be loaded");
            }
        }
        if (crypto == null && (propFile = this.getString(cryptoPropertyFile, mc)) != null) {
            crypto = this.cryptos.get(propFile);
            if (crypto == null) {
                crypto = this.loadCryptoFromPropertiesFile(propFile, requestData);
                this.cryptos.put(propFile, crypto);
            }
            if (crypto == null) {
                LOG.warn("The Crypto properties file " + propFile + " specified by " + cryptoPropertyFile + " could not be loaded or found");
            }
        }
        return crypto;
    }

    protected Crypto loadCryptoFromPropertiesFile(String propFilename, RequestData reqData) throws WSSecurityException {
        ClassLoader classLoader = this.getClassLoader(reqData.getMsgContext());
        Properties properties = CryptoFactory.getProperties(propFilename, classLoader);
        return CryptoFactory.getInstance(properties, classLoader, this.getPasswordEncryptor(reqData));
    }

    public CallbackHandler getCallbackHandler(String callbackHandlerClass, String callbackHandlerRef, RequestData requestData) throws WSSecurityException {
        String callback;
        Object mc = requestData.getMsgContext();
        CallbackHandler cbHandler = (CallbackHandler)this.getOption(callbackHandlerRef);
        if (cbHandler == null) {
            cbHandler = (CallbackHandler)this.getProperty(mc, callbackHandlerRef);
        }
        if (cbHandler == null && (callback = this.getString(callbackHandlerClass, mc)) != null) {
            cbHandler = this.loadCallbackHandler(callback, requestData);
        }
        return cbHandler;
    }

    public CallbackHandler getPasswordCallbackHandler(RequestData reqData) throws WSSecurityException {
        return this.getCallbackHandler("passwordCallbackClass", "passwordCallbackRef", reqData);
    }

    private CallbackHandler loadCallbackHandler(String callbackHandlerClass, RequestData requestData) throws WSSecurityException {
        Class<CallbackHandler> cbClass = null;
        CallbackHandler cbHandler = null;
        try {
            cbClass = Loader.loadClass(this.getClassLoader(requestData.getMsgContext()), callbackHandlerClass, CallbackHandler.class);
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

    protected PasswordEncryptor getPasswordEncryptor(RequestData requestData) {
        CallbackHandler callbackHandler;
        Object mc;
        Object o;
        Object o2;
        PasswordEncryptor passwordEncryptor = requestData.getPasswordEncryptor();
        if (passwordEncryptor == null && (o2 = this.getOption("passwordEncryptorInstance")) instanceof PasswordEncryptor) {
            passwordEncryptor = (PasswordEncryptor)o2;
        }
        if (passwordEncryptor == null && (o = this.getProperty(mc = requestData.getMsgContext(), "passwordEncryptorInstance")) instanceof PasswordEncryptor) {
            passwordEncryptor = (PasswordEncryptor)o;
        }
        if (passwordEncryptor == null && (callbackHandler = requestData.getCallbackHandler()) != null) {
            passwordEncryptor = new JasyptPasswordEncryptor(callbackHandler);
        }
        return passwordEncryptor;
    }

    public WSPasswordCallback getPasswordCB(String username, int doAction, CallbackHandler callbackHandler, RequestData requestData) throws WSSecurityException {
        if (callbackHandler != null) {
            return this.performPasswordCallback(callbackHandler, username, doAction);
        }
        String password = this.getPassword(requestData.getMsgContext());
        if (password == null) {
            String err = "provided null or empty password";
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"WSHandler: application " + err});
        }
        WSPasswordCallback pwCb = this.constructPasswordCallback(username, doAction);
        pwCb.setPassword(password);
        return pwCb;
    }

    private WSPasswordCallback performPasswordCallback(CallbackHandler cbHandler, String username, int doAction) throws WSSecurityException {
        WSPasswordCallback pwCb = this.constructPasswordCallback(username, doAction);
        Callback[] callbacks = new Callback[]{pwCb};
        try {
            cbHandler.handle(callbacks);
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"WSHandler: password callback failed"});
        }
        return pwCb;
    }

    private WSPasswordCallback constructPasswordCallback(String username, int doAction) throws WSSecurityException {
        int reason;
        switch (doAction) {
            case 1: 
            case 64: {
                reason = 2;
                break;
            }
            case 2: {
                reason = 3;
                break;
            }
            case 32768: {
                reason = 9;
                break;
            }
            case 4: {
                reason = 9;
                break;
            }
            case 65536: {
                reason = 9;
                break;
            }
            default: {
                reason = 0;
            }
        }
        return new WSPasswordCallback(username, reason);
    }

    private void splitEncParts(boolean required, String tmpS, List<WSEncryptionPart> parts, RequestData reqData) throws WSSecurityException {
        WSEncryptionPart encPart = null;
        String[] rawParts = tmpS.split(";");
        for (int i = 0; i < rawParts.length; ++i) {
            String mode;
            String[] partDef = rawParts[i].split("}");
            if (partDef.length == 1) {
                LOG.debug("single partDef: '{}'", (Object)partDef[0]);
                encPart = new WSEncryptionPart(partDef[0].trim(), reqData.getSoapConstants().getEnvelopeURI(), "Content");
            } else if (partDef.length == 2) {
                mode = partDef[0].trim().substring(1);
                String element = partDef[1].trim();
                encPart = new WSEncryptionPart(element, mode);
            } else if (partDef.length == 3) {
                mode = partDef[0].trim();
                mode = mode.length() <= 1 ? "Content" : mode.substring(1);
                String nmSpace = partDef[1].trim();
                if (nmSpace.length() <= 1) {
                    nmSpace = reqData.getSoapConstants().getEnvelopeURI();
                } else if ((nmSpace = nmSpace.substring(1)).equals("Null")) {
                    nmSpace = null;
                }
                String element = partDef[2].trim();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("partDefs: '" + mode + "' ,'" + nmSpace + "' ,'" + element + "'");
                }
                encPart = new WSEncryptionPart(element, nmSpace, mode);
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"WSHandler: wrong part definition: " + tmpS});
            }
            encPart.setRequired(required);
            parts.add(encPart);
        }
    }

    private void handleSpecialUser(RequestData reqData) {
        EncryptionActionToken actionToken = reqData.getEncryptionToken();
        if (actionToken == null || !"useReqSigCert".equals(actionToken.getUser())) {
            return;
        }
        List results = (List)this.getProperty(reqData.getMsgContext(), "RECV_RESULTS");
        if (results == null) {
            return;
        }
        for (WSHandlerResult rResult : results) {
            String hActor = rResult.getActor();
            if (!WSSecurityUtil.isActorEqual(reqData.getActor(), hActor)) continue;
            List<WSSecurityEngineResult> wsSecEngineResults = rResult.getResults();
            for (WSSecurityEngineResult wser : wsSecEngineResults) {
                Integer wserAction = (Integer)wser.get("action");
                if (wserAction == null || wserAction != 2) continue;
                X509Certificate cert = (X509Certificate)wser.get("x509-certificate");
                actionToken.setCertificate(cert);
                return;
            }
        }
    }

    protected void decodeSignatureParameter2(RequestData reqData) throws WSSecurityException {
        String issuerCertConstraintsStringValue;
        if (reqData.getSigVerCrypto() == null) {
            reqData.setSigVerCrypto(this.loadSignatureVerificationCrypto(reqData));
        }
        if (reqData.getSigVerCrypto() == null) {
            reqData.setSigVerCrypto(this.loadSignatureCrypto(reqData));
        }
        boolean enableRevocation = this.decodeBooleanConfigValue(reqData.getMsgContext(), "enableRevocation", false);
        reqData.setEnableRevocation(enableRevocation);
        String certConstraints = this.getString("sigSubjectCertConstraints", reqData.getMsgContext());
        if (certConstraints != null) {
            String certConstraintsSeparator = this.getString("sigCertConstraintsSeparator", reqData.getMsgContext());
            if (certConstraintsSeparator == null || certConstraintsSeparator.isEmpty()) {
                certConstraintsSeparator = ",";
            }
            Collection<Pattern> subjectCertConstraints = this.getCertConstraints(certConstraints, certConstraintsSeparator);
            reqData.setSubjectCertConstraints(subjectCertConstraints);
        }
        if ((issuerCertConstraintsStringValue = this.getString("sigIssuerCertConstraints", reqData.getMsgContext())) != null) {
            String certConstraintsSeparator = this.getString("sigCertConstraintsSeparator", reqData.getMsgContext());
            if (certConstraintsSeparator == null || certConstraintsSeparator.isEmpty()) {
                certConstraintsSeparator = ",";
            }
            Collection<Pattern> issuerCertConstraints = this.getCertConstraints(issuerCertConstraintsStringValue, certConstraintsSeparator);
            reqData.setIssuerDNPatterns(issuerCertConstraints);
        }
        String value = this.getString("expandXOPIncludeForSignature", reqData.getMsgContext());
        boolean expandXOP = false;
        expandXOP = value != null ? this.decodeBooleanConfigValue(reqData.getMsgContext(), "expandXOPIncludeForSignature", true) : this.decodeBooleanConfigValue(reqData.getMsgContext(), "expandXOPInclude", true);
        reqData.setExpandXopInclude(expandXOP);
    }

    private Collection<Pattern> getCertConstraints(String certConstraints, String separator) throws WSSecurityException {
        String[] certConstraintsList = certConstraints.split(separator);
        if (certConstraintsList != null && certConstraintsList.length > 0) {
            ArrayList<Pattern> certConstraintsCollection = new ArrayList<Pattern>(certConstraintsList.length);
            for (String certConstraint : certConstraintsList) {
                try {
                    certConstraintsCollection.add(Pattern.compile(certConstraint.trim()));
                }
                catch (PatternSyntaxException ex) {
                    LOG.debug(ex.getMessage(), ex);
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)ex);
                }
            }
            return certConstraintsCollection;
        }
        return Collections.emptyList();
    }

    protected void decodeDecryptionParameter(RequestData reqData) throws WSSecurityException {
        if (reqData.getDecCrypto() == null) {
            reqData.setDecCrypto(this.loadDecryptionCrypto(reqData));
        }
        boolean allowRsa15 = this.decodeBooleanConfigValue(reqData.getMsgContext(), "allowRSA15KeyTransportAlgorithm", false);
        reqData.setAllowRSA15KeyTransportAlgorithm(allowRsa15);
    }

    public String getString(String key, Object mc) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        String s2 = this.getStringOption(key);
        if (s2 != null) {
            return s2;
        }
        if (mc == null) {
            throw new IllegalArgumentException("Message context cannot be null");
        }
        return (String)this.getProperty(mc, key);
    }

    public String getStringOption(String key) {
        Object o = this.getOption(key);
        if (o instanceof String) {
            return (String)o;
        }
        return null;
    }

    public ClassLoader getClassLoader(Object msgCtx) {
        try {
            return Loader.getTCL();
        }
        catch (Exception ex) {
            return null;
        }
    }

    public abstract Object getOption(String var1);

    public abstract Object getProperty(Object var1, String var2);

    public abstract void setProperty(Object var1, String var2, Object var3);

    public abstract String getPassword(Object var1);

    public abstract void setPassword(Object var1, String var2);
}

