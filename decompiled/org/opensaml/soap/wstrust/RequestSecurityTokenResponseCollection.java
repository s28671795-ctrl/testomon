/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.soap.wstrust.RequestSecurityTokenResponse;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface RequestSecurityTokenResponseCollection
extends AttributeExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "RequestSecurityTokenResponseCollection";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponseCollection", "wst");
    public static final String TYPE_LOCAL_NAME = "RequestSecurityTokenResponseCollectionType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponseCollectionType", "wst");

    public List<RequestSecurityTokenResponse> getRequestSecurityTokenResponses();
}

