/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import javax.xml.namespace.QName;

public final class WSDLConstants {
    public static final String WSDL_PREFIX = "wsdl";
    public static final String NS_WSDL11 = "http://schemas.xmlsoap.org/wsdl/";
    public static final String NP_XMLNS = "xmlns";
    public static final String NS_XMLNS = "http://www.w3.org/2000/xmlns/";
    public static final String NP_SCHEMA_XSD = "xsd";
    public static final String NS_SCHEMA_XSD = "http://www.w3.org/2001/XMLSchema";
    public static final QName QNAME_SCHEMA = new QName("http://www.w3.org/2001/XMLSchema", "schema");
    public static final QName QNAME_SCHEMA_IMPORT = new QName("http://www.w3.org/2001/XMLSchema", "import");
    public static final QName QNAME_SCHEMA_INCLUDE = new QName("http://www.w3.org/2001/XMLSchema", "include");
    public static final String NP_SCHEMA_XSI = "xsi";
    public static final String NS_SCHEMA_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String A_XSI_TYPE = "type";
    public static final String A_XSI_NIL = "nil";
    public static final QName NA_XSI_TYPE = new QName("xsi", "type", "http://www.w3.org/2001/XMLSchema-instance");
    public static final QName NA_XSI_NIL = new QName("xsi", "nil", "http://www.w3.org/2001/XMLSchema-instance");
    public static final String NS_SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
    public static final String NS_SOAP11 = "http://schemas.xmlsoap.org/wsdl/soap/";
    public static final String NS_SOAP12 = "http://schemas.xmlsoap.org/wsdl/soap12/";
    public static final String SOAP11_PREFIX = "soap";
    public static final String SOAP12_PREFIX = "soap12";
    public static final String NS_SOAP_HTTP_TRANSPORT = "http://schemas.xmlsoap.org/soap/http";
    public static final String NS_SOAP11_HTTP_TRANSPORT = "http://schemas.xmlsoap.org/soap/http";
    public static final QName QNAME_SOAP_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "binding");
    public static final QName QNAME_SOAP_OPERATION = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "operation");
    public static final QName QNAME_SOAP_BODY = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "body");
    public static final QName QNAME_SOAP_FAULT = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "fault");
    public static final QName QNAME_SOAP_BINDING_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address");
    public static final String NS_SOAP12_HTTP_BINDING = "http://www.w3.org/2003/05/soap/bindings/HTTP/";
    public static final QName QNAME_SOAP12_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "binding");
    public static final QName QNAME_SOAP12_BINDING_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "address");
    public static final String DOCUMENT = "document";
    public static final String RPC = "rpc";
    public static final String LITERAL = "literal";
    public static final String REPLACE_WITH_ACTUAL_URL = "REPLACE_WITH_ACTUAL_URL";
    public static final String JMS_PREFIX = "jms";
    public static final String TNS_PREFIX = "tns";
    public static final QName QNAME_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/", "binding");
    public static final QName QNAME_DEFINITIONS = new QName("http://schemas.xmlsoap.org/wsdl/", "definitions");
    public static final QName QNAME_DOCUMENTATION = new QName("http://schemas.xmlsoap.org/wsdl/", "documentation");
    public static final QName QNAME_IMPORT = new QName("http://schemas.xmlsoap.org/wsdl/", "import");
    public static final QName QNAME_MESSAGE = new QName("http://schemas.xmlsoap.org/wsdl/", "message");
    public static final QName QNAME_PART = new QName("http://schemas.xmlsoap.org/wsdl/", "part");
    public static final QName QNAME_OPERATION = new QName("http://schemas.xmlsoap.org/wsdl/", "operation");
    public static final QName QNAME_INPUT = new QName("http://schemas.xmlsoap.org/wsdl/", "input");
    public static final QName QNAME_OUTPUT = new QName("http://schemas.xmlsoap.org/wsdl/", "output");
    public static final QName QNAME_PORT = new QName("http://schemas.xmlsoap.org/wsdl/", "port");
    public static final QName QNAME_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/", "address");
    public static final QName QNAME_PORT_TYPE = new QName("http://schemas.xmlsoap.org/wsdl/", "portType");
    public static final QName QNAME_FAULT = new QName("http://schemas.xmlsoap.org/wsdl/", "fault");
    public static final QName QNAME_SERVICE = new QName("http://schemas.xmlsoap.org/wsdl/", "service");
    public static final QName QNAME_TYPES = new QName("http://schemas.xmlsoap.org/wsdl/", "types");
    public static final String ATTR_PART_ELEMENT = "element";
    public static final String ATTR_PART_TYPE = "type";
    public static final String ATTR_TYPE = "type";
    public static final int DOC_WRAPPED = 1;
    public static final int DOC_BARE = 2;
    public static final int RPC_WRAPPED = 3;
    public static final int ERORR_STYLE_USE = -1;
    public static final String NS_BINDING_XML = "http://cxf.apache.org/bindings/xformat";
    public static final QName QNAME_XMLHTTP_BINDING_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/http/", "address");
    public static final String ATTR_TRANSPORT = "transport";
    public static final String ATTR_LOCATION = "location";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_NAMESPACE = "namespace";
    public static final String ATTR_TNS = "targetNamespace";
    public static final String CONVENTIONAL_TNS_PREFIX = "tns";
    public static final String WSDL11 = "1.1";
    public static final String WSDL20 = "2.0";

    private WSDLConstants() {
    }

    public static WSDLVersion getVersion(String version) {
        if (WSDL11.equals(version)) {
            return WSDLVersion.WSDL11;
        }
        if (WSDL20.equals(version)) {
            return WSDLVersion.WSDL20;
        }
        return WSDLVersion.UNKNOWN;
    }

    public static enum WSDLVersion {
        WSDL11,
        WSDL20,
        UNKNOWN;

    }
}

