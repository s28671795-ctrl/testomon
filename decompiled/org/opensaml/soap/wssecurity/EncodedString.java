/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.AttributedString;

public interface EncodedString
extends AttributedString {
    public static final String TYPE_LOCAL_NAME = "EncodedString";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "EncodedString", "wsse");
    public static final String ENCODING_TYPE_ATTRIB_NAME = "EncodingType";
    public static final String ENCODING_TYPE_BASE64_BINARY = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";

    public String getEncodingType();

    public void setEncodingType(String var1);
}

