/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.impl.AssertionIDRequestServiceImpl;

public class AssertionIDRequestServiceBuilder
extends AbstractSAMLObjectBuilder<AssertionIDRequestService> {
    @Override
    public AssertionIDRequestService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AssertionIDRequestService", "md");
    }

    @Override
    public AssertionIDRequestService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AssertionIDRequestServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

