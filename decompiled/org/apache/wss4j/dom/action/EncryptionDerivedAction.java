/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import java.util.List;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.EncryptionActionToken;
import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.action.AbstractDerivedAction;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;
import org.apache.wss4j.dom.message.WSSecDKEncrypt;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class EncryptionDerivedAction
extends AbstractDerivedAction
implements Action {
    @Override
    public void execute(WSHandler handler, SecurityActionToken actionToken, RequestData reqData) throws WSSecurityException {
        CallbackHandler callbackHandler = reqData.getCallbackHandler();
        if (callbackHandler == null) {
            callbackHandler = handler.getPasswordCallbackHandler(reqData);
        }
        EncryptionActionToken encryptionToken = null;
        if (actionToken instanceof EncryptionActionToken) {
            encryptionToken = (EncryptionActionToken)actionToken;
        }
        if (encryptionToken == null) {
            encryptionToken = reqData.getEncryptionToken();
        }
        WSPasswordCallback passwordCallback = handler.getPasswordCB(encryptionToken.getUser(), 65536, callbackHandler, reqData);
        WSSecDKEncrypt wsEncrypt = new WSSecDKEncrypt(reqData.getSecHeader());
        wsEncrypt.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        wsEncrypt.setWsDocInfo(reqData.getWsDocInfo());
        wsEncrypt.setExpandXopInclude(reqData.isExpandXopInclude());
        if (encryptionToken.getKeyIdentifierId() != 0) {
            wsEncrypt.setKeyIdentifierType(encryptionToken.getKeyIdentifierId());
        }
        if (encryptionToken.getSymmetricAlgorithm() != null) {
            wsEncrypt.setSymmetricEncAlgorithm(encryptionToken.getSymmetricAlgorithm());
        }
        wsEncrypt.setUserInfo(encryptionToken.getUser(), passwordCallback.getPassword());
        if (reqData.isUse200512Namespace()) {
            wsEncrypt.setWscVersion(2);
        } else {
            wsEncrypt.setWscVersion(1);
        }
        if (encryptionToken.getDerivedKeyLength() > 0) {
            wsEncrypt.setDerivedKeyLength(encryptionToken.getDerivedKeyLength());
        }
        Document doc = reqData.getSecHeader().getSecurityHeaderElement().getOwnerDocument();
        Element tokenElement = this.setupTokenReference(reqData, encryptionToken, wsEncrypt, passwordCallback, doc);
        wsEncrypt.setAttachmentCallbackHandler(reqData.getAttachmentCallbackHandler());
        wsEncrypt.setStoreBytesInAttachment(reqData.isStoreBytesInAttachment());
        try {
            List<WSEncryptionPart> parts = encryptionToken.getParts();
            if (parts != null && !parts.isEmpty()) {
                wsEncrypt.getParts().addAll(parts);
            } else {
                wsEncrypt.getParts().add(WSSecurityUtil.getDefaultEncryptionPart(doc));
            }
            wsEncrypt.prepare();
            Element externRefList = wsEncrypt.encrypt();
            Node nextSibling = null;
            if (tokenElement == null && "EncryptedKey".equals(encryptionToken.getDerivedKeyTokenReference())) {
                nextSibling = this.findEncryptedKeySibling(reqData);
            } else if (tokenElement == null && "SecurityContextToken".equals(encryptionToken.getDerivedKeyTokenReference())) {
                nextSibling = this.findSCTSibling(reqData);
            }
            if (nextSibling == null) {
                wsEncrypt.prependDKElementToHeader();
            } else {
                reqData.getSecHeader().getSecurityHeaderElement().insertBefore(wsEncrypt.getdktElement(), nextSibling);
            }
            wsEncrypt.addExternalRefElement(externRefList);
            if (tokenElement != null) {
                WSSecurityUtil.prependChildElement(reqData.getSecHeader().getSecurityHeaderElement(), tokenElement);
            }
        }
        catch (WSSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"Error during Encryption: "});
        }
    }

    private Element setupTokenReference(RequestData reqData, EncryptionActionToken encryptionToken, WSSecDKEncrypt wsEncrypt, WSPasswordCallback passwordCallback, Document doc) throws WSSecurityException {
        String derivedKeyTokenReference = encryptionToken.getDerivedKeyTokenReference();
        if ("SecurityContextToken".equals(derivedKeyTokenReference)) {
            return this.setupSCTReference(wsEncrypt, passwordCallback, encryptionToken, reqData.getSignatureToken(), reqData.isUse200512Namespace(), doc);
        }
        return this.setupEKReference(wsEncrypt, reqData.getSecHeader(), passwordCallback, encryptionToken, reqData.getSignatureToken(), reqData.isUse200512Namespace(), doc, encryptionToken.getKeyTransportAlgorithm(), encryptionToken.getMgfAlgorithm());
    }
}

