/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface KeyType
extends XSURI,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "KeyType";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyType", "wst");
    public static final String TYPE_LOCAL_NAME = "KeyTypeOpenEnum";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyTypeOpenEnum", "wst");
    public static final String PUBLIC_KEY = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/PublicKey";
    public static final String SYMMETRIC_KEY = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/SymmetricKey";
    public static final String BEARER = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Bearer";
}

