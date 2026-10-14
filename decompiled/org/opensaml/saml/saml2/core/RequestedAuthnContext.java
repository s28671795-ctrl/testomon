/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;

public interface RequestedAuthnContext
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RequestedAuthnContext";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "RequestedAuthnContext", "saml2p");
    public static final String TYPE_LOCAL_NAME = "RequestedAuthnContextType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "RequestedAuthnContextType", "saml2p");
    public static final String COMPARISON_ATTRIB_NAME = "Comparison";

    public AuthnContextComparisonTypeEnumeration getComparison();

    public void setComparison(AuthnContextComparisonTypeEnumeration var1);

    public List<AuthnContextClassRef> getAuthnContextClassRefs();

    public List<AuthnContextDeclRef> getAuthnContextDeclRefs();
}

