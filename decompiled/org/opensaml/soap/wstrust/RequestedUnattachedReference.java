/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wstrust.RequestedReferenceType;

public interface RequestedUnattachedReference
extends RequestedReferenceType {
    public static final String ELEMENT_LOCAL_NAME = "RequestedUnattachedReference";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedUnattachedReference", "wst");
}

