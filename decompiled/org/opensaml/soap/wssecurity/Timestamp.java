/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wssecurity.Created;
import org.opensaml.soap.wssecurity.Expires;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public interface Timestamp
extends IdBearing,
AttributeExtensibleXMLObject,
ElementExtensibleXMLObject,
WSSecurityObject {
    public static final String ELEMENT_LOCAL_NAME = "Timestamp";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp", "wsu");

    public Created getCreated();

    public void setCreated(Created var1);

    public Expires getExpires();

    public void setExpires(Expires var1);
}

