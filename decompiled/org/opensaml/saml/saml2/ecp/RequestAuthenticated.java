/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface RequestAuthenticated
extends SAMLObject,
MustUnderstandBearing,
ActorBearing {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RequestAuthenticated";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RequestAuthenticated", "ecp");
    public static final String TYPE_LOCAL_NAME = "RequestAuthenticatedType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RequestAuthenticatedType", "ecp");
}

