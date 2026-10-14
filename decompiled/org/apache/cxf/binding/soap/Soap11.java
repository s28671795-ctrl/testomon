/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapVersion;

public final class Soap11
implements SoapVersion {
    public static final String SOAP_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String SOAP_ENCODING_URI = "http://schemas.xmlsoap.org/soap/encoding/";
    public static final QName ENCODED_STRING = new QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
    public static final QName ENCODED_LONG = new QName("http://schemas.xmlsoap.org/soap/encoding/", "long");
    public static final QName ENCODED_FLOAT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "float");
    public static final QName ENCODED_CHAR = new QName("http://schemas.xmlsoap.org/soap/encoding/", "char");
    public static final QName ENCODED_DOUBLE = new QName("http://schemas.xmlsoap.org/soap/encoding/", "double");
    public static final QName ENCODED_INT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "int");
    public static final QName ENCODED_SHORT = new QName("http://schemas.xmlsoap.org/soap/encoding/", "short");
    public static final QName ENCODED_BOOLEAN = new QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean");
    public static final QName ENCODED_DATETIME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "dateTime");
    public static final QName ENCODED_BASE64 = new QName("http://schemas.xmlsoap.org/soap/encoding/", "base64Binary");
    public static final QName ENCODED_DECIMAL = new QName("http://schemas.xmlsoap.org/soap/encoding/", "decimal");
    public static final QName ENCODED_INTEGER = new QName("http://schemas.xmlsoap.org/soap/encoding/", "integer");
    private static final Soap11 INSTANCE = new Soap11();
    private static final double VERSION = 1.1;
    private static final String NONE_ROLE = "http://schemas.xmlsoap.org/soap/envelope//role/none";
    private static final String ULTIMATE_RECEIVER_ROLE = "http://schemas.xmlsoap.org/soap/envelope//role/ultimateReceiver";
    private static final String NEXT_ROLE = "http://schemas.xmlsoap.org/soap/actor/next";
    private static final String SOAP_ENCODING_STYLE = "http://schemas.xmlsoap.org/soap/encoding/";
    private final QName envelope = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Envelope");
    private final QName header = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Header");
    private final QName body = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Body");
    private final QName fault = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Fault");

    private Soap11() {
    }

    public static Soap11 getInstance() {
        return INSTANCE;
    }

    @Override
    public String getBindingId() {
        return "http://schemas.xmlsoap.org/soap/";
    }

    @Override
    public double getVersion() {
        return 1.1;
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
        return "http://schemas.xmlsoap.org/soap/encoding/";
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
        return "actor";
    }

    @Override
    public String getAttrNameMustUnderstand() {
        return "mustUnderstand";
    }

    @Override
    public String getAttrValueMustUnderstand(boolean value) {
        return value ? "1" : "0";
    }

    @Override
    public QName getReceiver() {
        return new QName(SOAP_NAMESPACE, "Server");
    }

    @Override
    public QName getSender() {
        return new QName(SOAP_NAMESPACE, "Client");
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
        return null;
    }

    @Override
    public String getContentType() {
        return "text/xml";
    }
}

