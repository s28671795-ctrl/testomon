/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface RequestSecurityToken
extends ElementExtensibleXMLObject,
AttributeExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "RequestSecurityToken";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityToken", "wst");
    public static final String TYPE_LOCAL_NAME = "RequestSecurityTokenType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenType", "wst");
    public static final String CONTEXT_ATTRIB_NAME = "Context";

    public String getContext();

    public void setContext(String var1);
}

