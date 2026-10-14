/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Audience
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Audience";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Audience", "saml2");
    public static final String TYPE_LOCAL_NAME = "AudienceType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceType", "saml2");

    public String getAudienceURI();

    public void setAudienceURI(String var1);
}

