/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface ComputedKey
extends XSURI,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "ComputedKey";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKey", "wst");
    public static final String TYPE_LOCAL_NAME = "ComputedKeyOpenEnum";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKeyOpenEnum", "wst");
    public static final String PSHA1 = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/CK/PSHA1";
    public static final String HASH = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/CK/HASH";
}

