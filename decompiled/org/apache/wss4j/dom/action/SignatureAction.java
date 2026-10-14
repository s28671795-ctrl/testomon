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
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SignatureAction
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
        WSPasswordCallback passwordCallback = handler.getPasswordCB(signatureToken.getUser(), 2, callbackHandler, reqData);
        WSSecSignature wsSign = new WSSecSignature(reqData.getSecHeader());
        wsSign.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        wsSign.setAddInclusivePrefixes(reqData.isAddInclusivePrefixes());
        wsSign.setWsDocInfo(reqData.getWsDocInfo());
        wsSign.setExpandXopInclude(reqData.isExpandXopInclude());
        wsSign.setSignatureProvider(reqData.getSignatureProvider());
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
        wsSign.setIncludeSignatureToken(signatureToken.isIncludeToken());
        wsSign.setUserInfo(signatureToken.getUser(), passwordCallback.getPassword());
        wsSign.setUseSingleCertificate(signatureToken.isUseSingleCert());
        if (passwordCallback.getKey() != null) {
            wsSign.setSecretKey(passwordCallback.getKey());
        } else if (signatureToken.getKey() != null) {
            wsSign.setSecretKey(signatureToken.getKey());
        } else if (signatureToken.getUser() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noSignatureUser");
        }
        if (signatureToken.getTokenId() != null) {
            wsSign.setCustomTokenId(signatureToken.getTokenId());
        }
        if (signatureToken.getTokenType() != null) {
            wsSign.setCustomTokenValueType(signatureToken.getTokenType());
        }
        if (signatureToken.getSha1Value() != null) {
            wsSign.setEncrKeySha1value(signatureToken.getSha1Value());
        }
        if (signatureToken.getKeyInfoElement() != null) {
            wsSign.setCustomKeyInfoElement(signatureToken.getKeyInfoElement());
        }
        wsSign.setAttachmentCallbackHandler(reqData.getAttachmentCallbackHandler());
        wsSign.setStoreBytesInAttachment(reqData.isStoreBytesInAttachment());
        try {
            List<WSEncryptionPart> parts;
            wsSign.prepare(signatureToken.getCrypto());
            Element siblingElementToPrepend = null;
            boolean signBST = false;
            for (WSEncryptionPart part : signatureToken.getParts()) {
                if ("STRTransform".equals(part.getName()) && part.getId() == null) {
                    part.setId(wsSign.getSecurityTokenReferenceURI());
                    continue;
                }
                if (reqData.isAppendSignatureAfterTimestamp() && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(part.getNamespace()) && "Timestamp".equals(part.getName())) {
                    int originalSignatureActionIndex = reqData.getOriginalSignatureActionPosition();
                    if (originalSignatureActionIndex <= 0) continue;
                    Element secHeader = reqData.getSecHeader().getSecurityHeaderElement();
                    Node lastChild = secHeader.getLastChild();
                    for (int count = 0; lastChild != null && count < originalSignatureActionIndex; ++count) {
                        while (lastChild != null && lastChild.getNodeType() != 1) {
                            lastChild = lastChild.getPreviousSibling();
                        }
                    }
                    if (!(lastChild instanceof Element)) continue;
                    siblingElementToPrepend = (Element)lastChild;
                    continue;
                }
                if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(part.getNamespace()) && "BinarySecurityToken".equals(part.getName())) {
                    signBST = true;
                    continue;
                }
                if (!"KeyInfo".equals(part.getName()) || !"http://www.w3.org/2000/09/xmldsig#".equals(part.getNamespace()) || part.getElement() != null) continue;
                Element keyInfoElement = wsSign.getKeyInfoElement();
                part.setElement(keyInfoElement);
                break;
            }
            if (signBST) {
                wsSign.prependBSTElementToHeader();
            }
            if ((parts = signatureToken.getParts()) == null || parts.isEmpty()) {
                parts = new ArrayList<WSEncryptionPart>(1);
                Document doc = reqData.getSecHeader().getSecurityHeaderElement().getOwnerDocument();
                parts.add(WSSecurityUtil.getDefaultEncryptionPart(doc));
            }
            List<Reference> referenceList = wsSign.addReferencesToSign(parts);
            if (signBST || reqData.isAppendSignatureAfterTimestamp() && siblingElementToPrepend == null) {
                wsSign.computeSignature(referenceList, false, null);
            } else {
                wsSign.computeSignature(referenceList, true, siblingElementToPrepend);
            }
            if (!signBST) {
                wsSign.prependBSTElementToHeader();
            }
            reqData.getSignatureValues().add(wsSign.getSignatureValue());
        }
        catch (WSSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"Error during Signature: "});
        }
    }
}

