/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wsaddressing.EndpointReferenceType;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Issuer
extends EndpointReferenceType,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Issuer";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Issuer", "wst");
}

