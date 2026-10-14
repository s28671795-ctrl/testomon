/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface AllowPostdating
extends WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "AllowPostdating";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AllowPostdating", "wst");
    public static final String TYPE_LOCAL_NAME = "AllowPostdatingType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AllowPostdatingType", "wst");
}

