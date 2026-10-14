/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.Created;
import org.opensaml.soap.wssecurity.Expires;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Lifetime
extends WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Lifetime";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Lifetime", "wst");
    public static final String TYPE_LOCAL_NAME = "LifetimeType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "LifetimeType", "wst");

    public Created getCreated();

    public void setCreated(Created var1);

    public Expires getExpires();

    public void setExpires(Expires var1);
}

