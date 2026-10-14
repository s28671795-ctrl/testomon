/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Challenge
extends XSString,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Challenge";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Challenge", "wst");
}

