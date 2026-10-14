/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import org.apache.wss4j.common.SignatureEncryptionActionToken;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.WSSecDerivedKeyBase;
import org.apache.wss4j.dom.message.WSSecEncryptedKey;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class AbstractDerivedAction {
    protected Node findEncryptedKeySibling(RequestData reqData) {
        Element secHeader = reqData.getSecHeader().getSecurityHeaderElement();
        return this.findSibling(secHeader, "http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");
    }

    protected Node findSCTSibling(RequestData reqData) {
        String namespace = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512";
        if (!reqData.isUse200512Namespace()) {
            namespace = "http://schemas.xmlsoap.org/ws/2005/02/sc";
        }
        Element secHeader = reqData.getSecHeader().getSecurityHeaderElement();
        return this.findSibling(secHeader, namespace, "SecurityContextToken");
    }

    protected Node findSibling(Element secHeader, String namespace, String localName) {
        if (secHeader == null) {
            return null;
        }
        for (Node firstChild = secHeader.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (!(firstChild instanceof Element) || !namespace.equals(((Element)firstChild).getNamespaceURI()) || !localName.equals(((Element)firstChild).getLocalName()) || firstChild.getNextSibling() == null) continue;
            return firstChild.getNextSibling();
        }
        return null;
    }

    protected Element setupSCTReference(WSSecDerivedKeyBase derivedKeyBase, WSPasswordCallback passwordCallback, SignatureEncryptionActionToken actionToken, SignatureEncryptionActionToken previousActionToken, boolean use200512Namespace, Document doc) throws WSSecurityException {
        if (use200512Namespace) {
            derivedKeyBase.setCustomValueType("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct");
        } else {
            derivedKeyBase.setCustomValueType("http://schemas.xmlsoap.org/ws/2005/02/sc/sct");
        }
        if (previousActionToken != null && previousActionToken.getKey() != null && previousActionToken.getKeyIdentifier() != null) {
            byte[] secret = previousActionToken.getKey();
            String tokenIdentifier = previousActionToken.getKeyIdentifier();
            derivedKeyBase.setExternalKey(secret, tokenIdentifier);
            return null;
        }
        String tokenIdentifier = IDGenerator.generateID("uuid:");
        derivedKeyBase.setExternalKey(passwordCallback.getKey(), tokenIdentifier);
        actionToken.setKey(passwordCallback.getKey());
        actionToken.setKeyIdentifier(tokenIdentifier);
        int version = 2;
        if (!use200512Namespace) {
            version = 1;
        }
        SecurityContextToken sct = new SecurityContextToken(version, doc, tokenIdentifier);
        return sct.getElement();
    }

    protected Element setupEKReference(WSSecDerivedKeyBase derivedKeyBase, WSSecHeader securityHeader, WSPasswordCallback passwordCallback, SignatureEncryptionActionToken actionToken, SignatureEncryptionActionToken previousActionToken, boolean use200512Namespace, Document doc, String keyTransportAlgorithm, String mgfAlgorithm) throws WSSecurityException {
        derivedKeyBase.setCustomValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
        if (previousActionToken != null && previousActionToken.getKey() != null && previousActionToken.getKeyIdentifier() != null) {
            byte[] ek = previousActionToken.getKey();
            String tokenIdentifier = previousActionToken.getKeyIdentifier();
            derivedKeyBase.setExternalKey(ek, tokenIdentifier);
            return null;
        }
        WSSecEncryptedKey encrKeyBuilder = new WSSecEncryptedKey(securityHeader);
        encrKeyBuilder.setUserInfo(actionToken.getUser());
        if (actionToken.getDerivedKeyIdentifier() != 0) {
            encrKeyBuilder.setKeyIdentifierType(actionToken.getDerivedKeyIdentifier());
        } else {
            encrKeyBuilder.setKeyIdentifierType(8);
        }
        if (actionToken.getDigestAlgorithm() != null) {
            encrKeyBuilder.setDigestAlgorithm(actionToken.getDigestAlgorithm());
        }
        if (keyTransportAlgorithm != null) {
            encrKeyBuilder.setKeyEncAlgo(keyTransportAlgorithm);
        }
        if (mgfAlgorithm != null) {
            encrKeyBuilder.setMGFAlgorithm(mgfAlgorithm);
        }
        encrKeyBuilder.prepare(actionToken.getCrypto());
        byte[] ek = encrKeyBuilder.getEphemeralKey();
        String tokenIdentifier = encrKeyBuilder.getId();
        actionToken.setKey(ek);
        actionToken.setKeyIdentifier(tokenIdentifier);
        derivedKeyBase.setExternalKey(ek, tokenIdentifier);
        return encrKeyBuilder.getEncryptedKeyElement();
    }
}

