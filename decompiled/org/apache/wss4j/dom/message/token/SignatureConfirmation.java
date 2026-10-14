/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message.token;

import java.util.Arrays;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SignatureConfirmation {
    public static final String SC_VALUE_ATTR = "Value";
    private Element element;
    private byte[] signatureValue;

    public SignatureConfirmation(Element elem, BSPEnforcer bspEnforcer) throws WSSecurityException {
        String sv;
        this.element = elem;
        String id = this.getID();
        if (id == null || "".equals(id)) {
            bspEnforcer.handleBSPRule(BSPRule.R5441);
        }
        if ((sv = this.element.getAttributeNS(null, SC_VALUE_ATTR)) != null) {
            this.signatureValue = XMLUtils.decode(sv);
        }
    }

    public SignatureConfirmation(Document doc, byte[] signVal) {
        this.element = doc.createElementNS("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11:SignatureConfirmation");
        org.apache.wss4j.common.util.XMLUtils.setNamespace(this.element, "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11");
        if (signVal != null) {
            String sv = XMLUtils.encodeToString(signVal);
            this.element.setAttributeNS(null, SC_VALUE_ATTR, sv);
        }
    }

    public void addWSUNamespace() {
        this.element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    }

    public Element getElement() {
        return this.element;
    }

    public String toString() {
        return DOM2Writer.nodeToString(this.element);
    }

    public void setID(String id) {
        this.element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", id);
    }

    public String getID() {
        return this.element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    public byte[] getSignatureValue() {
        return this.signatureValue;
    }

    public int hashCode() {
        int result = 17;
        if (this.signatureValue != null) {
            result = 31 * result + Arrays.hashCode(this.signatureValue);
        }
        return result;
    }

    public boolean equals(Object object) {
        if (!(object instanceof SignatureConfirmation)) {
            return false;
        }
        SignatureConfirmation signatureConfirmation = (SignatureConfirmation)object;
        byte[] sigValue = signatureConfirmation.getSignatureValue();
        return Arrays.equals(sigValue, this.getSignatureValue());
    }
}

