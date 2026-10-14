/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public interface Security
extends AttributeExtensibleXMLObject,
ElementExtensibleXMLObject,
WSSecurityObject {
    public static final String ELEMENT_LOCAL_NAME = "Security";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "wsse");
    public static final String TYPE_LOCAL_NAME = "SecurityHeaderType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityHeaderType", "wsse");
}

