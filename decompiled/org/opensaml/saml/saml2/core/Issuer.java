/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.NameIDType;

public interface Issuer
extends NameIDType,
SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Issuer";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "saml2");
    public static final String TYPE_LOCAL_NAME = "IssuerType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "IssuerType", "saml2");
}

