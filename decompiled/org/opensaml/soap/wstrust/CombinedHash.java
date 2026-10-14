/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface CombinedHash
extends XSBase64Binary,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "CombinedHash";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CombinedHash", "wst");
    public static final String AUTH_HASH = "AUTH-HASH";
}

