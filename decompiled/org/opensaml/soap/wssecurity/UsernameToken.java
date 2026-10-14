/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.soap.wssecurity.Username;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public interface UsernameToken
extends IdBearing,
AttributeExtensibleXMLObject,
ElementExtensibleXMLObject,
WSSecurityObject {
    public static final String ELEMENT_LOCAL_NAME = "UsernameToken";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken", "wsse");
    public static final String TYPE_LOCAL_NAME = "UsernameTokenType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameTokenType", "wsse");

    public Username getUsername();

    public void setUsername(Username var1);
}

