/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface OnBehalfOf
extends ElementExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "OnBehalfOf";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "OnBehalfOf", "wst");
    public static final String TYPE_LOCAL_NAME = "OnBehalfOfType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "OnBehalfOfType", "wst");
}

