/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSInteger;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface KeySize
extends XSInteger,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "KeySize";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeySize", "wst");
}

