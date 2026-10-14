/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapVersion;

public final class Soap12
implements SoapVersion {
    public static final String SOAP_NAMESPACE = "http://www.w3.org/2003/05/soap-envelope";
    private static final Soap12 INSTANCE = new Soap12();
    private static final double VERSION = 1.2;
    private static final String NONE_ROLE = "http://www.w3.org/2003/05/soap-envelope/role/none";
    private static final String ULTIMATE_RECEIVER_ROLE = "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver";
    private static final String NEXT_ROLE = "http://www.w3.org/2003/05/soap-envelope/role/next";
    private static final String SOAP_ENCODING_STYLE = "http://www.w3.org/2003/05/soap-encoding";
    private final QName envelope = new QName("http://www.w3.org/2003/05/soap-envelope", "Envelope");
    private final QName header = new QName("http://www.w3.org/2003/05/soap-envelope", "Header");
    private final QName body = new QName("http://www.w3.org/2003/05/soap-envelope", "Body");
    private final QName fault = new QName("http://www.w3.org/2003/05/soap-envelope", "Fault");

    private Soap12() {
    }

    public static Soap12 getInstance() {
        return INSTANCE;
    }

    @Override
    public String getBindingId() {
        return "http://schemas.xmlsoap.org/wsdl/soap12/";
    }

    @Override
    public double getVersion() {
        return 1.2;
    }

    @Override
    public String getNamespace() {
        return SOAP_NAMESPACE;
    }

    @Override
    public QName getEnvelope() {
        return this.envelope;
    }

    @Override
    public QName getHeader() {
        return this.header;
    }

    @Override
    public QName getBody() {
        return this.body;
    }

    @Override
    public QName getFault() {
        return this.fault;
    }

    @Override
    public String getSoapEncodingStyle() {
        return SOAP_ENCODING_STYLE;
    }

    @Override
    public String getNoneRole() {
        return NONE_ROLE;
    }

    @Override
    public String getUltimateReceiverRole() {
        return ULTIMATE_RECEIVER_ROLE;
    }

    @Override
    public String getNextRole() {
        return NEXT_ROLE;
    }

    @Override
    public String getAttrNameRole() {
        return "role";
    }

    @Override
    public String getAttrNameMustUnderstand() {
        return "mustUnderstand";
    }

    @Override
    public String getAttrValueMustUnderstand(boolean value) {
        return value ? "true" : "false";
    }

    @Override
    public QName getReceiver() {
        return new QName(SOAP_NAMESPACE, "Receiver");
    }

    @Override
    public QName getSender() {
        return new QName(SOAP_NAMESPACE, "Sender");
    }

    @Override
    public QName getMustUnderstand() {
        return new QName(SOAP_NAMESPACE, "MustUnderstand");
    }

    @Override
    public QName getVersionMismatch() {
        return new QName(SOAP_NAMESPACE, "VersionMismatch");
    }

    @Override
    public QName getDateEncodingUnknown() {
        return new QName(SOAP_NAMESPACE, "DataEncodingUnknown");
    }

    @Override
    public String getContentType() {
        return "application/soap+xml";
    }
}

