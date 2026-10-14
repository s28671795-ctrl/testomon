/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import java.util.ArrayList;
import java.util.List;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.WsuIdAllocator;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.callback.DOMCallbackLookup;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSSecBase {
    protected String user;
    protected String password;
    protected int keyIdentifierType = 2;
    protected CallbackLookup callbackLookup;
    protected CallbackHandler attachmentCallbackHandler;
    protected boolean storeBytesInAttachment;
    protected boolean expandXopInclude;
    protected boolean addWSUNamespace;
    private WsuIdAllocator idAllocator;
    private final List<WSEncryptionPart> parts = new ArrayList<WSEncryptionPart>();
    private final WSSecHeader securityHeader;
    private final Document doc;
    private WSDocInfo wsDocInfo;

    public WSSecBase(WSSecHeader securityHeader) {
        this.securityHeader = securityHeader;
        this.doc = securityHeader != null && securityHeader.getSecurityHeaderElement() != null ? securityHeader.getSecurityHeaderElement().getOwnerDocument() : null;
        this.addWSUNamespace = securityHeader.getWsuPrefix() != null && !"wsu".equals(securityHeader.getWsuPrefix());
    }

    public WSSecBase(Document doc) {
        this.doc = doc;
        this.securityHeader = new WSSecHeader(doc);
    }

    protected Document getDocument() {
        return this.doc;
    }

    public WSSecHeader getSecurityHeader() {
        return this.securityHeader;
    }

    public void setCallbackLookup(CallbackLookup callbackLookup) {
        this.callbackLookup = callbackLookup;
    }

    public List<WSEncryptionPart> getParts() {
        return this.parts;
    }

    public void setKeyIdentifierType(int keyIdType) {
        this.keyIdentifierType = keyIdType;
    }

    public int getKeyIdentifierType() {
        return this.keyIdentifierType;
    }

    public void setAttachmentCallbackHandler(CallbackHandler attachmentCallbackHandler) {
        this.attachmentCallbackHandler = attachmentCallbackHandler;
    }

    public void setStoreBytesInAttachment(boolean storeBytesInAttachment) {
        this.storeBytesInAttachment = storeBytesInAttachment;
    }

    protected String setBodyID(Document doc) throws Exception {
        Element bodyElement;
        if (this.callbackLookup == null) {
            this.callbackLookup = new DOMCallbackLookup(doc);
        }
        if ((bodyElement = this.callbackLookup.getSOAPBody()) == null) {
            throw new Exception("SOAP Body Element node not found");
        }
        return this.setWsuId(bodyElement);
    }

    protected String setWsuId(Element bodyElement) {
        String id = bodyElement.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
        String newAttrNs = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
        String newAttrPrefix = "wsu";
        if (id == null || id.length() == 0) {
            if ("http://www.w3.org/2001/04/xmlenc#".equals(bodyElement.getNamespaceURI()) && ("EncryptedData".equals(bodyElement.getLocalName()) || "EncryptedKey".equals(bodyElement.getLocalName()))) {
                id = bodyElement.getAttributeNS(null, "Id");
                newAttrPrefix = "xenc";
                newAttrNs = "http://www.w3.org/2001/04/xmlenc#";
            } else if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(bodyElement.getNamespaceURI()) && "Assertion".equals(bodyElement.getLocalName())) {
                id = bodyElement.getAttributeNS(null, "AssertionID");
            } else if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(bodyElement.getNamespaceURI()) && "Assertion".equals(bodyElement.getLocalName())) {
                id = bodyElement.getAttributeNS(null, "ID");
            } else if ("http://www.w3.org/2000/09/xmldsig#".equals(bodyElement.getNamespaceURI()) && "KeyInfo".equals(bodyElement.getLocalName())) {
                id = bodyElement.getAttributeNS(null, "Id");
            }
        }
        if (id == null || id.length() == 0) {
            id = this.getIdAllocator().createId("id-", bodyElement);
            String prefix = XMLUtils.setNamespace(bodyElement, newAttrNs, newAttrPrefix);
            bodyElement.setAttributeNS(newAttrNs, prefix + ":Id", id);
        }
        return id;
    }

    public void setUserInfo(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public WsuIdAllocator getIdAllocator() {
        if (this.idAllocator != null) {
            return this.idAllocator;
        }
        return WSSConfig.DEFAULT_ID_ALLOCATOR;
    }

    public void setIdAllocator(WsuIdAllocator idAllocator) {
        this.idAllocator = idAllocator;
    }

    public boolean isExpandXopInclude() {
        return this.expandXopInclude;
    }

    public void setExpandXopInclude(boolean expandXopInclude) {
        this.expandXopInclude = expandXopInclude;
    }

    public WSDocInfo getWsDocInfo() {
        return this.wsDocInfo;
    }

    public void setWsDocInfo(WSDocInfo wsDocInfo) {
        this.wsDocInfo = wsDocInfo;
    }
}

