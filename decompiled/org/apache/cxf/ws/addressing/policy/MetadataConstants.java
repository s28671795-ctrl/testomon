/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.policy;

import javax.xml.namespace.QName;

public final class MetadataConstants {
    public static final String NAMESPACE_URI = "http://www.w3.org/2007/02/addressing/metadata";
    public static final String NAMESPACE_URI_0705 = "http://www.w3.org/2007/05/addressing/metadata";
    public static final String ADDRESSING_ELEM_NAME = "Addressing";
    public static final String ANON_RESPONSES_ELEM_NAME = "AnonymousResponses";
    public static final String NON_ANON_RESPONSES_ELEM_NAME = "NonAnonymousResponses";
    public static final String USING_ADDRESSING_ELEM_NAME = "UsingAddressing";
    public static final String ADDR_POLICY_2004_NAMESPACE_URI = "http://schemas.xmlsoap.org/ws/2004/08/addressing/policy";
    public static final String ADDR_WSDL_2005_NAMESPACE_URI = "http://www.w3.org/2005/02/addressing/wsdl";
    public static final String ADDR_WSDL_2006_NAMESPACE_URI = "http://www.w3.org/2006/05/addressing/wsdl";
    public static final QName ADDRESSING_ASSERTION_QNAME = new QName("http://www.w3.org/2007/02/addressing/metadata", "Addressing");
    public static final QName ANON_RESPONSES_ASSERTION_QNAME = new QName("http://www.w3.org/2007/02/addressing/metadata", "AnonymousResponses");
    public static final QName NON_ANON_RESPONSES_ASSERTION_QNAME = new QName("http://www.w3.org/2007/02/addressing/metadata", "NonAnonymousResponses");
    public static final QName ADDRESSING_ASSERTION_QNAME_0705 = new QName("http://www.w3.org/2007/05/addressing/metadata", "Addressing");
    public static final QName ANON_RESPONSES_ASSERTION_QNAME_0705 = new QName("http://www.w3.org/2007/05/addressing/metadata", "AnonymousResponses");
    public static final QName NON_ANON_RESPONSES_ASSERTION_QNAME_0705 = new QName("http://www.w3.org/2007/05/addressing/metadata", "NonAnonymousResponses");
    public static final QName USING_ADDRESSING_2004_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing/policy", "UsingAddressing");
    public static final QName USING_ADDRESSING_2005_QNAME = new QName("http://www.w3.org/2005/02/addressing/wsdl", "UsingAddressing");
    public static final QName USING_ADDRESSING_2006_QNAME = new QName("http://www.w3.org/2006/05/addressing/wsdl", "UsingAddressing");

    private MetadataConstants() {
    }
}

