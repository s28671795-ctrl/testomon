/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;
import org.apache.wss4j.dom.message.WSSecSAMLToken;

public class SAMLTokenUnsignedAction
implements Action {
    @Override
    public void execute(WSHandler handler, SecurityActionToken actionToken, RequestData reqData) throws WSSecurityException {
        WSSecSAMLToken builder = new WSSecSAMLToken(reqData.getSecHeader());
        builder.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        builder.setWsDocInfo(reqData.getWsDocInfo());
        builder.setExpandXopInclude(reqData.isExpandXopInclude());
        CallbackHandler samlCallbackHandler = handler.getCallbackHandler("samlCallbackClass", "samlCallbackRef", reqData);
        if (samlCallbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noSAMLCallbackHandler");
        }
        SAMLCallback samlCallback = new SAMLCallback();
        SAMLUtil.doSAMLCallback(samlCallbackHandler, samlCallback);
        SamlAssertionWrapper samlAssertion = new SamlAssertionWrapper(samlCallback);
        if (samlCallback.isSignAssertion()) {
            samlAssertion.signAssertion(samlCallback.getIssuerKeyName(), samlCallback.getIssuerKeyPassword(), samlCallback.getIssuerCrypto(), samlCallback.isSendKeyValue(), samlCallback.getCanonicalizationAlgorithm(), samlCallback.getSignatureAlgorithm());
        }
        builder.build(samlAssertion);
        byte[] signatureValue = samlAssertion.getSignatureValue();
        if (signatureValue != null) {
            reqData.getSignatureValues().add(signatureValue);
        }
    }
}

