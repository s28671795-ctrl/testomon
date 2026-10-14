/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.SecurityTokenReference;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface RequestedReferenceType
extends WSTrustObject {
    public static final String TYPE_LOCAL_NAME = "RequestedReferenceType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedReferenceType", "wst");

    public SecurityTokenReference getSecurityTokenReference();

    public void setSecurityTokenReference(SecurityTokenReference var1);
}

