/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message.token;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.derivedKey.ConversationConstants;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class SecurityContextToken {
    private Element element;
    private Element elementIdentifier;
    private Element elementInstance;
    private String tokenType = "http://schemas.xmlsoap.org/ws/2005/02/sc/sct";

    public SecurityContextToken(Document doc) throws WSSecurityException {
        this(2, doc);
    }

    public SecurityContextToken(Document doc, String uuid) throws WSSecurityException {
        this(2, doc, uuid);
    }

    public SecurityContextToken(int version, Document doc) throws WSSecurityException {
        String ns = ConversationConstants.getWSCNs(version);
        this.element = doc.createElementNS(ns, "wsc:SecurityContextToken");
        XMLUtils.setNamespace(this.element, ns, "wsc");
        this.elementIdentifier = doc.createElementNS(ns, "wsc:Identifier");
        this.element.appendChild(this.elementIdentifier);
        String uuid = IDGenerator.generateID("uuid:");
        this.elementIdentifier.appendChild(doc.createTextNode(uuid));
    }

    public SecurityContextToken(int version, Document doc, String uuid) throws WSSecurityException {
        String ns = ConversationConstants.getWSCNs(version);
        this.element = doc.createElementNS(ns, "wsc:SecurityContextToken");
        XMLUtils.setNamespace(this.element, ns, "wsc");
        this.elementIdentifier = doc.createElementNS(ns, "wsc:Identifier");
        this.element.appendChild(this.elementIdentifier);
        this.elementIdentifier.appendChild(doc.createTextNode(uuid));
        this.tokenType = version == 1 ? "http://schemas.xmlsoap.org/ws/2005/02/sc/sct" : "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct";
    }

    public SecurityContextToken(int version, Document doc, String uuid, String instance) throws WSSecurityException {
        this(version, doc, uuid);
        if (instance != null) {
            String ns = ConversationConstants.getWSCNs(version);
            this.elementInstance = doc.createElementNS(ns, "Instance");
            this.element.appendChild(this.elementInstance);
            this.elementInstance.appendChild(doc.createTextNode(instance));
        }
    }

    public SecurityContextToken(Element elem) throws WSSecurityException {
        this.element = elem;
        QName el = new QName(this.element.getNamespaceURI(), this.element.getLocalName());
        if (el.equals(ConversationConstants.SECURITY_CTX_TOKEN_QNAME_05_02)) {
            this.tokenType = "http://schemas.xmlsoap.org/ws/2005/02/sc/sct";
        } else if (el.equals(ConversationConstants.SECURITY_CTX_TOKEN_QNAME_05_12)) {
            this.tokenType = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct";
        } else {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN);
        }
        this.elementIdentifier = XMLUtils.getDirectChildElement(this.element, "Identifier", el.getNamespaceURI());
        this.elementInstance = XMLUtils.getDirectChildElement(this.element, "Instance", el.getNamespaceURI());
    }

    public void addWSUNamespace() {
        this.element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    }

    public void setIdentifier(String uuid) {
        Text node = this.getFirstNode(this.elementIdentifier);
        node.setData(uuid);
    }

    public String getIdentifier() {
        Text text;
        if (this.elementIdentifier != null && (text = this.getFirstNode(this.elementIdentifier)) != null) {
            return text.getData();
        }
        return null;
    }

    public String getInstance() {
        Text text;
        if (this.elementInstance != null && (text = this.getFirstNode(this.elementInstance)) != null) {
            return text.getData();
        }
        return null;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setElement(Element elem) {
        this.element.appendChild(elem);
    }

    private Text getFirstNode(Element e) {
        Node node = e.getFirstChild();
        return node != null && 3 == node.getNodeType() ? (Text)node : null;
    }

    public Element getElement() {
        return this.element;
    }

    public String toString() {
        return DOM2Writer.nodeToString(this.element);
    }

    public String getID() {
        return this.element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    public void setID(String id) {
        this.element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", id);
    }

    public int hashCode() {
        int result = 17;
        String identifier = this.getIdentifier();
        if (identifier != null) {
            result = 31 * result + identifier.hashCode();
        }
        return result;
    }

    public boolean equals(Object object) {
        if (!(object instanceof SecurityContextToken)) {
            return false;
        }
        SecurityContextToken securityToken = (SecurityContextToken)object;
        return this.compare(this.getIdentifier(), securityToken.getIdentifier());
    }

    private boolean compare(String item1, String item2) {
        if (item1 == null && item2 != null) {
            return false;
        }
        return item1 == null || item1.equals(item2);
    }
}

