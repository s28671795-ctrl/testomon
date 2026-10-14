/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.EncodedString;

public interface BinarySecurityToken
extends EncodedString {
    public static final String ELEMENT_LOCAL_NAME = "BinarySecurityToken";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken", "wsse");
    public static final String TYPE_LOCAL_NAME = "BinarySecurityTokenType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityTokenType", "wsse");
    public static final String VALUE_TYPE_ATTRIB_NAME = "ValueType";

    public String getValueType();

    public void setValueType(String var1);
}

