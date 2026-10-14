/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface RequestSecurityTokenResponse
extends ElementExtensibleXMLObject,
AttributeExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "RequestSecurityTokenResponse";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponse", "wst");
    public static final String TYPE_LOCAL_NAME = "RequestSecurityTokenResponseType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponseType", "wst");
    public static final String CONTEXT_ATTRIB_NAME = "Context";

    public String getContext();

    public void setContext(String var1);
}

