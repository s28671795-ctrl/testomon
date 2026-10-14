/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import java.util.ArrayList;
import java.util.List;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.crypto.dsig.Reference;
import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.SignatureActionToken;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;
import org.apache.wss4j.dom.message.WSSecSignature;
import org.apache.wss4j.dom.message.WSSecUsernameToken;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Document;

public class UsernameTokenSignedAction
implements Action {
    @Override
    public void execute(WSHandler handler, SecurityActionToken actionToken, RequestData reqData) throws WSSecurityException {
        CallbackHandler callbackHandler = reqData.getCallbackHandler();
        if (callbackHandler == null) {
            callbackHandler = handler.getPasswordCallbackHandler(reqData);
        }
        WSPasswordCallback passwordCallback = handler.getPasswordCB(reqData.getUsername(), 64, callbackHandler, reqData);
        if (reqData.getUsername() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noUser");
        }
        WSSecUsernameToken builder = new WSSecUsernameToken(reqData.getSecHeader());
        builder.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        builder.setPrecisionInMilliSeconds(reqData.isPrecisionInMilliSeconds());
        builder.setWsTimeSource(reqData.getWssConfig().getCurrentTime());
        builder.setWsDocInfo(reqData.getWsDocInfo());
        builder.setExpandXopInclude(reqData.isExpandXopInclude());
        int iterations = reqData.getDerivedKeyIterations();
        boolean useMac = reqData.isUseDerivedKeyForMAC();
        builder.addDerivedKey(useMac, null, iterations);
        builder.setUserInfo(reqData.getUsername(), passwordCallback.getPassword());
        builder.addCreated();
        builder.addNonce();
        builder.prepare();
        SignatureActionToken signatureToken = null;
        if (actionToken instanceof SignatureActionToken) {
            signatureToken = (SignatureActionToken)actionToken;
        }
        if (signatureToken == null) {
            signatureToken = reqData.getSignatureToken();
        }
        WSSecSignature sign = new WSSecSignature(reqData.getSecHeader());
        sign.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        sign.setAddInclusivePrefixes(reqData.isAddInclusivePrefixes());
        sign.setCustomTokenValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
        sign.setCustomTokenId(builder.getId());
        sign.setSecretKey(builder.getDerivedKey());
        sign.setKeyIdentifierType(9);
        if (signatureToken.getDigestAlgorithm() != null) {
            sign.setDigestAlgo(signatureToken.getDigestAlgorithm());
        }
        if (signatureToken.getSignatureAlgorithm() != null) {
            sign.setSignatureAlgorithm(signatureToken.getSignatureAlgorithm());
        } else {
            sign.setSignatureAlgorithm("http://www.w3.org/2000/09/xmldsig#hmac-sha1");
        }
        sign.prepare(null);
        List<WSEncryptionPart> parts = null;
        if (!signatureToken.getParts().isEmpty()) {
            parts = signatureToken.getParts();
        } else {
            parts = new ArrayList<WSEncryptionPart>(1);
            Document doc = reqData.getSecHeader().getSecurityHeaderElement().getOwnerDocument();
            parts.add(WSSecurityUtil.getDefaultEncryptionPart(doc));
        }
        List<Reference> referenceList = sign.addReferencesToSign(parts);
        try {
            sign.computeSignature(referenceList);
            reqData.getSignatureValues().add(sign.getSignatureValue());
        }
        catch (WSSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"WSHandler: Error during UsernameTokenSignature"});
        }
        builder.prependToHeader();
    }
}

