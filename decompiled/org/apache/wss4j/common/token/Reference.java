/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.token;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.common.util.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Reference {
    public static final QName TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Reference");
    private Element element;

    public Reference(Element elem) throws WSSecurityException {
        if (elem == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noReference");
        }
        this.element = elem;
        QName el = new QName(this.element.getNamespaceURI(), this.element.getLocalName());
        if (!el.equals(TOKEN)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "badElement", new Object[]{TOKEN, el});
        }
        String uri = this.getURI();
        if (uri == null || "".equals(uri)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "badReferenceURI");
        }
    }

    public Reference(Document doc) {
        this.element = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Reference");
    }

    public void addWSSENamespace() {
        XMLUtils.setNamespace(this.element, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse");
    }

    public Element getElement() {
        return this.element;
    }

    public String getValueType() {
        return this.element.getAttributeNS(null, "ValueType");
    }

    public String getURI() {
        return this.element.getAttributeNS(null, "URI");
    }

    public void setValueType(String valueType) {
        if (valueType != null) {
            this.element.setAttributeNS(null, "ValueType", valueType);
        }
    }

    public void setURI(String uri) {
        this.element.setAttributeNS(null, "URI", uri);
    }

    public String toString() {
        return DOM2Writer.nodeToString(this.element);
    }

    public int hashCode() {
        String valueType;
        int result = 17;
        String uri = this.getURI();
        if (uri != null) {
            result = 31 * result + uri.hashCode();
        }
        if ((valueType = this.getValueType()) != null) {
            result = 31 * result + valueType.hashCode();
        }
        return result;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Reference)) {
            return false;
        }
        Reference reference = (Reference)object;
        if (!this.compare(this.getURI(), reference.getURI())) {
            return false;
        }
        return this.compare(this.getValueType(), reference.getValueType());
    }

    private boolean compare(String item1, String item2) {
        if (item1 == null && item2 != null) {
            return false;
        }
        return item1 == null || item1.equals(item2);
    }
}

