/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AuthnContextDeclRef
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnContextDeclRef";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextDeclRef", "saml2");

    public String getAuthnContextDeclRef();

    public void setAuthnContextDeclRef(String var1);
}

