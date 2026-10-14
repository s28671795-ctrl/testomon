/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Code
extends XSURI,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Code";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Code", "wst");
    public static final String TYPE_LOCAL_NAME = "StatusCodeOpenEnum";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "StatusCodeOpenEnum", "wst");
    public static final String VALID = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/status/valid";
    public static final String INVALID = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/status/invalid";
}

