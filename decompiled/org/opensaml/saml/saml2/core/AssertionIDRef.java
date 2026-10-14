/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Evidentiary;

public interface AssertionIDRef
extends SAMLObject,
Evidentiary {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AssertionIDRef";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionIDRef", "saml2");

    public String getAssertionID();

    public void setAssertionID(String var1);
}

