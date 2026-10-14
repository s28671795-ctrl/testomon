/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom;

import javax.xml.namespace.QName;
import org.apache.wss4j.dom.SOAPConstants;

public class SOAP12Constants
implements SOAPConstants {
    private static final long serialVersionUID = 3784866613259361834L;
    private static final QName HEADER_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Header");
    private static final QName BODY_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Body");
    private static final QName ROLE_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "role");
    public static final String PROP_WEBMETHOD = "soap12.webmethod";

    @Override
    public String getEnvelopeURI() {
        return "http://www.w3.org/2003/05/soap-envelope";
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
        return "http://www.w3.org/2003/05/soap-envelope/role/next";
    }

    @Override
    public String getMustUnderstand() {
        return "true";
    }
}

