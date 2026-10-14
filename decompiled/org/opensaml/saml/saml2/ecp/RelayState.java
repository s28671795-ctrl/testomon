/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface RelayState
extends XSString,
SAMLObject,
MustUnderstandBearing,
ActorBearing {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RelayState";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RelayState", "ecp");
    public static final String TYPE_LOCAL_NAME = "RelayStateType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RelayStateType", "ecp");
}

