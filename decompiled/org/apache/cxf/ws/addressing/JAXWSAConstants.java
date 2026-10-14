/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import javax.xml.namespace.QName;

public final class JAXWSAConstants {
    public static final String WSAW_PREFIX = "wsaw";
    public static final String NS_WSAW = "http://www.w3.org/2006/05/addressing/wsdl";
    public static final String WSAM_PREFIX = "wsam";
    public static final String NS_WSAM = "http://www.w3.org/2007/05/addressing/metadata";
    public static final QName WSAW_ACTION_QNAME = new QName("http://www.w3.org/2006/05/addressing/wsdl", "Action");
    public static final QName WSAM_ACTION_QNAME = new QName("http://www.w3.org/2007/05/addressing/metadata", "Action");
    public static final QName WSAW_USINGADDRESSING_QNAME = new QName("http://www.w3.org/2006/05/addressing/wsdl", "UsingAddressing");
    public static final String NS_WSA = "http://www.w3.org/2005/08/addressing";
    public static final String WSA_PREFIX = "wsa";
    public static final String WSA_XSD = "http://www.w3.org/2006/03/addressing/ws-addr.xsd";
    public static final String WSA_ERF_NAME = "EndpointReference";
    public static final String WSA_REFERENCEPARAMETERS_NAME = "ReferenceParameters";
    public static final String WSA_METADATA_NAME = "Metadata";
    public static final String WSA_ADDRESS_NAME = "Address";
    public static final String WSAM_SERVICENAME_NAME = "ServiceName";
    public static final String WSAM_INTERFACE_NAME = "InterfaceName";
    public static final String WSAM_ENDPOINT_NAME = "EndpointName";
    public static final String WSDLI_PFX = "wsdli";
    public static final String WSDLI_WSDLLOCATION = "wsdlLocation";
    public static final String NS_WSDLI = "http://www.w3.org/ns/wsdl-instance";
    public static final String CLIENT_ADDRESSING_PROPERTIES = "javax.xml.ws.addressing.context";
    public static final String ADDRESSING_PROPERTIES_INBOUND = "javax.xml.ws.addressing.context.inbound";
    public static final String ADDRESSING_PROPERTIES_OUTBOUND = "javax.xml.ws.addressing.context.outbound";
    @Deprecated
    public static final String CLIENT_ADDRESSING_PROPERTIES_INBOUND = "javax.xml.ws.addressing.context.inbound";
    @Deprecated
    public static final String CLIENT_ADDRESSING_PROPERTIES_OUTBOUND = "javax.xml.ws.addressing.context.outbound";
    @Deprecated
    public static final String SERVER_ADDRESSING_PROPERTIES_INBOUND = "javax.xml.ws.addressing.context.inbound";
    @Deprecated
    public static final String SERVER_ADDRESSING_PROPERTIES_OUTBOUND = "javax.xml.ws.addressing.context.outbound";

    private JAXWSAConstants() {
    }
}

