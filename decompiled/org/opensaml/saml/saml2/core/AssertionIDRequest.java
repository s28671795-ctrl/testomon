/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.RequestAbstractType;

public interface AssertionIDRequest
extends RequestAbstractType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AssertionIDRequest";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AssertionIDRequest", "saml2p");

    public List<AssertionIDRef> getAssertionIDRefs();
}

