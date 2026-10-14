/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Claims
extends AttributeExtensibleXMLObject,
ElementExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Claims";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Claims", "wst");
    public static final String TYPE_LOCAL_NAME = "ClaimsType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ClaimsType", "wst");
    public static final String DIALECT_ATTRIB_NAME = "Dialect";

    public String getDialect();

    public void setDialect(String var1);
}

