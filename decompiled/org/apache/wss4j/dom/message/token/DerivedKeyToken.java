/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message.token;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.derivedKey.ConversationConstants;
import org.apache.wss4j.common.derivedKey.DerivedKeyUtils;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.WSDerivedKeyTokenPrincipal;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class DerivedKeyToken {
    private static final Logger LOG = LoggerFactory.getLogger(DerivedKeyToken.class);
    private Element element;
    private Element elementSecurityTokenReference;
    private Element elementProperties;
    private Element elementGeneration;
    private Element elementOffset;
    private Element elementLength;
    private Element elementLabel;
    private Element elementNonce;
    private String ns;
    private final BSPEnforcer bspEnforcer;

    public DerivedKeyToken(Document doc) throws WSSecurityException {
        this(2, doc);
    }

    public DerivedKeyToken(int version, Document doc) throws WSSecurityException {
        LOG.debug("DerivedKeyToken: created");
        this.ns = ConversationConstants.getWSCNs(version);
        this.element = doc.createElementNS(this.ns, "wsc:DerivedKeyToken");
        org.apache.wss4j.common.util.XMLUtils.setNamespace(this.element, this.ns, "wsc");
        this.bspEnforcer = new BSPEnforcer();
    }

    public DerivedKeyToken(Element elem, BSPEnforcer bspEnforcer) throws WSSecurityException {
        LOG.debug("DerivedKeyToken: created : element constructor");
        this.element = elem;
        this.bspEnforcer = bspEnforcer;
        QName el = new QName(this.element.getNamespaceURI(), this.element.getLocalName());
        if (!el.equals(ConversationConstants.DERIVED_KEY_TOKEN_QNAME_05_02) && !el.equals(ConversationConstants.DERIVED_KEY_TOKEN_QNAME_05_12)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN);
        }
        this.elementSecurityTokenReference = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "SecurityTokenReference", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        this.ns = el.getNamespaceURI();
        this.elementProperties = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Properties", this.ns);
        this.elementGeneration = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Generation", this.ns);
        this.elementOffset = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Offset", this.ns);
        this.elementLength = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Length", this.ns);
        this.elementLabel = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Label", this.ns);
        this.elementNonce = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(this.element, "Nonce", this.ns);
    }

    public void addWSUNamespace() {
        this.element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    }

    public void setSecurityTokenReference(SecurityTokenReference ref) {
        this.elementSecurityTokenReference = ref.getElement();
        WSSecurityUtil.prependChildElement(this.element, ref.getElement());
    }

    public void setSecurityTokenReference(Element elem) {
        this.elementSecurityTokenReference = elem;
        WSSecurityUtil.prependChildElement(this.element, elem);
    }

    public SecurityTokenReference getSecurityTokenReference() throws WSSecurityException {
        if (this.elementSecurityTokenReference != null) {
            return new SecurityTokenReference(this.elementSecurityTokenReference, this.bspEnforcer);
        }
        return null;
    }

    public Element getSecurityTokenReferenceElement() {
        return this.elementSecurityTokenReference;
    }

    private void addProperty(String propName, String propValue) {
        if (this.elementProperties == null) {
            this.elementProperties = this.element.getOwnerDocument().createElementNS(this.ns, "wsc:Properties");
            this.element.appendChild(this.elementProperties);
        }
        Element tempElement = this.element.getOwnerDocument().createElementNS(this.ns, "wsc:" + propName);
        tempElement.appendChild(this.element.getOwnerDocument().createTextNode(propValue));
        this.elementProperties.appendChild(tempElement);
    }

    public void setProperties(String name, String label, String nonce) {
        HashMap<String, String> table = new HashMap<String, String>();
        table.put("Name", name);
        table.put("Label", label);
        table.put("Nonce", nonce);
        this.setProperties(table);
    }

    public void setProperties(Map<String, String> properties) {
        if (properties != null && !properties.isEmpty()) {
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                String propertyName = entry.getValue();
                Element node = org.apache.wss4j.common.util.XMLUtils.findElement(this.elementProperties, propertyName, this.ns);
                if (node != null) {
                    Text node1 = this.getFirstNode(node);
                    if (node1 == null) continue;
                    node1.setData(properties.get(propertyName));
                    continue;
                }
                this.addProperty(propertyName, properties.get(propertyName));
            }
        }
    }

    public Map<String, String> getProperties() {
        if (this.elementProperties != null) {
            HashMap<String, String> table = new HashMap<String, String>();
            for (Node node = this.elementProperties.getFirstChild(); node != null; node = node.getNextSibling()) {
                Text text;
                if (1 != node.getNodeType() || (text = this.getFirstNode((Element)node)) == null) continue;
                table.put(node.getNodeName(), text.getData());
            }
            return table;
        }
        return null;
    }

    public void setLength(int length) {
        this.elementLength = this.element.getOwnerDocument().createElementNS(this.ns, "wsc:Length");
        this.elementLength.appendChild(this.element.getOwnerDocument().createTextNode(Long.toString(length)));
        this.element.appendChild(this.elementLength);
    }

    public int getLength() {
        Text text;
        if (this.elementLength != null && (text = this.getFirstNode(this.elementLength)) != null) {
            return Integer.parseInt(text.getData());
        }
        return 32;
    }

    public void setOffset(int offset) throws WSSecurityException {
        if (this.elementGeneration != null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "offsetError");
        }
        this.elementOffset = this.element.getOwnerDocument().createElementNS(this.ns, "wsc:Offset");
        this.elementOffset.appendChild(this.element.getOwnerDocument().createTextNode(Integer.toString(offset)));
        this.element.appendChild(this.elementOffset);
    }

    public int getOffset() {
        Text text;
        if (this.elementOffset != null && (text = this.getFirstNode(this.elementOffset)) != null) {
            return Integer.parseInt(text.getData());
        }
        return 0;
    }

    public void setGeneration(int generation) throws WSSecurityException {
        if (this.elementOffset != null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "offsetError");
        }
        this.elementGeneration = this.element.getOwnerDocument().createElementNS(this.ns, "wsc:Generation");
        this.elementGeneration.appendChild(this.element.getOwnerDocument().createTextNode(Integer.toString(generation)));
        this.element.appendChild(this.elementGeneration);
    }

    public int getGeneration() {
        Text text;
        if (this.elementGeneration != null && (text = this.getFirstNode(this.elementGeneration)) != null) {
            return Integer.parseInt(text.getData());
        }
        return -1;
    }

    public void setLabel(String label) {
        this.elementLabel = this.element.getOwnerDocument().createElementNS(this.ns, "wsc:Label");
        this.elementLabel.appendChild(this.element.getOwnerDocument().createTextNode(label));
        this.element.appendChild(this.elementLabel);
    }

    public void setNonce(String nonce) {
        this.elementNonce = this.element.getOwnerDocument().createElementNS(this.ns, "wsc:Nonce");
        this.elementNonce.appendChild(this.element.getOwnerDocument().createTextNode(nonce));
        this.element.appendChild(this.elementNonce);
    }

    public String getLabel() {
        Text text;
        if (this.elementLabel != null && (text = this.getFirstNode(this.elementLabel)) != null) {
            return text.getData();
        }
        return null;
    }

    public String getNonce() {
        Text text;
        if (this.elementNonce != null && (text = this.getFirstNode(this.elementNonce)) != null) {
            return text.getData();
        }
        return null;
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

    public String getAlgorithm() {
        String algo = this.element.getAttributeNS(this.ns, "Algorithm");
        if ("".equals(algo)) {
            return "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1";
        }
        return algo;
    }

    public Principal createPrincipal() throws WSSecurityException {
        WSDerivedKeyTokenPrincipal principal = new WSDerivedKeyTokenPrincipal(this.getID());
        principal.setNonce(this.getNonce());
        principal.setLabel(this.getLabel());
        principal.setLength(this.getLength());
        principal.setOffset(this.getOffset());
        principal.setAlgorithm(this.getAlgorithm());
        String basetokenId = null;
        SecurityTokenReference securityTokenReference = this.getSecurityTokenReference();
        if (securityTokenReference != null && securityTokenReference.getReference() != null) {
            basetokenId = securityTokenReference.getReference().getURI();
            basetokenId = org.apache.wss4j.common.util.XMLUtils.getIDFromReference(basetokenId);
        } else if (securityTokenReference != null) {
            basetokenId = securityTokenReference.getKeyIdentifierValue();
        }
        principal.setBasetokenId(basetokenId);
        return principal;
    }

    public void setAlgorithm(String algo) {
        if (algo != null) {
            this.element.setAttributeNS(this.ns, "Algorithm", algo);
        }
    }

    public byte[] deriveKey(int length, byte[] secret) throws WSSecurityException {
        try {
            byte[] nonce = XMLUtils.decode(this.getNonce());
            return DerivedKeyUtils.deriveKey(this.getAlgorithm(), this.getLabel(), length, secret, nonce, this.getOffset());
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e);
        }
    }

    public int hashCode() {
        String nonce;
        String label;
        int length;
        int offset;
        int generation;
        int result = 17;
        String algorithm = this.getAlgorithm();
        if (algorithm != null) {
            result = 31 * result + algorithm.hashCode();
        }
        try {
            SecurityTokenReference tokenReference = this.getSecurityTokenReference();
            if (tokenReference != null) {
                result = 31 * result + tokenReference.hashCode();
            }
        }
        catch (WSSecurityException e) {
            LOG.error(e.getMessage(), e);
        }
        Map<String, String> properties = this.getProperties();
        if (properties != null) {
            result = 31 * result + properties.hashCode();
        }
        if ((generation = this.getGeneration()) != -1) {
            result = 31 * result + generation;
        }
        if ((offset = this.getOffset()) != -1) {
            result = 31 * result + offset;
        }
        if ((length = this.getLength()) != -1) {
            result = 31 * result + length;
        }
        if ((label = this.getLabel()) != null) {
            result = 31 * result + label.hashCode();
        }
        if ((nonce = this.getNonce()) != null) {
            result = 31 * result + nonce.hashCode();
        }
        return result;
    }

    public boolean equals(Object object) {
        if (!(object instanceof DerivedKeyToken)) {
            return false;
        }
        DerivedKeyToken token = (DerivedKeyToken)object;
        if (!this.compare(this.getAlgorithm(), token.getAlgorithm())) {
            return false;
        }
        try {
            if (this.getSecurityTokenReference() != null && !this.getSecurityTokenReference().equals(token.getSecurityTokenReference()) || this.getSecurityTokenReference() == null && token.getSecurityTokenReference() != null) {
                return false;
            }
        }
        catch (WSSecurityException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        if (!this.compare(this.getProperties(), token.getProperties())) {
            return false;
        }
        if (this.getGeneration() != token.getGeneration()) {
            return false;
        }
        if (this.getOffset() != token.getOffset()) {
            return false;
        }
        if (this.getLength() != token.getLength()) {
            return false;
        }
        if (!this.compare(this.getLabel(), token.getLabel())) {
            return false;
        }
        return this.compare(this.getNonce(), token.getNonce());
    }

    private boolean compare(String item1, String item2) {
        if (item1 == null && item2 != null) {
            return false;
        }
        return item1 == null || item1.equals(item2);
    }

    private boolean compare(Map<String, String> item1, Map<String, String> item2) {
        if (item1 == null && item2 != null) {
            return false;
        }
        return item1 == null || item1.equals(item2);
    }
}

