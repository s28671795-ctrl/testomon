/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.SignatureActionToken;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;
import org.apache.wss4j.dom.saml.WSSecSignatureSAML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLTokenSignedAction
implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(SAMLTokenSignedAction.class);

    @Override
    public void execute(WSHandler handler, SecurityActionToken actionToken, RequestData reqData) throws WSSecurityException {
        Crypto crypto = null;
        try {
            crypto = handler.loadSignatureCrypto(reqData);
        }
        catch (Exception ex) {
            LOG.debug(ex.getMessage(), ex);
        }
        CallbackHandler samlCallbackHandler = handler.getCallbackHandler("samlCallbackClass", "samlCallbackRef", reqData);
        if (samlCallbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noSAMLCallbackHandler");
        }
        SAMLCallback samlCallback = new SAMLCallback();
        SAMLUtil.doSAMLCallback(samlCallbackHandler, samlCallback);
        SamlAssertionWrapper samlAssertion = new SamlAssertionWrapper(samlCallback);
        if (samlCallback.isSignAssertion()) {
            samlAssertion.signAssertion(samlCallback.getIssuerKeyName(), samlCallback.getIssuerKeyPassword(), samlCallback.getIssuerCrypto(), samlCallback.isSendKeyValue(), samlCallback.getCanonicalizationAlgorithm(), samlCallback.getSignatureAlgorithm(), samlCallback.getSignatureDigestAlgorithm());
        }
        WSSecSignatureSAML wsSign = new WSSecSignatureSAML(reqData.getSecHeader());
        wsSign.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        wsSign.setAddInclusivePrefixes(reqData.isAddInclusivePrefixes());
        wsSign.setWsDocInfo(reqData.getWsDocInfo());
        wsSign.setExpandXopInclude(reqData.isExpandXopInclude());
        wsSign.setSignatureProvider(reqData.getSignatureProvider());
        CallbackHandler callbackHandler = handler.getPasswordCallbackHandler(reqData);
        SignatureActionToken signatureToken = null;
        if (actionToken instanceof SignatureActionToken) {
            signatureToken = (SignatureActionToken)actionToken;
        }
        if (signatureToken == null) {
            signatureToken = reqData.getSignatureToken();
        }
        WSPasswordCallback passwordCallback = handler.getPasswordCB(signatureToken.getUser(), 16, callbackHandler, reqData);
        wsSign.setUserInfo(signatureToken.getUser(), passwordCallback.getPassword());
        if (signatureToken.getKeyIdentifierId() != 0) {
            wsSign.setKeyIdentifierType(signatureToken.getKeyIdentifierId());
        }
        if (signatureToken.getSignatureAlgorithm() != null) {
            wsSign.setSignatureAlgorithm(signatureToken.getSignatureAlgorithm());
        }
        if (signatureToken.getDigestAlgorithm() != null) {
            wsSign.setDigestAlgo(signatureToken.getDigestAlgorithm());
        }
        if (signatureToken.getC14nAlgorithm() != null) {
            wsSign.setSigCanonicalization(signatureToken.getC14nAlgorithm());
        }
        if (signatureToken.getKeyInfoElement() != null) {
            wsSign.setCustomKeyInfoElement(signatureToken.getKeyInfoElement());
        }
        if (!signatureToken.getParts().isEmpty()) {
            wsSign.getParts().addAll(signatureToken.getParts());
        }
        try {
            wsSign.build(crypto, samlAssertion, samlCallback.getIssuerCrypto(), samlCallback.getIssuerKeyName(), samlCallback.getIssuerKeyPassword());
            reqData.getSignatureValues().add(wsSign.getSignatureValue());
            byte[] signatureValue = samlAssertion.getSignatureValue();
            if (signatureValue != null) {
                reqData.getSignatureValues().add(signatureValue);
            }
        }
        catch (WSSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"Error when signing the SAML token: "});
        }
    }
}

