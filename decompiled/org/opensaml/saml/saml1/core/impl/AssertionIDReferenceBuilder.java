/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AssertionIDReference;
import org.opensaml.saml.saml1.core.impl.AssertionIDReferenceImpl;

public class AssertionIDReferenceBuilder
extends AbstractSAMLObjectBuilder<AssertionIDReference> {
    @Override
    public AssertionIDReference buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference", "saml1");
    }

    @Override
    public AssertionIDReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AssertionIDReferenceImpl(namespaceURI, localName, namespacePrefix);
    }
}

