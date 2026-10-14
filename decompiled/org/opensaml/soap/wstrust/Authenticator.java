/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wstrust.CombinedHash;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Authenticator
extends ElementExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Authenticator";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Authenticator", "wst");
    public static final String TYPE_LOCAL_NAME = "AuthenticatorType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AuthenticatorType", "wst");

    public CombinedHash getCombinedHash();

    public void setCombinedHash(CombinedHash var1);
}

