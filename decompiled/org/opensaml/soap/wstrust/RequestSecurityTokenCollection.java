/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.soap.wstrust.RequestSecurityToken;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface RequestSecurityTokenCollection
extends WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "RequestSecurityTokenCollection";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenCollection", "wst");
    public static final String TYPE_LOCAL_NAME = "RequestSecurityTokenCollectionType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenCollectionType", "wst");

    public List<RequestSecurityToken> getRequestSecurityTokens();
}

