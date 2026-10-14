/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AttributeProfile
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeProfile";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeProfile", "md");
    public static final String TYPE_LOCAL_NAME = "AttributeProfileType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeProfileType", "md");

    public String getProfileURI();

    public void setProfileURI(String var1);
}

