/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface GivenName
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "GivenName";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "GivenName", "md");

    public String getName();

    public void setName(String var1);
}

