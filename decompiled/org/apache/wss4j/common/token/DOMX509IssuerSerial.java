/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.token;

import java.math.BigInteger;
import javax.security.auth.x500.X500Principal;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.common.util.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class DOMX509IssuerSerial {
    private final Element element;
    private final String issuer;
    private final BigInteger serialNumber;

    public DOMX509IssuerSerial(Element issuerSerialElement) {
        this.element = issuerSerialElement;
        Element issuerNameElement = XMLUtils.getDirectChildElement(this.element, "X509IssuerName", "http://www.w3.org/2000/09/xmldsig#");
        this.issuer = XMLUtils.getElementText(issuerNameElement);
        Element serialNumberElement = XMLUtils.getDirectChildElement(this.element, "X509SerialNumber", "http://www.w3.org/2000/09/xmldsig#");
        String serialNumberStr = XMLUtils.getElementText(serialNumberElement);
        this.serialNumber = serialNumberStr != null ? new BigInteger(serialNumberStr) : null;
    }

    public DOMX509IssuerSerial(Document doc, String issuer, BigInteger serialNumber) {
        if (issuer == null) {
            throw new NullPointerException("The issuerName cannot be null");
        }
        if (serialNumber == null) {
            throw new NullPointerException("The serialNumber cannot be null");
        }
        this.issuer = new X500Principal(issuer).getName();
        this.serialNumber = serialNumber;
        this.element = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:X509IssuerSerial");
        Element issuerNameElement = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:X509IssuerName");
        issuerNameElement.appendChild(doc.createTextNode(this.issuer));
        this.element.appendChild(issuerNameElement);
        Element serialNumberElement = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:X509SerialNumber");
        serialNumberElement.appendChild(doc.createTextNode(serialNumber.toString()));
        this.element.appendChild(serialNumberElement);
    }

    public Element getElement() {
        return this.element;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public BigInteger getSerialNumber() {
        return this.serialNumber;
    }

    public String toString() {
        return DOM2Writer.nodeToString(this.element);
    }
}

