/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wstrust.RequestSecurityTokenResponseCollection;

public interface IssuedTokens
extends RequestSecurityTokenResponseCollection {
    public static final String ELEMENT_LOCAL_NAME = "IssuedTokens";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "IssuedTokens", "wst");
}

