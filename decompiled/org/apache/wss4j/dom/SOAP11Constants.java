/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom;

import javax.xml.namespace.QName;
import org.apache.wss4j.dom.SOAPConstants;

public class SOAP11Constants
implements SOAPConstants {
    private static final long serialVersionUID = 3809268485386395322L;
    private static final QName HEADER_QNAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Header");
    private static final QName BODY_QNAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Body");
    private static final QName ROLE_QNAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "actor");

    @Override
    public String getEnvelopeURI() {
        return "http://schemas.xmlsoap.org/soap/envelope/";
    }

    @Override
    public QName getHeaderQName() {
        return HEADER_QNAME;
    }

    @Override
    public QName getBodyQName() {
        return BODY_QNAME;
    }

    @Override
    public QName getRoleAttributeQName() {
        return ROLE_QNAME;
    }

    @Override
    public String getNextRoleURI() {
        return "http://schemas.xmlsoap.org/soap/actor/next";
    }

    @Override
    public String getMustUnderstand() {
        return "1";
    }
}

