/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Evidentiary;

public interface AssertionURIRef
extends SAMLObject,
Evidentiary {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AssertionURIRef";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionURIRef", "saml2");

    public String getAssertionURI();

    public void setAssertionURI(String var1);
}

