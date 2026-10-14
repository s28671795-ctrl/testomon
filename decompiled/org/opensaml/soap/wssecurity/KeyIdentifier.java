/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.EncodedString;

public interface KeyIdentifier
extends EncodedString {
    public static final String ELEMENT_LOCAL_NAME = "KeyIdentifier";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "KeyIdentifier", "wsse");
    public static final String TYPE_LOCAL_NAME = "KeyIdentifierType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "KeyIdentifierType", "wsse");
    public static final String VALUE_TYPE_ATTRIB_NAME = "ValueType";

    public String getValueType();

    public void setValueType(String var1);
}

