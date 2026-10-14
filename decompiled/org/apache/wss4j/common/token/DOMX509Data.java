/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.token;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.DOMX509IssuerSerial;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.common.util.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class DOMX509Data {
    private final Element element;
    private DOMX509IssuerSerial x509IssuerSerial;

    public DOMX509Data(Element x509DataElement) throws WSSecurityException {
        this.element = x509DataElement;
        Element issuerSerialElement = XMLUtils.getDirectChildElement(this.element, "X509IssuerSerial", "http://www.w3.org/2000/09/xmldsig#");
        this.x509IssuerSerial = new DOMX509IssuerSerial(issuerSerialElement);
    }

    public DOMX509Data(Document doc, DOMX509IssuerSerial domIssuerSerial) {
        this.element = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:X509Data");
        this.element.appendChild(domIssuerSerial.getElement());
    }

    public boolean containsIssuerSerial() {
        return this.x509IssuerSerial != null;
    }

    public DOMX509IssuerSerial getIssuerSerial() {
        return this.x509IssuerSerial;
    }

    public Element getElement() {
        return this.element;
    }

    public String toString() {
        return DOM2Writer.nodeToString(this.element);
    }
}

