/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface RequestedTokenCancelled
extends WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "RequestedTokenCancelled";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedTokenCancelled", "wst");
    public static final String TYPE_LOCAL_NAME = "RequestedTokenCancelledType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedTokenCancelledType", "wst");
}

