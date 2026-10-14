/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import java.util.List;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.crypto.dsig.Reference;
import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.SignatureActionToken;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.action.AbstractDerivedAction;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;
import org.apache.wss4j.dom.message.WSSecDKSign;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SignatureDerivedAction
extends AbstractDerivedAction
implements Action {
    @Override
    public void execute(WSHandler handler, SecurityActionToken actionToken, RequestData reqData) throws WSSecurityException {
        CallbackHandler callbackHandler = reqData.getCallbackHandler();
        if (callbackHandler == null) {
            callbackHandler = handler.getPasswordCallbackHandler(reqData);
        }
        SignatureActionToken signatureToken = null;
        if (actionToken instanceof SignatureActionToken) {
            signatureToken = (SignatureActionToken)actionToken;
        }
        if (signatureToken == null) {
            signatureToken = reqData.getSignatureToken();
        }
        WSPasswordCallback passwordCallback = handler.getPasswordCB(signatureToken.getUser(), 32768, callbackHandler, reqData);
        WSSecDKSign wsSign = new WSSecDKSign(reqData.getSecHeader());
        wsSign.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        wsSign.setAddInclusivePrefixes(reqData.isAddInclusivePrefixes());
        wsSign.setWsDocInfo(reqData.getWsDocInfo());
        wsSign.setExpandXopInclude(reqData.isExpandXopInclude());
        if (signatureToken.getSignatureAlgorithm() != null) {
            wsSign.setSignatureAlgorithm(signatureToken.getSignatureAlgorithm());
        }
        if (signatureToken.getDigestAlgorithm() != null) {
            wsSign.setDigestAlgorithm(signatureToken.getDigestAlgorithm());
        }
        if (signatureToken.getC14nAlgorithm() != null) {
            wsSign.setSigCanonicalization(signatureToken.getC14nAlgorithm());
        }
        wsSign.setUserInfo(signatureToken.getUser(), passwordCallback.getPassword());
        if (reqData.isUse200512Namespace()) {
            wsSign.setWscVersion(2);
        } else {
            wsSign.setWscVersion(1);
        }
        if (signatureToken.getDerivedKeyLength() > 0) {
            wsSign.setDerivedKeyLength(signatureToken.getDerivedKeyLength());
        }
        Document doc = reqData.getSecHeader().getSecurityHeaderElement().getOwnerDocument();
        Element tokenElement = this.setupTokenReference(reqData, signatureToken, wsSign, passwordCallback, doc);
        wsSign.setAttachmentCallbackHandler(reqData.getAttachmentCallbackHandler());
        wsSign.setStoreBytesInAttachment(reqData.isStoreBytesInAttachment());
        try {
            List<WSEncryptionPart> parts = signatureToken.getParts();
            if (parts != null && !parts.isEmpty()) {
                wsSign.getParts().addAll(parts);
            } else {
                wsSign.getParts().add(WSSecurityUtil.getDefaultEncryptionPart(doc));
            }
            wsSign.prepare();
            List<Reference> referenceList = wsSign.addReferencesToSign(wsSign.getParts());
            Node nextSibling = null;
            if (tokenElement == null && "EncryptedKey".equals(signatureToken.getDerivedKeyTokenReference())) {
                nextSibling = this.findEncryptedKeySibling(reqData);
            } else if (tokenElement == null && "SecurityContextToken".equals(signatureToken.getDerivedKeyTokenReference())) {
                nextSibling = this.findSCTSibling(reqData);
            }
            if (nextSibling == null) {
                wsSign.computeSignature(referenceList);
            } else {
                wsSign.computeSignature(referenceList, true, (Element)nextSibling);
            }
            if (nextSibling == null) {
                wsSign.prependDKElementToHeader();
            } else {
                reqData.getSecHeader().getSecurityHeaderElement().insertBefore(wsSign.getdktElement(), wsSign.getSignatureElement());
            }
            if (tokenElement != null) {
                WSSecurityUtil.prependChildElement(reqData.getSecHeader().getSecurityHeaderElement(), tokenElement);
            }
            reqData.getSignatureValues().add(wsSign.getSignatureValue());
        }
        catch (WSSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"Error during Signature: "});
        }
    }

    private Element setupTokenReference(RequestData reqData, SignatureActionToken signatureToken, WSSecDKSign wsSign, WSPasswordCallback passwordCallback, Document doc) throws WSSecurityException {
        String derivedKeyTokenReference = signatureToken.getDerivedKeyTokenReference();
        if ("EncryptedKey".equals(derivedKeyTokenReference)) {
            return this.setupEKReference(wsSign, reqData.getSecHeader(), passwordCallback, signatureToken, reqData.getEncryptionToken(), reqData.isUse200512Namespace(), doc, null, null);
        }
        if ("SecurityContextToken".equals(derivedKeyTokenReference)) {
            return this.setupSCTReference(wsSign, passwordCallback, signatureToken, reqData.getEncryptionToken(), reqData.isUse200512Namespace(), doc);
        }
        if (signatureToken.getDerivedKeyIdentifier() != 0) {
            wsSign.setKeyIdentifierType(signatureToken.getDerivedKeyIdentifier());
        } else {
            wsSign.setKeyIdentifierType(8);
        }
        byte[] key = null;
        if (passwordCallback.getKey() != null) {
            key = passwordCallback.getKey();
        } else if (signatureToken.getKey() != null) {
            key = signatureToken.getKey();
        } else if (signatureToken.getCrypto() != null) {
            Crypto crypto = signatureToken.getCrypto();
            key = crypto.getPrivateKey(signatureToken.getUser(), passwordCallback.getPassword()).getEncoded();
        }
        wsSign.setCrypto(signatureToken.getCrypto());
        wsSign.setExternalKey(key, (String)null);
        return null;
    }
}

