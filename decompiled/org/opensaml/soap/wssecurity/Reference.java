/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public interface Reference
extends AttributeExtensibleXMLObject,
WSSecurityObject {
    public static final String ELEMENT_LOCAL_NAME = "Reference";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Reference", "wsse");
    public static final String TYPE_LOCAL_NAME = "ReferenceType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "ReferenceType", "wsse");
    public static final String URI_ATTRIB_NAME = "URI";
    public static final String VALUE_TYPE_ATTRIB_NAME = "ValueType";

    public String getURI();

    public void setURI(String var1);

    public String getValueType();

    public void setValueType(String var1);
}

