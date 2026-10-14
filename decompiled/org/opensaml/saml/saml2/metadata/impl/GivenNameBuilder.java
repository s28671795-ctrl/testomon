/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.GivenName;
import org.opensaml.saml.saml2.metadata.impl.GivenNameImpl;

public class GivenNameBuilder
extends AbstractSAMLObjectBuilder<GivenName> {
    @Override
    public GivenName buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "GivenName", "md");
    }

    @Override
    public GivenName buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new GivenNameImpl(namespaceURI, localName, namespacePrefix);
    }
}

